/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.maze.Maze;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractCharacter;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;

public class Player extends AbstractCharacter {
    // Constructors
    public Player() {
	super();
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_PLAYER;
    }

    @Override
    public String getName() {
	return "Player";
    }

    @Override
    public String getPluralName() {
	return "Players";
    }

    @Override
    public String getDescription() {
	return "This is you - the Player.";
    }

    // Random Generation Rules
    @Override
    public boolean isRequired() {
	return true;
    }

    @Override
    public int getMinimumRequiredQuantity(final Maze maze) {
	return 1;
    }

    @Override
    public int getMaximumRequiredQuantity(final Maze maze) {
	return 1;
    }
}