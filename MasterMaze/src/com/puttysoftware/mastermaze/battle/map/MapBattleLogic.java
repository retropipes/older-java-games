/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.battle.map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.ai.map.AutoMapAI;
import com.puttysoftware.mastermaze.ai.map.MapAIContext;
import com.puttysoftware.mastermaze.ai.map.MapAIRoutine;
import com.puttysoftware.mastermaze.battle.BattlePrestige;
import com.puttysoftware.mastermaze.battle.BattleResults;
import com.puttysoftware.mastermaze.battle.GenericBattle;
import com.puttysoftware.mastermaze.battle.VictorySpoilsDescription;
import com.puttysoftware.mastermaze.battle.damageengines.DamageEngine;
import com.puttysoftware.mastermaze.creatures.Creature;
import com.puttysoftware.mastermaze.creatures.PartyManager;
import com.puttysoftware.mastermaze.creatures.PartyMember;
import com.puttysoftware.mastermaze.creatures.StatConstants;
import com.puttysoftware.mastermaze.creatures.monsters.BaseMonster;
import com.puttysoftware.mastermaze.creatures.monsters.MonsterFactory;
import com.puttysoftware.mastermaze.effects.Effect;
import com.puttysoftware.mastermaze.items.combat.CombatItem;
import com.puttysoftware.mastermaze.items.combat.CombatItemChucker;
import com.puttysoftware.mastermaze.maze.Maze;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.maze.generic.TypeConstants;
import com.puttysoftware.mastermaze.maze.objects.BattleCharacter;
import com.puttysoftware.mastermaze.maze.objects.Empty;
import com.puttysoftware.mastermaze.prefs.PreferencesManager;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;
import com.puttysoftware.mastermaze.spells.Spell;
import com.puttysoftware.mastermaze.spells.SpellCaster;
import com.puttysoftware.randomrange.RandomRange;

public class MapBattleLogic extends GenericBattle {
    // Fields
    private MapBattleDefinitions bd;
    private DamageEngine de;
    private final AutoMapAI auto;
    private int damage;
    private int result;
    private int activeIndex;
    private boolean terminatedEarly;
    private boolean newRound;
    private int[] speedArray;
    private int lastSpeed;
    private boolean[] speedMarkArray;
    private boolean resultDoneAlready;
    private boolean lastAIActionResult;
    private MapBattleAITask ait;
    private VictorySpoilsDescription vsd;
    private final MapBattleGUI battleGUI;
    private static final int ITEM_ACTION_POINTS = 6;
    private static final int STEAL_ACTION_POINTS = 3;
    private static final int DRAIN_ACTION_POINTS = 3;

    // Constructors
    public MapBattleLogic() {
        this.battleGUI = new MapBattleGUI();
        this.auto = new AutoMapAI();
    }

    // Methods
    @Override
    public JFrame getOutputFrame() {
        return this.battleGUI.getOutputFrame();
    }

    @Override
    public void doBattle() {
        final Maze m = Maze.getTemporaryBattleCopy();
        final MapBattle b = new MapBattle();
        this.doBattleInternal(m, b);
    }

    @Override
    public void doBattleByProxy() {
        final BaseMonster m = MonsterFactory.getNewMonsterInstance(true, true,
                true, false);
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        playerCharacter.offsetExperience(m.getExperience());
        playerCharacter.offsetGold(m.getGold());
        MasterMaze.getApplication().getGameManager()
                .addToScore(m.getExperience() + m.getGold());
        // Level Up Check
        if (playerCharacter.checkLevelUp()) {
            playerCharacter.levelUp();
            MasterMaze.getApplication().getGameManager().keepNextMessage();
            MasterMaze.getApplication().showMessage(
                    "You reached level " + playerCharacter.getLevel() + ".");
        }
    }

    private void doBattleInternal(final Maze bMaze, final MapBattle b) {
        // Initialize Battle
        MasterMaze.getApplication().setInBattle();
        this.bd = new MapBattleDefinitions();
        this.bd.setBattleMaze(bMaze);
        this.de = DamageEngine.getInstance();
        this.resultDoneAlready = false;
        this.terminatedEarly = false;
        this.result = BattleResults.IN_PROGRESS;
        // Generate Friends
        final BattleCharacter[] friends = PartyManager.getParty()
                .getBattleCharacters();
        // Generate Enemies
        final BattleCharacter[] enemies = b.getBattlers();
        int hostileCount = 0;
        for (final BattleCharacter enemie : enemies) {
            if (enemie != null) {
                hostileCount++;
                enemie.getTemplate().setTeamID(1);
                enemie.getTemplate().healAndRegenerateFully();
                if (enemie.getTemplate() instanceof BaseMonster) {
                    final BaseMonster mon = (BaseMonster) enemie.getTemplate();
                    mon.loadMonster();
                }
            }
        }
        this.vsd = new VictorySpoilsDescription(hostileCount);
        // Merge and Create AI Contexts
        for (int x = 0; x < friends.length + enemies.length; x++) {
            if (x < friends.length) {
                this.bd.addBattler(friends[x]);
            } else {
                if (enemies[x - friends.length] != null) {
                    this.bd.addBattler(enemies[x - friends.length]);
                }
            }
            if (this.bd.getBattlers()[x] != null) {
                // Create an AI Context
                this.bd.getBattlerAIContexts()[x] = new MapAIContext(
                        this.bd.getBattlers()[x], this.bd.getBattleMaze());
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
        SoundManager.playSound(SoundConstants.SOUND_BATTLE);
        this.showBattle();
        this.updateStatsAndEffects();
        this.redrawBattle();
    }

    private void battleDone() {
        this.stopWaitingForAI();
        if (!this.resultDoneAlready) {
            // Handle Results
            this.resultDoneAlready = true;
            if (this.result == BattleResults.WON) {
                final int gold = this.getGold();
                this.vsd.setGoldWon(gold);
                SoundManager.playSound(SoundConstants.SOUND_VICTORY);
                CommonDialogs.showTitledDialog("The party is victorious!",
                        "Victory!");
                PartyManager.getParty().distributeVictorySpoils(this.vsd);
                MasterMaze.getApplication().getGameManager().addToScore(
                        Math.max(1, (this.vsd.getTotalExp() + gold) / (100
                                * PartyManager.getParty().getActivePCCount())));
            } else if (this.result == BattleResults.LOST) {
                CommonDialogs.showTitledDialog("The party has been defeated!",
                        "Defeat...");
            } else if (this.result == BattleResults.DRAW) {
                CommonDialogs.showTitledDialog("The battle was a draw.",
                        "Draw");
            } else if (this.result == BattleResults.FLED) {
                CommonDialogs.showTitledDialog("The party fled!", "Party Fled");
            } else if (this.result == BattleResults.ENEMY_FLED) {
                CommonDialogs.showTitledDialog("The enemies fled!",
                        "Enemies Fled");
            } else if (this.result == BattleResults.IN_PROGRESS) {
                CommonDialogs.showTitledDialog(
                        "The battle isn't over, but somehow the game thinks it is.",
                        "Uh-Oh!");
            } else {
                CommonDialogs.showTitledDialog(
                        "The result of the battle is unknown!", "Uh-Oh!");
            }
            // Strip Effects
            PartyManager.getParty().stripPartyEffects();
            // Level Up Check
            PartyManager.getParty().checkPartyLevelUp();
            // Leave Battle
            this.hideBattle();
            MasterMaze.getApplication().setInGame();
            // Return to whence we came
            MasterMaze.getApplication().getGameManager().showOutput();
            MasterMaze.getApplication().getGameManager().redrawMaze();
            MasterMaze.getApplication().getGameManager().updateStats();
            // Check for Game Over
            MasterMaze.getApplication().getGameManager().checkGameOver();
        }
    }

    private void clearStatusMessage() {
        this.battleGUI.clearStatusMessage();
    }

    @Override
    public void setStatusMessage(final String msg) {
        this.battleGUI.setStatusMessage(msg);
    }

    private int getResult() {
        int currResult;
        if (this.result != BattleResults.IN_PROGRESS) {
            return this.result;
        }
        if (this.areTeamEnemiesAlive(Creature.TEAM_PARTY)
                && !this.isTeamAlive(Creature.TEAM_PARTY)) {
            currResult = BattleResults.LOST;
        } else if (!this.areTeamEnemiesAlive(Creature.TEAM_PARTY)
                && this.isTeamAlive(Creature.TEAM_PARTY)) {
            currResult = BattleResults.WON;
        } else if (!this.areTeamEnemiesAlive(Creature.TEAM_PARTY)
                && !this.isTeamAlive(Creature.TEAM_PARTY)) {
            currResult = BattleResults.DRAW;
        } else if (this.isTeamAlive(Creature.TEAM_PARTY)
                && !this.isTeamGone(Creature.TEAM_PARTY)
                && this.areTeamEnemiesDeadOrGone(Creature.TEAM_PARTY)) {
            currResult = BattleResults.WON;
        } else if (!this.isTeamAlive(Creature.TEAM_PARTY)
                && !this.isTeamGone(Creature.TEAM_PARTY)
                && !this.areTeamEnemiesDeadOrGone(Creature.TEAM_PARTY)) {
            currResult = BattleResults.LOST;
        } else if (this.areTeamEnemiesGone(Creature.TEAM_PARTY)) {
            currResult = BattleResults.ENEMY_FLED;
        } else if (this.isTeamGone(Creature.TEAM_PARTY)) {
            currResult = BattleResults.FLED;
        } else {
            currResult = BattleResults.IN_PROGRESS;
        }
        return currResult;
    }

    @Override
    public boolean getTerminatedEarly() {
        return this.terminatedEarly;
    }

    @Override
    public void executeNextAIAction() {
        int action;
        try {
            action = this.bd.getActiveCharacter().getTemplate().getMapAI()
                    .getNextAction(
                            this.bd.getBattlerAIContexts()[this.activeIndex]);
            switch (action) {
                case MapAIRoutine.ACTION_MOVE:
                    final int x = this.bd.getActiveCharacter().getTemplate()
                            .getMapAI().getMoveX();
                    final int y = this.bd.getActiveCharacter().getTemplate()
                            .getMapAI().getMoveY();
                    this.lastAIActionResult = this.updatePosition(x, y);
                    this.bd.getActiveCharacter().getTemplate().getMapAI()
                            .setLastResult(this.lastAIActionResult);
                    break;
                case MapAIRoutine.ACTION_CAST_SPELL:
                    this.lastAIActionResult = this.castSpell();
                    this.bd.getActiveCharacter().getTemplate().getMapAI()
                            .setLastResult(this.lastAIActionResult);
                    break;
                case MapAIRoutine.ACTION_DRAIN:
                    this.lastAIActionResult = this.drain();
                    this.bd.getActiveCharacter().getTemplate().getMapAI()
                            .setLastResult(this.lastAIActionResult);
                    break;
                case MapAIRoutine.ACTION_STEAL:
                    this.lastAIActionResult = this.steal();
                    this.bd.getActiveCharacter().getTemplate().getMapAI()
                            .setLastResult(this.lastAIActionResult);
                    break;
                default:
                    this.lastAIActionResult = true;
                    this.stopWaitingForAI();
                    this.endTurn();
                    break;
            }
            final int currResult = this.getResult();
            if (currResult != BattleResults.IN_PROGRESS) {
                this.terminatedEarly = true;
            }
        } catch (final NullPointerException npe) {
            // Battle has ended
            return;
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
            case MapAIRoutine.ACTION_MOVE:
                final int x = this.auto.getMoveX();
                final int y = this.auto.getMoveY();
                this.updatePositionInternal(x, y, false, acting);
                break;
            default:
                break;
        }
        final int currResult = this.getResult();
        if (currResult != BattleResults.IN_PROGRESS) {
            this.terminatedEarly = true;
        }
    }

    private void displayRoundResults(final Creature enemy,
            final Creature active) {
        // Display round results
        final String activeName = active.getName();
        final String enemyName = enemy.getName();
        final String damageString = Integer.toString(this.damage);
        String displayDamageString = " ";
        if (this.damage == 0) {
            if (this.de.weaponMissed()) {
                displayDamageString = activeName + " tries to hit " + enemyName
                        + ", but MISSES!";
                SoundManager.playSound(SoundConstants.SOUND_MISSED);
            } else if (this.de.enemyDodged()) {
                displayDamageString = activeName + " tries to hit " + enemyName
                        + ", but " + enemyName + " AVOIDS the attack!";
                SoundManager.playSound(SoundConstants.SOUND_MISSED);
            } else {
                displayDamageString = activeName + " tries to hit " + enemyName
                        + ", but the attack is BLOCKED!";
                SoundManager.playSound(SoundConstants.SOUND_MISSED);
            }
        } else {
            String displayDamagePrefix = "";
            if (this.de.weaponFumble()) {
                SoundManager.playSound(SoundConstants.SOUND_FUMBLE);
                displayDamageString = "FUMBLE! " + activeName
                        + " drops their weapon on themselves, doing "
                        + damageString + " damage!";
            } else {
                if (this.de.weaponCrit() && this.de.weaponPierce()) {
                    displayDamagePrefix = "PIERCING CRITICAL HIT! ";
                    SoundManager.playSound(SoundConstants.SOUND_COUNTER);
                    SoundManager.playSound(SoundConstants.SOUND_CRITICAL);
                } else if (this.de.weaponCrit()) {
                    displayDamagePrefix = "CRITICAL HIT! ";
                    SoundManager.playSound(SoundConstants.SOUND_CRITICAL);
                } else if (this.de.weaponPierce()) {
                    displayDamagePrefix = "PIERCING HIT! ";
                    SoundManager.playSound(SoundConstants.SOUND_COUNTER);
                }
                displayDamageString = displayDamagePrefix + activeName
                        + " hits " + enemyName + " for " + damageString
                        + " damage!";
                SoundManager.playSound(SoundConstants.SOUND_HIT);
            }
        }
        this.setStatusMessage(displayDamageString);
    }

    private void computeDamage(final Creature enemy, final Creature acting) {
        // Compute Damage
        this.damage = 0;
        final int actual = this.de.computeDamage(enemy, acting);
        // Update Prestige
        if (actual != 0) {
            BattlePrestige.dealtDamage(acting, actual);
            BattlePrestige.tookDamage(enemy, actual);
            BattlePrestige.hitEnemy(acting);
            BattlePrestige.hitByEnemy(enemy);
        } else {
            BattlePrestige.missedEnemy(acting);
            BattlePrestige.dodgedAttack(enemy);
        }
        // Hit or Missed
        this.damage = actual;
        if (this.de.weaponFumble()) {
            acting.doDamage(this.damage);
        } else {
            enemy.doDamage(this.damage);
        }
        this.displayRoundResults(enemy, acting);
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
                this.speedArray[x] = this.bd.getBattlers()[x].getTemplate()
                        .getEffectedSpeed();
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
                this.bd.getBattleMaze().getRows() - 1);
        final RandomRange randY = new RandomRange(0,
                this.bd.getBattleMaze().getColumns() - 1);
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
                    MazeObject obj = this.bd.getBattleMaze().getBattleCell(rx,
                            ry);
                    while (obj.isSolidInBattle()) {
                        rx = randX.generate();
                        ry = randY.generate();
                        obj = this.bd.getBattleMaze().getBattleCell(rx, ry);
                    }
                    this.bd.getBattlers()[x].setX(rx);
                    this.bd.getBattlers()[x].setY(ry);
                    this.bd.getBattleMaze()
                            .setBattleCell(this.bd.getBattlers()[x], rx, ry);
                }
            }
        }
    }

    private boolean setNextActive(final boolean isNewRound) {
        this.terminatedEarly = false;
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
            if (this.bd.getActiveCharacter().getTemplate().hasAI()
                    && !MasterMaze.inDebugMode()) {
                // Run AI
                this.waitForAI();
                this.ait = new MapBattleAITask(this);
                this.ait.start();
            } else {
                // No AI
                SoundManager.playSound(SoundConstants.SOUND_PLAYER_UP);
            }
            return false;
        } else {
            // Reset Speed Array
            this.resetSpeedArray();
            // Reset Action Counters
            this.bd.roundResetBattlers();
            // Maintain effects
            this.maintainEffects();
            this.updateStatsAndEffects();
            // Perform new round actions
            this.performNewRoundActions();
            // Play new round sound
            SoundManager.playSound(SoundConstants.SOUND_NEXT_ROUND);
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

    private int getGold() {
        int res = 0;
        for (int x = 0; x < this.bd.getBattlers().length; x++) {
            if (this.bd.getBattlers()[x] != null) {
                if (this.bd.getBattlers()[x].getTeamID() != 0) {
                    res += this.bd.getBattlers()[x].getTemplate().getGold();
                }
            }
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
        return this.updatePositionInternal(x, y, true,
                this.bd.getActiveCharacter());
    }

    @Override
    public void fireArrow(final int x, final int y) {
        if (this.bd.getActiveCharacter().getCurrentAP() > 0) {
            // Has actions left
            this.bd.getActiveCharacter().modifyAP(1);
            this.battleGUI.turnEventHandlersOff();
            final MapBattleArrowTask at = new MapBattleArrowTask(x, y, this.bd
                    .getActiveCharacter().getTemplate().getFaith().getFaithID(),
                    this.bd);
            at.start();
        } else {
            // Deny arrow - out of actions
            if (!this.bd.getActiveCharacter().getTemplate().hasAI()
                    || MasterMaze.inDebugMode()) {
                this.setStatusMessage("Out of actions!");
            }
        }
    }

    @Override
    public void arrowDone(final BattleCharacter hit) {
        this.battleGUI.turnEventHandlersOn();
        // Handle death
        if (hit != null && !hit.getTemplate().isAlive()) {
            if (hit.getTeamID() != Creature.TEAM_PARTY) {
                // Update victory spoils
                final int partySize = PartyManager.getParty()
                        .getActivePCCount();
                this.vsd.setExpPerMonster(
                        this.bd.findBattler(hit.getName()) - partySize,
                        hit.getTemplate().getExperience());
            }
            // Update Prestige
            BattlePrestige
                    .killedEnemy(this.bd.getActiveCharacter().getTemplate());
            BattlePrestige.killedInBattle(hit.getTemplate());
            // Remove effects from dead character
            hit.getTemplate().stripAllEffects();
            // Set dead character to inactive
            hit.deactivate();
            // Remove character from battle
            this.bd.getBattleMaze().setBattleCell(new Empty(), hit.getX(),
                    hit.getY());
        }
        // Check result
        final int currResult = this.getResult();
        if (currResult != BattleResults.IN_PROGRESS) {
            // Battle Done
            this.result = currResult;
            this.battleDone();
        }
    }

    private boolean updatePositionInternal(final int x, final int y,
            final boolean useAP, final BattleCharacter active) {
        this.updateAllAIContexts();
        int px = active.getX();
        int py = active.getY();
        final Maze m = this.bd.getBattleMaze();
        MazeObject next = null;
        MazeObject nextGround = null;
        MazeObject currGround = null;
        active.saveLocation();
        this.battleGUI.getViewManager().saveViewingWindow();
        try {
            next = m.getBattleCell(px + x, py + y);
            nextGround = m.getBattleGround(px + x, py + y);
            currGround = m.getBattleGround(px, py);
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Ignore
        }
        if (next != null && nextGround != null && currGround != null) {
            if (!next.isSolidInBattle()) {
                if (useAP && this.getActiveActionCounter() >= MazeObject
                        .getBattleAPCost() || !useAP) {
                    // Move
                    MazeObject obj1 = null;
                    MazeObject obj2 = null;
                    MazeObject obj3 = null;
                    MazeObject obj4 = null;
                    MazeObject obj6 = null;
                    MazeObject obj7 = null;
                    MazeObject obj8 = null;
                    MazeObject obj9 = null;
                    try {
                        obj1 = m.getBattleCell(px - 1, py - 1);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        // Ignore
                    }
                    try {
                        obj2 = m.getBattleCell(px, py - 1);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        // Ignore
                    }
                    try {
                        obj3 = m.getBattleCell(px + 1, py - 1);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        // Ignore
                    }
                    try {
                        obj4 = m.getBattleCell(px - 1, py);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        // Ignore
                    }
                    try {
                        obj6 = m.getBattleCell(px + 1, py - 1);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        // Ignore
                    }
                    try {
                        obj7 = m.getBattleCell(px - 1, py + 1);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        // Ignore
                    }
                    try {
                        obj8 = m.getBattleCell(px, py + 1);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        // Ignore
                    }
                    try {
                        obj9 = m.getBattleCell(px + 1, py + 1);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        // Ignore
                    }
                    // Auto-attack check
                    if (obj1 != null) {
                        if (obj1.isOfType(
                                TypeConstants.TYPE_BATTLE_CHARACTER)) {
                            if (!(x == -1 && y == 0 || x == -1 && y == -1
                                    || x == 0 && y == -1)) {
                                final BattleCharacter bc1 = (BattleCharacter) obj1;
                                if (bc1.getTeamID() != active.getTeamID()) {
                                    this.executeAutoAI(bc1);
                                }
                            }
                        }
                    }
                    if (obj2 != null) {
                        if (obj2.isOfType(
                                TypeConstants.TYPE_BATTLE_CHARACTER)) {
                            if (y == 1) {
                                final BattleCharacter bc2 = (BattleCharacter) obj2;
                                if (bc2.getTeamID() != active.getTeamID()) {
                                    this.executeAutoAI(bc2);
                                }
                            }
                        }
                    }
                    if (obj3 != null) {
                        if (obj3.isOfType(
                                TypeConstants.TYPE_BATTLE_CHARACTER)) {
                            if (!(x == 0 && y == -1 || x == 1 && y == -1
                                    || x == 1 && y == 0)) {
                                final BattleCharacter bc3 = (BattleCharacter) obj3;
                                if (bc3.getTeamID() != active.getTeamID()) {
                                    this.executeAutoAI(bc3);
                                }
                            }
                        }
                    }
                    if (obj4 != null) {
                        if (obj4.isOfType(
                                TypeConstants.TYPE_BATTLE_CHARACTER)) {
                            if (x == 1) {
                                final BattleCharacter bc4 = (BattleCharacter) obj4;
                                if (bc4.getTeamID() != active.getTeamID()) {
                                    this.executeAutoAI(bc4);
                                }
                            }
                        }
                    }
                    if (obj6 != null) {
                        if (obj6.isOfType(
                                TypeConstants.TYPE_BATTLE_CHARACTER)) {
                            if (x == -1) {
                                final BattleCharacter bc6 = (BattleCharacter) obj6;
                                if (bc6.getTeamID() != active.getTeamID()) {
                                    this.executeAutoAI(bc6);
                                }
                            }
                        }
                    }
                    if (obj7 != null) {
                        if (obj7.isOfType(
                                TypeConstants.TYPE_BATTLE_CHARACTER)) {
                            if (!(x == -1 && y == 0 || x == -1 && y == 1
                                    || x == 0 && y == 1)) {
                                final BattleCharacter bc7 = (BattleCharacter) obj7;
                                if (bc7.getTeamID() != active.getTeamID()) {
                                    this.executeAutoAI(bc7);
                                }
                            }
                        }
                    }
                    if (obj8 != null) {
                        if (obj8.isOfType(
                                TypeConstants.TYPE_BATTLE_CHARACTER)) {
                            if (y == -1) {
                                final BattleCharacter bc8 = (BattleCharacter) obj8;
                                if (bc8.getTeamID() != active.getTeamID()) {
                                    this.executeAutoAI(bc8);
                                }
                            }
                        }
                    }
                    if (obj9 != null) {
                        if (obj9.isOfType(
                                TypeConstants.TYPE_BATTLE_CHARACTER)) {
                            if (!(x == 0 && y == 1 || x == 1 && y == 1
                                    || x == 1 && y == 0)) {
                                final BattleCharacter bc9 = (BattleCharacter) obj9;
                                if (bc9.getTeamID() != active.getTeamID()) {
                                    this.executeAutoAI(bc9);
                                }
                            }
                        }
                    }
                    m.setBattleCell(active.getSavedObject(), px, py);
                    active.offsetX(x);
                    active.offsetY(y);
                    px += x;
                    py += y;
                    this.battleGUI.getViewManager()
                            .offsetViewingWindowLocationX(y);
                    this.battleGUI.getViewManager()
                            .offsetViewingWindowLocationY(x);
                    active.setSavedObject(m.getBattleCell(px, py));
                    m.setBattleCell(active, px, py);
                    this.decrementActiveActionCounterBy(
                            MazeObject.getBattleAPCost());
                    SoundManager.playSound(SoundConstants.SOUND_WALK);
                    // If the random battle environment is enabled...
                    if (PreferencesManager.getRandomBattleEnvironment()) {
                        // Run any script attached to ground
                        nextGround.postMoveBattleAction(active);
                    }
                } else {
                    // Deny move - out of actions
                    if (!this.bd.getActiveCharacter().getTemplate().hasAI()
                            || MasterMaze.inDebugMode()) {
                        this.setStatusMessage("Out of moves!");
                    }
                    return false;
                }
            } else {
                if (next.isOfType(TypeConstants.TYPE_BATTLE_CHARACTER)) {
                    if (useAP && this.getActiveAttackCounter() > 0 || !useAP) {
                        // Attack
                        final BattleCharacter bc = (BattleCharacter) next;
                        if (bc.getTeamID() == active.getTeamID()) {
                            // Attack Friend?
                            if (!active.getTemplate().hasAI()
                                    || MasterMaze.inDebugMode()) {
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
                        final Creature enemy = bc.getTemplate();
                        if (useAP) {
                            this.decrementActiveAttackCounter();
                        }
                        // If the random battle environment is enabled...
                        if (PreferencesManager.getRandomBattleEnvironment()) {
                            // Run any script attached to ground
                            currGround.postMoveBattleAction(active);
                        }
                        // Do damage
                        this.computeDamage(enemy, active.getTemplate());
                        // Handle low health for party members
                        if (enemy.isAlive()
                                && enemy.getTeamID() == Creature.TEAM_PARTY
                                && enemy.getCurrentHP() <= enemy.getMaximumHP()
                                        * 3 / 10) {
                            SoundManager
                                    .playSound(SoundConstants.SOUND_LOW_HEALTH);
                        }
                        // Handle enemy death
                        if (!enemy.isAlive()) {
                            if (enemy.getTeamID() != Creature.TEAM_PARTY) {
                                // Update victory spoils
                                final int partySize = PartyManager.getParty()
                                        .getActivePCCount();
                                this.vsd.setExpPerMonster(
                                        this.bd.findBattler(enemy.getName())
                                                - partySize,
                                        enemy.getExperience());
                            }
                            // Update Prestige
                            BattlePrestige.killedEnemy(active.getTemplate());
                            BattlePrestige.killedInBattle(enemy);
                            // Remove effects from dead character
                            bc.getTemplate().stripAllEffects();
                            // Set dead character to inactive
                            bc.deactivate();
                            // Remove character from battle
                            this.bd.getBattleMaze().setBattleCell(new Empty(),
                                    bc.getX(), bc.getY());
                        }
                        // Handle self death
                        if (!active.getTemplate().isAlive()) {
                            // Update Prestige
                            BattlePrestige.killedInBattle(active.getTemplate());
                            // Remove effects from dead character
                            active.getTemplate().stripAllEffects();
                            // Set dead character to inactive
                            active.deactivate();
                            // Remove character from battle
                            this.bd.getBattleMaze().setBattleCell(new Empty(),
                                    active.getX(), active.getY());
                            // End turn
                            this.endTurn();
                        }
                    } else {
                        // Deny attack - out of actions
                        if (!this.bd.getActiveCharacter().getTemplate().hasAI()
                                || MasterMaze.inDebugMode()) {
                            this.setStatusMessage("Out of attacks!");
                        }
                        return false;
                    }
                } else {
                    // Move Failed
                    if (!active.getTemplate().hasAI()
                            || MasterMaze.inDebugMode()) {
                        this.setStatusMessage("Can't go that way");
                    }
                    return false;
                }
            }
        } else {
            // Confirm Flee
            if (!active.getTemplate().hasAI() || MasterMaze.inDebugMode()) {
                SoundManager.playSound(SoundConstants.SOUND_SPECIAL);
                final int confirm = CommonDialogs
                        .showConfirmDialog("Embrace Cowardice?", "Battle");
                if (confirm != JOptionPane.YES_OPTION) {
                    this.battleGUI.getViewManager().restoreViewingWindow();
                    active.restoreLocation();
                    return false;
                }
            }
            // Flee
            this.battleGUI.getViewManager().restoreViewingWindow();
            active.restoreLocation();
            // Update Prestige
            BattlePrestige.ranAway(active.getTemplate());
            // Set fled character to inactive
            active.deactivate();
            // Remove character from battle
            m.setBattleCell(new Empty(), active.getX(), active.getY());
            // End Turn
            this.endTurn();
            this.updateStatsAndEffects();
            final int currResult = this.getResult();
            if (currResult != BattleResults.IN_PROGRESS) {
                // Battle Done
                this.result = currResult;
                this.battleDone();
            }
            this.battleGUI.getViewManager().setViewingWindowCenterX(py);
            this.battleGUI.getViewManager().setViewingWindowCenterY(px);
            this.redrawBattle();
            return true;
        }
        this.updateStatsAndEffects();
        final int currResult = this.getResult();
        if (currResult != BattleResults.IN_PROGRESS) {
            // Battle Done
            this.result = currResult;
            this.battleDone();
        }
        this.battleGUI.getViewManager().setViewingWindowCenterX(py);
        this.battleGUI.getViewManager().setViewingWindowCenterY(px);
        this.redrawBattle();
        return true;
    }

    @Override
    public BaseMonster getEnemy() {
        return (BaseMonster) this.getEnemyBC().getTemplate();
    }

    private BattleCharacter getEnemyBC() {
        final int px = this.bd.getActiveCharacter().getX();
        final int py = this.bd.getActiveCharacter().getY();
        final Maze m = this.bd.getBattleMaze();
        MazeObject next = null;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                try {
                    next = m.getBattleCell(px + x, py + y);
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Ignore
                }
                if (next != null) {
                    if (next.isSolidInBattle()) {
                        if (next.isOfType(
                                TypeConstants.TYPE_BATTLE_CHARACTER)) {
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
            if (!this.bd.getActiveCharacter().getTemplate().hasAI()
                    || MasterMaze.inDebugMode()) {
                // Active character has no AI, or AI is turned off
                final boolean success = SpellCaster.selectAndCastSpell(
                        this.bd.getActiveCharacter().getTemplate(),
                        this.bd.getActiveCharacter().getTeamID(), true,
                        this.bd);
                if (success) {
                    this.decrementActiveSpellCounter();
                    // Update Prestige
                    BattlePrestige.castSpell(this.bd.getSelfTarget());
                }
                final int currResult = this.getResult();
                if (currResult != BattleResults.IN_PROGRESS) {
                    // Battle Done
                    this.result = currResult;
                    this.battleDone();
                }
                return success;
            } else {
                // Active character has AI, and AI is turned on
                final Spell sp = this.bd.getActiveCharacter().getTemplate()
                        .getMapAI().getSpellToCast();
                final boolean success = SpellCaster.castSpell(sp,
                        this.bd.getActiveCharacter().getTemplate(),
                        this.bd.getActiveCharacter().getTeamID(), true,
                        this.bd);
                if (success) {
                    this.decrementActiveSpellCounter();
                    // Update Prestige
                    BattlePrestige.castSpell(this.bd.getSelfTarget());
                }
                final int currResult = this.getResult();
                if (currResult != BattleResults.IN_PROGRESS) {
                    // Battle Done
                    this.result = currResult;
                    this.battleDone();
                }
                return success;
            }
        } else {
            // Deny cast - out of actions
            if (!this.bd.getActiveCharacter().getTemplate().hasAI()
                    || MasterMaze.inDebugMode()) {
                this.setStatusMessage("Out of actions!");
            }
            return false;
        }
    }

    @Override
    public boolean useItem() {
        // Check Action Counter
        if (this.getActiveActionCounter() > 0) {
            if (!this.bd.getActiveCharacter().getTemplate().hasAI()
                    || MasterMaze.inDebugMode()) {
                // Active character has no AI, or AI is turned off
                final boolean success = CombatItemChucker.selectAndUseItem(
                        this.bd.getActiveCharacter().getTemplate(),
                        this.bd.getActiveCharacter().getTeamID(), true,
                        this.bd);
                if (success) {
                    this.bd.getActiveCharacter()
                            .modifyAP(MapBattleLogic.ITEM_ACTION_POINTS);
                }
                final int currResult = this.getResult();
                if (currResult != BattleResults.IN_PROGRESS) {
                    // Battle Done
                    this.result = currResult;
                    this.battleDone();
                }
                return success;
            } else {
                // Active character has AI, and AI is turned on
                final CombatItem cui = this.bd.getActiveCharacter()
                        .getTemplate().getMapAI().getItemToUse();
                final boolean success = CombatItemChucker.useItem(cui,
                        this.bd.getActiveCharacter().getTemplate(),
                        this.bd.getActiveCharacter().getTeamID(), true,
                        this.bd);
                if (success) {
                    this.bd.getActiveCharacter()
                            .modifyAP(MapBattleLogic.ITEM_ACTION_POINTS);
                }
                final int currResult = this.getResult();
                if (currResult != BattleResults.IN_PROGRESS) {
                    // Battle Done
                    this.result = currResult;
                    this.battleDone();
                }
                return success;
            }
        } else {
            // Deny use - out of actions
            if (!this.bd.getActiveCharacter().getTemplate().hasAI()
                    || MasterMaze.inDebugMode()) {
                this.setStatusMessage("Out of actions!");
            }
            return false;
        }
    }

    @Override
    public boolean steal() {
        // Check Action Counter
        if (this.getActiveActionCounter() > 0) {
            Creature activeEnemy = null;
            try {
                activeEnemy = this.getEnemyBC().getTemplate();
            } catch (final NullPointerException npe) {
                // Ignore
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
            if (!this.bd.getActiveCharacter().getTemplate().hasAI()
                    || MasterMaze.inDebugMode()) {
                this.setStatusMessage("Out of actions!");
            }
            return false;
        }
    }

    @Override
    public boolean drain() {
        // Check Action Counter
        if (this.getActiveActionCounter() > 0) {
            Creature activeEnemy = null;
            try {
                activeEnemy = this.getEnemyBC().getTemplate();
            } catch (final NullPointerException npe) {
                // Ignore
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
            if (!this.bd.getActiveCharacter().getTemplate().hasAI()
                    || MasterMaze.inDebugMode()) {
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
            if (this.result != BattleResults.IN_PROGRESS) {
                this.battleDone();
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

    @Override
    public void redrawOneBattleSquare(final int x, final int y,
            final MazeObject obj3) {
        this.battleGUI.redrawOneBattleSquare(this.bd, x, y, obj3);
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

    private void maintainEffects() {
        for (int x = 0; x < this.bd.getBattlers().length; x++) {
            // Maintain Effects
            final Creature active = this.bd.getBattlers()[x].getTemplate();
            if (this.bd.getBattlers()[x] != null
                    && this.bd.getBattlers()[x].isActive()) {
                // Use Effects
                active.useEffects();
                // Display all effect messages
                final String effectMessages = this.bd.getBattlers()[x]
                        .getTemplate().getAllCurrentEffectMessages();
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
                        && active.getTeamID() == Creature.TEAM_PARTY
                        && active.getCurrentHP() <= active.getMaximumHP() * 3
                                / 10) {
                    SoundManager.playSound(SoundConstants.SOUND_LOW_HEALTH);
                }
                // Cull Inactive Effects
                active.cullInactiveEffects();
                // Handle death caused by effects
                if (!active.isAlive()) {
                    if (this.bd.getBattlers()[x]
                            .getTeamID() != Creature.TEAM_PARTY) {
                        // Update victory spoils
                        final int partySize = PartyManager.getParty()
                                .getActivePCCount();
                        this.vsd.setExpPerMonster(x - partySize,
                                this.bd.getBattlers()[x].getTemplate()
                                        .getExperience());
                    }
                    // Set dead character to inactive
                    this.bd.getBattlers()[x].deactivate();
                    // Remove effects from dead character
                    active.stripAllEffects();
                    // Remove character from battle
                    this.bd.getBattleMaze().setBattleCell(new Empty(),
                            this.bd.getBattlers()[x].getX(),
                            this.bd.getBattlers()[x].getY());
                    if (this.bd.getActiveCharacter().getName()
                            .equals(this.bd.getBattlers()[x].getName())) {
                        // Active character died, end turn
                        this.endTurn();
                    }
                }
            }
        }
    }

    private void updateAllAIContexts() {
        for (int x = 0; x < this.bd.getBattlers().length; x++) {
            if (this.bd.getBattlers()[x] != null) {
                // Update all AI Contexts
                if (this.bd.getBattlerAIContexts()[x] != null) {
                    this.bd.getBattlerAIContexts()[x]
                            .updateContext(this.bd.getBattleMaze());
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
                                .getTemplate().hasAI()
                        && !MasterMaze.inDebugMode()
                        && this.bd.getBattlers()[x].isActive()
                        && this.bd.getBattlers()[x].getTemplate().isAlive()) {
                    this.bd.getBattlerAIContexts()[x].getCharacter()
                            .getTemplate().getMapAI().newRoundHook();
                }
            }
        }
    }

    @Override
    public boolean isWaitingForAI() {
        return !this.battleGUI.areEventHandlersOn();
    }

    private void waitForAI() {
        this.battleGUI.turnEventHandlersOff();
        MasterMaze.getApplication().getMenuManager().disableBattleMenus();
    }

    private void stopWaitingForAI() {
        if (this.ait != null && this.ait.isAlive()) {
            this.ait.turnOver();
        }
        this.battleGUI.turnEventHandlersOn();
        MasterMaze.getApplication().getMenuManager().enableBattleMenus();
    }
}
