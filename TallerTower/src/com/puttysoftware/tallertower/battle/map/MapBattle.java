/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.battle.map;

import com.puttysoftware.tallertower.creatures.monsters.MonsterFactory;
import com.puttysoftware.tallertower.maze.objects.BattleCharacter;

public class MapBattle {
    // Fields
    private final BattleCharacter monster;

    // Constructors
    public MapBattle() {
        super();
        this.monster = new BattleCharacter(
                MonsterFactory.getNewMonsterInstance());
    }

    // Methods
    public BattleCharacter getBattlers() {
        return this.monster;
    }
}
