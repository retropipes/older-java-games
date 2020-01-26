/*  TallerTower: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: TallerTower@worldwizard.net
 */
package com.puttysoftware.tallertower.maze.objects;

import com.puttysoftware.tallertower.creatures.party.PartyManager;
import com.puttysoftware.tallertower.maze.abc.AbstractTrap;
import com.puttysoftware.tallertower.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.tallertower.resourcemanagers.SoundConstants;
import com.puttysoftware.tallertower.resourcemanagers.SoundManager;

public class HealTrap extends AbstractTrap {
    // Constructors
    public HealTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_HEAL_TRAP);
    }

    @Override
    public String getName() {
        return "Heal Trap";
    }

    @Override
    public String getPluralName() {
        return "Heal Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        int healing = PartyManager.getParty().getLeader().getMaximumHP() / 10;
        if (healing < 1) {
            healing = 1;
        }
        PartyManager.getParty().getLeader().heal(healing);
        SoundManager.playSound(SoundConstants.SOUND_HEAL);
    }

    @Override
    public String getDescription() {
        return "Heal Traps heal you when stepped on.";
    }
}