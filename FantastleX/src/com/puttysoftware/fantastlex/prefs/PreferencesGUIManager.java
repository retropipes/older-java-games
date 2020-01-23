/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.prefs;

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

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;

class PreferencesGUIManager {
    // Fields
    private JFrame prefFrame;
    JCheckBox[] music;
    private JCheckBox sound;
    private JCheckBox checkUpdatesStartup;
    private JCheckBox moveOneAtATime;
    private JCheckBox editorAutoEdge;
    private JComboBox<String> editorFillChoices;
    private JComboBox<String> viewingWindowChoices;
    private JComboBox<String> battleSpeedChoices;
    private JCheckBox randomBattle;
    private JCheckBox characterChangesPermanent;
    private JCheckBox battleStyle;
    private static final int[] VIEWING_WINDOW_SIZES = new int[] { 19, 17, 23,
            27, 33 };
    private static final int DEFAULT_SIZE_INDEX = 3;
    static final int DEFAULT_VIEWING_WINDOW_SIZE = PreferencesGUIManager.VIEWING_WINDOW_SIZES[PreferencesGUIManager.DEFAULT_SIZE_INDEX];
    private static final String[] VIEWING_WINDOW_SIZE_NAMES = new String[] {
            "Mobile", "Small", "Medium", "Large", "Huge" };
    private static final String[] editorFillChoiceArray = new String[] {
            "Grass", "Dirt", "Sand", "Snow", "Tile", "Tundra" };
    private static final String[] battleSpeedChoiceArray = new String[] {
            "Very Slow", "Slow", "Moderate", "Fast", "Very Fast" };
    private static final int GRID_LENGTH = 5;

    // Constructors
    public PreferencesGUIManager() {
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
        final Application app = FantastleX.getApplication();
        app.setInPrefs();
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
        final Application app = FantastleX.getApplication();
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
        for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
            this.music[x].setSelected(PreferencesManager.getMusicEnabled(x));
        }
        this.checkUpdatesStartup.setSelected(PreferencesManager
                .shouldCheckUpdatesAtStartup());
        this.moveOneAtATime.setSelected(PreferencesManager.oneMove());
        this.viewingWindowChoices.setSelectedIndex(PreferencesGUIManager
                .viewingWindowSizeToIndex(PreferencesManager
                        .getViewingWindowSize()));
        this.battleSpeedChoices.setSelectedIndex(PreferencesManager
                .getBattleSpeedValue());
        this.sound.setSelected(PreferencesManager.getSoundsEnabled());
        this.characterChangesPermanent.setSelected(PreferencesManager
                .areCharacterChangesPermanent());
        this.randomBattle.setSelected(PreferencesManager
                .getRandomBattleEnvironment());
        this.battleStyle.setSelected(PreferencesManager.getBattleStyle());
    }

    public void setPrefs() {
        PreferencesManager.setEditorDefaultFill((String) this.editorFillChoices
                .getSelectedItem());
        PreferencesManager.setEditorAutoEdge(this.editorAutoEdge.isSelected());
        for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
            PreferencesManager.setMusicEnabled(x, this.music[x].isSelected());
        }
        PreferencesManager.setCheckUpdatesAtStartup(this.checkUpdatesStartup
                .isSelected());
        PreferencesManager.setOneMove(this.moveOneAtATime.isSelected());
        final int vwSize = PreferencesManager.getViewingWindowSize();
        final int newSize = PreferencesGUIManager.VIEWING_WINDOW_SIZES[this.viewingWindowChoices
                .getSelectedIndex()];
        PreferencesManager.setViewingWindowSize(newSize);
        if (vwSize != newSize) {
            FantastleX.getApplication().getGameManager()
                    .viewingWindowSizeChanged();
            FantastleX.getApplication().getEditor().viewingWindowSizeChanged();
        }
        PreferencesManager.setBattleSpeed(this.battleSpeedChoices
                .getSelectedIndex());
        PreferencesManager
                .setCharacterChangesPermanent(this.characterChangesPermanent
                        .isSelected());
        PreferencesManager.setSoundsEnabled(this.sound.isSelected());
        PreferencesManager.setRandomBattleEnvironment(this.randomBattle
                .isSelected());
        PreferencesManager.setBattleStyle(this.battleStyle.isSelected());
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
        final Container gamePane = new Container();
        final Container editorPane = new Container();
        final Container mediaPane = new Container();
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
        this.music[PreferencesManager.MUSIC_ALL] = new JCheckBox(
                "Enable ALL music", true);
        this.music[PreferencesManager.MUSIC_EXPLORING] = new JCheckBox(
                "Enable exploring music", true);
        this.music[PreferencesManager.MUSIC_BATTLE] = new JCheckBox(
                "Enable battle music", true);
        this.sound = new JCheckBox("Enable sounds", true);
        this.checkUpdatesStartup = new JCheckBox(
                "Check for Updates at Startup", true);
        this.moveOneAtATime = new JCheckBox("One Move at a Time", true);
        this.battleSpeedChoices = new JComboBox<>(
                PreferencesGUIManager.battleSpeedChoiceArray);
        this.randomBattle = new JCheckBox("Randomize Battlefield", true);
        this.characterChangesPermanent = new JCheckBox(
                "Make Character Changes Permanent", true);
        this.battleStyle = new JCheckBox("Enable Map-Based Battles", true);
        this.prefFrame.setContentPane(mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.prefFrame.addWindowListener(handler);
        mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        gamePane.setLayout(new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        gamePane.add(new JLabel("Battle Speed"));
        gamePane.add(this.battleSpeedChoices);
        gamePane.add(this.moveOneAtATime);
        gamePane.add(this.randomBattle);
        gamePane.add(this.battleStyle);
        editorPane.setLayout(new GridLayout(PreferencesGUIManager.GRID_LENGTH,
                1));
        editorPane.add(new JLabel("Default fill for new mazes:"));
        editorPane.add(this.editorFillChoices);
        editorPane.add(this.editorAutoEdge);
        mediaPane
                .setLayout(new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        for (int x = 0; x < PreferencesManager.MUSIC_LENGTH; x++) {
            mediaPane.add(this.music[x]);
        }
        mediaPane.add(this.sound);
        miscPane.setLayout(new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        miscPane.add(this.checkUpdatesStartup);
        miscPane.add(new JLabel("Viewing Window Size"));
        miscPane.add(this.viewingWindowChoices);
        miscPane.add(this.characterChangesPermanent);
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(prefsOK);
        buttonPane.add(prefsCancel);
        prefTabPane.addTab("Game", null, gamePane, "Game");
        prefTabPane.addTab("Editor", null, editorPane, "Editor");
        prefTabPane.addTab("Media", null, mediaPane, "Media");
        prefTabPane.addTab("Misc.", null, miscPane, "Misc.");
        mainPrefPane.add(prefTabPane, BorderLayout.CENTER);
        mainPrefPane.add(buttonPane, BorderLayout.SOUTH);
        this.music[PreferencesManager.MUSIC_ALL].addItemListener(handler);
        prefsOK.addActionListener(handler);
        prefsCancel.addActionListener(handler);
        final Image iconlogo = FantastleX.getApplication().getIconLogo();
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
                FantastleX.getErrorLogger().logError(ex);
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
                FantastleX.getErrorLogger().logError(ex);
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
