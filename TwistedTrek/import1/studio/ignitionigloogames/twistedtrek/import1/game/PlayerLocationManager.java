/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.game;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public class PlayerLocationManager {
	// Fields
	private int oldLocX, oldLocY, oldLocZ, oldLocW;
	private int locX, locY, locZ, locW;

	// Constructors
	public PlayerLocationManager() {
		this.resetPlayerLocation();
	}

	// Methods
	public int getPlayerLocationX() {
		return this.locX;
	}

	public int getPlayerLocationY() {
		return this.locY;
	}

	public int getPlayerLocationZ() {
		return this.locZ;
	}

	public int getPlayerLocationW() {
		return this.locW;
	}

	public void setPlayerLocationX(final int val) {
		this.locX = val;
	}

	public void setPlayerLocationY(final int val) {
		this.locY = val;
	}

	public void setPlayerLocationZ(final int val) {
		this.locZ = val;
		this.fixLocationZ();
	}

	public void setPlayerLocationW(final int val) {
		this.locW = val;
	}

	public void setPlayerLocation(final int valX, final int valY, final int valZ, final int valW) {
		this.locX = valX;
		this.locY = valY;
		this.locZ = valZ;
		this.locW = valW;
		this.fixLocationZ();
	}

	public void offsetPlayerLocation(final int valX, final int valY, final int valZ, final int valW) {
		this.locX += valX;
		this.locY += valY;
		this.locZ += valZ;
		this.locW += valW;
		this.fixLocationZ();
	}

	public void offsetPlayerLocationX(final int val) {
		this.locX += val;
	}

	public void offsetPlayerLocationY(final int val) {
		this.locY += val;
	}

	public void offsetPlayerLocationZ(final int val) {
		this.locZ += val;
		this.fixLocationZ();
	}

	public void offsetPlayerLocationW(final int val) {
		this.locW += val;
	}

	public void resetPlayerLocation() {
		this.locX = 0;
		this.locY = 0;
		this.locZ = 0;
		this.locW = 0;
		this.oldLocX = 0;
		this.oldLocY = 0;
		this.oldLocZ = 0;
		this.oldLocW = 0;
	}

	public void savePlayerLocation() {
		this.oldLocX = this.locX;
		this.oldLocY = this.locY;
		this.oldLocZ = this.locZ;
		this.oldLocW = this.locW;
	}

	public void restorePlayerLocation() {
		this.locX = this.oldLocX;
		this.locY = this.oldLocY;
		this.locZ = this.oldLocZ;
		this.locW = this.oldLocW;
		this.fixLocationZ();
	}

	private void fixLocationZ() {
		final Maze m = Import1.getApplication().getMazeManager().getMaze();
		if (m.is3rdDimensionWraparoundEnabled(this.locW)) {
			if (this.locZ < 0) {
				this.locZ = m.getFloors(this.locW) - 1;
			} else if (this.locZ > m.getFloors(this.locW) - 1) {
				this.locZ = 0;
			}
		}
	}
}
