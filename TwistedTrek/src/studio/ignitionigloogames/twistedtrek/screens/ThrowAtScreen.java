/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.screens;

import studio.ignitionigloogames.twistedtrek.Creature;
import studio.ignitionigloogames.twistedtrek.Item;
import studio.ignitionigloogames.twistedtrek.Line;
import studio.ignitionigloogames.twistedtrek.Point;

public class ThrowAtScreen extends TargetBasedScreen {
    private final Item item;

    public ThrowAtScreen(final Creature newPlayer, final int sx, final int sy, final Item newItem) {
	super(newPlayer, "Throw " + newPlayer.nameOf(newItem) + " at?", sx, sy);
	this.item = newItem;
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
	this.player.throwItem(this.item, nx, ny, this.player.z);
    }
}
