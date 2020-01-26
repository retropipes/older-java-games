/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver3.battle;

class DirectionResolver {
    static final int resolveRelativeDirection(final int dirX, final int dirY) {
        final int fdX = (int) Math.signum(dirX);
        final int fdY = (int) Math.signum(dirY);
        if (fdX == 0 && fdY == 0) {
            return DirectionConstants.DIRECTION_NONE;
        } else if (fdX == 0 && fdY == -1) {
            return DirectionConstants.DIRECTION_NORTH;
        } else if (fdX == 0 && fdY == 1) {
            return DirectionConstants.DIRECTION_SOUTH;
        } else if (fdX == -1 && fdY == 0) {
            return DirectionConstants.DIRECTION_WEST;
        } else if (fdX == 1 && fdY == 0) {
            return DirectionConstants.DIRECTION_EAST;
        } else if (fdX == 1 && fdY == 1) {
            return DirectionConstants.DIRECTION_SOUTHEAST;
        } else if (fdX == -1 && fdY == 1) {
            return DirectionConstants.DIRECTION_SOUTHWEST;
        } else if (fdX == -1 && fdY == -1) {
            return DirectionConstants.DIRECTION_NORTHWEST;
        } else if (fdX == 1 && fdY == -1) {
            return DirectionConstants.DIRECTION_NORTHEAST;
        } else {
            return DirectionConstants.DIRECTION_INVALID;
        }
    }

    static final String resolveDirectionConstantToName(final int dir) {
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
