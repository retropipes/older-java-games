/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.prefs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.puttysoftware.gemma.support.scenario.Extension;

public class LocalPreferencesManager {
    // Fields
    private final static LocalPreferencesStoreManager storeMgr = new LocalPreferencesStoreManager();
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Preferences/";
    private static final String WIN_DIR = "\\Putty Software\\Gemma\\";
    private static final String UNIX_DIR = "/.puttysoftware/gemma/";
    private static final String MAC_FILE = "com.puttysoftware.gemma.local";
    private static final String WIN_FILE = "local_preferences";
    private static final String UNIX_FILE = "local_preferences";

    // Private constructor
    private LocalPreferencesManager() {
        // Do nothing
    }

    // Methods
    public static boolean getSoundsEnabled() {
        return LocalPreferencesManager.storeMgr.getBoolean("SoundsEnabled",
                true);
    }

    public static void setSoundsEnabled(boolean value) {
        LocalPreferencesManager.storeMgr.setBoolean("SoundsEnabled", value);
    }

    public static boolean getRandomBattleEnvironment() {
        return LocalPreferencesManager.storeMgr
                .getBoolean("RandomBattleEnvironment", true);
    }

    public static void setRandomBattleEnvironment(boolean value) {
        LocalPreferencesManager.storeMgr.setBoolean("RandomBattleEnvironment",
                value);
    }

    private static String getPrefsDirPrefix() {
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv(LocalPreferencesManager.MAC_PREFIX);
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(LocalPreferencesManager.WIN_PREFIX);
        } else {
            // Other - assume UNIX-like
            return System.getenv(LocalPreferencesManager.UNIX_PREFIX);
        }
    }

    private static String getPrefsDirectory() {
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return LocalPreferencesManager.MAC_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return LocalPreferencesManager.WIN_DIR;
        } else {
            // Other - assume UNIX-like
            return LocalPreferencesManager.UNIX_DIR;
        }
    }

    private static String getPrefsFileExtension() {
        return "." + Extension.getPreferencesExtension();
    }

    private static String getPrefsFileName() {
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return LocalPreferencesManager.MAC_FILE;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return LocalPreferencesManager.WIN_FILE;
        } else {
            // Other - assume UNIX-like
            return LocalPreferencesManager.UNIX_FILE;
        }
    }

    private static String getPrefsFile() {
        StringBuilder b = new StringBuilder();
        b.append(LocalPreferencesManager.getPrefsDirPrefix());
        b.append(LocalPreferencesManager.getPrefsDirectory());
        b.append(LocalPreferencesManager.getPrefsFileName());
        b.append(LocalPreferencesManager.getPrefsFileExtension());
        return b.toString();
    }

    public static void writePrefs() {
        try (BufferedOutputStream buf = new BufferedOutputStream(
                new FileOutputStream(LocalPreferencesManager.getPrefsFile()))) {
            LocalPreferencesManager.storeMgr.saveStore(buf);
        } catch (IOException io) {
            // Ignore
        }
    }

    public static void readPrefs() {
        try (BufferedInputStream buf = new BufferedInputStream(
                new FileInputStream(LocalPreferencesManager.getPrefsFile()))) {
            // Read new preferences
            LocalPreferencesManager.storeMgr.loadStore(buf);
        } catch (IOException io) {
            // Populate store with defaults
            LocalPreferencesManager.storeMgr.setBoolean("SoundsEnabled", true);
            LocalPreferencesManager.storeMgr
                    .setBoolean("RandomBattleEnvironment", true);
        }
    }
}
