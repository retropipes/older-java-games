/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.screens;

import studio.ignitionigloogames.twistedtrek.Creature;
import studio.ignitionigloogames.twistedtrek.Item;

public class DropScreen extends InventoryBasedScreen {
    public DropScreen(final Creature newPlayer) {
        super(newPlayer);
    }

    @Override
    protected String getVerb() {
        return "drop";
    }

    @Override
    protected boolean isAcceptable(final Item item) {
        return true;
    }

    @Override
    protected Screen use(final Item item) {
        this.player.drop(item);
        return null;
    }
}
