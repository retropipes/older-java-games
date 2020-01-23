/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericToggleWall;

public class CyanWallOn extends GenericToggleWall {
    // Constructors
    public CyanWallOn() {
	super(true);
    }

    // Scriptability
    @Override
    public String getName() {
	return "Cyan Wall On";
    }

    @Override
    public String getPluralName() {
	return "Cyan Walls On";
    }

    @Override
    public byte getObjectID() {
	return (byte) 18;
    }

    @Override
    public String getDescription() {
	return "Cyan Walls On can NOT be walked through, and will change to Cyan Walls Off when a Cyan Button is pressed.";
    }
}