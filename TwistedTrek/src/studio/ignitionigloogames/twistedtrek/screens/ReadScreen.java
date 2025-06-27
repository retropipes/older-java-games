/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.screens;

import studio.ignitionigloogames.twistedtrek.Creature;
import studio.ignitionigloogames.twistedtrek.Item;

public class ReadScreen extends InventoryBasedScreen {
    private final int sx;
    private final int sy;

    public ReadScreen(final Creature newPlayer, final int nsx, final int nsy) {
        super(newPlayer);
        this.sx = nsx;
        this.sy = nsy;
    }

    @Override
    protected String getVerb() {
        return "read";
    }

    @Override
    protected boolean isAcceptable(final Item item) {
        return !item.writtenSpells().isEmpty();
    }

    @Override
    protected Screen use(final Item item) {
        return new ReadSpellScreen(this.player, this.sx, this.sy, item);
    }
}
