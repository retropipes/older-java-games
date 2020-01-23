/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.ai;

public final class RandomAIRoutinePicker {
    // Constructors
    private RandomAIRoutinePicker() {
        // Do nothing
    }

    // Methods
    public static AIRoutine getNextRoutine() {
        return new SeekerAI();
    }
}
