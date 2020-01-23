/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.randomrange.RandomRange;
import studio.ignitionigloogames.twistedtrek.import2.Application;
import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractMovingObject;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;

public class Monster extends AbstractMovingObject {
    // Constructors
    public Monster() {
	super(false);
	this.setSavedObject(new Empty());
	this.activateTimer(1);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
	if (Import2.getApplication().getMode() != Application.STATUS_BATTLE) {
	    Import2.getApplication().getBattle().doBattle();
	    Import2.getApplication().getMazeManager().getMaze().postBattle(this, dirX, dirY, true);
	}
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
	// Move the monster
	final RandomRange r = new RandomRange(0, 7);
	final int move = r.generate();
	Import2.getApplication().getMazeManager().getMaze().updateMonsterPosition(move, dirX, dirY, this);
	this.activateTimer(1);
    }

    @Override
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_MONSTER;
    }

    @Override
    public String getName() {
	return "Monster";
    }

    @Override
    public String getPluralName() {
	return "Monsters";
    }

    @Override
    public String getDescription() {
	return "Monsters are dangerous. Encountering one starts a battle.";
    }
}
