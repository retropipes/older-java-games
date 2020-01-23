/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.creatures.monsters;

class FaithRandomScalingDynamicMonster extends FaithRandomScalingBaseMonster {
    // Constructors
    FaithRandomScalingDynamicMonster() {
        super();
    }

    @Override
    public boolean dynamic() {
        return true;
    }
}
