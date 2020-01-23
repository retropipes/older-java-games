/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericDirectTeleportTo;

public class MagentaHouse extends GenericDirectTeleportTo {
    // Constructors
    public MagentaHouse() {
        super();
    }

    @Override
    public String getName() {
        return "Magenta House";
    }

    @Override
    public String getPluralName() {
        return "Magenta Houses";
    }

    @Override
    public String getDescription() {
        return "Magenta Houses send you inside when walked on.";
    }
}