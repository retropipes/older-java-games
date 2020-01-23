/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.abc;

import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;

public abstract class AbstractInfiniteLock extends AbstractLock {
    protected AbstractInfiniteLock(final AbstractInfiniteKey mgk) {
        super(mgk);
    }

    protected AbstractInfiniteLock(final AbstractInfiniteKey mgk,
            final boolean doesAcceptPushInto) {
        super(mgk, doesAcceptPushInto);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_INFINITE_LOCK);
        this.type.set(TypeConstants.TYPE_LOCK);
    }
}