/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.screens;

import studio.ignitionigloogames.twistedtrek.Creature;
import studio.ignitionigloogames.twistedtrek.Item;

public class ThrowScreen extends InventoryBasedScreen {
    private final int sx;
    private final int sy;

    public ThrowScreen(final Creature newPlayer, final int nsx, final int nsy) {
	super(newPlayer);
	this.sx = nsx;
	this.sy = nsy;
    }

    @Override
    protected String getVerb() {
	return "throw";
    }

    @Override
    protected boolean isAcceptable(final Item item) {
	return true;
    }

    @Override
    protected Screen use(final Item item) {
	return new ThrowAtScreen(this.player, this.sx, this.sy, item);
    }
}
