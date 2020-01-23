/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericDirectTeleportTo;

public class SeaweedHouse extends GenericDirectTeleportTo {
    // Constructors
    public SeaweedHouse() {
        super();
    }

    @Override
    public String getName() {
        return "Seaweed House";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Houses";
    }

    @Override
    public String getDescription() {
        return "Seaweed Houses send you inside when walked on.";
    }
}