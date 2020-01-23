/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractPassThroughObject;
import studio.ignitionigloogames.twistedtrek.import2.maze.utilities.TypeConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;

public class Empty extends AbstractPassThroughObject {
    // Constructors
    public Empty() {
	super();
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_EMPTY;
    }

    @Override
    public String getName() {
	return "Empty";
    }

    @Override
    public String getPluralName() {
	return "Squares of Emptiness";
    }

    @Override
    public String getDescription() {
	return "Squares of Emptiness are what fills areas that aren't occupied by other objects.";
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_PASS_THROUGH);
	this.type.set(TypeConstants.TYPE_EMPTY_SPACE);
    }
}