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

public class HurtTrap extends AbstractTrap {
    // Fields
    private int damage;

    // Constructors
    public HurtTrap() {
        super(ColorConstants.COLOR_SKY,
                ObjectImageConstants.OBJECT_IMAGE_HEALTH,
                ColorConstants.COLOR_DARK_SKY);
    }

    @Override
    public String getName() {
        return "Hurt Trap";
    }

    @Override
    public String getPluralName() {
        return "Hurt Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final DungeonObjectInventory inv) {
        this.damage = PartyManager.getParty().getLeader().getMaximumHP() / 50;
        if (this.damage < 1) {
            this.damage = 1;
        }
        PartyManager.getParty().getLeader().doDamage(this.damage);
        SoundManager.playSound(SoundConstants.SOUND_BARRIER);
    }

    @Override
    public String getDescription() {
        return "Hurt Traps hurt you when stepped on.";
    }
}