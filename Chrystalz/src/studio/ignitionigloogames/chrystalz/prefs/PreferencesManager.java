/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.prefs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;

import studio.ignitionigloogames.chrystalz.manager.file.Extension;

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
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Preferences/";
    private static final String WIN_DIR = "\\Ignition Igloo Games\\Chrystalz\\";
    private static final String UNIX_DIR = "/.ignitionigloogames/chrystalz/";
    private static final String MAC_FILE = "studio.ignitionigloogames.chrystalz";
    private static final String WIN_FILE = "ChrystalzPreferences";
    private static final String UNIX_FILE = "ChrystalzPreferences";
    private static final String SOUNDS_SETTING = "SoundsEnabled";
    private static final String MOVE_SETTING = "OneMove";
    private static final String DIFFICULTY_SETTING = "GameDifficulty";
    private static final String MUSIC_SETTING = "EnableMusic";
    private static final int BATTLE_SPEED = 1000;
    private static final int VIEWING_WINDOW_SIZE = 11;

    // Private constructor
    private PreferencesManager() {
        // Do nothing
    }

    // Methods
    public static int getBattleSpeed() {
        return PreferencesManager.BATTLE_SPEED;
    }

    public static boolean getSoundsEnabled() {
        return PreferencesManager.storeMgr
                .getBoolean(PreferencesManager.SOUNDS_SETTING, true);
    }

    public static void setSoundsEnabled(final boolean value) {
        PreferencesManager.storeMgr
                .setBoolean(PreferencesManager.SOUNDS_SETTING, value);
    }

    public static int getViewingWindowSize() {
        return PreferencesManager.VIEWING_WINDOW_SIZE;
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

    public static boolean getMusicEnabled() {
        return PreferencesManager.storeMgr
                .getBoolean(PreferencesManager.MUSIC_SETTING, true);
    }

    static void setMusicEnabled(final boolean status) {
        PreferencesManager.storeMgr.setBoolean(PreferencesManager.MUSIC_SETTING,
                status);
    }

    public static JFrame getPrefFrame() {
        return PreferencesManager.guiMgr.getPrefFrame();
    }

    public static void showPrefs() {
        PreferencesManager.guiMgr.showPrefs();
    }

    private static String getPrefsDirPrefix() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv(PreferencesManager.MAC_PREFIX);
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(PreferencesManager.WIN_PREFIX);
        } else {
            // Other - assume UNIX-like
            return System.getenv(PreferencesManager.UNIX_PREFIX);
        }
    }

    private static String getPrefsDirectory() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return PreferencesManager.MAC_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return PreferencesManager.WIN_DIR;
        } else {
            // Other - assume UNIX-like
            return PreferencesManager.UNIX_DIR;
        }
    }

    private static String getPrefsFileExtension() {
        return "." + Extension.getPreferencesExtension();
    }

    private static String getPrefsFileName() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return PreferencesManager.MAC_FILE;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return PreferencesManager.WIN_FILE;
        } else {
            // Other - assume UNIX-like
            return PreferencesManager.UNIX_FILE;
        }
    }

    private static String getPrefsFile() {
        final StringBuilder b = new StringBuilder();
        b.append(PreferencesManager.getPrefsDirPrefix());
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
            PreferencesManager.storeMgr
                    .setBoolean(PreferencesManager.MUSIC_SETTING, true);
            PreferencesManager.storeMgr
                    .setBoolean(PreferencesManager.SOUNDS_SETTING, true);
            PreferencesManager.storeMgr.setInteger(
                    PreferencesManager.DIFFICULTY_SETTING,
                    PreferencesManager.DEFAULT_DIFFICULTY);
        }
    }
}
