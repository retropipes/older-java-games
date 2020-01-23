/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.generic.GenericPotion;

public class SuperHealPotion extends GenericPotion {
    // Constructors
    public SuperHealPotion() {
        super(false);
    }

    @Override
    public String getName() {
        return "Super Heal Potion";
    }

    @Override
    public String getPluralName() {
        return "Super Heal Potions";
    }

    @Override
    public int getEffectValue() {
        return WidgetWarren.getApplication().getMazeManager().getMaze()
                .getMaximumHP();
    }

    @Override
    public String getDescription() {
        return "Super Heal Potions heal you completely when picked up.";
    }
}