/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.InfiniteRecursionException;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericMovableObject;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public class Pit extends StairsDown {
	// Constructors
	public Pit() {
		super(true);
	}

	@Override
	public String getName() {
		return "Pit";
	}

	@Override
	public String getPluralName() {
		return "Pits";
	}

	@Override
	public boolean preMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
		return this.searchNestedPits(dirX, dirY,
				Import1.getApplication().getGameManager().getPlayerManager().getPlayerLocationZ() - 1, inv);
	}

	private boolean searchNestedPits(final int dirX, final int dirY, final int floor, final ObjectInventory inv) {
		final Application app = Import1.getApplication();
		// Stop infinite recursion
		final int level = app.getGameManager().getPlayerManager().getPlayerLocationW();
		final int lcl = -app.getMazeManager().getMaze().getFloors(level);
		if (floor <= lcl) {
			throw new InfiniteRecursionException();
		}
		if (app.getGameManager().doesFloorExist(floor)) {
			final MazeObject obj = app.getMazeManager().getMaze().getCell(dirX, dirY, floor,
					app.getGameManager().getPlayerManager().getPlayerLocationW(), Maze.LAYER_OBJECT);
			if (obj.isConditionallySolid(inv)) {
				return false;
			} else {
				if (obj.getName().equals("Pit") || obj.getName().equals("Invisible Pit")) {
					return this.searchNestedPits(dirX, dirY, floor - 1, inv);
				} else {
					return true;
				}
			}
		} else {
			return false;
		}
	}

	@Override
	public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
		final Application app = Import1.getApplication();
		app.getGameManager().updatePositionAbsolute(this.getDestinationRow(), this.getDestinationColumn(),
				this.getDestinationFloor(), this.getDestinationLevel());
		if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
			MazeObject.playFallSound();
		}
	}

	@Override
	public void pushIntoAction(final ObjectInventory inv, final MazeObject pushed, final int x, final int y,
			final int z, final int w) {
		final Application app = Import1.getApplication();
		try {
			this.searchNestedPits(x, y, z - 1, inv);
			if (pushed.isPushable()) {
				final GenericMovableObject pushedInto = (GenericMovableObject) pushed;
				app.getGameManager().updatePushedIntoPositionAbsolute(x, y, z - 1, w, x, y, z, w, pushedInto, this);
				if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
					MazeObject.playFallSound();
				}
			}
		} catch (final InfiniteRecursionException ir) {
			if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
				MazeObject.playFallSound();
			}
			Import1.getApplication().getMazeManager().getMaze().setCell(new Empty(), x, y, z, w, Maze.LAYER_OBJECT);
		}
	}

	@Override
	public boolean isConditionallyDirectionallySolid(final boolean ie, final int dirX, final int dirY,
			final ObjectInventory inv) {
		final Application app = Import1.getApplication();
		if (!app.getGameManager().isFloorBelow()) {
			if (ie) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public void editorPlaceHook() {
		// Do nothing
	}

	@Override
	public MazeObject editorPropertiesHook() {
		return null;
	}

	@Override
	public byte getObjectID() {
		return (byte) 10;
	}

	@Override
	public String getDescription() {
		return "Pits dump anything that wanders in to the floor below. If one of these is placed on the bottom-most floor, it is impassable.";
	}
}
