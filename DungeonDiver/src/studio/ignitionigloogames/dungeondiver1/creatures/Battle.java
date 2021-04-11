package studio.ignitionigloogames.dungeondiver1.creatures;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.creatures.ai.AIRoutine;
import studio.ignitionigloogames.dungeondiver1.creatures.buffs.Buff;
import studio.ignitionigloogames.dungeondiver1.creatures.spells.Spell;
import studio.ignitionigloogames.dungeondiver1.creatures.spells.SpellBookManager;
import studio.ignitionigloogames.dungeondiver1.utilities.RandomRange;

public class Battle implements BattleResults, MoveTypes {
    // Fields
    protected int damage;
    protected int enemyDamage;
    protected int riposteDamage;
    protected int riposteEnemyDamage;
    protected boolean enemyDidDamage;
    protected boolean playerDidDamage;
    protected int moveType;
    protected int enemyMoveType;
    protected JFrame battleFrame;
    protected Container holderPane, iconPane, messagePane, buttonPane;
    protected JLabel iconLabel;
    protected JTextArea messageArea;
    protected JButton attack, flee, spell, done;
    protected BattleEventHandler handler;
    protected Creature enemy;
    protected int result;
    protected int enemyAction;
    protected static boolean IN_BATTLE = false;
    public static final int RUN_CHANCE = 80;

    // Constructor
    public Battle() {
        // Initialize Battle Parameters
        this.damage = 0;
        this.enemyDamage = 0;
        this.riposteDamage = 0;
        this.riposteEnemyDamage = 0;
        this.enemyDidDamage = false;
        this.playerDidDamage = false;
        this.moveType = MoveTypes.NORMAL;
        this.enemyMoveType = MoveTypes.NORMAL;
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
        this.messageArea.setMinimumSize(this.messagePane.getSize());
        this.attack = new JButton("Attack");
        this.flee = new JButton("Flee");
        this.spell = new JButton("Cast Spell");
        this.done = new JButton("Continue");
        this.battleFrame.getRootPane().setDefaultButton(this.done);
        this.iconPane.setLayout(new FlowLayout());
        this.messagePane.setLayout(new FlowLayout());
        this.buttonPane.setLayout(new GridLayout(1, 4));
        this.holderPane.setLayout(new BorderLayout());
        this.iconPane.add(this.iconLabel);
        this.messagePane.add(this.messageArea);
        this.buttonPane.add(this.attack);
        this.buttonPane.add(this.flee);
        this.buttonPane.add(this.spell);
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
        this.done.addActionListener(this.handler);
        this.attack.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "Attack");
        this.attack.getActionMap().put("Attack", this.handler);
        this.flee.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), "Flee");
        this.flee.getActionMap().put("Flee", this.handler);
        this.spell.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "Cast Spell");
        this.spell.getActionMap().put("Cast Spell", this.handler);
    }

    protected final void computeDamage() {
        // Compute Damage
        if (this.result != BattleResults.ENEMY_FLED) {
            this.computePlayerDamage();
            this.computeEnemyDamage();
        }
    }

    protected final void computePlayerDamage() {
        // Compute Player Damage
        if (this.result != BattleResults.ENEMY_FLED) {
            final Player player = DungeonDiver.getHoldingBag().getPlayer();
            this.moveType = MoveTypes.NORMAL;
            final RandomRange randomAttackGenerator = new RandomRange(0,
                    (int) player.getBuffedStat(StatConstants.STAT_ATTACK));
            final RandomRange randomEnemyDefenseGenerator = new RandomRange(0,
                    (int) this.enemy.getBuffedStat(StatConstants.STAT_DEFENSE));
            int currDamage = 0;
            int currRiposteEnemyDamage = 0;
            final double multiplier = Battle.powerMove();
            final int normalizedMultiplier = (int) (multiplier * 2);
            final int addition = Battle.pierce();
            this.moveType = normalizedMultiplier + addition;
            final int randomAttack = (int) (randomAttackGenerator.generate()
                    * multiplier);
            final int randomEnemyDefense = randomEnemyDefenseGenerator
                    .generate();
            if (addition == MoveTypes.PIERCING_MODIFIER) {
                currDamage = randomAttack;
            } else {
                if (randomAttack - randomEnemyDefense < 0) {
                    currRiposteEnemyDamage = randomEnemyDefense - randomAttack;
                } else {
                    currDamage = randomAttack - randomEnemyDefense;
                }
            }
            this.damage = currDamage;
            this.riposteEnemyDamage = currRiposteEnemyDamage;
            if (currRiposteEnemyDamage > 0) {
                this.enemyDidDamage = true;
            }
            if (currDamage > 0) {
                this.playerDidDamage = true;
            }
            player.doDamage(currRiposteEnemyDamage);
            this.enemy.doDamage(currDamage);
        }
    }

    protected final void computeEnemyDamage() {
        // Compute Enemy Damage
        if (this.result != BattleResults.ENEMY_FLED) {
            final Player player = DungeonDiver.getHoldingBag().getPlayer();
            final RandomRange randomDefenseGenerator = new RandomRange(0,
                    (int) player.getBuffedStat(StatConstants.STAT_DEFENSE));
            final RandomRange randomEnemyAttackGenerator = new RandomRange(0,
                    (int) this.enemy.getBuffedStat(StatConstants.STAT_ATTACK));
            int currEnemyDamage = 0;
            int currRiposteDamage = 0;
            final double multiplier = Battle.powerMove();
            final int normalizedMultiplier = (int) (multiplier * 2);
            final int addition = Battle.pierce();
            this.enemyMoveType = normalizedMultiplier + addition;
            final int randomEnemyAttack = (int) (randomEnemyAttackGenerator
                    .generate() * multiplier);
            final int randomDefense = randomDefenseGenerator.generate();
            if (addition == MoveTypes.PIERCING_MODIFIER) {
                currEnemyDamage = randomEnemyAttack;
            } else {
                if (randomEnemyAttack - randomDefense < 0) {
                    currRiposteDamage = randomDefense - randomEnemyAttack;
                } else {
                    currEnemyDamage = randomEnemyAttack - randomDefense;
                }
            }
            this.enemyDamage = currEnemyDamage;
            this.riposteDamage = currRiposteDamage;
            if (currEnemyDamage > 0) {
                this.enemyDidDamage = true;
            }
            if (currRiposteDamage > 0) {
                this.playerDidDamage = true;
            }
            player.doDamage(currEnemyDamage);
            this.enemy.doDamage(currRiposteDamage);
        }
    }

    protected final void displayBattleStats() {
        if (this.result != BattleResults.ENEMY_FLED) {
            final Player player = DungeonDiver.getHoldingBag().getPlayer();
            final String enemyName = this.enemy.getName();
            final String fightingWhat = this.enemy.getFightingWhatString();
            final String monsterLevelString = enemyName + "'s Level: "
                    + Integer.toString(this.enemy.getLevel());
            final String monsterHPString = this.enemy.getHPString();
            final String monsterMPString = this.enemy.getMPString();
            final String playerHPString = player.getHPString();
            final String playerMPString = player.getMPString();
            final String displayMonsterHPString = enemyName + "'s HP: "
                    + monsterHPString;
            final String displayMonsterMPString = enemyName + "'s MP: "
                    + monsterMPString;
            final String displayPlayerHPString = "Your HP: " + playerHPString;
            final String displayPlayerMPString = "Your MP: " + playerMPString;
            final String displayString = fightingWhat + "\n"
                    + monsterLevelString + "\n" + displayMonsterHPString + "\n"
                    + displayMonsterMPString + "\n" + displayPlayerHPString
                    + "\n" + displayPlayerMPString;
            this.appendToMessageArea(displayString);
        }
    }

    protected final void displayRoundResults() {
        // Display round results
        if (this.result != BattleResults.ENEMY_FLED) {
            this.displayPlayerRoundResults();
            this.displayEnemyRoundResults();
        }
    }

    protected final void displayPlayerRoundResults() {
        // Display player round results
        if (this.result != BattleResults.ENEMY_FLED) {
            final String enemyName = this.enemy.getName();
            final String playerDamageString = Integer.toString(this.damage);
            String displayPlayerDamageString = null;
            String playerWhackString = null;
            if (this.damage == 0 && this.riposteEnemyDamage == 0) {
                displayPlayerDamageString = "You try to hit the " + enemyName
                        + ", but MISS!";
            } else if (this.damage == 0 && this.riposteEnemyDamage != 0) {
                displayPlayerDamageString = "You try to hit the " + enemyName
                        + ", but are RIPOSTED for " + this.riposteEnemyDamage
                        + " damage!";
            } else {
                displayPlayerDamageString = "You hit the " + enemyName + " for "
                        + playerDamageString + " damage!";
            }
            if (this.moveType == MoveTypes.PIERCING_ULTIMATE_WHACK) {
                playerWhackString = "You execute an ULTIMATE mighty blow!\n"
                        + "Your attack pierces the " + enemyName
                        + "'s armor!\n";
            } else if (this.moveType == MoveTypes.PIERCING_MAJOR_WHACK) {
                playerWhackString = "You execute a major mighty blow!\n"
                        + "Your attack pierces the " + enemyName
                        + "'s armor!\n";
            } else if (this.moveType == MoveTypes.PIERCING_WHACK) {
                playerWhackString = "You execute a mighty blow!\n"
                        + "Your attack pierces the " + enemyName
                        + "'s armor!\n";
            } else if (this.moveType == MoveTypes.PIERCING_MINOR_WHACK) {
                playerWhackString = "You execute a minor mighty blow!\n"
                        + "Your attack pierces the " + enemyName
                        + "'s armor!\n";
            } else if (this.moveType == MoveTypes.PIERCING) {
                playerWhackString = "Your attack pierces the " + enemyName
                        + "'s armor!\n";
            } else if (this.moveType == MoveTypes.ULTIMATE_WHACK) {
                playerWhackString = "You execute an ULTIMATE mighty blow!\n";
            } else if (this.moveType == MoveTypes.MAJOR_WHACK) {
                playerWhackString = "You execute a major mighty blow!\n";
            } else if (this.moveType == MoveTypes.WHACK) {
                playerWhackString = "You execute a mighty blow!\n";
            } else if (this.moveType == MoveTypes.MINOR_WHACK) {
                playerWhackString = "You execute a minor mighty blow!\n";
            } else {
                playerWhackString = "";
            }
            final String displayString = playerWhackString
                    + displayPlayerDamageString;
            this.appendToMessageAreaNewLine(displayString);
        }
    }

    protected final void displayEnemyRoundResults() {
        // Display player round results
        if (this.result != BattleResults.ENEMY_FLED) {
            final String enemyName = this.enemy.getName();
            final String enemyDamageString = Integer.toString(this.enemyDamage);
            String displayEnemyDamageString = null;
            String enemyWhackString = null;
            if (this.enemyDamage == 0 && this.riposteDamage == 0) {
                displayEnemyDamageString = "The " + enemyName
                        + " tries to hit you, but MISSES!";
            } else if (this.enemyDamage == 0 && this.riposteDamage != 0) {
                displayEnemyDamageString = "The " + enemyName
                        + " tries to hit you, but you RIPOSTE for "
                        + this.riposteDamage + " damage!";
            } else {
                displayEnemyDamageString = "The " + enemyName + " hits you for "
                        + enemyDamageString + " damage!";
            }
            if (this.enemyMoveType == MoveTypes.PIERCING_ULTIMATE_WHACK) {
                enemyWhackString = "The " + enemyName
                        + " executes an ULTIMATE mighty blow!\n" + "The "
                        + enemyName + "'s attack pierces YOUR armor!\n";
            } else if (this.enemyMoveType == MoveTypes.PIERCING_MAJOR_WHACK) {
                enemyWhackString = "The " + enemyName
                        + " executes a major mighty blow!\n" + "The "
                        + enemyName + "'s attack pierces YOUR armor!\n";
            } else if (this.enemyMoveType == MoveTypes.PIERCING_WHACK) {
                enemyWhackString = "The " + enemyName
                        + " executes a mighty blow!\n" + "The " + enemyName
                        + "'s attack pierces YOUR armor!\n";
            } else if (this.enemyMoveType == MoveTypes.PIERCING_MINOR_WHACK) {
                enemyWhackString = "The " + enemyName
                        + " executes a minor mighty blow!\n" + "The "
                        + enemyName + "'s attack pierces YOUR armor!\n";
            } else if (this.enemyMoveType == MoveTypes.PIERCING) {
                enemyWhackString = "The " + enemyName
                        + "'s attack pierces YOUR armor!\n";
            } else if (this.enemyMoveType == MoveTypes.ULTIMATE_WHACK) {
                enemyWhackString = "The " + enemyName
                        + " executes an ULTIMATE mighty blow!\n";
            } else if (this.enemyMoveType == MoveTypes.MAJOR_WHACK) {
                enemyWhackString = "The " + enemyName
                        + " executes a major mighty blow!\n";
            } else if (this.enemyMoveType == MoveTypes.WHACK) {
                enemyWhackString = "The " + enemyName
                        + " executes a mighty blow!\n";
            } else if (this.enemyMoveType == MoveTypes.MINOR_WHACK) {
                enemyWhackString = "The " + enemyName
                        + " executes a minor mighty blow!\n";
            } else {
                enemyWhackString = "";
            }
            final String displayString = enemyWhackString
                    + displayEnemyDamageString;
            this.appendToMessageAreaNewLine(displayString);
        }
    }

    // Method
    public void doBattle() {
        Battle.IN_BATTLE = true;
        DungeonDiver.getHoldingBag().getDungeonGUI().hideDungeon();
        this.enemy = new Monster();
        this.iconLabel.setIcon(this.enemy.getImage());
        this.enemyDidDamage = false;
        this.playerDidDamage = false;
        this.setResult(BattleResults.IN_PROGRESS);
        this.attack.setVisible(true);
        this.flee.setVisible(true);
        this.spell.setVisible(true);
        this.done.setVisible(false);
        this.attack.setEnabled(true);
        this.flee.setEnabled(true);
        this.spell.setEnabled(true);
        this.done.setEnabled(false);
        this.firstUpdateMessageArea();
        this.battleFrame.setVisible(true);
    }

    public final Creature getEnemy() {
        return this.enemy;
    }

    public final int getResult() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        int currResult;
        if (this.enemy.isAlive() && !player.isAlive()) {
            if (!this.playerDidDamage) {
                currResult = BattleResults.ANNIHILATED;
            } else {
                currResult = BattleResults.LOST;
            }
        } else if (!this.enemy.isAlive() && player.isAlive()) {
            if (!this.enemyDidDamage) {
                currResult = BattleResults.PERFECT;
            } else {
                currResult = BattleResults.WON;
            }
        } else if (!this.enemy.isAlive() && !player.isAlive()) {
            currResult = BattleResults.DRAW;
        } else {
            currResult = BattleResults.IN_PROGRESS;
        }
        return currResult;
    }

    protected final void maintainBuffs() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        player.useBuffs();
        player.cullInactiveBuffs();
        this.enemy.useBuffs();
        this.enemy.cullInactiveBuffs();
    }

    protected final static double powerMove() {
        final double[] multiplier = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1.5, 1.5, 1.5, 1.5, 2, 2,
                2, 3, 3, 4 };
        final RandomRange ms = new RandomRange(0, multiplier.length - 1);
        return multiplier[ms.generate()];
    }

    protected final static int pierce() {
        final int[] addition = { 0, 0, 0, 0, 0, 0, 0, 0, 0,
                MoveTypes.PIERCING_MODIFIER };
        final RandomRange ms = new RandomRange(0, addition.length - 1);
        return addition[ms.generate()];
    }

    protected final void displayActiveBuffs() {
        if (this.result == BattleResults.IN_PROGRESS) {
            final Player player = DungeonDiver.getHoldingBag().getPlayer();
            final String buffString = player.getCompleteBuffString();
            final String buffMessages = player.getAllCurrentBuffMessages();
            final String enemyBuffMessages = this.enemy
                    .getAllCurrentBuffMessages();
            if (!buffString.equals(Buff.getNullBuffString())) {
                this.appendToMessageAreaNewLine(buffString);
            }
            if (!buffMessages.equals(Buff.getNullMessage())) {
                this.appendToMessageAreaNewLine(buffMessages);
            }
            if (!enemyBuffMessages.equals(Buff.getNullMessage())) {
                this.appendToMessageAreaNewLine(enemyBuffMessages);
            }
        }
    }

    protected final void clearMessageArea() {
        this.messageArea.setText("");
    }

    protected final void appendToMessageArea(final String s) {
        this.messageArea.setText(this.messageArea.getText() + s);
    }

    protected final void appendToMessageAreaNewLine(final String s) {
        this.messageArea.setText(this.messageArea.getText() + "\n" + s);
    }

    protected final void firstUpdateMessageArea() {
        this.clearMessageArea();
        this.displayBattleStats();
        this.battleFrame.pack();
    }

    public final void lastUpdateMessageArea() {
        this.clearMessageArea();
        this.displayBattleStats();
        this.displayActiveBuffs();
        this.displayPlayerRoundResults();
        if (this.enemyAction == AIRoutine.ACTION_ATTACK) {
            this.displayEnemyRoundResults();
        }
        this.battleFrame.pack();
    }

    public final void lastUpdateMessageAreaFleeFailed() {
        this.clearMessageArea();
        this.appendToMessageArea(
                "You try to run away, but don't quite make it!\n");
        this.displayBattleStats();
        this.displayActiveBuffs();
        this.displayPlayerRoundResults();
        if (this.enemyAction == AIRoutine.ACTION_ATTACK) {
            this.displayEnemyRoundResults();
        }
        this.battleFrame.pack();
    }

    public final void updateMessageArea() {
        this.clearMessageArea();
        this.computePlayerDamage();
        this.enemyAction = this.enemy.getAI().getNextAction(this.enemy);
        this.enemy.getAI().performAction(this.enemyAction);
        this.displayBattleStats();
        this.displayActiveBuffs();
        this.maintainBuffs();
        this.displayPlayerRoundResults();
        if (this.enemyAction == AIRoutine.ACTION_ATTACK) {
            this.displayEnemyRoundResults();
        }
        this.battleFrame.pack();
    }

    public final boolean doEnemyAttack() {
        this.computeEnemyDamage();
        return true;
    }

    public final boolean doEnemyFlee() {
        this.setResult(BattleResults.ENEMY_FLED);
        this.doResult();
        return true;
    }

    public final boolean doEnemyFleeFailure() {
        this.appendToMessageArea(
                "The enemy tries to run away, but doesn't quite make it!\n");
        return false;
    }

    public final boolean doEnemyCastSpell(final Spell newSpell) {
        return SpellBookManager.castSpell(newSpell, this.enemy);
    }

    public final void updateMessageAreaPostSpellCast() {
        this.clearMessageArea();
        this.enemyAction = this.enemy.getAI().getNextAction(this.enemy);
        this.enemy.getAI().performAction(this.enemyAction);
        this.displayBattleStats();
        this.displayActiveBuffs();
        this.maintainBuffs();
        if (this.enemyAction == AIRoutine.ACTION_ATTACK) {
            this.displayEnemyRoundResults();
        }
        this.battleFrame.pack();
    }

    public final void updateMessageAreaFleeFailed() {
        this.clearMessageArea();
        this.computePlayerDamage();
        this.enemyAction = this.enemy.getAI().getNextAction(this.enemy);
        this.enemy.getAI().performAction(this.enemyAction);
        this.appendToMessageArea(
                "You try to run away, but don't quite make it!\n");
        this.displayBattleStats();
        this.displayActiveBuffs();
        this.maintainBuffs();
        this.displayPlayerRoundResults();
        if (this.enemyAction == AIRoutine.ACTION_ATTACK) {
            this.displayEnemyRoundResults();
        }
        this.battleFrame.pack();
    }

    public void doResult() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        final Monster m = (Monster) this.enemy;
        this.appendToMessageArea("\n");
        if (this.result == BattleResults.WON) {
            this.appendToMessageArea("You gain " + m.getExperience()
                    + " experience and " + m.getGold() + " Gold.");
            player.addExperience(m.getExperience());
            player.addGold(m.getGold());
        } else if (this.result == BattleResults.PERFECT) {
            this.appendToMessageArea("You gain " + m.getExperience()
                    + " experience and " + m.getGold() + " Gold,\nplus "
                    + m.getPerfectBonusGold()
                    + " extra gold for a perfect fight!");
            player.addExperience(m.getExperience());
            player.addGold(m.getGold() + m.getPerfectBonusGold());
        } else if (this.result == BattleResults.LOST) {
            this.appendToMessageArea(
                    "You lose 10% experience and all Gold, but are fully healed.");
            player.defeat();
        } else if (this.result == BattleResults.ANNIHILATED) {
            this.appendToMessageArea(
                    "You lose 20% experience and all Gold for losing without\n"
                            + "hurting your foe, but are fully healed.");
            player.annihilated();
        } else if (this.result == BattleResults.DRAW) {
            this.appendToMessageArea(
                    "The battle was a draw. You are fully healed!");
            player.healPercentage(Creature.FULL_HEAL_PERCENTAGE);
            player.regeneratePercentage(Creature.FULL_HEAL_PERCENTAGE);
        } else if (this.result == BattleResults.FLED) {
            this.appendToMessageArea("You ran away successfully!");
        } else if (this.result == BattleResults.ENEMY_FLED) {
            this.appendToMessageArea("The enemy runs away!\n");
            this.appendToMessageArea(
                    "Since the enemy ran away, you gain nothing for this battle.");
        }
        // Cleanup
        this.attack.setVisible(false);
        this.flee.setVisible(false);
        this.spell.setVisible(false);
        this.done.setVisible(true);
        this.attack.setEnabled(false);
        this.flee.setEnabled(false);
        this.spell.setEnabled(false);
        this.done.setEnabled(true);
        player.stripAllBuffs();
        this.enemy.stripAllBuffs();
        // Level Up Check
        if (player.checkLevelUp()) {
            player.levelUp();
            this.appendToMessageAreaNewLine(
                    "You reached level " + player.getLevel() + ".");
        }
        // Final Cleanup
        this.battleFrame.pack();
    }

    public final void setResult(final int newResult) {
        this.result = newResult;
    }

    public final void battleDone() {
        this.battleFrame.setVisible(false);
        Battle.IN_BATTLE = false;
        DungeonDiver.getHoldingBag().getDungeonGUI().showDungeon();
    }

    public final static boolean isInBattle() {
        return Battle.IN_BATTLE;
    }
}
