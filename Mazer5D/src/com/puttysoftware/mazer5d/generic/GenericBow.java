/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.generic;

public abstract class GenericBow extends GenericUsableObject {
    // Fields
    private final int AT;

    // Constructors
    protected GenericBow(final int uses, final int arrowType) {
        super(uses);
        this.AT = arrowType;
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        // Do nothing
    }

    @Override
    public void useAction(final MazeObject mo, final int x, final int y,
            final int z) {
        // Do nothing
    }

    @Override
    public abstract String getName();

    public int getArrowType() {
        return this.AT;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_BOW);
        this.type.set(TypeConstants.TYPE_USABLE);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }
}
