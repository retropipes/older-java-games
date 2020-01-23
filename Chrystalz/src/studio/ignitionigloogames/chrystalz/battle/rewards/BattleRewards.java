package studio.ignitionigloogames.chrystalz.battle.rewards;

import studio.ignitionigloogames.chrystalz.battle.BattleResult;
import studio.ignitionigloogames.chrystalz.battle.types.AbstractBattleType;

public abstract class BattleRewards {
    public static void doRewards(final AbstractBattleType bt,
            final BattleResult br, final long bonusExp, final int bonusGold) {
        if (bt.isFinalBossBattle()) {
            FinalBossBattleRewards.doRewards(br);
        } else if (bt.isBossBattle()) {
            BossBattleRewards.doRewards(br);
        } else {
            RegularBattleRewards.doRewards(br, bonusExp, bonusGold);
        }
    }
}