/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleLock;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class GloveWall extends AbstractMultipleLock {
    // Constructors
    public GloveWall() {
        super(new Glove(), ColorConstants.COLOR_GREEN);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_GLOVES;
    }

    @Override
    public String getName() {
        return "Glove Wall";
    }

    @Override
    public String getPluralName() {
        return "Glove Walls";
    }

    @Override
    public String getDescription() {
        return "Glove Walls are impassable without enough Gloves.";
    }

    @Override
    public String getIdentifierV1() {
        return "Green Crystal Wall";
    }
}