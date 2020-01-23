/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren;

import java.awt.FlowLayout;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.puttysoftware.help.HTMLHelpViewer;
import com.puttysoftware.widgetwarren.resourcemanagers.GUIConstants;

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
            URL helpURL = GeneralHelpManager.class
                    .getResource("/com/puttysoftware/widgetwarren/resources/help/help.html");
            this.hv = new HTMLHelpViewer(helpURL);
            this.helpFrame = new JFrame("WidgetWarren Help");
            this.helpFrame
                    .setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            this.helpFrame.setLayout(new FlowLayout());
            this.helpFrame.add(this.hv.getHelp());
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
        this.helpFrame.setVisible(true);
    }
}