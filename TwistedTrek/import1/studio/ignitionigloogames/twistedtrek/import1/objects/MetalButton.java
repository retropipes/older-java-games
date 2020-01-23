/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericField;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.generic.TypeConstants;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;
import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.SoundManager;

public class MetalButton extends GenericField {
    // Fields
    private final int targetRow;
    private final int targetCol;
    private final int targetFloor;
    private final int targetLevel;

    // Constructors
    public MetalButton() {
	super(false, new MetalBoots());
	this.targetRow = 0;
	this.targetCol = 0;
	this.targetFloor = 0;
	this.targetLevel = 0;
    }

    public MetalButton(final int tRow, final int tCol, final int tFloor, final int tLevel) {
	super(false, new MetalBoots());
	this.targetRow = tRow;
	this.targetCol = tCol;
	this.targetFloor = tFloor;
	this.targetLevel = tLevel;
    }

    @Override
    public boolean equals(final Object obj) {
	if (obj == null) {
	    return false;
	}
	if (this.getClass() != obj.getClass()) {
	    return false;
	}
	final MetalButton other = (MetalButton) obj;
	if (this.targetRow != other.targetRow) {
	    return false;
	}
	if (this.targetCol != other.targetCol) {
	    return false;
	}
	if (this.targetFloor != other.targetFloor) {
	    return false;
	}
	if (this.targetLevel != other.targetLevel) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 79 * hash + this.targetRow;
	hash = 79 * hash + this.targetCol;
	hash = 79 * hash + this.targetFloor;
	hash = 79 * hash + this.targetLevel;
	return hash;
    }

    public int getTargetRow() {
	return this.targetRow;
    }

    public int getTargetColumn() {
	return this.targetCol;
    }

    public int getTargetFloor() {
	return this.targetFloor;
    }

    public int getTargetLevel() {
	return this.targetLevel;
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	// Do nothing
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	if (inv.isItemThere(this.getKey())) {
	    final Application app = Import1.getApplication();
	    final MazeObject there = app.getMazeManager().getMazeObject(this.getTargetRow(), this.getTargetColumn(),
		    this.getTargetFloor(), this.getTargetLevel(), this.getLayer());
	    if (there != null) {
		if (there.getName().equals(new MetalDoor().getName())) {
		    app.getGameManager().morph(new Empty(), this.getTargetRow(), this.getTargetColumn(),
			    this.getTargetFloor(), this.getTargetLevel());
		} else {
		    app.getGameManager().morph(new MetalDoor(), this.getTargetRow(), this.getTargetColumn(),
			    this.getTargetFloor(), this.getTargetLevel());
		}
	    }
	    if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
		this.playMoveSuccessSound();
	    }
	} else {
	    if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
		SoundManager.play("walk");
	    }
	}
    }

    @Override
    public String getName() {
	return "Metal Button";
    }

    @Override
    public String getPluralName() {
	return "Metal Buttons";
    }

    @Override
    public boolean isConditionallySolid(final ObjectInventory inv) {
	return this.isSolid();
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie, final int dirX, final int dirY,
	    final ObjectInventory inv) {
	return this.isDirectionallySolid(ie, dirX, dirY);
    }

    @Override
    public byte getObjectID() {
	return (byte) 5;
    }

    @Override
    public int getLayer() {
	return Maze.LAYER_OBJECT;
    }

    @Override
    public void editorProbeHook() {
	Messager.showMessage(this.getName() + ": Target (" + (this.targetCol + 1) + "," + (this.targetRow + 1) + ","
		+ (this.targetFloor + 1) + "," + (this.targetLevel + 1) + ")");
    }

    @Override
    public MazeObject editorPropertiesHook() {
	return Import1.getApplication().getEditor().editMetalButtonTarget();
    }

    @Override
    public String getDescription() {
	return "Metal Buttons will not trigger without Metal Boots.";
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_BUTTON);
	this.type.set(TypeConstants.TYPE_FIELD);
	this.type.set(TypeConstants.TYPE_INFINITE_LOCK);
	this.type.set(TypeConstants.TYPE_LOCK);
    }

    @Override
    public String getMoveSuccessSoundName() {
	return "button";
    }

    @Override
    public boolean defersSetProperties() {
	return true;
    }

    @Override
    public boolean hasAdditionalProperties() {
	return true;
    }
}
