package net.worldwizard.support.ai;

public final class AIScriptConstants {
    // Commands
    public static final String COMMAND_MOVE = "move";
    public static final String COMMAND_CAST_SPELL = "cast selected spell";
    public static final String COMMAND_STEAL = "steal";
    public static final String COMMAND_DRAIN = "drain";
    public static final String COMMAND_USE_ITEM = "use selected item";
    public static final String COMMAND_END_TURN = "end turn";
    // Meta-Commands
    public static final String META_COMMAND_ATTACK = "attack";
    public static final String META_COMMAND_TURN_45_LEFT = "turn 45 left";
    public static final String META_COMMAND_TURN_90_LEFT = "turn 90 left";
    public static final String META_COMMAND_TURN_45_RIGHT = "turn 45 right";
    public static final String META_COMMAND_TURN_90_RIGHT = "turn 90 right";
    public static final String META_COMMAND_TURN_180 = "turn 180";
    public static final String META_COMMAND_TURN_RANDOMLY = "turn randomly";
    public static final String META_COMMAND_SELECT_SPELL_POISON = "select spell poison";
    public static final String META_COMMAND_SELECT_SPELL_HEAL = "select spell heal";
    public static final String META_COMMAND_SELECT_SPELL_WEAPON_DRAIN = "select spell weapon drain";
    public static final String META_COMMAND_SELECT_SPELL_ARMOR_DRAIN = "select spell armor drain";
    public static final String META_COMMAND_SELECT_SPELL_WEAPON_CHARGE = "select spell weapon charge";
    public static final String META_COMMAND_SELECT_SPELL_ARMOR_CHARGE = "select spell armor charge";
    public static final String META_COMMAND_SELECT_SPELL_ATTACK_LOCK = "select spell attack lock";
    public static final String META_COMMAND_SCAN_RADIUS = "scan ";
    public static final String META_COMMAND_RESET_LAST_RESULT = "reset last result";
    // Tests
    public static final String TEST_POSITIVE_COMMAND_PREFIX = "if i can ";
    public static final String TEST_NEGATIVE_COMMAND_PREFIX = "if i can not ";
    public static final String TEST_POSITIVE_LAST_RESULT = "if last action succeeded";
    public static final String TEST_NEGATIVE_LAST_RESULT = "if last action failed";
    // Jumps
    public static final String JUMP_TO = "jump to ";
    // Sections
    public static final String SECTION_START = "[";

    // Private Constructor
    private AIScriptConstants() {
        // Do nothing
    }
}
