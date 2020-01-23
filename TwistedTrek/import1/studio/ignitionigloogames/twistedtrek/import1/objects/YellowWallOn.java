/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericToggleWall;

public class YellowWallOn extends GenericToggleWall {
    // Constructors
    public YellowWallOn() {
	super(true);
    }

    // Scriptability
    @Override
    public String getName() {
	return "Yellow Wall On";
    }

    @Override
    public String getPluralName() {
	return "Yellow Walls On";
    }

    @Override
    public byte getObjectID() {
	return (byte) 16;
    }

    @Override
    public String getDescription() {
	return "Yellow Walls On can NOT be walked through, and will change to Yellow Walls Off when a Yellow Button is pressed.";
    }
}