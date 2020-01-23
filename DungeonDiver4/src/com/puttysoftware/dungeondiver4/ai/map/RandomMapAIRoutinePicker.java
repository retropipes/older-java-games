/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.ai.map;

public final class RandomMapAIRoutinePicker {
    // Constructors
    private RandomMapAIRoutinePicker() {
        // Do nothing
    }

    // Methods
    public static AbstractMapAIRoutine getNextRoutine() {
        return new SeekerAI();
    }
}
