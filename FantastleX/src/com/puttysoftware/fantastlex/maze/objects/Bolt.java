/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

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