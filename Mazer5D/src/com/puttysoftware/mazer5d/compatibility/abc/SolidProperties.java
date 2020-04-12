/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.abc;

import java.util.Arrays;

class SolidProperties implements Cloneable, DirectionConstants {
    // Properties
    private final boolean[] solidX;
    private final boolean[] solidI;

    // Constructors
    public SolidProperties() {
        this.solidX = new boolean[DirectionConstants.DIRECTION_COUNT];
        this.solidI = new boolean[DirectionConstants.DIRECTION_COUNT];
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
        final SolidProperties other = (SolidProperties) obj;
        if (!Arrays.equals(this.solidX, other.solidX)) {
            return false;
        }
        if (!Arrays.equals(this.solidI, other.solidI)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Arrays.hashCode(this.solidX);
        hash = 89 * hash + Arrays.hashCode(this.solidI);
        return hash;
    }

    @Override
    public SolidProperties clone() {
        final SolidProperties copy = new SolidProperties();
        System.arraycopy(this.solidX, 0, copy.solidX, 0, this.solidX.length);
        System.arraycopy(this.solidI, 0, copy.solidI, 0, this.solidI.length);
        return copy;
    }

    public boolean isSolid() {
        boolean result = false;
        for (int x = 0; x < DirectionConstants.DIRECTION_COUNT; x++) {
            result = result || this.solidX[x];
            result = result || this.solidI[x];
        }
        return result;
    }

    public boolean isDirectionallySolid(final boolean ie, final int dirX,
            final int dirY) {
        final int dir = DirectionResolver.resolveRelativeDirection(dirX, dirY);
        if (ie) {
            try {
                if (dir != DirectionConstants.DIRECTION_NONE) {
                    return this.solidX[dir];
                } else {
                    return false;
                }
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                return true;
            }
        } else {
            try {
                if (dir != DirectionConstants.DIRECTION_NONE) {
                    return this.solidI[dir];
                } else {
                    return false;
                }
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                return true;
            }
        }
    }

    public void setSolid(final boolean value) {
        for (int x = 0; x < DirectionConstants.DIRECTION_COUNT; x++) {
            this.solidX[x] = value;
            this.solidI[x] = value;
        }
    }

    public void setDirectionallySolid(final boolean ie, final int dir,
            final boolean value) {
        if (ie) {
            try {
                this.solidX[dir] = value;
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        } else {
            try {
                this.solidI[dir] = value;
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }
}
