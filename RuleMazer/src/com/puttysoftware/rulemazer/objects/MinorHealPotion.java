/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericPotion;

public class MinorHealPotion extends GenericPotion {
    // Fields
    private static final int MIN_HEAL = 1;
    private static final int MAX_HEAL = 5;

    // Constructors
    public MinorHealPotion() {
        super(true, MinorHealPotion.MIN_HEAL, MinorHealPotion.MAX_HEAL);
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