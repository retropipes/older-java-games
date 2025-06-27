/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericToggleWall;

public class GreenWallOn extends GenericToggleWall {
    // Constructors
    public GreenWallOn() {
        super(true);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Green Wall On";
    }

    @Override
    public String getPluralName() {
        return "Green Walls On";
    }

    @Override
    public byte getObjectID() {
        return (byte) 4;
    }

    @Override
    public String getDescription() {
        return "Green Walls On can NOT be walked through, and will change to Green Walls Off when a Green Button is pressed.";
    }
}