/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public abstract class GenericButton extends MazeObject {
    // Fields
    private GenericToggleWall offState;
    private GenericToggleWall onState;

    // Constructors
    protected GenericButton(final GenericToggleWall off, final GenericToggleWall on) {
	super(false);
	this.offState = off;
	this.onState = on;
    }

    @Override
    public boolean equals(final Object obj) {
	if (obj == null) {
	    return false;
	}
	if (this.getClass() != obj.getClass()) {
	    return false;
	}
	final GenericButton other = (GenericButton) obj;
	if (this.offState != other.offState && (this.offState == null || !this.offState.equals(other.offState))) {
	    return false;
	}
	if (this.onState != other.onState && (this.onState == null || !this.onState.equals(other.onState))) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 13 * hash + (this.offState != null ? this.offState.hashCode() : 0);
	hash = 13 * hash + (this.onState != null ? this.onState.hashCode() : 0);
	return hash;
    }

    @Override
    public GenericButton clone() {
	final GenericButton copy = (GenericButton) super.clone();
	copy.offState = (GenericToggleWall) this.offState.clone();
	copy.onState = (GenericToggleWall) this.onState.clone();
	return copy;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	final int currLevel = Import1.getApplication().getGameManager().getPlayerManager().getPlayerLocationW();
	Import1.getApplication().getMazeManager().getMaze().findAllObjectPairsAndSwap(currLevel, this.offState,
		this.onState);
	Import1.getApplication().getGameManager().redrawMazeNoRebuild();
	if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    MazeObject.playButtonSound();
	}
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY, final int locZ, final int locW, final int dirX,
	    final int dirY, final int arrowType, final ObjectInventory inv) {
	// Behave as if the button was stepped on
	this.postMoveAction(false, dirX, dirY, inv);
	return false;
    }

    @Override
    public abstract String getName();

    @Override
    public byte getGroupID() {
	return (byte) 24;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_BUTTON);
    }

    @Override
    public int getLayer() {
	return Maze.LAYER_OBJECT;
    }

    @Override
    public String getMoveSuccessSoundName() {
	return "button";
    }

    @Override
    public int getCustomProperty(final int propID) {
	return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
	// Do nothing
    }
}