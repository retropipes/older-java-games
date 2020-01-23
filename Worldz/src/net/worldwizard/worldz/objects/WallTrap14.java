/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericWallTrap;

public class WallTrap14 extends GenericWallTrap {
    public WallTrap14() {
        super(14, new TrappedWall14());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 14 disappear when stepped on, causing all Trapped Walls 14 to also disappear.";
    }
}
