/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.ai;

import studio.ignitionigloogames.twistedtrek.Creature;

public class ZombieAi extends CreatureAi {
	private final Creature player;

	public ZombieAi(final Creature newCreature, final Creature newPlayer) {
		super(newCreature);
		this.player = newPlayer;
	}

	@Override
	public void onUpdate() {
		if (Math.random() < 0.2) {
			return;
		}
		if (this.creature.canSee(this.player.x, this.player.y, this.player.z)) {
			this.hunt(this.player);
		} else {
			this.wander();
		}
	}
}
