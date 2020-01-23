/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericWallTrap;

public class WallTrap0 extends GenericWallTrap {
    public WallTrap0() {
        super(0, new TrappedWall0());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 0 disappear when stepped on, causing all Trapped Walls 0 to also disappear.";
    }
}
