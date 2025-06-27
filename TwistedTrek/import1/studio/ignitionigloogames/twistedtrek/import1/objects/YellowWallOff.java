/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericToggleWall;

public class YellowWallOff extends GenericToggleWall {
    // Constructors
    public YellowWallOff() {
        super(false);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Yellow Walls Off";
    }

    @Override
    public byte getObjectID() {
        return (byte) 15;
    }

    @Override
    public String getDescription() {
        return "Yellow Walls Off can be walked through, and will change to Yellow Walls On when a Yellow Button is pressed.";
    }
}