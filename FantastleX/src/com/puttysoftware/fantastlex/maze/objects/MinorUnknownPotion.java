/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.creatures.StatConstants;
import com.puttysoftware.fantastlex.maze.abc.AbstractPotion;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class MinorUnknownPotion extends AbstractPotion {
    // Fields
    private static final int MIN_EFFECT = -3;
    private static final int MAX_EFFECT = 3;

    // Constructors
    public MinorUnknownPotion() {
        super(StatConstants.STAT_CURRENT_HP, true,
                MinorUnknownPotion.MIN_EFFECT, MinorUnknownPotion.MAX_EFFECT);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_MINOR_UNKNOWN_POTION;
    }

    @Override
    public String getName() {
        return "Minor Unknown Potion";
    }

    @Override
    public String getPluralName() {
        return "Minor Unknown Potions";
    }

    @Override
    public String getDescription() {
        return "Minor Unknown Potions might heal you or hurt you slightly when picked up.";
    }
}