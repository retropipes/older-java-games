/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.effects.MazeEffectConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundManager;

public class ConfusionTrap extends AbstractTrap {
    // Constructors
    public ConfusionTrap() {
	super(ObjectImageConstants.OBJECT_IMAGE_CONFUSION_TRAP);
    }

    @Override
    public String getName() {
	return "Confusion Trap";
    }

    @Override
    public String getPluralName() {
	return "Confusion Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
	Import2.getApplication().showMessage("You are confused!");
	Import2.getApplication().getGameManager().activateEffect(MazeEffectConstants.EFFECT_CONFUSED);
	SoundManager.playSound(SoundConstants.SOUND_CONFUSED);
    }

    @Override
    public String getDescription() {
	return "Confusion Traps randomly alter your controls for 6 steps when stepped on.";
    }
}