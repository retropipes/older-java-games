/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericBoots;

public class GlueBoots extends GenericBoots {
    // Constructors
    public GlueBoots() {
        super();
    }

    @Override
    public String getName() {
        return "Glue Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Glue Boots";
    }

    @Override
    public String getDescription() {
        return "Glue Boots allow walking on Ice without slipping. Note that you can only wear one pair of boots at once.";
    }
}
