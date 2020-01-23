/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractGround;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;

public class Tile extends AbstractGround {
    // Constructors
    public Tile() {
	super();
    }

    @Override
    public final int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_TILE;
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
    public String getDescription() {
	return "Tile is one of the many types of ground - unlike other types of ground, objects can be pushed and pulled over Tiles.";
    }
}