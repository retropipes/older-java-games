/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.variables;

public class Extension {
    // Constants
    private static final String PREFERENCES_EXTENSION = "xml";
    private static final String REGISTRY_EXTENSION = "regi";
    private static final String VARIABLES_EXTENSION = "vars";
    private static final String SAVED_GAME_EXTENSION = "save2";
    private static final String SCORES_EXTENSION = "scores";
    private static final String AI_SCRIPT_EXTENSION = "aiscpt";
    private static final String CHARACTER_EXTENSION = "char";
    private static final String MONSTER_EXTENSION = "monster";
    private static final String EFFECT_EXTENSION = "effect";
    private static final String SPELL_EXTENSION = "spell";
    private static final String SPELL_BOOK_EXTENSION = "spellbook";
    private static final String BATTLE_EXTENSION = "battle";
    private static final String ITEM_EXTENSION = "item";
    private static final String RACE_EXTENSION = "race";
    private static final String CASTE_EXTENSION = "caste";

    // Methods
    public static String getPreferencesExtension() {
        return Extension.PREFERENCES_EXTENSION;
    }

    public static String getRegistryExtensionWithPeriod() {
        return "." + Extension.REGISTRY_EXTENSION;
    }

    public static String getVariablesExtension() {
        return Extension.VARIABLES_EXTENSION;
    }

    public static String getVariablesExtensionWithPeriod() {
        return "." + Extension.VARIABLES_EXTENSION;
    }

    public static String getGameExtension() {
        return Extension.SAVED_GAME_EXTENSION;
    }

    public static String getGameExtensionWithPeriod() {
        return "." + Extension.SAVED_GAME_EXTENSION;
    }

    public static String getScoresExtensionWithPeriod() {
        return "." + Extension.SCORES_EXTENSION;
    }

    public static String getCharacterExtension() {
        return Extension.CHARACTER_EXTENSION;
    }

    public static String getCharacterExtensionWithPeriod() {
        return "." + Extension.CHARACTER_EXTENSION;
    }

    public static String getMonsterExtension() {
        return Extension.MONSTER_EXTENSION;
    }

    public static String getMonsterExtensionWithPeriod() {
        return "." + Extension.MONSTER_EXTENSION;
    }

    public static String getEffectExtension() {
        return Extension.EFFECT_EXTENSION;
    }

    public static String getEffectExtensionWithPeriod() {
        return "." + Extension.EFFECT_EXTENSION;
    }

    public static String getSpellExtension() {
        return Extension.SPELL_EXTENSION;
    }

    public static String getSpellExtensionWithPeriod() {
        return "." + Extension.SPELL_EXTENSION;
    }

    public static String getSpellBookExtension() {
        return Extension.SPELL_BOOK_EXTENSION;
    }

    public static String getSpellBookExtensionWithPeriod() {
        return "." + Extension.SPELL_BOOK_EXTENSION;
    }

    public static String getBattleExtension() {
        return Extension.BATTLE_EXTENSION;
    }

    public static String getBattleExtensionWithPeriod() {
        return "." + Extension.BATTLE_EXTENSION;
    }

    public static String getItemExtension() {
        return Extension.ITEM_EXTENSION;
    }

    public static String getItemExtensionWithPeriod() {
        return "." + Extension.ITEM_EXTENSION;
    }

    public static String getAIScriptExtension() {
        return Extension.AI_SCRIPT_EXTENSION;
    }

    public static String getAIScriptExtensionWithPeriod() {
        return "." + Extension.AI_SCRIPT_EXTENSION;
    }

    public static String getRaceExtension() {
        return Extension.RACE_EXTENSION;
    }

    public static String getRaceExtensionWithPeriod() {
        return "." + Extension.RACE_EXTENSION;
    }

    public static String getCasteExtension() {
        return Extension.CASTE_EXTENSION;
    }

    public static String getCasteExtensionWithPeriod() {
        return "." + Extension.CASTE_EXTENSION;
    }
}
