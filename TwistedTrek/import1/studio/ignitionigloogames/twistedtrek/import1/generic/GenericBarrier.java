/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.PCManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public abstract class GenericBarrier extends GenericWall {
    // Fields
    private static final int BARRIER_DAMAGE = 10;

    // Constructors
    protected GenericBarrier() {
	super();
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	// Display impassable barrier message
	final Application app = Import1.getApplication();
	Messager.showMessage("The barrier is impassable!");
	// Play move failed sound, if it's enabled
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playMoveFailedSound();
	}
	// Hurt the player a little for attempting to cross the barrier
	PCManager.getPlayer().doDamagePercentage(GenericBarrier.BARRIER_DAMAGE);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
	return Maze.LAYER_OBJECT;
    }

    @Override
    public byte getGroupID() {
	return (byte) 31;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_BARRIER);
	this.type.set(TypeConstants.TYPE_WALL);
    }

    @Override
    public String getMoveFailedSoundName() {
	return "barrier";
    }
}