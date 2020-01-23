/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.editor.rulesets;

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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.puttysoftware.mazerunner2.MazeRunnerII;

class RuleSetEditor {
    // Fields
    private RuleSet generator;
    private JFrame editFrame;
    private JCheckBox required;
    private JCheckBox percentage;
    private JTextField minQuantity;
    private JTextField maxQuantity;
    private JTextField generateQuantity;

    // Constructors
    public RuleSetEditor() {
        setUpGUI();
    }

    // Methods
    public void setRuleSet(RuleSet gen) {
        this.generator = gen;
        loadRuleSetEditor();
    }

    public void showRuleSetEditor() {
        MazeRunnerII.getApplication().getRuleSetPicker().hideOutput();
        this.editFrame.setVisible(true);
    }

    void hideRuleSetEditor() {
        this.editFrame.setVisible(false);
        MazeRunnerII.getApplication().getRuleSetPicker().showOutput();
    }

    void saveRuleSetEditor() {
        try {
            int min = Integer.parseInt(this.minQuantity.getText());
            int max = Integer.parseInt(this.maxQuantity.getText());
            int gen = Integer.parseInt(this.generateQuantity.getText());
            boolean req = this.required.isSelected();
            if (this.percentage.isSelected()) {
                this.generator.setQuantityRelative(min, max);
            } else {
                this.generator.setQuantityAbsolute(min, max);
            }
            this.generator.setGenerateQuantity(gen);
            this.generator.setRequired(req);
        } catch (NumberFormatException nf) {
            // Ignore
        } catch (IllegalArgumentException ia) {
            // Ignore
        }
    }

    private void loadRuleSetEditor() {
        this.required.setSelected(this.generator.isRequired());
        this.percentage.setSelected(this.generator.getPercentageFlag());
        this.minQuantity.setText(Integer.toString(this.generator
                .getMinimumRequiredQuantity()));
        this.maxQuantity.setText(Integer.toString(this.generator
                .getMaximumRequiredQuantity()));
        this.generateQuantity.setText(Integer.toString(this.generator
                .getGenerateQuantity()));
    }

    private void setUpGUI() {
        EventHandler handler = new EventHandler();
        this.editFrame = new JFrame("Rule Set Editor");
        final Image iconlogo = MazeRunnerII.getApplication().getIconLogo();
        this.editFrame.setIconImage(iconlogo);
        Container mainEditPane = new Container();
        Container contentPane = new Container();
        Container buttonPane = new Container();
        JButton editOK = new JButton("OK");
        editOK.setDefaultCapable(true);
        this.editFrame.getRootPane().setDefaultButton(editOK);
        JButton editCancel = new JButton("Cancel");
        editCancel.setDefaultCapable(false);
        this.required = new JCheckBox("Is Object Required?", true);
        this.percentage = new JCheckBox("Are Quantities Percents?", false);
        this.minQuantity = new JTextField("");
        this.maxQuantity = new JTextField("");
        this.generateQuantity = new JTextField("");
        this.editFrame.setContentPane(mainEditPane);
        this.editFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.editFrame.addWindowListener(handler);
        mainEditPane.setLayout(new BorderLayout());
        this.editFrame.setResizable(false);
        contentPane.setLayout(new GridLayout(8, 1));
        contentPane.add(this.required);
        contentPane.add(this.percentage);
        contentPane.add(new JLabel("Minimum Quantity"));
        contentPane.add(this.minQuantity);
        contentPane.add(new JLabel("Maximum Quantity"));
        contentPane.add(this.maxQuantity);
        contentPane.add(new JLabel("Generate Percentage"));
        contentPane.add(this.generateQuantity);
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(editOK);
        buttonPane.add(editCancel);
        mainEditPane.add(contentPane, BorderLayout.CENTER);
        mainEditPane.add(buttonPane, BorderLayout.SOUTH);
        editOK.addActionListener(handler);
        editCancel.addActionListener(handler);
        this.editFrame.pack();
    }

    private class EventHandler implements ActionListener, WindowListener {
        EventHandler() {
            // Do nothing
        }

        // Handle buttons
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                RuleSetEditor ge = RuleSetEditor.this;
                final String cmd = e.getActionCommand();
                if (cmd.equals("OK")) {
                    ge.saveRuleSetEditor();
                    ge.hideRuleSetEditor();
                } else if (cmd.equals("Cancel")) {
                    ge.hideRuleSetEditor();
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
            RuleSetEditor pm = RuleSetEditor.this;
            pm.hideRuleSetEditor();
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
