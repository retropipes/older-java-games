/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.game.GameLogicManager;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.effects.MazeEffectConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundManager;

public class Amulet extends AbstractTrap {
    // Constructors
    public Amulet() {
	super(ObjectImageConstants.OBJECT_IMAGE_AMULET);
    }

    @Override
    public String getName() {
	return "Amulet";
    }

    @Override
    public String getPluralName() {
	return "Amulets";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
	Import2.getApplication().showMessage("You no longer slide on ice!");
	final GameLogicManager glm = Import2.getApplication().getGameManager();
	glm.activateEffect(MazeEffectConstants.EFFECT_STICKY);
	SoundManager.playSound(SoundConstants.SOUND_GRAB);
	GameLogicManager.decay();
    }

    @Override
    public String getDescription() {
	return "Amulets make you not slide on ice for 15 steps when stepped on.";
    }
}