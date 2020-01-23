/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

public abstract class GenericPass extends GenericInfiniteKey {
    // Constructors
    protected GenericPass() {
	super();
    }

    @Override
    public abstract String getName();

    @Override
    public byte getGroupID() {
	return (byte) 13;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_PASS);
	this.type.set(TypeConstants.TYPE_INFINITE_KEY);
	this.type.set(TypeConstants.TYPE_KEY);
	this.type.set(TypeConstants.TYPE_INVENTORYABLE);
	this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }
}
