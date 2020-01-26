/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.creatures.party.PartyManager;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractTrap;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class HealTrap extends AbstractTrap {
    // Fields
    private int healing;

    // Constructors
    public HealTrap() {
        super(ColorConstants.COLOR_MAGENTA,
                ObjectImageConstants.OBJECT_IMAGE_HEALTH,
                ColorConstants.COLOR_DARK_MAGENTA);
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
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final DungeonObjectInventory inv) {
        this.healing = PartyManager.getParty().getLeader().getMaximumHP() / 50;
        if (this.healing < 1) {
            this.healing = 1;
        }
        PartyManager.getParty().getLeader().heal(this.healing);
        SoundManager.playSound(SoundConstants.SOUND_BARRIER);
    }

    @Override
    public String getDescription() {
        return "Heal Traps heal you when stepped on.";
    }
}