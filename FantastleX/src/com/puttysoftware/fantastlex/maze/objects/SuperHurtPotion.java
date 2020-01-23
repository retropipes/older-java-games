/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.creatures.StatConstants;
import com.puttysoftware.fantastlex.creatures.party.PartyManager;
import com.puttysoftware.fantastlex.maze.abc.AbstractPotion;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class SuperHurtPotion extends AbstractPotion {
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