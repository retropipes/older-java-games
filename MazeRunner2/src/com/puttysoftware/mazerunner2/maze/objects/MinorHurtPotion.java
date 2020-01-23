/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.creatures.StatConstants;
import com.puttysoftware.mazerunner2.maze.abc.AbstractPotion;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

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