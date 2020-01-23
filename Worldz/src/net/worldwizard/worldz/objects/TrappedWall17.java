/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericTrappedWall;

public class TrappedWall17 extends GenericTrappedWall {
    public TrappedWall17() {
        super(17);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 17 disappear when any Wall Trap 17 is triggered.";
    }
}
