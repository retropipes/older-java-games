/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.prefs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

import com.puttysoftware.riskyrescue.Application;
import com.puttysoftware.riskyrescue.RiskyRescue;
import com.puttysoftware.riskyrescue.Support;

class PreferencesGUIManager {
    // Fields
    private JFrame prefFrame;
    private JComboBox<String> battleSpeedChoices;
    final JCheckBox[] music;
    private JCheckBox sound;
    private JCheckBox moveOneAtATime;
    private static final int GRID_LENGTH = 4;
    private static final String[] battleSpeedChoiceArray = new String[] {
            "Very Slow", "Slow", "Moderate", "Fast", "Very Fast" };

    // Constructors
    PreferencesGUIManager() {
        this.music = new JCheckBox[PreferencesManager.MUSIC_LENGTH];
        this.setUpGUI();
        this.setDefaultPrefs();
    }

    // Methods
    public JFrame getPrefFrame() {
        if (this.prefFrame != null && this.prefFrame.isVisible()) {
            return this.prefFrame;
        } else {
            return null;
        }
    }

    public void showPrefs() {
        final Application app = RiskyRescue.getApplication();
        if (app.getMode() == Application.STATUS_BATTLE) {
            // Don't show preferences while in a battle
            return;
        }
        app.setInPrefs();
        app.attachMenus(this.prefFrame);
        app.getMenuManager().setPrefMenus();
        this.prefFrame.setVisible(true);
        final int formerMode = app.getFormerMode();
        if (formerMode == Application.STATUS_GUI) {
            app.getGUIManager().hideGUI();
        } else if (formerMode == Application.STATUS_GAME) {
            app.getGameManager().hideOutput();
        }
    }

    void hidePrefs() {
        final Application app = RiskyRescue.getApplication();
        this.prefFrame.setVisible(false);
        PreferencesManager.writePrefs();
        final int formerMode = app.getFormerMode();
        app.restoreFormerMode();
        if (formerMode == Application.STATUS_GUI) {
            app.getGUIManager().showGUI();
        } else if (formerMode == Application.STATUS_GAME) {
            app.getGameManager().showOutput();
        }
    }

    private void loadPrefs() {
        this.battleSpeedChoices
                .setSelectedIndex(PreferencesManager.getBattleSpeedValue());
        for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
            this.music[x].setSelected(PreferencesManager.getMusicEnabled(x));
        }
        this.moveOneAtATime.setSelected(PreferencesManager.oneMove());
        this.sound.setSelected(LocalPreferencesManager.getSoundsEnabled());
    }

    void setPrefs() {
        PreferencesManager
                .setBattleSpeed(this.battleSpeedChoices.getSelectedIndex());
        for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
            PreferencesManager.setMusicEnabled(x, this.music[x].isSelected());
        }
        PreferencesManager.setOneMove(this.moveOneAtATime.isSelected());
        LocalPreferencesManager.setSoundsEnabled(this.sound.isSelected());
        this.hidePrefs();
    }

    private void setDefaultPrefs() {
        PreferencesManager.readPrefs();
        LocalPreferencesManager.readPrefs();
        this.loadPrefs();
    }

    private void setUpGUI() {
        final EventHandler handler = new EventHandler();
        if (Support.inDebugMode()) {
            this.prefFrame = new JFrame("Preferences (DEBUG)");
        } else {
            this.prefFrame = new JFrame("Preferences");
        }
        JTabbedPane prefTabPane;
        Container mainPrefPane, buttonPane, gamePane, mediaPane;
        JButton prefsOK, prefsCancel;
        prefTabPane = new JTabbedPane();
        mainPrefPane = new Container();
        gamePane = new Container();
        mediaPane = new Container();
        prefTabPane.setOpaque(true);
        buttonPane = new Container();
        prefsOK = new JButton("OK");
        prefsOK.setDefaultCapable(true);
        this.prefFrame.getRootPane().setDefaultButton(prefsOK);
        prefsCancel = new JButton("Cancel");
        prefsCancel.setDefaultCapable(false);
        this.battleSpeedChoices = new JComboBox<>(
                PreferencesGUIManager.battleSpeedChoiceArray);
        this.music[PreferencesManager.MUSIC_ALL] = new JCheckBox(
                "Enable ALL music", true);
        this.music[PreferencesManager.MUSIC_DUNGEON] = new JCheckBox(
                "Enable dungeon music", true);
        this.music[PreferencesManager.MUSIC_BATTLE] = new JCheckBox(
                "Enable battle music", true);
        this.sound = new JCheckBox("Enable sounds", true);
        this.moveOneAtATime = new JCheckBox("One Move at a Time", true);
        this.prefFrame.setContentPane(mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.prefFrame.addWindowListener(handler);
        mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        gamePane.setLayout(
                new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        gamePane.add(new JLabel("Battle Speed"));
        gamePane.add(this.battleSpeedChoices);
        gamePane.add(this.moveOneAtATime);
        mediaPane.setLayout(
                new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
            mediaPane.add(this.music[x]);
        }
        mediaPane.add(this.sound);
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(prefsOK);
        buttonPane.add(prefsCancel);
        prefTabPane.addTab("Game", null, gamePane, "Game");
        prefTabPane.addTab("Media", null, mediaPane, "Media");
        mainPrefPane.add(prefTabPane, BorderLayout.CENTER);
        mainPrefPane.add(buttonPane, BorderLayout.SOUTH);
        this.music[PreferencesManager.MUSIC_ALL].addItemListener(handler);
        prefsOK.addActionListener(handler);
        prefsCancel.addActionListener(handler);
        this.prefFrame.pack();
    }

    private class EventHandler
            implements ActionListener, ItemListener, WindowListener {
        public EventHandler() {
            // Do nothing
        }

        // Handle buttons
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                final PreferencesGUIManager pm = PreferencesGUIManager.this;
                final String cmd = e.getActionCommand();
                switch (cmd) {
                case "OK":
                    pm.setPrefs();
                    break;
                case "Cancel":
                    pm.hidePrefs();
                    break;
                default:
                    break;
                }
            } catch (final Exception ex) {
                RiskyRescue.logError(ex);
            }
        }

        @Override
        public void itemStateChanged(final ItemEvent e) {
            try {
                final PreferencesGUIManager pm = PreferencesGUIManager.this;
                final Object o = e.getItem();
                if (o.getClass().equals(JCheckBox.class)) {
                    final JCheckBox check = (JCheckBox) o;
                    if (check.equals(pm.music[PreferencesManager.MUSIC_ALL])) {
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
                RiskyRescue.logError(ex);
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
