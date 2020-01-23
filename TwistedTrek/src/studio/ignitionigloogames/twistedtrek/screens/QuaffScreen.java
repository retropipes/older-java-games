/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.screens;

import studio.ignitionigloogames.twistedtrek.Creature;
import studio.ignitionigloogames.twistedtrek.Item;

public class QuaffScreen extends InventoryBasedScreen {
    public QuaffScreen(final Creature newPlayer) {
	super(newPlayer);
    }

    @Override
    protected String getVerb() {
	return "quaff";
    }

    @Override
    protected boolean isAcceptable(final Item item) {
	return item.quaffEffect() != null;
    }

    @Override
    protected Screen use(final Item item) {
	this.player.quaff(item);
	return null;
    }
}
