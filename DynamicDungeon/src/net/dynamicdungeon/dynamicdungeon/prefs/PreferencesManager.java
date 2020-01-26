/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.prefs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;

import net.dynamicdungeon.dynamicdungeon.dungeon.Extension;
import net.dynamicdungeon.dynamicdungeon.utilities.DynamicProperties;

public class PreferencesManager {
    // Fields
    private static PreferencesStoreManager storeMgr = new PreferencesStoreManager();
    private static PreferencesGUIManager guiMgr = new PreferencesGUIManager();
    public static final int DIFFICULTY_VERY_EASY = 0;
    public static final int DIFFICULTY_EASY = 1;
    public static final int DIFFICULTY_NORMAL = 2;
    public static final int DIFFICULTY_HARD = 3;
    public static final int DIFFICULTY_VERY_HARD = 4;
    private static final int DEFAULT_DIFFICULTY = PreferencesManager.DIFFICULTY_NORMAL;
    private static final String PREFS_FILE = "com.puttysoftware.ddremix";
    private static final String SOUNDS_SETTING = "SoundsEnabled";
    private static final String WINDOW_SETTING = "ViewingWindowSize";
    private static final String MOVE_SETTING = "OneMove";
    private static final String TIME_SETTING = "UseTimeBattleEngine";
    private static final String DIFFICULTY_SETTING = "GameDifficulty";
    private static final int BATTLE_SPEED = 1000;
    private static boolean HIGH_DEF = false;

    // Private constructor
    private PreferencesManager() {
        // Do nothing
    }

    // Methods
    public static int getBattleSpeed() {
        return PreferencesManager.BATTLE_SPEED;
    }

    public static boolean useTimeBattleEngine() {
        return PreferencesManager.storeMgr
                .getBoolean(PreferencesManager.TIME_SETTING, false);
    }

    static void setTimeBattleEngine(final boolean value) {
        PreferencesManager.storeMgr.setBoolean(PreferencesManager.TIME_SETTING,
                value);
    }

    public static boolean getSoundsEnabled() {
        return PreferencesManager.storeMgr
                .getBoolean(PreferencesManager.SOUNDS_SETTING, true);
    }

    public static void setSoundsEnabled(final boolean value) {
        PreferencesManager.storeMgr
                .setBoolean(PreferencesManager.SOUNDS_SETTING, value);
    }

    public static boolean getHighDefEnabled() {
        return PreferencesManager.HIGH_DEF;
    }

    public static void setHighDefEnabled(final boolean value) {
        PreferencesManager.HIGH_DEF = value;
    }

    public static int getViewingWindowSize() {
        return PreferencesGUIManager.VIEWING_WINDOW_SIZES[PreferencesManager
                .getViewingWindowSizeIndex()];
    }

    static int getViewingWindowSizeIndex() {
        return PreferencesManager.storeMgr.getInteger(
                PreferencesManager.WINDOW_SETTING,
                PreferencesGUIManager.DEFAULT_SIZE_INDEX);
    }

    static void setViewingWindowSizeIndex(final int value) {
        PreferencesManager.storeMgr
                .setInteger(PreferencesManager.WINDOW_SETTING, value);
    }

    public static boolean oneMove() {
        return PreferencesManager.storeMgr
                .getBoolean(PreferencesManager.MOVE_SETTING, true);
    }

    static void setOneMove(final boolean value) {
        PreferencesManager.storeMgr.setBoolean(PreferencesManager.MOVE_SETTING,
                value);
    }

    public static int getGameDifficulty() {
        return PreferencesManager.storeMgr.getInteger(
                PreferencesManager.DIFFICULTY_SETTING,
                PreferencesManager.DEFAULT_DIFFICULTY);
    }

    static void setGameDifficulty(final int value) {
        PreferencesManager.storeMgr
                .setInteger(PreferencesManager.DIFFICULTY_SETTING, value);
    }

    public static JFrame getPrefFrame() {
        return PreferencesManager.guiMgr.getPrefFrame();
    }

    public static void showPrefs() {
        PreferencesManager.guiMgr.showPrefs();
    }

    private static String getPrefsDirectory() {
        return DynamicProperties.getApplicationSupportDirectory();
    }

    private static String getPrefsFileExtension() {
        return "." + Extension.getPreferencesExtension();
    }

    private static String getPrefsFileName() {
        return PreferencesManager.PREFS_FILE;
    }

    private static String getPrefsFile() {
        final StringBuilder b = new StringBuilder();
        b.append(PreferencesManager.getPrefsDirectory());
        b.append(PreferencesManager.getPrefsFileName());
        b.append(PreferencesManager.getPrefsFileExtension());
        return b.toString();
    }

    public static void writePrefs() {
        try (BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(PreferencesManager.getPrefsFile()))) {
            PreferencesManager.storeMgr.saveStore(bos);
        } catch (final IOException io) {
            // Ignore
        }
    }

    static void readPrefs() {
        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(PreferencesManager.getPrefsFile()))) {
            // Read new preferences
            PreferencesManager.storeMgr.loadStore(bis);
        } catch (final IOException io) {
            // Populate store with defaults
            PreferencesManager.storeMgr
                    .setBoolean(PreferencesManager.MOVE_SETTING, true);
            PreferencesManager.storeMgr.setInteger(
                    PreferencesManager.WINDOW_SETTING,
                    PreferencesGUIManager.DEFAULT_SIZE_INDEX);
            PreferencesManager.storeMgr
                    .setBoolean(PreferencesManager.SOUNDS_SETTING, true);
            PreferencesManager.storeMgr
                    .setBoolean(PreferencesManager.TIME_SETTING, false);
            PreferencesManager.storeMgr.setInteger(
                    PreferencesManager.DIFFICULTY_SETTING,
                    PreferencesManager.DEFAULT_DIFFICULTY);
        }
    }
}
