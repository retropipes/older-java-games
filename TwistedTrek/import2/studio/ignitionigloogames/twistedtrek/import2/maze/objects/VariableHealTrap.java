/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.randomrange.RandomRange;
import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.creatures.party.PartyManager;
import studio.ignitionigloogames.twistedtrek.import2.game.GameLogicManager;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractTrap;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundManager;

public class VariableHealTrap extends AbstractTrap {
    // Fields
    private static final int MIN_HEALING = 1;

    // Constructors
    public VariableHealTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_VARIABLE_HEAL_TRAP);
    }

    @Override
    public String getName() {
        return "Variable Heal Trap";
    }

    @Override
    public String getPluralName() {
        return "Variable Heal Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
        int maxHealing = PartyManager.getParty().getLeader().getMaximumHP() / 5;
        if (maxHealing < VariableHealTrap.MIN_HEALING) {
            maxHealing = VariableHealTrap.MIN_HEALING;
        }
        final RandomRange healingGiven = new RandomRange(VariableHealTrap.MIN_HEALING, maxHealing);
        PartyManager.getParty().getLeader().heal(healingGiven.generate());
        SoundManager.playSound(SoundConstants.SOUND_HEAL);
        Import2.getApplication().getGameManager();
        GameLogicManager.decay();
    }

    @Override
    public String getDescription() {
        return "Variable Heal Traps heal you when stepped on, then disappear.";
    }
}