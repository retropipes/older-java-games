package net.worldwizard.worldz.ai;

import net.worldwizard.randomnumbers.RandomRange;

public final class RandomAIRoutinePicker {
    // Fields
    private static RandomRange raip = new RandomRange(1, 3);

    // Constructors
    private RandomAIRoutinePicker() {
        // Do nothing
    }

    // Methods
    public static AIRoutine getNextRoutine() {
        final int which = RandomAIRoutinePicker.raip.generate();
        switch (which) {
            case 1:
                return new StumblerAI();
            case 2:
                return new ScannerAI();
            case 3:
                return new SeekerAI();
            default:
                break;
        }
        // Shouldn't ever get here
        return null;
    }
}
