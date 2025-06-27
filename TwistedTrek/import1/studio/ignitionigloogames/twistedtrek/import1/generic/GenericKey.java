/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

public abstract class GenericKey extends GenericInventoryableObject {
    // Fields
    private boolean infinite;

    // Constructors
    protected GenericKey(final boolean infiniteUse) {
        super(false, 0);
        this.infinite = infiniteUse;
    }

    @Override
    public GenericKey clone() {
        final GenericKey copy = (GenericKey) super.clone();
        copy.infinite = this.infinite;
        return copy;
    }

    public boolean isInfinite() {
        return this.infinite;
    }

    @Override
    public byte getGroupID() {
        return (byte) 4;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_KEY);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public abstract String getName();

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
