/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.ai.map;

import java.awt.Point;

public class AutoMapAI extends MapAIRoutine {
    // Constructor
    public AutoMapAI() {
        super();
    }

    @Override
    public int getNextAction(final MapAIContext ac) {
        final Point there = ac.isEnemyNearby();
        if (there != null) {
            // Something hostile is nearby, so attack it
            this.moveX = there.x;
            this.moveY = there.y;
            return MapAIRoutine.ACTION_MOVE;
        } else {
            return MapAIRoutine.ACTION_END_TURN;
        }
    }
}
