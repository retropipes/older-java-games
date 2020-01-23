/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.editor;

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

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.Maze;
import com.puttysoftware.mazerunner2.maze.MazeConstants;

public class LevelPreferencesManager {
    // Fields
    private JFrame prefFrame;
    private JCheckBox horizontalWrap;
    private JCheckBox verticalWrap;
    private JCheckBox thirdDimensionalWrap;
    private JTextField levelTitle;
    private JTextArea levelStartMessage;
    private JTextArea levelEndMessage;
    private JCheckBox useOffset;
    private JTextField nextLevel;
    private JCheckBox useAlternateOffset;
    private JTextField alternateNextLevel;
    private JTextField exploreRadius;
    private JCheckBox vmExplore;
    private JCheckBox vmLOS;
    private JComboBox<String> poisonPowerChoices;
    private String[] poisonPowerChoiceArray;
    private JTextField timeLimit;
    private JCheckBox autoFinishEnabled;
    private JTextField autoFinishThreshold;
    private JCheckBox alternateAutoFinishEnabled;
    private JTextField alternateAutoFinishThreshold;
    private JTextField illumination;
    private JTextField finishMoveSpeed;

    // Constructors
    public LevelPreferencesManager() {
        this.setUpGUI();
    }

    // Methods
    public void showPrefs() {
        this.loadPrefs();
        MazeRunnerII.getApplication().getEditor().disableOutput();
        this.prefFrame.setVisible(true);
    }

    public void hidePrefs() {
        this.prefFrame.setVisible(false);
        MazeRunnerII.getApplication().getEditor().enableOutput();
        MazeRunnerII.getApplication().getMazeManager().setDirty(true);
        MazeRunnerII.getApplication().getEditor().redrawEditor();
    }

    void setPrefs() {
        Maze m = MazeRunnerII.getApplication().getMazeManager().getMaze();
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
        m.setUseOffset(this.useOffset.isSelected());
        if (this.useOffset.isSelected()) {
            m.setNextLevelOffset(Integer.parseInt(this.nextLevel.getText()));
        } else {
            m.setNextLevel(Integer.parseInt(this.nextLevel.getText()) - 1);
        }
        m.setUseAlternateOffset(this.useAlternateOffset.isSelected());
        if (this.useAlternateOffset.isSelected()) {
            m.setAlternateNextLevelOffset(Integer
                    .parseInt(this.alternateNextLevel.getText()));
        } else {
            m.setAlternateNextLevel(Integer.parseInt(this.alternateNextLevel
                    .getText()) - 1);
        }
        int newER = m.getExploreRadius();
        try {
            newER = Integer.parseInt(this.exploreRadius.getText());
            if (newER < 1 || newER > 16) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException nfe) {
            newER = m.getExploreRadius();
        }
        m.setExploreRadius(newER);
        int newVM = MazeConstants.VISION_MODE_NONE;
        if (this.vmExplore.isSelected()) {
            newVM = newVM | MazeConstants.VISION_MODE_EXPLORE;
        }
        if (this.vmLOS.isSelected()) {
            newVM = newVM | MazeConstants.VISION_MODE_LOS;
        }
        m.setVisionMode(newVM);
        m.setPoisonPower(this.poisonPowerChoices.getSelectedIndex());
        int tv = Integer.parseInt(this.timeLimit.getText());
        if (tv > 0) {
            m.activateTimer(tv);
        } else {
            m.deactivateTimer();
        }
        m.setAutoFinishEnabled(this.autoFinishEnabled.isSelected());
        m.setAutoFinishThreshold(Integer.parseInt(this.autoFinishThreshold
                .getText()));
        m.setAlternateAutoFinishEnabled(this.alternateAutoFinishEnabled
                .isSelected());
        m.setAlternateAutoFinishThreshold(Integer
                .parseInt(this.alternateAutoFinishThreshold.getText()));
        int newFMS = m.getFinishMoveSpeed();
        try {
            newFMS = Integer.parseInt(this.finishMoveSpeed.getText());
            if (newFMS < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException nfe) {
            newFMS = m.getFinishMoveSpeed();
        }
        m.setFinishMoveSpeed(newFMS);
        int newVR = m.getVisionRadius();
        try {
            newVR = Integer.parseInt(this.illumination.getText());
            if (newVR < 1 || newVR > 16) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException nfe) {
            newVR = m.getVisionRadius();
        }
        m.setVisionRadius(newVR);
    }

    private void loadPrefs() {
        Maze m = MazeRunnerII.getApplication().getMazeManager().getMaze();
        this.horizontalWrap.setSelected(m.isHorizontalWraparoundEnabled());
        this.verticalWrap.setSelected(m.isVerticalWraparoundEnabled());
        this.thirdDimensionalWrap.setSelected(m
                .is3rdDimensionWraparoundEnabled());
        this.levelTitle.setText(m.getLevelTitle());
        this.levelStartMessage.setText(m.getLevelStartMessage());
        this.levelEndMessage.setText(m.getLevelEndMessage());
        this.useOffset.setSelected(m.useOffset());
        if (m.useOffset()) {
            this.nextLevel.setText(Integer.toString(m.getNextLevel()));
        } else {
            this.nextLevel.setText(Integer.toString(m.getNextLevel() + 1));
        }
        this.useAlternateOffset.setSelected(m.useAlternateOffset());
        if (m.useAlternateOffset()) {
            this.alternateNextLevel.setText(Integer.toString(m
                    .getAlternateNextLevel()));
        } else {
            this.alternateNextLevel.setText(Integer.toString(m
                    .getAlternateNextLevel() + 1));
        }
        this.exploreRadius.setText(Integer.toString(m.getExploreRadius()));
        int vm = m.getVisionMode();
        if ((vm | MazeConstants.VISION_MODE_EXPLORE) == vm) {
            this.vmExplore.setSelected(true);
        } else {
            this.vmExplore.setSelected(false);
        }
        if ((vm | MazeConstants.VISION_MODE_LOS) == vm) {
            this.vmLOS.setSelected(true);
        } else {
            this.vmLOS.setSelected(false);
        }
        this.poisonPowerChoices.setSelectedIndex(m.getPoisonPower());
        if (m.isTimerActive()) {
            this.timeLimit.setText(Integer.toString(m.getTimerValue()));
        } else {
            this.timeLimit.setText("0");
        }
        this.autoFinishEnabled.setSelected(m.getAutoFinishEnabled());
        this.autoFinishThreshold.setText(Integer.toString(m
                .getAutoFinishThreshold()));
        this.alternateAutoFinishEnabled.setSelected(m
                .getAlternateAutoFinishEnabled());
        this.alternateAutoFinishThreshold.setText(Integer.toString(m
                .getAlternateAutoFinishThreshold()));
        this.illumination.setText(Integer.toString(m.getVisionRadius()));
        this.finishMoveSpeed.setText(Integer.toString(m.getFinishMoveSpeed()));
    }

    private void setUpGUI() {
        EventHandler handler = new EventHandler();
        this.prefFrame = new JFrame("Level Preferences");
        final Image iconlogo = MazeRunnerII.getApplication().getIconLogo();
        this.prefFrame.setIconImage(iconlogo);
        Container mainPrefPane = new Container();
        Container contentPane = new Container();
        Container buttonPane = new Container();
        JButton prefsOK = new JButton("OK");
        prefsOK.setDefaultCapable(true);
        this.prefFrame.getRootPane().setDefaultButton(prefsOK);
        JButton prefsCancel = new JButton("Cancel");
        prefsCancel.setDefaultCapable(false);
        this.horizontalWrap = new JCheckBox("Enable horizontal wraparound",
                false);
        this.verticalWrap = new JCheckBox("Enable vertical wraparound", false);
        this.thirdDimensionalWrap = new JCheckBox(
                "Enable 3rd dimension wraparound", false);
        this.levelTitle = new JTextField("");
        this.levelStartMessage = new JTextArea("");
        this.levelEndMessage = new JTextArea("");
        this.useOffset = new JCheckBox("Next Level Is Relative");
        this.nextLevel = new JTextField("");
        this.useAlternateOffset = new JCheckBox(
                "Alternate Next Level Is Relative");
        this.alternateNextLevel = new JTextField("");
        this.exploreRadius = new JTextField("");
        this.vmExplore = new JCheckBox("Enable exploring vision mode");
        this.vmLOS = new JCheckBox("Enable line-of-sight vision mode");
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
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(
                this.poisonPowerChoiceArray);
        this.poisonPowerChoices = new JComboBox<>();
        this.poisonPowerChoices.setModel(model);
        this.timeLimit = new JTextField("");
        this.autoFinishEnabled = new JCheckBox("Enable Auto-Finish");
        this.autoFinishThreshold = new JTextField("");
        this.alternateAutoFinishEnabled = new JCheckBox(
                "Enable Alternate Auto-Finish");
        this.alternateAutoFinishThreshold = new JTextField("");
        this.illumination = new JTextField("");
        this.finishMoveSpeed = new JTextField("");
        this.prefFrame.setContentPane(mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.prefFrame.addWindowListener(handler);
        mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        contentPane.setLayout(new GridLayout(33, 1));
        contentPane.add(this.horizontalWrap);
        contentPane.add(this.verticalWrap);
        contentPane.add(this.thirdDimensionalWrap);
        contentPane.add(new JLabel("Level Title"));
        contentPane.add(this.levelTitle);
        contentPane.add(new JLabel("Level Start Message"));
        contentPane.add(this.levelStartMessage);
        contentPane.add(new JLabel("Level End Message"));
        contentPane.add(this.levelEndMessage);
        contentPane.add(this.useOffset);
        contentPane.add(new JLabel("Next Level Number"));
        contentPane.add(this.nextLevel);
        contentPane.add(this.useAlternateOffset);
        contentPane.add(new JLabel("Alternate Next Level Number"));
        contentPane.add(this.alternateNextLevel);
        contentPane.add(new JLabel("Exploring Mode Vision Radius (1-16)"));
        contentPane.add(this.exploreRadius);
        contentPane.add(this.vmExplore);
        contentPane.add(this.vmLOS);
        contentPane.add(new JLabel("Poison Power"));
        contentPane.add(this.poisonPowerChoices);
        contentPane.add(new JLabel("Time Limit (0 to disable)"));
        contentPane.add(this.timeLimit);
        contentPane.add(this.autoFinishEnabled);
        contentPane.add(new JLabel("Sun Stones Needed To Trigger Auto-Finish"));
        contentPane.add(this.autoFinishThreshold);
        contentPane.add(this.alternateAutoFinishEnabled);
        contentPane.add(new JLabel(
                "Moon Stones Needed To Trigger Alternate Auto-Finish"));
        contentPane.add(this.alternateAutoFinishThreshold);
        contentPane.add(new JLabel("Starting Illumination (1-16)"));
        contentPane.add(this.illumination);
        contentPane.add(new JLabel("Finish Move Speed (1-100)"));
        contentPane.add(this.finishMoveSpeed);
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(prefsOK);
        buttonPane.add(prefsCancel);
        mainPrefPane.add(contentPane, BorderLayout.CENTER);
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
                LevelPreferencesManager lpm = LevelPreferencesManager.this;
                final String cmd = e.getActionCommand();
                if (cmd.equals("OK")) {
                    lpm.setPrefs();
                    lpm.hidePrefs();
                } else if (cmd.equals("Cancel")) {
                    lpm.hidePrefs();
                }
            } catch (Exception ex) {
                MazeRunnerII.getErrorLogger().logError(ex);
            }
        }

        // handle window
        @Override
        public void windowOpened(WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowClosing(WindowEvent e) {
            LevelPreferencesManager pm = LevelPreferencesManager.this;
            pm.hidePrefs();
        }

        @Override
        public void windowClosed(WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowIconified(WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowActivated(WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            // Do nothing
        }
    }
}
