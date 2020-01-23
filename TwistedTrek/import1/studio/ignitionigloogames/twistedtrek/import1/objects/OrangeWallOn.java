/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericToggleWall;

public class OrangeWallOn extends GenericToggleWall {
    // Constructors
    public OrangeWallOn() {
	super(true);
    }

    // Scriptability
    @Override
    public String getName() {
	return "Orange Wall On";
    }

    @Override
    public String getPluralName() {
	return "Orange Walls On";
    }

    @Override
    public byte getObjectID() {
	return (byte) 8;
    }

    @Override
    public String getDescription() {
	return "Orange Walls On can NOT be walked through, and will change to Orange Walls Off when a Orange Button is pressed.";
    }
}