/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

public abstract class GenericInfiniteLock extends GenericLock {
    protected GenericInfiniteLock(final GenericInfiniteKey mgk) {
        super(mgk);
        this.setType(TypeConstants.TYPE_INFINITE_LOCK);
    }

    protected GenericInfiniteLock(final GenericInfiniteKey mgk,
            final boolean doesAcceptPushInto) {
        super(mgk, doesAcceptPushInto);
        this.setType(TypeConstants.TYPE_INFINITE_LOCK);
    }

    protected GenericInfiniteLock(final boolean isSolid, final GenericKey mgk) {
        super(isSolid, mgk);
        this.setType(TypeConstants.TYPE_INFINITE_LOCK);
    }
}