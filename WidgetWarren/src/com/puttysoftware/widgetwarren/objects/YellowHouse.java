/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericDirectTeleportTo;

public class YellowHouse extends GenericDirectTeleportTo {
    // Constructors
    public YellowHouse() {
        super();
    }

    @Override
    public String getName() {
        return "Yellow House";
    }

    @Override
    public String getPluralName() {
        return "Yellow Houses";
    }

    @Override
    public String getDescription() {
        return "Yellow Houses send you inside when walked on.";
    }
}