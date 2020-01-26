/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.puttysoftware.gemma.resourcemanagers.GraphicsConstants;
import com.puttysoftware.gemma.support.Support;
import com.puttysoftware.gemma.support.map.generic.MapObjectList;
import com.puttysoftware.gemma.support.resourcemodifiers.ImageTransformer;
import com.puttysoftware.help.GraphicalHelpViewer;
import com.puttysoftware.images.BufferedImageIcon;

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
                title = "Gemma Help (DEBUG)";
            } else {
                title = "Gemma Help";
            }
            final ButtonHandler buttonHandler = new ButtonHandler();
            final MapObjectList objectList = Gemma.getApplication()
                    .getObjects();
            final String[] objectNames = objectList.getAllDescriptions();
            final BufferedImageIcon[] objectAppearances = objectList
                    .getAllObjectHelpImages();
            this.hv = new GraphicalHelpViewer(objectAppearances, objectNames,
                    ImageTransformer.getReplacementColor());
            final JButton export = new JButton("Export");
            export.addActionListener(buttonHandler);
            this.helpFrame = new JFrame(title);
            this.helpFrame
                    .setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            this.helpFrame.setLayout(new BorderLayout());
            this.helpFrame.add(this.hv.getHelp(), BorderLayout.CENTER);
            this.helpFrame.add(export, BorderLayout.SOUTH);
            this.hv.setHelpSize(GraphicsConstants.MAX_WINDOW_WIDTH,
                    GraphicsConstants.MAX_WINDOW_HEIGHT);
            this.helpFrame.pack();
            this.helpFrame.setResizable(false);
            // Mac OS X-specific fixes
            if (System.getProperty("os.name").startsWith("Mac OS X")) {
                final MenuManager menu = new MenuManager();
                menu.setHelpMenus();
                this.helpFrame.setJMenuBar(menu.getMainMenuBar());
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
