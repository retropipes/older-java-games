/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.editor.MazeEditor;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTeleport;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class Teleport extends GenericTeleport {
    // Constructors
    public Teleport() {
        super(0, 0, 0, 0);
    }

    public Teleport(final int destinationRow, final int destinationColumn, final int destinationFloor,
            final int destinationLevel) {
        super(destinationRow, destinationColumn, destinationFloor, destinationLevel);
    }

    @Override
    public String getName() {
        return "Teleport";
    }

    @Override
    public String getPluralName() {
        return "Teleports";
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = Import1.getApplication().getEditor();
        final MazeObject mo = me.editTeleportDestination(MazeEditor.TELEPORT_TYPE_GENERIC);
        return mo;
    }

    @Override
    public byte getObjectID() {
        return (byte) 8;
    }

    @Override
    public String getDescription() {
        return "Teleports send you to a predetermined destination when stepped on.";
    }
}