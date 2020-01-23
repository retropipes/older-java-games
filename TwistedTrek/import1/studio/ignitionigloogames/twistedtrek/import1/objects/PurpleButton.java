/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericButton;

public class PurpleButton extends GenericButton {
    public PurpleButton() {
	super(new PurpleWallOff(), new PurpleWallOn());
    }

    @Override
    public String getName() {
	return "Purple Button";
    }

    @Override
    public String getPluralName() {
	return "Purple Buttons";
    }

    @Override
    public byte getObjectID() {
	return (byte) 5;
    }

    @Override
    public String getDescription() {
	return "Purple Buttons will cause all Purple Walls Off to become On, and all Purple Walls On to become Off.";
    }
}
