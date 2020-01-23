/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericTrappedWall;

public class TrappedWall14 extends GenericTrappedWall {
    public TrappedWall14() {
        super(14);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 14 disappear when any Wall Trap 14 is triggered.";
    }
}
