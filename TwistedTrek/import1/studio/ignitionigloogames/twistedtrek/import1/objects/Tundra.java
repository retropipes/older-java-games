/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericGround;

public class Tundra extends GenericGround {
    // Constructors
    public Tundra() {
	super();
    }

    @Override
    public String getName() {
	return "Tundra";
    }

    @Override
    public String getPluralName() {
	return "Squares of Tundra";
    }

    @Override
    public byte getObjectID() {
	return (byte) 9;
    }

    @Override
    public String getDescription() {
	return "Tundra is one of the many types of ground.";
    }
}