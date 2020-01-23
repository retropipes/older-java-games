/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.creatures.StatConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericPotion;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class MajorHealPotion extends GenericPotion {
    // Fields
    private static final int MIN_HEAL = 6;
    private static final int MAX_HEAL = 50;

    // Constructors
    public MajorHealPotion() {
        super(StatConstants.STAT_CURRENT_HP, true, MajorHealPotion.MIN_HEAL,
                MajorHealPotion.MAX_HEAL);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_MAJOR_HEAL_POTION;
    }

    @Override
    public String getName() {
        return "Major Heal Potion";
    }

    @Override
    public String getPluralName() {
        return "Major Heal Potions";
    }

    @Override
    public String getDescription() {
        return "Major Heal Potions heal you significantly when picked up.";
    }
}