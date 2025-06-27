/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWall;

public class OneWayNorthWall extends GenericWall {
    public OneWayNorthWall() {
        super(false, true, true, true, false, true, true, true);
    }

    @Override
    public String getName() {
        return "One-Way North Wall";
    }

    @Override
    public String getPluralName() {
        return "One-Way North Walls";
    }

    @Override
    public byte getObjectID() {
        return (byte) 5;
    }

    @Override
    public String getDescription() {
        return "One-Way North Walls allow movement through them only North.";
    }
}
