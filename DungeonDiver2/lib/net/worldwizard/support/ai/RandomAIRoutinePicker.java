package net.worldwizard.support.ai;

import java.util.ArrayList;

import net.worldwizard.randomnumbers.RandomRange;

public final class RandomAIRoutinePicker {
    // Fields
    private static RandomRange raip;
    private static ArrayList<AIRoutine> dynRoutines;

    // Constructors
    private RandomAIRoutinePicker() {
        // Do nothing
    }

    // Methods
    public static AIRoutine getNextRoutine() {
        if (RandomAIRoutinePicker.dynRoutines != null) {
            final int which = RandomAIRoutinePicker.raip.generate();
            return RandomAIRoutinePicker.dynRoutines.get(which);
        } else {
            return new SeekerAI();
        }
    }

    public static void addRoutine(final AIRoutine routine) {
        if (RandomAIRoutinePicker.dynRoutines == null) {
            RandomAIRoutinePicker.dynRoutines = new ArrayList<>();
        }
        RandomAIRoutinePicker.dynRoutines.add(routine);
        RandomAIRoutinePicker.raip = new RandomRange(0,
                RandomAIRoutinePicker.dynRoutines.size() - 1);
    }

    public static void removeRoutine(final AIRoutine routine) {
        if (RandomAIRoutinePicker.dynRoutines != null) {
            RandomAIRoutinePicker.dynRoutines.remove(routine);
            if (RandomAIRoutinePicker.dynRoutines.size() == 0) {
                RandomAIRoutinePicker.dynRoutines = null;
                RandomAIRoutinePicker.raip = null;
            } else {
                RandomAIRoutinePicker.raip = new RandomRange(0,
                        RandomAIRoutinePicker.dynRoutines.size() - 1);
            }
        }
    }
}
