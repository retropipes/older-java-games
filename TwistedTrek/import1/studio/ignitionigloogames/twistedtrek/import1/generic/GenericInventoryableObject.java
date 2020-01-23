/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public abstract class GenericInventoryableObject extends MazeObject {
    // Constructors
    protected GenericInventoryableObject(final boolean isUsable, final int newUses) {
	super(false, isUsable, newUses, true);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	inv.addItem(this);
	final Application app = Import1.getApplication();
	app.getGameManager().decay();
	// Play grab sound, if it's enabled
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playMoveSuccessSound();
	}
    }

    @Override
    public byte getGroupID() {
	return (byte) 12;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_INVENTORYABLE);
	this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public int getLayer() {
	return Maze.LAYER_OBJECT;
    }

    @Override
    public String getMoveSuccessSoundName() {
	return "grab";
    }
}
