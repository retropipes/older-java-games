/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.game.GameLogicManager;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractTrigger;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundManager;

public class ClosedDoor extends AbstractTrigger {
    // Constructors
    public ClosedDoor() {
	super();
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_CLOSED_DOOR;
    }

    // Scriptability
    @Override
    public String getName() {
	return "Closed Door";
    }

    @Override
    public String getPluralName() {
	return "Closed Doors";
    }

    @Override
    public String getDescription() {
	return "Closed Doors open when stepped on.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
	SoundManager.playSound(SoundConstants.SOUND_PICK_LOCK);
	final GameLogicManager glm = Import2.getApplication().getGameManager();
	GameLogicManager.morph(new OpenDoor());
	glm.redrawMaze();
    }
}
