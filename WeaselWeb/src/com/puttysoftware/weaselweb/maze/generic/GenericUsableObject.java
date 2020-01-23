/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

import com.puttysoftware.weaselweb.maze.MazeConstants;

public abstract class GenericUsableObject extends GenericInventoryableObject {
    // Constructors
    protected GenericUsableObject(final int newUses) {
        super(true, newUses);
        this.setType(TypeConstants.TYPE_USABLE);
    }

    @Override
    public abstract void useAction(MazeObject mo, int x, int y, int z);

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    public abstract void useHelper(int x, int y, int z);
}
