/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericPotion;

public class MajorHealPotion extends GenericPotion {
    // Fields
    private static final int MIN_HEAL = 6;
    private static final int MAX_HEAL = 50;

    // Constructors
    public MajorHealPotion() {
        super(true, MajorHealPotion.MIN_HEAL, MajorHealPotion.MAX_HEAL);
    }

    @Override
    public String getName() {
        return "Major Heal Potion";
    }

    @Override
    public String getPluralName() {
        return "Major Heal Potions";
    }

    @Override
    public String getDescription() {
        return "Major Heal Potions heal you significantly when picked up.";
    }
}