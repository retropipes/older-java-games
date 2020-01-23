/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericPotion;

public class MinorUnknownPotion extends GenericPotion {
    // Fields
    private static final int MIN_EFFECT = -3;
    private static final int MAX_EFFECT = 3;

    // Constructors
    public MinorUnknownPotion() {
        super(true, MinorUnknownPotion.MIN_EFFECT,
                MinorUnknownPotion.MAX_EFFECT);
    }

    @Override
    public String getName() {
        return "Minor Unknown Potion";
    }

    @Override
    public String getPluralName() {
        return "Minor Unknown Potions";
    }

    @Override
    public String getDescription() {
        return "Minor Unknown Potions might heal you or hurt you slightly when picked up.";
    }
}