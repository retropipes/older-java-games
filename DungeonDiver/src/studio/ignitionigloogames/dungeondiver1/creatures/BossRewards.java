package studio.ignitionigloogames.dungeondiver1.creatures;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.creatures.spells.SpellBookManager;
import studio.ignitionigloogames.dungeondiver1.gui.ListDialog;

public class BossRewards {
    // Methods
    public void doRewards() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        final String[] rewardOptions = { "Attack", "Defense", "HP", "MP" };
        String dialogResult = null;
        while (dialogResult == null) {
            dialogResult = ListDialog.showDialog(
                    "You get to increase a stat permanently.\nWhich Stat?",
                    "Boss Rewards", rewardOptions, rewardOptions[0]);
        }
        if (dialogResult.equals(rewardOptions[0])) {
            // Attack
            player.spendPointOnAttack();
        } else if (dialogResult.equals(rewardOptions[1])) {
            // Defense
            player.spendPointOnDefense();
        } else if (dialogResult.equals(rewardOptions[2])) {
            // HP
            player.spendPointOnHP();
        } else {
            // MP
            player.spendPointOnMP();
        }
        final int c = SpellBookManager.selectClass();
        if (c != 0) {
            final int pAtk = player.getPermanentAttackPoints();
            final int pDef = player.getPermanentDefensePoints();
            final int pHP = player.getPermanentHPPoints();
            final int pMP = player.getPermanentMPPoints();
            final int k = player.getKills();
            final Player p = ClassManager.getPlayerInstancePostKill(c, pAtk,
                    pDef, pHP, pMP, k);
            DungeonDiver.getHoldingBag().setPlayer(p);
        }
    }
}
