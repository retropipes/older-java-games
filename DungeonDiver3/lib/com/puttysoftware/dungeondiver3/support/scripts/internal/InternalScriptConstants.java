/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.scripts.internal;

public class InternalScriptConstants {
    // Action Code Arguments
    public static final boolean MOVE_RELATIVE = false;
    public static final boolean MOVE_ABSOLUTE = true;
    // Action Code Argument Counts
    private static final int ARGC_NONE = 0;
    private static final int ARGC_MESSAGE = 1;
    private static final int ARGC_SOUND = 1;
    private static final int ARGC_SHOP = 1;
    private static final int ARGC_MOVE = 5;
    private static final int ARGC_DECAY = 0;
    private static final int ARGC_SWAP_PAIRS = 2;
    private static final int ARGC_REDRAW = 0;
    private static final int ARGC_ADD_TO_SCORE = 1;
    private static final int ARGC_RANDOM_CHANCE = 1;
    private static final int ARGC_BATTLE = 0;
    private static final int ARGC_RELATIVE_LEVEL_CHANGE = 1;
    // Action Code Argument Types
    private static final Class<?>[] ARGT_NONE = null;
    private static final Class<?>[] ARGT_MESSAGE = new Class[] { String.class };
    private static final Class<?>[] ARGT_SOUND = new Class[] { int.class };
    private static final Class<?>[] ARGT_SHOP = new Class[] { int.class };
    private static final Class<?>[] ARGT_MOVE = new Class[] { boolean.class,
            boolean.class, int.class, int.class, int.class };
    private static final Class<?>[] ARGT_DECAY = null;
    private static final Class<?>[] ARGT_SWAP_PAIRS = new Class[] {
            String.class, String.class };
    private static final Class<?>[] ARGT_REDRAW = null;
    private static final Class<?>[] ARGT_ADD_TO_SCORE = new Class[] { int.class };
    private static final Class<?>[] ARGT_RANDOM_CHANCE = new Class[] { int.class };
    private static final Class<?>[] ARGT_BATTLE = null;
    private static final Class<?>[] ARGT_RELATIVE_LEVEL_CHANGE = new Class[] { int.class };
    // Argument Count Validation Array
    public static final int[] ARGUMENT_COUNT_VALIDATION = new int[] {
            ARGC_NONE, ARGC_MESSAGE, ARGC_SOUND, ARGC_SHOP, ARGC_MOVE,
            ARGC_DECAY, ARGC_REDRAW, ARGC_SWAP_PAIRS, ARGC_ADD_TO_SCORE,
            ARGC_RANDOM_CHANCE, ARGC_BATTLE, ARGC_RELATIVE_LEVEL_CHANGE };
    // Argument Type Validation Array
    public static final Class<?>[][] ARGUMENT_TYPE_VALIDATION = new Class[][] {
            ARGT_NONE, ARGT_MESSAGE, ARGT_SOUND, ARGT_SHOP, ARGT_MOVE,
            ARGT_DECAY, ARGT_REDRAW, ARGT_SWAP_PAIRS, ARGT_ADD_TO_SCORE,
            ARGT_RANDOM_CHANCE, ARGT_BATTLE, ARGT_RELATIVE_LEVEL_CHANGE };

    // Private Constructor
    private InternalScriptConstants() {
        // Do nothing
    }
}
