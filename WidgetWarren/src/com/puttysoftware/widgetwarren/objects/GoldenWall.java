/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericMultipleLock;

public class GoldenWall extends GenericMultipleLock {
    // Constructors
    public GoldenWall() {
        super(new GoldenSquare());
    }

    @Override
    public String getName() {
        return "Golden Wall";
    }

    @Override
    public String getPluralName() {
        return "Golden Walls";
    }

    @Override
    public String getDescription() {
        return "Golden Walls are impassable without enough Golden Squares.";
    }
}