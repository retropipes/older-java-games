/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleLock;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class BoltWall extends AbstractMultipleLock {
    // Constructors
    public BoltWall() {
        super(new Bolt(), ColorConstants.COLOR_YELLOW);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_BOLT;
    }

    @Override
    public String getName() {
        return "Bolt Wall";
    }

    @Override
    public String getPluralName() {
        return "Bolt Walls";
    }

    @Override
    public String getDescription() {
        return "Bolt Walls are impassable without enough Bolts.";
    }

    @Override
    public String getIdentifierV1() {
        return "Yellow Crystal Wall";
    }
}