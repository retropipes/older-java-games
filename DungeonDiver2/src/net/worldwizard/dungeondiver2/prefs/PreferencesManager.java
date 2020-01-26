/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.dungeondiver2.prefs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;

import net.worldwizard.dungeondiver2.DungeonDiverII;
import net.worldwizard.dungeondiver2.battle.BattleSpeedConstants;
import net.worldwizard.support.Support;
import net.worldwizard.support.variables.Extension;

public class PreferencesManager {
    // Fields
    private static PreferencesStoreManager storeMgr = new PreferencesStoreManager();
    private static PreferencesGUIManager guiMgr = new PreferencesGUIManager();
    public static final int MUSIC_ALL = 0;
    public static final int MUSIC_EXPLORING = 1;
    public static final int MUSIC_BATTLE = 2;
    public static final int MUSIC_BOSS = 3;
    static final int MUSIC_LENGTH = 4;
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Preferences/";
    private static final String WIN_DIR = "\\DungeonDiverII\\";
    private static final String UNIX_DIR = "/.dungeondiver2/";
    private static final String MAC_FILE = "net.worldwizard.DungeonDiverII";
    private static final String WIN_FILE = "DungeonDiverIIPreferences";
    private static final String UNIX_FILE = "DungeonDiverIIPreferences";

    // Private constructor
    private PreferencesManager() {
        // Do nothing
    }

    // Methods
    public static int getGeneratorRandomness() {
        return PreferencesManager.storeMgr.getInteger("GeneratorRandomness", 3);
    }

    static void setGeneratorRandomness(final int value) {
        PreferencesManager.storeMgr.setInteger("GeneratorRandomness", value);
        DungeonDiverII.getApplication().getVariablesManager().getMap()
                .setGeneratorRandomness(value, 6);
    }

    public static int getBattleSpeed() {
        return BattleSpeedConstants.BATTLE_SPEED_ARRAY[PreferencesManager.storeMgr
                .getInteger("BattleSpeed",
                        BattleSpeedConstants.BATTLE_SPEED_MODERATE)];
    }

    static int getBattleSpeedValue() {
        return PreferencesManager.storeMgr.getInteger("BattleSpeed",
                BattleSpeedConstants.BATTLE_SPEED_MODERATE);
    }

    static void setBattleSpeed(final int value) {
        PreferencesManager.storeMgr.setInteger("BattleSpeed", value);
    }

    public static String getLastDirOpen() {
        return PreferencesManager.storeMgr.getString("LastDirOpen", "");
    }

    public static void setLastDirOpen(final String value) {
        PreferencesManager.storeMgr.setString("LastDirOpen", value);
    }

    public static String getLastDirSave() {
        return PreferencesManager.storeMgr.getString("LastDirSave", "");
    }

    public static void setLastDirSave(final String value) {
        PreferencesManager.storeMgr.setString("LastDirSave", value);
    }

    public static boolean shouldCheckUpdatesAtStartup() {
        return PreferencesManager.storeMgr.getBoolean("UpdatesStartup", true);
    }

    public static boolean shouldCheckBetaUpdatesAtStartup() {
        if (Support.isBetaModeEnabled()) {
            return PreferencesManager.storeMgr.getBoolean("BetaUpdatesStartup",
                    true);
        } else {
            return false;
        }
    }

    static void setCheckUpdatesAtStartup(final boolean value) {
        PreferencesManager.storeMgr.setBoolean("UpdatesStartup", value);
    }

    static void setCheckBetaUpdatesAtStartup(final boolean value) {
        PreferencesManager.storeMgr.setBoolean("BetaUpdatesStartup", value);
    }

    public static boolean oneMove() {
        return PreferencesManager.storeMgr.getBoolean("OneMove", true);
    }

    static void setOneMove(final boolean value) {
        PreferencesManager.storeMgr.setBoolean("OneMove", value);
    }

    public static boolean getMusicEnabled(final int mus) {
        if (!PreferencesManager.storeMgr.getBoolean("MUSIC_0", false)) {
            return false;
        } else {
            return PreferencesManager.storeMgr.getBoolean("MUSIC_" + mus, true);
        }
    }

    public static void setMusicEnabled(final int mus, final boolean status) {
        PreferencesManager.storeMgr.setBoolean("MUSIC_" + mus, status);
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
            PreferencesManager.storeMgr.setString("LastDirOpen", "");
            PreferencesManager.storeMgr.setString("LastDirSave", "");
            PreferencesManager.storeMgr.setBoolean("UpdatesStartup", true);
            PreferencesManager.storeMgr.setBoolean("BetaUpdatesStartup", true);
            PreferencesManager.storeMgr.setBoolean("OneMove", true);
            for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
                PreferencesManager.storeMgr.setBoolean("MUSIC_" + x, true);
            }
            PreferencesManager.storeMgr.setInteger("BattleSpeed", 2);
            PreferencesManager.storeMgr.setInteger("GeneratorRandomness", 3);
        }
    }
}
