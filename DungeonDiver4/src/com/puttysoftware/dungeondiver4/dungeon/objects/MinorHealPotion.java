/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.creatures.StatConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractPotion;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class MinorHealPotion extends AbstractPotion {
    // Fields
    private static final int MIN_HEAL = 1;
    private static final int MAX_HEAL = 5;

    // Constructors
    public MinorHealPotion() {
        super(StatConstants.STAT_CURRENT_HP, true, MinorHealPotion.MIN_HEAL,
                MinorHealPotion.MAX_HEAL);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_MINOR_HEAL_POTION;
    }

    @Override
    public String getName() {
        return "Minor Heal Potion";
    }

    @Override
    public String getPluralName() {
        return "Minor Heal Potions";
    }

    @Override
    public String getDescription() {
        return "Minor Heal Potions heal you slightly when picked up.";
    }
}