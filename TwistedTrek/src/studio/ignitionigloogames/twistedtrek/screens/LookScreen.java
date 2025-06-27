/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.screens;

import studio.ignitionigloogames.twistedtrek.Creature;
import studio.ignitionigloogames.twistedtrek.Item;
import studio.ignitionigloogames.twistedtrek.world.Tile;

public class LookScreen extends TargetBasedScreen {
	public LookScreen(final Creature newPlayer, final String newCaption, final int sx, final int sy) {
		super(newPlayer, newCaption, sx, sy);
	}

	@Override
	public void enterWorldCoordinate(final int nx, final int ny, final int screenX, final int screenY) {
		final Creature creature = this.player.creature(nx, ny, this.player.z);
		if (creature != null) {
			this.caption = creature.name() + creature.details();
			return;
		}
		final Item item = this.player.item(nx, ny, this.player.z);
		if (item != null) {
			this.caption = this.player.nameOf(item) + item.details();
			return;
		}
		final Tile tile = this.player.tile(nx, ny, this.player.z);
		this.caption = tile.details();
	}
}
