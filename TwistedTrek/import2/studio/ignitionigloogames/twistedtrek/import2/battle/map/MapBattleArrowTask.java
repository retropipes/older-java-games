/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.battle.map;

import studio.ignitionigloogames.twistedtrek.import2.Application;
import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.battle.AbstractBattle;
import studio.ignitionigloogames.twistedtrek.import2.creatures.faiths.Faith;
import studio.ignitionigloogames.twistedtrek.import2.maze.Maze;
import studio.ignitionigloogames.twistedtrek.import2.maze.MazeConstants;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractMazeObject;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractTransientObject;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.Arrow;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.BattleCharacter;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.Empty;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.Wall;
import studio.ignitionigloogames.twistedtrek.import2.maze.utilities.DirectionResolver;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundManager;

public class MapBattleArrowTask extends Thread {
    // Fields
    private final int x, y;
    private final Maze battleMaze;
    private final BattleCharacter active;

    // Constructors
    public MapBattleArrowTask(final int newX, final int newY, final Maze maze, final BattleCharacter ac) {
	this.x = newX;
	this.y = newY;
	this.battleMaze = maze;
	this.active = ac;
	this.setName("Arrow Handler");
    }

    @Override
    public void run() {
	try {
	    boolean res = true;
	    final Application app = Import2.getApplication();
	    final Maze m = this.battleMaze;
	    final int px = this.active.getX();
	    final int py = this.active.getY();
	    int cumX = this.x;
	    int cumY = this.y;
	    final int incX = this.x;
	    final int incY = this.y;
	    AbstractMazeObject o = null;
	    try {
		o = m.getCell(px + cumX, py + cumY, 0, MazeConstants.LAYER_OBJECT);
	    } catch (final ArrayIndexOutOfBoundsException ae) {
		o = new Wall();
	    }
	    final AbstractTransientObject a = MapBattleArrowTask.createArrow();
	    final int newDir = DirectionResolver.resolveRelativeDirection(incX, incY);
	    a.setDirection(newDir);
	    SoundManager.playSound(SoundConstants.SOUND_ARROW);
	    while (res) {
		res = o.arrowHitBattleCheck();
		if (!res) {
		    break;
		}
		// Draw arrow
		app.getBattle().redrawOneBattleSquare(px + cumX, py + cumY, a);
		// Delay, for animation purposes
		Thread.sleep(MapBattleArrowSpeedConstants.getArrowSpeed());
		// Clear arrow
		app.getBattle().redrawOneBattleSquare(px + cumX, py + cumY, new Empty());
		cumX += incX;
		cumY += incY;
		try {
		    o = m.getCell(px + cumX, py + cumY, 0, MazeConstants.LAYER_OBJECT);
		} catch (final ArrayIndexOutOfBoundsException ae) {
		    res = false;
		}
	    }
	    // Check to see if the arrow hit a creature
	    BattleCharacter hit = null;
	    if (o instanceof BattleCharacter) {
		// Arrow hit a creature, hurt it
		hit = (BattleCharacter) o;
		final Faith shooter = this.active.getTemplate().getFaith();
		final Faith target = hit.getTemplate().getFaith();
		final int mult = (int) (shooter.getMultiplierForOtherFaith(target.getFaithID()) * 10);
		final AbstractBattle bl = app.getBattle();
		if (mult == 0) {
		    hit.getTemplate().doDamage(1);
		    bl.setStatusMessage("Ow, you got shot! It didn't hurt much.");
		} else if (mult == 5) {
		    hit.getTemplate().doDamage(3);
		    bl.setStatusMessage("Ow, you got shot! It hurt a little bit.");
		} else if (mult == 10) {
		    hit.getTemplate().doDamage(5);
		    bl.setStatusMessage("Ow, you got shot! It hurt somewhat.");
		} else if (mult == 20) {
		    hit.getTemplate().doDamage(8);
		    bl.setStatusMessage("Ow, you got shot! It hurt significantly!");
		}
	    }
	    // Arrow has died
	    SoundManager.playSound(SoundConstants.SOUND_ARROW_DIE);
	    app.getBattle().arrowDone(hit);
	} catch (final Throwable t) {
	    Import2.getErrorLogger().logError(t);
	}
    }

    private static AbstractTransientObject createArrow() {
	return new Arrow();
    }
}
