/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.editor;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.maze.Maze;

public class LevelPreferencesManager {
    // Fields
    private JFrame prefFrame;
    private Container mainPrefPane, contentPane, buttonPane;
    private JButton prefsOK, prefsCancel;
    private JCheckBox horizontalWrap;
    private JCheckBox verticalWrap;
    private JCheckBox thirdDimensionalWrap;
    private JTextField levelTitle;
    private JTextArea levelStartMessage;
    private JTextArea levelEndMessage;
    private JComboBox<String> poisonPowerChoices;
    private String[] poisonPowerChoiceArray;
    private JTextField timeLimit;
    private JCheckBox autoFinishEnabled;
    private JTextField autoFinishThreshold;
    private JCheckBox useOffset;
    private JTextField nextLevel;
    private JTextField illumination;
    private JTextField finishMoveSpeed;
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
        Main.getApplication().getEditor().disableOutput();
        this.prefFrame.setVisible(true);
    }

    public void hidePrefs() {
        this.prefFrame.setVisible(false);
        Main.getApplication().getEditor().enableOutput();
        Main.getApplication().getMazeManager().setDirty(true);
        Main.getApplication().getEditor().redrawEditor();
    }

    void setPrefs() {
        final Maze m = Main.getApplication().getMazeManager().getMaze();
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
        m.setLevelTitle(this.levelTitle.getText());
        m.setLevelStartMessage(this.levelStartMessage.getText());
        m.setLevelEndMessage(this.levelEndMessage.getText());
        m.setPoisonPower(this.poisonPowerChoices.getSelectedIndex());
        final int tv = Integer.parseInt(this.timeLimit.getText());
        if (tv > 0) {
            m.activateTimer(tv);
        } else {
            m.deactivateTimer();
        }
        m.setAutoFinishThresholdEnabled(this.autoFinishEnabled.isSelected());
        m.setAutoFinishThreshold(
                Integer.parseInt(this.autoFinishThreshold.getText()));
        m.setUseOffset(this.useOffset.isSelected());
        if (this.useOffset.isSelected()) {
            m.setNextLevelOffset(Integer.parseInt(this.nextLevel.getText()));
        } else {
            m.setNextLevel(Integer.parseInt(this.nextLevel.getText()) - 1);
        }
        int newVR = m.getVisionRadius();
        try {
            newVR = Integer.parseInt(this.illumination.getText());
            if (newVR < 1 || newVR > 6) {
                throw new NumberFormatException();
            }
        } catch (final NumberFormatException nfe) {
            newVR = m.getVisionRadius();
        }
        m.setVisionRadius(newVR);
        int newFMS = m.getFinishMoveSpeed();
        try {
            newFMS = Integer.parseInt(this.finishMoveSpeed.getText());
            if (newFMS < 1) {
                throw new NumberFormatException();
            }
        } catch (final NumberFormatException nfe) {
            newFMS = m.getFinishMoveSpeed();
        }
        m.setFinishMoveSpeed(newFMS);
    }

    private void loadPrefs() {
        final Maze m = Main.getApplication().getMazeManager().getMaze();
        this.horizontalWrap.setSelected(m.isHorizontalWraparoundEnabled());
        this.verticalWrap.setSelected(m.isVerticalWraparoundEnabled());
        this.thirdDimensionalWrap
                .setSelected(m.is3rdDimensionWraparoundEnabled());
        this.levelTitle.setText(m.getLevelTitle());
        this.levelStartMessage.setText(m.getLevelStartMessage());
        this.levelEndMessage.setText(m.getLevelEndMessage());
        this.poisonPowerChoices.setSelectedIndex(m.getPoisonPower());
        if (m.isTimerActive()) {
            this.timeLimit.setText(Integer.toString(m.getTimerValue()));
        } else {
            this.timeLimit.setText("0");
        }
        this.autoFinishEnabled.setSelected(m.getAutoFinishThresholdEnabled());
        this.autoFinishThreshold
                .setText(Integer.toString(m.getAutoFinishThreshold()));
        this.useOffset.setSelected(m.useOffset());
        if (m.useOffset()) {
            this.nextLevel.setText(Integer.toString(m.getNextLevel()));
        } else {
            this.nextLevel.setText(Integer.toString(m.getNextLevel() + 1));
        }
        this.illumination.setText(Integer.toString(m.getVisionRadius()));
        this.finishMoveSpeed.setText(Integer.toString(m.getFinishMoveSpeed()));
    }

    private void setUpGUI() {
        this.handler = new EventHandler();
        this.prefFrame = new JFrame("Level Preferences");
        final Image iconlogo = Main.getApplication().getIconLogo();
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
        this.levelTitle = new JTextField("");
        this.levelStartMessage = new JTextArea("");
        this.levelEndMessage = new JTextArea("");
        this.poisonPowerChoiceArray = new String[Maze.getMaxPoisonPower() + 1];
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
        this.timeLimit = new JTextField();
        this.autoFinishEnabled = new JCheckBox("Enable Auto-Finish");
        this.autoFinishThreshold = new JTextField();
        this.useOffset = new JCheckBox("Next Level Is Relative");
        this.nextLevel = new JTextField();
        this.illumination = new JTextField("");
        this.finishMoveSpeed = new JTextField("");
        this.prefFrame.setContentPane(this.mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.prefFrame.addWindowListener(this.handler);
        this.mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        this.contentPane.setLayout(new GridLayout(23, 1));
        this.contentPane.add(this.horizontalWrap);
        this.contentPane.add(this.verticalWrap);
        this.contentPane.add(this.thirdDimensionalWrap);
        this.contentPane.add(new JLabel("Level Title"));
        this.contentPane.add(this.levelTitle);
        this.contentPane.add(new JLabel("Level Start Message"));
        this.contentPane.add(this.levelStartMessage);
        this.contentPane.add(new JLabel("Level End Message"));
        this.contentPane.add(this.levelEndMessage);
        this.contentPane.add(new JLabel("Poison Power"));
        this.contentPane.add(this.poisonPowerChoices);
        this.contentPane.add(new JLabel("Time Limit (0 to disable)"));
        this.contentPane.add(this.timeLimit);
        this.contentPane.add(this.autoFinishEnabled);
        this.contentPane
                .add(new JLabel("Sun Stones Needed To Trigger Auto-Finish"));
        this.contentPane.add(this.autoFinishThreshold);
        this.contentPane.add(this.useOffset);
        this.contentPane.add(new JLabel("Next Level Number"));
        this.contentPane.add(this.nextLevel);
        this.contentPane.add(new JLabel("Starting Illumination (1-6)"));
        this.contentPane.add(this.illumination);
        this.contentPane.add(new JLabel("Finish Move Speed (1-100)"));
        this.contentPane.add(this.finishMoveSpeed);
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
                Main.getDebug().debug(ex);
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
