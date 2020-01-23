/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericToggleWall;

public class MagentaWallOff extends GenericToggleWall {
    // Constructors
    public MagentaWallOff() {
	super(false);
    }

    // Scriptability
    @Override
    public String getName() {
	return "Magenta Wall Off";
    }

    @Override
    public String getPluralName() {
	return "Magenta Walls Off";
    }

    @Override
    public byte getObjectID() {
	return (byte) 5;
    }

    @Override
    public String getDescription() {
	return "Magenta Walls Off can be walked through, and will change to Magenta Walls On when a Magenta Button is pressed.";
    }
}