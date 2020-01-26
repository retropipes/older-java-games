/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.prefs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import net.dynamicdungeon.commondialogs.CommonDialogs;
import net.dynamicdungeon.dynamicdungeon.Application;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.LogoManager;

class PreferencesGUIManager {
    // Fields
    private JFrame prefFrame;
    private JCheckBox sound;
    private JCheckBox moveOneAtATime;
    private JCheckBox useTimeBattleEngine;
    private ButtonGroup viewingWindowGroup;
    private JRadioButton[] viewingWindowChoices;
    private JComboBox<String> difficultyPicker;
    static final int[] VIEWING_WINDOW_SIZES = new int[] { 7, 11, 15, 19, 23 };
    static final int DEFAULT_SIZE_INDEX = 2;
    static final int DEFAULT_VIEWING_WINDOW_SIZE = PreferencesGUIManager.VIEWING_WINDOW_SIZES[PreferencesGUIManager.DEFAULT_SIZE_INDEX];
    private static final String[] VIEWING_WINDOW_SIZE_NAMES = new String[] {
            "Tiny", "Small", "Medium", "Large", "Huge" };
    private static final String[] DIFFICULTY_NAMES = new String[] { "Very Easy",
            "Easy", "Normal", "Hard", "Very Hard" };
    private static final int GRID_LENGTH = 7;

    // Constructors
    public PreferencesGUIManager() {
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
        final Application app = DynamicDungeon.getApplication();
        if (app.getMode() == Application.STATUS_BATTLE) {
            // Deny
            CommonDialogs.showTitledDialog(
                    "Preferences may NOT be changed in the middle of battle!",
                    "Battle");
        } else {
            app.setMode(Application.STATUS_PREFS);
            if (System.getProperty("os.name").startsWith("Mac OS X")) {
                this.prefFrame
                        .setJMenuBar(app.getMenuManager().getMainMenuBar());
            }
            app.getMenuManager().setPrefMenus();
            this.prefFrame.setVisible(true);
            final int formerMode = app.getFormerMode();
            app.restoreFormerMode();
            if (formerMode == Application.STATUS_GUI) {
                app.getGUIManager().hideGUITemporarily();
            } else if (formerMode == Application.STATUS_GAME) {
                app.getGameManager().hideOutput();
            }
        }
    }

    public void hidePrefs() {
        final Application app = DynamicDungeon.getApplication();
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
        this.moveOneAtATime.setSelected(PreferencesManager.oneMove());
        final int index = PreferencesManager.getViewingWindowSizeIndex();
        this.viewingWindowChoices[index].setSelected(true);
        this.sound.setSelected(PreferencesManager.getSoundsEnabled());
        this.useTimeBattleEngine
                .setSelected(PreferencesManager.useTimeBattleEngine());
        this.difficultyPicker
                .setSelectedIndex(PreferencesManager.getGameDifficulty());
    }

    public void setPrefs() {
        PreferencesManager.setOneMove(this.moveOneAtATime.isSelected());
        final int vwSize = PreferencesManager.getViewingWindowSizeIndex();
        final int newSize = this.viewingWindowGroup.getSelection()
                .getMnemonic();
        PreferencesManager.setViewingWindowSizeIndex(newSize);
        if (vwSize != newSize) {
            DynamicDungeon.getApplication().getGameManager()
                    .viewingWindowSizeChanged();
            DynamicDungeon.getApplication().resetBattleGUI();
        }
        PreferencesManager.setSoundsEnabled(this.sound.isSelected());
        PreferencesManager
                .setTimeBattleEngine(this.useTimeBattleEngine.isSelected());
        PreferencesManager
                .setGameDifficulty(this.difficultyPicker.getSelectedIndex());
        this.hidePrefs();
    }

    public final void setDefaultPrefs() {
        PreferencesManager.readPrefs();
        this.loadPrefs();
    }

    private void setUpGUI() {
        final EventHandler handler = new EventHandler();
        this.prefFrame = new JFrame("Preferences");
        final Image iconlogo = LogoManager.getIconLogo();
        this.prefFrame.setIconImage(iconlogo);
        final JTabbedPane prefTabPane = new JTabbedPane();
        final Container mainPrefPane = new Container();
        final Container miscPane = new Container();
        final Container viewPane = new Container();
        prefTabPane.setOpaque(true);
        final Container buttonPane = new Container();
        final JButton prefsOK = new JButton("OK");
        prefsOK.setDefaultCapable(true);
        this.prefFrame.getRootPane().setDefaultButton(prefsOK);
        final JButton prefsCancel = new JButton("Cancel");
        prefsCancel.setDefaultCapable(false);
        this.viewingWindowGroup = new ButtonGroup();
        this.viewingWindowChoices = new JRadioButton[PreferencesGUIManager.VIEWING_WINDOW_SIZE_NAMES.length];
        for (int z = 0; z < PreferencesGUIManager.VIEWING_WINDOW_SIZE_NAMES.length; z++) {
            this.viewingWindowChoices[z] = new JRadioButton(
                    PreferencesGUIManager.VIEWING_WINDOW_SIZE_NAMES[z]);
            this.viewingWindowChoices[z].setMnemonic(z);
            this.viewingWindowGroup.add(this.viewingWindowChoices[z]);
        }
        this.sound = new JCheckBox("Enable sounds", true);
        this.moveOneAtATime = new JCheckBox("One Move at a Time", true);
        this.useTimeBattleEngine = new JCheckBox("Use Time Battle Engine",
                false);
        this.difficultyPicker = new JComboBox<>(
                PreferencesGUIManager.DIFFICULTY_NAMES);
        this.prefFrame.setContentPane(mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.prefFrame.addWindowListener(handler);
        mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        miscPane.setLayout(
                new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        miscPane.add(this.sound);
        miscPane.add(this.moveOneAtATime);
        miscPane.add(this.useTimeBattleEngine);
        miscPane.add(new JLabel("Game Difficulty"));
        miscPane.add(this.difficultyPicker);
        viewPane.setLayout(
                new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        viewPane.add(new JLabel("Viewing Window Size"));
        for (int z = 0; z < PreferencesGUIManager.VIEWING_WINDOW_SIZE_NAMES.length; z++) {
            viewPane.add(this.viewingWindowChoices[z]);
        }
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(prefsOK);
        buttonPane.add(prefsCancel);
        prefTabPane.addTab("View", null, viewPane, "View");
        prefTabPane.addTab("Misc.", null, miscPane, "Misc.");
        mainPrefPane.add(prefTabPane, BorderLayout.CENTER);
        mainPrefPane.add(buttonPane, BorderLayout.SOUTH);
        prefsOK.addActionListener(handler);
        prefsCancel.addActionListener(handler);
        this.prefFrame.pack();
    }

    private class EventHandler implements ActionListener, WindowListener {
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
                DynamicDungeon.getErrorLogger().logError(ex);
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
