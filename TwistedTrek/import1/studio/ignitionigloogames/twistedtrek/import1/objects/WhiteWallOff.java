/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericToggleWall;

public class WhiteWallOff extends GenericToggleWall {
    // Constructors
    public WhiteWallOff() {
	super(false);
    }

    // Scriptability
    @Override
    public String getName() {
	return "White Wall Off";
    }

    @Override
    public String getPluralName() {
	return "White Walls Off";
    }

    @Override
    public byte getObjectID() {
	return (byte) 13;
    }

    @Override
    public String getDescription() {
	return "White Walls Off can be walked through, and will change to White Walls On when a White Button is pressed.";
    }
}