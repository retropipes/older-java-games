/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericToggleWall;

public class BlueWallOff extends GenericToggleWall {
    // Constructors
    public BlueWallOff() {
	super(false);
    }

    // Scriptability
    @Override
    public String getName() {
	return "Blue Wall Off";
    }

    @Override
    public String getPluralName() {
	return "Blue Walls Off";
    }

    @Override
    public byte getObjectID() {
	return (byte) 1;
    }

    @Override
    public String getDescription() {
	return "Blue Walls Off can be walked through, and will change to Blue Walls On when a Blue Button is pressed.";
    }
}