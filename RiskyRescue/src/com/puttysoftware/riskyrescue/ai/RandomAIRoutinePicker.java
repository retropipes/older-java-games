/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.ai;

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
