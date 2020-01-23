/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

public abstract class GenericInfiniteLock extends GenericLock {
    protected GenericInfiniteLock(final GenericInfiniteKey mgk) {
	super(mgk);
    }

    protected GenericInfiniteLock(final GenericInfiniteKey mgk, final boolean doesAcceptPushInto) {
	super(mgk, doesAcceptPushInto);
    }

    protected GenericInfiniteLock(final boolean isSolid, final GenericKey mgk) {
	super(isSolid, mgk);
    }

    @Override
    public byte getGroupID() {
	return (byte) 9;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_INFINITE_LOCK);
	this.type.set(TypeConstants.TYPE_LOCK);
    }
}