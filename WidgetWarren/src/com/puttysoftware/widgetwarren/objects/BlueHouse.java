/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericDirectTeleportTo;

public class BlueHouse extends GenericDirectTeleportTo {
    // Constructors
    public BlueHouse() {
        super();
    }

    @Override
    public String getName() {
        return "Blue House";
    }

    @Override
    public String getPluralName() {
        return "Blue Houses";
    }

    @Override
    public String getDescription() {
        return "Blue Houses send you inside when walked on.";
    }
}