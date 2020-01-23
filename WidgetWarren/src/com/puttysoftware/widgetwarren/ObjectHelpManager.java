/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.puttysoftware.help.GraphicalHelpViewer;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.widgetwarren.generic.MazeObjectList;
import com.puttysoftware.widgetwarren.resourcemanagers.GUIConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.LogoManager;

public class ObjectHelpManager {
    // Fields
    private JFrame helpFrame;
    private JButton export;
    private MazeObjectList objectList;
    private String[] objectNames;
    private BufferedImageIcon[] objectAppearances;
    GraphicalHelpViewer hv;
    private MenuManager menu;
    private ButtonHandler buttonHandler;
    private boolean inited = false;

    // Constructors
    public ObjectHelpManager() {
        // Do nothing
    }

    // Methods
    public void showHelp() {
        this.initHelp();
        this.helpFrame.setVisible(true);
    }

    private void initHelp() {
        if (!this.inited) {
            this.buttonHandler = new ButtonHandler();
            this.objectList = WidgetWarren.getApplication().getObjects();
            this.objectNames = this.objectList.getAllDescriptions();
            this.objectAppearances = this.objectList.getAllEditorAppearances();
            this.hv = new GraphicalHelpViewer(this.objectAppearances,
                    this.objectNames, new Color(223, 223, 223));
            this.export = new JButton("Export");
            this.export.addActionListener(this.buttonHandler);
            this.helpFrame = new JFrame("WidgetWarren Object Help");
            final Image iconlogo = LogoManager.getLogo();
            this.helpFrame.setIconImage(iconlogo);
            this.helpFrame
                    .setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            this.helpFrame.setLayout(new BorderLayout());
            this.helpFrame.add(this.hv.getHelp(), BorderLayout.CENTER);
            this.helpFrame.add(this.export, BorderLayout.SOUTH);
            this.hv.setHelpSize(GUIConstants.MAX_WINDOW_SIZE,
                    GUIConstants.MAX_WINDOW_SIZE);
            this.helpFrame.pack();
            this.helpFrame.setResizable(false);
            // Mac OS X-specific fixes
            if (System.getProperty("os.name").startsWith("Mac OS X")) {
                this.menu = new MenuManager();
                this.menu.setHelpMenus();
                this.helpFrame.setJMenuBar(this.menu.getMainMenuBar());
            }
            this.inited = true;
        }
    }

    private class ButtonHandler implements ActionListener {
        public ButtonHandler() {
            // Do nothing
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            ObjectHelpManager.this.hv.exportHelp();
        }
    }
}
