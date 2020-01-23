/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.creatures.StatConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericPotion;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class MinorHealPotion extends GenericPotion {
    // Fields
    private static final int MIN_HEAL = 1;
    private static final int MAX_HEAL = 5;

    // Constructors
    public MinorHealPotion() {
        super(StatConstants.STAT_CURRENT_HP, true, MinorHealPotion.MIN_HEAL,
                MinorHealPotion.MAX_HEAL);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_MINOR_HEAL_POTION;
    }

    @Override
    public String getName() {
        return "Minor Heal Potion";
    }

    @Override
    public String getPluralName() {
        return "Minor Heal Potions";
    }

    @Override
    public String getDescription() {
        return "Minor Heal Potions heal you slightly when picked up.";
    }
}