/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleKey;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Cap extends GenericMultipleKey {
    // Constructors
    public Cap() {
        super(ColorConstants.COLOR_RED);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_CAP;
    }

    @Override
    public String getName() {
        return "Cap";
    }

    @Override
    public String getPluralName() {
        return "Caps";
    }

    @Override
    public String getDescription() {
        return "Caps are the keys to Cap Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Red Crystal";
    }
}