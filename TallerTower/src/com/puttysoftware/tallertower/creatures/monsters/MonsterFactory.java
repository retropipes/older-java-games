/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.creatures.monsters;

import com.puttysoftware.tallertower.creatures.AbstractCreature;
import com.puttysoftware.tallertower.creatures.party.PartyManager;
import com.puttysoftware.tallertower.maze.Maze;

public class MonsterFactory {
    private MonsterFactory() {
        // Do nothing
    }

    public static AbstractCreature getNewMonsterInstance() {
        if (PartyManager.getParty().getTowerLevel() == Maze.getMaxLevels() - 1) {
            return new BossMonster();
        } else {
            return new BothRandomScalingStaticMonster();
        }
    }
}
