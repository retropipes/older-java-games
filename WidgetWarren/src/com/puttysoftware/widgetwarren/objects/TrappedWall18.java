/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericTrappedWall;

public class TrappedWall18 extends GenericTrappedWall {
    public TrappedWall18() {
        super(18);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 18 disappear when any Wall Trap 18 is triggered.";
    }
}
