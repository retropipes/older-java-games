/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class Sword extends AbstractMultipleKey {
    // Constructors
    public Sword() {
        super(ColorConstants.COLOR_SKY);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SWORD;
    }

    @Override
    public String getName() {
        return "Sword";
    }

    @Override
    public String getPluralName() {
        return "Swords";
    }

    @Override
    public String getDescription() {
        return "Swords are the keys to Sword Walls.";
    }

    @Override
    public String getIdentifierV1() {
        return "Sky Crystal";
    }
}