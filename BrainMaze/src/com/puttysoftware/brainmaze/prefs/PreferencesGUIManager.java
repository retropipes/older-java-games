/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.prefs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import com.puttysoftware.brainmaze.Application;
import com.puttysoftware.brainmaze.BrainMaze;

class PreferencesGUIManager {
    // Fields
    private JFrame prefFrame;
    JCheckBox[] sounds;
    JCheckBox[] music;
    private JCheckBox moveOneAtATime;
    private JCheckBox editorAutoEdge;
    private JComboBox<String> editorFillChoices;
    private JComboBox<String> viewingWindowChoices;
    private static final int[] VIEWING_WINDOW_SIZES = new int[] { 13, 17, 23,
            27, 33 };
    private static final String[] VIEWING_WINDOW_SIZE_NAMES = new String[] {
            "Mobile", "Small", "Medium", "Large", "Huge" };
    private static final String[] editorFillChoiceArray = new String[] {
            "Grass", "Dirt", "Sand", "Snow", "Tile", "Tundra" };
    private static final int GRID_LENGTH = 3;

    // Constructors
    public PreferencesGUIManager() {
        this.sounds = new JCheckBox[PreferencesManager.SOUNDS_LENGTH];
        this.music = new JCheckBox[PreferencesManager.MUSIC_LENGTH];
        this.setUpGUI();
        this.setDefaultPrefs();
    }

    // Methods
    private static int viewingWindowSizeToIndex(final int size) {
        return (size - 13) / 4;
    }

    public JFrame getPrefFrame() {
        if (this.prefFrame != null && this.prefFrame.isVisible()) {
            return this.prefFrame;
        } else {
            return null;
        }
    }

    public void showPrefs() {
        final Application app = BrainMaze.getApplication();
        app.setInPrefs(true);
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            this.prefFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
        }
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

    public void hidePrefs() {
        final Application app = BrainMaze.getApplication();
        app.setInPrefs(false);
        this.prefFrame.setVisible(false);
        PreferencesManager.writePrefs();
        final int formerMode = app.getFormerMode();
        if (formerMode == Application.STATUS_GUI) {
            app.getGUIManager().showGUI();
        } else if (formerMode == Application.STATUS_GAME) {
            app.getGameManager().showOutput();
        } else if (formerMode == Application.STATUS_EDITOR) {
            app.getEditor().showOutput();
        }
    }

    private void loadPrefs() {
        this.editorFillChoices.setSelectedItem(PreferencesManager
                .getEditorDefaultFill());
        this.editorAutoEdge.setSelected(PreferencesManager.getEditorAutoEdge());
        for (int x = 0; x < PreferencesManager.SOUNDS_LENGTH; x++) {
            this.sounds[x].setSelected(PreferencesManager.getSoundEnabled(x));
        }
        for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
            this.music[x].setSelected(PreferencesManager.getMusicEnabled(x));
        }
        this.moveOneAtATime.setSelected(PreferencesManager.oneMove());
        this.viewingWindowChoices.setSelectedIndex(PreferencesGUIManager
                .viewingWindowSizeToIndex(PreferencesManager
                        .getViewingWindowSize()));
    }

    public void setPrefs() {
        PreferencesManager.setEditorDefaultFill((String) this.editorFillChoices
                .getSelectedItem());
        PreferencesManager.setEditorAutoEdge(this.editorAutoEdge.isSelected());
        for (int x = 0; x < PreferencesManager.SOUNDS_LENGTH; x++) {
            PreferencesManager.setSoundEnabled(x, this.sounds[x].isSelected());
        }
        for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
            PreferencesManager.setMusicEnabled(x, this.music[x].isSelected());
        }
        PreferencesManager.setOneMove(this.moveOneAtATime.isSelected());
        final int vwSize = PreferencesManager.getViewingWindowSize();
        final int newSize = PreferencesGUIManager.VIEWING_WINDOW_SIZES[this.viewingWindowChoices
                .getSelectedIndex()];
        PreferencesManager.setViewingWindowSize(newSize);
        if (vwSize != newSize) {
            BrainMaze.getApplication().getGameManager()
                    .viewingWindowSizeChanged();
            BrainMaze.getApplication().getEditor().viewingWindowSizeChanged();
        }
        this.hidePrefs();
    }

    public final void setDefaultPrefs() {
        PreferencesManager.readPrefs();
        this.loadPrefs();
    }

    private void setUpGUI() {
        final EventHandler handler = new EventHandler();
        this.prefFrame = new JFrame("Preferences");
        final JTabbedPane prefTabPane = new JTabbedPane();
        final Container mainPrefPane = new Container();
        final Container editorPane = new Container();
        final Container soundPane = new Container();
        final Container musicPane = new Container();
        final Container miscPane = new Container();
        prefTabPane.setOpaque(true);
        final Container buttonPane = new Container();
        final JButton prefsOK = new JButton("OK");
        prefsOK.setDefaultCapable(true);
        this.prefFrame.getRootPane().setDefaultButton(prefsOK);
        final JButton prefsCancel = new JButton("Cancel");
        prefsCancel.setDefaultCapable(false);
        this.editorFillChoices = new JComboBox<>(
                PreferencesGUIManager.editorFillChoiceArray);
        this.editorAutoEdge = new JCheckBox("Enable automatic transitions",
                false);
        this.viewingWindowChoices = new JComboBox<>(
                PreferencesGUIManager.VIEWING_WINDOW_SIZE_NAMES);
        this.sounds[PreferencesManager.SOUNDS_ALL] = new JCheckBox(
                "Enable ALL sounds", true);
        this.sounds[PreferencesManager.SOUNDS_UI] = new JCheckBox(
                "Enable user interface sounds", true);
        this.sounds[PreferencesManager.SOUNDS_GAME] = new JCheckBox(
                "Enable game sounds", true);
        this.music[PreferencesManager.MUSIC_ALL] = new JCheckBox(
                "Enable ALL music", true);
        this.music[PreferencesManager.MUSIC_EXPLORING] = new JCheckBox(
                "Enable exploring music", true);
        this.moveOneAtATime = new JCheckBox("One Move at a Time", true);
        this.prefFrame.setContentPane(mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.prefFrame.addWindowListener(handler);
        mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        editorPane.setLayout(new GridLayout(PreferencesGUIManager.GRID_LENGTH,
                1));
        editorPane.add(new JLabel("Default fill for new mazes:"));
        editorPane.add(this.editorFillChoices);
        editorPane.add(this.editorAutoEdge);
        soundPane
                .setLayout(new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        for (int x = 0; x < PreferencesManager.SOUNDS_LENGTH; x++) {
            soundPane.add(this.sounds[x]);
        }
        musicPane
                .setLayout(new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
            musicPane.add(this.music[x]);
        }
        miscPane.setLayout(new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        miscPane.add(this.moveOneAtATime);
        miscPane.add(new JLabel("Viewing Window Size"));
        miscPane.add(this.viewingWindowChoices);
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(prefsOK);
        buttonPane.add(prefsCancel);
        prefTabPane.addTab("Editor", null, editorPane, "Editor");
        prefTabPane.addTab("Sounds", null, soundPane, "Sounds");
        prefTabPane.addTab("Music", null, musicPane, "Music");
        prefTabPane.addTab("Misc.", null, miscPane, "Misc.");
        mainPrefPane.add(prefTabPane, BorderLayout.CENTER);
        mainPrefPane.add(buttonPane, BorderLayout.SOUTH);
        this.sounds[PreferencesManager.SOUNDS_ALL].addItemListener(handler);
        this.music[PreferencesManager.MUSIC_ALL].addItemListener(handler);
        prefsOK.addActionListener(handler);
        prefsCancel.addActionListener(handler);
        final Image iconlogo = BrainMaze.getApplication().getIconLogo();
        this.prefFrame.setIconImage(iconlogo);
        this.prefFrame.pack();
    }

    private class EventHandler implements ActionListener, ItemListener,
            WindowListener {
        EventHandler() {
            // Do nothing
        }

        // Handle buttons
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                final PreferencesGUIManager pm = PreferencesGUIManager.this;
                final String cmd = e.getActionCommand();
                if (cmd.equals("OK")) {
                    pm.setPrefs();
                } else if (cmd.equals("Cancel")) {
                    pm.hidePrefs();
                }
            } catch (final Exception ex) {
                BrainMaze.getErrorLogger().logError(ex);
            }
        }

        @Override
        public void itemStateChanged(final ItemEvent e) {
            try {
                final PreferencesGUIManager pm = PreferencesGUIManager.this;
                final Object o = e.getItem();
                if (o.getClass().equals(JCheckBox.class)) {
                    final JCheckBox check = (JCheckBox) o;
                    if (check.equals(pm.sounds[PreferencesManager.SOUNDS_ALL])) {
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
                BrainMaze.getErrorLogger().logError(ex);
            }
        }

        @Override
        public void windowOpened(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowClosing(final WindowEvent e) {
            final PreferencesGUIManager pm = PreferencesGUIManager.this;
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
