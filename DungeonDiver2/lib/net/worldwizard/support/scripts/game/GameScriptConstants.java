package net.worldwizard.support.scripts.game;

public class GameScriptConstants {
    // Action Code Arguments
    public static final boolean MOVE_RELATIVE = false;
    // Action Code Argument Counts
    public static final int ARGC_NONE = 0;
    public static final int ARGC_MESSAGE = 1;
    public static final int ARGC_SOUND = 1;
    public static final int ARGC_SHOP = 1;
    public static final int ARGC_MOVE = 5;
    public static final int ARGC_END_GAME = 0;
    public static final int ARGC_MODIFY = 6;
    public static final int ARGC_DELETE_SCRIPT = 0;
    public static final int ARGC_RANDOM_CHANCE = 1;
    public static final int ARGC_BATTLE = 0;
    public static final int ARGC_DECAY = 0;
    public static final int ARGC_SWAP_PAIRS = 2;
    public static final int ARGC_REDRAW = 0;
    public static final int ARGC_LEVEL_CHANGE = 1;
    public static final int ARGC_ADD_TO_SCORE = 1;
    public static final int ARGC_RELATIVE_LEVEL_CHANGE = 1;
    // Action Code Argument Types
    public static final Class<?>[] ARGT_NONE = null;
    public static final Class<?>[] ARGT_MESSAGE = new Class[] { String.class };
    public static final Class<?>[] ARGT_SOUND = new Class[] { int.class };
    public static final Class<?>[] ARGT_SHOP = new Class[] { int.class };
    public static final Class<?>[] ARGT_MOVE = new Class[] { boolean.class,
            boolean.class, int.class, int.class, int.class };
    public static final Class<?>[] ARGT_END_GAME = null;
    public static final Class<?>[] ARGT_MODIFY = new Class[] { boolean.class,
            int.class, int.class, int.class, int.class, String.class };
    public static final Class<?>[] ARGT_DELETE_SCRIPT = null;
    public static final Class<?>[] ARGT_RANDOM_CHANCE = new Class[] {
            int.class };
    public static final Class<?>[] ARGT_BATTLE = null;
    public static final Class<?>[] ARGT_DECAY = null;
    public static final Class<?>[] ARGT_SWAP_PAIRS = new Class[] { String.class,
            String.class };
    public static final Class<?>[] ARGT_REDRAW = null;
    public static final Class<?>[] ARGT_LEVEL_CHANGE = new Class[] {
            int.class };
    public static final Class<?>[] ARGT_ADD_TO_SCORE = new Class[] {
            int.class };
    public static final Class<?>[] ARGT_RELATIVE_LEVEL_CHANGE = new Class[] {
            int.class };
    // Argument Count Validation Array
    public static final int[] ARGUMENT_COUNT_VALIDATION = new int[] {
            GameScriptConstants.ARGC_NONE, GameScriptConstants.ARGC_MESSAGE,
            GameScriptConstants.ARGC_SOUND, GameScriptConstants.ARGC_SHOP,
            GameScriptConstants.ARGC_MOVE, GameScriptConstants.ARGC_END_GAME,
            GameScriptConstants.ARGC_MODIFY,
            GameScriptConstants.ARGC_DELETE_SCRIPT,
            GameScriptConstants.ARGC_RANDOM_CHANCE,
            GameScriptConstants.ARGC_BATTLE, GameScriptConstants.ARGC_DECAY,
            GameScriptConstants.ARGC_REDRAW,
            GameScriptConstants.ARGC_LEVEL_CHANGE,
            GameScriptConstants.ARGC_SWAP_PAIRS,
            GameScriptConstants.ARGC_ADD_TO_SCORE,
            GameScriptConstants.ARGC_RELATIVE_LEVEL_CHANGE };
    // Argument Type Validation Array
    public static final Class<?>[][] ARGUMENT_TYPE_VALIDATION = new Class[][] {
            GameScriptConstants.ARGT_NONE, GameScriptConstants.ARGT_MESSAGE,
            GameScriptConstants.ARGT_SOUND, GameScriptConstants.ARGT_SHOP,
            GameScriptConstants.ARGT_MOVE, GameScriptConstants.ARGT_END_GAME,
            GameScriptConstants.ARGT_MODIFY,
            GameScriptConstants.ARGT_DELETE_SCRIPT,
            GameScriptConstants.ARGT_RANDOM_CHANCE,
            GameScriptConstants.ARGT_BATTLE, GameScriptConstants.ARGT_DECAY,
            GameScriptConstants.ARGT_REDRAW,
            GameScriptConstants.ARGT_LEVEL_CHANGE,
            GameScriptConstants.ARGT_SWAP_PAIRS,
            GameScriptConstants.ARGT_ADD_TO_SCORE,
            GameScriptConstants.ARGT_RELATIVE_LEVEL_CHANGE };

    // Private Constructor
    private GameScriptConstants() {
        // Do nothing
    }
}
