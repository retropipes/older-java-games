/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWall;

public class Wall extends GenericWall {
    // Constructors
    public Wall() {
	super();
    }

    @Override
    public String getName() {
	return "Wall";
    }

    @Override
    public String getPluralName() {
	return "Walls";
    }

    @Override
    public byte getObjectID() {
	return (byte) 8;
    }

    @Override
    public String getDescription() {
	return "Walls are impassable - you'll need to go around them.";
    }
}