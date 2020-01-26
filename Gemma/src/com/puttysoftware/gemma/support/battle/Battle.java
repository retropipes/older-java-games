/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.battle;

import com.puttysoftware.gemma.support.creatures.PartyManager;
import com.puttysoftware.gemma.support.creatures.StatConstants;
import com.puttysoftware.gemma.support.creatures.monsters.BaseMonster;
import com.puttysoftware.gemma.support.creatures.monsters.MonsterFactory;
import com.puttysoftware.gemma.support.map.objects.BattleCharacter;

public class Battle {
    // Fields
    private final BaseMonster[] monsterArray;
    private static final int MAX_MONSTERS = 5;

    // Constructors
    public Battle() {
        super();
        this.monsterArray = new BaseMonster[Battle.MAX_MONSTERS];
        // Fill array with monsters
        if (PartyManager.getParty().getLeader()
                .getLevel() < StatConstants.LEVEL_MAX) {
            final int numMonsters = PartyManager.getParty().getActivePCCount();
            for (int x = 0; x < numMonsters; x++) {
                this.monsterArray[x] = MonsterFactory.getNewMonsterInstance();
            }
        } else {
            this.monsterArray[0] = MonsterFactory.getBossMonsterInstance();
        }
    }

    // Methods
    public boolean isBossBattle() {
        return PartyManager.getParty().getLeader()
                .getLevel() == StatConstants.LEVEL_MAX;
    }

    private BaseMonster[] compactMonsterArray() {
        final BaseMonster[] temp = new BaseMonster[this.monsterArray.length];
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
        final BaseMonster[] compacted = this.compactMonsterArray();
        final BattleCharacter[] battlerArray = new BattleCharacter[compacted.length];
        for (int x = 0; x < battlerArray.length; x++) {
            if (compacted[x] != null) {
                battlerArray[x] = new BattleCharacter(compacted[x]);
            }
        }
        return battlerArray;
    }
}
