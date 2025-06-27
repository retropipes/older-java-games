/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.effects.MazeEffectConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundManager;

public class DizzinessTrap extends AbstractTrap {
    // Constructors
    public DizzinessTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_DIZZINESS_TRAP);
    }

    @Override
    public String getName() {
        return "Dizziness Trap";
    }

    @Override
    public String getPluralName() {
        return "Dizziness Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
        Import2.getApplication().showMessage("You feel dizzy!");
        Import2.getApplication().getGameManager().activateEffect(MazeEffectConstants.EFFECT_DIZZY);
        SoundManager.playSound(SoundConstants.SOUND_DIZZY);
    }

    @Override
    public String getDescription() {
        return "Dizziness Traps randomly alter your controls each step for 3 steps when stepped on.";
    }
}