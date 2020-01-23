/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericContainer;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class TreasureChest extends GenericContainer {
    // Constructors
    public TreasureChest() {
	super(new Key());
    }

    public TreasureChest(final MazeObject inside) {
	super(new Key(), inside);
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
	    Messager.showMessage("You need a key");
	}
	// Play move failed sound, if it's enabled
	if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playMoveFailedSound();
	}
    }

    @Override
    public String getName() {
	return "Treasure Chest";
    }

    @Override
    public String getPluralName() {
	return "Treasure Chests";
    }

    @Override
    public MazeObject editorPropertiesHook() {
	return Import1.getApplication().getEditor().editTreasureChestContents();
    }

    @Override
    public byte getObjectID() {
	return (byte) 1;
    }

    @Override
    public String getDescription() {
	return "Treasure Chests require Keys to open, and contain 1 other item.";
    }
}