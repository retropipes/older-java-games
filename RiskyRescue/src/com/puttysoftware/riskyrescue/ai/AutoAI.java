/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.ai;

import java.awt.Point;

public class AutoAI extends AIRoutine {
    // Constructor
    public AutoAI() {
        super();
    }

    @Override
    public int getNextAction(final AIContext ac) {
        final Point there = ac.isEnemyNearby();
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
