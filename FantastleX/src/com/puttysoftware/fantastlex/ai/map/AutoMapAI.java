/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.ai.map;

import java.awt.Point;

public class AutoMapAI extends AbstractMapAIRoutine {
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
            return AbstractMapAIRoutine.ACTION_MOVE;
        } else {
            return AbstractMapAIRoutine.ACTION_END_TURN;
        }
    }
}
