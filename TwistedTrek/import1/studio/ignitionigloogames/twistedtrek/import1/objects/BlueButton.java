/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericButton;

public class BlueButton extends GenericButton {
    public BlueButton() {
	super(new BlueWallOff(), new BlueWallOn());
    }

    @Override
    public String getName() {
	return "Blue Button";
    }

    @Override
    public String getPluralName() {
	return "Blue Buttons";
    }

    @Override
    public byte getObjectID() {
	return (byte) 1;
    }

    @Override
    public String getDescription() {
	return "Blue Buttons will cause all Blue Walls Off to become On, and all Blue Walls On to become Off.";
    }
}
