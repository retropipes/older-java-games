package studio.ignitionigloogames.dungeondiver1.creatures;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.creatures.spells.SpellBookManager;
import studio.ignitionigloogames.dungeondiver1.utilities.RandomRange;

public class BattleEventHandler extends AbstractAction {
    // Serialization
    private static final long serialVersionUID = 23439639253456L;

    @Override
    public void actionPerformed(final ActionEvent e) {
        boolean triedToFlee = false;
        try {
            final String cmd = e.getActionCommand();
            final Battle b = DungeonDiver.getHoldingBag().getBattle();
            if (cmd.equals("Attack") || cmd.equals("a")) {
                // Attack
                b.updateMessageArea();
            } else if (cmd.equals("Flee") || cmd.equals("f")) {
                // Try to Flee
                triedToFlee = true;
                final RandomRange rf = new RandomRange(0, 100);
                final int runChance = rf.generate();
                if (runChance <= Battle.RUN_CHANCE) {
                    // Success
                    b.setResult(BattleResults.FLED);
                    b.doResult();
                    return;
                } else {
                    // Failure
                    b.updateMessageAreaFleeFailed();
                }
            } else if (cmd.equals("Continue")) {
                // Battle Done
                Battle.battleDone();
                return;
            } else if (cmd.equals("Cast Spell") || cmd.equals("s")) {
                // Use Skill
                final Player player = DungeonDiver.getHoldingBag().getPlayer();
                final boolean success = SpellBookManager
                        .selectAndCastSpell(player);
                if (success) {
                    b.updateMessageAreaPostSpellCast();
                }
            }
            // Check result
            final int result = b.getResult();
            if (result != BattleResults.IN_PROGRESS) {
                b.setResult(result);
                if (triedToFlee) {
                    b.lastUpdateMessageAreaFleeFailed();
                } else {
                    b.lastUpdateMessageArea();
                }
                b.doResult();
            }
        } catch (final Throwable t) {
            DungeonDiver.debug(t);
        }
    }
}
