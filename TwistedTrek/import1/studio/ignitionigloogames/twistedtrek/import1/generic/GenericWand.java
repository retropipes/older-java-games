/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;

public abstract class GenericWand extends GenericUsableObject {
    // Constructors
    protected GenericWand() {
        super(1);
    }

    @Override
    public abstract String getName();

    @Override
    public void useAction(final MazeObject mo, final int x, final int y, final int z, final int w) {
        final Application app = Import1.getApplication();
        app.getGameManager().morph(mo, x, y, z, w);
    }

    @Override
    public abstract void useHelper(int x, int y, int z, int w);

    @Override
    public byte getGroupID() {
        return (byte) 20;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WAND);
        this.type.set(TypeConstants.TYPE_USABLE);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }
}
