/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.editor.MazeEditor;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class RandomInvisibleOneShotTeleport extends RandomInvisibleTeleport {
    // Constructors
    public RandomInvisibleOneShotTeleport() {
	super();
    }

    public RandomInvisibleOneShotTeleport(final int newRandomRangeY, final int newRandomRangeX) {
	super(newRandomRangeY, newRandomRangeX);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	final Application app = Import1.getApplication();
	app.getGameManager().decay();
	int dr, dc;
	do {
	    dr = this.getDestinationRow();
	    dc = this.getDestinationColumn();
	} while (!app.getGameManager().tryUpdatePositionRelative(dr, dc));
	app.getGameManager().updatePositionRelative(dr, dc);
	Messager.showMessage("Invisible Teleport!");
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playMoveSuccessSound();
	}
    }

    @Override
    public String getName() {
	return "Random Invisible One-Shot Teleport";
    }

    @Override
    public String getGameName() {
	return "Empty";
    }

    @Override
    public String getPluralName() {
	return "Random Invisible One-Shot Teleports";
    }

    @Override
    public MazeObject editorPropertiesHook() {
	final MazeEditor me = Import1.getApplication().getEditor();
	final MazeObject mo = me.editTeleportDestination(MazeEditor.TELEPORT_TYPE_RANDOM_INVISIBLE_ONESHOT);
	return mo;
    }

    @Override
    public byte getObjectID() {
	return (byte) 2;
    }

    @Override
    public String getDescription() {
	return "Random Invisible One-Shot Teleports are random, invisible, and only work once.";
    }
}