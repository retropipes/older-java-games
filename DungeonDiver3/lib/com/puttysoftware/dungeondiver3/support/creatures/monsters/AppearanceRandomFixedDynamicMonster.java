/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.creatures.monsters;

class AppearanceRandomFixedDynamicMonster
        extends AppearanceRandomFixedBaseMonster {
    // Constructors
    AppearanceRandomFixedDynamicMonster() {
        super();
    }

    @Override
    public boolean dynamic() {
        return true;
    }
}
