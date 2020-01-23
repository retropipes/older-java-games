/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.battle.map;

import com.puttysoftware.fantastlex.creatures.monsters.AbstractMonster;
import com.puttysoftware.fantastlex.creatures.monsters.MonsterFactory;
import com.puttysoftware.fantastlex.creatures.party.PartyManager;
import com.puttysoftware.fantastlex.maze.objects.BattleCharacter;

public class MapBattle {
    // Fields
    private final AbstractMonster[] monsterArray;
    private static final int MAX_MONSTERS = 90;

    // Constructors
    public MapBattle() {
        super();
        this.monsterArray = new AbstractMonster[MapBattle.MAX_MONSTERS];
        // Fill array with monsters
        final int numMonsters = PartyManager.getParty().getActivePCCount();
        for (int x = 0; x < numMonsters; x++) {
            this.monsterArray[x] = MonsterFactory.getNewMonsterInstance(true,
                    true, true, false);
        }
    }

    // Methods
    private AbstractMonster[] compactMonsterArray() {
        final AbstractMonster[] temp = new AbstractMonster[this.monsterArray.length];
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
        final AbstractMonster[] compacted = this.compactMonsterArray();
        final BattleCharacter[] battlerArray = new BattleCharacter[compacted.length];
        for (int x = 0; x < battlerArray.length; x++) {
            if (compacted[x] != null) {
                battlerArray[x] = new BattleCharacter(compacted[x]);
            }
        }
        return battlerArray;
    }
}
