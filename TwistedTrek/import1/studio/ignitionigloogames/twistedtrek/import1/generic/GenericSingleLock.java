/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

public abstract class GenericSingleLock extends GenericLock {
    protected GenericSingleLock(final GenericSingleKey mgk) {
	super(mgk);
    }

    // Scriptability
    @Override
    public byte getGroupID() {
	return (byte) 7;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_SINGLE_LOCK);
	this.type.set(TypeConstants.TYPE_LOCK);
    }
}