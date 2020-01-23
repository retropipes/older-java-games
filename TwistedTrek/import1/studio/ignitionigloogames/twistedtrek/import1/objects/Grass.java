/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericGround;

public class Grass extends GenericGround {
    // Constructors
    public Grass() {
	super();
    }

    @Override
    public String getName() {
	return "Grass";
    }

    @Override
    public String getPluralName() {
	return "Squares of Grass";
    }

    @Override
    public byte getObjectID() {
	return (byte) 5;
    }

    @Override
    public String getDescription() {
	return "Grass is one of the many types of ground.";
    }
}