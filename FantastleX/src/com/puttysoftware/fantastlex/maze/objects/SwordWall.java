/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMultipleLock;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class SwordWall extends AbstractMultipleLock {
    // Constructors
    public SwordWall() {
        super(new Sword(), ColorConstants.COLOR_SKY);
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SUIT;
    }

    @Override
    public String getName() {
        return "Sword Wall";
    }

    @Override
    public String getPluralName() {
        return "Sword Walls";
    }

    @Override
    public String getDescription() {
        return "Sword Walls are impassable without enough Swords.";
    }

    @Override
    public String getIdentifierV1() {
        return "Sky Crystal Wall";
    }
}