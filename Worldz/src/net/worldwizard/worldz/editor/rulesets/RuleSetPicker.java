/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.editor.rulesets;

import java.awt.BorderLayout;
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

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.picturepicker.PicturePicker;
import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.generic.WorldObjectList;
import net.worldwizard.worldz.resourcemanagers.GraphicsManager;

public class RuleSetPicker {
    // Declarations
    private JFrame outputFrame;
    private Container outputPane, borderPane;
    private final EventHandler handler;
    private PicturePicker picker;
    private final WorldObjectList objectList;
    private final String[] names;
    private final WorldObject[] objects;
    private final BufferedImageIcon[] editorAppearances;
    private int index;
    private JButton create, destroy, edit, load, save;
    private final RuleSetEditor rsEditor;

    public RuleSetPicker() {
        this.handler = new EventHandler();
        this.objectList = Worldz.getApplication().getObjects();
        this.names = this.objectList.getAllNames();
        this.objects = this.objectList.getAllObjects();
        this.editorAppearances = this.objectList.getAllEditorAppearances();
        this.rsEditor = new RuleSetEditor();
        this.setUpGUI();
    }

    void createObjectRuleSet() {
        this.index = this.picker.getPicked();
        final WorldObject object = this.objects[this.index];
        object.giveRuleSet();
        Messager.showTitledDialog("Rule Set Created.", "Rule Set Picker");
    }

    void destroyObjectRuleSet() {
        this.index = this.picker.getPicked();
        final WorldObject object = this.objects[this.index];
        object.takeRuleSet();
        Messager.showTitledDialog("Rule Set Destroyed.", "Rule Set Picker");
    }

    void editObjectRuleSet() {
        this.index = this.picker.getPicked();
        final WorldObject object = this.objects[this.index];
        if (object.hasRuleSet()) {
            this.rsEditor.setRuleSet(object.getRuleSet());
            this.rsEditor.showRuleSetEditor();
        } else {
            Messager.showTitledDialog(
                    "This object does not have a rule set to edit!",
                    "Rule Set Picker");
        }
    }

    public void editRuleSets() {
        final Application app = Worldz.getApplication();
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
        final Application app = Worldz.getApplication();
        this.hideOutput();
        app.getEditor().showOutput();
    }

    private void setUpGUI() {
        final Application app = Worldz.getApplication();
        this.outputFrame = new JFrame("Rule Set Picker");
        final Image iconlogo = app.getIconLogo();
        this.outputFrame.setIconImage(iconlogo);
        this.outputPane = new Container();
        this.borderPane = new Container();
        this.create = new JButton("Create");
        this.destroy = new JButton("Destroy");
        this.load = new JButton("Load");
        this.save = new JButton("Save");
        this.edit = new JButton("Edit");
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
        this.picker = new PicturePicker(this.editorAppearances, this.names);
        this.picker.setPickerDimensions(GraphicsManager.MAX_WINDOW_SIZE);
        this.borderPane.add(this.picker.getPicker(), BorderLayout.CENTER);
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
