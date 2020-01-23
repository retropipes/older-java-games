/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericButton;

public class YellowButton extends GenericButton {
    public YellowButton() {
	super(new YellowWallOff(), new YellowWallOn());
    }

    @Override
    public String getName() {
	return "Yellow Button";
    }

    @Override
    public String getPluralName() {
	return "Yellow Buttons";
    }

    @Override
    public byte getObjectID() {
	return (byte) 8;
    }

    @Override
    public String getDescription() {
	return "Yellow Buttons will cause all Yellow Walls Off to become On, and all Yellow Walls On to become Off.";
    }
}
