/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.prefs;

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
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.puttysoftware.dungeondiver3.Application;
import com.puttysoftware.dungeondiver3.DungeonDiver3;
import com.puttysoftware.dungeondiver3.support.Support;
import com.puttysoftware.dungeondiver3.support.prefs.LocalPreferencesManager;
import com.puttysoftware.platform.Platform;

class PreferencesGUIManager {
    // Fields
    private JFrame prefFrame;
    private JComboBox<String> battleSpeedChoices;
    final JCheckBox[] music;
    private JCheckBox sound;
    private JCheckBox randomBattle;
    private JCheckBox checkUpdatesStartup;
    private JCheckBox checkBetaUpdatesStartup;
    private JCheckBox moveOneAtATime;
    private JCheckBox characterChangesPermanent;
    private JSlider generatorRandomness;
    private static final int GRID_LENGTH = 6;
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
        final Application app = DungeonDiver3.getApplication();
        if (app.getMode() == Application.STATUS_BATTLE) {
            // Don't show preferences while in a battle
            return;
        }
        app.setInPrefs();
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            this.prefFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
        }
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
        final Application app = DungeonDiver3.getApplication();
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
        this.generatorRandomness
                .setValue(PreferencesManager.getGeneratorRandomness());
        this.battleSpeedChoices
                .setSelectedIndex(PreferencesManager.getBattleSpeedValue());
        for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
            this.music[x].setSelected(PreferencesManager.getMusicEnabled(x));
        }
        this.checkUpdatesStartup
                .setSelected(PreferencesManager.shouldCheckUpdatesAtStartup());
        this.checkBetaUpdatesStartup.setSelected(
                PreferencesManager.shouldCheckBetaUpdatesAtStartup());
        this.moveOneAtATime.setSelected(PreferencesManager.oneMove());
        this.characterChangesPermanent
                .setSelected(PreferencesManager.areCharacterChangesPermanent());
        this.sound.setSelected(LocalPreferencesManager.getSoundsEnabled());
        this.randomBattle.setSelected(
                LocalPreferencesManager.getRandomBattleEnvironment());
    }

    void setPrefs() {
        PreferencesManager
                .setGeneratorRandomness(this.generatorRandomness.getValue());
        PreferencesManager
                .setBattleSpeed(this.battleSpeedChoices.getSelectedIndex());
        for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
            PreferencesManager.setMusicEnabled(x, this.music[x].isSelected());
        }
        PreferencesManager.setCheckUpdatesAtStartup(
                this.checkUpdatesStartup.isSelected());
        PreferencesManager.setCheckBetaUpdatesAtStartup(
                this.checkBetaUpdatesStartup.isSelected());
        PreferencesManager.setOneMove(this.moveOneAtATime.isSelected());
        PreferencesManager.setCharacterChangesPermanent(
                this.characterChangesPermanent.isSelected());
        LocalPreferencesManager.setSoundsEnabled(this.sound.isSelected());
        LocalPreferencesManager
                .setRandomBattleEnvironment(this.randomBattle.isSelected());
        this.hidePrefs();
    }

    private final void setDefaultPrefs() {
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
        Container mainPrefPane, buttonPane, gamePane, miscPane, mediaPane;
        JButton prefsOK, prefsCancel;
        prefTabPane = new JTabbedPane();
        mainPrefPane = new Container();
        gamePane = new Container();
        mediaPane = new Container();
        miscPane = new Container();
        prefTabPane.setOpaque(true);
        buttonPane = new Container();
        prefsOK = new JButton("OK");
        prefsOK.setDefaultCapable(true);
        this.prefFrame.getRootPane().setDefaultButton(prefsOK);
        prefsCancel = new JButton("Cancel");
        prefsCancel.setDefaultCapable(false);
        this.battleSpeedChoices = new JComboBox<>(
                PreferencesGUIManager.battleSpeedChoiceArray);
        this.generatorRandomness = new JSlider(SwingConstants.HORIZONTAL, 0, 6,
                3);
        this.generatorRandomness.setMajorTickSpacing(1);
        this.generatorRandomness.setPaintTicks(true);
        this.generatorRandomness.setSnapToTicks(true);
        final JLabel generatorRandomnessValue = new JLabel(
                "Generator Randomness");
        this.randomBattle = new JCheckBox("Randomize Battlefield", false);
        this.music[PreferencesManager.MUSIC_ALL] = new JCheckBox(
                "Enable ALL music", true);
        this.music[PreferencesManager.MUSIC_EXPLORING] = new JCheckBox(
                "Enable exploring music", true);
        this.music[PreferencesManager.MUSIC_BATTLE] = new JCheckBox(
                "Enable battle music", true);
        this.sound = new JCheckBox("Enable sounds", true);
        this.checkUpdatesStartup = new JCheckBox("Check for Updates at Startup",
                true);
        this.checkBetaUpdatesStartup = new JCheckBox(
                "Check for Pre-Release Updates at Startup", true);
        this.moveOneAtATime = new JCheckBox("One Move at a Time", true);
        this.characterChangesPermanent = new JCheckBox(
                "Make Character Changes Permanent", false);
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
        gamePane.add(generatorRandomnessValue);
        gamePane.add(this.generatorRandomness);
        gamePane.add(this.randomBattle);
        mediaPane.setLayout(
                new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
            mediaPane.add(this.music[x]);
        }
        mediaPane.add(this.sound);
        miscPane.setLayout(
                new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        miscPane.add(this.checkUpdatesStartup);
        if (Support.isBetaModeEnabled()) {
            miscPane.add(this.checkBetaUpdatesStartup);
        }
        miscPane.add(this.characterChangesPermanent);
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(prefsOK);
        buttonPane.add(prefsCancel);
        prefTabPane.addTab("Game", null, gamePane, "Game");
        prefTabPane.addTab("Media", null, mediaPane, "Media");
        prefTabPane.addTab("Misc.", null, miscPane, "Misc.");
        mainPrefPane.add(prefTabPane, BorderLayout.CENTER);
        mainPrefPane.add(buttonPane, BorderLayout.SOUTH);
        this.music[PreferencesManager.MUSIC_ALL].addItemListener(handler);
        prefsOK.addActionListener(handler);
        prefsCancel.addActionListener(handler);
        Platform.hookFrameIcon(this.prefFrame, Application.getIconLogo());
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
                if (cmd.equals("OK")) {
                    pm.setPrefs();
                } else if (cmd.equals("Cancel")) {
                    pm.hidePrefs();
                }
            } catch (final Exception ex) {
                DungeonDiver3.getErrorLogger().logError(ex);
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
                DungeonDiver3.getErrorLogger().logError(ex);
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
