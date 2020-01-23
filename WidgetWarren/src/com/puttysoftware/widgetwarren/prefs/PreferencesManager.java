/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.prefs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;

import com.puttysoftware.widgetwarren.generic.MazeObject;
import com.puttysoftware.widgetwarren.maze.Extension;
import com.puttysoftware.widgetwarren.objects.Dirt;
import com.puttysoftware.widgetwarren.objects.Grass;
import com.puttysoftware.widgetwarren.objects.Sand;
import com.puttysoftware.widgetwarren.objects.Snow;
import com.puttysoftware.widgetwarren.objects.Tile;
import com.puttysoftware.widgetwarren.objects.Tundra;
import com.puttysoftware.widgetwarren.security.SandboxManager;

public class PreferencesManager {
    // Fields
    private static PreferencesStoreManager storeMgr = new PreferencesStoreManager();
    private static PreferencesGUIManager guiMgr = new PreferencesGUIManager();
    static final int SOUNDS_ALL = 0;
    public static final int SOUNDS_UI = 1;
    public static final int SOUNDS_GAME = 2;
    static final int SOUNDS_LENGTH = 3;
    private static final String MAC_FILE = "com.puttysoftware.widgetwarren.preferences";
    private static final String WIN_FILE = "WidgetWarrenPreferences";
    private static final String UNIX_FILE = "WidgetWarrenPreferences";

    // Private constructor
    private PreferencesManager() {
        // Do nothing
    }

    // Methods
    public static boolean oneMove() {
        return PreferencesManager.storeMgr.getBoolean("OneMove", true);
    }

    static void setOneMove(final boolean value) {
        PreferencesManager.storeMgr.setBoolean("OneMove", value);
    }

    public static boolean getSoundEnabled(final int snd) {
        if (!PreferencesManager.storeMgr.getBoolean("SOUND_0", false)) {
            return false;
        } else {
            return PreferencesManager.storeMgr.getBoolean("SOUND_" + snd, true);
        }
    }

    public static void setSoundEnabled(final int snd, final boolean status) {
        PreferencesManager.storeMgr.setBoolean("SOUND_" + snd, status);
    }

    public static boolean getMusicEnabled() {
        return PreferencesManager.storeMgr.getBoolean("MusicEnabled", true);
    }

    public static void setMusicEnabled(final boolean status) {
        PreferencesManager.storeMgr.setBoolean("MusicEnabled", status);
    }

    public static MazeObject getEditorDefaultFill() {
        final String choice = PreferencesManager.storeMgr.getString(
                "EditorDefaultFill", "Grass");
        if (choice.equals("Tile")) {
            return new Tile();
        } else if (choice.equals("Grass")) {
            return new Grass();
        } else if (choice.equals("Dirt")) {
            return new Dirt();
        } else if (choice.equals("Snow")) {
            return new Snow();
        } else if (choice.equals("Tundra")) {
            return new Tundra();
        } else if (choice.equals("Sand")) {
            return new Sand();
        } else {
            return null;
        }
    }

    static void setEditorDefaultFill(final String value) {
        PreferencesManager.storeMgr.setString("EditorDefaultFill", value);
    }

    public static JFrame getPrefFrame() {
        return PreferencesManager.guiMgr.getPrefFrame();
    }

    public static void showPrefs() {
        PreferencesManager.guiMgr.showPrefs();
    }

    private static String getPrefsDirectory() {
        return SandboxManager.getSandboxManager().getSupportDirectory()
                + File.separator;
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
        b.append(PreferencesManager.getPrefsDirectory());
        b.append(PreferencesManager.getPrefsFileName());
        b.append(PreferencesManager.getPrefsFileExtension());
        return b.toString();
    }

    public static void writePrefs() {
        try {
            PreferencesManager.storeMgr.saveStore(new BufferedOutputStream(
                    new FileOutputStream(PreferencesManager.getPrefsFile())));
        } catch (final IOException io) {
            // Ignore
        }
    }

    static void readPrefs() {
        try {
            // Read new preferences
            PreferencesManager.storeMgr.loadStore(new BufferedInputStream(
                    new FileInputStream(PreferencesManager.getPrefsFile())));
        } catch (final IOException io) {
            // Populate store with defaults
            PreferencesManager.storeMgr.setString("LastDirOpen", SandboxManager
                    .getSandboxManager().getDocumentsDirectory());
            PreferencesManager.storeMgr.setString("LastDirSave", SandboxManager
                    .getSandboxManager().getDocumentsDirectory());
            PreferencesManager.storeMgr.setBoolean("OneMove", true);
            for (int x = 0; x < PreferencesManager.SOUNDS_LENGTH; x++) {
                PreferencesManager.storeMgr.setBoolean("SOUND_" + x, true);
            }
            PreferencesManager.storeMgr.setString("EditorDefaultFill", "Grass");
            PreferencesManager.storeMgr.setBoolean("MusicEnabled", true);
        }
    }
}
