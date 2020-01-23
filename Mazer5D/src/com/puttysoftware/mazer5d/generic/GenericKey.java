/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.generic;

public abstract class GenericKey extends GenericInventoryableObject {
    // Fields
    private boolean infinite;

    // Constructors
    protected GenericKey(final boolean infiniteUse) {
        super(false, 0);
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
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public abstract String getName();

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
