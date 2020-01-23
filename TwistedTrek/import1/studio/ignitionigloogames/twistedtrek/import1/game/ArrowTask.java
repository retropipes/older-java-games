/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.game;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.generic.ArrowTypeConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTransientObject;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;
import studio.ignitionigloogames.twistedtrek.import1.objects.Arrow;
import studio.ignitionigloogames.twistedtrek.import1.objects.Empty;
import studio.ignitionigloogames.twistedtrek.import1.objects.IceArrow;
import studio.ignitionigloogames.twistedtrek.import1.objects.Wall;
import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.SoundManager;

public class ArrowTask extends Thread {
    // Fields
    private int x, y;
    private final int at;

    // Constructors
    public ArrowTask(final int newX, final int newY, final int newAT) {
	this.x = newX;
	this.y = newY;
	this.at = newAT;
    }

    @Override
    public void run() {
	boolean res = true;
	final Application app = Import1.getApplication();
	final PlayerLocationManager plMgr = app.getGameManager().getPlayerManager();
	final ObjectInventory inv = app.getGameManager().getObjectInventory();
	final int px = plMgr.getPlayerLocationX();
	final int py = plMgr.getPlayerLocationY();
	final int pz = plMgr.getPlayerLocationZ();
	final int pw = plMgr.getPlayerLocationW();
	final int[] mod = app.getGameManager().doEffects(this.x, this.y);
	this.x = mod[0];
	this.y = mod[1];
	int cumX = this.x;
	int cumY = this.y;
	final int incX = this.x;
	final int incY = this.y;
	final Maze m = app.getMazeManager().getMaze();
	m.tickTimers(pw, pz);
	MazeObject o = null;
	try {
	    o = m.getCell(px + cumX, py + cumY, pz, pw, Maze.LAYER_OBJECT);
	} catch (final ArrayIndexOutOfBoundsException ae) {
	    o = new Wall();
	}
	final GenericTransientObject a = ArrowTask.createArrowForType(this.at);
	final String suffix = MazeObject
		.resolveDirectionConstantToName(MazeObject.resolveRelativeDirection(incX, incY));
	a.setNameSuffix(suffix);
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    SoundManager.play("arrow");
	}
	while (!o.isConditionallyDirectionallySolid(true, incX, incY, inv)) {
	    res = o.arrowHitAction(px + cumX, py + cumY, pz, pw, incX, incY, this.at, inv);
	    if (!res) {
		break;
	    }
	    if (!o.isConditionallyDirectionallySolid(true, incX, incY, inv)) {
		app.getGameManager().redrawOneSquare(px + cumX, py + cumY, true, a.getName());
	    }
	    app.getGameManager().redrawOneSquare(px + cumX, py + cumY, false, new Empty().getName());
	    cumX += incX;
	    cumY += incY;
	    try {
		o = m.getCell(px + cumX, py + cumY, pz, pw, Maze.LAYER_OBJECT);
	    } catch (final ArrayIndexOutOfBoundsException ae) {
		o = new Wall();
	    }
	}
	// Fire arrow hit action for final object, if it hasn't already been
	// fired
	if (res) {
	    o.arrowHitAction(px + cumX, py + cumY, pz, pw, incX, incY, this.at, inv);
	}
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    SoundManager.play("arrowdie");
	}
	app.getGameManager().arrowDone();
    }

    private static GenericTransientObject createArrowForType(final int type) {
	switch (type) {
	case ArrowTypeConstants.ARROW_TYPE_PLAIN:
	    return new Arrow();
	case ArrowTypeConstants.ARROW_TYPE_ICE:
	    return new IceArrow();
	default:
	    return null;
	}
    }
}
