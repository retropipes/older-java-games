/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericWallTrap;

public class WallTrap11 extends GenericWallTrap {
    public WallTrap11() {
        super(11, new TrappedWall11());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 11 disappear when stepped on, causing all Trapped Walls 11 to also disappear.";
    }
}
