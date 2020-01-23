/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleLock;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class BracerWall extends AbstractMultipleLock {
    // Constructors
    public BracerWall() {
        super(new Bracer(), ColorConstants.COLOR_CYAN);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_BRACERS;
    }

    @Override
    public String getName() {
        return "Bracer Wall";
    }

    @Override
    public String getPluralName() {
        return "Bracer Walls";
    }

    @Override
    public String getDescription() {
        return "Bracer Walls are impassable without enough Bracers.";
    }

    @Override
    public String getIdentifierV1() {
        return "Cyan Crystal Wall";
    }
}