/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer;

import java.awt.FlowLayout;
import java.awt.Image;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.puttysoftware.help.HTMLHelpViewer;
import com.puttysoftware.rulemazer.resourcemanagers.GraphicsManager;
import com.puttysoftware.rulemazer.resourcemanagers.HelpManager;

public class GeneralHelpManager {
    // Fields
    private JFrame helpFrame;
    private HTMLHelpViewer hv;
    private MenuManager menu;
    private boolean inited = false;

    // Constructors
    public GeneralHelpManager() {
        // Do nothing
    }

    // Methods
    public void showHelp() {
        if (!this.inited) {
            final URL helpURL = HelpManager.getHelpURL();
            this.hv = new HTMLHelpViewer(helpURL);
            this.helpFrame = new JFrame("RuleMazer Help");
            final Image iconlogo = Main.getApplication().getIconLogo();
            this.helpFrame.setIconImage(iconlogo);
            this.helpFrame
                    .setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            this.helpFrame.setLayout(new FlowLayout());
            this.helpFrame.add(this.hv.getHelp());
            this.hv.setHelpSize(GraphicsManager.MAX_WINDOW_SIZE,
                    GraphicsManager.MAX_WINDOW_SIZE);
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
        this.helpFrame.setVisible(true);
    }
}