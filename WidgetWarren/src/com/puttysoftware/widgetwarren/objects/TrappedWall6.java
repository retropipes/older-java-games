/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericTrappedWall;

public class TrappedWall6 extends GenericTrappedWall {
    public TrappedWall6() {
        super(6);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 6 disappear when any Wall Trap 6 is triggered.";
    }
}
