/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.creatures.monsters;

class AppearanceRandomScalingDynamicMonster
        extends AbstractAppearanceRandomScalingMonster {
    // Constructors
    AppearanceRandomScalingDynamicMonster() {
        super();
    }

    @Override
    public boolean dynamic() {
        return true;
    }
}
