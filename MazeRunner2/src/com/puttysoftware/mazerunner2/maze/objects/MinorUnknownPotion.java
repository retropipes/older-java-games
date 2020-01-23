/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.creatures.StatConstants;
import com.puttysoftware.mazerunner2.maze.abc.AbstractPotion;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

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