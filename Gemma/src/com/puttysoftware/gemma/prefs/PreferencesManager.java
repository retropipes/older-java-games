/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.prefs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;

import com.puttysoftware.gemma.Gemma;
import com.puttysoftware.gemma.battle.BattleSpeedConstants;
import com.puttysoftware.gemma.support.prefs.LocalPreferencesManager;
import com.puttysoftware.gemma.support.scenario.Extension;

public class PreferencesManager {
    // Fields
    private final static PreferencesStoreManager storeMgr = new PreferencesStoreManager();
    private final static PreferencesGUIManager guiMgr = new PreferencesGUIManager();
    static final int MUSIC_ALL = 0;
    public static final int MUSIC_EXPLORING = 1;
    public static final int MUSIC_BATTLE = 2;
    static final int MUSIC_LENGTH = 3;
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Preferences/";
    private static final String WIN_DIR = "\\Putty Software\\Gemma\\";
    private static final String UNIX_DIR = "/.puttysoftware/gemma/";
    private static final String MAC_FILE = "com.puttysoftware.gemma";
    private static final String WIN_FILE = "preferences";
    private static final String UNIX_FILE = "preferences";

    // Private constructor
    private PreferencesManager() {
        // Do nothing
    }

    // Methods
    public static int getGeneratorRandomness() {
        return PreferencesManager.storeMgr.getInteger("GeneratorRandomness", 3);
    }

    static void setGeneratorRandomness(int value) {
        PreferencesManager.storeMgr.setInteger("GeneratorRandomness", value);
        Gemma.getApplication().getScenarioManager().getMap()
                .setGeneratorRandomness(value, Gemma.GENERATOR_RANDOMNESS_MAX);
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

    static void setBattleSpeed(int value) {
        PreferencesManager.storeMgr.setInteger("BattleSpeed", value);
    }

    public static boolean oneMove() {
        return PreferencesManager.storeMgr.getBoolean("OneMove", true);
    }

    static void setOneMove(boolean value) {
        PreferencesManager.storeMgr.setBoolean("OneMove", value);
    }

    public static boolean getMusicEnabled(final int mus) {
        if (!PreferencesManager.storeMgr.getBoolean("MUSIC_0", false)) {
            return false;
        } else {
            return PreferencesManager.storeMgr.getBoolean("MUSIC_" + mus, true);
        }
    }

    static void setMusicEnabled(final int mus, final boolean status) {
        PreferencesManager.storeMgr.setBoolean("MUSIC_" + mus, status);
    }

    public static JFrame getPrefFrame() {
        return PreferencesManager.guiMgr.getPrefFrame();
    }

    public static void showPrefs() {
        PreferencesManager.guiMgr.showPrefs();
    }

    private static String getPrefsDirPrefix() {
        String osName = System.getProperty("os.name");
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
        String osName = System.getProperty("os.name");
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
        String osName = System.getProperty("os.name");
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
        StringBuilder b = new StringBuilder();
        b.append(PreferencesManager.getPrefsDirPrefix());
        b.append(PreferencesManager.getPrefsDirectory());
        b.append(PreferencesManager.getPrefsFileName());
        b.append(PreferencesManager.getPrefsFileExtension());
        return b.toString();
    }

    public static void writePrefs() {
        try (BufferedOutputStream buf = new BufferedOutputStream(
                new FileOutputStream(PreferencesManager.getPrefsFile()))) {
            PreferencesManager.storeMgr.saveStore(buf);
        } catch (IOException io) {
            // Ignore
        }
        LocalPreferencesManager.writePrefs();
    }

    static void readPrefs() {
        try (BufferedInputStream buf = new BufferedInputStream(
                new FileInputStream(PreferencesManager.getPrefsFile()))) {
            // Read new preferences
            PreferencesManager.storeMgr.loadStore(buf);
        } catch (IOException io) {
            // Populate store with defaults
            PreferencesManager.storeMgr.setBoolean("OneMove", true);
            for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
                PreferencesManager.storeMgr.setBoolean("MUSIC_" + x, true);
            }
            PreferencesManager.storeMgr.setInteger("BattleSpeed", 2);
            PreferencesManager.storeMgr.setInteger("GeneratorRandomness", 3);
        }
    }
}
