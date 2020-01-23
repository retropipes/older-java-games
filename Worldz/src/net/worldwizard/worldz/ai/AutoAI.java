package net.worldwizard.worldz.ai;

public class AutoAI extends AIRoutine {
    // Constructor
    public AutoAI() {
        super();
    }

    @Override
    public int getNextAction(final AIContext ac) {
        final int[] there = ac.isEnemyNearby();
        if (there != null) {
            // Something hostile is nearby, so attack it
            this.moveX = there[0];
            this.moveY = there[1];
            return AIRoutine.ACTION_MOVE;
        } else {
            return AIRoutine.ACTION_END_TURN;
        }
    }
}
