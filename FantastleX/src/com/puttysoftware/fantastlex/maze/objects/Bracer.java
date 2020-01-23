/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class Bracer extends AbstractMultipleKey {
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