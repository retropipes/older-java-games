/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPassThroughObject;

public class FakeWall extends GenericPassThroughObject {
    // Constructors
    public FakeWall() {
	super();
    }

    @Override
    public String getName() {
	return "Fake Wall";
    }

    @Override
    public String getGameName() {
	return "Wall";
    }

    @Override
    public String getPluralName() {
	return "Fake Walls";
    }

    @Override
    public byte getObjectID() {
	return (byte) 2;
    }

    @Override
    public String getDescription() {
	return "Fake Walls look like walls, but can be walked through.";
    }
}