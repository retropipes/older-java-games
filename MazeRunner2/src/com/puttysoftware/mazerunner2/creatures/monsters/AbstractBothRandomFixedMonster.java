/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.creatures.monsters;

abstract class AbstractBothRandomFixedMonster
        extends AbstractBothRandomMonster {
    // Constructors
    AbstractBothRandomFixedMonster() {
        super();
    }

    @Override
    public boolean scales() {
        return false;
    }
}
