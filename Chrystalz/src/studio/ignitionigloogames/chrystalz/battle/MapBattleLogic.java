/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.battle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import studio.ignitionigloogames.chrystalz.Application;
import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.ai.AbstractMapAIRoutine;
import studio.ignitionigloogames.chrystalz.ai.AutoMapAI;
import studio.ignitionigloogames.chrystalz.ai.MapAIContext;
import studio.ignitionigloogames.chrystalz.battle.damageengines.AbstractDamageEngine;
import studio.ignitionigloogames.chrystalz.battle.rewards.BattleRewards;
import studio.ignitionigloogames.chrystalz.battle.types.AbstractBattleType;
import studio.ignitionigloogames.chrystalz.creatures.AbstractCreature;
import studio.ignitionigloogames.chrystalz.creatures.StatConstants;
import studio.ignitionigloogames.chrystalz.creatures.monsters.MonsterFactory;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyMember;
import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.dungeon.DungeonConstants;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractBattleCharacter;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractGameObject;
import studio.ignitionigloogames.chrystalz.dungeon.objects.BattleCharacter;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Empty;
import studio.ignitionigloogames.chrystalz.effects.Effect;
import studio.ignitionigloogames.chrystalz.manager.asset.MusicConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.MusicManager;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundManager;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;
import studio.ignitionigloogames.chrystalz.spells.Spell;
import studio.ignitionigloogames.chrystalz.spells.SpellCaster;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;
import studio.ignitionigloogames.common.random.RandomRange;

public class MapBattleLogic extends AbstractBattle {
    // Fields
    private AbstractBattleType battleType;
    private MapBattleDefinitions bd;
    private AbstractDamageEngine pde;
    private AbstractDamageEngine ede;
    private final AutoMapAI auto;
    private int damage;
    private BattleResult result;
    private int activeIndex;
    private long battleExp;
    private boolean newRound;
    private int[] speedArray;
    private int lastSpeed;
    private boolean[] speedMarkArray;
    private boolean resultDoneAlready;
    private boolean lastAIActionResult;
    private final MapBattleAITask ait;
    private MapBattleGUI battleGUI;
    private BattleCharacter enemy;
    private static final int STEAL_ACTION_POINTS = 3;
    private static final int DRAIN_ACTION_POINTS = 3;

    // Constructors
    public MapBattleLogic() {
        this.battleGUI = new MapBattleGUI();
        this.auto = new AutoMapAI();
        this.ait = new MapBattleAITask(this);
        this.ait.start();
    }

    // Methods
    @Override
    public JFrame getOutputFrame() {
        return this.battleGUI.getOutputFrame();
    }

    @Override
    public void doBattle() {
        this.battleType = AbstractBattleType.createBattle();
        if (MusicManager.isMusicPlaying()) {
            MusicManager.stopMusic();
        }
        MusicManager.playMusic(MusicConstants.MUSIC_BATTLE);
        this.doBattleInternal();
    }

    @Override
    public void doBossBattle() {
        this.battleType = AbstractBattleType.createBossBattle();
        if (MusicManager.isMusicPlaying()) {
            MusicManager.stopMusic();
        }
        MusicManager.playMusic(MusicConstants.MUSIC_BOSS);
        this.doBattleInternal();
    }

    @Override
    public void doFinalBossBattle() {
        this.battleType = AbstractBattleType.createFinalBossBattle();
        if (MusicManager.isMusicPlaying()) {
            MusicManager.stopMusic();
        }
        MusicManager.playMusic(MusicConstants.MUSIC_BOSS);
        this.doBattleInternal();
    }

    @Override
    public void doBattleByProxy() {
        final AbstractCreature m = MonsterFactory.getNewMonsterInstance();
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        playerCharacter.offsetExperience(m.getExperience());
        playerCharacter.offsetGold(m.getGold());
        // Level Up Check
        if (playerCharacter.checkLevelUp()) {
            playerCharacter.levelUp();
            Chrystalz.getApplication().getGame().keepNextMessage();
            Chrystalz.getApplication().showMessage(
                    "You reached level " + playerCharacter.getLevel() + ".");
        }
    }

    private void doBattleInternal() {
        // Initialize Battle
        final Dungeon bMap = Dungeon.getTemporaryBattleCopy();
        Chrystalz.getApplication().getGame().hideOutput();
        Chrystalz.getApplication().setMode(Application.STATUS_BATTLE);
        this.bd = new MapBattleDefinitions();
        this.bd.setBattleDungeon(bMap);
        this.pde = AbstractDamageEngine.getPlayerInstance();
        this.ede = AbstractDamageEngine.getEnemyInstance();
        this.resultDoneAlready = false;
        this.result = BattleResult.IN_PROGRESS;
        // Generate Friends
        final BattleCharacter friends = PartyManager.getParty()
                .getBattleCharacters();
        // Generate Enemies
        this.enemy = this.battleType.getBattlers();
        this.enemy.getTemplate().healAndRegenerateFully();
        this.enemy.getTemplate().loadCreature();
        // Merge and Create AI Contexts
        for (int x = 0; x < 2; x++) {
            if (x == 0) {
                this.bd.addBattler(friends);
            } else {
                this.bd.addBattler(this.enemy);
            }
            if (this.bd.getBattlers()[x] != null) {
                // Create an AI Context
                this.bd.getBattlerAIContexts()[x] = new MapAIContext(
                        this.bd.getBattlers()[x], this.bd.getBattleDungeon());
            }
        }
        // Reset Inactive Indicators and Action Counters
        this.bd.resetBattlers();
        // Generate Speed Array
        this.generateSpeedArray();
        // Set Character Locations
        this.setCharacterLocations();
        // Set First Active
        this.newRound = this.setNextActive(true);
        // Clear status message
        this.clearStatusMessage();
        // Start Battle
        this.battleGUI.getViewManager()
                .setViewingWindowCenterX(this.bd.getActiveCharacter().getY());
        this.battleGUI.getViewManager()
                .setViewingWindowCenterY(this.bd.getActiveCharacter().getX());
        SoundManager.playSound(SoundConstants.FIGHT);
        this.showBattle();
        this.updateStatsAndEffects();
        this.redrawBattle();
    }

    @Override
    public void battleDone() {
        // Leave Battle
        this.hideBattle();
        Chrystalz.getApplication().setMode(Application.STATUS_GAME);
        // Return to whence we came
        Chrystalz.getApplication().getGame().showOutput();
        Chrystalz.getApplication().getGame().redrawDungeon();
    }

    private void clearStatusMessage() {
        this.battleGUI.clearStatusMessage();
    }

    @Override
    public void setStatusMessage(final String msg) {
        this.battleGUI.setStatusMessage(msg);
    }

    @Override
    public BattleResult getResult() {
        BattleResult currResult;
        if (this.result != BattleResult.IN_PROGRESS) {
            return this.result;
        }
        if (this.areTeamEnemiesAlive(AbstractCreature.TEAM_PARTY)
                && !this.isTeamAlive(AbstractCreature.TEAM_PARTY)) {
            currResult = BattleResult.LOST;
        } else if (!this.areTeamEnemiesAlive(AbstractCreature.TEAM_PARTY)
                && this.isTeamAlive(AbstractCreature.TEAM_PARTY)) {
            currResult = BattleResult.WON;
        } else if (!this.areTeamEnemiesAlive(AbstractCreature.TEAM_PARTY)
                && !this.isTeamAlive(AbstractCreature.TEAM_PARTY)) {
            currResult = BattleResult.DRAW;
        } else if (this.isTeamAlive(AbstractCreature.TEAM_PARTY)
                && !this.isTeamGone(AbstractCreature.TEAM_PARTY)
                && this.areTeamEnemiesDeadOrGone(AbstractCreature.TEAM_PARTY)) {
            currResult = BattleResult.WON;
        } else if (!this.isTeamAlive(AbstractCreature.TEAM_PARTY)
                && !this.isTeamGone(AbstractCreature.TEAM_PARTY)
                && !this.areTeamEnemiesDeadOrGone(
                        AbstractCreature.TEAM_PARTY)) {
            currResult = BattleResult.LOST;
        } else if (this.areTeamEnemiesGone(AbstractCreature.TEAM_PARTY)) {
            currResult = BattleResult.ENEMY_FLED;
        } else if (this.isTeamGone(AbstractCreature.TEAM_PARTY)) {
            currResult = BattleResult.FLED;
        } else {
            currResult = BattleResult.IN_PROGRESS;
        }
        return currResult;
    }

    @Override
    public void executeNextAIAction() {
        if (this.bd != null && this.bd.getActiveCharacter() != null
                && this.bd.getActiveCharacter().getTemplate() != null
                && this.bd.getActiveCharacter().getTemplate()
                        .getMapAI() != null) {
            final BattleCharacter active = this.bd.getActiveCharacter();
            if (active.getTemplate().isAlive()) {
                final int action = active.getTemplate().getMapAI()
                        .getNextAction(this.bd
                                .getBattlerAIContexts()[this.activeIndex]);
                switch (action) {
                    case AbstractMapAIRoutine.ACTION_MOVE:
                        final int x = active.getTemplate().getMapAI().getMoveX();
                        final int y = active.getTemplate().getMapAI().getMoveY();
                        this.lastAIActionResult = this.updatePosition(x, y);
                        active.getTemplate().getMapAI()
                                .setLastResult(this.lastAIActionResult);
                        break;
                    case AbstractMapAIRoutine.ACTION_CAST_SPELL:
                        this.lastAIActionResult = this.castSpell();
                        active.getTemplate().getMapAI()
                                .setLastResult(this.lastAIActionResult);
                        break;
                    case AbstractMapAIRoutine.ACTION_DRAIN:
                        this.lastAIActionResult = this.drain();
                        active.getTemplate().getMapAI()
                                .setLastResult(this.lastAIActionResult);
                        break;
                    case AbstractMapAIRoutine.ACTION_STEAL:
                        this.lastAIActionResult = this.steal();
                        active.getTemplate().getMapAI()
                                .setLastResult(this.lastAIActionResult);
                        break;
                    default:
                        this.lastAIActionResult = true;
                        this.endTurn();
                        this.stopWaitingForAI();
                        this.ait.aiWait();
                        break;
                }
            }
        }
    }

    @Override
    public boolean getLastAIActionResult() {
        return this.lastAIActionResult;
    }

    private void executeAutoAI(final BattleCharacter acting) {
        final int index = this.bd.findBattler(acting.getName());
        final int action = this.auto
                .getNextAction(this.bd.getBattlerAIContexts()[index]);
        switch (action) {
            case AbstractMapAIRoutine.ACTION_MOVE:
                final int x = this.auto.getMoveX();
                final int y = this.auto.getMoveY();
                final int activeTID = this.bd.getActiveCharacter().getTeamID();
                final BattleCharacter theEnemy = activeTID == AbstractCreature.TEAM_PARTY
                        ? this.enemy
                        : this.bd.getBattlers()[this.bd.findFirstBattlerOnTeam(
                                AbstractCreature.TEAM_PARTY)];
                final AbstractDamageEngine activeDE = activeTID == AbstractCreature.TEAM_PARTY
                        ? this.ede
                        : this.pde;
                this.updatePositionInternal(x, y, false, acting, theEnemy,
                        activeDE);
                break;
            default:
                break;
        }
    }

    private void displayRoundResults(final AbstractCreature theEnemy,
            final AbstractCreature active,
            final AbstractDamageEngine activeDE) {
        // Display round results
        final int hitSound = active.getItems().getWeaponHitSound(active);
        final String activeName = active.getName();
        final String enemyName = theEnemy.getName();
        String damageString = Integer.toString(this.damage);
        String displayDamageString = " ";
        if (this.damage == 0) {
            if (activeDE.weaponMissed()) {
                displayDamageString = activeName + " tries to hit " + enemyName
                        + ", but MISSES!";
                SoundManager.playSound(SoundConstants.MISSED);
            } else if (activeDE.enemyDodged()) {
                displayDamageString = activeName + " tries to hit " + enemyName
                        + ", but " + enemyName + " AVOIDS the attack!";
                SoundManager.playSound(SoundConstants.MISSED);
            } else {
                displayDamageString = activeName + " tries to hit " + enemyName
                        + ", but the attack is BLOCKED!";
                SoundManager.playSound(SoundConstants.MISSED);
            }
        } else if (this.damage < 0) {
            damageString = Integer.toString(-this.damage);
            String displayDamagePrefix = "";
            if (activeDE.weaponCrit() && activeDE.weaponPierce()) {
                displayDamagePrefix = "PIERCING CRITICAL HIT! ";
                SoundManager.playSound(SoundConstants.COUNTER);
                SoundManager.playSound(SoundConstants.CRITICAL_HIT);
            } else if (activeDE.weaponCrit()) {
                displayDamagePrefix = "CRITICAL HIT! ";
                SoundManager.playSound(SoundConstants.CRITICAL_HIT);
            } else if (activeDE.weaponPierce()) {
                displayDamagePrefix = "PIERCING HIT! ";
                SoundManager.playSound(SoundConstants.COUNTER);
            }
            displayDamageString = displayDamagePrefix + activeName
                    + " tries to hit " + enemyName + ", but " + enemyName
                    + " RIPOSTES for " + damageString + " damage!";
            SoundManager.playSound(SoundConstants.COUNTER);
        } else {
            String displayDamagePrefix = "";
            if (activeDE.weaponFumble()) {
                SoundManager.playSound(SoundConstants.FUMBLE);
                displayDamageString = "FUMBLE! " + activeName
                        + " drops their weapon on themselves, doing "
                        + damageString + " damage!";
            } else {
                if (activeDE.weaponCrit() && activeDE.weaponPierce()) {
                    displayDamagePrefix = "PIERCING CRITICAL HIT! ";
                    SoundManager.playSound(SoundConstants.COUNTER);
                    SoundManager.playSound(SoundConstants.CRITICAL_HIT);
                } else if (activeDE.weaponCrit()) {
                    displayDamagePrefix = "CRITICAL HIT! ";
                    SoundManager.playSound(SoundConstants.CRITICAL_HIT);
                } else if (activeDE.weaponPierce()) {
                    displayDamagePrefix = "PIERCING HIT! ";
                    SoundManager.playSound(SoundConstants.COUNTER);
                }
                displayDamageString = displayDamagePrefix + activeName
                        + " hits " + enemyName + " for " + damageString
                        + " damage!";
                SoundManager.playSound(hitSound);
            }
        }
        this.setStatusMessage(displayDamageString);
    }

    private void computeDamage(final AbstractCreature theEnemy,
            final AbstractCreature acting,
            final AbstractDamageEngine activeDE) {
        // Compute Damage
        this.damage = 0;
        final int actual = activeDE.computeDamage(theEnemy, acting);
        // Hit or Missed
        this.damage = actual;
        if (activeDE.weaponFumble()) {
            acting.doDamage(this.damage);
        } else {
            if (this.damage < 0) {
                acting.doDamage(-this.damage);
            } else {
                theEnemy.doDamage(this.damage);
            }
        }
        this.displayRoundResults(theEnemy, acting, activeDE);
    }

    private void generateSpeedArray() {
        this.speedArray = new int[this.bd.getBattlers().length];
        this.speedMarkArray = new boolean[this.speedArray.length];
        this.resetSpeedArray();
    }

    private void resetSpeedArray() {
        for (int x = 0; x < this.speedArray.length; x++) {
            if (this.bd.getBattlers()[x] != null
                    && this.bd.getBattlers()[x].getTemplate().isAlive()) {
                this.speedArray[x] = (int) this.bd.getBattlers()[x]
                        .getTemplate()
                        .getEffectedStat(StatConstants.STAT_AGILITY);
            } else {
                this.speedArray[x] = Integer.MIN_VALUE;
            }
        }
        for (int x = 0; x < this.speedMarkArray.length; x++) {
            if (this.speedArray[x] != Integer.MIN_VALUE) {
                this.speedMarkArray[x] = false;
            } else {
                this.speedMarkArray[x] = true;
            }
        }
    }

    private void setCharacterLocations() {
        final RandomRange randX = new RandomRange(0,
                this.bd.getBattleDungeon().getRows() - 1);
        final RandomRange randY = new RandomRange(0,
                this.bd.getBattleDungeon().getColumns() - 1);
        int rx, ry;
        // Set Character Locations
        for (int x = 0; x < this.bd.getBattlers().length; x++) {
            if (this.bd.getBattlers()[x] != null) {
                if (this.bd.getBattlers()[x].isActive()
                        && this.bd.getBattlers()[x].getTemplate().getX() == -1
                        && this.bd.getBattlers()[x].getTemplate()
                                .getY() == -1) {
                    rx = randX.generate();
                    ry = randY.generate();
                    AbstractGameObject obj = this.bd.getBattleDungeon()
                            .getCell(rx, ry, DungeonConstants.LAYER_OBJECT);
                    while (obj.isSolidInBattle()) {
                        rx = randX.generate();
                        ry = randY.generate();
                        obj = this.bd.getBattleDungeon().getCell(rx, ry,
                                DungeonConstants.LAYER_OBJECT);
                    }
                    this.bd.getBattlers()[x].setX(rx);
                    this.bd.getBattlers()[x].setY(ry);
                    this.bd.getBattleDungeon().setCell(this.bd.getBattlers()[x],
                            rx, ry, DungeonConstants.LAYER_OBJECT);
                }
            }
        }
    }

    private boolean setNextActive(final boolean isNewRound) {
        int res = 0;
        if (isNewRound) {
            res = this.findNextSmallestSpeed(Integer.MAX_VALUE);
        } else {
            res = this.findNextSmallestSpeed(this.lastSpeed);
        }
        if (res != -1) {
            this.lastSpeed = this.speedArray[res];
            this.activeIndex = res;
            this.bd.setActiveCharacter(this.bd.getBattlers()[this.activeIndex]);
            // Check
            if (!this.bd.getActiveCharacter().isActive()) {
                // Inactive, pick new active character
                return this.setNextActive(isNewRound);
            }
            // AI Check
            if (this.bd.getActiveCharacter().getTemplate().hasMapAI()) {
                // Run AI
                this.waitForAI();
                this.ait.aiRun();
            } else {
                // No AI
                SoundManager.playSound(SoundConstants.PLAYER_UP);
            }
            return false;
        } else {
            // Reset Speed Array
            this.resetSpeedArray();
            // Reset Action Counters
            this.bd.roundResetBattlers();
            // Maintain effects
            this.maintainEffects(true);
            this.updateStatsAndEffects();
            // Perform new round actions
            this.performNewRoundActions();
            // Play new round sound
            SoundManager.playSound(SoundConstants.NEXT_ROUND);
            // Nobody to act next, set new round flag
            return true;
        }
    }

    private int findNextSmallestSpeed(final int max) {
        int res = -1;
        int found = 0;
        for (int x = 0; x < this.speedArray.length; x++) {
            if (!this.speedMarkArray[x]) {
                if (this.speedArray[x] <= max && this.speedArray[x] > found) {
                    res = x;
                    found = this.speedArray[x];
                }
            }
        }
        if (res != -1) {
            this.speedMarkArray[res] = true;
        }
        return res;
    }

    private boolean isTeamAlive(final int teamID) {
        for (int x = 0; x < this.bd.getBattlers().length; x++) {
            if (this.bd.getBattlers()[x] != null) {
                if (this.bd.getBattlers()[x].getTeamID() == teamID) {
                    final boolean res = this.bd.getBattlers()[x].getTemplate()
                            .isAlive();
                    if (res) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean areTeamEnemiesAlive(final int teamID) {
        for (int x = 0; x < this.bd.getBattlers().length; x++) {
            if (this.bd.getBattlers()[x] != null) {
                if (this.bd.getBattlers()[x].getTeamID() != teamID) {
                    final boolean res = this.bd.getBattlers()[x].getTemplate()
                            .isAlive();
                    if (res) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean areTeamEnemiesDeadOrGone(final int teamID) {
        int deadCount = 0;
        for (int x = 0; x < this.bd.getBattlers().length; x++) {
            if (this.bd.getBattlers()[x] != null) {
                if (this.bd.getBattlers()[x].getTeamID() != teamID) {
                    final boolean res = this.bd.getBattlers()[x].getTemplate()
                            .isAlive() && this.bd.getBattlers()[x].isActive();
                    if (res) {
                        return false;
                    }
                    if (!this.bd.getBattlers()[x].getTemplate().isAlive()) {
                        deadCount++;
                    }
                }
            }
        }
        return deadCount > 0;
    }

    private boolean areTeamEnemiesGone(final int teamID) {
        boolean res = true;
        for (int x = 0; x < this.bd.getBattlers().length; x++) {
            if (this.bd.getBattlers()[x] != null) {
                if (this.bd.getBattlers()[x].getTeamID() != teamID) {
                    if (this.bd.getBattlers()[x].getTemplate().isAlive()) {
                        res = res && !this.bd.getBattlers()[x].isActive();
                        if (!res) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean isTeamGone(final int teamID) {
        boolean res = true;
        for (int x = 0; x < this.bd.getBattlers().length; x++) {
            if (this.bd.getBattlers()[x] != null) {
                if (this.bd.getBattlers()[x].getTeamID() == teamID) {
                    if (this.bd.getBattlers()[x].getTemplate().isAlive()) {
                        res = res && !this.bd.getBattlers()[x].isActive();
                        if (!res) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean updatePosition(final int x, final int y) {
        final int activeTID = this.bd.getActiveCharacter().getTeamID();
        BattleCharacter theEnemy = activeTID == AbstractCreature.TEAM_PARTY
                ? this.enemy
                : this.bd.getBattlers()[this.bd
                        .findFirstBattlerOnTeam(AbstractCreature.TEAM_PARTY)];
        final AbstractDamageEngine activeDE = activeTID == AbstractCreature.TEAM_PARTY
                ? this.ede
                : this.pde;
        if (x == 0 && y == 0) {
            theEnemy = this.bd.getActiveCharacter();
        }
        return this.updatePositionInternal(x, y, true,
                this.bd.getActiveCharacter(), theEnemy, activeDE);
    }

    private boolean updatePositionInternal(final int x, final int y,
            final boolean useAP, final BattleCharacter activeBC,
            final BattleCharacter theEnemy,
            final AbstractDamageEngine activeDE) {
        final AbstractCreature active = activeBC.getTemplate();
        this.updateAllAIContexts();
        int px = activeBC.getX();
        int py = activeBC.getY();
        final Dungeon m = this.bd.getBattleDungeon();
        AbstractGameObject next = null;
        AbstractGameObject nextGround = null;
        AbstractGameObject currGround = null;
        activeBC.saveLocation();
        this.battleGUI.getViewManager().saveViewingWindow();
        try {
            next = m.getCell(px + x, py + y, DungeonConstants.LAYER_OBJECT);
            nextGround = m.getCell(px + x, py + y,
                    DungeonConstants.LAYER_GROUND);
            currGround = m.getCell(px, py, DungeonConstants.LAYER_GROUND);
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Ignore
        }
        if (next != null && nextGround != null && currGround != null) {
            if (!next.isSolidInBattle()) {
                if (useAP && this.getActiveActionCounter() >= MapAIContext
                        .getAPCost() || !useAP) {
                    // Move
                    AbstractGameObject obj1 = null;
                    AbstractGameObject obj2 = null;
                    AbstractGameObject obj3 = null;
                    AbstractGameObject obj4 = null;
                    AbstractGameObject obj6 = null;
                    AbstractGameObject obj7 = null;
                    AbstractGameObject obj8 = null;
                    AbstractGameObject obj9 = null;
                    try {
                        obj1 = m.getCell(px - 1, py - 1,
                                DungeonConstants.LAYER_OBJECT);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        // Ignore
                    }
                    try {
                        obj2 = m.getCell(px, py - 1,
                                DungeonConstants.LAYER_OBJECT);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        // Ignore
                    }
                    try {
                        obj3 = m.getCell(px + 1, py - 1,
                                DungeonConstants.LAYER_OBJECT);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        // Ignore
                    }
                    try {
                        obj4 = m.getCell(px - 1, py,
                                DungeonConstants.LAYER_OBJECT);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        // Ignore
                    }
                    try {
                        obj6 = m.getCell(px + 1, py - 1,
                                DungeonConstants.LAYER_OBJECT);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        // Ignore
                    }
                    try {
                        obj7 = m.getCell(px - 1, py + 1,
                                DungeonConstants.LAYER_OBJECT);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        // Ignore
                    }
                    try {
                        obj8 = m.getCell(px, py + 1,
                                DungeonConstants.LAYER_OBJECT);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        // Ignore
                    }
                    try {
                        obj9 = m.getCell(px + 1, py + 1,
                                DungeonConstants.LAYER_OBJECT);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        // Ignore
                    }
                    // Auto-attack check
                    if (obj1 != null) {
                        if (obj1 instanceof BattleCharacter) {
                            if (!(x == -1 && y == 0 || x == -1 && y == -1
                                    || x == 0 && y == -1)) {
                                final BattleCharacter bc1 = (BattleCharacter) obj1;
                                if (bc1.getTeamID() != activeBC.getTeamID()) {
                                    this.executeAutoAI(bc1);
                                }
                            }
                        }
                    }
                    if (obj2 != null) {
                        if (obj2 instanceof BattleCharacter) {
                            if (y == 1) {
                                final BattleCharacter bc2 = (BattleCharacter) obj2;
                                if (bc2.getTeamID() != activeBC.getTeamID()) {
                                    this.executeAutoAI(bc2);
                                }
                            }
                        }
                    }
                    if (obj3 != null) {
                        if (obj3 instanceof BattleCharacter) {
                            if (!(x == 0 && y == -1 || x == 1 && y == -1
                                    || x == 1 && y == 0)) {
                                final BattleCharacter bc3 = (BattleCharacter) obj3;
                                if (bc3.getTeamID() != activeBC.getTeamID()) {
                                    this.executeAutoAI(bc3);
                                }
                            }
                        }
                    }
                    if (obj4 != null) {
                        if (obj4 instanceof BattleCharacter) {
                            if (x == 1) {
                                final BattleCharacter bc4 = (BattleCharacter) obj4;
                                if (bc4.getTeamID() != activeBC.getTeamID()) {
                                    this.executeAutoAI(bc4);
                                }
                            }
                        }
                    }
                    if (obj6 != null) {
                        if (obj6 instanceof BattleCharacter) {
                            if (x == -1) {
                                final BattleCharacter bc6 = (BattleCharacter) obj6;
                                if (bc6.getTeamID() != activeBC.getTeamID()) {
                                    this.executeAutoAI(bc6);
                                }
                            }
                        }
                    }
                    if (obj7 != null) {
                        if (obj7 instanceof BattleCharacter) {
                            if (!(x == -1 && y == 0 || x == -1 && y == 1
                                    || x == 0 && y == 1)) {
                                final BattleCharacter bc7 = (BattleCharacter) obj7;
                                if (bc7.getTeamID() != activeBC.getTeamID()) {
                                    this.executeAutoAI(bc7);
                                }
                            }
                        }
                    }
                    if (obj8 != null) {
                        if (obj8 instanceof BattleCharacter) {
                            if (y == -1) {
                                final BattleCharacter bc8 = (BattleCharacter) obj8;
                                if (bc8.getTeamID() != activeBC.getTeamID()) {
                                    this.executeAutoAI(bc8);
                                }
                            }
                        }
                    }
                    if (obj9 != null) {
                        if (obj9 instanceof BattleCharacter) {
                            if (!(x == 0 && y == 1 || x == 1 && y == 1
                                    || x == 1 && y == 0)) {
                                final BattleCharacter bc9 = (BattleCharacter) obj9;
                                if (bc9.getTeamID() != activeBC.getTeamID()) {
                                    this.executeAutoAI(bc9);
                                }
                            }
                        }
                    }
                    m.setCell(activeBC.getSavedObject(), px, py,
                            DungeonConstants.LAYER_OBJECT);
                    activeBC.offsetX(x);
                    activeBC.offsetY(y);
                    px += x;
                    py += y;
                    this.battleGUI.getViewManager()
                            .offsetViewingWindowLocationX(y);
                    this.battleGUI.getViewManager()
                            .offsetViewingWindowLocationY(x);
                    activeBC.setSavedObject(
                            m.getCell(px, py, DungeonConstants.LAYER_OBJECT));
                    m.setCell(activeBC, px, py, DungeonConstants.LAYER_OBJECT);
                    this.decrementActiveActionCounterBy(
                            MapAIContext.getAPCost());
                    SoundManager.playSound(SoundConstants.WALK);
                } else {
                    // Deny move - out of actions
                    if (!this.bd.getActiveCharacter().getTemplate()
                            .hasMapAI()) {
                        this.setStatusMessage("Out of moves!");
                    }
                    return false;
                }
            } else {
                if (next instanceof BattleCharacter) {
                    if (useAP && this.getActiveAttackCounter() > 0 || !useAP) {
                        // Attack
                        final BattleCharacter bc = (BattleCharacter) next;
                        if (bc.getTeamID() == activeBC.getTeamID()) {
                            // Attack Friend?
                            if (!active.hasMapAI()) {
                                final int confirm = CommonDialogs
                                        .showConfirmDialog("Attack Friend?",
                                                "Battle");
                                if (confirm != JOptionPane.YES_OPTION) {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        }
                        if (useAP) {
                            this.decrementActiveAttackCounter();
                        }
                        // Do damage
                        this.computeDamage(theEnemy.getTemplate(), active,
                                activeDE);
                        // Handle low health for party members
                        if (theEnemy.getTemplate().isAlive() && theEnemy
                                .getTeamID() == AbstractCreature.TEAM_PARTY
                                && theEnemy.getTemplate()
                                        .getCurrentHP() <= theEnemy
                                                .getTemplate().getMaximumHP()
                                                * 3 / 10) {
                            SoundManager.playSound(SoundConstants.DANGER);
                        }
                        // Handle enemy death
                        if (!theEnemy.getTemplate().isAlive()) {
                            if (theEnemy
                                    .getTeamID() != AbstractCreature.TEAM_PARTY) {
                                // Update victory spoils
                                this.battleExp = theEnemy.getTemplate()
                                        .getExperience();
                            }
                            this.handleDeath(bc);
                        }
                        // Handle self death
                        if (!active.isAlive()) {
                            this.handleDeath(activeBC);
                        }
                    } else {
                        // Deny attack - out of actions
                        if (!this.bd.getActiveCharacter().getTemplate()
                                .hasMapAI()) {
                            this.setStatusMessage("Out of attacks!");
                        }
                        return false;
                    }
                } else {
                    // Move Failed
                    if (!active.hasMapAI()) {
                        this.setStatusMessage("Can't go that way");
                    }
                    return false;
                }
            }
        } else {
            // Confirm Flee
            if (!active.hasMapAI()) {
                SoundManager.playSound(SoundConstants.SPECIAL);
                final int confirm = CommonDialogs
                        .showConfirmDialog("Embrace Cowardice?", "Battle");
                if (confirm != JOptionPane.YES_OPTION) {
                    this.battleGUI.getViewManager().restoreViewingWindow();
                    activeBC.restoreLocation();
                    return false;
                }
            }
            // Flee
            SoundManager.playSound(SoundConstants.RUN_AWAY);
            this.battleGUI.getViewManager().restoreViewingWindow();
            activeBC.restoreLocation();
            // Set fled character to inactive
            activeBC.deactivate();
            // Remove character from battle
            m.setCell(new Empty(), activeBC.getX(), activeBC.getY(),
                    DungeonConstants.LAYER_OBJECT);
            // End Turn
            this.endTurn();
            this.updateStatsAndEffects();
            final BattleResult currResult = this.getResult();
            if (currResult != BattleResult.IN_PROGRESS) {
                // Battle Done
                this.result = currResult;
                this.doResult();
            }
            this.battleGUI.getViewManager().setViewingWindowCenterX(py);
            this.battleGUI.getViewManager().setViewingWindowCenterY(px);
            this.redrawBattle();
            return true;
        }
        this.updateStatsAndEffects();
        final BattleResult currResult = this.getResult();
        if (currResult != BattleResult.IN_PROGRESS) {
            // Battle Done
            this.result = currResult;
            this.doResult();
        }
        this.battleGUI.getViewManager().setViewingWindowCenterX(py);
        this.battleGUI.getViewManager().setViewingWindowCenterY(px);
        this.redrawBattle();
        return true;
    }

    @Override
    public AbstractCreature getEnemy() {
        return this.enemy.getTemplate();
    }

    private BattleCharacter getEnemyBC() {
        final int px = this.bd.getActiveCharacter().getX();
        final int py = this.bd.getActiveCharacter().getY();
        final Dungeon m = this.bd.getBattleDungeon();
        AbstractGameObject next = null;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                try {
                    next = m.getCell(px + x, py + y,
                            DungeonConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Ignore
                }
                if (next != null) {
                    if (next.isSolidInBattle()) {
                        if (next instanceof BattleCharacter) {
                            return (BattleCharacter) next;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void showBattle() {
        this.battleGUI.showBattle();
    }

    private void hideBattle() {
        this.battleGUI.hideBattle();
    }

    @Override
    public boolean castSpell() {
        // Check Spell Counter
        if (this.getActiveSpellCounter() > 0) {
            if (!this.bd.getActiveCharacter().getTemplate().hasMapAI()) {
                // Active character has no AI, or AI is turned off
                final boolean success = SpellCaster.selectAndCastSpell(
                        this.bd.getActiveCharacter().getTemplate());
                if (success) {
                    SoundManager.playSound(SoundConstants.CAST_SPELL);
                    this.decrementActiveSpellCounter();
                }
                final BattleResult currResult = this.getResult();
                if (currResult != BattleResult.IN_PROGRESS) {
                    // Battle Done
                    this.result = currResult;
                    this.doResult();
                }
                return success;
            } else {
                // Active character has AI, and AI is turned on
                final Spell sp = this.bd.getActiveCharacter().getTemplate()
                        .getMapAI().getSpellToCast();
                final boolean success = SpellCaster.castSpell(sp,
                        this.bd.getActiveCharacter().getTemplate());
                if (success) {
                    SoundManager.playSound(SoundConstants.MONSTER_SPELL);
                    this.decrementActiveSpellCounter();
                }
                final BattleResult currResult = this.getResult();
                if (currResult != BattleResult.IN_PROGRESS) {
                    // Battle Done
                    this.result = currResult;
                    this.doResult();
                }
                return success;
            }
        } else {
            // Deny cast - out of actions
            if (!this.bd.getActiveCharacter().getTemplate().hasMapAI()) {
                this.setStatusMessage("Out of actions!");
            }
            return false;
        }
    }

    @Override
    public boolean steal() {
        // Check Action Counter
        if (this.getActiveActionCounter() > 0) {
            AbstractCreature activeEnemy = null;
            final AbstractBattleCharacter enemyBC = this.getEnemyBC();
            if (enemyBC != null) {
                activeEnemy = enemyBC.getTemplate();
            }
            int stealChance;
            int stealAmount = 0;
            this.bd.getActiveCharacter()
                    .modifyAP(MapBattleLogic.STEAL_ACTION_POINTS);
            stealChance = StatConstants.CHANCE_STEAL;
            if (activeEnemy == null) {
                // Failed - nobody to steal from
                this.setStatusMessage(this.bd.getActiveCharacter().getName()
                        + " tries to steal, but nobody is there to steal from!");
                return false;
            }
            if (stealChance <= 0) {
                // Failed
                this.setStatusMessage(this.bd.getActiveCharacter().getName()
                        + " tries to steal, but fails!");
                return false;
            } else if (stealChance >= 100) {
                // Succeeded, unless target has 0 Gold
                final RandomRange stole = new RandomRange(0,
                        activeEnemy.getGold());
                stealAmount = stole.generate();
                if (stealAmount == 0) {
                    this.setStatusMessage(this.bd.getActiveCharacter().getName()
                            + " tries to steal, but no Gold is left to steal!");
                    return false;
                } else {
                    this.bd.getActiveCharacter().getTemplate()
                            .offsetGold(stealAmount);
                    this.setStatusMessage(this.bd.getActiveCharacter().getName()
                            + " tries to steal, and successfully steals "
                            + stealAmount + " gold!");
                    return true;
                }
            } else {
                final RandomRange chance = new RandomRange(0, 100);
                final int randomChance = chance.generate();
                if (randomChance <= stealChance) {
                    // Succeeded, unless target has 0 Gold
                    final RandomRange stole = new RandomRange(0,
                            activeEnemy.getGold());
                    stealAmount = stole.generate();
                    if (stealAmount == 0) {
                        this.setStatusMessage(this.bd.getActiveCharacter()
                                .getName()
                                + " tries to steal, but no Gold is left to steal!");
                        return false;
                    } else {
                        this.bd.getActiveCharacter().getTemplate()
                                .offsetGold(stealAmount);
                        this.setStatusMessage(this.bd.getActiveCharacter()
                                .getName()
                                + " tries to steal, and successfully steals "
                                + stealAmount + " gold!");
                        return true;
                    }
                } else {
                    // Failed
                    this.setStatusMessage(this.bd.getActiveCharacter().getName()
                            + " tries to steal, but fails!");
                    return false;
                }
            }
        } else {
            // Deny steal - out of actions
            if (!this.bd.getActiveCharacter().getTemplate().hasMapAI()) {
                this.setStatusMessage("Out of actions!");
            }
            return false;
        }
    }

    @Override
    public boolean drain() {
        // Check Action Counter
        if (this.getActiveActionCounter() > 0) {
            AbstractCreature activeEnemy = null;
            final AbstractBattleCharacter enemyBC = this.getEnemyBC();
            if (enemyBC != null) {
                activeEnemy = enemyBC.getTemplate();
            }
            int drainChance;
            int drainAmount = 0;
            this.bd.getActiveCharacter()
                    .modifyAP(MapBattleLogic.DRAIN_ACTION_POINTS);
            drainChance = StatConstants.CHANCE_DRAIN;
            if (activeEnemy == null) {
                // Failed - nobody to drain from
                this.setStatusMessage(this.bd.getActiveCharacter().getName()
                        + " tries to drain, but nobody is there to drain from!");
                return false;
            }
            if (drainChance <= 0) {
                // Failed
                this.setStatusMessage(this.bd.getActiveCharacter().getName()
                        + " tries to drain, but fails!");
                return false;
            } else if (drainChance >= 100) {
                // Succeeded, unless target has 0 MP
                final RandomRange drained = new RandomRange(0,
                        activeEnemy.getCurrentMP());
                drainAmount = drained.generate();
                if (drainAmount == 0) {
                    this.setStatusMessage(this.bd.getActiveCharacter().getName()
                            + " tries to drain, but no MP is left to drain!");
                    return false;
                } else {
                    activeEnemy.offsetCurrentMP(-drainAmount);
                    this.bd.getActiveCharacter().getTemplate()
                            .offsetCurrentMP(drainAmount);
                    this.setStatusMessage(this.bd.getActiveCharacter().getName()
                            + " tries to drain, and successfully drains "
                            + drainAmount + " MP!");
                    return true;
                }
            } else {
                final RandomRange chance = new RandomRange(0, 100);
                final int randomChance = chance.generate();
                if (randomChance <= drainChance) {
                    // Succeeded
                    final RandomRange drained = new RandomRange(0,
                            activeEnemy.getCurrentMP());
                    drainAmount = drained.generate();
                    if (drainAmount == 0) {
                        this.setStatusMessage(this.bd.getActiveCharacter()
                                .getName()
                                + " tries to drain, but no MP is left to drain!");
                        return false;
                    } else {
                        activeEnemy.offsetCurrentMP(-drainAmount);
                        this.bd.getActiveCharacter().getTemplate()
                                .offsetCurrentMP(drainAmount);
                        this.setStatusMessage(this.bd.getActiveCharacter()
                                .getName()
                                + " tries to drain, and successfully drains "
                                + drainAmount + " MP!");
                        return true;
                    }
                } else {
                    // Failed
                    this.setStatusMessage(this.bd.getActiveCharacter().getName()
                            + " tries to drain, but fails!");
                    return false;
                }
            }
        } else {
            // Deny drain - out of actions
            if (!this.bd.getActiveCharacter().getTemplate().hasMapAI()) {
                this.setStatusMessage("Out of actions!");
            }
            return false;
        }
    }

    @Override
    public void endTurn() {
        this.newRound = this.setNextActive(this.newRound);
        if (this.newRound) {
            this.setStatusMessage("New Round");
            this.newRound = this.setNextActive(this.newRound);
            // Check result
            this.result = this.getResult();
            if (this.result != BattleResult.IN_PROGRESS) {
                this.doResult();
                return;
            }
        }
        this.updateStatsAndEffects();
        this.battleGUI.getViewManager()
                .setViewingWindowCenterX(this.bd.getActiveCharacter().getY());
        this.battleGUI.getViewManager()
                .setViewingWindowCenterY(this.bd.getActiveCharacter().getX());
        this.redrawBattle();
    }

    private void redrawBattle() {
        this.battleGUI.redrawBattle(this.bd);
    }

    private void updateStatsAndEffects() {
        this.battleGUI.updateStatsAndEffects(this.bd);
    }

    private int getActiveActionCounter() {
        return this.bd.getActiveCharacter().getCurrentAP();
    }

    private int getActiveAttackCounter() {
        return this.bd.getActiveCharacter().getCurrentAT();
    }

    private int getActiveSpellCounter() {
        return this.bd.getActiveCharacter().getCurrentSP();
    }

    private void decrementActiveActionCounterBy(final int amount) {
        this.bd.getActiveCharacter().modifyAP(amount);
    }

    private void decrementActiveAttackCounter() {
        this.bd.getActiveCharacter().modifyAttacks(1);
    }

    private void decrementActiveSpellCounter() {
        this.bd.getActiveCharacter().modifySpells(1);
    }

    @Override
    public void maintainEffects(final boolean player) {
        for (int x = 0; x < this.bd.getBattlers().length; x++) {
            // Maintain Effects
            final BattleCharacter activeBC = this.bd.getBattlers()[x];
            if (activeBC != null && activeBC.isActive()) {
                final AbstractCreature active = activeBC.getTemplate();
                // Use Effects
                active.useEffects();
                // Display all effect messages
                final String effectMessages = activeBC.getTemplate()
                        .getAllCurrentEffectMessages();
                final String[] individualEffectMessages = effectMessages
                        .split("\n");
                for (final String message : individualEffectMessages) {
                    if (!message.equals(Effect.getNullMessage())) {
                        this.setStatusMessage(message);
                        try {
                            Thread.sleep(PreferencesManager.getBattleSpeed());
                        } catch (final InterruptedException ie) {
                            // Ignore
                        }
                    }
                }
                // Handle low health for party members
                if (active.isAlive()
                        && active.getTeamID() == AbstractCreature.TEAM_PARTY
                        && active.getCurrentHP() <= active.getMaximumHP() * 3
                                / 10) {
                    SoundManager.playSound(SoundConstants.DANGER);
                }
                // Cull Inactive Effects
                active.cullInactiveEffects();
                // Handle death caused by effects
                if (!active.isAlive()) {
                    if (activeBC.getTeamID() != AbstractCreature.TEAM_PARTY) {
                        // Update victory spoils
                        this.battleExp = activeBC.getTemplate().getExperience();
                    }
                    this.handleDeath(activeBC);
                }
            }
        }
    }

    private void handleDeath(final BattleCharacter activeBC) {
        // Something has died
        SoundManager.playSound(SoundConstants.DEATH);
        final AbstractCreature active = activeBC.getTemplate();
        // Set dead character to inactive
        activeBC.deactivate();
        // Remove effects from dead character
        active.stripAllEffects();
        // Remove character from battle
        this.bd.getBattleDungeon().setCell(new Empty(), activeBC.getX(),
                activeBC.getY(), DungeonConstants.LAYER_OBJECT);
        if (this.bd.getActiveCharacter().getName().equals(activeBC.getName())) {
            // Active character died, end turn
            this.endTurn();
        }
    }

    private void updateAllAIContexts() {
        for (int x = 0; x < this.bd.getBattlers().length; x++) {
            if (this.bd.getBattlers()[x] != null) {
                // Update all AI Contexts
                if (this.bd.getBattlerAIContexts()[x] != null) {
                    this.bd.getBattlerAIContexts()[x]
                            .updateContext(this.bd.getBattleDungeon());
                }
            }
        }
    }

    private void performNewRoundActions() {
        for (int x = 0; x < this.bd.getBattlers().length; x++) {
            if (this.bd.getBattlers()[x] != null) {
                // Perform New Round Actions
                if (this.bd.getBattlerAIContexts()[x] != null
                        && this.bd.getBattlerAIContexts()[x].getCharacter()
                                .getTemplate().hasMapAI()
                        && this.bd.getBattlers()[x].isActive()
                        && this.bd.getBattlers()[x].getTemplate().isAlive()) {
                    this.bd.getBattlerAIContexts()[x].getCharacter()
                            .getTemplate().getMapAI().newRoundHook();
                }
            }
        }
    }

    @Override
    public void resetGUI() {
        // Destroy old GUI
        this.battleGUI.getOutputFrame().dispose();
        // Create new GUI
        this.battleGUI = new MapBattleGUI();
    }

    @Override
    public boolean isWaitingForAI() {
        return !this.battleGUI.areEventHandlersOn();
    }

    private void waitForAI() {
        this.battleGUI.turnEventHandlersOff();
    }

    private void stopWaitingForAI() {
        this.battleGUI.turnEventHandlersOn();
    }

    @Override
    public void displayActiveEffects() {
        // Do nothing
    }

    @Override
    public void displayBattleStats() {
        // Do nothing
    }

    @Override
    public boolean doPlayerActions(final int action) {
        switch (action) {
            case AbstractMapAIRoutine.ACTION_CAST_SPELL:
                this.castSpell();
                break;
            case AbstractMapAIRoutine.ACTION_DRAIN:
                this.drain();
                break;
            case AbstractMapAIRoutine.ACTION_STEAL:
                this.steal();
                break;
            default:
                this.endTurn();
                break;
        }
        return true;
    }

    @Override
    public void doResult() {
        this.stopWaitingForAI();
        if (!this.resultDoneAlready) {
            // Handle Results
            this.resultDoneAlready = true;
            if (this.result == BattleResult.WON) {
                SoundManager.playSound(SoundConstants.VICTORY);
                CommonDialogs.showTitledDialog("The party is victorious!",
                        "Victory!");
            } else if (this.result == BattleResult.PERFECT) {
                SoundManager.playSound(SoundConstants.VICTORY);
                CommonDialogs.showTitledDialog(
                        "The party is victorious, and avoided damage!",
                        "Perfect Victory!");
            } else if (this.result == BattleResult.LOST) {
                CommonDialogs.showTitledDialog("The party has been defeated!",
                        "Defeat!");
            } else if (this.result == BattleResult.ANNIHILATED) {
                CommonDialogs.showTitledDialog(
                        "The party has been defeated without dealing any damage!",
                        "Annihilated!");
            } else if (this.result == BattleResult.DRAW) {
                CommonDialogs.showTitledDialog("The battle was a draw.",
                        "Draw");
            } else if (this.result == BattleResult.FLED) {
                CommonDialogs.showTitledDialog("The party fled!", "Party Fled");
            } else if (this.result == BattleResult.ENEMY_FLED) {
                CommonDialogs.showTitledDialog("The enemy fled!", "Enemy Fled");
            } else {
                CommonDialogs.showTitledDialog(
                        "The battle isn't over, but somehow the game thinks it is.",
                        "Uh-Oh!");
            }
            // Rewards
            final long exp = this.battleExp;
            final int gold = this.getEnemy().getGold();
            BattleRewards.doRewards(this.battleType, this.result, exp, gold);
            // Strip effects
            PartyManager.getParty().getLeader().stripAllEffects();
            // Level Up Check
            PartyManager.getParty().checkPartyLevelUp();
            // Battle Done
            this.battleDone();
        }
    }

    @Override
    public void setResult(final int resultCode) {
        // Do nothing
    }
}
