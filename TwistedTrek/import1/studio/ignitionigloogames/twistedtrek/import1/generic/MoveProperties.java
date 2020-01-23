/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import java.util.Arrays;

class MoveProperties implements DirectionConstants {
    // Properties
    private final boolean[] push;
    private final boolean[] pull;
    private final boolean[] pushInto;
    private final boolean[] pullInto;
    private final boolean[] pushOut;
    private final boolean[] pullOut;

    // Constructors
    public MoveProperties() {
	this.push = new boolean[DirectionConstants.DIRECTION_COUNT];
	this.pull = new boolean[DirectionConstants.DIRECTION_COUNT];
	this.pushInto = new boolean[DirectionConstants.DIRECTION_COUNT];
	this.pullInto = new boolean[DirectionConstants.DIRECTION_COUNT];
	this.pushOut = new boolean[DirectionConstants.DIRECTION_COUNT];
	this.pullOut = new boolean[DirectionConstants.DIRECTION_COUNT];
    }

    // Methods
    @Override
    public boolean equals(final Object obj) {
	if (obj == null) {
	    return false;
	}
	if (this.getClass() != obj.getClass()) {
	    return false;
	}
	final MoveProperties other = (MoveProperties) obj;
	if (!Arrays.equals(this.push, other.push)) {
	    return false;
	}
	if (!Arrays.equals(this.pull, other.pull)) {
	    return false;
	}
	if (!Arrays.equals(this.pushInto, other.pushInto)) {
	    return false;
	}
	if (!Arrays.equals(this.pullInto, other.pullInto)) {
	    return false;
	}
	if (!Arrays.equals(this.pushOut, other.pushOut)) {
	    return false;
	}
	if (!Arrays.equals(this.pullOut, other.pullOut)) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 17 * hash + Arrays.hashCode(this.push);
	hash = 17 * hash + Arrays.hashCode(this.pull);
	hash = 17 * hash + Arrays.hashCode(this.pushInto);
	hash = 17 * hash + Arrays.hashCode(this.pullInto);
	hash = 17 * hash + Arrays.hashCode(this.pushOut);
	hash = 17 * hash + Arrays.hashCode(this.pullOut);
	return hash;
    }

    @Override
    public MoveProperties clone() {
	final MoveProperties copy = new MoveProperties();
	System.arraycopy(this.push, 0, copy.push, 0, this.push.length);
	System.arraycopy(this.pull, 0, copy.pull, 0, this.pull.length);
	System.arraycopy(this.pushOut, 0, copy.pushOut, 0, this.pushOut.length);
	System.arraycopy(this.pullOut, 0, copy.pullOut, 0, this.pullOut.length);
	System.arraycopy(this.pushInto, 0, copy.pushInto, 0, this.pushInto.length);
	System.arraycopy(this.pullInto, 0, copy.pullInto, 0, this.pullInto.length);
	return copy;
    }

    public boolean isPushable() {
	boolean result = true;
	for (int x = 0; x < DirectionConstants.DIRECTION_COUNT; x++) {
	    result = result && this.push[x];
	}
	return result;
    }

    public boolean isDirectionallyPushable(final int dirX, final int dirY) {
	final int dir = MazeObject.resolveRelativeDirection(dirX, dirY);
	try {
	    if (dir != DirectionConstants.DIRECTION_NONE) {
		return this.push[dir];
	    } else {
		return false;
	    }
	} catch (final ArrayIndexOutOfBoundsException aioob) {
	    return false;
	}
    }

    public boolean isPullable() {
	boolean result = true;
	for (int x = 0; x < DirectionConstants.DIRECTION_COUNT; x++) {
	    result = result && this.pull[x];
	}
	return result;
    }

    public boolean isDirectionallyPullable(final int dirX, final int dirY) {
	final int dir = MazeObject.resolveRelativeDirection(dirX, dirY);
	try {
	    if (dir != DirectionConstants.DIRECTION_NONE) {
		return this.pull[dir];
	    } else {
		return false;
	    }
	} catch (final ArrayIndexOutOfBoundsException aioob) {
	    return false;
	}
    }

    public boolean isPushableInto() {
	boolean result = true;
	for (int x = 0; x < DirectionConstants.DIRECTION_COUNT; x++) {
	    result = result && this.pushInto[x];
	}
	return result;
    }

    public boolean isDirectionallyPushableInto(final int dirX, final int dirY) {
	final int dir = MazeObject.resolveRelativeDirection(dirX, dirY);
	try {
	    if (dir != DirectionConstants.DIRECTION_NONE) {
		return this.pushInto[dir];
	    } else {
		return false;
	    }
	} catch (final ArrayIndexOutOfBoundsException aioob) {
	    return false;
	}
    }

    public boolean isPullableInto() {
	boolean result = true;
	for (int x = 0; x < DirectionConstants.DIRECTION_COUNT; x++) {
	    result = result && this.pullInto[x];
	}
	return result;
    }

    public boolean isDirectionallyPullableInto(final int dirX, final int dirY) {
	final int dir = MazeObject.resolveRelativeDirection(dirX, dirY);
	try {
	    if (dir != DirectionConstants.DIRECTION_NONE) {
		return this.pullInto[dir];
	    } else {
		return false;
	    }
	} catch (final ArrayIndexOutOfBoundsException aioob) {
	    return false;
	}
    }

    public boolean isPushableOut() {
	boolean result = true;
	for (int x = 0; x < DirectionConstants.DIRECTION_COUNT; x++) {
	    result = result && this.pushOut[x];
	}
	return result;
    }

    public boolean isDirectionallyPushableOut(final int dirX, final int dirY) {
	final int dir = MazeObject.resolveRelativeDirection(dirX, dirY);
	try {
	    if (dir != DirectionConstants.DIRECTION_NONE) {
		return this.pushOut[dir];
	    } else {
		return false;
	    }
	} catch (final ArrayIndexOutOfBoundsException aioob) {
	    return false;
	}
    }

    public boolean isPullableOut() {
	boolean result = true;
	for (int x = 0; x < DirectionConstants.DIRECTION_COUNT; x++) {
	    result = result && this.pullOut[x];
	}
	return result;
    }

    public boolean isDirectionallyPullableOut(final int dirX, final int dirY) {
	final int dir = MazeObject.resolveRelativeDirection(dirX, dirY);
	try {
	    if (dir != DirectionConstants.DIRECTION_NONE) {
		return this.pullOut[dir];
	    } else {
		return false;
	    }
	} catch (final ArrayIndexOutOfBoundsException aioob) {
	    return false;
	}
    }

    public void setPushable(final boolean value) {
	for (int x = 0; x < DirectionConstants.DIRECTION_COUNT; x++) {
	    this.push[x] = value;
	}
    }

    public void setDirectionallyPushable(final int dir, final boolean value) {
	try {
	    this.push[dir] = value;
	} catch (final ArrayIndexOutOfBoundsException aioob) {
	    // Do nothing
	}
    }

    public void setPullable(final boolean value) {
	for (int x = 0; x < DirectionConstants.DIRECTION_COUNT; x++) {
	    this.pull[x] = value;
	}
    }

    public void setDirectionallyPullable(final int dir, final boolean value) {
	try {
	    this.pull[dir] = value;
	} catch (final ArrayIndexOutOfBoundsException aioob) {
	    // Do nothing
	}
    }

    public void setPushableInto(final boolean value) {
	for (int x = 0; x < DirectionConstants.DIRECTION_COUNT; x++) {
	    this.pushInto[x] = value;
	}
    }

    public void setDirectionallyPushableInto(final int dir, final boolean value) {
	try {
	    this.pushInto[dir] = value;
	} catch (final ArrayIndexOutOfBoundsException aioob) {
	    // Do nothing
	}
    }

    public void setPullableInto(final boolean value) {
	for (int x = 0; x < DirectionConstants.DIRECTION_COUNT; x++) {
	    this.pullInto[x] = value;
	}
    }

    public void setDirectionallyPullableInto(final int dir, final boolean value) {
	try {
	    this.pullInto[dir] = value;
	} catch (final ArrayIndexOutOfBoundsException aioob) {
	    // Do nothing
	}
    }

    public void setPushableOut(final boolean value) {
	for (int x = 0; x < DirectionConstants.DIRECTION_COUNT; x++) {
	    this.pushOut[x] = value;
	}
    }

    public void setDirectionallyPushableOut(final int dir, final boolean value) {
	try {
	    this.pushOut[dir] = value;
	} catch (final ArrayIndexOutOfBoundsException aioob) {
	    // Do nothing
	}
    }

    public void setPullableOut(final boolean value) {
	for (int x = 0; x < DirectionConstants.DIRECTION_COUNT; x++) {
	    this.pullOut[x] = value;
	}
    }

    public void setDirectionallyPullableOut(final int dir, final boolean value) {
	try {
	    this.pullOut[dir] = value;
	} catch (final ArrayIndexOutOfBoundsException aioob) {
	    // Do nothing
	}
    }
}
