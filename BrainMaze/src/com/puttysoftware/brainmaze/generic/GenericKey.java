/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.generic;

public abstract class GenericKey extends GenericInventoryableObject {
    // Fields
    private boolean infinite;

    // Constructors
    protected GenericKey(final boolean infiniteUse) {
        super();
        this.infinite = infiniteUse;
    }

    @Override
    public GenericKey clone() {
        final GenericKey copy = (GenericKey) super.clone();
        copy.infinite = this.infinite;
        return copy;
    }

    public boolean isInfinite() {
        return this.infinite;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_KEY);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
    }

    @Override
    public abstract String getName();
}
