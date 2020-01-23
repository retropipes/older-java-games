/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.creatures.StatConstants;
import com.puttysoftware.fantastlex.maze.abc.AbstractPotion;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class MajorHurtPotion extends AbstractPotion {
    // Fields
    private static final int MIN_HURT = -6;
    private static final int MAX_HURT = -50;

    // Constructors
    public MajorHurtPotion() {
        super(StatConstants.STAT_CURRENT_HP, true, MajorHurtPotion.MAX_HURT,
                MajorHurtPotion.MIN_HURT);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_MAJOR_HURT_POTION;
    }

    @Override
    public String getName() {
        return "Major Hurt Potion";
    }

    @Override
    public String getPluralName() {
        return "Major Hurt Potions";
    }

    @Override
    public String getDescription() {
        return "Major Hurt Potions hurt you significantly when picked up.";
    }
}