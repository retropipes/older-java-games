/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractMultipleKey;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class Bolt extends AbstractMultipleKey {
    // Constructors
    public Bolt() {
        super(ColorConstants.COLOR_YELLOW);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BOLT;
    }

    @Override
    public String getName() {
        return "Bolt";
    }

    @Override
    public String getPluralName() {
        return "Bolts";
    }

    @Override
    public String getDescription() {
        return "Bolts are the keys to Bolt Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Yellow Crystal";
    }
}