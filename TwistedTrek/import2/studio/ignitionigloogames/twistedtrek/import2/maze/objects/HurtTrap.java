/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.creatures.party.PartyManager;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractTrap;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundManager;

public class HurtTrap extends AbstractTrap {
    // Constructors
    public HurtTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_HURT_TRAP);
    }

    @Override
    public String getName() {
        return "Hurt Trap";
    }

    @Override
    public String getPluralName() {
        return "Hurt Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
        int damage = PartyManager.getParty().getLeader().getMaximumHP() / 10;
        if (damage < 1) {
            damage = 1;
        }
        PartyManager.getParty().getLeader().doDamage(damage);
        SoundManager.playSound(SoundConstants.SOUND_BARRIER);
    }

    @Override
    public String getDescription() {
        return "Hurt Traps hurt you when stepped on.";
    }
}