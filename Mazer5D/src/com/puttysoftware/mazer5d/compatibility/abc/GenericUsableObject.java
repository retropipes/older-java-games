/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.abc;

import com.puttysoftware.mazer5d.objectmodel.Layers;

public abstract class GenericUsableObject extends GenericInventoryableObject {
    // Constructors
    protected GenericUsableObject(final int newUses) {
        super(true, newUses);
    }

    @Override
    public abstract void useAction(MazeObjectModel mo, int x, int y, int z);

    @Override
    public abstract String getName();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_USABLE);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public int getLayer() {
        return Layers.OBJECT;
    }

    @Override
    public abstract void useHelper(int x, int y, int z);
}
