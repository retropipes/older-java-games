/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.screens;

import studio.ignitionigloogames.twistedtrek.Creature;
import studio.ignitionigloogames.twistedtrek.Item;

public class EquipScreen extends InventoryBasedScreen {
    public EquipScreen(final Creature newPlayer) {
        super(newPlayer);
    }

    @Override
    protected String getVerb() {
        return "wear or wield";
    }

    @Override
    protected boolean isAcceptable(final Item item) {
        return item.attackValue() > 0 || item.defenseValue() > 0;
    }

    @Override
    protected Screen use(final Item item) {
        this.player.equip(item);
        return null;
    }
}
