/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.creatures.StatConstants;
import com.puttysoftware.mazerunner2.maze.abc.AbstractPotion;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class SuperUnknownPotion extends AbstractPotion {
    // Fields
    private static final int MIN_EFFECT = -99;
    private static final int MAX_EFFECT = 99;

    // Constructors
    public SuperUnknownPotion() {
        super(StatConstants.STAT_CURRENT_HP, true,
                SuperUnknownPotion.MIN_EFFECT, SuperUnknownPotion.MAX_EFFECT);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SUPER_UNKNOWN_POTION;
    }

    @Override
    public String getName() {
        return "Super Unknown Potion";
    }

    @Override
    public String getPluralName() {
        return "Super Unknown Potions";
    }

    @Override
    public String getDescription() {
        return "Super Unknown Potions might heal you almost fully or hurt you to the brink of death when picked up.";
    }
}