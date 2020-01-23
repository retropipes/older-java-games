/*  LaserTank: An Arena-Solving Game
 Copyright (C) 2008-2013 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.lasertank.prefs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import com.puttysoftware.lasertank.Application;
import com.puttysoftware.lasertank.LaserTank;
import com.puttysoftware.lasertank.resourcemanagers.LogoManager;
import com.puttysoftware.lasertank.stringmanagers.StringConstants;
import com.puttysoftware.lasertank.stringmanagers.StringLoader;
import com.puttysoftware.lasertank.utilities.EditorLayoutConstants;

class PreferencesGUIManager {
    // Fields
    private JFrame prefFrame;
    private JCheckBox sounds;
    private JCheckBox music;
    private JCheckBox checkUpdatesStartup;
    private JCheckBox enableAnimation;
    private JCheckBox moveOneAtATime;
    private JComboBox<String> actionDelay;
    private JComboBox<String> languageList;
    private JComboBox<String> editorLayoutList;
    private JCheckBox editorShowAllObjects;
    private static final int GRID_LENGTH = 12;

    // Constructors
    PreferencesGUIManager() {
        this.setUpGUI();
        this.setDefaultPrefs();
    }

    // Methods
    void activeLanguageChanged() {
        // Dispose of old GUI
        if (this.prefFrame != null) {
            this.prefFrame.dispose();
        }
        this.setUpGUI();
        PreferencesManager.writePrefs();
        this.loadPrefs();
    }

    public JFrame getPrefFrame() {
        if (this.prefFrame != null && this.prefFrame.isVisible()) {
            return this.prefFrame;
        } else {
            return null;
        }
    }

    public void showPrefs() {
        final Application app = LaserTank.getApplication();
        app.setInPrefs();
        this.prefFrame.setVisible(true);
        final int formerMode = app.getFormerMode();
        if (formerMode == Application.STATUS_GUI) {
            app.getGUIManager().hideGUI();
        } else if (formerMode == Application.STATUS_GAME) {
            app.getGameManager().hideOutput();
        } else if (formerMode == Application.STATUS_EDITOR) {
            app.getEditor().hideOutput();
        }
    }

    void hidePrefs() {
        final Application app = LaserTank.getApplication();
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
        this.enableAnimation.setSelected(PreferencesManager.enableAnimation());
        this.sounds.setSelected(PreferencesManager.getSoundsEnabled());
        this.music.setSelected(PreferencesManager.getMusicEnabled());
        this.checkUpdatesStartup
                .setSelected(PreferencesManager.shouldCheckUpdatesAtStartup());
        this.moveOneAtATime.setSelected(PreferencesManager.oneMove());
        this.actionDelay.setSelectedIndex(PreferencesManager.getActionDelay());
        this.languageList.setSelectedIndex(PreferencesManager.getLanguageID());
        this.editorLayoutList
                .setSelectedIndex(PreferencesManager.getEditorLayoutID());
        this.editorShowAllObjects
                .setSelected(PreferencesManager.getEditorShowAllObjects());
    }

    void setPrefs() {
        PreferencesManager
                .setEnableAnimation(this.enableAnimation.isSelected());
        PreferencesManager.setSoundsEnabled(this.sounds.isSelected());
        PreferencesManager.setMusicEnabled(this.music.isSelected());
        PreferencesManager.setCheckUpdatesAtStartup(
                this.checkUpdatesStartup.isSelected());
        PreferencesManager.setOneMove(this.moveOneAtATime.isSelected());
        PreferencesManager.setActionDelay(this.actionDelay.getSelectedIndex());
        PreferencesManager.setLanguageID(this.languageList.getSelectedIndex());
        PreferencesManager
                .setEditorLayoutID(this.editorLayoutList.getSelectedIndex());
        PreferencesManager.setEditorShowAllObjects(
                this.editorShowAllObjects.isSelected());
        this.hidePrefs();
    }

    private void setDefaultPrefs() {
        PreferencesManager.readPrefs();
        this.loadPrefs();
    }

    private void setUpGUI() {
        final EventHandler handler = new EventHandler();
        this.prefFrame = new JFrame(
                StringLoader.loadString(StringConstants.PREFS_STRINGS_FILE,
                        StringConstants.PREFS_STRING_TITLE));
        final Container mainPrefPane = new Container();
        final Container buttonPane = new Container();
        final Container settingsPane = new Container();
        final JButton prefsOK = new JButton(
                StringLoader.loadString(StringConstants.DIALOG_STRINGS_FILE,
                        StringConstants.DIALOG_STRING_OK_BUTTON));
        prefsOK.setDefaultCapable(true);
        this.prefFrame.getRootPane().setDefaultButton(prefsOK);
        final JButton prefsCancel = new JButton(
                StringLoader.loadString(StringConstants.DIALOG_STRINGS_FILE,
                        StringConstants.DIALOG_STRING_CANCEL_BUTTON));
        prefsCancel.setDefaultCapable(false);
        this.sounds = new JCheckBox(
                StringLoader.loadString(StringConstants.PREFS_STRINGS_FILE,
                        StringConstants.PREFS_STRING_ENABLE_SOUNDS),
                true);
        this.music = new JCheckBox(
                StringLoader.loadString(StringConstants.PREFS_STRINGS_FILE,
                        StringConstants.PREFS_STRING_ENABLE_MUSIC),
                true);
        this.checkUpdatesStartup = new JCheckBox(
                StringLoader.loadString(StringConstants.PREFS_STRINGS_FILE,
                        StringConstants.PREFS_STRING_STARTUP_UPDATES),
                true);
        this.moveOneAtATime = new JCheckBox(
                StringLoader.loadString(StringConstants.PREFS_STRINGS_FILE,
                        StringConstants.PREFS_STRING_ONE_MOVE),
                true);
        this.enableAnimation = new JCheckBox(
                StringLoader.loadString(StringConstants.PREFS_STRINGS_FILE,
                        StringConstants.PREFS_STRING_ENABLE_ANIMATION),
                true);
        this.actionDelay = new JComboBox<>(new String[] {
                StringLoader.loadString(StringConstants.PREFS_STRINGS_FILE,
                        StringConstants.PREFS_STRING_SPEED_1),
                StringLoader.loadString(StringConstants.PREFS_STRINGS_FILE,
                        StringConstants.PREFS_STRING_SPEED_2),
                StringLoader.loadString(StringConstants.PREFS_STRINGS_FILE,
                        StringConstants.PREFS_STRING_SPEED_3),
                StringLoader.loadString(StringConstants.PREFS_STRINGS_FILE,
                        StringConstants.PREFS_STRING_SPEED_4),
                StringLoader.loadString(StringConstants.PREFS_STRINGS_FILE,
                        StringConstants.PREFS_STRING_SPEED_5) });
        this.languageList = new JComboBox<>(
                StringLoader.loadLocalizedLanguagesList());
        this.editorLayoutList = new JComboBox<>(
                EditorLayoutConstants.getEditorLayoutList());
        this.editorShowAllObjects = new JCheckBox(
                StringLoader.loadString(StringConstants.PREFS_STRINGS_FILE,
                        StringConstants.PREFS_STRING_SHOW_ALL_OBJECTS),
                true);
        this.prefFrame.setContentPane(mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.prefFrame.addWindowListener(handler);
        mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        settingsPane.setLayout(
                new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        settingsPane.add(this.sounds);
        settingsPane.add(this.music);
        settingsPane.add(this.enableAnimation);
        settingsPane.add(this.checkUpdatesStartup);
        settingsPane.add(this.moveOneAtATime);
        settingsPane.add(new JLabel(
                StringLoader.loadString(StringConstants.PREFS_STRINGS_FILE,
                        StringConstants.PREFS_STRING_SPEED_LABEL)));
        settingsPane.add(this.actionDelay);
        settingsPane.add(new JLabel(
                StringLoader.loadString(StringConstants.PREFS_STRINGS_FILE,
                        StringConstants.PREFS_STRING_ACTIVE_LANGUAGE_LABEL)));
        settingsPane.add(this.languageList);
        settingsPane.add(new JLabel(
                StringLoader.loadString(StringConstants.PREFS_STRINGS_FILE,
                        StringConstants.PREFS_STRING_EDITOR_LAYOUT_LABEL)));
        settingsPane.add(this.editorLayoutList);
        settingsPane.add(this.editorShowAllObjects);
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(prefsOK);
        buttonPane.add(prefsCancel);
        mainPrefPane.add(settingsPane, BorderLayout.CENTER);
        mainPrefPane.add(buttonPane, BorderLayout.SOUTH);
        prefsOK.addActionListener(handler);
        prefsCancel.addActionListener(handler);
        final Image iconlogo = LogoManager.getIconLogo();
        this.prefFrame.setIconImage(iconlogo);
        this.prefFrame.pack();
    }

    private class EventHandler implements ActionListener, WindowListener {
        public EventHandler() {
            // Do nothing
        }

        // Handle buttons
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                final PreferencesGUIManager pm = PreferencesGUIManager.this;
                final String cmd = e.getActionCommand();
                if (cmd.equals(StringLoader.loadString(
                        StringConstants.DIALOG_STRINGS_FILE,
                        StringConstants.DIALOG_STRING_OK_BUTTON))) {
                    pm.setPrefs();
                } else if (cmd.equals(StringLoader.loadString(
                        StringConstants.DIALOG_STRINGS_FILE,
                        StringConstants.DIALOG_STRING_CANCEL_BUTTON))) {
                    pm.hidePrefs();
                }
            } catch (final Exception ex) {
                LaserTank.getErrorLogger().logError(ex);
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
