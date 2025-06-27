/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.ai;

import studio.ignitionigloogames.randomrange.RandomRange;
import studio.ignitionigloogames.twistedtrek.import1.creatures.Creature;
import studio.ignitionigloogames.twistedtrek.import1.spells.Spell;

public class HealIfHealthLowAIRoutine extends AIRoutine {
	// Constants
	private static final int HEAL_CHANCE = 90;
	private static final double HEAL_PERCENT = 0.2;

	@Override
	public int getNextAction(final Creature c) {
		final Spell heal = c.getSpellBook().getSpellByID(0);
		final int cost = heal.getCost();
		final int currMP = c.getCurrentMP();
		if (cost <= currMP) {
			final int currHP = c.getCurrentHP();
			final int targetHP = (int) (currHP * HealIfHealthLowAIRoutine.HEAL_PERCENT);
			if (currHP <= targetHP) {
				final RandomRange chance = new RandomRange(1, 100);
				if (chance.generate() <= HealIfHealthLowAIRoutine.HEAL_CHANCE) {
					this.spell = heal;
					return AIRoutine.ACTION_CAST_SPELL;
				} else {
					this.spell = null;
					return AIRoutine.ACTION_ATTACK;
				}
			} else {
				this.spell = null;
				return AIRoutine.ACTION_ATTACK;
			}
		} else {
			this.spell = null;
			return AIRoutine.ACTION_ATTACK;
		}
	}
}
