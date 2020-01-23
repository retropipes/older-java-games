/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.ai.map;

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
