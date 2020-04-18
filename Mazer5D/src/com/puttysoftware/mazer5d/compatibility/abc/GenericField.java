/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.abc;

import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.objectmodel.Layers;

public abstract class GenericField extends GenericInfiniteLock {
    // Constructors
    protected GenericField(final GenericPass mgp) {
        super(mgp);
    }

    protected GenericField(final GenericPass mgp,
            final boolean doesAcceptPushInto) {
        super(mgp, doesAcceptPushInto);
    }

    protected GenericField(final boolean isSolid, final GenericPass mgp) {
        super(isSolid, mgp);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        // Do nothing
    }

    @Override
    public boolean isConditionallySolid(final ObjectInventory inv) {
        return !inv.isItemThere(this.getKey());
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final ObjectInventory inv) {
        return !inv.isItemThere(this.getKey());
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return Layers.GROUND;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_FIELD);
        this.type.set(TypeConstants.TYPE_INFINITE_LOCK);
        this.type.set(TypeConstants.TYPE_LOCK);
    }
}
