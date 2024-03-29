/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.creatures.monsters;

import net.dynamicdungeon.dynamicdungeon.creatures.AbstractCreature;
import net.dynamicdungeon.dynamicdungeon.creatures.party.PartyManager;
import net.dynamicdungeon.dynamicdungeon.dungeon.Dungeon;

public class MonsterFactory {
    private MonsterFactory() {
        // Do nothing
    }

    public static AbstractCreature getNewMonsterInstance() {
        if (PartyManager.getParty().getDungeonLevel() == Dungeon.getMaxLevels()
                - 1) {
            return new BossMonster();
        } else {
            return new BothRandomScalingStaticMonster();
        }
    }
}
