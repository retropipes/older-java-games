package studio.ignitionigloogames.dungeondiver1.creatures;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;

public class BossBattle extends Battle {
    // Fields
    protected final BossRewards rewards;

    // Constructor
    public BossBattle() {
        this.rewards = new BossRewards();
    }

    // Method
    @Override
    public void doBattle() {
        Battle.IN_BATTLE = true;
        DungeonDiver.getHoldingBag().getDungeonGUI().hideDungeon();
        this.enemy = new Boss();
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

    @Override
    public void doResult() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        this.appendToMessageArea("\n");
        // Cleanup
        player.stripAllBuffs();
        this.attack.setVisible(false);
        this.flee.setVisible(false);
        this.spell.setVisible(false);
        this.done.setVisible(true);
        this.attack.setEnabled(false);
        this.flee.setEnabled(false);
        this.spell.setEnabled(false);
        this.done.setEnabled(true);
        if (this.result == BattleResults.WON) {
            this.appendToMessageArea("You beat the Boss!");
            this.rewards.doRewards();
            this.battleDone();
            DungeonDiver.getHoldingBag().getDungeonGUI().newDungeonAndScheme();
        } else if (this.result == BattleResults.PERFECT) {
            this.appendToMessageArea(
                    "You beat the Boss, and didn't suffer any damage!");
            this.rewards.doRewards();
            this.battleDone();
            DungeonDiver.getHoldingBag().getDungeonGUI().newDungeonAndScheme();
        } else if (this.result == BattleResults.LOST) {
            this.appendToMessageArea("The Boss beat you...");
            player.healPercentage(Creature.FULL_HEAL_PERCENTAGE);
        } else if (this.result == BattleResults.ANNIHILATED) {
            this.appendToMessageArea(
                    "The Boss beat you... and you didn't even hurt him!");
            player.healPercentage(Creature.FULL_HEAL_PERCENTAGE);
        } else if (this.result == BattleResults.DRAW) {
            this.appendToMessageArea("The battle was a draw!");
            player.healPercentage(Creature.FULL_HEAL_PERCENTAGE);
        } else if (this.result == BattleResults.FLED) {
            this.appendToMessageArea("Come back when you're ready...");
            player.healPercentage(Creature.FULL_HEAL_PERCENTAGE);
        }
        this.battleFrame.pack();
    }
}