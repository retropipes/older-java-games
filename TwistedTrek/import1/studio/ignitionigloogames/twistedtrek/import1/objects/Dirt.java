/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericGround;

public class Dirt extends GenericGround {
    // Constructors
    public Dirt() {
	super();
    }

    @Override
    public String getName() {
	return "Dirt";
    }

    @Override
    public String getPluralName() {
	return "Squares of Dirt";
    }

    @Override
    public String getIdentifier4() {
	return "Rock";
    }

    @Override
    public byte getObjectID() {
	return (byte) 6;
    }

    @Override
    public String getDescription() {
	return "Dirt is one of the many types of ground.";
    }
}