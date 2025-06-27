/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTeleport;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class ControllableTeleport extends GenericTeleport {
    // Constructors
    public ControllableTeleport() {
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
    }

    @Override
    public String getName() {
        return "Controllable Teleport";
    }

    @Override
    public String getPluralName() {
        return "Controllable Teleports";
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
        return "Controllable Teleports let you choose the place you teleport to.";
    }

    @Override
    public int getCustomFormat() {
        return 0;
    }
}