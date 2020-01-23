/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.editor.MazeEditor;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericInvisibleTeleport;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class InvisibleTeleport extends GenericInvisibleTeleport {
    // Constructors
    public InvisibleTeleport() {
	super(0, 0, 0, 0);
    }

    public InvisibleTeleport(final int destinationRow, final int destinationColumn, final int destinationFloor,
	    final int destinationLevel) {
	super(destinationRow, destinationColumn, destinationFloor, destinationLevel);
    }

    // Scriptability
    @Override
    public String getName() {
	return "Invisible Teleport";
    }

    @Override
    public String getGameName() {
	return "Empty";
    }

    @Override
    public String getPluralName() {
	return "Invisible Teleports";
    }

    @Override
    public MazeObject editorPropertiesHook() {
	final MazeEditor me = Import1.getApplication().getEditor();
	final MazeObject mo = me.editTeleportDestination(MazeEditor.TELEPORT_TYPE_INVISIBLE_GENERIC);
	return mo;
    }

    @Override
    public byte getObjectID() {
	return (byte) 2;
    }

    @Override
    public String getDescription() {
	return "Invisible Teleports behave like regular teleports, except for the fact that they can't be seen.";
    }
}