/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.abc;

import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;

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