/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericMultipleKey;

public class SilverSquare extends GenericMultipleKey {
    // Constructors
    public SilverSquare() {
        super();
    }

    @Override
    public String getName() {
        return "Silver Square";
    }

    @Override
    public String getPluralName() {
        return "Silver Squares";
    }

    @Override
    public String getDescription() {
        return "Silver Squares are the keys to Silver Walls.";
    }
}