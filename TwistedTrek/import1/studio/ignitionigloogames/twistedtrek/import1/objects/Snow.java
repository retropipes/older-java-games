/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericGround;

public class Snow extends GenericGround {
    // Constructors
    public Snow() {
	super();
    }

    @Override
    public String getName() {
	return "Snow";
    }

    @Override
    public String getPluralName() {
	return "Squares of Snow";
    }

    @Override
    public byte getObjectID() {
	return (byte) 8;
    }

    @Override
    public String getDescription() {
	return "Snow is one of the many types of ground.";
    }
}