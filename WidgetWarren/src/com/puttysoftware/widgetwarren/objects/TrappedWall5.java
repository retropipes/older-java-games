/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericTrappedWall;

public class TrappedWall5 extends GenericTrappedWall {
    public TrappedWall5() {
        super(5);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 5 disappear when any Wall Trap 5 is triggered.";
    }
}
