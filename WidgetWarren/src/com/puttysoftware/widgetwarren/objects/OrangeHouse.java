/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericDirectTeleportTo;

public class OrangeHouse extends GenericDirectTeleportTo {
    // Constructors
    public OrangeHouse() {
        super();
    }

    @Override
    public String getName() {
        return "Orange House";
    }

    @Override
    public String getPluralName() {
        return "Orange Houses";
    }

    @Override
    public String getDescription() {
        return "Orange Houses send you inside when walked on.";
    }
}