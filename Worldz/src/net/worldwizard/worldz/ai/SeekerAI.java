package net.worldwizard.worldz.ai;

import net.worldwizard.randomnumbers.RandomRange;

public class SeekerAI extends AIRoutine {
    // Fields
    private final RandomRange randMove;

    // Constructor
    public SeekerAI() {
        super();
        this.randMove = new RandomRange(-1, 1);
    }

    @Override
    public int getNextAction(final AIContext ac) {
        int[] there = ac.isEnemyNearby();
        if (there != null) {
            // Something hostile is nearby, so attack it
            if (ac.getCharacter().getAttacks() > 0) {
                this.moveX = there[0];
                this.moveY = there[1];
                return AIRoutine.ACTION_MOVE;
            } else {
                return AIRoutine.ACTION_END_TURN;
            }
        } else {
            // Look further
            for (int x = 2; x <= 6; x++) {
                there = ac.isEnemyNearby(x, x);
                if (there != null) {
                    // Found something hostile, move towards it
                    if (this.lastResult == false) {
                        // Last move failed, try to move around object
                        there = SeekerAI.turnRight45(this.moveX, this.moveY);
                        this.moveX = there[0];
                        this.moveY = there[1];
                    } else {
                        this.moveX = (int) Math.signum(there[0]);
                        this.moveY = (int) Math.signum(there[1]);
                    }
                    break;
                }
            }
            if (ac.getCharacter().getAP() > 0) {
                if (there == null) {
                    // Wander randomly
                    this.moveX = this.randMove.generate();
                    this.moveY = this.randMove.generate();
                    // Don't attack self
                    while (this.moveX == 0 && this.moveY == 0) {
                        this.moveX = this.randMove.generate();
                        this.moveY = this.randMove.generate();
                    }
                }
                return AIRoutine.ACTION_MOVE;
            } else {
                return AIRoutine.ACTION_END_TURN;
            }
        }
    }

    private static int[] turnRight45(final int x, final int y) {
        if (x == -1 && y == -1) {
            return new int[] { -1, 0 };
        } else if (x == -1 && y == 0) {
            return new int[] { -1, -1 };
        } else if (x == -1 && y == 1) {
            return new int[] { -1, 0 };
        } else if (x == 0 && y == -1) {
            return new int[] { 1, -1 };
        } else if (x == 0 && y == 1) {
            return new int[] { -1, 1 };
        } else if (x == 1 && y == -1) {
            return new int[] { 1, 0 };
        } else if (x == 1 && y == 0) {
            return new int[] { 1, -1 };
        } else if (x == 1 && y == 1) {
            return new int[] { 0, -1 };
        } else {
            return new int[] { x, y };
        }
    }
}
