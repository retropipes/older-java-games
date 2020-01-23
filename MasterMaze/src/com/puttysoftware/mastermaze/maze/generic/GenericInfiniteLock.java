/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.generic;

public abstract class GenericInfiniteLock extends GenericLock {
    protected GenericInfiniteLock(final GenericInfiniteKey mgk) {
        super(mgk);
    }

    protected GenericInfiniteLock(final GenericInfiniteKey mgk,
            final boolean doesAcceptPushInto) {
        super(mgk, doesAcceptPushInto);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_INFINITE_LOCK);
        this.type.set(TypeConstants.TYPE_LOCK);
    }
}