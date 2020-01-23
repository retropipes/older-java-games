/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericWallTrap;

public class WallTrap1 extends GenericWallTrap {
    public WallTrap1() {
        super(1, new TrappedWall1());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 1 disappear when stepped on, causing all Trapped Walls 1 to also disappear.";
    }
}
