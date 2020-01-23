/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.creatures.StatConstants;
import com.puttysoftware.mazerunner2.creatures.party.PartyManager;
import com.puttysoftware.mazerunner2.maze.abc.AbstractPotion;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class SuperHealPotion extends AbstractPotion {
    // Constructors
    public SuperHealPotion() {
        super(StatConstants.STAT_CURRENT_HP, false);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SUPER_HEAL_POTION;
    }

    @Override
    public String getName() {
        return "Super Heal Potion";
    }

    @Override
    public String getPluralName() {
        return "Super Heal Potions";
    }

    @Override
    public int getEffectValue() {
        return PartyManager.getParty().getLeader().getMaximumHP();
    }

    @Override
    public String getDescription() {
        return "Super Heal Potions heal you completely when picked up.";
    }
}