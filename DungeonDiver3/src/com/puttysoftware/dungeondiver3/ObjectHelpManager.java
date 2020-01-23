/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.puttysoftware.dungeondiver3.resourcemanagers.GraphicsConstants;
import com.puttysoftware.dungeondiver3.support.Support;
import com.puttysoftware.dungeondiver3.support.map.generic.MapObjectList;
import com.puttysoftware.dungeondiver3.support.resourcemodifiers.ImageTransformer;
import com.puttysoftware.help.GraphicalHelpViewer;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.platform.Platform;

class ObjectHelpManager {
    // Fields
    private JFrame helpFrame;
    GraphicalHelpViewer hv;
    private boolean inited;

    // Constructors
    public ObjectHelpManager() {
        this.inited = false;
    }

    // Methods
    void showHelp() {
        this.initHelp();
        this.helpFrame.setVisible(true);
    }

    private void initHelp() {
        if (!this.inited) {
            String title;
            if (Support.inDebugMode()) {
                title = "DungeonDiver3 Help (DEBUG)";
            } else {
                title = "DungeonDiver3 Help";
            }
            ButtonHandler buttonHandler = new ButtonHandler();
            MapObjectList objectList = DungeonDiver3.getApplication()
                    .getObjects();
            String[] objectNames = objectList.getAllDescriptions();
            BufferedImageIcon[] objectAppearances = objectList
                    .getAllObjectHelpImages();
            this.hv = new GraphicalHelpViewer(objectAppearances, objectNames,
                    ImageTransformer.getReplacementColor());
            JButton export = new JButton("Export");
            export.addActionListener(buttonHandler);
            this.helpFrame = new JFrame(title);
            Platform.hookFrameIcon(this.helpFrame, Application.getIconLogo());
            this.helpFrame
                    .setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            this.helpFrame.setLayout(new BorderLayout());
            this.helpFrame.add(this.hv.getHelp(), BorderLayout.CENTER);
            this.helpFrame.add(export, BorderLayout.SOUTH);
            this.hv.setHelpSize(GraphicsConstants.MAX_WINDOW_SIZE,
                    GraphicsConstants.MAX_WINDOW_SIZE);
            this.helpFrame.pack();
            this.helpFrame.setResizable(false);
            // Mac OS X-specific fixes
            if (System.getProperty("os.name").startsWith("Mac OS X")) {
                MenuManager menu = new MenuManager();
                menu.setHelpMenus();
                this.helpFrame.setJMenuBar(menu.getMainMenuBar());
            }
            this.inited = true;
        }
    }

    private class ButtonHandler implements ActionListener {
        public ButtonHandler() {
            // TODO Auto-generated constructor stub
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ObjectHelpManager.this.hv.exportHelp();
        }
    }
}
