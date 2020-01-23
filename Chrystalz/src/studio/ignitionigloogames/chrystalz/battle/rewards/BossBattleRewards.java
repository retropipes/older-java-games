/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.battle.rewards;

import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.battle.BattleResult;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyMember;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundManager;

class BossBattleRewards {
    // Fields
    static final String[] rewardOptions = { "Attack", "Defense", "HP", "MP" };

    // Constructor
    private BossBattleRewards() {
        // Do nothing
    }

    // Methods
    public static void doRewards(final BattleResult br) {
        final PartyMember player = PartyManager.getParty().getLeader();
        player.healAndRegenerateFully();
        if (br == BattleResult.LOST) {
            player.offsetExperiencePercentage(-10);
            player.offsetGoldPercentage(-100);
        } else if (br == BattleResult.ANNIHILATED) {
            player.offsetExperiencePercentage(-20);
            player.offsetGoldPercentage(-100);
        } else if (br == BattleResult.WON || br == BattleResult.PERFECT) {
            SoundManager.playSound(SoundConstants.BOSS_DIE);
            // Send player to next zone
            Chrystalz.getApplication().getGame().goToLevelOffset(1);
        }
    }
}
