package net.worldwizard.worldz.generic;

import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.world.WorldConstants;

public abstract class GenericDungeonObject extends WorldObject {
    // Constructors
    public GenericDungeonObject(final boolean solid) {
        super(solid);
    }

    // Methods
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        this.postMoveActionHook();
    }

    public void postMoveActionHook() {
        // Do nothing
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return WorldConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_DUNGEON);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return WorldObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
