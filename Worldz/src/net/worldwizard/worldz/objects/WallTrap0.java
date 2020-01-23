/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericWallTrap;

public class WallTrap0 extends GenericWallTrap {
    public WallTrap0() {
        super(0, new TrappedWall0());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 0 disappear when stepped on, causing all Trapped Walls 0 to also disappear.";
    }
}
