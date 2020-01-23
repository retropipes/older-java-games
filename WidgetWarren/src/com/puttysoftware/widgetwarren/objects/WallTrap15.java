/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericWallTrap;

public class WallTrap15 extends GenericWallTrap {
    public WallTrap15() {
        super(15, new TrappedWall15());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 15 disappear when stepped on, causing all Trapped Walls 15 to also disappear.";
    }
}
