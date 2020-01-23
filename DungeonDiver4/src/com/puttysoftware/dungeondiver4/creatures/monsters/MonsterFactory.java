/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.creatures.monsters;

public class MonsterFactory {
    private MonsterFactory() {
        // Do nothing
    }

    public static Monster getNewMonsterInstance() {
        return new Monster();
    }
}
