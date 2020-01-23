/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericDirectTeleportTo;

public class CyanHouse extends GenericDirectTeleportTo {
    // Constructors
    public CyanHouse() {
        super();
    }

    @Override
    public String getName() {
        return "Cyan House";
    }

    @Override
    public String getPluralName() {
        return "Cyan Houses";
    }

    @Override
    public String getDescription() {
        return "Cyan Houses send you inside when walked on.";
    }
}