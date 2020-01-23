/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleKey;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Bracer extends GenericMultipleKey {
    // Constructors
    public Bracer() {
        super(ColorConstants.COLOR_CYAN);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BRACERS;
    }

    @Override
    public String getName() {
        return "Bracer";
    }

    @Override
    public String getPluralName() {
        return "Bracers";
    }

    @Override
    public String getDescription() {
        return "Bracers are the keys to Bracer Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Cyan Crystal";
    }
}