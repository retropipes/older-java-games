/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.editor.MazeEditor;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTeleport;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class TwoWayTeleport extends GenericTeleport {
    public TwoWayTeleport() {
        super(0, 0, 0, 0);
    }

    public TwoWayTeleport(final int destRow, final int destCol, final int destFloor, final int destLevel) {
        super(destRow, destCol, destFloor, destLevel);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        final Application app = Import1.getApplication();
        app.getGameManager().updatePositionAbsoluteNoEvents(this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveSuccessSound();
        }
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = Import1.getApplication().getEditor();
        final MazeObject mo = me.editTeleportDestination(MazeEditor.TELEPORT_TYPE_TWOWAY);
        return mo;
    }

    @Override
    public String getName() {
        return "Two-Way Teleport";
    }

    @Override
    public String getPluralName() {
        return "Two-Way Teleports";
    }

    @Override
    public byte getObjectID() {
        return (byte) 9;
    }

    @Override
    public String getDescription() {
        return "Two-Way Teleports send you to their companion at their destination, and are linked such that stepping on the companion sends you back to the original.";
    }
}
