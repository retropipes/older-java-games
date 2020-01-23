/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public abstract class GenericSingleKey extends GenericKey {
    // Constructors
    protected GenericSingleKey(final int tc) {
        super(false);
        this.setTemplateColor(tc);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_SINGLE_KEY);
        this.type.set(TypeConstants.TYPE_KEY);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_KEY;
    }
}