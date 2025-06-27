/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.scripts.internal;

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
        private static final int ARGC_UPDATE_GSA = 1;
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
        private static final Class<?>[] ARGT_ADD_TO_SCORE = new Class[] {
                        int.class };
        private static final Class<?>[] ARGT_RANDOM_CHANCE = new Class[] {
                        int.class };
        private static final Class<?>[] ARGT_BATTLE = null;
        private static final Class<?>[] ARGT_RELATIVE_LEVEL_CHANGE = new Class[] {
                        int.class };
        private static final Class<?>[] ARGT_UPDATE_GSA = new Class[] { int.class };
        // Argument Count Validation Array
        public static final int[] ARGUMENT_COUNT_VALIDATION = new int[] {
                        InternalScriptConstants.ARGC_NONE,
                        InternalScriptConstants.ARGC_MESSAGE,
                        InternalScriptConstants.ARGC_SOUND,
                        InternalScriptConstants.ARGC_SHOP,
                        InternalScriptConstants.ARGC_MOVE,
                        InternalScriptConstants.ARGC_DECAY,
                        InternalScriptConstants.ARGC_REDRAW,
                        InternalScriptConstants.ARGC_SWAP_PAIRS,
                        InternalScriptConstants.ARGC_ADD_TO_SCORE,
                        InternalScriptConstants.ARGC_RANDOM_CHANCE,
                        InternalScriptConstants.ARGC_BATTLE,
                        InternalScriptConstants.ARGC_RELATIVE_LEVEL_CHANGE,
                        InternalScriptConstants.ARGC_UPDATE_GSA };
        // Argument Type Validation Array
        public static final Class<?>[][] ARGUMENT_TYPE_VALIDATION = new Class[][] {
                        InternalScriptConstants.ARGT_NONE,
                        InternalScriptConstants.ARGT_MESSAGE,
                        InternalScriptConstants.ARGT_SOUND,
                        InternalScriptConstants.ARGT_SHOP,
                        InternalScriptConstants.ARGT_MOVE,
                        InternalScriptConstants.ARGT_DECAY,
                        InternalScriptConstants.ARGT_REDRAW,
                        InternalScriptConstants.ARGT_SWAP_PAIRS,
                        InternalScriptConstants.ARGT_ADD_TO_SCORE,
                        InternalScriptConstants.ARGT_RANDOM_CHANCE,
                        InternalScriptConstants.ARGT_BATTLE,
                        InternalScriptConstants.ARGT_RELATIVE_LEVEL_CHANGE,
                        InternalScriptConstants.ARGT_UPDATE_GSA };

        // Private Constructor
        private InternalScriptConstants() {
                // Do nothing
        }
}
