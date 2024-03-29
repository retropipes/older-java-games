/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.creatures.StatConstants;
import com.puttysoftware.mazerunner2.maze.abc.AbstractPotion;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

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