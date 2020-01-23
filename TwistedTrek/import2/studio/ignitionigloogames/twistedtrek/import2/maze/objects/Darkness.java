/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractPassThroughObject;
import studio.ignitionigloogames.twistedtrek.import2.maze.utilities.TypeConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;

public class Darkness extends AbstractPassThroughObject {
    // Constructors
    public Darkness() {
	super();
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_DARKNESS;
    }

    @Override
    public String getName() {
	return "Darkness";
    }

    @Override
    public String getPluralName() {
	return "Squares of Darkness";
    }

    @Override
    public String getDescription() {
	return "Squares of Darkness are what fills areas that cannot be seen.";
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_PASS_THROUGH);
	this.type.set(TypeConstants.TYPE_EMPTY_SPACE);
    }
}