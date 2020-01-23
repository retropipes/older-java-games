/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTeleport;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class OneShotControllableTeleport extends GenericTeleport {
    // Constructors
    public OneShotControllableTeleport() {
	super(0, 0, 0, 0);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	final Application app = Import1.getApplication();
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playMoveSuccessSound();
	}
	app.getGameManager().controllableTeleport();
	app.getGameManager().decay();
    }

    @Override
    public String getName() {
	return "One-Shot Controllable Teleport";
    }

    @Override
    public String getPluralName() {
	return "One-Shot Controllable Teleports";
    }

    @Override
    public void editorProbeHook() {
	Messager.showMessage(this.getName());
    }

    @Override
    public MazeObject editorPropertiesHook() {
	return null;
    }

    @Override
    public byte getObjectID() {
	return (byte) 0;
    }

    @Override
    public String getMoveSuccessSoundName() {
	return "walk";
    }

    @Override
    public String getDescription() {
	return "One-Shot Controllable Teleports let you choose the place you teleport to, then disappear.";
    }

    @Override
    public int getCustomFormat() {
	return 0;
    }
}