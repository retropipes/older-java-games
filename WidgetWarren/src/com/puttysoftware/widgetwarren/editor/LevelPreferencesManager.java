/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.maze.Maze;
import com.puttysoftware.widgetwarren.resourcemanagers.LogoManager;

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
    private JCheckBox useAlternateOffset;
    private JTextField alternateNextLevel;
    private JTextField illumination;
    private JTextField finishMoveSpeed;
    private JTextField exploreRadius;
    private JTextField alternateAutoFinishThreshold;
    private EventHandler handler;

    // Constructors
    public LevelPreferencesManager() {
        this.setUpGUI();
    }

    // Methods
    void viewModeChanged(final boolean newViewMode) {
        if (newViewMode) {
            this.setUpGUIView();
        } else {
            this.setUpGUIEdit();
        }
    }

    public void showPrefs() {
        this.loadPrefs();
        WidgetWarren.getApplication().getEditor().disableOutput();
        this.prefFrame.setVisible(true);
    }

    public void hidePrefs() {
        this.prefFrame.setVisible(false);
        WidgetWarren.getApplication().getEditor().enableOutput();
        WidgetWarren.getApplication().getEditor().redrawEditor();
    }

    void setPrefs() {
        final Maze m = WidgetWarren.getApplication().getMazeManager().getMaze();
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
        m.setAlternateAutoFinishThreshold(
                Integer.parseInt(this.alternateAutoFinishThreshold.getText()));
        m.setUseOffset(this.useOffset.isSelected());
        if (this.useOffset.isSelected()) {
            m.setNextLevelOffset(Integer.parseInt(this.nextLevel.getText()));
        } else {
            m.setNextLevel(Integer.parseInt(this.nextLevel.getText()) - 1);
        }
        m.setUseAlternateOffset(this.useAlternateOffset.isSelected());
        if (this.useAlternateOffset.isSelected()) {
            m.setAlternateNextLevelOffset(
                    Integer.parseInt(this.alternateNextLevel.getText()));
        } else {
            m.setAlternateNextLevel(
                    Integer.parseInt(this.alternateNextLevel.getText()) - 1);
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
        int newER = m.getExploreRadius();
        try {
            newER = Integer.parseInt(this.exploreRadius.getText());
            if (newER < 1 || newER > 6) {
                throw new NumberFormatException();
            }
        } catch (final NumberFormatException nfe) {
            newER = m.getExploreRadius();
        }
        m.setExploreRadius(newER);
        WidgetWarren.getApplication().getMazeManager().setDirty(true);
    }

    private void loadPrefs() {
        final Maze m = WidgetWarren.getApplication().getMazeManager().getMaze();
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
        this.alternateAutoFinishThreshold
                .setText(Integer.toString(m.getAlternateAutoFinishThreshold()));
        this.useOffset.setSelected(m.useOffset());
        if (m.useOffset()) {
            this.nextLevel.setText(Integer.toString(m.getNextLevel()));
        } else {
            this.nextLevel.setText(Integer.toString(m.getNextLevel() + 1));
        }
        this.useAlternateOffset.setSelected(m.useAlternateOffset());
        if (m.useAlternateOffset()) {
            this.alternateNextLevel
                    .setText(Integer.toString(m.getAlternateNextLevel()));
        } else {
            this.alternateNextLevel
                    .setText(Integer.toString(m.getAlternateNextLevel() + 1));
        }
        this.illumination.setText(Integer.toString(m.getVisionRadius()));
        this.finishMoveSpeed.setText(Integer.toString(m.getFinishMoveSpeed()));
        this.exploreRadius.setText(Integer.toString(m.getExploreRadius()));
    }

    private void setUpGUI() {
        this.handler = new EventHandler();
        this.prefFrame = new JFrame("Level Preferences");
        final Image iconlogo = LogoManager.getLogo();
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
        this.timeLimit = new JTextField("");
        this.autoFinishEnabled = new JCheckBox("Enable Auto-Finish");
        this.autoFinishThreshold = new JTextField("");
        this.alternateAutoFinishThreshold = new JTextField("");
        this.useOffset = new JCheckBox("Next Level Is Relative");
        this.nextLevel = new JTextField("");
        this.useAlternateOffset = new JCheckBox(
                "Alternate Next Level Is Relative");
        this.alternateNextLevel = new JTextField("");
        this.illumination = new JTextField("");
        this.finishMoveSpeed = new JTextField("");
        this.exploreRadius = new JTextField("");
        this.prefFrame.setContentPane(this.mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.prefFrame.addWindowListener(this.handler);
        this.mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        this.contentPane
                .setLayout(new BoxLayout(this.contentPane, BoxLayout.Y_AXIS));
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
        this.contentPane.add(new JLabel(
                "Moon Stones Needed To Trigger Alternate Auto-Finish"));
        this.contentPane.add(this.alternateAutoFinishThreshold);
        this.contentPane.add(this.useOffset);
        this.contentPane.add(new JLabel("Next Level Number"));
        this.contentPane.add(this.nextLevel);
        this.contentPane.add(this.useAlternateOffset);
        this.contentPane.add(new JLabel("Alternate Next Level Number"));
        this.contentPane.add(this.alternateNextLevel);
        this.contentPane.add(new JLabel("Starting Illumination (1-6)"));
        this.contentPane.add(this.illumination);
        this.contentPane.add(new JLabel("Finish Move Speed (1-100)"));
        this.contentPane.add(this.finishMoveSpeed);
        this.contentPane.add(new JLabel("Exploring Mode Vision Radius (1-6)"));
        this.contentPane.add(this.exploreRadius);
        this.buttonPane.setLayout(new FlowLayout());
        this.buttonPane.add(this.prefsOK);
        this.buttonPane.add(this.prefsCancel);
        this.mainPrefPane.add(this.contentPane, BorderLayout.CENTER);
        this.mainPrefPane.add(this.buttonPane, BorderLayout.SOUTH);
        this.prefsOK.addActionListener(this.handler);
        this.prefsCancel.addActionListener(this.handler);
        this.prefFrame.pack();
    }

    private void setUpGUIView() {
        this.horizontalWrap.setEnabled(false);
        this.verticalWrap.setEnabled(false);
        this.thirdDimensionalWrap.setEnabled(false);
        this.levelTitle.setEditable(false);
        this.levelStartMessage.setEditable(false);
        this.levelEndMessage.setEditable(false);
        this.poisonPowerChoices.setEditable(false);
        this.timeLimit.setEditable(false);
        this.autoFinishEnabled.setEnabled(false);
        this.autoFinishThreshold.setEditable(false);
        this.alternateAutoFinishThreshold.setEditable(false);
        this.useOffset.setEnabled(false);
        this.nextLevel.setEditable(false);
        this.useAlternateOffset.setEnabled(false);
        this.alternateNextLevel.setEditable(false);
        this.illumination.setEditable(false);
        this.finishMoveSpeed.setEditable(false);
        this.exploreRadius.setEditable(false);
        this.prefsOK.setEnabled(false);
    }

    private void setUpGUIEdit() {
        this.horizontalWrap.setEnabled(true);
        this.verticalWrap.setEnabled(true);
        this.thirdDimensionalWrap.setEnabled(true);
        this.levelTitle.setEditable(true);
        this.levelStartMessage.setEditable(true);
        this.levelEndMessage.setEditable(true);
        this.poisonPowerChoices.setEditable(true);
        this.timeLimit.setEditable(true);
        this.autoFinishEnabled.setEnabled(true);
        this.autoFinishThreshold.setEditable(true);
        this.alternateAutoFinishThreshold.setEditable(true);
        this.useOffset.setEnabled(true);
        this.nextLevel.setEditable(true);
        this.useAlternateOffset.setEnabled(true);
        this.alternateNextLevel.setEditable(true);
        this.illumination.setEditable(true);
        this.finishMoveSpeed.setEditable(true);
        this.exploreRadius.setEditable(true);
        this.prefsOK.setEnabled(true);
    }

    private class EventHandler implements ActionListener, WindowListener {
        public EventHandler() {
            // Do nothing
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
                WidgetWarren.getErrorLogger().logError(ex);
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
