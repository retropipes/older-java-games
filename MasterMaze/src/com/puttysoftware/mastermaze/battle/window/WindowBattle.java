package com.puttysoftware.mastermaze.battle.window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.ai.window.WindowAIRoutine;
import com.puttysoftware.mastermaze.battle.BattlePrestige;
import com.puttysoftware.mastermaze.battle.BattleResults;
import com.puttysoftware.mastermaze.battle.GenericBattle;
import com.puttysoftware.mastermaze.battle.damageengines.DamageEngine;
import com.puttysoftware.mastermaze.creatures.Creature;
import com.puttysoftware.mastermaze.creatures.PartyManager;
import com.puttysoftware.mastermaze.creatures.PartyMember;
import com.puttysoftware.mastermaze.creatures.StatConstants;
import com.puttysoftware.mastermaze.creatures.monsters.BaseMonster;
import com.puttysoftware.mastermaze.creatures.monsters.MonsterFactory;
import com.puttysoftware.mastermaze.effects.Effect;
import com.puttysoftware.mastermaze.items.combat.CombatItemChucker;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.maze.objects.BattleCharacter;
import com.puttysoftware.mastermaze.prefs.PreferencesManager;
import com.puttysoftware.mastermaze.resourcemanagers.MusicManager;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;
import com.puttysoftware.mastermaze.spells.SpellCaster;
import com.puttysoftware.randomrange.RandomRange;

public class WindowBattle extends GenericBattle {
    // Fields
    int stealAmount;
    int damage;
    boolean enemyDidDamage;
    boolean playerDidDamage;
    JFrame battleFrame;
    Container holderPane, iconPane, messagePane, buttonPane;
    JLabel iconLabel;
    JTextArea messageArea;
    JButton attack, flee, spell, steal, drain, item, done;
    BattleEventHandler handler;
    BaseMonster enemy;
    int result;
    private final DamageEngine de;
    static final int BASE_RUN_CHANCE = 80;
    static final int RUN_CHANCE_DIFF_FACTOR = 5;
    static final int ENEMY_BASE_RUN_CHANCE = 60;
    static final int ENEMY_RUN_CHANCE_DIFF_FACTOR = 10;

    // Constructor
    public WindowBattle() {
        // Initialize Battle Parameters
        this.de = DamageEngine.getInstance();
        this.damage = 0;
        this.stealAmount = 0;
        this.enemyDidDamage = false;
        this.playerDidDamage = false;
        // Initialize GUI
        this.battleFrame = new JFrame("Battle");
        this.holderPane = new Container();
        this.iconPane = new Container();
        this.messagePane = new Container();
        this.buttonPane = new Container();
        this.iconLabel = new JLabel("");
        this.messageArea = new JTextArea();
        this.messageArea.setOpaque(true);
        this.messageArea.setEditable(false);
        this.messageArea.setBackground(this.battleFrame.getBackground());
        this.attack = new JButton("Attack");
        this.flee = new JButton("Flee");
        this.spell = new JButton("Cast Spell");
        this.steal = new JButton("Steal");
        this.drain = new JButton("Drain");
        this.item = new JButton("Use Item");
        this.done = new JButton("Continue");
        this.battleFrame.getRootPane().setDefaultButton(this.done);
        this.iconPane.setLayout(new FlowLayout());
        this.messagePane.setLayout(new FlowLayout());
        this.buttonPane.setLayout(new GridLayout(2, 4));
        this.holderPane.setLayout(new BorderLayout());
        this.iconPane.add(this.iconLabel);
        this.messagePane.add(this.messageArea);
        this.buttonPane.add(this.attack);
        this.buttonPane.add(this.flee);
        this.buttonPane.add(this.spell);
        this.buttonPane.add(this.steal);
        this.buttonPane.add(this.drain);
        this.buttonPane.add(this.item);
        this.buttonPane.add(this.done);
        this.holderPane.add(this.iconPane, BorderLayout.WEST);
        this.holderPane.add(this.messagePane, BorderLayout.CENTER);
        this.holderPane.add(this.buttonPane, BorderLayout.SOUTH);
        this.battleFrame.setContentPane(this.holderPane);
        this.battleFrame.setResizable(false);
        this.battleFrame.pack();
        // Initialize Event Handlers
        this.handler = new BattleEventHandler();
        this.attack.addActionListener(this.handler);
        this.flee.addActionListener(this.handler);
        this.spell.addActionListener(this.handler);
        this.steal.addActionListener(this.handler);
        this.drain.addActionListener(this.handler);
        this.done.addActionListener(this.handler);
        this.attack.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "Attack");
        this.attack.getActionMap().put("Attack", this.handler);
        this.flee.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), "Flee");
        this.flee.getActionMap().put("Flee", this.handler);
        this.spell.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "Cast Spell");
        this.spell.getActionMap().put("Cast Spell", this.handler);
        this.steal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "Steal");
        this.steal.getActionMap().put("Steal", this.handler);
        this.drain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "Drain");
        this.drain.getActionMap().put("Drain", this.handler);
        this.item.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "Use Item");
        this.item.getActionMap().put("Use Item", this.handler);
    }

    @Override
    public JFrame getOutputFrame() {
        return this.battleFrame;
    }

    final boolean doPlayerActions(final int actionToPerform) {
        boolean success = true;
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        if (actionToPerform == WindowAIRoutine.ACTION_ATTACK) {
            final int actions = playerCharacter
                    .getWindowBattleActionsPerRound();
            for (int x = 0; x < actions; x++) {
                this.computePlayerDamage();
                this.displayPlayerRoundResults();
            }
        } else if (actionToPerform == WindowAIRoutine.ACTION_CAST_SPELL) {
            success = this.castSpell();
        } else if (actionToPerform == WindowAIRoutine.ACTION_FLEE) {
            final RandomRange rf = new RandomRange(0, 100);
            final int runChance = rf.generate();
            if (runChance <= this.computeRunChance()) {
                // Success
                this.setResult(BattleResults.FLED);
            } else {
                // Failure
                success = false;
                this.updateMessageAreaFleeFailed();
            }
        } else if (actionToPerform == WindowAIRoutine.ACTION_STEAL) {
            success = this.steal();
            if (success) {
                SoundManager.playSound(SoundConstants.SOUND_DRAIN);
                this.updateMessageAreaPostSteal();
            } else {
                SoundManager.playSound(SoundConstants.SOUND_ACTION_FAILED);
                this.updateMessageAreaStealFailed();
            }
        } else if (actionToPerform == WindowAIRoutine.ACTION_DRAIN) {
            success = this.drain();
            if (success) {
                SoundManager.playSound(SoundConstants.SOUND_DRAIN);
                this.updateMessageAreaPostDrain();
            } else {
                SoundManager.playSound(SoundConstants.SOUND_ACTION_FAILED);
                this.updateMessageAreaDrainFailed();
            }
        } else if (actionToPerform == WindowAIRoutine.ACTION_USE_ITEM) {
            success = this.useItem();
        }
        return success;
    }

    @Override
    public final void executeNextAIAction() {
        final int actionToPerform = this.enemy.getWindowAI().getNextAction(
                this.enemy);
        if (actionToPerform == WindowAIRoutine.ACTION_ATTACK) {
            final int actions = this.enemy.getWindowBattleActionsPerRound();
            for (int x = 0; x < actions; x++) {
                this.computeEnemyDamage();
                this.displayEnemyRoundResults();
            }
        } else if (actionToPerform == WindowAIRoutine.ACTION_CAST_SPELL) {
            SpellCaster.castSpell(this.enemy.getWindowAI().getSpellToCast(),
                    this.enemy);
        } else if (actionToPerform == WindowAIRoutine.ACTION_FLEE) {
            final RandomRange rf = new RandomRange(0, 100);
            final int runChance = rf.generate();
            if (runChance <= this.computeEnemyRunChance()) {
                // Success
                this.setResult(BattleResults.ENEMY_FLED);
            } else {
                // Failure
                this.updateMessageAreaEnemyFleeFailed();
            }
        }
    }

    private void computeDamage(final Creature theEnemy, final Creature acting) {
        // Compute Damage
        this.damage = 0;
        final int actual = this.de.computeDamage(theEnemy, acting);
        // Update Prestige
        if (actual != 0) {
            BattlePrestige.dealtDamage(acting, actual);
            BattlePrestige.tookDamage(theEnemy, actual);
            BattlePrestige.hitEnemy(acting);
            BattlePrestige.hitByEnemy(theEnemy);
        } else {
            BattlePrestige.missedEnemy(acting);
            BattlePrestige.dodgedAttack(theEnemy);
        }
        // Hit or Missed
        this.damage = actual;
        if (this.de.weaponFumble()) {
            acting.doDamage(this.damage);
        } else {
            if (this.damage < 0) {
                acting.doDamage(-this.damage);
            } else {
                theEnemy.doDamage(this.damage);
            }
        }
        // Check damage
        if (acting instanceof PartyMember) {
            if (this.damage > 0) {
                this.playerDidDamage = true;
            } else if (this.damage < 0) {
                this.enemyDidDamage = true;
            }
        } else if (acting instanceof BaseMonster) {
            if (this.damage > 0) {
                this.enemyDidDamage = true;
            } else if (this.damage < 0) {
                this.playerDidDamage = true;
            }
        }
    }

    final void computePlayerDamage() {
        // Compute Player Damage
        this.computeDamage(this.enemy, PartyManager.getParty().getLeader());
    }

    final void computeEnemyDamage() {
        // Compute Enemy Damage
        this.computeDamage(PartyManager.getParty().getLeader(), this.enemy);
    }

    final int computeRunChance() {
        return WindowBattle.BASE_RUN_CHANCE - this.enemy.getLevelDifference()
                * WindowBattle.RUN_CHANCE_DIFF_FACTOR;
    }

    final int computeEnemyRunChance() {
        return WindowBattle.ENEMY_BASE_RUN_CHANCE
                + this.enemy.getLevelDifference()
                * WindowBattle.ENEMY_RUN_CHANCE_DIFF_FACTOR;
    }

    final void displayBattleStats() {
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        final String enemyName = this.enemy.getName();
        final String fightingWhat = this.enemy.getFightingWhatString();
        final String monsterLevelString = enemyName + "'s Level: "
                + Integer.toString(this.enemy.getLevel());
        final String monsterHPString = this.enemy.getHPString();
        final String monsterMPString = this.enemy.getMPString();
        final String playerHPString = playerCharacter.getHPString();
        final String playerMPString = playerCharacter.getMPString();
        final String displayMonsterHPString = enemyName + "'s HP: "
                + monsterHPString;
        final String displayMonsterMPString = enemyName + "'s MP: "
                + monsterMPString;
        final String displayPlayerHPString = "Your HP: " + playerHPString;
        final String displayPlayerMPString = "Your MP: " + playerMPString;
        final String displayString = fightingWhat + "\n" + monsterLevelString
                + "\n" + displayMonsterHPString + "\n" + displayMonsterMPString
                + "\n" + displayPlayerHPString + "\n" + displayPlayerMPString;
        this.setStatusMessage(displayString);
    }

    final void displayPlayerRoundResults() {
        // Display player round results
        if (this.result != BattleResults.ENEMY_FLED) {
            final String enemyName = this.enemy.getName();
            final String playerDamageString = Integer.toString(this.damage);
            final String playerFumbleDamageString = Integer
                    .toString(this.damage);
            String displayPlayerDamageString = null;
            String playerWhackString = "";
            if (this.de.weaponFumble()) {
                displayPlayerDamageString = "FUMBLE! You drop your weapon, doing "
                        + playerFumbleDamageString + " damage to yourself!";
                SoundManager.playSound(SoundConstants.SOUND_FUMBLE);
            } else {
                if (this.damage == 0) {
                    displayPlayerDamageString = "You try to hit the "
                            + enemyName + ", but MISS!";
                    SoundManager.playSound(SoundConstants.SOUND_MISSED);
                } else if (this.damage < 0) {
                    displayPlayerDamageString = "You try to hit the "
                            + enemyName + ", but are RIPOSTED for "
                            + -this.damage + " damage!";
                    SoundManager.playSound(SoundConstants.SOUND_COUNTER);
                } else {
                    displayPlayerDamageString = "You hit the " + enemyName
                            + " for " + playerDamageString + " damage!";
                    SoundManager.playSound(SoundConstants.SOUND_HIT);
                }
                if (this.de.weaponCrit()) {
                    playerWhackString += "CRITICAL HIT!\n";
                    SoundManager.playSound(SoundConstants.SOUND_CRITICAL);
                }
                if (this.de.weaponPierce()) {
                    playerWhackString += "Your attack pierces the " + enemyName
                            + "'s armor!\n";
                }
            }
            final String displayString = playerWhackString
                    + displayPlayerDamageString;
            this.setStatusMessage(displayString);
        }
    }

    final void displayEnemyRoundResults() {
        // Display enemy round results
        if (this.result != BattleResults.ENEMY_FLED) {
            final String enemyName = this.enemy.getName();
            final String enemyDamageString = Integer.toString(this.damage);
            final String enemyFumbleDamageString = Integer
                    .toString(this.damage);
            String displayEnemyDamageString = null;
            String enemyWhackString = "";
            if (this.de.weaponFumble()) {
                displayEnemyDamageString = "FUMBLE! The " + enemyName
                        + " drops its weapon, doing " + enemyFumbleDamageString
                        + " damage to itself!";
                SoundManager.playSound(SoundConstants.SOUND_FUMBLE);
                enemyWhackString = "";
            } else {
                if (this.damage == 0) {
                    displayEnemyDamageString = "The " + enemyName
                            + " tries to hit you, but MISSES!";
                    SoundManager.playSound(SoundConstants.SOUND_MISSED);
                } else if (this.damage < 0) {
                    displayEnemyDamageString = "The " + enemyName
                            + " tries to hit you, but you RIPOSTE for "
                            + -this.damage + " damage!";
                    SoundManager.playSound(SoundConstants.SOUND_COUNTER);
                } else {
                    displayEnemyDamageString = "The " + enemyName
                            + " hits you for " + enemyDamageString + " damage!";
                    SoundManager.playSound(SoundConstants.SOUND_HIT);
                }
                if (this.de.weaponCrit()) {
                    enemyWhackString += "CRITICAL HIT!\n";
                    SoundManager.playSound(SoundConstants.SOUND_CRITICAL);
                }
                if (this.de.weaponPierce()) {
                    enemyWhackString += "The " + enemyName
                            + "'s attack pierces YOUR armor!\n";
                }
            }
            final String displayString = enemyWhackString
                    + displayEnemyDamageString;
            this.setStatusMessage(displayString);
        }
    }

    // Methods
    @Override
    public void doBattle() {
        MasterMaze.getApplication().getGameManager().hideOutput();
        if (PreferencesManager.getMusicEnabled(PreferencesManager.MUSIC_BATTLE)) {
            MusicManager.playMusic("battle");
        }
        SoundManager.playSound(SoundConstants.SOUND_BATTLE);
        this.enemy = MonsterFactory.getNewMonsterInstance(true, true, true,
                false);
        this.enemy.loadMonster();
        this.iconLabel.setIcon(this.enemy.getImage());
        this.enemyDidDamage = false;
        this.playerDidDamage = false;
        this.setResult(BattleResults.IN_PROGRESS);
        this.attack.setVisible(true);
        this.flee.setVisible(true);
        this.spell.setVisible(true);
        this.steal.setVisible(true);
        this.drain.setVisible(true);
        this.done.setVisible(false);
        this.attack.setEnabled(true);
        this.flee.setEnabled(true);
        this.spell.setEnabled(true);
        this.steal.setEnabled(true);
        this.drain.setEnabled(true);
        this.done.setEnabled(false);
        this.firstUpdateMessageArea();
        this.battleFrame.setVisible(true);
    }

    @Override
    public void doBattleByProxy() {
        this.enemy = MonsterFactory.getNewMonsterInstance(true, true, true,
                false);
        this.enemy.loadMonster();
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        final BaseMonster m = this.enemy;
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

    final int getResult() {
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        int currResult;
        if (this.result != BattleResults.IN_PROGRESS) {
            return this.result;
        }
        if (this.enemy.isAlive() && !playerCharacter.isAlive()) {
            if (!this.playerDidDamage) {
                currResult = BattleResults.ANNIHILATED;
            } else {
                currResult = BattleResults.LOST;
            }
        } else if (!this.enemy.isAlive() && playerCharacter.isAlive()) {
            if (!this.enemyDidDamage) {
                currResult = BattleResults.PERFECT;
            } else {
                currResult = BattleResults.WON;
            }
        } else if (!this.enemy.isAlive() && !playerCharacter.isAlive()) {
            currResult = BattleResults.DRAW;
        } else {
            currResult = BattleResults.IN_PROGRESS;
        }
        return currResult;
    }

    final void maintainEffects() {
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        playerCharacter.useEffects();
        playerCharacter.cullInactiveEffects();
        this.enemy.useEffects();
        this.enemy.cullInactiveEffects();
    }

    final void displayActiveEffects() {
        boolean flag1 = false, flag2 = false, flag3 = false;
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        final String effectString = playerCharacter.getCompleteEffectString();
        final String effectMessages = playerCharacter
                .getAllCurrentEffectMessages();
        final String enemyEffectMessages = this.enemy
                .getAllCurrentEffectMessages();
        if (!effectString.equals(Effect.getNullMessage())) {
            flag1 = true;
        }
        if (!effectMessages.equals(Effect.getNullMessage())) {
            flag2 = true;
        }
        if (!enemyEffectMessages.equals(Effect.getNullMessage())) {
            flag3 = true;
        }
        if (flag1) {
            this.setStatusMessage(effectString);
        }
        if (flag2) {
            this.setStatusMessage(effectMessages);
        }
        if (flag3) {
            this.setStatusMessage(enemyEffectMessages);
        }
    }

    final void clearMessageArea() {
        this.messageArea.setText("");
    }

    @Override
    public final void setStatusMessage(final String s) {
        this.messageArea.setText(this.messageArea.getText() + s + "\n");
    }

    final void stripExtraNewLine() {
        final String currText = this.messageArea.getText();
        this.messageArea.setText(currText.substring(0, currText.length() - 1));
    }

    final void firstUpdateMessageArea() {
        this.clearMessageArea();
        this.setStatusMessage("*** Beginning of Round ***");
        this.displayBattleStats();
        this.setStatusMessage("*** Beginning of Round ***\n");
        // Determine initiative
        boolean enemyGotJump = false;
        if (this.enemy.getSpeed() > PartyManager.getParty().getLeader()
                .getSpeed()) {
            // Enemy acts first!
            enemyGotJump = true;
        } else if (this.enemy.getSpeed() < PartyManager.getParty().getLeader()
                .getSpeed()) {
            // You act first!
            enemyGotJump = false;
        } else {
            // Equal, decide randomly
            final RandomRange jump = new RandomRange(0, 1);
            final int whoFirst = jump.generate();
            if (whoFirst == 1) {
                // Enemy acts first!
                enemyGotJump = true;
            } else {
                // You act first!
                enemyGotJump = false;
            }
        }
        if (enemyGotJump) {
            this.setStatusMessage("The enemy acts first!");
            this.executeNextAIAction();
            // Display Active Effects
            this.displayActiveEffects();
            // Maintain Effects
            this.maintainEffects();
        } else {
            this.setStatusMessage("You act first!");
        }
        this.setStatusMessage("\n*** End of Round ***");
        this.displayBattleStats();
        this.setStatusMessage("*** End of Round ***");
        this.stripExtraNewLine();
        this.battleFrame.pack();
    }

    final void updateMessageAreaEnemyFleeFailed() {
        this.setStatusMessage("The enemy tries to run away, but doesn't quite make it!");
    }

    final void updateMessageAreaPostSteal() {
        this.setStatusMessage("You try to steal money, and successfully steal "
                + this.stealAmount + " Gold!");
    }

    final void updateMessageAreaPostDrain() {
        this.setStatusMessage("You try to drain the enemy, and succeed!");
    }

    final void updateMessageAreaFleeFailed() {
        this.setStatusMessage("You try to run away, but don't quite make it!");
    }

    @Override
    public final boolean steal() {
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        final int stealChance = StatConstants.CHANCE_STEAL;
        final RandomRange chance = new RandomRange(0, 100);
        final int randomChance = chance.generate();
        if (randomChance <= stealChance) {
            // Succeeded
            final RandomRange stole = new RandomRange(0, this.enemy.getGold());
            this.stealAmount = stole.generate();
            playerCharacter.offsetGold(this.stealAmount);
            return true;
        } else {
            // Failed
            this.stealAmount = 0;
            return false;
        }
    }

    @Override
    public final boolean drain() {
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        final int drainChance = StatConstants.CHANCE_DRAIN;
        final RandomRange chance = new RandomRange(0, 100);
        final int randomChance = chance.generate();
        if (randomChance <= drainChance) {
            // Succeeded
            final RandomRange drained = new RandomRange(0,
                    this.enemy.getCurrentMP());
            final int drainAmount = drained.generate();
            this.enemy.offsetCurrentMP(-drainAmount);
            playerCharacter.offsetCurrentMP(drainAmount);
            return true;
        } else {
            // Failed
            return false;
        }
    }

    final void updateMessageAreaStealFailed() {
        this.setStatusMessage("You try to steal money from the enemy, but the attempt fails!");
    }

    final void updateMessageAreaDrainFailed() {
        this.setStatusMessage("You try to drain the enemy's MP, but the attempt fails!");
    }

    void doResult() {
        if (PreferencesManager.getMusicEnabled(PreferencesManager.MUSIC_BATTLE)) {
            MusicManager.stopMusic();
        }
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        final BaseMonster m = this.enemy;
        if (this.result == BattleResults.WON) {
            this.setStatusMessage("You gain " + m.getExperience()
                    + " experience and " + m.getGold() + " Gold.");
            playerCharacter.offsetExperience(m.getExperience());
            playerCharacter.offsetGold(m.getGold());
            SoundManager.playSound(SoundConstants.SOUND_VICTORY);
            MasterMaze.getApplication().getGameManager()
                    .addToScore(m.getExperience() + m.getGold());
        } else if (this.result == BattleResults.PERFECT) {
            this.setStatusMessage("You gain " + m.getExperience()
                    + " experience and " + m.getGold() + " Gold,\nplus "
                    + m.getPerfectBonusGold()
                    + " extra gold for a perfect fight!");
            playerCharacter.offsetExperience(m.getExperience());
            playerCharacter.offsetGold(m.getGold() + m.getPerfectBonusGold());
            SoundManager.playSound(SoundConstants.SOUND_VICTORY);
            MasterMaze
                    .getApplication()
                    .getGameManager()
                    .addToScore(
                            m.getExperience() + m.getGold()
                                    + m.getPerfectBonusGold());
        } else if (this.result == BattleResults.LOST) {
            this.setStatusMessage("You lost...");
        } else if (this.result == BattleResults.ANNIHILATED) {
            this.setStatusMessage("You lost without hurting your foe... you were annihilated!");
        } else if (this.result == BattleResults.DRAW) {
            this.setStatusMessage("The battle was a draw. You are fully healed!");
            playerCharacter.healPercentage(Creature.FULL_HEAL_PERCENTAGE);
            playerCharacter.regeneratePercentage(Creature.FULL_HEAL_PERCENTAGE);
        } else if (this.result == BattleResults.FLED) {
            this.setStatusMessage("You ran away successfully!");
        } else if (this.result == BattleResults.ENEMY_FLED) {
            this.setStatusMessage("The enemy runs away!");
            this.setStatusMessage("Since the enemy ran away, you gain nothing for this battle.");
        }
        // Cleanup
        this.attack.setVisible(false);
        this.flee.setVisible(false);
        this.spell.setVisible(false);
        this.steal.setVisible(false);
        this.drain.setVisible(false);
        this.done.setVisible(true);
        this.attack.setEnabled(false);
        this.flee.setEnabled(false);
        this.spell.setEnabled(false);
        this.steal.setEnabled(false);
        this.drain.setEnabled(false);
        this.done.setEnabled(true);
        playerCharacter.stripAllEffects();
        this.enemy.stripAllEffects();
        // Level Up Check
        if (playerCharacter.checkLevelUp()) {
            playerCharacter.levelUp();
            if (PreferencesManager.getSoundsEnabled()) {
                SoundManager.playSound(SoundConstants.SOUND_LEVEL_UP);
            }
            this.setStatusMessage("You reached level "
                    + playerCharacter.getLevel() + ".");
        }
        // Final Cleanup
        this.stripExtraNewLine();
        this.battleFrame.pack();
    }

    final void setResult(final int newResult) {
        this.result = newResult;
    }

    final void battleDone() {
        this.battleFrame.setVisible(false);
        MasterMaze.getApplication().getGameManager().showOutput();
        MasterMaze.getApplication().getGameManager().redrawMaze();
    }

    private class BattleEventHandler extends AbstractAction {
        private static final long serialVersionUID = 20239525230523523L;

        public BattleEventHandler() {
            // TODO Auto-generated constructor stub
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                boolean success = true;
                final String cmd = e.getActionCommand();
                final WindowBattle b = WindowBattle.this;
                // Clear Message Area
                b.clearMessageArea();
                // Display Beginning Stats
                b.setStatusMessage("*** Beginning of Round ***");
                b.displayBattleStats();
                b.setStatusMessage("*** Beginning of Round ***\n");
                // Do Player Actions
                if (cmd.equals("Attack") || cmd.equals("a")) {
                    // Attack
                    success = b.doPlayerActions(WindowAIRoutine.ACTION_ATTACK);
                } else if (cmd.equals("Flee") || cmd.equals("f")) {
                    // Try to Flee
                    success = b.doPlayerActions(WindowAIRoutine.ACTION_FLEE);
                    if (success) {
                        // Strip Extra Newline Character
                        b.stripExtraNewLine();
                        // Pack Battle Frame
                        b.battleFrame.pack();
                        // Get out of here
                        b.doResult();
                        return;
                    } else {
                        success = b
                                .doPlayerActions(WindowAIRoutine.ACTION_ATTACK);
                    }
                } else if (cmd.equals("Continue")) {
                    // Battle Done
                    b.battleDone();
                    return;
                } else if (cmd.equals("Cast Spell") || cmd.equals("c")) {
                    // Cast Spell
                    success = b
                            .doPlayerActions(WindowAIRoutine.ACTION_CAST_SPELL);
                    if (!success) {
                        // Strip Two Extra Newline Characters
                        b.stripExtraNewLine();
                        b.stripExtraNewLine();
                        // Pack Battle Frame
                        b.battleFrame.pack();
                        // Get out of here
                        return;
                    }
                } else if (cmd.equals("Steal") || cmd.equals("s")) {
                    // Steal Money
                    success = b.doPlayerActions(WindowAIRoutine.ACTION_STEAL);
                } else if (cmd.equals("Drain") || cmd.equals("d")) {
                    // Drain Enemy
                    success = b.doPlayerActions(WindowAIRoutine.ACTION_DRAIN);
                } else if (cmd.equals("Use Item") || cmd.equals("i")) {
                    // Use Item
                    success = b
                            .doPlayerActions(WindowAIRoutine.ACTION_USE_ITEM);
                    if (!success) {
                        // Strip Two Extra Newline Characters
                        b.stripExtraNewLine();
                        b.stripExtraNewLine();
                        // Pack Battle Frame
                        b.battleFrame.pack();
                        // Get out of here
                        return;
                    }
                }
                // Do Enemy Actions
                b.executeNextAIAction();
                // Display Active Effects
                b.displayActiveEffects();
                // Maintain Effects
                b.maintainEffects();
                // Display End Stats
                b.setStatusMessage("\n*** End of Round ***");
                b.displayBattleStats();
                b.setStatusMessage("*** End of Round ***");
                // Check Result
                final int currResult = b.getResult();
                if (currResult != BattleResults.IN_PROGRESS) {
                    b.setResult(currResult);
                    b.doResult();
                } else {
                    // Strip Extra Newline Character
                    b.stripExtraNewLine();
                    // Pack Battle Frame
                    b.battleFrame.pack();
                }
            } catch (final Throwable t) {
                MasterMaze.getErrorLogger().logError(t);
            }
        }
    }

    @Override
    public boolean getTerminatedEarly() {
        return false;
    }

    @Override
    public boolean getLastAIActionResult() {
        return true;
    }

    @Override
    public boolean updatePosition(final int x, final int y) {
        return true;
    }

    @Override
    public void fireArrow(final int x, final int y) {
        // Do nothing
    }

    @Override
    public void arrowDone(final BattleCharacter hit) {
        // Do nothing
    }

    @Override
    public boolean castSpell() {
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        return SpellCaster.selectAndCastSpell(playerCharacter);
    }

    @Override
    public boolean useItem() {
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        return CombatItemChucker.selectAndUseItem(playerCharacter);
    }

    @Override
    public void endTurn() {
        // Do nothing
    }

    @Override
    public void redrawOneBattleSquare(final int x, final int y,
            final MazeObject obj3) {
        // Do nothing
    }

    @Override
    public boolean isWaitingForAI() {
        return false;
    }

    @Override
    public BaseMonster getEnemy() {
        return this.enemy;
    }
}
