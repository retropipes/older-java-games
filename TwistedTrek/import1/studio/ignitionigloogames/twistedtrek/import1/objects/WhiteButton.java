/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericButton;

public class WhiteButton extends GenericButton {
    public WhiteButton() {
	super(new WhiteWallOff(), new WhiteWallOn());
    }

    @Override
    public String getName() {
	return "White Button";
    }

    @Override
    public String getPluralName() {
	return "White Buttons";
    }

    @Override
    public byte getObjectID() {
	return (byte) 7;
    }

    @Override
    public String getDescription() {
	return "White Buttons will cause all White Walls Off to become On, and all White Walls On to become Off.";
    }
}
