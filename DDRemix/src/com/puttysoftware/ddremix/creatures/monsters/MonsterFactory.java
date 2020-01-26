/*  DDRemix: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.creatures.monsters;

import com.puttysoftware.ddremix.creatures.AbstractCreature;
import com.puttysoftware.ddremix.creatures.party.PartyManager;
import com.puttysoftware.ddremix.maze.Maze;

public class MonsterFactory {
    private MonsterFactory() {
        // Do nothing
    }

    public static AbstractCreature getNewMonsterInstance() {
        if (PartyManager.getParty().getDungeonLevel() == Maze.getMaxLevels()
                - 1) {
            return new BossMonster();
        } else {
            return new BothRandomScalingStaticMonster();
        }
    }
}
