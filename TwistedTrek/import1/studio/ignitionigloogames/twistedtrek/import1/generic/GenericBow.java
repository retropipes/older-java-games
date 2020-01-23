/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;

public abstract class GenericBow extends GenericUsableObject {
    // Constructors
    protected GenericBow(final int uses) {
	super(uses);
    }

    @Override
    public abstract String getName();

    @Override
    public void useAction(final MazeObject mo, final int x, final int y, final int z, final int w) {
	Import1.getApplication().getGameManager().setArrowType(ArrowTypeConstants.ARROW_TYPE_ICE);
	Import1.getApplication().getGameManager().keepNextMessage();
	Messager.showMessage(this.getName() + " activated.");
    }

    @Override
    public void useHelper(final int x, final int y, final int z, final int w) {
	this.useAction(null, x, y, z, w);
    }

    @Override
    public byte getGroupID() {
	return (byte) 0;
    }

    @Override
    public int getCustomProperty(final int propID) {
	return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
	// Do nothing
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_BOW);
	this.type.set(TypeConstants.TYPE_USABLE);
	this.type.set(TypeConstants.TYPE_INVENTORYABLE);
	this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }
}
