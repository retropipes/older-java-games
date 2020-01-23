/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.creatures.StatConstants;
import com.puttysoftware.fantastlex.maze.abc.AbstractPotion;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class MinorHurtPotion extends AbstractPotion {
    // Fields
    private static final int MIN_HURT = -1;
    private static final int MAX_HURT = -5;

    // Constructors
    public MinorHurtPotion() {
        super(StatConstants.STAT_CURRENT_HP, true, MinorHurtPotion.MAX_HURT,
                MinorHurtPotion.MIN_HURT);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_MINOR_HURT_POTION;
    }

    @Override
    public String getName() {
        return "Minor Hurt Potion";
    }

    @Override
    public String getPluralName() {
        return "Minor Hurt Potions";
    }

    @Override
    public String getDescription() {
        return "Minor Hurt Potions hurt you slightly when picked up.";
    }
}