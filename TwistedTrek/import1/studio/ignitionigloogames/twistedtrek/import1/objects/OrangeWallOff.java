/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericToggleWall;

public class OrangeWallOff extends GenericToggleWall {
    // Constructors
    public OrangeWallOff() {
	super(false);
    }

    // Scriptability
    @Override
    public String getName() {
	return "Orange Wall Off";
    }

    @Override
    public String getPluralName() {
	return "Orange Walls Off";
    }

    @Override
    public byte getObjectID() {
	return (byte) 7;
    }

    @Override
    public String getDescription() {
	return "Orange Walls Off can be walked through, and will change to Orange Walls On when a Orange Button is pressed.";
    }
}