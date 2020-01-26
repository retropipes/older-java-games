/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.editor.rulesets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.puttysoftware.brainmaze.Application;
import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.generic.MazeObject;
import com.puttysoftware.brainmaze.generic.MazeObjectList;
import com.puttysoftware.brainmaze.resourcemanagers.GraphicsManager;
import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.picturepicker.PicturePicker;

public class RuleSetPicker {
    // Declarations
    private JFrame outputFrame;
    private PicturePicker picker;
    private final String[] names;
    private final MazeObject[] objects;
    private final BufferedImageIcon[] editorAppearances;
    private final RuleSetEditor rsEditor;

    public RuleSetPicker() {
        final MazeObjectList objectList = BrainMaze.getApplication()
                .getObjects();
        this.names = objectList.getAllNames();
        this.objects = objectList.getAllObjects();
        this.editorAppearances = objectList.getAllEditorAppearances();
        this.rsEditor = new RuleSetEditor();
        this.setUpGUI();
    }

    void createObjectRuleSet() {
        final int index = this.picker.getPicked();
        final MazeObject object = this.objects[index];
        object.giveRuleSet();
        CommonDialogs.showTitledDialog("Rule Set Created.", "Rule Set Picker");
    }

    void destroyObjectRuleSet() {
        final int index = this.picker.getPicked();
        final MazeObject object = this.objects[index];
        object.takeRuleSet();
        CommonDialogs
                .showTitledDialog("Rule Set Destroyed.", "Rule Set Picker");
    }

    void editObjectRuleSet() {
        final int index = this.picker.getPicked();
        final MazeObject object = this.objects[index];
        if (object.hasRuleSet()) {
            this.rsEditor.setRuleSet(object.getRuleSet());
            this.rsEditor.showRuleSetEditor();
        } else {
            CommonDialogs.showTitledDialog(
                    "This object does not have a rule set to edit!",
                    "Rule Set Picker");
        }
    }

    public void editRuleSets() {
        final Application app = BrainMaze.getApplication();
        app.getEditor().hideOutput();
        this.showOutput();
    }

    public void showOutput() {
        this.outputFrame.setVisible(true);
    }

    public void hideOutput() {
        if (this.outputFrame != null) {
            this.outputFrame.setVisible(false);
        }
    }

    void exitRuleSetEditor() {
        final Application app = BrainMaze.getApplication();
        this.hideOutput();
        app.getEditor().showOutput();
    }

    private void setUpGUI() {
        final EventHandler handler = new EventHandler();
        final Application app = BrainMaze.getApplication();
        this.outputFrame = new JFrame("Rule Set Picker");
        final Image iconlogo = app.getIconLogo();
        this.outputFrame.setIconImage(iconlogo);
        final Container outputPane = new Container();
        final Container borderPane = new Container();
        final JButton create = new JButton("Create");
        final JButton destroy = new JButton("Destroy");
        final JButton edit = new JButton("Edit");
        final JButton load = new JButton("Load");
        final JButton save = new JButton("Save");
        borderPane.setLayout(new BorderLayout());
        this.outputFrame.setContentPane(borderPane);
        this.outputFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        borderPane.add(outputPane, BorderLayout.SOUTH);
        outputPane.setLayout(new FlowLayout());
        outputPane.add(create);
        outputPane.add(destroy);
        outputPane.add(edit);
        outputPane.add(load);
        outputPane.add(save);
        this.outputFrame.addWindowListener(handler);
        create.addActionListener(handler);
        destroy.addActionListener(handler);
        edit.addActionListener(handler);
        load.addActionListener(handler);
        save.addActionListener(handler);
        this.picker = new PicturePicker(this.editorAppearances, this.names,
                new Color(223, 223, 223));
        this.picker.changePickerColor(new Color(223, 223, 223));
        this.picker.updatePickerLayout(GraphicsManager.MAX_WINDOW_SIZE);
        borderPane.add(this.picker.getPicker(), BorderLayout.CENTER);
        this.outputFrame.setResizable(false);
        this.outputFrame.pack();
    }

    private class EventHandler implements ActionListener, WindowListener {
        EventHandler() {
            // Do nothing
        }

        // handle buttons
        @Override
        public void actionPerformed(final ActionEvent e) {
            final String cmd = e.getActionCommand();
            final RuleSetPicker ge = RuleSetPicker.this;
            if (cmd.equals("Create")) {
                ge.createObjectRuleSet();
            } else if (cmd.equals("Destroy")) {
                ge.destroyObjectRuleSet();
            } else if (cmd.equals("Edit")) {
                ge.editObjectRuleSet();
            } else if (cmd.equals("Load")) {
                RuleSetManager.importRuleSet();
            } else if (cmd.equals("Save")) {
                RuleSetManager.exportRuleSet();
            }
        }

        // Handle windows
        @Override
        public void windowActivated(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowClosed(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowClosing(final WindowEvent we) {
            RuleSetPicker.this.exitRuleSetEditor();
        }

        @Override
        public void windowDeactivated(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowDeiconified(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowIconified(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowOpened(final WindowEvent we) {
            // Do nothing
        }
    }
}
