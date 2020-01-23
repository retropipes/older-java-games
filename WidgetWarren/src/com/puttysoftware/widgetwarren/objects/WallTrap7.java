/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericWallTrap;

public class WallTrap7 extends GenericWallTrap {
    public WallTrap7() {
        super(7, new TrappedWall7());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 7 disappear when stepped on, causing all Trapped Walls 7 to also disappear.";
    }
}
