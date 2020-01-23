/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.Maze;
import com.puttysoftware.mazerunner2.maze.abc.AbstractCharacter;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

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
    public int getMinimumRequiredQuantity(Maze maze) {
        return 1;
    }

    @Override
    public int getMaximumRequiredQuantity(Maze maze) {
        return 1;
    }
}