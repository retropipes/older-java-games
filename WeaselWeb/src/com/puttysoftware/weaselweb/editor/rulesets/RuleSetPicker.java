/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.editor.rulesets;

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

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.picturepicker.PicturePicker;
import com.puttysoftware.weaselweb.Application;
import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.maze.generic.MazeObject;
import com.puttysoftware.weaselweb.maze.generic.MazeObjectList;
import com.puttysoftware.weaselweb.resourcemanagers.ImageManager;
import com.puttysoftware.weaselweb.resourcemanagers.LogoManager;

public class RuleSetPicker {
    // Declarations
    private JFrame outputFrame;
    private Container outputPane, borderPane;
    private final EventHandler handler;
    private PicturePicker picker;
    private final MazeObjectList objectList;
    private final String[] names;
    private final MazeObject[] objects;
    private final BufferedImageIcon[] editorAppearances;
    private int index;
    private JButton create, destroy, edit, load, save;
    private final RuleSetEditor rsEditor;

    public RuleSetPicker() {
        this.handler = new EventHandler();
        this.objectList = WeaselWeb.getApplication().getObjects();
        this.names = this.objectList.getAllNames();
        this.objects = this.objectList.getAllObjects();
        this.editorAppearances = this.objectList.getAllEditorAppearances();
        this.rsEditor = new RuleSetEditor();
        this.setUpGUI();
    }

    void createObjectRuleSet() {
        this.index = this.picker.getPicked();
        final MazeObject object = this.objects[this.index];
        object.giveRuleSet();
        CommonDialogs.showTitledDialog("Rule Set Created.", "Rule Set Picker");
    }

    void destroyObjectRuleSet() {
        this.index = this.picker.getPicked();
        final MazeObject object = this.objects[this.index];
        object.takeRuleSet();
        CommonDialogs.showTitledDialog("Rule Set Destroyed.",
                "Rule Set Picker");
    }

    void editObjectRuleSet() {
        this.index = this.picker.getPicked();
        final MazeObject object = this.objects[this.index];
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
        final Application app = WeaselWeb.getApplication();
        app.getEditor().hideOutput();
        this.showOutput();
    }

    public boolean isOutputVisible() {
        if (this.outputFrame == null) {
            return false;
        } else {
            return this.outputFrame.isVisible();
        }
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
        final Application app = WeaselWeb.getApplication();
        this.hideOutput();
        app.getEditor().showOutput();
    }

    private void setUpGUI() {
        this.outputFrame = new JFrame("Rule Set Picker");
        final Image iconlogo = LogoManager.getIconLogo();
        this.outputFrame.setIconImage(iconlogo);
        this.outputPane = new Container();
        this.borderPane = new Container();
        this.create = new JButton("Create");
        this.destroy = new JButton("Destroy");
        this.edit = new JButton("Edit");
        this.load = new JButton("Load");
        this.save = new JButton("Save");
        this.borderPane.setLayout(new BorderLayout());
        this.outputFrame.setContentPane(this.borderPane);
        this.outputFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.borderPane.add(this.outputPane, BorderLayout.SOUTH);
        this.outputPane.setLayout(new FlowLayout());
        this.outputPane.add(this.create);
        this.outputPane.add(this.destroy);
        this.outputPane.add(this.edit);
        this.outputPane.add(this.load);
        this.outputPane.add(this.save);
        this.outputFrame.addWindowListener(this.handler);
        this.create.addActionListener(this.handler);
        this.destroy.addActionListener(this.handler);
        this.edit.addActionListener(this.handler);
        this.load.addActionListener(this.handler);
        this.save.addActionListener(this.handler);
        this.picker = new PicturePicker(this.editorAppearances, this.names,
                new Color(223, 223, 223));
        this.picker.changePickerColor(new Color(223, 223, 223));
        this.picker.updatePickerLayout(ImageManager.MAX_WINDOW_SIZE);
        this.borderPane.add(this.picker.getPicker(), BorderLayout.CENTER);
        this.outputFrame.setResizable(false);
        this.outputFrame.pack();
    }

    private class EventHandler implements ActionListener, WindowListener {
        public EventHandler() {
            // TODO Auto-generated constructor stub
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
                RuleSetManager.loadRuleSet();
            } else if (cmd.equals("Save")) {
                RuleSetManager.saveRuleSet();
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
