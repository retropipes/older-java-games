/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericButton;

public class CyanButton extends GenericButton {
    public CyanButton() {
	super(new CyanWallOff(), new CyanWallOn());
    }

    @Override
    public String getName() {
	return "Cyan Button";
    }

    @Override
    public String getPluralName() {
	return "Cyan Buttons";
    }

    @Override
    public byte getObjectID() {
	return (byte) 9;
    }

    @Override
    public String getDescription() {
	return "Cyan Buttons will cause all Cyan Walls Off to become On, and all Cyan Walls On to become Off.";
    }
}
