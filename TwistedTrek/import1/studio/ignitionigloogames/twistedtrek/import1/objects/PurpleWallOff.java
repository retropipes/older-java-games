/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericToggleWall;

public class PurpleWallOff extends GenericToggleWall {
    // Constructors
    public PurpleWallOff() {
	super(false);
    }

    // Scriptability
    @Override
    public String getName() {
	return "Purple Wall Off";
    }

    @Override
    public String getPluralName() {
	return "Purple Walls Off";
    }

    @Override
    public byte getObjectID() {
	return (byte) 9;
    }

    @Override
    public String getDescription() {
	return "Purple Walls Off can be walked through, and will change to Purple Walls On when a Purple Button is pressed.";
    }
}