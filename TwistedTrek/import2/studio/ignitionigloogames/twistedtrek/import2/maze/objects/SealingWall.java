/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractWall;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;

public class SealingWall extends AbstractWall {
    // Constructors
    public SealingWall() {
	super();
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_SEALING_WALL;
    }

    @Override
    public String getName() {
	return "Sealing Wall";
    }

    @Override
    public String getPluralName() {
	return "Sealing Walls";
    }

    @Override
    public String getDescription() {
	return "Sealing Walls are impassable and indestructible - you'll need to go around them.";
    }
}