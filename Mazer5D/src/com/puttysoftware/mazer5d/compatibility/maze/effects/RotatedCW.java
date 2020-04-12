/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.maze.effects;

import com.puttysoftware.mazer5d.compatibility.abc.DirectionConstants;

public class RotatedCW extends MazeEffect {
    // Constructor
    public RotatedCW(final int newRounds) {
        super("Rotated CW", newRounds);
    }

    @Override
    public int modifyMove1(final int arg) {
        switch (arg) {
        case DirectionConstants.DIRECTION_NORTH:
            return DirectionConstants.DIRECTION_EAST;
        case DirectionConstants.DIRECTION_SOUTH:
            return DirectionConstants.DIRECTION_WEST;
        case DirectionConstants.DIRECTION_WEST:
            return DirectionConstants.DIRECTION_NORTH;
        case DirectionConstants.DIRECTION_EAST:
            return DirectionConstants.DIRECTION_SOUTH;
        case DirectionConstants.DIRECTION_NORTHWEST:
            return DirectionConstants.DIRECTION_NORTHEAST;
        case DirectionConstants.DIRECTION_NORTHEAST:
            return DirectionConstants.DIRECTION_SOUTHEAST;
        case DirectionConstants.DIRECTION_SOUTHWEST:
            return DirectionConstants.DIRECTION_NORTHWEST;
        case DirectionConstants.DIRECTION_SOUTHEAST:
            return DirectionConstants.DIRECTION_SOUTHWEST;
        default:
            break;
        }
        return 0;
    }
}