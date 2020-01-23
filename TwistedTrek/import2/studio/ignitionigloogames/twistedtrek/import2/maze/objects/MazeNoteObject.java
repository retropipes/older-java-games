/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractMarker;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;

public class MazeNoteObject extends AbstractMarker {
    // Constructors
    public MazeNoteObject() {
	super();
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_NOTE;
    }

    @Override
    public String getName() {
	return "Maze Note";
    }

    @Override
    public String getPluralName() {
	return "Maze Notes";
    }

    @Override
    public String getDescription() {
	return "";
    }
}