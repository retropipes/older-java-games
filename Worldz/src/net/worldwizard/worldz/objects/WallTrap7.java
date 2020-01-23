/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericWallTrap;

public class WallTrap7 extends GenericWallTrap {
    public WallTrap7() {
        super(7, new TrappedWall7());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 7 disappear when stepped on, causing all Trapped Walls 7 to also disappear.";
    }
}
