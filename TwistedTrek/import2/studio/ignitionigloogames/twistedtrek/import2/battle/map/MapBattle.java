/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.battle.map;

import studio.ignitionigloogames.twistedtrek.import2.creatures.monsters.MonsterFactory;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.BattleCharacter;

public class MapBattle {
    // Fields
    private final BattleCharacter monster;

    // Constructors
    public MapBattle() {
	super();
	this.monster = new BattleCharacter(MonsterFactory.getNewMonsterInstance());
    }

    // Methods
    public BattleCharacter getBattlers() {
	return this.monster;
    }
}
