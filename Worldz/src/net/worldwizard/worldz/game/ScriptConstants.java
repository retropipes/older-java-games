package net.worldwizard.worldz.game;

public interface ScriptConstants {
        // Action Code Arguments
        boolean MOVE_RELATIVE = false;
        boolean MOVE_ABSOLUTE = true;
        // Action Code Argument Counts
        int ARGC_NONE = 0;
        int ARGC_MESSAGE = 1;
        int ARGC_SOUND = 1;
        int ARGC_SHOP = 1;
        int ARGC_MOVE = 6;
        int ARGC_END_GAME = 0;
        int ARGC_MODIFY = 6;
        int ARGC_DELETE_SCRIPT = 0;
        int ARGC_RANDOM_CHANCE = 1;
        int ARGC_BATTLE = 0;
        // Action Code Argument Types
        Class<?>[] ARGT_NONE = null;
        Class<?>[] ARGT_MESSAGE = new Class[] { String.class };
        Class<?>[] ARGT_SOUND = new Class[] { String.class };
        Class<?>[] ARGT_SHOP = new Class[] { int.class };
        Class<?>[] ARGT_MOVE = new Class[] { boolean.class, boolean.class,
                        int.class, int.class, int.class, int.class };
        Class<?>[] ARGT_END_GAME = null;
        Class<?>[] ARGT_MODIFY = new Class[] { boolean.class, int.class, int.class,
                        int.class, int.class, String.class };
        Class<?>[] ARGT_DELETE_SCRIPT = null;
        Class<?>[] ARGT_RANDOM_CHANCE = new Class[] { int.class };
        Class<?>[] ARGT_BATTLE = null;
        // Argument Count Validation Array
        int[] ARGUMENT_COUNT_VALIDATION = new int[] { ScriptConstants.ARGC_NONE,
                        ScriptConstants.ARGC_MESSAGE, ScriptConstants.ARGC_SOUND,
                        ScriptConstants.ARGC_SHOP, ScriptConstants.ARGC_MOVE,
                        ScriptConstants.ARGC_END_GAME, ScriptConstants.ARGC_MODIFY,
                        ScriptConstants.ARGC_DELETE_SCRIPT,
                        ScriptConstants.ARGC_RANDOM_CHANCE, ScriptConstants.ARGC_BATTLE };
        // Argument Type Validation Array
        Class<?>[][] ARGUMENT_TYPE_VALIDATION = new Class[][] {
                        ScriptConstants.ARGT_NONE, ScriptConstants.ARGT_MESSAGE,
                        ScriptConstants.ARGT_SOUND, ScriptConstants.ARGT_SHOP,
                        ScriptConstants.ARGT_MOVE, ScriptConstants.ARGT_END_GAME,
                        ScriptConstants.ARGT_MODIFY, ScriptConstants.ARGT_DELETE_SCRIPT,
                        ScriptConstants.ARGT_RANDOM_CHANCE, ScriptConstants.ARGT_BATTLE };
}
