/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.editor.MazeEditor;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericInvisibleTeleport;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class InvisibleOneShotTeleport extends GenericInvisibleTeleport {
    // Constructors
    public InvisibleOneShotTeleport() {
        super(0, 0, 0, 0);
    }

    public InvisibleOneShotTeleport(final int destinationRow, final int destinationColumn, final int destinationFloor,
            final int destinationLevel) {
        super(destinationRow, destinationColumn, destinationFloor, destinationLevel);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        final Application app = Import1.getApplication();
        app.getGameManager().decay();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        Messager.showMessage("Invisible Teleport!");
        if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveSuccessSound();
        }
    }

    @Override
    public String getName() {
        return "Invisible One-Shot Teleport";
    }

    @Override
    public String getGameName() {
        return "Empty";
    }

    @Override
    public String getPluralName() {
        return "Invisible One-Shot Teleports";
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = Import1.getApplication().getEditor();
        final MazeObject mo = me.editTeleportDestination(MazeEditor.TELEPORT_TYPE_INVISIBLE_ONESHOT);
        return mo;
    }

    @Override
    public byte getObjectID() {
        return (byte) 1;
    }

    @Override
    public String getDescription() {
        return "Invisible One-Shot Teleports are a combination of invisible and one-shot teleport behaviors.";
    }
}