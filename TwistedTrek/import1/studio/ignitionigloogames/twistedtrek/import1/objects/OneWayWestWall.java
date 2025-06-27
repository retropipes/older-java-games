/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWall;

public class OneWayWestWall extends GenericWall {
    public OneWayWestWall() {
        super(true, true, true, false, true, true, true, false);
    }

    @Override
    public String getName() {
        return "One-Way West Wall";
    }

    @Override
    public String getPluralName() {
        return "One-Way West Walls";
    }

    @Override
    public byte getObjectID() {
        return (byte) 7;
    }

    @Override
    public String getDescription() {
        return "One-Way West Walls allow movement through them only West.";
    }
}
