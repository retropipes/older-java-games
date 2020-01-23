/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericDirectTeleportTo;

public class WhiteHouse extends GenericDirectTeleportTo {
    // Constructors
    public WhiteHouse() {
        super();
    }

    @Override
    public String getName() {
        return "White House";
    }

    @Override
    public String getPluralName() {
        return "White Houses";
    }

    @Override
    public String getDescription() {
        return "White Houses send you inside when walked on.";
    }
}