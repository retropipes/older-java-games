package net.worldwizard.worldz.game;

public interface ScriptConstants {
    // Action Code Arguments
    public static final boolean MOVE_RELATIVE = false;
    public static final boolean MOVE_ABSOLUTE = true;
    // Action Code Argument Counts
    public static final int ARGC_NONE = 0;
    public static final int ARGC_MESSAGE = 1;
    public static final int ARGC_SOUND = 1;
    public static final int ARGC_SHOP = 1;
    public static final int ARGC_MOVE = 6;
    public static final int ARGC_END_GAME = 0;
    public static final int ARGC_MODIFY = 6;
    public static final int ARGC_DELETE_SCRIPT = 0;
    public static final int ARGC_RANDOM_CHANCE = 1;
    public static final int ARGC_BATTLE = 0;
    // Action Code Argument Types
    public static final Class<?>[] ARGT_NONE = null;
    public static final Class<?>[] ARGT_MESSAGE = new Class[] { String.class };
    public static final Class<?>[] ARGT_SOUND = new Class[] { String.class };
    public static final Class<?>[] ARGT_SHOP = new Class[] { int.class };
    public static final Class<?>[] ARGT_MOVE = new Class[] { boolean.class,
            boolean.class, int.class, int.class, int.class, int.class };
    public static final Class<?>[] ARGT_END_GAME = null;
    public static final Class<?>[] ARGT_MODIFY = new Class[] { boolean.class,
            int.class, int.class, int.class, int.class, String.class };
    public static final Class<?>[] ARGT_DELETE_SCRIPT = null;
    public static final Class<?>[] ARGT_RANDOM_CHANCE = new Class[] { int.class };
    public static final Class<?>[] ARGT_BATTLE = null;
    // Argument Count Validation Array
    public static final int[] ARGUMENT_COUNT_VALIDATION = new int[] {
            ScriptConstants.ARGC_NONE, ScriptConstants.ARGC_MESSAGE,
            ScriptConstants.ARGC_SOUND, ScriptConstants.ARGC_SHOP,
            ScriptConstants.ARGC_MOVE, ScriptConstants.ARGC_END_GAME,
            ScriptConstants.ARGC_MODIFY, ScriptConstants.ARGC_DELETE_SCRIPT,
            ScriptConstants.ARGC_RANDOM_CHANCE, ScriptConstants.ARGC_BATTLE };
    // Argument Type Validation Array
    public static final Class<?>[][] ARGUMENT_TYPE_VALIDATION = new Class[][] {
            ScriptConstants.ARGT_NONE, ScriptConstants.ARGT_MESSAGE,
            ScriptConstants.ARGT_SOUND, ScriptConstants.ARGT_SHOP,
            ScriptConstants.ARGT_MOVE, ScriptConstants.ARGT_END_GAME,
            ScriptConstants.ARGT_MODIFY, ScriptConstants.ARGT_DELETE_SCRIPT,
            ScriptConstants.ARGT_RANDOM_CHANCE, ScriptConstants.ARGT_BATTLE };
}
