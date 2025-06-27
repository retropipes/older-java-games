/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.screens;

import studio.ignitionigloogames.twistedtrek.Creature;
import studio.ignitionigloogames.twistedtrek.Line;
import studio.ignitionigloogames.twistedtrek.Point;

public class FireWeaponScreen extends TargetBasedScreen {
	public FireWeaponScreen(final Creature newPlayer, final int sx, final int sy) {
		super(newPlayer, "Fire " + newPlayer.nameOf(newPlayer.weapon()) + " at?", sx, sy);
	}

	@Override
	public boolean isAcceptable(final int nx, final int ny) {
		if (!this.player.canSee(nx, ny, this.player.z)) {
			return false;
		}
		for (final Point p : new Line(this.player.x, this.player.y, nx, ny)) {
			if (!this.player.realTile(p.x, p.y, this.player.z).isGround()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void selectWorldCoordinate(final int nx, final int ny, final int screenX, final int screenY) {
		final Creature other = this.player.creature(nx, ny, this.player.z);
		if (other == null) {
			this.player.notify("There's no one there to fire at.");
		} else {
			this.player.rangedWeaponAttack(other);
		}
	}
}
