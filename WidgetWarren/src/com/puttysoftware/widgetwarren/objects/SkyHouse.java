/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericDirectTeleportTo;

public class SkyHouse extends GenericDirectTeleportTo {
    // Constructors
    public SkyHouse() {
        super();
    }

    @Override
    public String getName() {
        return "Sky House";
    }

    @Override
    public String getPluralName() {
        return "Sky Houses";
    }

    @Override
    public String getDescription() {
        return "Sky Houses send you inside when walked on.";
    }
}