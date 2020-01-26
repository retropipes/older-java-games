/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.world.World;

public class LevelPreferencesManager {
    // Fields
    private JFrame prefFrame;
    private Container mainPrefPane, contentPane, buttonPane;
    private JButton prefsOK, prefsCancel;
    private JCheckBox horizontalWrap;
    private JCheckBox verticalWrap;
    private JCheckBox thirdDimensionalWrap;
    private JComboBox<String> poisonPowerChoices;
    private String[] poisonPowerChoiceArray;
    private EventHandler handler;

    // Constructors
    public LevelPreferencesManager() {
        this.setUpGUI();
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
        this.loadPrefs();
        Worldz.getApplication().getEditor().disableOutput();
        this.prefFrame.setVisible(true);
    }

    public void hidePrefs() {
        this.prefFrame.setVisible(false);
        Worldz.getApplication().getEditor().enableOutput();
        Worldz.getApplication().getWorldManager().setDirty(true);
        Worldz.getApplication().getEditor().redrawEditor();
    }

    void setPrefs() {
        final World m = Worldz.getApplication().getWorldManager().getWorld();
        if (this.horizontalWrap.isSelected()) {
            m.enableHorizontalWraparound();
        } else {
            m.disableHorizontalWraparound();
        }
        if (this.verticalWrap.isSelected()) {
            m.enableVerticalWraparound();
        } else {
            m.disableVerticalWraparound();
        }
        if (this.thirdDimensionalWrap.isSelected()) {
            m.enable3rdDimensionWraparound();
        } else {
            m.disable3rdDimensionWraparound();
        }
        m.setPoisonPower(this.poisonPowerChoices.getSelectedIndex());
    }

    private void loadPrefs() {
        final World m = Worldz.getApplication().getWorldManager().getWorld();
        this.horizontalWrap.setSelected(m.isHorizontalWraparoundEnabled());
        this.verticalWrap.setSelected(m.isVerticalWraparoundEnabled());
        this.thirdDimensionalWrap
                .setSelected(m.is3rdDimensionWraparoundEnabled());
        this.poisonPowerChoices.setSelectedIndex(m.getPoisonPower());
    }

    private void setUpGUI() {
        this.handler = new EventHandler();
        this.prefFrame = new JFrame("Level Preferences");
        final Image iconlogo = Worldz.getApplication().getIconLogo();
        this.prefFrame.setIconImage(iconlogo);
        this.mainPrefPane = new Container();
        this.contentPane = new Container();
        this.buttonPane = new Container();
        this.prefsOK = new JButton("OK");
        this.prefsOK.setDefaultCapable(true);
        this.prefFrame.getRootPane().setDefaultButton(this.prefsOK);
        this.prefsCancel = new JButton("Cancel");
        this.prefsCancel.setDefaultCapable(false);
        this.horizontalWrap = new JCheckBox("Enable horizontal wraparound",
                false);
        this.verticalWrap = new JCheckBox("Enable vertical wraparound", false);
        this.thirdDimensionalWrap = new JCheckBox(
                "Enable 3rd dimension wraparound", false);
        this.poisonPowerChoiceArray = new String[World.getMaxPoisonPower() + 1];
        for (int x = 0; x < this.poisonPowerChoiceArray.length; x++) {
            if (x == 0) {
                this.poisonPowerChoiceArray[x] = "None";
            } else if (x == 1) {
                this.poisonPowerChoiceArray[x] = "1 health / 1 step";
            } else {
                this.poisonPowerChoiceArray[x] = "1 health / "
                        + Integer.toString(x) + " steps";
            }
        }
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(
                this.poisonPowerChoiceArray);
        this.poisonPowerChoices = new JComboBox<>();
        this.poisonPowerChoices.setModel(model);
        this.prefFrame.setContentPane(this.mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.prefFrame.addWindowListener(this.handler);
        this.mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        this.contentPane.setLayout(new GridLayout(5, 1));
        this.contentPane.add(this.horizontalWrap);
        this.contentPane.add(this.verticalWrap);
        this.contentPane.add(this.thirdDimensionalWrap);
        this.contentPane.add(new JLabel("Poison Power"));
        this.contentPane.add(this.poisonPowerChoices);
        this.buttonPane.setLayout(new FlowLayout());
        this.buttonPane.add(this.prefsOK);
        this.buttonPane.add(this.prefsCancel);
        this.mainPrefPane.add(this.contentPane, BorderLayout.CENTER);
        this.mainPrefPane.add(this.buttonPane, BorderLayout.SOUTH);
        this.prefsOK.addActionListener(this.handler);
        this.prefsCancel.addActionListener(this.handler);
        this.prefFrame.pack();
    }

    private class EventHandler implements ActionListener, WindowListener {
        public EventHandler() {
            // TODO Auto-generated constructor stub
        }

        // Handle buttons
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                final LevelPreferencesManager lpm = LevelPreferencesManager.this;
                final String cmd = e.getActionCommand();
                if (cmd.equals("OK")) {
                    lpm.setPrefs();
                    lpm.hidePrefs();
                } else if (cmd.equals("Cancel")) {
                    lpm.hidePrefs();
                }
            } catch (final Exception ex) {
                Worldz.getDebug().debug(ex);
            }
        }

        // handle window
        @Override
        public void windowOpened(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowClosing(final WindowEvent e) {
            final LevelPreferencesManager pm = LevelPreferencesManager.this;
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
