/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.ai;

import java.util.List;

import studio.ignitionigloogames.twistedtrek.Creature;
import studio.ignitionigloogames.twistedtrek.FieldOfView;
import studio.ignitionigloogames.twistedtrek.Item;
import studio.ignitionigloogames.twistedtrek.world.Tile;

public class PlayerAi extends CreatureAi {
    private final List<String> messages;
    private final FieldOfView fov;

    public PlayerAi(final Creature newCreature, final List<String> newMessages, final FieldOfView newFov) {
        super(newCreature);
        this.messages = newMessages;
        this.fov = newFov;
    }

    @Override
    public void onEnter(final int x, final int y, final int z, final Tile tile) {
        if (tile.isGround()) {
            this.creature.x = x;
            this.creature.y = y;
            this.creature.z = z;
            final Item item = this.creature.item(this.creature.x, this.creature.y, this.creature.z);
            if (item != null) {
                this.creature.notify("There's a " + this.creature.nameOf(item) + " here.");
            }
        } else if (tile.isDiggable()) {
            this.creature.dig(x, y, z);
        }
    }

    @Override
    public void onNotify(final String message) {
        this.messages.add(message);
    }

    @Override
    public boolean canSee(final int wx, final int wy, final int wz) {
        return this.fov.isVisible(wx, wy, wz);
    }

    @Override
    public void onGainLevel() {
    }

    @Override
    public Tile rememberedTile(final int wx, final int wy, final int wz) {
        return this.fov.tile(wx, wy, wz);
    }
}
