/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericToggleWall;

public class GreenWallOff extends GenericToggleWall {
    // Constructors
    public GreenWallOff() {
        super(false);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Green Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Green Walls Off";
    }

    @Override
    public byte getObjectID() {
        return (byte) 3;
    }

    @Override
    public String getDescription() {
        return "Green Walls Off can be walked through, and will change to Green Walls On when a Green Button is pressed.";
    }
}