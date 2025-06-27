/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericToggleWall;

public class BlueWallOn extends GenericToggleWall {
    // Constructors
    public BlueWallOn() {
        super(true);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Blue Wall On";
    }

    @Override
    public String getPluralName() {
        return "Blue Walls On";
    }

    @Override
    public byte getObjectID() {
        return (byte) 2;
    }

    @Override
    public String getDescription() {
        return "Blue Walls On can NOT be walked through, and will change to Blue Walls Off when a Blue Button is pressed.";
    }
}