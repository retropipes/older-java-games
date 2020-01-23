/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

public abstract class GenericInfiniteKey extends GenericKey {
    // Constructors
    protected GenericInfiniteKey() {
	super(true);
    }

    // Scriptability
    @Override
    public abstract String getName();

    @Override
    public byte getGroupID() {
	return (byte) 8;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_INFINITE_KEY);
	this.type.set(TypeConstants.TYPE_KEY);
	this.type.set(TypeConstants.TYPE_INVENTORYABLE);
	this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }
}