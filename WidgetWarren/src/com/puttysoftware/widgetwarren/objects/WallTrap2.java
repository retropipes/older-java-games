/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericWallTrap;

public class WallTrap2 extends GenericWallTrap {
    public WallTrap2() {
        super(2, new TrappedWall2());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 2 disappear when stepped on, causing all Trapped Walls 2 to also disappear.";
    }
}
