package studio.ignitionigloogames.dungeondiver1;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;

import studio.ignitionigloogames.dungeondiver1.gui.MainWindow;

public class Preferences {
    // Fields
    private JPanel mainPrefPane, subPrefPane1, subPrefPane2;
    private JButton prefsOK, prefsCancel;
    private boolean[] prefValues;
    private boolean[] savedPrefValues;
    JCheckBox[] prefs;
    private EventHandler handler;
    public static final int MOVEMENT_MODIFIERS_DISABLED = 0;
    public static final int CONFUSION_TRAP_ENABLED = 1;
    public static final int CLOCKWISE_ROTATION_TRAP_ENABLED = 2;
    public static final int COUNTERCLOCKWISE_ROTATION_TRAP_ENABLED = 3;
    public static final int SPINNER_TRAP_ENABLED = 4;
    public static final int WARP_TRAP_ENABLED = 5;
    public static final int ICE_ENABLED = 6;
    public static final int MONSTERS_ON_MAP_ENABLED = 7;
    private static final int GLOBAL_DISABLE_LOWER_LIMIT = 1;
    private static final int GLOBAL_DISABLE_UPPER_LIMIT = 7;
    private static final int MAX_PREFS = 8;

    // Constructors
    public Preferences() {
        this.setUpGUI();
        this.setDefaultPrefs();
    }

    // Methods
    public boolean getPreferenceValue(final int pref) {
        boolean result = false;
        if (pref >= 0 && pref < MAX_PREFS) {
            result = this.prefValues[pref];
        }
        return result;
    }

    private void restorePreferenceValues() {
        for (int x = 0; x < Preferences.MAX_PREFS; x++) {
            this.prefs[x].setSelected(this.savedPrefValues[x]);
        }
    }

    private void savePreferenceValues() {
        for (int x = 0; x < Preferences.MAX_PREFS; x++) {
            this.savedPrefValues[x] = this.prefs[x].isSelected();
        }
    }

    public void setPreferenceValue(final int pref, final boolean status) {
        if (pref >= 0 && pref < MAX_PREFS) {
            this.prefs[pref].setSelected(status);
            this.prefValues[pref] = status;
        }
    }

    public void showPrefs() {
        this.savePreferenceValues();
        MainWindow main = MainWindow.getMainWindow();
        main.setTitle("Preferences");
        main.attachAndSave(this.mainPrefPane);
        main.addWindowListener(this.handler);
    }

    public void hidePrefs() {
        MainWindow.getMainWindow().restoreSaved();
    }

    void cancelPrefs() {
        this.restorePreferenceValues();
        this.hidePrefs();
    }

    void setPrefs() {
        this.hidePrefs();
        for (int x = 0; x < Preferences.MAX_PREFS; x++) {
            this.prefValues[x] = this.prefs[x].isSelected();
        }
    }

    private void setDefaultPrefs() {
        this.setPreferenceValue(Preferences.MOVEMENT_MODIFIERS_DISABLED, false);
        this.setPreferenceValue(Preferences.CONFUSION_TRAP_ENABLED, true);
        this.setPreferenceValue(Preferences.CLOCKWISE_ROTATION_TRAP_ENABLED,
                true);
        this.setPreferenceValue(
                Preferences.COUNTERCLOCKWISE_ROTATION_TRAP_ENABLED, true);
        this.setPreferenceValue(Preferences.SPINNER_TRAP_ENABLED, true);
        this.setPreferenceValue(Preferences.WARP_TRAP_ENABLED, true);
        this.setPreferenceValue(Preferences.ICE_ENABLED, true);
        this.setPreferenceValue(Preferences.MONSTERS_ON_MAP_ENABLED, true);
    }

    private void setUpGUI() {
        this.handler = new EventHandler();
        this.mainPrefPane = new JPanel();
        this.subPrefPane1 = new JPanel();
        this.subPrefPane2 = new JPanel();
        this.prefsOK = new JButton("OK");
        this.prefsOK.setDefaultCapable(true);
        this.prefsCancel = new JButton("Cancel");
        this.prefsCancel.setDefaultCapable(false);
        this.prefs = new JCheckBox[Preferences.MAX_PREFS];
        this.prefValues = new boolean[Preferences.MAX_PREFS];
        this.savedPrefValues = new boolean[Preferences.MAX_PREFS];
        this.prefs[Preferences.MOVEMENT_MODIFIERS_DISABLED] = new JCheckBox(
                "Disable ALL movement modifiers", false);
        this.prefs[Preferences.CONFUSION_TRAP_ENABLED] = new JCheckBox(
                "Enable confusion traps", true);
        this.prefs[Preferences.CLOCKWISE_ROTATION_TRAP_ENABLED] = new JCheckBox(
                "Enable clockwise rotation traps", true);
        this.prefs[Preferences.COUNTERCLOCKWISE_ROTATION_TRAP_ENABLED] = new JCheckBox(
                "Enable counterclockwise rotation traps", true);
        this.prefs[Preferences.SPINNER_TRAP_ENABLED] = new JCheckBox(
                "Enable spinner traps", true);
        this.prefs[Preferences.WARP_TRAP_ENABLED] = new JCheckBox(
                "Enable warp traps", true);
        this.prefs[Preferences.ICE_ENABLED] = new JCheckBox("Enable ice", true);
        this.prefs[Preferences.MONSTERS_ON_MAP_ENABLED] = new JCheckBox(
                "Show monsters in dungeon", true);
        this.mainPrefPane.setLayout(new BorderLayout());
        this.subPrefPane1.setLayout(new GridLayout(Preferences.MAX_PREFS, 1));
        for (int x = 0; x < Preferences.MAX_PREFS; x++) {
            this.subPrefPane1.add(this.prefs[x]);
        }
        this.subPrefPane2.setLayout(new FlowLayout());
        this.subPrefPane2.add(this.prefsOK);
        this.subPrefPane2.add(this.prefsCancel);
        this.mainPrefPane.add(this.subPrefPane1, BorderLayout.CENTER);
        this.mainPrefPane.add(this.subPrefPane2, BorderLayout.SOUTH);
        this.prefs[Preferences.MOVEMENT_MODIFIERS_DISABLED]
                .addItemListener(this.handler);
        this.prefsOK.addActionListener(this.handler);
        this.prefsCancel.addActionListener(this.handler);
    }

    private class EventHandler
            implements ActionListener, ItemListener, WindowListener {
        public EventHandler() {
            // TODO Auto-generated constructor stub
        }

        // Handle buttons
        @Override
        public void actionPerformed(final ActionEvent e) {
            final String cmd = e.getActionCommand();
            if (cmd.equals("OK")) {
                Preferences.this.setPrefs();
            } else if (cmd.equals("Cancel")) {
                Preferences.this.cancelPrefs();
            }
        }

        @Override
        public void itemStateChanged(final ItemEvent e) {
            if (e.getItemSelectable() == Preferences.this.prefs[Preferences.MOVEMENT_MODIFIERS_DISABLED]) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    for (int x = Preferences.GLOBAL_DISABLE_LOWER_LIMIT; x < Preferences.GLOBAL_DISABLE_UPPER_LIMIT; x++) {
                        Preferences.this.prefs[x].setEnabled(true);
                    }
                } else {
                    for (int x = Preferences.GLOBAL_DISABLE_LOWER_LIMIT; x < Preferences.GLOBAL_DISABLE_UPPER_LIMIT; x++) {
                        Preferences.this.prefs[x].setSelected(false);
                        Preferences.this.prefs[x].setEnabled(false);
                    }
                }
            }
        }

        @Override
        public void windowActivated(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowClosed(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowClosing(final WindowEvent e) {
            Preferences.this.hidePrefs();
        }

        @Override
        public void windowDeactivated(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowDeiconified(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowIconified(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowOpened(final WindowEvent e) {
            // Do nothing
        }
    }
}