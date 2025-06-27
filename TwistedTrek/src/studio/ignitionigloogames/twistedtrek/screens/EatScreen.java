/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.screens;

import studio.ignitionigloogames.twistedtrek.Creature;
import studio.ignitionigloogames.twistedtrek.Item;

public class EatScreen extends InventoryBasedScreen {
    public EatScreen(final Creature newPlayer) {
        super(newPlayer);
    }

    @Override
    protected String getVerb() {
        return "eat";
    }

    @Override
    protected boolean isAcceptable(final Item item) {
        return item.foodValue() != 0;
    }

    @Override
    protected Screen use(final Item item) {
        this.player.eat(item);
        return null;
    }
}
