/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class Dagger extends AbstractMultipleKey {
    // Constructors
    public Dagger() {
        super(ColorConstants.COLOR_BLUE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_DAGGER;
    }

    @Override
    public String getName() {
        return "Dagger";
    }

    @Override
    public String getPluralName() {
        return "Daggers";
    }

    @Override
    public String getDescription() {
        return "Daggers are the keys to Dagger Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Blue Crystal";
    }
}