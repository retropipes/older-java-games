/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.effects.MazeEffectConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundManager;

public class ClockwiseRotationTrap extends AbstractTrap {
    // Constructors
    public ClockwiseRotationTrap() {
	super(ObjectImageConstants.OBJECT_IMAGE_CW_ROTATION_TRAP);
    }

    @Override
    public String getName() {
	return "Clockwise Rotation Trap";
    }

    @Override
    public String getPluralName() {
	return "Clockwise Rotation Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
	SoundManager.playSound(SoundConstants.SOUND_CHANGE);
	Import2.getApplication().showMessage("Your controls are rotated!");
	Import2.getApplication().getGameManager().activateEffect(MazeEffectConstants.EFFECT_ROTATED_CLOCKWISE);
    }

    @Override
    public String getDescription() {
	return "Clockwise Rotation Traps rotate your controls clockwise for 6 steps when stepped on.";
    }
}