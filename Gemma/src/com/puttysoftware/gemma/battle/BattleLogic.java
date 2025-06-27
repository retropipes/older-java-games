/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.battle;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.gemma.Gemma;
import com.puttysoftware.gemma.battle.damageengines.DamageEngine;
import com.puttysoftware.gemma.game.scripts.InternalScriptRunner;
import com.puttysoftware.gemma.prefs.PreferencesManager;
import com.puttysoftware.gemma.support.Support;
import com.puttysoftware.gemma.support.ai.AIContext;
import com.puttysoftware.gemma.support.ai.AIRoutine;
import com.puttysoftware.gemma.support.ai.AutoAI;
import com.puttysoftware.gemma.support.battle.Battle;
import com.puttysoftware.gemma.support.battle.BattleDefinitions;
import com.puttysoftware.gemma.support.battle.VictorySpoilsDescription;
import com.puttysoftware.gemma.support.creatures.Creature;
import com.puttysoftware.gemma.support.creatures.PartyManager;
import com.puttysoftware.gemma.support.creatures.StatConstants;
import com.puttysoftware.gemma.support.creatures.monsters.BaseMonster;
import com.puttysoftware.gemma.support.effects.Effect;
import com.puttysoftware.gemma.support.items.combat.CombatItem;
import com.puttysoftware.gemma.support.items.combat.CombatItemChucker;
import com.puttysoftware.gemma.support.map.Map;
import com.puttysoftware.gemma.support.map.generic.MapObject;
import com.puttysoftware.gemma.support.map.generic.TypeConstants;
import com.puttysoftware.gemma.support.map.objects.BattleCharacter;
import com.puttysoftware.gemma.support.map.objects.Empty;
import com.puttysoftware.gemma.support.prefs.LocalPreferencesManager;
import com.puttysoftware.gemma.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.gemma.support.resourcemanagers.SoundManager;
import com.puttysoftware.gemma.support.scripts.internal.InternalScript;
import com.puttysoftware.gemma.support.spells.Spell;
import com.puttysoftware.gemma.support.spells.SpellCaster;
import com.puttysoftware.randomrange.RandomRange;

public class BattleLogic {
    // Fields
    private BattleDefinitions bd;
    private DamageEngine de;
    private final AutoAI auto;
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
    private AITask ait;
    private VictorySpoilsDescription vsd;
    private final BattleGUI battleGUI;
    private static final int DRAIN_ACTION_POINTS = 3;

    // Constructors
    public BattleLogic() {
        this.battleGUI = new BattleGUI();
        this.auto = new AutoAI();
    }

    // Methods
    public JFrame getOutputFrame() {
        return this.battleGUI.getOutputFrame();
    }

    public void doFixedBattle(final Map bMap, final Battle b) {
        this.doBattleInternal(bMap, b);
    }

    private void doBattleInternal(final Map bMap, final Battle b) {
        // Initialize Battle
        Gemma.getApplication().setInBattle();
        this.bd = new BattleDefinitions();
        this.bd.setBattleMap(bMap);
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
        for (int x = 0; x < enemies.length; x++) {
            if (enemies[x] != null) {
                hostileCount++;
                enemies[x].getTemplate().setTeamID(1);
                enemies[x].getTemplate().healAndRegenerateFully();
                if (enemies[x].getTemplate() instanceof BaseMonster) {
                    final BaseMonster mon = (BaseMonster) enemies[x]
                            .getTemplate();
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
                this.bd.getBattlerAIContexts()[x] = new AIContext(
                        this.bd.getBattlers()[x], this.bd.getBattleMap());
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
        SoundManager.playSound(GameSoundConstants.SOUND_BATTLE);
        this.showBattle(b.isBossBattle());
        this.updateStatsAndEffects();
        this.redrawBattle();
    }

    private void battleDone() {
        this.stopWaitingForAI();
        if (!this.resultDoneAlready) {
            // Handle Results
            this.bd.setActiveCharacter(null);
            this.resultDoneAlready = true;
            if (this.result == BattleResults.WON) {
                if (this.bd.findBattler("The Final Boss") != -1) {
                    // Boss battle
                    CommonDialogs.showTitledDialog("The Final Boss has fallen!",
                            "Final Victory!");
                    Gemma.getApplication().getGameManager().addToScore(10000);
                    // Leave Battle
                    this.hideBattle();
                    Gemma.getApplication().setInGame();
                    // Return to whence we came
                    Gemma.getApplication().getGameManager().showOutput();
                    Gemma.getApplication().getGameManager().victory();
                    return;
                } else {
                    // Normal battle
                    final int gold = this.getGold();
                    this.vsd.setGoldWon(gold);
                    SoundManager.playSound(GameSoundConstants.SOUND_VICTORY);
                    CommonDialogs.showTitledDialog("The party is victorious!",
                            "Victory!");
                    final Creature enemy = this.bd.getBattlers()[this.bd
                            .findBattler(this.bd.getNameOfPartyEnemy())]
                            .getTemplate();
                    PartyManager.getParty().distributeVictorySpoils(this.vsd,
                            enemy.getLevel());
                    Gemma.getApplication().getGameManager()
                            .addToScore(Math.max(1,
                                    (this.vsd.getTotalExp() + gold)
                                            / (100 * PartyManager.getParty()
                                                    .getActivePCCount())));
                }
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
            final ArrayList<InternalScript> levelUpScripts = PartyManager
                    .getParty().checkPartyLevelUp();
            for (final InternalScript is : levelUpScripts) {
                InternalScriptRunner.runScript(is);
            }
            // Leave Battle
            this.hideBattle();
            Gemma.getApplication().setInGame();
            // Return to whence we came
            Gemma.getApplication().getGameManager().showOutput();
            Gemma.getApplication().getGameManager().redrawMap();
            Gemma.getApplication().getGameManager().updateStats();
            // Check for Game Over
            Gemma.getApplication().getGameManager().checkGameOver();
        }
    }

    private void clearStatusMessage() {
        this.battleGUI.clearStatusMessage();
    }

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

    boolean getTerminatedEarly() {
        return this.terminatedEarly;
    }

    void executeNextAIAction() {
        final BattleCharacter active = this.bd.getActiveCharacter();
        final AIContext aicontext = this.bd
                .getBattlerAIContexts()[this.activeIndex];
        final AIRoutine ai = this.bd.getActiveCharacter().getTemplate().getAI();
        if (active != null && aicontext != null && ai != null) {
            final int action = active.getTemplate().getAI()
                    .getNextAction(aicontext);
            switch (action) {
                case AIRoutine.ACTION_MOVE:
                    final int x = this.bd.getActiveCharacter().getTemplate().getAI()
                            .getMoveX();
                    final int y = this.bd.getActiveCharacter().getTemplate().getAI()
                            .getMoveY();
                    this.lastAIActionResult = this.updatePosition(x, y);
                    ai.setLastResult(this.lastAIActionResult);
                    break;
                case AIRoutine.ACTION_CAST_SPELL:
                    this.lastAIActionResult = this.castSpell();
                    ai.setLastResult(this.lastAIActionResult);
                    break;
                case AIRoutine.ACTION_DRAIN:
                    this.lastAIActionResult = this.drain();
                    ai.setLastResult(this.lastAIActionResult);
                    break;
                case AIRoutine.ACTION_STEAL:
                    this.lastAIActionResult = this.steal();
                    ai.setLastResult(this.lastAIActionResult);
                    break;
                default:
                    this.lastAIActionResult = true;
                    this.endTurn();
                    this.stopWaitingForAI();
                    break;
            }
            final int currResult = this.getResult();
            if (currResult != BattleResults.IN_PROGRESS) {
                this.terminatedEarly = true;
            }
        }
    }

    boolean getLastAIActionResult() {
        return this.lastAIActionResult;
    }

    private void executeAutoAI(final BattleCharacter acting) {
        final int index = this.bd.findBattler(acting.getName());
        final int action = this.auto
                .getNextAction(this.bd.getBattlerAIContexts()[index]);
        switch (action) {
            case AIRoutine.ACTION_MOVE:
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
        String displayDamageString;
        if (this.damage == 0) {
            if (this.de.weaponMissed()) {
                displayDamageString = activeName + " tries to hit " + enemyName
                        + ", but MISSES!";
                SoundManager.playSound(GameSoundConstants.SOUND_MISSED);
            } else if (this.de.enemyDodged()) {
                displayDamageString = activeName + " tries to hit " + enemyName
                        + ", but " + enemyName + " AVOIDS the attack!";
                SoundManager.playSound(GameSoundConstants.SOUND_MISSED);
            } else {
                displayDamageString = activeName + " tries to hit " + enemyName
                        + ", but the attack is BLOCKED!";
                SoundManager.playSound(GameSoundConstants.SOUND_MISSED);
            }
        } else {
            String displayDamagePrefix = "";
            if (this.de.weaponCrit() && this.de.weaponPierce()) {
                displayDamagePrefix = "PIERCING CRITICAL HIT! ";
                SoundManager.playSound(GameSoundConstants.SOUND_COUNTER);
                SoundManager.playSound(GameSoundConstants.SOUND_CRITICAL);
            } else if (this.de.weaponCrit()) {
                displayDamagePrefix = "CRITICAL HIT! ";
                SoundManager.playSound(GameSoundConstants.SOUND_CRITICAL);
            } else if (this.de.weaponPierce()) {
                displayDamagePrefix = "PIERCING HIT! ";
                SoundManager.playSound(GameSoundConstants.SOUND_COUNTER);
            }
            displayDamageString = displayDamagePrefix + activeName + " hits "
                    + enemyName + " for " + damageString + " damage!";
            SoundManager.playSound(GameSoundConstants.SOUND_HIT);
        }
        this.setStatusMessage(displayDamageString);
    }

    private void computeDamage(final Creature enemy, final Creature acting) {
        // Compute Damage
        this.damage = 0;
        final int actual = this.de.computeDamage(enemy, acting);
        // Update Prestige
        if (actual != 0) {
            BattleDefinitions.dealtDamage(acting, actual);
            BattleDefinitions.tookDamage(enemy, actual);
            BattleDefinitions.hitEnemy(acting);
            BattleDefinitions.hitByEnemy(enemy);
        } else {
            BattleDefinitions.missedEnemy(acting);
            BattleDefinitions.dodgedAttack(enemy);
        }
        // Hit or Missed
        this.damage = actual;
        enemy.doDamage(this.damage);
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
                this.bd.getBattleMap().getRows() - 1);
        final RandomRange randY = new RandomRange(0,
                this.bd.getBattleMap().getColumns() - 1);
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
                    MapObject obj = this.bd.getBattleMap().getBattleCell(rx,
                            ry);
                    while (obj.isSolidInBattle()) {
                        rx = randX.generate();
                        ry = randY.generate();
                        obj = this.bd.getBattleMap().getBattleCell(rx, ry);
                    }
                    this.bd.getBattlers()[x].setX(rx);
                    this.bd.getBattlers()[x].setY(ry);
                    this.bd.getBattleMap()
                            .setBattleCell(this.bd.getBattlers()[x], rx, ry);
                }
            }
        }
    }

    private boolean setNextActive(final boolean isNewRound) {
        this.terminatedEarly = false;
        int res;
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
                    && !Support.inDebugMode()) {
                // Run AI
                this.waitForAI();
                this.ait = new AITask(this);
                this.ait.start();
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

    boolean updatePosition(final int x, final int y) {
        return this.updatePositionInternal(x, y, true,
                this.bd.getActiveCharacter());
    }

    void fireArrow(final int x, final int y) {
        if (this.bd.getActiveCharacter().getCurrentAP() > 0) {
            // Has actions left
            this.bd.getActiveCharacter().modifyAP(1);
            this.battleGUI.turnEventHandlersOff();
            final ArrowTask at = new ArrowTask(x, y, this.bd
                    .getActiveCharacter().getTemplate().getFaith().getFaithID(),
                    this.bd);
            at.start();
        } else {
            // Deny arrow - out of actions
            if (!this.bd.getActiveCharacter().getTemplate().hasAI()
                    || Support.inDebugMode()) {
                this.setStatusMessage("Out of actions!");
            }
        }
    }

    void arrowDone(final BattleCharacter hit) {
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
            BattleDefinitions
                    .killedEnemy(this.bd.getActiveCharacter().getTemplate());
            BattleDefinitions.killedInBattle(hit.getTemplate());
            // Play death sound
            SoundManager.playSound(GameSoundConstants.SOUND_DEATH);
            // Remove effects from dead character
            hit.getTemplate().stripAllEffects();
            // Set dead character to inactive
            hit.deactivate();
            // Remove character from battle
            this.bd.getBattleMap().setBattleCell(new Empty(), hit.getX(),
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
        final Map m = this.bd.getBattleMap();
        MapObject next = null;
        MapObject nextGround = null;
        MapObject currGround = null;
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
                if (useAP && this.getActiveActionCounter() >= MapObject
                        .getBattleAPCost() || !useAP) {
                    // Move
                    MapObject obj1 = null;
                    MapObject obj2 = null;
                    MapObject obj3 = null;
                    MapObject obj4 = null;
                    MapObject obj6 = null;
                    MapObject obj7 = null;
                    MapObject obj8 = null;
                    MapObject obj9 = null;
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
                            MapObject.getBattleAPCost());
                    SoundManager.playSound(MapObject.getBattleMoveSoundID());
                    // If the random battle environment is enabled...
                    if (LocalPreferencesManager.getRandomBattleEnvironment()) {
                        // Run any script attached to ground
                        InternalScriptRunner.runScript(
                                nextGround.getBattlePostMoveScript(active));
                    }
                } else {
                    // Deny move - out of actions
                    if (!this.bd.getActiveCharacter().getTemplate().hasAI()
                            || Support.inDebugMode()) {
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
                                    || Support.inDebugMode()) {
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
                        if (LocalPreferencesManager
                                .getRandomBattleEnvironment()) {
                            // Run any script attached to ground
                            InternalScriptRunner.runScript(
                                    currGround.getBattlePostMoveScript(active));
                        }
                        // Do damage
                        this.computeDamage(enemy, active.getTemplate());
                        // Handle low health for party members
                        if (enemy.isAlive()
                                && enemy.getTeamID() == Creature.TEAM_PARTY
                                && enemy.getCurrentHP() <= enemy.getMaximumHP()
                                        * 3 / 10) {
                            SoundManager.playSound(
                                    GameSoundConstants.SOUND_LOW_HEALTH);
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
                            BattleDefinitions.killedEnemy(active.getTemplate());
                            BattleDefinitions.killedInBattle(enemy);
                            // Play death sound
                            SoundManager
                                    .playSound(GameSoundConstants.SOUND_DEATH);
                            // Remove effects from dead character
                            bc.getTemplate().stripAllEffects();
                            // Set dead character to inactive
                            bc.deactivate();
                            // Remove character from battle
                            this.bd.getBattleMap().setBattleCell(new Empty(),
                                    bc.getX(), bc.getY());
                        }
                        // Handle self death
                        if (!active.getTemplate().isAlive()) {
                            // Update Prestige
                            BattleDefinitions
                                    .killedInBattle(active.getTemplate());
                            // Play death sound
                            SoundManager
                                    .playSound(GameSoundConstants.SOUND_DEATH);
                            // Remove effects from dead character
                            active.getTemplate().stripAllEffects();
                            // Set dead character to inactive
                            active.deactivate();
                            // Remove character from battle
                            this.bd.getBattleMap().setBattleCell(new Empty(),
                                    active.getX(), active.getY());
                            // End turn
                            this.endTurn();
                        }
                    } else {
                        // Deny attack - out of actions
                        if (!this.bd.getActiveCharacter().getTemplate().hasAI()
                                || Support.inDebugMode()) {
                            this.setStatusMessage("Out of attacks!");
                        }
                        return false;
                    }
                } else {
                    // Move Failed
                    if (!active.getTemplate().hasAI()
                            || Support.inDebugMode()) {
                        this.setStatusMessage("Can't go that way");
                    }
                    return false;
                }
            }
        } else {
            // Confirm Flee
            if (!active.getTemplate().hasAI() || Support.inDebugMode()) {
                SoundManager.playSound(GameSoundConstants.SOUND_SPECIAL);
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
            BattleDefinitions.ranAway(active.getTemplate());
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

    private BattleCharacter getEnemy() {
        final int px = this.bd.getActiveCharacter().getX();
        final int py = this.bd.getActiveCharacter().getY();
        final Map m = this.bd.getBattleMap();
        MapObject next = null;
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

    private void showBattle(final boolean isBoss) {
        this.battleGUI.showBattle(isBoss);
    }

    private void hideBattle() {
        this.battleGUI.hideBattle();
    }

    public boolean castSpell() {
        // Check Spell Counter
        if (this.getActiveSpellCounter() > 0) {
            if (!this.bd.getActiveCharacter().getTemplate().hasAI()
                    || Support.inDebugMode()) {
                // Active character has no AI, or AI is turned off
                final boolean success = SpellCaster.selectAndCastSpell(
                        this.bd.getActiveCharacter().getTemplate(),
                        this.bd.getActiveCharacter().getTeamID(), true,
                        this.bd);
                if (success) {
                    this.decrementActiveSpellCounter();
                    // Update Prestige
                    BattleDefinitions.castSpell(this.bd.getSelfTarget());
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
                        .getAI().getSpellToCast();
                final boolean success = SpellCaster.castSpell(sp,
                        this.bd.getActiveCharacter().getTemplate(),
                        this.bd.getActiveCharacter().getTeamID(), true,
                        this.bd);
                if (success) {
                    this.decrementActiveSpellCounter();
                    // Update Prestige
                    BattleDefinitions.castSpell(this.bd.getSelfTarget());
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
                    || Support.inDebugMode()) {
                this.setStatusMessage("Out of actions!");
            }
            return false;
        }
    }

    public boolean useItem() {
        // Check Item Counter
        if (this.getActiveItemCounter() > 0) {
            if (!this.bd.getActiveCharacter().getTemplate().hasAI()
                    || Support.inDebugMode()) {
                // Active character has no AI, or AI is turned off
                final boolean success = CombatItemChucker.selectAndUseItem(
                        this.bd.getActiveCharacter().getTemplate(),
                        this.bd.getActiveCharacter().getTeamID(), true,
                        this.bd);
                if (success) {
                    this.bd.getActiveCharacter().modifyItems(1);
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
                        .getTemplate().getAI().getItemToUse();
                final boolean success = CombatItemChucker.useItem(cui,
                        this.bd.getActiveCharacter().getTemplate(),
                        this.bd.getActiveCharacter().getTeamID(), true,
                        this.bd);
                if (success) {
                    this.bd.getActiveCharacter().modifyItems(1);
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
                    || Support.inDebugMode()) {
                this.setStatusMessage("Out of item uses!");
            }
            return false;
        }
    }

    public boolean steal() {
        // Check Steal Counter
        if (this.getActiveStealCounter() > 0) {
            Creature activeEnemy = null;
            try {
                activeEnemy = this.getEnemy().getTemplate();
            } catch (final NullPointerException npe) {
                // Ignore
            }
            int stealChance;
            int stealAmount;
            this.bd.getActiveCharacter().modifySteals(1);
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
                    || Support.inDebugMode()) {
                this.setStatusMessage("Out of steals!");
            }
            return false;
        }
    }

    public boolean drain() {
        // Check Action Counter
        if (this.getActiveActionCounter() > 0) {
            Creature activeEnemy = null;
            try {
                activeEnemy = this.getEnemy().getTemplate();
            } catch (final NullPointerException npe) {
                // Ignore
            }
            int drainChance;
            int drainAmount;
            this.bd.getActiveCharacter()
                    .modifyAP(BattleLogic.DRAIN_ACTION_POINTS);
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
                    || Support.inDebugMode()) {
                this.setStatusMessage("Out of actions!");
            }
            return false;
        }
    }

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
        if (this.battleGUI.getOutputFrame().isVisible()
                && this.bd.getActiveCharacter() != null) {
            this.battleGUI.getViewManager().setViewingWindowCenterX(
                    this.bd.getActiveCharacter().getY());
            this.battleGUI.getViewManager().setViewingWindowCenterY(
                    this.bd.getActiveCharacter().getX());
            this.redrawBattle();
        }
    }

    private void redrawBattle() {
        this.battleGUI.redrawBattle(this.bd);
    }

    void redrawOneBattleSquare(final int x, final int y, final MapObject obj3) {
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

    private int getActiveItemCounter() {
        return this.bd.getActiveCharacter().getCurrentIT();
    }

    private int getActiveStealCounter() {
        return this.bd.getActiveCharacter().getCurrentST();
    }

    private void decrementActiveActionCounterBy(final int amount) {
        final BattleCharacter active = this.bd.getActiveCharacter();
        if (active != null) {
            active.modifyAP(amount);
        }
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
            if (this.bd.getBattlers()[x] != null
                    && this.bd.getBattlers()[x].isActive()) {
                // Use Effects
                this.bd.getBattlers()[x].getTemplate().useEffects();
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
                // Cull Inactive Effects
                this.bd.getBattlers()[x].getTemplate().cullInactiveEffects();
                // Handle death caused by effects
                if (!this.bd.getBattlers()[x].getTemplate().isAlive()) {
                    if (this.bd.getBattlers()[x]
                            .getTeamID() != Creature.TEAM_PARTY) {
                        // Update victory spoils
                        final int partySize = PartyManager.getParty()
                                .getActivePCCount();
                        this.vsd.setExpPerMonster(x - partySize,
                                this.bd.getBattlers()[x].getTemplate()
                                        .getExperience());
                    }
                    // Play death sound
                    SoundManager.playSound(GameSoundConstants.SOUND_DEATH);
                    // Set dead character to inactive
                    this.bd.getBattlers()[x].deactivate();
                    // Remove effects from dead character
                    this.bd.getBattlers()[x].getTemplate().stripAllEffects();
                    // Remove character from battle
                    this.bd.getBattleMap().setBattleCell(new Empty(),
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
                            .updateContext(this.bd.getBattleMap());
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
                        && !Support.inDebugMode()
                        && this.bd.getBattlers()[x].isActive()
                        && this.bd.getBattlers()[x].getTemplate().isAlive()) {
                    this.bd.getBattlerAIContexts()[x].getCharacter()
                            .getTemplate().getAI().newRoundHook();
                }
            }
        }
    }

    boolean isWaitingForAI() {
        return !this.battleGUI.areEventHandlersOn();
    }

    private void waitForAI() {
        this.battleGUI.turnEventHandlersOff();
        Gemma.getApplication().getMenuManager().disableBattleMenus();
    }

    private void stopWaitingForAI() {
        if (this.ait != null && this.ait.isAlive()) {
            this.ait.turnOver();
        }
        this.battleGUI.turnEventHandlersOn();
        Gemma.getApplication().getMenuManager().enableBattleMenus();
    }
}
