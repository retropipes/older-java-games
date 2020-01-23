/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.ai;

import java.awt.Point;

public class AutoAI extends AIRoutine {
    // Constructor
    public AutoAI() {
        super();
    }

    @Override
    public int getNextAction(AIContext ac) {
        Point there = ac.isEnemyNearby();
        if (there != null) {
            // Something hostile is nearby, so attack it
            this.moveX = there.x;
            this.moveY = there.y;
            return AIRoutine.ACTION_MOVE;
        } else {
            return AIRoutine.ACTION_END_TURN;
        }
    }
}
