/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericDirectTeleportTo;

public class RoseHouse extends GenericDirectTeleportTo {
    // Constructors
    public RoseHouse() {
        super();
    }

    @Override
    public String getName() {
        return "Rose House";
    }

    @Override
    public String getPluralName() {
        return "Rose Houses";
    }

    @Override
    public String getDescription() {
        return "Rose Houses send you inside when walked on.";
    }
}