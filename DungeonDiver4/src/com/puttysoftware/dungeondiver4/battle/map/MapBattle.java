/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.battle.map;

import com.puttysoftware.dungeondiver4.creatures.monsters.Monster;
import com.puttysoftware.dungeondiver4.creatures.monsters.MonsterFactory;
import com.puttysoftware.dungeondiver4.creatures.party.PartyManager;
import com.puttysoftware.dungeondiver4.dungeon.objects.BattleCharacter;

public class MapBattle {
    // Fields
    private final Monster[] monsterArray;
    private static final int MAX_MONSTERS = 8;

    // Constructors
    public MapBattle() {
        super();
        this.monsterArray = new Monster[MapBattle.MAX_MONSTERS];
        // Fill array with monsters
        int numMonsters = PartyManager.getParty().getActivePCCount();
        for (int x = 0; x < numMonsters; x++) {
            this.monsterArray[x] = MonsterFactory.getNewMonsterInstance();
        }
    }

    // Methods
    private Monster[] compactMonsterArray() {
        Monster[] temp = new Monster[this.monsterArray.length];
        System.arraycopy(this.monsterArray, 0, temp, 0,
                this.monsterArray.length);
        for (int x = 0; x < temp.length; x++) {
            if (temp[x] == null) {
                if (x < temp.length - 1) {
                    temp[x] = temp[x + 1];
                }
            }
        }
        return temp;
    }

    public BattleCharacter[] getBattlers() {
        Monster[] compacted = this.compactMonsterArray();
        BattleCharacter[] battlerArray = new BattleCharacter[compacted.length];
        for (int x = 0; x < battlerArray.length; x++) {
            if (compacted[x] != null) {
                battlerArray[x] = new BattleCharacter(compacted[x]);
            }
        }
        return battlerArray;
    }
}
