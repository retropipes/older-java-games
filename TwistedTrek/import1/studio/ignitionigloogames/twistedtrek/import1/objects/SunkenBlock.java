/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericGround;

public class SunkenBlock extends GenericGround {
    // Constructors
    public SunkenBlock() {
	super(true, true, true, true);
    }

    @Override
    public String getName() {
	return "Sunken Block";
    }

    @Override
    public String getPluralName() {
	return "Sunken Blocks";
    }

    @Override
    public byte getObjectID() {
	return (byte) 3;
    }

    @Override
    public String getDescription() {
	return "Sunken Blocks are created when Pushable Blocks are pushed into Water, and behave just like Tiles.";
    }
}