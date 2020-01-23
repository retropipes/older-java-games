/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWall;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public class FadingWall extends GenericWall {
    // Fields
    private static final int SCAN_LIMIT = 3;

    // Constructors
    public FadingWall() {
	super();
	this.activateTimer(1);
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
	// Disappear if the player is close to us
	boolean scanResult = false;
	final Application app = Import1.getApplication();
	final int pz = app.getGameManager().getPlayerManager().getPlayerLocationZ();
	final int pw = app.getGameManager().getPlayerManager().getPlayerLocationW();
	final int pl = Maze.LAYER_OBJECT;
	final String targetName = new Player().getName();
	scanResult = app.getMazeManager().getMaze().radialScan(dirX, dirY, pz, pw, pl, FadingWall.SCAN_LIMIT,
		targetName);
	if (scanResult) {
	    app.getGameManager().morph(new Empty(), dirX, dirY, pz, pw);
	}
	this.activateTimer(1);
    }

    @Override
    public String getName() {
	return "Fading Wall";
    }

    @Override
    public String getGameName() {
	return "Wall";
    }

    @Override
    public String getPluralName() {
	return "Fading Walls";
    }

    @Override
    public byte getObjectID() {
	return (byte) 0;
    }

    @Override
    public String getDescription() {
	return "Fading Walls disappear when you get close to them.";
    }
}
