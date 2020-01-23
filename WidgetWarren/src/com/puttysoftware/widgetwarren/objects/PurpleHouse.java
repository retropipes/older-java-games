/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericDirectTeleportTo;

public class PurpleHouse extends GenericDirectTeleportTo {
    // Constructors
    public PurpleHouse() {
        super();
    }

    @Override
    public String getName() {
        return "Purple House";
    }

    @Override
    public String getPluralName() {
        return "Purple Houses";
    }

    @Override
    public String getDescription() {
        return "Purple Houses send you inside when walked on.";
    }
}