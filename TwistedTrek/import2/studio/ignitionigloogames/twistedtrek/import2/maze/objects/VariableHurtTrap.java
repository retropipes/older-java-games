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

public class VariableHurtTrap extends AbstractTrap {
    // Fields
    private static final int MIN_DAMAGE = 1;

    // Constructors
    public VariableHurtTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_VARIABLE_HURT_TRAP);
    }

    @Override
    public String getName() {
        return "Variable Hurt Trap";
    }

    @Override
    public String getPluralName() {
        return "Variable Hurt Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
        int maxDamage = PartyManager.getParty().getLeader().getMaximumHP() / 5;
        if (maxDamage < VariableHurtTrap.MIN_DAMAGE) {
            maxDamage = VariableHurtTrap.MIN_DAMAGE;
        }
        final RandomRange damageDealt = new RandomRange(VariableHurtTrap.MIN_DAMAGE, maxDamage);
        PartyManager.getParty().getLeader().doDamage(damageDealt.generate());
        SoundManager.playSound(SoundConstants.SOUND_BARRIER);
        Import2.getApplication().getGameManager();
        GameLogicManager.decay();
    }

    @Override
    public String getDescription() {
        return "Variable Hurt Traps hurt you when stepped on, then disappear.";
    }
}