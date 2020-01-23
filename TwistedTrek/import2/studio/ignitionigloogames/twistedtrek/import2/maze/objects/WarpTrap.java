/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.randomrange.RandomRange;
import studio.ignitionigloogames.twistedtrek.import2.Application;
import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractTrap;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundManager;

public class WarpTrap extends AbstractTrap {
    // Constructors
    public WarpTrap() {
	super(ObjectImageConstants.OBJECT_IMAGE_WARP_TRAP);
    }

    @Override
    public String getName() {
	return "Warp Trap";
    }

    @Override
    public String getPluralName() {
	return "Warp Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
	RandomRange rr, rc, rf;
	final Application app = Import2.getApplication();
	int maxRow, maxCol, maxFloor, rRow, rCol, rFloor;
	maxRow = app.getMazeManager().getMaze().getRows() - 1;
	rr = new RandomRange(0, maxRow);
	maxCol = app.getMazeManager().getMaze().getColumns() - 1;
	rc = new RandomRange(0, maxCol);
	maxFloor = app.getMazeManager().getMaze().getFloors() - 1;
	rf = new RandomRange(0, maxFloor);
	app.getGameManager();
	do {
	    rRow = rr.generate();
	    rCol = rc.generate();
	    rFloor = rf.generate();
	} while (app.getGameManager().tryUpdatePositionAbsolute(rRow, rCol, rFloor));
	app.getGameManager().updatePositionAbsolute(rRow, rCol, rFloor);
	SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public String getDescription() {
	return "Warp Traps send anything that steps on one to a random location.";
    }
}
