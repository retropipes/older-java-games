/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.editor.rulesets;

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

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.resourcemanagers.LogoManager;

class RuleSetEditor {
    // Fields
    private RuleSet generator;
    private JFrame editFrame;
    private Container mainEditPane, contentPane, buttonPane;
    private JButton editOK, editCancel;
    private JCheckBox required;
    private JCheckBox percentage;
    private JTextField minQuantity;
    private JTextField maxQuantity;
    private JTextField generateQuantity;
    private EventHandler handler;

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
        WidgetWarren.getApplication().getRuleSetPicker().hideOutput();
        this.editFrame.setVisible(true);
    }

    void hideRuleSetEditor() {
        this.editFrame.setVisible(false);
        WidgetWarren.getApplication().getRuleSetPicker().showOutput();
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
        this.minQuantity.setText(Integer.toString(this.generator
                .getMinimumRequiredQuantity()));
        this.maxQuantity.setText(Integer.toString(this.generator
                .getMaximumRequiredQuantity()));
        this.generateQuantity.setText(Integer.toString(this.generator
                .getGenerateQuantity()));
    }

    private void setUpGUI() {
        this.handler = new EventHandler();
        this.editFrame = new JFrame("Rule Set Editor");
        final Image iconlogo = LogoManager.getLogo();
        this.editFrame.setIconImage(iconlogo);
        this.mainEditPane = new Container();
        this.contentPane = new Container();
        this.buttonPane = new Container();
        this.editOK = new JButton("OK");
        this.editOK.setDefaultCapable(true);
        this.editFrame.getRootPane().setDefaultButton(this.editOK);
        this.editCancel = new JButton("Cancel");
        this.editCancel.setDefaultCapable(false);
        this.required = new JCheckBox("Is Object Required?", true);
        this.percentage = new JCheckBox("Are Quantities Percents?", false);
        this.minQuantity = new JTextField("");
        this.maxQuantity = new JTextField("");
        this.generateQuantity = new JTextField("");
        this.editFrame.setContentPane(this.mainEditPane);
        this.editFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.editFrame.addWindowListener(this.handler);
        this.mainEditPane.setLayout(new BorderLayout());
        this.editFrame.setResizable(false);
        this.contentPane.setLayout(new GridLayout(8, 1));
        this.contentPane.add(this.required);
        this.contentPane.add(this.percentage);
        this.contentPane.add(new JLabel("Minimum Quantity"));
        this.contentPane.add(this.minQuantity);
        this.contentPane.add(new JLabel("Maximum Quantity"));
        this.contentPane.add(this.maxQuantity);
        this.contentPane.add(new JLabel("Generate Percentage"));
        this.contentPane.add(this.generateQuantity);
        this.buttonPane.setLayout(new FlowLayout());
        this.buttonPane.add(this.editOK);
        this.buttonPane.add(this.editCancel);
        this.mainEditPane.add(this.contentPane, BorderLayout.CENTER);
        this.mainEditPane.add(this.buttonPane, BorderLayout.SOUTH);
        this.editOK.addActionListener(this.handler);
        this.editCancel.addActionListener(this.handler);
        this.editFrame.pack();
    }

    private class EventHandler implements ActionListener, WindowListener {
        public EventHandler() {
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
