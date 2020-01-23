/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.creatures.monsters;

abstract class FaithRandomFixedBaseMonster extends FaithRandomBaseMonster {
    // Constructors
    FaithRandomFixedBaseMonster() {
        super();
    }

    @Override
    public boolean scales() {
        return false;
    }
}
