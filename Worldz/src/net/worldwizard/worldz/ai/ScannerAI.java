package net.worldwizard.worldz.ai;

import net.worldwizard.randomnumbers.RandomRange;

public class ScannerAI extends AIRoutine {
    // Fields
    private final RandomRange randMove;

    // Constructor
    public ScannerAI() {
        super();
        this.randMove = new RandomRange(-1, 1);
    }

    @Override
    public int getNextAction(final AIContext ac) {
        final int[] there = ac.isEnemyNearby();
        if (there != null) {
            if (ac.getCharacter().getAttacks() > 0) {
                // Something hostile is nearby, so attack it
                this.moveX = there[0];
                this.moveY = there[1];
                return AIRoutine.ACTION_MOVE;
            } else {
                return AIRoutine.ACTION_END_TURN;
            }
        } else {
            if (ac.getCharacter().getAP() > 0) {
                // Wander randomly
                this.moveX = this.randMove.generate();
                this.moveY = this.randMove.generate();
                // Don't attack self
                while (this.moveX == 0 && this.moveY == 0) {
                    this.moveX = this.randMove.generate();
                    this.moveY = this.randMove.generate();
                }
                return AIRoutine.ACTION_MOVE;
            } else {
                return AIRoutine.ACTION_END_TURN;
            }
        }
    }
}
