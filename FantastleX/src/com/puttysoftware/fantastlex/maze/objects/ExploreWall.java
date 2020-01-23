/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleLock;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class ExploreWall extends AbstractMultipleLock {
    // Constructors
    public ExploreWall() {
        super(new Explore(), ColorConstants.COLOR_SEAWEED);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_EXPLORE;
    }

    @Override
    public String getName() {
        return "Explore Wall";
    }

    @Override
    public String getPluralName() {
        return "Explore Walls";
    }

    @Override
    public String getDescription() {
        return "Explore Walls are impassable without enough Explores.";
    }

    @Override
    public String getIdentifierV1() {
        return "Seaweed Crystal Wall";
    }
}