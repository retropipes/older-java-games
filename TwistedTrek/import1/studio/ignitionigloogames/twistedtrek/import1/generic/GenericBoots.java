/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

public abstract class GenericBoots extends GenericPass {
    // Constructors
    protected GenericBoots() {
        super();
    }

    @Override
    public abstract String getName();

    public final String getBootsName() {
        final String name = this.getName();
        return name.substring(0, name.length() - 6);
    }

    @Override
    public byte getGroupID() {
        return (byte) 22;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_BOOTS);
        this.type.set(TypeConstants.TYPE_PASS);
        this.type.set(TypeConstants.TYPE_INFINITE_KEY);
        this.type.set(TypeConstants.TYPE_KEY);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }
}
