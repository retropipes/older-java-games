/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericButton;

public class GreenButton extends GenericButton {
    public GreenButton() {
	super(new GreenWallOff(), new GreenWallOn());
    }

    @Override
    public String getName() {
	return "Green Button";
    }

    @Override
    public String getPluralName() {
	return "Green Buttons";
    }

    @Override
    public byte getObjectID() {
	return (byte) 2;
    }

    @Override
    public String getDescription() {
	return "Green Buttons will cause all Green Walls Off to become On, and all Green Walls On to become Off.";
    }
}
