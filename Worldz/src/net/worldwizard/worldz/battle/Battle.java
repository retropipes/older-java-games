/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.battle;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.ai.AIContext;
import net.worldwizard.worldz.ai.AIRoutine;
import net.worldwizard.worldz.ai.AutoAI;
import net.worldwizard.worldz.creatures.Creature;
import net.worldwizard.worldz.creatures.Monster;
import net.worldwizard.worldz.creatures.PartyManager;
import net.worldwizard.worldz.creatures.PartyMember;
import net.worldwizard.worldz.creatures.StatConstants;
import net.worldwizard.worldz.creatures.castes.Caste;
import net.worldwizard.worldz.creatures.castes.CasteConstants;
import net.worldwizard.worldz.generic.TypeConstants;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.items.combat.CombatItemManager;
import net.worldwizard.worldz.items.combat.CombatUsableItem;
import net.worldwizard.worldz.objects.BattleCharacter;
import net.worldwizard.worldz.objects.Empty;
import net.worldwizard.worldz.resourcemanagers.GraphicsManager;
import net.worldwizard.worldz.resourcemanagers.MusicManager;
import net.worldwizard.worldz.resourcemanagers.SoundManager;
import net.worldwizard.worldz.spells.Spell;
import net.worldwizard.worldz.spells.SpellBookManager;
import net.worldwizard.worldz.world.World;

public class Battle {
    // Fields
    private JFrame battleFrame;
    private Container battlePane, borderPane;
    private JLabel messageLabel;
    private EventHandler handler;
    private BattleCharacter activeCharacter;
    private final BattleCharacter[] battlers;
    private final AIContext[] aiContexts;
    private final BattleViewingWindowManager vwMgr;
    private final BattleStats bs;
    private JLabel[][] drawGrid;
    private World battleWorld;
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
    private boolean aiEnabled;
    boolean eventHandlersOn;
    private boolean resultDoneAlready;
    private static boolean IN_BATTLE = false;
    private static final int ITEM_ACTION_POINTS = 6;
    private static final int STEAL_ACTION_POINTS = 6;
    private static final int DRAIN_ACTION_POINTS = 6;
    private static final int MAX_BATTLERS = 100;

    // Constructors
    public Battle() {
        this.vwMgr = new BattleViewingWindowManager();
        this.bs = new BattleStats();
        this.battlers = new BattleCharacter[Battle.MAX_BATTLERS];
        this.aiContexts = new AIContext[Battle.MAX_BATTLERS];
        this.setUpGUI();
        this.activeIndex = 0;
        this.aiEnabled = true;
        this.terminatedEarly = false;
        this.eventHandlersOn = true;
        this.auto = new AutoAI();
        this.resultDoneAlready = false;
    }

    // Methods
    public static boolean isInBattle() {
        return Battle.IN_BATTLE;
    }

    private static void setInBattle(final boolean battle) {
        Battle.IN_BATTLE = battle;
    }

    public JFrame getBattleFrame() {
        return this.battleFrame;
    }

    public void doBattle() {
        // Initialize Battle
        Battle.setInBattle(true);
        this.resultDoneAlready = false;
        this.result = BattleResults.IN_PROGRESS;
        final Application app = Worldz.getApplication();
        app.getGameManager().hideOutput();
        // Propagate dirty flag
        app.getWorldManager().setDirty(app.getWorldManager().getDirty());
        this.battleWorld = app.getGameManager().getTemporaryBattleCopy();
        // Generate Friends
        final BattleCharacter[] friends = PartyManager.getParty()
                .getBattleCharacters();
        // Generate Enemies
        final BattleCharacter[] enemies = new BattleCharacter[friends.length];
        final int ml = PartyManager.getParty().getPartyLevel();
        for (int x = 0; x < enemies.length; x++) {
            enemies[x] = new BattleCharacter(new Monster(ml));
            enemies[x].setTeamID(1);
        }
        this.hostileCount = enemies.length;
        // Merge and Create AI Contexts
        for (int x = 0; x < this.battlers.length; x++) {
            if (x < friends.length) {
                this.battlers[x] = friends[x];
            } else {
                if (x >= friends.length + enemies.length) {
                    break;
                }
                this.battlers[x] = enemies[x - friends.length];
            }
            // Create an AI Context
            this.aiContexts[x] = new AIContext(this.battlers[x],
                    this.battleWorld);
        }
        // Reset Inactive Indicators and Action Counters
        for (final BattleCharacter battler : this.battlers) {
            if (battler != null) {
                battler.activate();
                battler.resetAP();
                battler.resetAttacks();
                battler.resetSpells();
            }
        }
        // Generate Speed Array
        this.generateSpeedArray();
        // Make sure message area is attached to the border pane
        this.borderPane.removeAll();
        this.borderPane.add(this.battlePane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.SOUTH);
        this.borderPane.add(this.bs.getStatsPane(), BorderLayout.EAST);
        // Set Character Locations
        this.setCharacterLocations();
        // Set First Active
        this.newRound = this.setNextActive(true);
        // Clear status message
        this.clearStatusMessage();
        // Start Battle
        this.vwMgr.setViewingWindowCenterX(this.activeCharacter.getY());
        this.vwMgr.setViewingWindowCenterY(this.activeCharacter.getX());
        if (app.getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_BATTLE)) {
            SoundManager.playSound("battle");
        }
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
                if (Worldz.getApplication().getPrefsManager()
                        .getSoundEnabled(PreferencesManager.SOUNDS_BATTLE)) {
                    SoundManager.playSound("victory");
                }
                Messager.showTitledDialog("The party gains " + exp
                        + " experience and " + gold + " Gold.", "Victory!");
                PartyManager.getParty().distributeVictorySpoils(exp, gold);
            } else if (this.result == BattleResults.LOST) {
                Messager.showTitledDialog("The party has been defeated!",
                        "Defeat...");
            } else if (this.result == BattleResults.DRAW) {
                Messager.showTitledDialog("The battle was a draw.", "Draw");
            } else if (this.result == BattleResults.FLED) {
                Messager.showTitledDialog("The party fled!", "Party Fled");
            } else if (this.result == BattleResults.ENEMY_FLED) {
                Messager.showTitledDialog("The enemies fled!", "Enemies Fled");
            } else if (this.result == BattleResults.IN_PROGRESS) {
                Messager.showTitledDialog(
                        "The battle isn't over, but somehow the game thinks it is.",
                        "Uh-Oh!");
            } else {
                Messager.showTitledDialog(
                        "The result of the battle is unknown!", "Uh-Oh!");
            }
            // Strip Effects
            PartyManager.getParty().stripPartyEffects();
            // Level Up Check
            PartyManager.getParty().checkPartyLevelUp();
            // Leave Battle
            this.hideBattle();
            Battle.setInBattle(false);
            // Return to whence we came
            Worldz.getApplication().getGameManager().showOutput();
            Worldz.getApplication().getGameManager().redrawWorld();
            Worldz.getApplication().getGameManager().updateStats();
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
        if (this.areEnemiesAlive() && !this.areFriendsAlive()) {
            currResult = BattleResults.LOST;
        } else if (!this.areEnemiesAlive() && this.areFriendsAlive()) {
            currResult = BattleResults.WON;
        } else if (!this.areEnemiesAlive() && !this.areFriendsAlive()) {
            currResult = BattleResults.DRAW;
        } else {
            if (this.areEnemiesGone()) {
                currResult = BattleResults.ENEMY_FLED;
            } else if (this.areFriendsGone()) {
                currResult = BattleResults.FLED;
            } else {
                currResult = BattleResults.IN_PROGRESS;
            }
        }
        return currResult;
    }

    BattleCharacter getActiveCharacter() {
        return this.activeCharacter;
    }

    boolean getTerminatedEarly() {
        return this.terminatedEarly;
    }

    void executeNextAIAction() {
        int action;
        try {
            action = this.activeCharacter.getTemplate().getAI()
                    .getNextAction(this.aiContexts[this.activeIndex]);
        } catch (final NullPointerException npe) {
            // Battle has ended
            return;
        }
        switch (action) {
            case AIRoutine.ACTION_MOVE:
                final int x = this.activeCharacter.getTemplate().getAI().getMoveX();
                final int y = this.activeCharacter.getTemplate().getAI().getMoveY();
                this.activeCharacter.getTemplate().getAI()
                        .setLastResult(this.updatePosition(x, y));
                break;
            case AIRoutine.ACTION_CAST_SPELL:
                this.activeCharacter.getTemplate().getAI()
                        .setLastResult(this.castSpell());
                break;
            case AIRoutine.ACTION_DRAIN:
                this.activeCharacter.getTemplate().getAI()
                        .setLastResult(this.drain());
                break;
            case AIRoutine.ACTION_STEAL:
                this.activeCharacter.getTemplate().getAI()
                        .setLastResult(this.steal());
                break;
            case AIRoutine.ACTION_END_TURN:
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
    }

    private void executeAutoAI(final BattleCharacter acting) {
        final int index = this.findEnemy(acting.getName(), 0,
                this.battlers.length);
        final int action = this.auto.getNextAction(this.aiContexts[index]);
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
            if (Worldz.getApplication().getPrefsManager()
                    .getSoundEnabled(PreferencesManager.SOUNDS_BATTLE)) {
                SoundManager.playSound("fumble");
            }
        } else {
            if (this.damage == 0) {
                displayDamageString = activeName + " tries to hit " + enemyName
                        + ", but MISSES!";
                if (Worldz.getApplication().getPrefsManager()
                        .getSoundEnabled(PreferencesManager.SOUNDS_BATTLE)) {
                    SoundManager.playSound("missed");
                }
            } else {
                displayDamageString = activeName + " hits " + enemyName
                        + " for " + damageString + " damage!";
                if (Worldz.getApplication().getPrefsManager()
                        .getSoundEnabled(PreferencesManager.SOUNDS_BATTLE)) {
                    SoundManager.playSound("hit");
                }
            }
        }
        this.setStatusMessage(displayDamageString);
    }

    private void computeDamage(final Creature enemy, final Creature acting) {
        // Compute Damage
        this.fumbleDamage = 0;
        if (Battle.fumble(acting)) {
            // Fumble!
            final RandomRange fumDamage = new RandomRange(1,
                    Math.max(acting.getWeaponPower(), 1));
            this.fumbleDamage = fumDamage.generate();
            acting.doDamage(this.fumbleDamage);
        } else {
            final RandomRange randomAttackGenerator = new RandomRange(0,
                    (int) acting.getEffectedStat(StatConstants.STAT_ATTACK));
            final RandomRange randomEnemyDefenseGenerator = new RandomRange(0,
                    (int) enemy.getEffectedStat(StatConstants.STAT_DEFENSE));
            int currDamage = 0;
            final int randomAttack = randomAttackGenerator.generate();
            final int randomEnemyDefense = randomEnemyDefenseGenerator
                    .generate();
            if (randomAttack - randomEnemyDefense < 0) {
                // Missed
                currDamage = 0;
            } else {
                // Hit
                currDamage = randomAttack - randomEnemyDefense;
            }
            this.damage = (int) (currDamage
                    * StatConstants.FACTOR_DIFFERENTIAL_DAMAGE);
            enemy.doDamage(this.damage);
        }
        this.displayRoundResults(enemy, acting);
    }

    private static boolean fumble(final Creature fumbler) {
        final RandomRange fum = new RandomRange(1, 100);
        final int fumChance = fum.generate();
        return fumChance <= fumbler
                .getEffectedStat(StatConstants.STAT_FUMBLE_CHANCE);
    }

    private void generateSpeedArray() {
        this.speedArray = new int[this.battlers.length];
        this.speedMarkArray = new boolean[this.speedArray.length];
        this.resetSpeedArray();
    }

    private void resetSpeedArray() {
        for (int x = 0; x < this.speedArray.length; x++) {
            if (this.battlers[x] != null) {
                this.speedArray[x] = this.battlers[x].getTemplate()
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
                this.battleWorld.getRows() - 1);
        final RandomRange randY = new RandomRange(0,
                this.battleWorld.getColumns() - 1);
        int rx, ry;
        // Set Character Locations
        for (final BattleCharacter battler : this.battlers) {
            if (battler != null) {
                rx = randX.generate();
                ry = randY.generate();
                WorldObject obj = this.battleWorld.getBattleCell(rx, ry);
                while (obj.isSolid()) {
                    rx = randX.generate();
                    ry = randY.generate();
                    obj = this.battleWorld.getBattleCell(rx, ry);
                }
                battler.setX(rx);
                battler.setY(ry);
                this.battleWorld.setBattleCell(battler, rx, ry);
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
            this.activeCharacter = this.battlers[this.activeIndex];
            // Check
            if (!this.activeCharacter.isActive()) {
                // Inactive, pick new active character
                return this.setNextActive(isNewRound);
            }
            // AI Check
            if (this.aiEnabled) {
                if (this.activeCharacter.getTemplate().hasAI()) {
                    // Run AI
                    this.waitForAI();
                    final AITask ait = new AITask();
                    ait.start();
                }
            }
            return false;
        } else {
            // Reset Speed Array
            this.resetSpeedArray();
            // Reset Action Counters
            for (final BattleCharacter battler : this.battlers) {
                if (battler != null) {
                    battler.resetAP();
                    battler.resetAttacks();
                    battler.resetSpells();
                }
            }
            // Nobody to act next, set new round flag
            return true;
        }
    }

    boolean isWaitingForAI() {
        return !this.eventHandlersOn;
    }

    private void waitForAI() {
        this.eventHandlersOn = false;
        Worldz.getApplication().getMenuManager().disableBattleMenus();
    }

    void stopWaitingForAI() {
        this.eventHandlersOn = true;
        Worldz.getApplication().getMenuManager().enableBattleMenus();
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
        for (final BattleCharacter battler : this.battlers) {
            if (battler != null) {
                if (battler.getTeamID() != 0) {
                    res += battler.getTemplate().getExperience();
                }
            }
        }
        return res / this.hostileCount;
    }

    private int getGold() {
        int res = 0;
        for (final BattleCharacter battler : this.battlers) {
            if (battler != null) {
                if (battler.getTeamID() != 0) {
                    res += battler.getTemplate().getGold();
                }
            }
        }
        return res / this.hostileCount;
    }

    public Creature[] getAllFriends() {
        final Creature[] tempFriends = new Creature[this.battlers.length];
        int nnc = 0;
        for (int x = 0; x < tempFriends.length; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getTeamID() == 0) {
                    tempFriends[x] = this.battlers[x].getTemplate();
                    nnc++;
                }
            }
        }
        final Creature[] friends = new Creature[nnc];
        nnc = 0;
        for (final Creature tempFriend : tempFriends) {
            if (tempFriend != null) {
                friends[nnc] = tempFriend;
                nnc++;
            }
        }
        return friends;
    }

    public Creature[] getAllEnemies() {
        final Creature[] tempEnemies = new Creature[this.battlers.length];
        int nnc = 0;
        for (int x = 0; x < tempEnemies.length; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getTeamID() != 0) {
                    tempEnemies[x] = this.battlers[x].getTemplate();
                    nnc++;
                }
            }
        }
        final Creature[] enemies = new Creature[nnc];
        nnc = 0;
        for (final Creature tempEnemie : tempEnemies) {
            if (tempEnemie != null) {
                enemies[nnc] = tempEnemie;
                nnc++;
            }
        }
        return enemies;
    }

    private String[] buildNameList() {
        final String[] tempNames = new String[this.battlers.length];
        int nnc = 0;
        for (int x = 0; x < tempNames.length; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getTeamID() != 0) {
                    tempNames[x] = this.battlers[x].getName();
                    nnc++;
                }
            }
        }
        final String[] names = new String[nnc];
        nnc = 0;
        for (final String tempName : tempNames) {
            if (tempName != null) {
                names[nnc] = tempName;
                nnc++;
            }
        }
        return names;
    }

    public Creature pickOneEnemy() {
        final String[] pickNames = this.buildNameList();
        return this.pickEnemyInternal(pickNames, 1, 1);
    }

    public Creature pickOneEnemyRandomly() {
        final String[] pickNames = this.buildNameList();
        final RandomRange r = new RandomRange(0, pickNames.length - 1);
        final int res = this.findEnemy(pickNames[r.generate()], 0,
                this.battlers.length);
        if (res != -1) {
            return this.battlers[res].getTemplate();
        } else {
            return null;
        }
    }

    private Creature pickEnemyInternal(final String[] pickNames,
            final int current, final int number) {
        String text;
        if (number > 1) {
            text = "Pick " + number + " Enemies";
        } else {
            text = "Pick 1 Enemy";
        }
        final String response = Messager.showInputDialog(
                text + " - " + current + " of " + number, "Battle", pickNames,
                pickNames[0]);
        if (response != null) {
            final int loc = this.findEnemy(response, 0, this.battlers.length);
            if (loc != -1) {
                return this.battlers[loc].getTemplate();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Creature[] pickMultipleEnemies(final int number) {
        final String[] pickNames = this.buildNameList();
        final Creature[] res = new Creature[number];
        for (int x = 0; x < number; x++) {
            final Creature member = this.pickEnemyInternal(pickNames, x + 1,
                    number);
            res[x] = member;
        }
        return res;
    }

    public Creature[] pickMultipleEnemiesRandomly(final int number) {
        final Creature[] res = new Creature[number];
        for (int x = 0; x < number; x++) {
            final Creature picked = this.pickOneEnemyRandomly();
            res[x] = picked;
        }
        return res;
    }

    private int findEnemy(final String name, final int start, final int limit) {
        for (int x = start; x < limit; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getName().equals(name)) {
                    return x;
                }
            }
        }
        return -1;
    }

    private boolean areFriendsAlive() {
        boolean res = false;
        for (final BattleCharacter battler : this.battlers) {
            if (battler != null) {
                if (battler.getTeamID() == 0) {
                    res = res || battler.getTemplate().isAlive();
                }
            }
        }
        return res;
    }

    private boolean areEnemiesAlive() {
        boolean res = false;
        for (final BattleCharacter battler : this.battlers) {
            if (battler != null) {
                if (battler.getTeamID() != 0) {
                    res = res || battler.getTemplate().isAlive();
                }
            }
        }
        return res;
    }

    private boolean areEnemiesGone() {
        boolean res = true;
        for (final BattleCharacter battler : this.battlers) {
            if (battler != null) {
                if (battler.getTeamID() != 0) {
                    if (battler.getTemplate().isAlive()) {
                        res = res && !battler.isActive();
                    }
                }
            }
        }
        return res;
    }

    private boolean areFriendsGone() {
        boolean res = true;
        for (final BattleCharacter battler : this.battlers) {
            if (battler != null) {
                if (battler.getTeamID() == 0) {
                    if (battler.getTemplate().isAlive()) {
                        res = res && !battler.isActive();
                    }
                }
            }
        }
        return res;
    }

    private int getActiveActionCounter() {
        return this.activeCharacter.getAP();
    }

    private int getActiveAttackCounter() {
        return this.activeCharacter.getAttacks();
    }

    private int getActiveSpellCounter() {
        return this.activeCharacter.getSpells();
    }

    private void decrementActiveActionCounter() {
        this.activeCharacter.modifyAP(1);
    }

    private void decrementActiveAttackCounter() {
        this.activeCharacter.modifyAttacks(1);
    }

    private void decrementActiveSpellCounter() {
        this.activeCharacter.modifySpells(1);
    }

    boolean updatePosition(final int x, final int y) {
        return this.updatePositionInternal(x, y, true, this.activeCharacter);
    }

    private boolean updatePositionInternal(final int x, final int y,
            final boolean useAP, final BattleCharacter active) {
        int px = active.getX();
        int py = active.getY();
        final World m = this.battleWorld;
        WorldObject next = null;
        active.saveLocation();
        this.vwMgr.saveViewingWindow();
        try {
            next = m.getBattleCell(px + x, py + y);
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Ignore
        }
        if (next != null) {
            if (!next.isSolid()) {
                if (useAP && this.getActiveActionCounter() > 0 || !useAP) {
                    // Move
                    WorldObject obj1 = null;
                    WorldObject obj2 = null;
                    WorldObject obj3 = null;
                    WorldObject obj4 = null;
                    WorldObject obj6 = null;
                    WorldObject obj7 = null;
                    WorldObject obj8 = null;
                    WorldObject obj9 = null;
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
                    this.decrementActiveActionCounter();
                    // Maintain effects
                    this.maintainEffects();
                    if (Worldz.getApplication().getPrefsManager()
                            .getSoundEnabled(
                                    PreferencesManager.SOUNDS_BATTLE)) {
                        SoundManager.playSound("walk");
                    } else {
                        // Deny move - out of actions
                        if (!this.activeCharacter.getTemplate().hasAI()
                                || this.activeCharacter.getTemplate().hasAI()
                                        && !this.aiEnabled) {
                            this.setStatusMessage("Out of moves!");
                        }
                        return false;
                    }
                }
            } else {
                if (next.isOfType(TypeConstants.TYPE_BATTLE_CHARACTER)) {
                    if (useAP && this.getActiveAttackCounter() > 0 || !useAP) {
                        // Attack
                        final BattleCharacter bc = (BattleCharacter) next;
                        if (bc.getTeamID() == active.getTeamID()) {
                            // Attack Friend?
                            if (!active.getTemplate().hasAI()) {
                                final int confirm = Messager.showConfirmDialog(
                                        "Attack Friend?", "Battle");
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
                        // Maintain effects
                        this.maintainEffects();
                        this.computeDamage(enemy, active.getTemplate());
                        // Handle death
                        if (!enemy.isAlive()) {
                            // Set dead character to inactive
                            bc.deactivate();
                            // Remove character from battle
                            this.battleWorld.setBattleCell(new Empty(),
                                    bc.getX(), bc.getY());
                        }
                    } else {
                        // Deny attack - out of actions
                        if (!this.activeCharacter.getTemplate().hasAI()
                                || this.activeCharacter.getTemplate().hasAI()
                                        && !this.aiEnabled) {
                            this.setStatusMessage("Out of attacks!");
                        }
                        return false;
                    }
                } else {
                    // Move Failed
                    if (!active.getTemplate().hasAI()
                            || this.activeCharacter.getTemplate().hasAI()
                                    && !this.aiEnabled) {
                        if (Worldz.getApplication().getPrefsManager()
                                .getSoundEnabled(
                                        PreferencesManager.SOUNDS_BATTLE)) {
                            SoundManager.playSound("walkfail");
                        }
                    }
                }
            }
        } else {
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
        final int px = this.activeCharacter.getX();
        final int py = this.activeCharacter.getY();
        final World m = this.battleWorld;
        WorldObject next = null;
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
                    if (next.isSolid()) {
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

    public boolean castSpell() {
        // Check Spell Counter
        if (this.getActiveSpellCounter() > 0) {
            if (!this.activeCharacter.getTemplate().hasAI()
                    || this.activeCharacter.getTemplate().hasAI()
                            && !this.aiEnabled) {
                // Active character has no AI, or AI is turned off
                final boolean success = SpellBookManager
                        .selectAndCastSpell(this.activeCharacter.getTemplate());
                if (success) {
                    this.decrementActiveSpellCounter();
                }
                // Maintain effects
                this.maintainEffects();
                this.updateStats();
                final int currResult = this.getResult();
                if (currResult != BattleResults.IN_PROGRESS) {
                    // Battle Done
                    this.result = currResult;
                    this.battleDone();
                }
                return success;
            } else {
                // Active character has AI, and AI is turned on
                final Spell sp = this.activeCharacter.getTemplate().getAI()
                        .getSpellToCast();
                final boolean success = SpellBookManager.castSpell(sp,
                        this.activeCharacter.getTemplate());
                if (success) {
                    this.decrementActiveSpellCounter();
                }
                // Maintain effects
                this.maintainEffects();
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
            // Deny cast - out of actions
            if (!this.activeCharacter.getTemplate().hasAI()
                    || this.activeCharacter.getTemplate().hasAI()
                            && !this.aiEnabled) {
                this.setStatusMessage("Out of actions!");
            }
            return false;
        }
    }

    public boolean useItem() {
        // Check Action Counter
        if (this.getActiveActionCounter() > 0) {
            if (!this.activeCharacter.getTemplate().hasAI()
                    || this.activeCharacter.getTemplate().hasAI()
                            && !this.aiEnabled) {
                // Active character has no AI, or AI is turned off
                final boolean success = CombatItemManager
                        .selectAndUseItem(this.activeCharacter.getTemplate());
                if (success) {
                    this.activeCharacter.modifyAP(Battle.ITEM_ACTION_POINTS);
                }
                // Maintain effects
                this.maintainEffects();
                this.updateStats();
                final int currResult = this.getResult();
                if (currResult != BattleResults.IN_PROGRESS) {
                    // Battle Done
                    this.result = currResult;
                    this.battleDone();
                }
                return success;
            } else {
                // Active character has AI, and AI is turned on
                final CombatUsableItem cui = this.activeCharacter.getTemplate()
                        .getAI().getItemToUse();
                final boolean success = CombatItemManager.useItem(cui,
                        this.activeCharacter.getTemplate());
                if (success) {
                    this.activeCharacter.modifyAP(Battle.ITEM_ACTION_POINTS);
                }
                // Maintain effects
                this.maintainEffects();
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
            if (!this.activeCharacter.getTemplate().hasAI()
                    || this.activeCharacter.getTemplate().hasAI()
                            && !this.aiEnabled) {
                this.setStatusMessage("Out of actions!");
            }
            return false;
        }
    }

    public boolean steal() {
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
            this.activeCharacter.modifyAP(Battle.STEAL_ACTION_POINTS);
            // Maintain effects
            this.maintainEffects();
            this.updateStats();
            try {
                final Caste caste = ((PartyMember) this.activeCharacter
                        .getTemplate()).getCaste();
                stealChance = StatConstants.CHANCE_STEAL + caste.getAttribute(
                        CasteConstants.CASTE_ATTRIBUTE_STEAL_SUCCESS_MODIFIER);
            } catch (final ClassCastException cce) {
                stealChance = StatConstants.CHANCE_STEAL;
            }
            if (activeEnemy == null) {
                // Failed - nobody to steal from
                this.setStatusMessage(this.activeCharacter.getName()
                        + " tries to steal, but nobody is there to steal from!");
                return false;
            }
            if (stealChance <= 0) {
                // Failed
                this.setStatusMessage(this.activeCharacter.getName()
                        + " tries to steal, but fails!");
                return false;
            } else if (stealChance >= 100) {
                // Succeeded
                final RandomRange stole = new RandomRange(0,
                        activeEnemy.getGold());
                stealAmount = stole.generate();
                this.activeCharacter.getTemplate().offsetGold(stealAmount);
                this.setStatusMessage(this.activeCharacter.getName()
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
                    this.activeCharacter.getTemplate().offsetGold(stealAmount);
                    this.setStatusMessage(this.activeCharacter.getName()
                            + " tries to steal, and successfully steals "
                            + stealAmount + " gold!");
                    return true;
                } else {
                    // Failed
                    this.setStatusMessage(this.activeCharacter.getName()
                            + " tries to steal, but fails!");
                    return false;
                }
            }
        } else {
            // Deny steal - out of actions
            if (!this.activeCharacter.getTemplate().hasAI()
                    || this.activeCharacter.getTemplate().hasAI()
                            && !this.aiEnabled) {
                this.setStatusMessage("Out of actions!");
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
            int drainAmount = 0;
            this.activeCharacter.modifyAP(Battle.DRAIN_ACTION_POINTS);
            // Maintain effects
            this.maintainEffects();
            this.updateStats();
            try {
                final Caste caste = ((PartyMember) this.activeCharacter
                        .getTemplate()).getCaste();
                drainChance = StatConstants.CHANCE_DRAIN + caste.getAttribute(
                        CasteConstants.CASTE_ATTRIBUTE_DRAIN_SUCCESS_MODIFIER);
            } catch (final ClassCastException cce) {
                drainChance = StatConstants.CHANCE_DRAIN;
            }
            if (activeEnemy == null) {
                // Failed - nobody to drain from
                this.setStatusMessage(this.activeCharacter.getName()
                        + " tries to drain, but nobody is there to drain from!");
                return false;
            }
            if (drainChance <= 0) {
                // Failed
                this.setStatusMessage(this.activeCharacter.getName()
                        + " tries to drain, but fails!");
                return false;
            } else if (drainChance >= 100) {
                // Succeeded
                final RandomRange drained = new RandomRange(0,
                        activeEnemy.getGold());
                drainAmount = drained.generate();
                activeEnemy.offsetCurrentMP(-drainAmount);
                this.activeCharacter.getTemplate().offsetCurrentMP(drainAmount);
                this.setStatusMessage(this.activeCharacter.getName()
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
                    this.activeCharacter.getTemplate()
                            .offsetCurrentMP(drainAmount);
                    this.setStatusMessage(this.activeCharacter.getName()
                            + " tries to drain, and successfully drains "
                            + drainAmount + " MP!");
                    return true;
                } else {
                    // Failed
                    this.setStatusMessage(this.activeCharacter.getName()
                            + " tries to drain, but fails!");
                    return false;
                }
            }
        } else {
            // Deny drain - out of actions
            if (!this.activeCharacter.getTemplate().hasAI()
                    || this.activeCharacter.getTemplate().hasAI()
                            && !this.aiEnabled) {
                this.setStatusMessage("Out of actions!");
            }
            return false;
        }
    }

    public void endTurn() {
        this.terminatedEarly = true;
        this.clearStatusMessage();
        this.newRound = this.setNextActive(this.newRound);
        if (this.newRound) {
            this.setStatusMessage("New Round");
            this.newRound = this.setNextActive(this.newRound);
        }
        this.updateStats();
        this.vwMgr.setViewingWindowCenterX(this.activeCharacter.getY());
        this.vwMgr.setViewingWindowCenterY(this.activeCharacter.getX());
        this.redrawBattle();
    }

    public boolean isAIOn() {
        return this.aiEnabled;
    }

    public void turnAIOn() {
        // Turn ON AI
        this.aiEnabled = true;
        Messager.showDialog("AI Turned ON.");
    }

    public void turnAIOff() {
        // Turn OFF AI
        this.aiEnabled = false;
        Messager.showDialog("AI Turned OFF.");
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
                        final BufferedImageIcon icon1 = this.battleWorld
                                .getBattleGround(y, x).getImage();
                        final BufferedImageIcon icon2 = this.battleWorld
                                .getBattleCell(y, x).getImage();
                        this.drawGrid[xFix][yFix].setIcon(GraphicsManager
                                .getCompositeImage(icon1, icon2));
                    } catch (final ArrayIndexOutOfBoundsException ae) {
                        this.drawGrid[xFix][yFix]
                                .setIcon(GraphicsManager.getImage("Void"));
                    } catch (final NullPointerException np) {
                        this.drawGrid[xFix][yFix]
                                .setIcon(GraphicsManager.getImage("Void"));
                    }
                }
            }
            this.battleFrame.pack();
        }
    }

    private void updateStats() {
        this.bs.updateStats(this.activeCharacter);
    }

    private void maintainEffects() {
        for (int x = 0; x < this.battlers.length; x++) {
            // Maintain Effects
            if (this.battlers[x] != null) {
                this.battlers[x].getTemplate().useEffects();
                this.battlers[x].getTemplate().cullInactiveEffects();
            }
            // Update all AI Contexts
            if (this.aiContexts[x] != null) {
                this.aiContexts[x].updateContext(this.battleWorld);
            }
        }
    }

    private void showBattle() {
        final Application app = Worldz.getApplication();
        app.getMenuManager().setBattleMenus();
        if (app.getPrefsManager()
                .getMusicEnabled(PreferencesManager.MUSIC_BATTLE)) {
            if (!MusicManager.isMusicPlaying()) {
                MusicManager.playMusic("battle");
            }
        }
        this.battleFrame.setVisible(true);
        this.battleFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
    }

    private void hideBattle() {
        if (Worldz.getApplication().getPrefsManager()
                .getMusicEnabled(PreferencesManager.MUSIC_BATTLE)) {
            if (MusicManager.isMusicPlaying()) {
                MusicManager.stopMusic();
            }
        }
        if (this.battleFrame != null) {
            this.battleFrame.setVisible(false);
        }
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
        final Image iconlogo = Worldz.getApplication().getIconLogo();
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
        this.borderPane.add(this.messageLabel, BorderLayout.SOUTH);
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
                final Battle b = Battle.this;
                if (b.eventHandlersOn) {
                    final int keyCode = e.getKeyCode();
                    switch (keyCode) {
                        case KeyEvent.VK_NUMPAD4:
                        case KeyEvent.VK_LEFT:
                        case KeyEvent.VK_A:
                            b.updatePosition(-1, 0);
                            break;
                        case KeyEvent.VK_NUMPAD2:
                        case KeyEvent.VK_DOWN:
                        case KeyEvent.VK_X:
                            b.updatePosition(0, 1);
                            break;
                        case KeyEvent.VK_NUMPAD6:
                        case KeyEvent.VK_RIGHT:
                        case KeyEvent.VK_D:
                            b.updatePosition(1, 0);
                            break;
                        case KeyEvent.VK_NUMPAD8:
                        case KeyEvent.VK_UP:
                        case KeyEvent.VK_W:
                            b.updatePosition(0, -1);
                            break;
                        case KeyEvent.VK_NUMPAD7:
                        case KeyEvent.VK_Q:
                            b.updatePosition(-1, -1);
                            break;
                        case KeyEvent.VK_NUMPAD9:
                        case KeyEvent.VK_E:
                            b.updatePosition(1, -1);
                            break;
                        case KeyEvent.VK_NUMPAD3:
                        case KeyEvent.VK_C:
                            b.updatePosition(1, 1);
                            break;
                        case KeyEvent.VK_NUMPAD1:
                        case KeyEvent.VK_Z:
                            b.updatePosition(-1, 1);
                            break;
                        case KeyEvent.VK_NUMPAD5:
                        case KeyEvent.VK_S:
                            // Confirm before attacking self
                            final int res = Messager.showConfirmDialog(
                                    "Are you sure you want to attack yourself?",
                                    "Battle");
                            if (res == JOptionPane.YES_OPTION) {
                                b.updatePosition(0, 0);
                            }
                            break;
                        default:
                            break;
                    }
                }
            } catch (final Exception ex) {
                Worldz.getDebug().debug(ex);
            }
        }

        @Override
        public void keyTyped(final KeyEvent e) {
            // Do nothing
        }
    }
}
