/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.battle.types;

import studio.ignitionigloogames.chrystalz.creatures.monsters.MonsterFactory;
import studio.ignitionigloogames.chrystalz.dungeon.objects.BattleCharacter;

class BossBattle extends AbstractBattleType {
    // Fields
    final BattleCharacter monster;

    // Constructors
    public BossBattle() {
        super();
        this.boss = true;
        this.monster = new BattleCharacter(MonsterFactory.getNewBossInstance());
    }

    // Methods
    @Override
    public BattleCharacter getBattlers() {
        return this.monster;
    }
}
