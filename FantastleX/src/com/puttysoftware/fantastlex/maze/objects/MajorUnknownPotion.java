/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.creatures.StatConstants;
import com.puttysoftware.fantastlex.maze.abc.AbstractPotion;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class MajorUnknownPotion extends AbstractPotion {
    // Fields
    private static final int MIN_EFFECT = -25;
    private static final int MAX_EFFECT = 25;

    // Constructors
    public MajorUnknownPotion() {
        super(StatConstants.STAT_CURRENT_HP, true,
                MajorUnknownPotion.MIN_EFFECT, MajorUnknownPotion.MAX_EFFECT);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_MAJOR_UNKNOWN_POTION;
    }

    @Override
    public String getName() {
        return "Major Unknown Potion";
    }

    @Override
    public String getPluralName() {
        return "Major Unknown Potions";
    }

    @Override
    public String getDescription() {
        return "Major Unknown Potions might heal you or hurt you significantly when picked up.";
    }
}