/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericWallTrap;

public class WallTrap11 extends GenericWallTrap {
    public WallTrap11() {
        super(11, new TrappedWall11());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 11 disappear when stepped on, causing all Trapped Walls 11 to also disappear.";
    }
}
