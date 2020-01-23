/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractMultipleLock;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class SuitWall extends AbstractMultipleLock {
    // Constructors
    public SuitWall() {
        super(new Suit(), ColorConstants.COLOR_MAGENTA);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SUIT;
    }

    @Override
    public String getName() {
        return "Suit Wall";
    }

    @Override
    public String getPluralName() {
        return "Suit Walls";
    }

    @Override
    public String getDescription() {
        return "Suit Walls are impassable without enough Suits.";
    }

    @Override
    public String getIdentifierV1() {
        return "Magenta Crystal Wall";
    }
}