/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericInvisibleChainTeleportDown;

public class InvisiblePit extends GenericInvisibleChainTeleportDown {
    // Constructors
    public InvisiblePit() {
        super();
    }

    @Override
    public String getName() {
        return "Invisible Pit";
    }

    @Override
    public String getGameName() {
        return "Empty";
    }

    @Override
    public String getPluralName() {
        return "Invislble Pits";
    }

    @Override
    public String getDescription() {
        return "Invisible Pits dump anything that wanders in to the floor below. If one of these is placed on the bottom-most floor, it is impassable.";
    }
}
