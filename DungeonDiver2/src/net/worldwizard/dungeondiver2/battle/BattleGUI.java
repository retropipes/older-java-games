/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.dungeondiver2.battle;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import net.worldwizard.commondialogs.CommonDialogs;
import net.worldwizard.dungeondiver2.DungeonDiver2;
import net.worldwizard.dungeondiver2.battle.damageengines.DamageEngine;
import net.worldwizard.dungeondiver2.game.scripts.GameScriptRunner;
import net.worldwizard.dungeondiver2.prefs.PreferencesManager;
import net.worldwizard.dungeondiver2.resourcemanagers.MusicConstants;
import net.worldwizard.dungeondiver2.resourcemanagers.MusicManager;
import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.support.ai.AIContext;
import net.worldwizard.support.ai.AIRoutine;
import net.worldwizard.support.ai.AutoAI;
import net.worldwizard.support.battle.Battle;
import net.worldwizard.support.battle.BattleDefinitions;
import net.worldwizard.support.creatures.Creature;
import net.worldwizard.support.creatures.PartyManager;
import net.worldwizard.support.creatures.PartyMember;
import net.worldwizard.support.creatures.StatConstants;
import net.worldwizard.support.creatures.castes.Caste;
import net.worldwizard.support.creatures.castes.CasteConstants;
import net.worldwizard.support.effects.Effect;
import net.worldwizard.support.items.combat.CombatItemManager;
import net.worldwizard.support.items.combat.CombatUsableItem;
import net.worldwizard.support.map.Map;
import net.worldwizard.support.map.generic.GameSoundConstants;
import net.worldwizard.support.map.generic.MapObject;
import net.worldwizard.support.map.generic.TypeConstants;
import net.worldwizard.support.map.objects.BattleCharacter;
import net.worldwizard.support.map.objects.Empty;
import net.worldwizard.support.resourcemanagers.ImageTransformer;
import net.worldwizard.support.resourcemanagers.MapObjectImageManager;
import net.worldwizard.support.resourcemanagers.SoundManager;
import net.worldwizard.support.scripts.game.GameScript;
import net.worldwizard.support.spells.Spell;
import net.worldwizard.support.spells.SpellCaster;

public class BattleGUI {
    // Fields
    private BattleDefinitions bd;
    private JFrame battleFrame;
    private Container battlePane, borderPane;
    private JLabel messageLabel;
    private EventHandler handler;
    private final BattleViewingWindowManager vwMgr;
    private final BattleStats bs;
    private DamageEngine de;
    private JLabel[][] drawGrid;
    boolean eventHandlersOn;
    private final AutoAI auto;
    private int damage;
    private int fumbleDamage;
    private int result;
    private int activeIndex;
    private int hostileCount;
    private boolean terminatedEarly;
    private boolean newRound;
    private int[] speedArray;
    private int lastSpeed;
    private boolean[] speedMarkArray;
    private boolean resultDoneAlready;
    private boolean lastAIActionResult;
    private final boolean bossMode;
    private static final int ITEM_ACTION_POINTS = 6;
    private static final int STEAL_ACTION_POINTS = 6;
    private static final int DRAIN_ACTION_POINTS = 6;

    // Constructors
    public BattleGUI(final boolean boss) {
        this.bd = new BattleDefinitions();
        this.auto = new AutoAI();
        this.vwMgr = new BattleViewingWindowManager();
        this.bs = new BattleStats();
        this.setUpGUI();
        this.eventHandlersOn = true;
        this.bossMode = boss;
    }

    // Methods
    public void doFixedBattle(final Map bMap, final Battle b) {
        this.doBattleInternal(bMap, b);
    }

    private void doBattleInternal(final Map bMap, final Battle b) {
        // Reset battle definitions
        this.bd = new BattleDefinitions();
        // Pre-Initialization
        this.bd.setBattleMap(bMap);
        // Initialize Battle
        this.de = DamageEngine.getInstance();
        this.resultDoneAlready = false;
        this.terminatedEarly = false;
        this.result = BattleResults.IN_PROGRESS;
        // Generate Friends
        final BattleCharacter[] friends = PartyManager.getParty()
                .getBattleCharacters();
        // Generate Enemies
        final BattleCharacter[] enemies = b.getBattlers();
        this.hostileCount = 0;
        for (final BattleCharacter enemie : enemies) {
            if (enemie != null) {
                this.hostileCount++;
                enemie.setTeamID(1);
            }
        }
        // Merge and Create AI Contexts
        for (int x = 0; x < friends.length + this.hostileCount; x++) {
            if (x < friends.length) {
                this.bd.addBattler(friends[x]);
            } else {
                this.bd.addBattler(enemies[x - friends.length]);
            }
            // Create an AI Context
            this.bd.getBattlerAIContexts()[x] = new AIContext(
                    this.bd.getBattlers()[x], this.bd.getBattleMap());
        }
        // Reset Inactive Indicators and Action Counters
        this.bd.resetBattlers();
        // Generate Speed Array
        this.generateSpeedArray();
        // Make sure message area is attached to the border pane
        this.borderPane.removeAll();
        this.borderPane.add(this.battlePane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        this.borderPane.add(this.bs.getStatsPane(), BorderLayout.EAST);
        // Set Character Locations
        this.setCharacterLocations();
        // Set First Active
        this.newRound = this.setNextActive(true);
        // Clear status message
        this.clearStatusMessage();
        // Start Battle
        this.vwMgr.setViewingWindowCenterX(this.bd.getActiveCharacter().getY());
        this.vwMgr.setViewingWindowCenterY(this.bd.getActiveCharacter().getX());
        SoundManager.playSound(GameSoundConstants.SOUND_ANGRY_MOB);
        this.showBattle();
        this.updateStats();
        this.redrawBattle();
    }

    private void battleDone() {
        this.stopWaitingForAI();
        if (!this.resultDoneAlready) {
            // Handle Results
            this.resultDoneAlready = true;
            if (this.result == BattleResults.WON) {
                final long exp = this.getExperience();
                final int gold = this.getGold();
                SoundManager.playSound(GameSoundConstants.SOUND_BOOTY);
                CommonDialogs.showTitledDialog("The party gains " + exp
                        + " experience and " + gold + " Gold.", "Victory!");
                PartyManager.getParty().distributeVictorySpoils(exp, gold);
                DungeonDiver2.getApplication().getGameManager()
                        .addToScore(Math.max(1, (exp + gold) / (100
                                * (PartyManager.getParty().getActivePCCount()
                                        + PartyManager.getParty()
                                                .getActiveNPCCount()))));
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
            final ArrayList<GameScript> scripts = PartyManager.getParty()
                    .checkPartyLevelUp();
            for (final GameScript script : scripts) {
                GameScriptRunner.runScript(script);
            }
            // Leave Battle
            this.hideBattle();
            // Return to whence we came
            DungeonDiver2.getApplication().getGameManager().showOutput();
            DungeonDiver2.getApplication().getGameManager().redrawMap();
            DungeonDiver2.getApplication().getGameManager().updateStats();
            // Check for Game Over
            DungeonDiver2.getApplication().getGameManager().checkGameOver();
        }
    }

    private void clearStatusMessage() {
        this.messageLabel.setText(" ");
    }

    private void setStatusMessage(final String msg) {
        this.messageLabel.setText(msg);
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
        } else {
            if (this.areTeamEnemiesGone(Creature.TEAM_PARTY)) {
                currResult = BattleResults.ENEMY_FLED;
            } else if (this.isTeamGone(Creature.TEAM_PARTY)) {
                currResult = BattleResults.FLED;
            } else {
                currResult = BattleResults.IN_PROGRESS;
            }
        }
        return currResult;
    }

    boolean getTerminatedEarly() {
        return this.terminatedEarly;
    }

    void executeNextAIAction() {
        int action;
        try {
            action = this.bd.getActiveCharacter().getTemplate().getAI()
                    .getNextAction(
                            this.bd.getBattlerAIContexts()[this.activeIndex]);
            switch (action) {
            case AIRoutine.ACTION_MOVE:
                final int x = this.bd.getActiveCharacter().getTemplate().getAI()
                        .getMoveX();
                final int y = this.bd.getActiveCharacter().getTemplate().getAI()
                        .getMoveY();
                this.lastAIActionResult = this.updatePosition(x, y);
                this.bd.getActiveCharacter().getTemplate().getAI()
                        .setLastResult(this.lastAIActionResult);
                break;
            case AIRoutine.ACTION_CAST_SPELL:
                this.lastAIActionResult = this.castSpell();
                this.bd.getActiveCharacter().getTemplate().getAI()
                        .setLastResult(this.lastAIActionResult);
                break;
            case AIRoutine.ACTION_DRAIN:
                this.lastAIActionResult = this.drain();
                this.bd.getActiveCharacter().getTemplate().getAI()
                        .setLastResult(this.lastAIActionResult);
                break;
            case AIRoutine.ACTION_STEAL:
                this.lastAIActionResult = this.steal();
                this.bd.getActiveCharacter().getTemplate().getAI()
                        .setLastResult(this.lastAIActionResult);
                break;
            case AIRoutine.ACTION_END_TURN:
                this.lastAIActionResult = true;
                this.endTurn();
                this.stopWaitingForAI();
                break;
            default:
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
        final String fumbleDamageString = Integer.toString(this.fumbleDamage);
        String displayDamageString = null;
        if (this.fumbleDamage != 0) {
            displayDamageString = "FUMBLE! " + activeName
                    + " drops their weapon, doing " + fumbleDamageString
                    + " damage to themselves!";
            SoundManager.playSound(GameSoundConstants.SOUND_DROP_ITEM);
        } else {
            if (this.damage == 0) {
                if (this.de.weaponMissed()) {
                    displayDamageString = activeName + " tries to hit "
                            + enemyName + ", but MISSES!";
                    SoundManager
                            .playSound(GameSoundConstants.SOUND_ATTACK_MISS);
                } else if (this.de.enemyDodged()) {
                    displayDamageString = activeName + " tries to hit "
                            + enemyName + ", but " + enemyName
                            + " AVOIDS the attack!";
                    SoundManager
                            .playSound(GameSoundConstants.SOUND_MISSILE_DODGE);
                } else {
                    displayDamageString = activeName + " tries to hit "
                            + enemyName + ", but MISSES!";
                    SoundManager
                            .playSound(GameSoundConstants.SOUND_ATTACK_MISS);
                }
            } else {
                displayDamageString = activeName + " hits " + enemyName
                        + " for " + damageString + " damage!";
                SoundManager.playSound(GameSoundConstants.SOUND_ATTACK_HIT);
            }
        }
        this.setStatusMessage(displayDamageString);
    }

    private void computeDamage(final Creature enemy, final Creature acting) {
        // Compute Damage
        this.fumbleDamage = 0;
        this.damage = 0;
        final int actual = this.de.computeDamage(enemy, acting);
        if (actual < 0) {
            // Fumble!
            this.fumbleDamage = -actual;
            acting.doDamage(this.fumbleDamage);
        } else {
            // Hit or Missed
            this.damage = actual;
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
            if (this.bd.getBattlers()[x] != null) {
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
                if (this.bd.getBattlers()[x].isActive()) {
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
            if (this.bd.getActiveCharacter().getTemplate().hasAI()) {
                // Run AI
                this.waitForAI();
                final AITask ait = new AITask(this);
                ait.start();
            }
            return false;
        } else {
            // Reset Speed Array
            this.resetSpeedArray();
            // Reset Action Counters
            for (int x = 0; x < this.bd.getBattlers().length; x++) {
                if (this.bd.getBattlers()[x] != null) {
                    this.bd.getBattlers()[x].resetAP();
                    this.bd.getBattlers()[x].resetAttacks();
                    this.bd.getBattlers()[x].resetSpells();
                }
            }
            // Maintain effects
            this.maintainEffects();
            // Perform new round actions
            this.performNewRoundActions();
            // Nobody to act next, set new round flag
            SoundManager.playSound(GameSoundConstants.SOUND_NEXT_ROUND);
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

    private long getExperience() {
        long res = 0L;
        for (int x = 0; x < this.bd.getBattlers().length; x++) {
            if (this.bd.getBattlers()[x] != null) {
                if (this.bd.getBattlers()[x].getTeamID() != 0) {
                    res += this.bd.getBattlers()[x].getTemplate()
                            .getExperience();
                }
            }
        }
        return res / this.hostileCount;
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
        return res / this.hostileCount;
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

    private boolean updatePositionInternal(final int x, final int y,
            final boolean useAP, final BattleCharacter active) {
        this.updateAllAIContexts();
        int px = active.getX();
        int py = active.getY();
        final Map m = this.bd.getBattleMap();
        MapObject next = null;
        MapObject nextGround = null;
        active.saveLocation();
        this.vwMgr.saveViewingWindow();
        try {
            next = m.getBattleCell(px + x, py + y);
            nextGround = m.getBattleGround(px + x, py + y);
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Ignore
        }
        if (next != null && nextGround != null) {
            if (!next.isSolidInBattle()) {
                if (useAP && this.getActiveActionCounter() >= nextGround
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
                    this.vwMgr.offsetViewingWindowLocationX(y);
                    this.vwMgr.offsetViewingWindowLocationY(x);
                    active.setSavedObject(m.getBattleCell(px, py));
                    m.setBattleCell(active, px, py);
                    this.decrementActiveActionCounterBy(
                            nextGround.getBattleAPCost());
                    SoundManager.playSound(nextGround.getBattleMoveSoundID());
                } else {
                    // Deny move - out of actions
                    SoundManager.playSound(GameSoundConstants.SOUND_NULL_EVENT);
                    this.setStatusMessage("Out of moves!");
                    return false;
                }
            } else {
                if (next.isOfType(TypeConstants.TYPE_BATTLE_CHARACTER)) {
                    if (useAP && this.getActiveAttackCounter() > 0 || !useAP) {
                        // Attack
                        final BattleCharacter bc = (BattleCharacter) next;
                        if (bc.getTeamID() == active.getTeamID()) {
                            // Attack Friend?
                            SoundManager
                                    .playSound(GameSoundConstants.SOUND_SONG);
                            final int confirm = CommonDialogs.showConfirmDialog(
                                    "Attack Friend?", "Battle");
                            if (confirm != JOptionPane.YES_OPTION) {
                                return false;
                            }
                        }
                        final Creature enemy = bc.getTemplate();
                        if (useAP) {
                            this.decrementActiveAttackCounter();
                        }
                        this.computeDamage(enemy, active.getTemplate());
                        // Handle enemy death
                        if (!enemy.isAlive()) {
                            // Play death sound
                            SoundManager
                                    .playSound(GameSoundConstants.SOUND_DEATH);
                            // Set dead character to inactive
                            bc.deactivate();
                            // Remove character from battle
                            this.bd.getBattleMap().setBattleCell(new Empty(),
                                    bc.getX(), bc.getY());
                        }
                        // Handle self death
                        if (!active.getTemplate().isAlive()) {
                            // Play death sound
                            SoundManager
                                    .playSound(GameSoundConstants.SOUND_DEATH);
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
                        SoundManager
                                .playSound(GameSoundConstants.SOUND_NULL_EVENT);
                        this.setStatusMessage("Out of attacks!");
                        return false;
                    }
                } else {
                    // Move Failed
                    this.setStatusMessage("Can't go that way");
                    SoundManager
                            .playSound(GameSoundConstants.SOUND_HIT_OBSTACLE);
                    return false;
                }
            }
        } else {
            if (!active.getTemplate().hasAI()) {
                // Confirm Flee
                SoundManager.playSound(GameSoundConstants.SOUND_SONG);
                final int confirm = CommonDialogs
                        .showConfirmDialog("Embrace Cowardice?", "Battle");
                if (confirm != JOptionPane.YES_OPTION) {
                    this.vwMgr.restoreViewingWindow();
                    active.restoreLocation();
                    return false;
                }
            }
            // Flee
            this.vwMgr.restoreViewingWindow();
            active.restoreLocation();
            // Set fled character to inactive
            active.deactivate();
            // Remove character from battle
            m.setBattleCell(new Empty(), active.getX(), active.getY());
            // End Turn
            this.endTurn();
            this.updateStats();
            final int currResult = this.getResult();
            if (currResult != BattleResults.IN_PROGRESS) {
                // Battle Done
                this.result = currResult;
                this.battleDone();
            }
            this.vwMgr.setViewingWindowCenterX(py);
            this.vwMgr.setViewingWindowCenterY(px);
            this.redrawBattle();
            return true;
        }
        this.updateStats();
        final int currResult = this.getResult();
        if (currResult != BattleResults.IN_PROGRESS) {
            // Battle Done
            this.result = currResult;
            this.battleDone();
        }
        this.vwMgr.setViewingWindowCenterX(py);
        this.vwMgr.setViewingWindowCenterY(px);
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
                            final BattleCharacter bc = (BattleCharacter) next;
                            return bc;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void showBattle() {
        DungeonDiver2.getApplication().getMenuManager().setBattleMenus();
        if (this.bossMode) {
            if (PreferencesManager
                    .getMusicEnabled(PreferencesManager.MUSIC_BOSS)) {
                MusicManager.stopMusic();
                MusicManager.playMusic(MusicConstants.MUSIC_BOSS);
            }
        } else {
            if (PreferencesManager
                    .getMusicEnabled(PreferencesManager.MUSIC_BATTLE)) {
                MusicManager.stopMusic();
                MusicManager.playMusic(MusicConstants.MUSIC_BATTLE);
            }
        }
        this.battleFrame.setVisible(true);
        this.battleFrame.setJMenuBar(DungeonDiver2.getApplication()
                .getMenuManager().getMainMenuBar());
    }

    private void hideBattle() {
        if (MusicManager.isMusicPlaying()) {
            MusicManager.stopMusic();
        }
        if (this.battleFrame != null) {
            this.battleFrame.setVisible(false);
        }
    }

    public boolean castSpell() {
        this.updateAllAIContexts();
        // Check Spell Counter
        if (this.getActiveSpellCounter() > 0) {
            if (!this.bd.getActiveCharacter().getTemplate().hasAI()) {
                // Active character has no AI
                final boolean success = SpellCaster.selectAndCastSpell(
                        this.bd.getActiveCharacter().getTemplate(),
                        this.bd.getActiveCharacter().getTeamID(), true,
                        this.bd);
                if (success) {
                    this.decrementActiveSpellCounter();
                }
                this.updateStats();
                final int currResult = this.getResult();
                if (currResult != BattleResults.IN_PROGRESS) {
                    // Battle Done
                    this.result = currResult;
                    this.battleDone();
                }
                return success;
            } else {
                // Active character has AI
                final Spell sp = this.bd.getActiveCharacter().getTemplate()
                        .getAI().getSpellToCast();
                final boolean success = SpellCaster.castSpell(sp,
                        this.bd.getActiveCharacter().getTemplate(),
                        this.bd.getActiveCharacter().getTeamID(), true,
                        this.bd);
                if (success) {
                    this.decrementActiveSpellCounter();
                }
                this.updateStats();
                final int currResult = this.getResult();
                if (currResult != BattleResults.IN_PROGRESS) {
                    // Battle Done
                    this.result = currResult;
                    this.battleDone();
                }
                return success;
            }
        } else {
            // Deny cast - out of spell casts
            SoundManager.playSound(GameSoundConstants.SOUND_NULL_EVENT);
            this.setStatusMessage("Out of spell casts!");
            return false;
        }
    }

    public boolean useItem() {
        this.updateAllAIContexts();
        // Check Action Counter
        if (this.getActiveActionCounter() > 0) {
            if (!this.bd.getActiveCharacter().getTemplate().hasAI()) {
                // Active character has no AI
                final boolean success = CombatItemManager.selectAndUseItem(
                        this.bd.getActiveCharacter().getTemplate(),
                        this.bd.getActiveCharacter().getTeamID(), true,
                        this.bd);
                if (success) {
                    this.bd.getActiveCharacter()
                            .modifyAP(BattleGUI.ITEM_ACTION_POINTS);
                }
                this.updateStats();
                final int currResult = this.getResult();
                if (currResult != BattleResults.IN_PROGRESS) {
                    // Battle Done
                    this.result = currResult;
                    this.battleDone();
                }
                return success;
            } else {
                // Active character has AI
                final CombatUsableItem cui = this.bd.getActiveCharacter()
                        .getTemplate().getAI().getItemToUse();
                final boolean success = CombatItemManager.useItem(cui,
                        this.bd.getActiveCharacter().getTemplate(),
                        this.bd.getActiveCharacter().getTeamID(), true,
                        this.bd);
                if (success) {
                    this.bd.getActiveCharacter()
                            .modifyAP(BattleGUI.ITEM_ACTION_POINTS);
                }
                this.updateStats();
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
            SoundManager.playSound(GameSoundConstants.SOUND_NULL_EVENT);
            this.setStatusMessage("Out of actions!");
            return false;
        }
    }

    public boolean steal() {
        this.updateAllAIContexts();
        // Check Action Counter
        if (this.getActiveActionCounter() > 0) {
            Creature activeEnemy = null;
            try {
                activeEnemy = this.getEnemy().getTemplate();
            } catch (final NullPointerException npe) {
                // Ignore
            }
            int stealChance;
            int stealAmount = 0;
            this.bd.getActiveCharacter()
                    .modifyAP(BattleGUI.STEAL_ACTION_POINTS);
            this.updateStats();
            try {
                final Caste caste = ((PartyMember) this.bd.getActiveCharacter()
                        .getTemplate()).getCaste();
                stealChance = StatConstants.CHANCE_STEAL + caste.getAttribute(
                        CasteConstants.CASTE_ATTRIBUTE_STEAL_SUCCESS_MODIFIER);
            } catch (final ClassCastException cce) {
                stealChance = StatConstants.CHANCE_STEAL;
            }
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
                // Succeeded
                final RandomRange stole = new RandomRange(0,
                        activeEnemy.getGold());
                stealAmount = stole.generate();
                this.bd.getActiveCharacter().getTemplate()
                        .offsetGold(stealAmount);
                this.setStatusMessage(this.bd.getActiveCharacter().getName()
                        + " tries to steal, and successfully steals "
                        + stealAmount + " gold!");
                return true;
            } else {
                final RandomRange chance = new RandomRange(0, 100);
                final int randomChance = chance.generate();
                if (randomChance <= stealChance) {
                    // Succeeded
                    final RandomRange stole = new RandomRange(0,
                            activeEnemy.getGold());
                    stealAmount = stole.generate();
                    this.bd.getActiveCharacter().getTemplate()
                            .offsetGold(stealAmount);
                    this.setStatusMessage(this.bd.getActiveCharacter().getName()
                            + " tries to steal, and successfully steals "
                            + stealAmount + " gold!");
                    return true;
                } else {
                    // Failed
                    this.setStatusMessage(this.bd.getActiveCharacter().getName()
                            + " tries to steal, but fails!");
                    return false;
                }
            }
        } else {
            // Deny steal - out of actions
            SoundManager.playSound(GameSoundConstants.SOUND_NULL_EVENT);
            this.setStatusMessage("Out of actions!");
            return false;
        }
    }

    public boolean drain() {
        this.updateAllAIContexts();
        // Check Action Counter
        if (this.getActiveActionCounter() > 0) {
            Creature activeEnemy = null;
            try {
                activeEnemy = this.getEnemy().getTemplate();
            } catch (final NullPointerException npe) {
                // Ignore
            }
            int drainChance;
            int drainAmount = 0;
            this.bd.getActiveCharacter()
                    .modifyAP(BattleGUI.DRAIN_ACTION_POINTS);
            this.updateStats();
            try {
                final Caste caste = ((PartyMember) this.bd.getActiveCharacter()
                        .getTemplate()).getCaste();
                drainChance = StatConstants.CHANCE_DRAIN + caste.getAttribute(
                        CasteConstants.CASTE_ATTRIBUTE_DRAIN_SUCCESS_MODIFIER);
            } catch (final ClassCastException cce) {
                drainChance = StatConstants.CHANCE_DRAIN;
            }
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
                // Succeeded
                final RandomRange drained = new RandomRange(0,
                        activeEnemy.getGold());
                drainAmount = drained.generate();
                activeEnemy.offsetCurrentMP(-drainAmount);
                this.bd.getActiveCharacter().getTemplate()
                        .offsetCurrentMP(drainAmount);
                this.setStatusMessage(this.bd.getActiveCharacter().getName()
                        + " tries to drain, and successfully drains "
                        + drainAmount + " MP!");
                return true;
            } else {
                final RandomRange chance = new RandomRange(0, 100);
                final int randomChance = chance.generate();
                if (randomChance <= drainChance) {
                    // Succeeded
                    final RandomRange drained = new RandomRange(0,
                            activeEnemy.getGold());
                    drainAmount = drained.generate();
                    activeEnemy.offsetCurrentMP(-drainAmount);
                    this.bd.getActiveCharacter().getTemplate()
                            .offsetCurrentMP(drainAmount);
                    this.setStatusMessage(this.bd.getActiveCharacter().getName()
                            + " tries to drain, and successfully drains "
                            + drainAmount + " MP!");
                    return true;
                } else {
                    // Failed
                    this.setStatusMessage(this.bd.getActiveCharacter().getName()
                            + " tries to drain, but fails!");
                    return false;
                }
            }
        } else {
            // Deny drain - out of actions
            SoundManager.playSound(GameSoundConstants.SOUND_NULL_EVENT);
            this.setStatusMessage("Out of actions!");
            return false;
        }
    }

    public void endTurn() {
        this.updateAllAIContexts();
        this.terminatedEarly = true;
        this.clearStatusMessage();
        this.newRound = this.setNextActive(this.newRound);
        if (this.newRound) {
            this.setStatusMessage("New Round");
            this.newRound = this.setNextActive(this.newRound);
        }
        this.updateStats();
        this.vwMgr.setViewingWindowCenterX(this.bd.getActiveCharacter().getY());
        this.vwMgr.setViewingWindowCenterY(this.bd.getActiveCharacter().getX());
        this.redrawBattle();
    }

    private void redrawBattle() {
        // Draw the battle, if it is visible
        if (this.battleFrame.isVisible()) {
            int x, y;
            int xFix, yFix;
            final int xView = this.vwMgr.getViewingWindowLocationX();
            final int yView = this.vwMgr.getViewingWindowLocationY();
            final int xlView = this.vwMgr.getLowerRightViewingWindowLocationX();
            final int ylView = this.vwMgr.getLowerRightViewingWindowLocationY();
            for (x = xView; x <= xlView; x++) {
                for (y = yView; y <= ylView; y++) {
                    xFix = x - xView;
                    yFix = y - yView;
                    try {
                        final BufferedImageIcon icon1 = this.bd.getBattleMap()
                                .getBattleGround(y, x).battleRenderHook(x, y);
                        final BufferedImageIcon icon2 = this.bd.getBattleMap()
                                .getBattleCell(y, x).battleRenderHook(x, y);
                        this.drawGrid[xFix][yFix].setIcon(ImageTransformer
                                .getCompositeImage(icon1, icon2));
                    } catch (final ArrayIndexOutOfBoundsException ae) {
                        this.drawGrid[xFix][yFix].setIcon(MapObjectImageManager
                                .getImage("Void", "Void", null));
                    } catch (final NullPointerException np) {
                        this.drawGrid[xFix][yFix].setIcon(MapObjectImageManager
                                .getImage("Void", "Void", null));
                    }
                }
            }
            this.battleFrame.pack();
        }
    }

    private void updateStats() {
        this.bs.updateStats(this.bd.getActiveCharacter());
    }

    public Creature[] getAllFriends() {
        return this.bd.getAllFriends();
    }

    public Creature[] getAllEnemies() {
        return this.bd.getAllEnemies();
    }

    public Creature getSelfTarget() {
        return this.bd.getSelfTarget();
    }

    private int getActiveActionCounter() {
        return this.bd.getActiveCharacter().getAP();
    }

    private int getActiveAttackCounter() {
        return this.bd.getActiveCharacter().getAttacks();
    }

    private int getActiveSpellCounter() {
        return this.bd.getActiveCharacter().getSpells();
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

    public void maintainEffects() {
        for (int x = 0; x < this.bd.getBattlers().length; x++) {
            // Maintain Effects
            if (this.bd.getBattlers()[x] != null) {
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
                this.bd.getBattlers()[x].getTemplate().useEffects();
                this.bd.getBattlers()[x].getTemplate().cullInactiveEffects();
            }
            // Handle death caused by effects
            if (this.bd.getBattlers()[x] != null) {
                if (!this.bd.getBattlers()[x].getTemplate().isAlive()) {
                    // Play death sound
                    SoundManager.playSound(GameSoundConstants.SOUND_DEATH);
                    // Set dead character to inactive
                    this.bd.getBattlers()[x].deactivate();
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
                                .getTemplate().hasAI()) {
                    this.bd.getBattlerAIContexts()[x].getCharacter()
                            .getTemplate().getAI().newRoundHook();
                }
            }
        }
    }

    boolean isWaitingForAI() {
        return !this.eventHandlersOn;
    }

    private void waitForAI() {
        this.eventHandlersOn = false;
        DungeonDiver2.getApplication().getMenuManager().disableBattleMenus();
    }

    void stopWaitingForAI() {
        this.eventHandlersOn = true;
        DungeonDiver2.getApplication().getMenuManager().enableBattleMenus();
    }

    private void setUpGUI() {
        this.handler = new EventHandler();
        this.borderPane = new Container();
        this.borderPane.setLayout(new BorderLayout());
        this.messageLabel = new JLabel(" ");
        this.messageLabel.setOpaque(true);
        this.battleFrame = new JFrame("Battle");
        this.battlePane = new Container();
        this.battleFrame.setContentPane(this.borderPane);
        final Image iconlogo = DungeonDiver2.getApplication().getIconLogo();
        this.battleFrame.setIconImage(iconlogo);
        this.battleFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.battlePane
                .setLayout(new GridLayout(this.vwMgr.getViewingWindowSizeX(),
                        this.vwMgr.getViewingWindowSizeY()));
        this.battleFrame.setResizable(false);
        this.drawGrid = new JLabel[this.vwMgr
                .getViewingWindowSizeX()][this.vwMgr.getViewingWindowSizeY()];
        final EmptyBorder eb = new EmptyBorder(0, 0, 0, 0);
        for (int x = 0; x < this.vwMgr.getViewingWindowSizeX(); x++) {
            for (int y = 0; y < this.vwMgr.getViewingWindowSizeY(); y++) {
                this.drawGrid[x][y] = new JLabel("", null,
                        SwingConstants.CENTER);
                // Mac OS X-specific fix to make draw grid line up properly
                if (System.getProperty("os.name").startsWith("Mac OS X")) {
                    this.drawGrid[x][y].setBorder(eb);
                }
                this.battlePane.add(this.drawGrid[x][y]);
            }
        }
        this.borderPane.add(this.battlePane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        this.borderPane.add(this.bs.getStatsPane(), BorderLayout.EAST);
        this.battleFrame.addKeyListener(this.handler);
    }

    private class EventHandler implements KeyListener {
        public EventHandler() {
            // TODO Auto-generated constructor stub
        }

        @Override
        public void keyPressed(final KeyEvent e) {
            // Do nothing
        }

        @Override
        public void keyReleased(final KeyEvent e) {
            try {
                boolean modKeyDown;
                if (System.getProperty("os.name")
                        .equalsIgnoreCase("Mac OS X")) {
                    modKeyDown = e.isMetaDown();
                } else {
                    modKeyDown = e.isControlDown();
                }
                final BattleGUI bg = BattleGUI.this;
                if (bg.eventHandlersOn && !modKeyDown) {
                    final int keyCode = e.getKeyCode();
                    switch (keyCode) {
                    case KeyEvent.VK_NUMPAD4:
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        bg.updatePosition(-1, 0);
                        break;
                    case KeyEvent.VK_NUMPAD2:
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_X:
                        bg.updatePosition(0, 1);
                        break;
                    case KeyEvent.VK_NUMPAD6:
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        bg.updatePosition(1, 0);
                        break;
                    case KeyEvent.VK_NUMPAD8:
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        bg.updatePosition(0, -1);
                        break;
                    case KeyEvent.VK_NUMPAD7:
                    case KeyEvent.VK_Q:
                        bg.updatePosition(-1, -1);
                        break;
                    case KeyEvent.VK_NUMPAD9:
                    case KeyEvent.VK_E:
                        bg.updatePosition(1, -1);
                        break;
                    case KeyEvent.VK_NUMPAD3:
                    case KeyEvent.VK_C:
                        bg.updatePosition(1, 1);
                        break;
                    case KeyEvent.VK_NUMPAD1:
                    case KeyEvent.VK_Z:
                        bg.updatePosition(-1, 1);
                        break;
                    case KeyEvent.VK_NUMPAD5:
                    case KeyEvent.VK_S:
                        // Confirm before attacking self
                        SoundManager.playSound(GameSoundConstants.SOUND_SONG);
                        final int res = CommonDialogs.showConfirmDialog(
                                "Are you sure you want to attack yourself?",
                                "Battle");
                        if (res == JOptionPane.YES_OPTION) {
                            bg.updatePosition(0, 0);
                        }
                        break;
                    default:
                        break;
                    }
                }
            } catch (final Exception ex) {
                DungeonDiver2.getErrorLogger().logError(ex);
            }
        }

        @Override
        public void keyTyped(final KeyEvent e) {
            // Do nothing
        }
    }
}
