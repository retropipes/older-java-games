/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

public abstract class GenericSingleKey extends GenericKey {
    // Constructors
    protected GenericSingleKey() {
        super(false);
    }

    @Override
    public byte getGroupID() {
        return (byte) 6;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_SINGLE_KEY);
        this.type.set(TypeConstants.TYPE_KEY);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }
}