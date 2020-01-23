package net.worldwizard.worldz.ai;

import net.worldwizard.randomnumbers.RandomRange;

public class StumblerAI extends AIRoutine {
    // Fields
    private final RandomRange randMove;

    // Constructor
    public StumblerAI() {
        super();
        this.randMove = new RandomRange(-1, 1);
    }

    @Override
    public int getNextAction(final AIContext ac) {
        if (ac.getCharacter().getAP() > 0) {
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
