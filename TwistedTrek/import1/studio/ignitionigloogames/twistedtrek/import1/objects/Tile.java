/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericGround;

public class Tile extends GenericGround {
    // Constructors
    public Tile() {
	super(true, true, true, true);
    }

    @Override
    public String getName() {
	return "Tile";
    }

    @Override
    public String getPluralName() {
	return "Tiles";
    }

    @Override
    public byte getObjectID() {
	return (byte) 2;
    }

    @Override
    public String getDescription() {
	return "Tile is one of the many types of ground - unlike other types of ground, objects can be pushed and pulled over Tiles.";
    }
}