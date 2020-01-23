/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.battle.types;

import studio.ignitionigloogames.chrystalz.creatures.monsters.MonsterFactory;
import studio.ignitionigloogames.chrystalz.dungeon.objects.BattleCharacter;

class RegularBattle extends AbstractBattleType {
    // Fields
    final BattleCharacter monster;

    // Constructors
    public RegularBattle() {
        super();
        this.monster = new BattleCharacter(
                MonsterFactory.getNewMonsterInstance());
    }

    // Methods
    @Override
    public BattleCharacter getBattlers() {
        return this.monster;
    }
}
