/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.editor.MazeEditor;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;

public abstract class GenericRandomInvisibleTeleport extends GenericRandomTeleport {
    // Constructors
    public GenericRandomInvisibleTeleport(final int newRandomRangeY, final int newRandomRangeX) {
        super(newRandomRangeY, newRandomRangeX);
    }

    // Scriptability
    @Override
    abstract public String getName();

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        final Application app = Import1.getApplication();
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
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = Import1.getApplication().getEditor();
        final MazeObject mo = me.editTeleportDestination(MazeEditor.TELEPORT_TYPE_RANDOM_INVISIBLE);
        return mo;
    }

    @Override
    public byte getGroupID() {
        return (byte) 17;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_RANDOM_INVISIBLE_TELEPORT);
        this.type.set(TypeConstants.TYPE_RANDOM_TELEPORT);
    }
}