/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.battle;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.Boss;
import studio.ignitionigloogames.twistedtrek.import1.creatures.Creature;
import studio.ignitionigloogames.twistedtrek.import1.creatures.PCManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.PlayerCharacter;
import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.SoundManager;

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
	Import1.getApplication().getGameManager().hideOutput();
	if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_BATTLE)) {
	    SoundManager.play("battle");
	}
	this.enemy = new Boss();
	this.iconLabel.setIcon(this.enemy.getImage());
	this.enemyDidDamage = false;
	this.playerDidDamage = false;
	this.setResult(BattleResults.IN_PROGRESS);
	this.attack.setVisible(true);
	this.flee.setVisible(true);
	this.spell.setVisible(true);
	this.steal.setVisible(true);
	this.drain.setVisible(true);
	this.item.setVisible(true);
	this.done.setVisible(false);
	this.attack.setEnabled(true);
	this.flee.setEnabled(true);
	this.spell.setEnabled(true);
	this.steal.setEnabled(false);
	this.drain.setEnabled(false);
	this.item.setEnabled(true);
	this.done.setEnabled(false);
	this.firstUpdateMessageArea();
	this.battleFrame.setVisible(true);
    }

    @Override
    public void doResult() {
	final PlayerCharacter playerCharacter = PCManager.getPlayer();
	this.appendToMessageArea("\n");
	// Cleanup
	playerCharacter.stripAllEffects();
	this.attack.setVisible(false);
	this.flee.setVisible(false);
	this.spell.setVisible(false);
	this.steal.setVisible(false);
	this.drain.setVisible(false);
	this.item.setVisible(false);
	this.done.setVisible(true);
	this.attack.setEnabled(false);
	this.flee.setEnabled(false);
	this.spell.setEnabled(false);
	this.item.setEnabled(false);
	this.done.setEnabled(true);
	if (this.result == BattleResults.WON) {
	    this.appendToMessageArea("You beat the Boss!");
	    this.rewards.doRewards();
	    this.battleDone();
	} else if (this.result == BattleResults.PERFECT) {
	    this.appendToMessageArea("You beat the Boss, and didn't suffer any damage!");
	    this.rewards.doRewards();
	    this.battleDone();
	} else if (this.result == BattleResults.LOST) {
	    this.appendToMessageArea("The Boss beat you...");
	    playerCharacter.healPercentage(Creature.FULL_HEAL_PERCENTAGE);
	} else if (this.result == BattleResults.ANNIHILATED) {
	    this.appendToMessageArea("The Boss beat you... and you didn't even hurt him!");
	    playerCharacter.healPercentage(Creature.FULL_HEAL_PERCENTAGE);
	} else if (this.result == BattleResults.DRAW) {
	    this.appendToMessageArea("The battle was a draw!");
	    playerCharacter.healPercentage(Creature.FULL_HEAL_PERCENTAGE);
	} else if (this.result == BattleResults.FLED) {
	    this.appendToMessageArea("Come back when you're ready...");
	    playerCharacter.healPercentage(Creature.FULL_HEAL_PERCENTAGE);
	}
	this.battleFrame.pack();
    }
}