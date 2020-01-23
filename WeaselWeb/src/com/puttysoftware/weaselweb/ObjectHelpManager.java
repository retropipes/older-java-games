/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb;

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
import com.puttysoftware.weaselweb.maze.generic.MazeObjectList;
import com.puttysoftware.weaselweb.resourcemanagers.ImageManager;
import com.puttysoftware.weaselweb.resourcemanagers.LogoManager;

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

    public void updateHelp() {
        this.initHelp();
        final MazeObjectList objs = WeaselWeb.getApplication().getObjects();
        this.hv.updateHelp(objs.getAllEditorAppearances(), objs.getAllNames());
    }

    private void initHelp() {
        if (!this.inited) {
            this.buttonHandler = new ButtonHandler();
            this.objectList = WeaselWeb.getApplication().getObjects();
            this.objectNames = this.objectList.getAllDescriptions();
            this.objectAppearances = this.objectList.getAllEditorAppearances();
            this.hv = new GraphicalHelpViewer(this.objectAppearances,
                    this.objectNames, new Color(223, 223, 223));
            this.export = new JButton("Export");
            this.export.addActionListener(this.buttonHandler);
            this.helpFrame = new JFrame("WeaselWeb Object Help");
            final Image iconlogo = LogoManager.getIconLogo();
            this.helpFrame.setIconImage(iconlogo);
            this.helpFrame
                    .setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            this.helpFrame.setLayout(new BorderLayout());
            this.helpFrame.add(this.hv.getHelp(), BorderLayout.CENTER);
            this.helpFrame.add(this.export, BorderLayout.SOUTH);
            this.hv.setHelpSize(ImageManager.MAX_WINDOW_SIZE,
                    ImageManager.MAX_WINDOW_SIZE);
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
            // TODO Auto-generated constructor stub
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            ObjectHelpManager.this.hv.exportHelp();
        }
    }
}
