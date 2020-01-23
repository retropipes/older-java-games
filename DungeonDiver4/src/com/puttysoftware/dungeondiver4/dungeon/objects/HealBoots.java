/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.creatures.party.PartyManager;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBoots;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class HealBoots extends AbstractBoots {
    // Constants
    private static final int HEAL_AMOUNT = 1;

    // Constructors
    public HealBoots() {
        super(ColorConstants.COLOR_MAGENTA);
    }

    @Override
    public String getName() {
        return "Heal Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Heal Boots";
    }

    @Override
    public String getDescription() {
        return "Heal Boots restore your health as you walk. Note that you can only wear one pair of boots at once.";
    }

    @Override
    public void stepAction() {
        PartyManager.getParty().getLeader().heal(HealBoots.HEAL_AMOUNT);
    }
}
