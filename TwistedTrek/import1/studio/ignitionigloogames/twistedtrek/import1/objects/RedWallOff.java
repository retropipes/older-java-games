/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericToggleWall;

public class RedWallOff extends GenericToggleWall {
    // Constructors
    public RedWallOff() {
        super(false);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Red Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Red Walls Off";
    }

    @Override
    public byte getObjectID() {
        return (byte) 11;
    }

    @Override
    public String getDescription() {
        return "Red Walls Off can be walked through, and will change to Red Walls On when a Red Button is pressed.";
    }
}