/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.editor.rulesets;

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

import com.puttysoftware.mastermaze.MasterMaze;

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
        this.setUpGUI();
    }

    // Methods
    public void setRuleSet(final RuleSet gen) {
        this.generator = gen;
        this.loadRuleSetEditor();
    }

    public void showRuleSetEditor() {
        MasterMaze.getApplication().getRuleSetPicker().hideOutput();
        this.editFrame.setVisible(true);
    }

    void hideRuleSetEditor() {
        this.editFrame.setVisible(false);
        MasterMaze.getApplication().getRuleSetPicker().showOutput();
    }

    void saveRuleSetEditor() {
        try {
            final int min = Integer.parseInt(this.minQuantity.getText());
            final int max = Integer.parseInt(this.maxQuantity.getText());
            final int gen = Integer.parseInt(this.generateQuantity.getText());
            final boolean req = this.required.isSelected();
            if (this.percentage.isSelected()) {
                this.generator.setQuantityRelative(min, max);
            } else {
                this.generator.setQuantityAbsolute(min, max);
            }
            this.generator.setGenerateQuantity(gen);
            this.generator.setRequired(req);
        } catch (final NumberFormatException nf) {
            // Ignore
        } catch (final IllegalArgumentException ia) {
            // Ignore
        }
    }

    private void loadRuleSetEditor() {
        this.required.setSelected(this.generator.isRequired());
        this.percentage.setSelected(this.generator.getPercentageFlag());
        this.minQuantity.setText(
                Integer.toString(this.generator.getMinimumRequiredQuantity()));
        this.maxQuantity.setText(
                Integer.toString(this.generator.getMaximumRequiredQuantity()));
        this.generateQuantity.setText(
                Integer.toString(this.generator.getGenerateQuantity()));
    }

    private void setUpGUI() {
        final EventHandler handler = new EventHandler();
        this.editFrame = new JFrame("Rule Set Editor");
        final Image iconlogo = MasterMaze.getApplication().getIconLogo();
        this.editFrame.setIconImage(iconlogo);
        final Container mainEditPane = new Container();
        final Container contentPane = new Container();
        final Container buttonPane = new Container();
        final JButton editOK = new JButton("OK");
        editOK.setDefaultCapable(true);
        this.editFrame.getRootPane().setDefaultButton(editOK);
        final JButton editCancel = new JButton("Cancel");
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
                final RuleSetEditor ge = RuleSetEditor.this;
                final String cmd = e.getActionCommand();
                if (cmd.equals("OK")) {
                    ge.saveRuleSetEditor();
                    ge.hideRuleSetEditor();
                } else if (cmd.equals("Cancel")) {
                    ge.hideRuleSetEditor();
                }
            } catch (final Exception ex) {
                MasterMaze.getErrorLogger().logError(ex);
            }
        }

        // handle window
        @Override
        public void windowOpened(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowClosing(final WindowEvent e) {
            final RuleSetEditor pm = RuleSetEditor.this;
            pm.hideRuleSetEditor();
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
