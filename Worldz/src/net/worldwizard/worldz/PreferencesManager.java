/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import net.worldwizard.io.DataConstants;
import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;
import net.worldwizard.worldz.battle.BattleSpeedConstants;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.objects.Dirt;
import net.worldwizard.worldz.objects.Grass;
import net.worldwizard.worldz.objects.Sand;
import net.worldwizard.worldz.objects.Snow;
import net.worldwizard.worldz.objects.Tile;
import net.worldwizard.worldz.objects.Tundra;
import net.worldwizard.worldz.world.Extension;

public class PreferencesManager {
    // Fields
    JFrame prefFrame;
    private JTabbedPane prefTabPane;
    private Container mainPrefPane, buttonPane, gamePane, editorPane, miscPane,
            soundPane, musicPane;
    private JButton prefsOK, prefsCancel;
    private JButton prefsExport, prefsImport;
    final JCheckBox[] sounds;
    final JCheckBox[] music;
    JCheckBox checkUpdatesStartup;
    JCheckBox checkBetaUpdatesStartup;
    JCheckBox moveOneAtATime;
    JComboBox<String> editorFillChoices;
    private String[] editorFillChoiceArray;
    JComboBox<String> battleSpeedChoices;
    private String[] battleSpeedChoiceArray;
    JComboBox<String> updateCheckInterval;
    private String[] updateCheckIntervalValues;
    int editorFill;
    int battleSpeedIndex;
    private EventHandler handler;
    private final PreferencesFileManager fileMgr;
    private final ExportImportManager eiMgr;
    boolean checkUpdatesStartupEnabled;
    boolean checkBetaUpdatesStartupEnabled;
    boolean moveOneAtATimeEnabled;
    final boolean[] soundsEnabled;
    final boolean[] musicEnabled;
    String lastDirOpen;
    String lastDirSave;
    int lastFilterUsed;
    private static final int SOUNDS_ALL = 0;
    public static final int SOUNDS_UI = 1;
    public static final int SOUNDS_GAME = 2;
    public static final int SOUNDS_BATTLE = 3;
    public static final int SOUNDS_SHOP = 4;
    public static final int MUSIC_ALL = 0;
    public static final int MUSIC_BATTLE = 1;
    public static final int MUSIC_EXPLORING = 2;
    public static final int FILTER_GAME = 0;
    public static final int FILTER_WORLD_V1 = 1;
    private static final int MUSIC_LENGTH = 3;
    private static final int SOUNDS_LENGTH = 5;
    private static final int PREFS_VERSION_MAJOR = 1;
    private static final int PREFS_VERSION_MINOR = 0;

    // Constructors
    public PreferencesManager() {
        this.sounds = new JCheckBox[PreferencesManager.SOUNDS_LENGTH];
        this.soundsEnabled = new boolean[PreferencesManager.SOUNDS_LENGTH];
        this.music = new JCheckBox[PreferencesManager.MUSIC_LENGTH];
        this.musicEnabled = new boolean[PreferencesManager.MUSIC_LENGTH];
        this.setUpGUI();
        this.fileMgr = new PreferencesFileManager();
        this.eiMgr = new ExportImportManager();
        this.setDefaultPrefs();
    }

    // Methods
    public int getBattleSpeed() {
        return BattleSpeedConstants.BATTLE_SPEED_ARRAY[this.battleSpeedIndex];
    }

    private static int getGridLength() {
        return 5;
    }

    public String getLastDirOpen() {
        return this.lastDirOpen;
    }

    public void setLastDirOpen(final String value) {
        this.lastDirOpen = value;
    }

    public String getLastDirSave() {
        return this.lastDirSave;
    }

    public void setLastDirSave(final String value) {
        this.lastDirSave = value;
    }

    public int getLastFilterUsedIndex() {
        return this.lastFilterUsed;
    }

    public void setLastFilterUsedIndex(final int value) {
        this.lastFilterUsed = value;
    }

    public boolean shouldCheckUpdatesAtStartup() {
        return this.checkUpdatesStartupEnabled;
    }

    public boolean shouldCheckBetaUpdatesAtStartup() {
        if (Worldz.getApplication().isBetaModeEnabled()) {
            return this.checkBetaUpdatesStartupEnabled;
        } else {
            return false;
        }
    }

    public boolean oneMove() {
        return this.moveOneAtATimeEnabled;
    }

    public boolean getSoundEnabled(final int snd) {
        if (!this.soundsEnabled[PreferencesManager.SOUNDS_ALL]) {
            return false;
        } else {
            return this.soundsEnabled[snd];
        }
    }

    public boolean getMusicEnabled(final int mus) {
        if (!this.musicEnabled[PreferencesManager.MUSIC_ALL]) {
            return false;
        } else {
            return this.musicEnabled[mus];
        }
    }

    public WorldObject getEditorDefaultFill() {
        final String choice = this.editorFillChoiceArray[this.editorFill];
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

    private void defaultEnableSounds() {
        for (int x = 0; x < PreferencesManager.SOUNDS_LENGTH; x++) {
            this.soundsEnabled[x] = true;
        }
    }

    private void defaultEnableMusic() {
        for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
            this.musicEnabled[x] = true;
        }
    }

    public void setSoundEnabled(final int snd, final boolean status) {
        this.soundsEnabled[snd] = status;
    }

    public void setMusicEnabled(final int mus, final boolean status) {
        this.musicEnabled[mus] = status;
    }

    public JFrame getPrefFrame() {
        if (this.prefFrame != null && this.prefFrame.isVisible()) {
            return this.prefFrame;
        } else {
            return null;
        }
    }

    public void showPrefs() {
        if (Worldz.inWorldz()) {
            final Application app = Worldz.getApplication();
            app.setInPrefs(true);
            this.prefFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
            app.getMenuManager().setPrefMenus();
            this.prefFrame.setVisible(true);
            final int formerMode = app.getFormerMode();
            if (formerMode == Application.STATUS_GUI) {
                app.getGUIManager().hideGUITemporarily();
            } else if (formerMode == Application.STATUS_GAME) {
                app.getGameManager().hideOutput();
            } else if (formerMode == Application.STATUS_EDITOR) {
                app.getEditor().hideOutput();
            }
        }
    }

    public void hidePrefs() {
        if (Worldz.inWorldz()) {
            final Application app = Worldz.getApplication();
            app.setInPrefs(false);
            this.prefFrame.setVisible(false);
            this.fileMgr.writePreferencesFile();
            final int formerMode = app.getFormerMode();
            if (formerMode == Application.STATUS_GUI) {
                app.getGUIManager().showGUI();
            } else if (formerMode == Application.STATUS_GAME) {
                app.getGameManager().showOutput();
            } else if (formerMode == Application.STATUS_EDITOR) {
                app.getEditor().showOutput();
            }
        }
    }

    public void writePrefs() {
        this.fileMgr.writePreferencesFile();
    }

    public void setPrefs(final boolean initial) {
        this.editorFill = this.editorFillChoices.getSelectedIndex();
        for (int x = 0; x < PreferencesManager.SOUNDS_LENGTH; x++) {
            this.setSoundEnabled(x, this.sounds[x].isSelected());
        }
        for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
            this.setMusicEnabled(x, this.music[x].isSelected());
        }
        this.checkUpdatesStartupEnabled = this.checkUpdatesStartup.isSelected();
        this.checkBetaUpdatesStartupEnabled = this.checkBetaUpdatesStartup
                .isSelected();
        this.moveOneAtATimeEnabled = this.moveOneAtATime.isSelected();
        this.battleSpeedIndex = this.battleSpeedChoices.getSelectedIndex();
        if (!initial) {
            this.hidePrefs();
        }
    }

    public void setDefaultPrefs() {
        if (!this.fileMgr.readPreferencesFile()) {
            this.resetDefaultPrefs();
        }
    }

    private void resetDefaultPrefs() {
        this.editorFill = 0;
        this.defaultEnableSounds();
        this.defaultEnableMusic();
        this.checkUpdatesStartup.setSelected(true);
        this.checkUpdatesStartupEnabled = true;
        this.checkBetaUpdatesStartup.setSelected(true);
        this.checkBetaUpdatesStartupEnabled = true;
        this.moveOneAtATime.setSelected(true);
        this.moveOneAtATimeEnabled = true;
        this.updateCheckInterval.setSelectedIndex(0);
        this.lastFilterUsed = PreferencesManager.FILTER_WORLD_V1;
        this.battleSpeedChoices.setSelectedIndex(2);
        this.battleSpeedIndex = 2;
    }

    void handleExport() {
        final boolean result = this.eiMgr
                .exportPreferencesFile(this.eiMgr.getExportDestination());
        if (!result) {
            Messager.showErrorDialog("Export Failed!", "Preferences");
        }
    }

    void handleImport() {
        final boolean result = this.eiMgr
                .importPreferencesFile(this.eiMgr.getImportSource());
        if (!result) {
            Messager.showErrorDialog("Import Failed!", "Preferences");
        } else {
            this.prefFrame.repaint();
        }
    }

    private void setUpGUI() {
        this.handler = new EventHandler();
        this.prefFrame = new JFrame("Preferences");
        this.prefTabPane = new JTabbedPane();
        this.mainPrefPane = new Container();
        this.gamePane = new Container();
        this.editorPane = new Container();
        this.soundPane = new Container();
        this.musicPane = new Container();
        this.miscPane = new Container();
        this.prefTabPane.setOpaque(true);
        this.buttonPane = new Container();
        this.prefsOK = new JButton("OK");
        this.prefsOK.setDefaultCapable(true);
        this.prefFrame.getRootPane().setDefaultButton(this.prefsOK);
        this.prefsCancel = new JButton("Cancel");
        this.prefsCancel.setDefaultCapable(false);
        this.prefsExport = new JButton("Export...");
        this.prefsExport.setDefaultCapable(false);
        this.prefsImport = new JButton("Import...");
        this.prefsImport.setDefaultCapable(false);
        this.editorFillChoiceArray = new String[] { "Grass", "Dirt", "Sand",
                "Snow", "Tile", "Tundra" };
        this.editorFillChoices = new JComboBox<>(this.editorFillChoiceArray);
        this.battleSpeedChoiceArray = new String[] { "Very Slow", "Slow",
                "Moderate", "Fast", "Very Fast" };
        this.battleSpeedChoices = new JComboBox<>(this.battleSpeedChoiceArray);
        this.sounds[PreferencesManager.SOUNDS_ALL] = new JCheckBox(
                "Enable ALL sounds", true);
        this.sounds[PreferencesManager.SOUNDS_UI] = new JCheckBox(
                "Enable user interface sounds", true);
        this.sounds[PreferencesManager.SOUNDS_GAME] = new JCheckBox(
                "Enable game sounds", true);
        this.sounds[PreferencesManager.SOUNDS_BATTLE] = new JCheckBox(
                "Enable battle sounds", true);
        this.sounds[PreferencesManager.SOUNDS_SHOP] = new JCheckBox(
                "Enable shop sounds", true);
        this.music[PreferencesManager.MUSIC_ALL] = new JCheckBox(
                "Enable ALL music", true);
        this.music[PreferencesManager.MUSIC_BATTLE] = new JCheckBox(
                "Enable battle music", true);
        this.music[PreferencesManager.MUSIC_EXPLORING] = new JCheckBox(
                "Enable exploring music", true);
        this.checkUpdatesStartup = new JCheckBox("Check for Updates at Startup",
                true);
        this.checkBetaUpdatesStartup = new JCheckBox(
                "Check for Beta Updates at Startup", true);
        this.moveOneAtATime = new JCheckBox("One Move at a Time", true);
        this.updateCheckIntervalValues = new String[] { "Daily",
                "Every 2nd Day", "Weekly", "Every 2nd Week", "Monthly" };
        this.updateCheckInterval = new JComboBox<>(
                this.updateCheckIntervalValues);
        this.prefFrame.setContentPane(this.mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.prefFrame.addWindowListener(this.handler);
        this.mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        this.gamePane.setLayout(
                new GridLayout(PreferencesManager.getGridLength(), 1));
        this.gamePane.add(new JLabel("Battle Speed"));
        this.gamePane.add(this.battleSpeedChoices);
        this.editorPane.setLayout(
                new GridLayout(PreferencesManager.getGridLength(), 1));
        this.editorPane.add(new JLabel("Default fill for new worlds:"));
        this.editorPane.add(this.editorFillChoices);
        this.soundPane.setLayout(
                new GridLayout(PreferencesManager.getGridLength(), 1));
        for (int x = 0; x < PreferencesManager.SOUNDS_LENGTH; x++) {
            this.soundPane.add(this.sounds[x]);
        }
        this.musicPane.setLayout(
                new GridLayout(PreferencesManager.getGridLength(), 1));
        for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
            this.musicPane.add(this.music[x]);
        }
        this.miscPane.setLayout(
                new GridLayout(PreferencesManager.getGridLength(), 1));
        this.miscPane.add(this.checkUpdatesStartup);
        if (Worldz.getApplication().isBetaModeEnabled()) {
            this.miscPane.add(this.checkBetaUpdatesStartup);
        }
        this.miscPane.add(this.moveOneAtATime);
        this.miscPane.add(new JLabel("Check How Often For Updates"));
        this.miscPane.add(this.updateCheckInterval);
        this.buttonPane.setLayout(new FlowLayout());
        this.buttonPane.add(this.prefsOK);
        this.buttonPane.add(this.prefsCancel);
        this.buttonPane.add(this.prefsExport);
        this.buttonPane.add(this.prefsImport);
        this.prefTabPane.addTab("Game", null, this.gamePane, "Game");
        this.prefTabPane.addTab("Editor", null, this.editorPane, "Editor");
        this.prefTabPane.addTab("Sounds", null, this.soundPane, "Sounds");
        this.prefTabPane.addTab("Music", null, this.musicPane, "Music");
        this.prefTabPane.addTab("Misc.", null, this.miscPane, "Misc.");
        this.mainPrefPane.add(this.prefTabPane, BorderLayout.CENTER);
        this.mainPrefPane.add(this.buttonPane, BorderLayout.SOUTH);
        this.sounds[PreferencesManager.SOUNDS_ALL]
                .addItemListener(this.handler);
        this.music[PreferencesManager.MUSIC_ALL].addItemListener(this.handler);
        this.prefsOK.addActionListener(this.handler);
        this.prefsCancel.addActionListener(this.handler);
        this.prefsExport.addActionListener(this.handler);
        this.prefsImport.addActionListener(this.handler);
        final Image iconlogo = Worldz.getApplication().getIconLogo();
        this.prefFrame.setIconImage(iconlogo);
        this.prefFrame.pack();
    }

    private class PreferencesFileManager {
        // Fields
        private static final String MAC_PREFIX = "HOME";
        private static final String WIN_PREFIX = "APPDATA";
        private static final String UNIX_PREFIX = "HOME";
        private static final String MAC_DIR = "/Library/Preferences/";
        private static final String WIN_DIR = "\\Worldz\\";
        private static final String UNIX_DIR = "/.worldz/";
        private static final String MAC_FILE = "net.worldwizard.worldz.preferences";
        private static final String WIN_FILE = "WorldzPreferences";
        private static final String UNIX_FILE = "WorldzPreferences";

        // Constructors
        public PreferencesFileManager() {
            // Do nothing
        }

        public boolean readPreferencesFile() {
            try {
                // Read the preferences from the file
                final DataReader s = new DataReader(this.getPrefsFile(),
                        DataConstants.DATA_MODE_TEXT);
                final PreferencesManager pm = PreferencesManager.this;
                // Read major version
                final int majorVersion = s.readInt();
                // Read minor version
                final int minorVersion = s.readInt();
                // Version check
                if (majorVersion == PreferencesManager.PREFS_VERSION_MAJOR) {
                    if (minorVersion > PreferencesManager.PREFS_VERSION_MINOR) {
                        throw new PreferencesException(
                                "Incompatible preferences minor version, using defaults.");
                    }
                } else {
                    throw new PreferencesException(
                            "Incompatible preferences major version, using defaults.");
                }
                pm.editorFillChoices.setSelectedIndex(s.readInt());
                pm.checkUpdatesStartup.setSelected(s.readBoolean());
                pm.moveOneAtATime.setSelected(s.readBoolean());
                for (int x = 0; x < PreferencesManager.SOUNDS_LENGTH; x++) {
                    pm.sounds[x].setSelected(s.readBoolean());
                }
                pm.updateCheckInterval.setSelectedIndex(s.readInt());
                pm.lastDirOpen = s.readString();
                pm.lastDirSave = s.readString();
                pm.checkBetaUpdatesStartup.setSelected(s.readBoolean());
                pm.lastFilterUsed = s.readInt();
                for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
                    pm.music[x].setSelected(s.readBoolean());
                }
                pm.battleSpeedChoices.setSelectedIndex(s.readInt());
                s.close();
                pm.setPrefs(true);
                return true;
            } catch (final PreferencesException pe) {
                Messager.showDialog(pe.getMessage());
                return false;
            } catch (final IOException ie) {
                return false;
            } catch (final Exception e) {
                Messager.showDialog(
                        "An error occurred while attempting to read the preferences file. Using defaults.");
                return false;
            }
        }

        public void writePreferencesFile() {
            try {
                // Create the needed subdirectories, if they don't already exist
                final String prefsFile = this.getPrefsFile();
                final File prefsParent = new File(this.getPrefsFile())
                        .getParentFile();
                if (!prefsParent.exists()) {
                    final boolean success = prefsParent.mkdirs();
                    if (!success) {
                        // Uh-oh, preferences writing failed
                        throw new IOException("Directory creation failed!");
                    }
                }
                // Write the preferences to the file
                final DataWriter s = new DataWriter(prefsFile,
                        DataConstants.DATA_MODE_TEXT);
                final PreferencesManager pm = PreferencesManager.this;
                s.writeInt(PreferencesManager.PREFS_VERSION_MAJOR);
                s.writeInt(PreferencesManager.PREFS_VERSION_MINOR);
                s.writeInt(pm.editorFill);
                s.writeBoolean(pm.checkUpdatesStartupEnabled);
                s.writeBoolean(pm.moveOneAtATimeEnabled);
                for (int x = 0; x < PreferencesManager.SOUNDS_LENGTH; x++) {
                    s.writeBoolean(pm.soundsEnabled[x]);
                }
                s.writeInt(pm.updateCheckInterval.getSelectedIndex());
                s.writeString(pm.lastDirOpen);
                s.writeString(pm.lastDirSave);
                s.writeBoolean(pm.checkBetaUpdatesStartupEnabled);
                s.writeInt(pm.lastFilterUsed);
                for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
                    s.writeBoolean(pm.musicEnabled[x]);
                }
                s.writeInt(pm.battleSpeedIndex);
                s.close();
            } catch (final IOException ie) {
                // Do nothing
            }
        }

        private String getPrefsDirPrefix() {
            final String osName = System.getProperty("os.name");
            if (osName.indexOf("Mac OS X") != -1) {
                // Mac OS X
                return System.getenv(PreferencesFileManager.MAC_PREFIX);
            } else if (osName.indexOf("Windows") != -1) {
                // Windows
                return System.getenv(PreferencesFileManager.WIN_PREFIX);
            } else {
                // Other - assume UNIX-like
                return System.getenv(PreferencesFileManager.UNIX_PREFIX);
            }
        }

        private String getPrefsDirectory() {
            final String osName = System.getProperty("os.name");
            if (osName.indexOf("Mac OS X") != -1) {
                // Mac OS X
                return PreferencesFileManager.MAC_DIR;
            } else if (osName.indexOf("Windows") != -1) {
                // Windows
                return PreferencesFileManager.WIN_DIR;
            } else {
                // Other - assume UNIX-like
                return PreferencesFileManager.UNIX_DIR;
            }
        }

        private String getPrefsFileExtension() {
            return "." + Extension.getPreferencesExtension();
        }

        private String getPrefsFileName() {
            final String osName = System.getProperty("os.name");
            if (osName.indexOf("Mac OS X") != -1) {
                // Mac OS X
                return PreferencesFileManager.MAC_FILE;
            } else if (osName.indexOf("Windows") != -1) {
                // Windows
                return PreferencesFileManager.WIN_FILE;
            } else {
                // Other - assume UNIX-like
                return PreferencesFileManager.UNIX_FILE;
            }
        }

        private String getPrefsFile() {
            final StringBuilder b = new StringBuilder();
            b.append(this.getPrefsDirPrefix());
            b.append(this.getPrefsDirectory());
            b.append(this.getPrefsFileName());
            b.append(this.getPrefsFileExtension());
            return b.toString();
        }
    }

    private class ExportImportManager {
        // Constructors
        public ExportImportManager() {
            // Do nothing
        }

        // Methods
        public boolean importPreferencesFile(final String importFile) {
            try {
                // Read the preferences from the file
                final DataReader s = new DataReader(importFile,
                        DataConstants.DATA_MODE_TEXT);
                final PreferencesManager pm = PreferencesManager.this;
                // Read major version
                s.readInt();
                // Read minor version
                s.readInt();
                pm.editorFillChoices.setSelectedIndex(s.readInt());
                pm.checkUpdatesStartup.setSelected(s.readBoolean());
                pm.moveOneAtATime.setSelected(s.readBoolean());
                for (int x = 0; x < PreferencesManager.SOUNDS_LENGTH; x++) {
                    pm.sounds[x].setSelected(s.readBoolean());
                }
                pm.updateCheckInterval.setSelectedIndex(s.readInt());
                pm.lastDirOpen = s.readString();
                pm.lastDirSave = s.readString();
                pm.checkBetaUpdatesStartup.setSelected(s.readBoolean());
                pm.lastFilterUsed = s.readInt();
                for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
                    pm.music[x].setSelected(s.readBoolean());
                }
                pm.battleSpeedChoices.setSelectedIndex(s.readInt());
                s.close();
                pm.setPrefs(true);
                return true;
            } catch (final IOException ie) {
                return false;
            }
        }

        public boolean exportPreferencesFile(final String exportFile) {
            try {
                // Write the preferences to the file
                final DataWriter s = new DataWriter(exportFile,
                        DataConstants.DATA_MODE_TEXT);
                final PreferencesManager pm = PreferencesManager.this;
                s.writeInt(PreferencesManager.PREFS_VERSION_MAJOR);
                s.writeInt(PreferencesManager.PREFS_VERSION_MINOR);
                s.writeInt(pm.editorFill);
                s.writeBoolean(pm.checkUpdatesStartupEnabled);
                s.writeBoolean(pm.moveOneAtATimeEnabled);
                for (int x = 0; x < PreferencesManager.SOUNDS_LENGTH; x++) {
                    s.writeBoolean(pm.soundsEnabled[x]);
                }
                s.writeInt(pm.updateCheckInterval.getSelectedIndex());
                s.writeString(pm.lastDirOpen);
                s.writeString(pm.lastDirSave);
                s.writeBoolean(pm.checkBetaUpdatesStartupEnabled);
                s.writeInt(pm.lastFilterUsed);
                for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
                    s.writeBoolean(pm.musicEnabled[x]);
                }
                s.writeInt(pm.battleSpeedIndex);
                s.close();
                return true;
            } catch (final IOException ie) {
                return false;
            }
        }

        public String getImportSource() {
            final FileDialog chooser = new FileDialog(
                    PreferencesManager.this.prefFrame, "Import",
                    FileDialog.LOAD);
            chooser.setVisible(true);
            return chooser.getDirectory() + chooser.getFile();
        }

        public String getExportDestination() {
            final FileDialog chooser = new FileDialog(
                    PreferencesManager.this.prefFrame, "Export",
                    FileDialog.SAVE);
            chooser.setVisible(true);
            return chooser.getDirectory() + chooser.getFile();
        }
    }

    private class EventHandler
            implements ActionListener, ItemListener, WindowListener {
        public EventHandler() {
            // TODO Auto-generated constructor stub
        }

        // Handle buttons
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                final PreferencesManager pm = PreferencesManager.this;
                final String cmd = e.getActionCommand();
                if (cmd.equals("OK")) {
                    pm.setPrefs(false);
                } else if (cmd.equals("Cancel")) {
                    pm.hidePrefs();
                } else if (cmd.equals("Export...")) {
                    pm.handleExport();
                } else if (cmd.equals("Import...")) {
                    pm.handleImport();
                }
            } catch (final Exception ex) {
                Worldz.getDebug().debug(ex);
            }
        }

        @Override
        public void itemStateChanged(final ItemEvent e) {
            try {
                final PreferencesManager pm = PreferencesManager.this;
                final Object o = e.getItem();
                if (o.getClass().equals(JCheckBox.class)) {
                    final JCheckBox check = (JCheckBox) o;
                    if (check
                            .equals(pm.sounds[PreferencesManager.SOUNDS_ALL])) {
                        if (e.getStateChange() == ItemEvent.SELECTED) {
                            for (int x = 1; x < PreferencesManager.SOUNDS_LENGTH; x++) {
                                pm.sounds[x].setEnabled(true);
                            }
                        } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                            for (int x = 1; x < PreferencesManager.SOUNDS_LENGTH; x++) {
                                pm.sounds[x].setEnabled(false);
                            }
                        }
                    } else if (check
                            .equals(pm.music[PreferencesManager.MUSIC_ALL])) {
                        if (e.getStateChange() == ItemEvent.SELECTED) {
                            for (int x = 1; x < PreferencesManager.MUSIC_LENGTH; x++) {
                                pm.music[x].setEnabled(true);
                            }
                        } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                            for (int x = 1; x < PreferencesManager.MUSIC_LENGTH; x++) {
                                pm.music[x].setEnabled(false);
                            }
                        }
                    }
                }
            } catch (final Exception ex) {
                Worldz.getDebug().debug(ex);
            }
        }

        @Override
        public void windowOpened(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowClosing(final WindowEvent e) {
            final PreferencesManager pm = PreferencesManager.this;
            pm.hidePrefs();
        }

        @Override
        public void windowClosed(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowIconified(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowDeiconified(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowActivated(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowDeactivated(final WindowEvent e) {
            // Do nothing
        }
    }
}
