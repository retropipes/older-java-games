/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

public abstract class GenericInfiniteLock extends GenericLock {
    protected GenericInfiniteLock(final GenericInfiniteKey mgk) {
        super(mgk);
    }

    protected GenericInfiniteLock(final GenericInfiniteKey mgk,
            final boolean doesAcceptPushInto) {
        super(mgk, doesAcceptPushInto);
    }

    protected GenericInfiniteLock(final boolean isSolid, final GenericKey mgk) {
        super(isSolid, mgk);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_INFINITE_LOCK);
        this.type.set(TypeConstants.TYPE_LOCK);
    }
}