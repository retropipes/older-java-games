/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.effects.MazeEffectConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundManager;

public class UTurnTrap extends AbstractTrap {
    // Constructors
    public UTurnTrap() {
	super(ObjectImageConstants.OBJECT_IMAGE_U_TURN_TRAP);
    }

    @Override
    public String getName() {
	return "U Turn Trap";
    }

    @Override
    public String getPluralName() {
	return "U Turn Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
	Import2.getApplication().showMessage("Your controls are turned around!");
	Import2.getApplication().getGameManager().activateEffect(MazeEffectConstants.EFFECT_U_TURNED);
	SoundManager.playSound(SoundConstants.SOUND_CHANGE);
    }

    @Override
    public String getDescription() {
	return "U Turn Traps invert your controls for 6 steps when stepped on.";
    }
}