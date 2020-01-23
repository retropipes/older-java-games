/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.creatures.PartyManager;
import com.puttysoftware.mastermaze.creatures.StatConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericPotion;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class SuperHurtPotion extends GenericPotion {
    // Constructors
    public SuperHurtPotion() {
        super(StatConstants.STAT_CURRENT_HP, false);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SUPER_HURT_POTION;
    }

    @Override
    public String getName() {
        return "Super Hurt Potion";
    }

    @Override
    public String getPluralName() {
        return "Super Hurt Potions";
    }

    @Override
    public int getEffectValue() {
        return -(PartyManager.getParty().getLeader().getCurrentHP() - 1);
    }

    @Override
    public String getDescription() {
        return "Super Hurt Potions bring you to the brink of death when picked up.";
    }
}