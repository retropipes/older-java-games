/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleLock;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class DaggerWall extends AbstractMultipleLock {
    // Constructors
    public DaggerWall() {
        super(new Dagger(), ColorConstants.COLOR_BLUE);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_DAGGER;
    }

    @Override
    public String getName() {
        return "Dagger Wall";
    }

    @Override
    public String getPluralName() {
        return "Dagger Walls";
    }

    @Override
    public String getDescription() {
        return "Dagger Walls are impassable without enough Daggers.";
    }

    @Override
    public String getIdentifierV1() {
        return "Blue Crystal Wall";
    }
}