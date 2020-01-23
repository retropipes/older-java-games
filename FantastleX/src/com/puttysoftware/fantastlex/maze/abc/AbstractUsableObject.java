/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.abc;

import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;

public abstract class AbstractUsableObject extends AbstractInventoryableObject {
    // Constructors
    protected AbstractUsableObject(final int newUses) {
        super(true, newUses);
    }

    @Override
    public abstract void useAction(AbstractMazeObject mo, int x, int y, int z);

    @Override
    public abstract String getName();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_USABLE);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public abstract void useHelper(int x, int y, int z);
}
