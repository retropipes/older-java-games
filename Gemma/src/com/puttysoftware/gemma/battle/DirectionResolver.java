/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: products@putttysoftware.com
 */
package com.puttysoftware.gemma.battle;

class DirectionResolver {
    static int resolveRelativeDirection(int dirX, int dirY) {
        int fdX = (int) Math.signum(dirX);
        int fdY = (int) Math.signum(dirY);
        if ((fdX == 0) && (fdY == 0)) {
            return DirectionConstants.DIRECTION_NONE;
        } else if ((fdX == 0) && (fdY == -1)) {
            return DirectionConstants.DIRECTION_NORTH;
        } else if ((fdX == 0) && (fdY == 1)) {
            return DirectionConstants.DIRECTION_SOUTH;
        } else if ((fdX == -1) && (fdY == 0)) {
            return DirectionConstants.DIRECTION_WEST;
        } else if ((fdX == 1) && (fdY == 0)) {
            return DirectionConstants.DIRECTION_EAST;
        } else if ((fdX == 1) && (fdY == 1)) {
            return DirectionConstants.DIRECTION_SOUTHEAST;
        } else if ((fdX == -1) && (fdY == 1)) {
            return DirectionConstants.DIRECTION_SOUTHWEST;
        } else if ((fdX == -1) && (fdY == -1)) {
            return DirectionConstants.DIRECTION_NORTHWEST;
        } else if ((fdX == 1) && (fdY == -1)) {
            return DirectionConstants.DIRECTION_NORTHEAST;
        } else {
            return DirectionConstants.DIRECTION_INVALID;
        }
    }

    static String resolveDirectionConstantToName(int dir) {
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
