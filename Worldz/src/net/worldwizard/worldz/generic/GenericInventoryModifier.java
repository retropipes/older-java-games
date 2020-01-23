/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.world.WorldConstants;

public abstract class GenericInventoryModifier extends WorldObject {
    // Constructors
    protected GenericInventoryModifier() {
        super(false);
    }

    @Override
    public abstract void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv);

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_INVENTORY_MODIFIER);
    }

    @Override
    public int getLayer() {
        return WorldConstants.LAYER_OBJECT;
    }

    @Override
    public String getMoveSuccessSoundName() {
        return "grab";
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
