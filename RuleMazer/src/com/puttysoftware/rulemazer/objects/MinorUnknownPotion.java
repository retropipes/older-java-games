/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericPotion;

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