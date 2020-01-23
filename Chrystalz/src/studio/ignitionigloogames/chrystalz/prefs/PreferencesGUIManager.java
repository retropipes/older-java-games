/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.prefs;

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

import studio.ignitionigloogames.chrystalz.Application;
import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.manager.asset.LogoManager;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;

class PreferencesGUIManager {
    // Fields
    private JFrame prefFrame;
    JCheckBox music;
    private JCheckBox sound;
    private JCheckBox moveOneAtATime;
    private JComboBox<String> difficultyPicker;
    private static final String[] DIFFICULTY_NAMES = new String[] { "Very Easy",
            "Easy", "Normal", "Hard", "Very Hard" };
    private static final int GRID_LENGTH = 5;

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
        final Application app = Chrystalz.getApplication();
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
                app.getGUIManager().hideGUI();
            } else if (formerMode == Application.STATUS_GAME) {
                app.getGame().hideOutput();
            }
        }
    }

    public void hidePrefs() {
        final Application app = Chrystalz.getApplication();
        this.prefFrame.setVisible(false);
        PreferencesManager.writePrefs();
        final int formerMode = app.getFormerMode();
        if (formerMode == Application.STATUS_GUI) {
            app.getGUIManager().showGUIAndKeepMusic();
        } else if (formerMode == Application.STATUS_GAME) {
            app.getGame().showOutputAndKeepMusic();
        }
    }

    private void loadPrefs() {
        this.music.setSelected(PreferencesManager.getMusicEnabled());
        this.moveOneAtATime.setSelected(PreferencesManager.oneMove());
        this.sound.setSelected(PreferencesManager.getSoundsEnabled());
        this.difficultyPicker
                .setSelectedIndex(PreferencesManager.getGameDifficulty());
    }

    public void setPrefs() {
        PreferencesManager.setMusicEnabled(this.music.isSelected());
        PreferencesManager.setOneMove(this.moveOneAtATime.isSelected());
        PreferencesManager.setSoundsEnabled(this.sound.isSelected());
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
        final Container mainPrefPane = new Container();
        final Container innerPrefPane = new Container();
        final Container buttonPane = new Container();
        final JButton prefsOK = new JButton("OK");
        prefsOK.setDefaultCapable(true);
        this.prefFrame.getRootPane().setDefaultButton(prefsOK);
        final JButton prefsCancel = new JButton("Cancel");
        prefsCancel.setDefaultCapable(false);
        this.music = new JCheckBox("Enable music", true);
        this.sound = new JCheckBox("Enable sounds", true);
        this.moveOneAtATime = new JCheckBox("One Move at a Time", true);
        this.difficultyPicker = new JComboBox<>(
                PreferencesGUIManager.DIFFICULTY_NAMES);
        this.prefFrame.setContentPane(mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.prefFrame.addWindowListener(handler);
        mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        innerPrefPane.setLayout(
                new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        innerPrefPane.add(this.music);
        innerPrefPane.add(this.sound);
        innerPrefPane.add(this.moveOneAtATime);
        innerPrefPane.add(new JLabel("Game Difficulty"));
        innerPrefPane.add(this.difficultyPicker);
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(prefsOK);
        buttonPane.add(prefsCancel);
        mainPrefPane.add(innerPrefPane, BorderLayout.CENTER);
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
                Chrystalz.getErrorLogger().logError(ex);
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
