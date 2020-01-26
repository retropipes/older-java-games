/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

public class DirectionResolver {
    public static final int resolveRelativeDirection(final int idirX,
            final int idirY) {
        final int dirX = (int) Math.signum(idirX);
        final int dirY = (int) Math.signum(idirY);
        if (dirX == 0 && dirY == 0) {
            return DirectionConstants.DIRECTION_NONE;
        } else if (dirX == 0 && dirY == -1) {
            return DirectionConstants.DIRECTION_NORTH;
        } else if (dirX == 0 && dirY == 1) {
            return DirectionConstants.DIRECTION_SOUTH;
        } else if (dirX == -1 && dirY == 0) {
            return DirectionConstants.DIRECTION_WEST;
        } else if (dirX == 1 && dirY == 0) {
            return DirectionConstants.DIRECTION_EAST;
        } else if (dirX == 1 && dirY == 1) {
            return DirectionConstants.DIRECTION_SOUTHEAST;
        } else if (dirX == -1 && dirY == 1) {
            return DirectionConstants.DIRECTION_SOUTHWEST;
        } else if (dirX == -1 && dirY == -1) {
            return DirectionConstants.DIRECTION_NORTHWEST;
        } else if (dirX == 1 && dirY == -1) {
            return DirectionConstants.DIRECTION_NORTHEAST;
        } else {
            return DirectionConstants.DIRECTION_INVALID;
        }
    }

    public static final int[] unresolveRelativeDirection(final int dir) {
        int[] res = new int[2];
        if (dir == DirectionConstants.DIRECTION_NONE) {
            res[0] = 0;
            res[1] = 0;
        } else if (dir == DirectionConstants.DIRECTION_NORTH) {
            res[0] = 0;
            res[1] = -1;
        } else if (dir == DirectionConstants.DIRECTION_SOUTH) {
            res[0] = 0;
            res[1] = 1;
        } else if (dir == DirectionConstants.DIRECTION_WEST) {
            res[0] = -1;
            res[1] = 0;
        } else if (dir == DirectionConstants.DIRECTION_EAST) {
            res[0] = 1;
            res[1] = 0;
        } else if (dir == DirectionConstants.DIRECTION_SOUTHEAST) {
            res[0] = 1;
            res[1] = 1;
        } else if (dir == DirectionConstants.DIRECTION_SOUTHWEST) {
            res[0] = -1;
            res[1] = 1;
        } else if (dir == DirectionConstants.DIRECTION_NORTHWEST) {
            res[0] = -1;
            res[1] = -1;
        } else if (dir == DirectionConstants.DIRECTION_NORTHEAST) {
            res[0] = 1;
            res[1] = -1;
        } else {
            res = null;
        }
        return res;
    }

    public static final String resolveDirectionConstantToName(final int dir) {
        String res = null;
        if (dir == DirectionConstants.DIRECTION_NORTH) {
            res = DirectionConstants.DIRECTION_NORTH_NAME;
        } else if (dir == DirectionConstants.DIRECTION_SOUTH) {
            res = DirectionConstants.DIRECTION_SOUTH_NAME;
        } else if (dir == DirectionConstants.DIRECTION_WEST) {
            res = DirectionConstants.DIRECTION_WEST_NAME;
        } else if (dir == DirectionConstants.DIRECTION_EAST) {
            res = DirectionConstants.DIRECTION_EAST_NAME;
        } else if (dir == DirectionConstants.DIRECTION_SOUTHEAST) {
            res = DirectionConstants.DIRECTION_SOUTHEAST_NAME;
        } else if (dir == DirectionConstants.DIRECTION_SOUTHWEST) {
            res = DirectionConstants.DIRECTION_SOUTHWEST_NAME;
        } else if (dir == DirectionConstants.DIRECTION_NORTHWEST) {
            res = DirectionConstants.DIRECTION_NORTHWEST_NAME;
        } else if (dir == DirectionConstants.DIRECTION_NORTHEAST) {
            res = DirectionConstants.DIRECTION_NORTHEAST_NAME;
        }
        return res;
    }
}
