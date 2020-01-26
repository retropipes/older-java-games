/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.creatures.monsters;

class AppearanceRandomScalingStaticMonster
        extends AbstractAppearanceRandomScalingMonster {
    // Constructors
    AppearanceRandomScalingStaticMonster() {
        super();
    }

    @Override
    public boolean dynamic() {
        return false;
    }
}
