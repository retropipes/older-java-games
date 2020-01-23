/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMultipleKey;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class Staff extends GenericMultipleKey {
    // Constructors
    public Staff() {
        super(ColorConstants.COLOR_PURPLE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_STAFF;
    }

    @Override
    public String getName() {
        return "Staff";
    }

    @Override
    public String getPluralName() {
        return "Staves";
    }

    @Override
    public String getDescription() {
        return "Staves are the keys to Staff Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Purple Crystal";
    }
}