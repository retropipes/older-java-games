/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWall;

public class OneWaySouthWall extends GenericWall {
    public OneWaySouthWall() {
	super(true, false, true, true, true, false, true, true);
    }

    @Override
    public String getName() {
	return "One-Way South Wall";
    }

    @Override
    public String getPluralName() {
	return "One-Way South Walls";
    }

    @Override
    public byte getObjectID() {
	return (byte) 6;
    }

    @Override
    public String getDescription() {
	return "One-Way South Walls allow movement through them only South.";
    }
}
