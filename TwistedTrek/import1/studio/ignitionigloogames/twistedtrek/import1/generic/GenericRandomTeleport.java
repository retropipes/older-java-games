/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import java.util.Random;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.editor.MazeEditor;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public abstract class GenericRandomTeleport extends MazeObject {
    // Fields
    private int randomRangeX;
    private int randomRangeY;
    private final Random generator;

    // Constructors
    public GenericRandomTeleport(final int newRandomRangeY, final int newRandomRangeX) {
	super(false);
	this.randomRangeX = newRandomRangeX;
	this.randomRangeY = newRandomRangeY;
	this.generator = new Random();
    }

    @Override
    public boolean equals(final Object obj) {
	if (obj == null) {
	    return false;
	}
	if (this.getClass() != obj.getClass()) {
	    return false;
	}
	final GenericRandomTeleport other = (GenericRandomTeleport) obj;
	if (this.randomRangeX != other.randomRangeX) {
	    return false;
	}
	if (this.randomRangeY != other.randomRangeY) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 89 * hash + this.randomRangeX;
	hash = 89 * hash + this.randomRangeY;
	return hash;
    }

    @Override
    public GenericRandomTeleport clone() {
	final GenericRandomTeleport copy = (GenericRandomTeleport) super.clone();
	copy.randomRangeX = this.randomRangeX;
	copy.randomRangeY = this.randomRangeY;
	return copy;
    }

    // Methods
    public int getDestinationRow() {
	if (this.randomRangeY == 0) {
	    return 0;
	} else {
	    int sign = this.generator.nextInt(2);
	    final int value = this.generator.nextInt(this.randomRangeY + 1);
	    if (sign == 0) {
		sign = -1;
	    }
	    return sign * value;
	}
    }

    public int getDestinationColumn() {
	if (this.randomRangeX == 0) {
	    return 0;
	} else {
	    int sign = this.generator.nextInt(2);
	    final int value = this.generator.nextInt(this.randomRangeX + 1);
	    if (sign == 0) {
		sign = -1;
	    }
	    return sign * value;
	}
    }

    public int getDestinationFloor() {
	final Application app = Import1.getApplication();
	return app.getGameManager().getPlayerManager().getPlayerLocationZ();
    }

    public int getDestinationLevel() {
	final Application app = Import1.getApplication();
	return app.getGameManager().getPlayerManager().getPlayerLocationW();
    }

    public int getRandomRowRange() {
	return this.randomRangeY;
    }

    public int getRandomColumnRange() {
	return this.randomRangeX;
    }

    @Override
    public int getLayer() {
	return Maze.LAYER_OBJECT;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	final Application app = Import1.getApplication();
	int dr, dc;
	do {
	    dr = this.getDestinationRow();
	    dc = this.getDestinationColumn();
	} while (!app.getGameManager().tryUpdatePositionRelative(dr, dc));
	app.getGameManager().updatePositionRelative(dr, dc);
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playMoveSuccessSound();
	}
    }

    @Override
    public void editorProbeHook() {
	Messager.showMessage(
		this.getName() + ": Row Radius " + this.randomRangeY + ", Column Radius " + this.randomRangeX);
    }

    @Override
    public MazeObject editorPropertiesHook() {
	final MazeEditor me = Import1.getApplication().getEditor();
	final MazeObject mo = me.editTeleportDestination(MazeEditor.TELEPORT_TYPE_RANDOM);
	return mo;
    }

    @Override
    public byte getGroupID() {
	return (byte) 16;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_RANDOM_TELEPORT);
    }

    @Override
    public String getMoveSuccessSoundName() {
	return "teleport";
    }

    @Override
    public boolean hasAdditionalProperties() {
	return true;
    }

    @Override
    public int getCustomProperty(final int propID) {
	switch (propID) {
	case 1:
	    return this.randomRangeX;
	case 2:
	    return this.randomRangeY;
	default:
	    return MazeObject.DEFAULT_CUSTOM_VALUE;
	}
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
	switch (propID) {
	case 1:
	    this.randomRangeX = value;
	    break;
	case 2:
	    this.randomRangeY = value;
	    break;
	default:
	    break;
	}
    }

    @Override
    public int getCustomFormat() {
	return 2;
    }
}
