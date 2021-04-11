/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.dungeondiver2.prefs;

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
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import net.worldwizard.dungeondiver2.Application;
import net.worldwizard.dungeondiver2.DungeonDiver2;
import net.worldwizard.support.Support;

class PreferencesGUIManager {
    // Fields
    private JFrame prefFrame;
    private JTabbedPane prefTabPane;
    private Container mainPrefPane, buttonPane, gamePane, miscPane, musicPane;
    private JButton prefsOK, prefsCancel;
    private JComboBox<String> battleSpeedChoices;
    private String[] battleSpeedChoiceArray;
    private JSlider generatorRandomness;
    private JLabel generatorRandomnessValue;
    final JCheckBox[] music;
    private JCheckBox checkUpdatesStartup;
    private JCheckBox checkBetaUpdatesStartup;
    private JCheckBox moveOneAtATime;
    private EventHandler handler;

    // Constructors
    PreferencesGUIManager() {
        this.music = new JCheckBox[PreferencesManager.MUSIC_LENGTH];
        this.setUpGUI();
        this.setDefaultPrefs();
    }

    // Methods
    private static int getGridLength() {
        return 5;
    }

    public JFrame getPrefFrame() {
        if (this.prefFrame != null && this.prefFrame.isVisible()) {
            return this.prefFrame;
        } else {
            return null;
        }
    }

    public void showPrefs() {
        final Application app = DungeonDiver2.getApplication();
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
        }
    }

    public void hidePrefs() {
        final Application app = DungeonDiver2.getApplication();
        app.setInPrefs(false);
        this.prefFrame.setVisible(false);
        PreferencesManager.writePrefs();
        final int formerMode = app.getFormerMode();
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
    }

    public void setPrefs() {
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
        this.hidePrefs();
    }

    public final void setDefaultPrefs() {
        PreferencesManager.readPrefs();
        this.loadPrefs();
    }

    private void setUpGUI() {
        this.handler = new EventHandler();
        this.prefFrame = new JFrame("Preferences");
        this.prefTabPane = new JTabbedPane();
        this.mainPrefPane = new Container();
        this.gamePane = new Container();
        this.musicPane = new Container();
        this.miscPane = new Container();
        this.prefTabPane.setOpaque(true);
        this.buttonPane = new Container();
        this.prefsOK = new JButton("OK");
        this.prefsOK.setDefaultCapable(true);
        this.prefFrame.getRootPane().setDefaultButton(this.prefsOK);
        this.prefsCancel = new JButton("Cancel");
        this.prefsCancel.setDefaultCapable(false);
        this.battleSpeedChoiceArray = new String[] { "Very Slow", "Slow",
                "Moderate", "Fast", "Very Fast" };
        this.generatorRandomness = new JSlider(SwingConstants.HORIZONTAL, 0, 6,
                3);
        this.generatorRandomness.setMajorTickSpacing(1);
        this.generatorRandomness.setPaintTicks(true);
        this.generatorRandomness.setSnapToTicks(true);
        this.generatorRandomnessValue = new JLabel("Generator Randomness");
        this.battleSpeedChoices = new JComboBox<>(this.battleSpeedChoiceArray);
        this.music[PreferencesManager.MUSIC_ALL] = new JCheckBox(
                "Enable ALL music", true);
        this.music[PreferencesManager.MUSIC_EXPLORING] = new JCheckBox(
                "Enable exploring music", true);
        this.music[PreferencesManager.MUSIC_BATTLE] = new JCheckBox(
                "Enable battle music", true);
        this.music[PreferencesManager.MUSIC_BOSS] = new JCheckBox(
                "Enable boss music", true);
        this.checkUpdatesStartup = new JCheckBox("Check for Updates at Startup",
                true);
        this.checkBetaUpdatesStartup = new JCheckBox(
                "Check for Beta Updates at Startup", true);
        this.moveOneAtATime = new JCheckBox("One Move at a Time", true);
        this.prefFrame.setContentPane(this.mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.prefFrame.addWindowListener(this.handler);
        this.mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        this.gamePane.setLayout(
                new GridLayout(PreferencesGUIManager.getGridLength(), 1));
        this.gamePane.add(new JLabel("Battle Speed"));
        this.gamePane.add(this.battleSpeedChoices);
        this.gamePane.add(this.moveOneAtATime);
        this.gamePane.add(this.generatorRandomnessValue);
        this.gamePane.add(this.generatorRandomness);
        this.musicPane.setLayout(
                new GridLayout(PreferencesGUIManager.getGridLength(), 1));
        for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
            this.musicPane.add(this.music[x]);
        }
        this.miscPane.setLayout(
                new GridLayout(PreferencesGUIManager.getGridLength(), 1));
        this.miscPane.add(this.checkUpdatesStartup);
        if (Support.isBetaModeEnabled()) {
            this.miscPane.add(this.checkBetaUpdatesStartup);
        }
        this.buttonPane.setLayout(new FlowLayout());
        this.buttonPane.add(this.prefsOK);
        this.buttonPane.add(this.prefsCancel);
        this.prefTabPane.addTab("Game", null, this.gamePane, "Game");
        this.prefTabPane.addTab("Music", null, this.musicPane, "Music");
        this.prefTabPane.addTab("Misc.", null, this.miscPane, "Misc.");
        this.mainPrefPane.add(this.prefTabPane, BorderLayout.CENTER);
        this.mainPrefPane.add(this.buttonPane, BorderLayout.SOUTH);
        this.music[PreferencesManager.MUSIC_ALL].addItemListener(this.handler);
        this.prefsOK.addActionListener(this.handler);
        this.prefsCancel.addActionListener(this.handler);
        final Image iconlogo = DungeonDiver2.getApplication().getIconLogo();
        this.prefFrame.setIconImage(iconlogo);
        this.prefFrame.pack();
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
                final PreferencesGUIManager pm = PreferencesGUIManager.this;
                final String cmd = e.getActionCommand();
                if (cmd.equals("OK")) {
                    pm.setPrefs();
                } else if (cmd.equals("Cancel")) {
                    pm.hidePrefs();
                }
            } catch (final Exception ex) {
                DungeonDiver2.getErrorLogger().logError(ex);
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
                DungeonDiver2.getErrorLogger().logError(ex);
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
