/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3;

import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.puttysoftware.dungeondiver3.resourcemanagers.GraphicsConstants;
import com.puttysoftware.dungeondiver3.support.Support;
import com.puttysoftware.help.XHTMLHelpViewer;
import com.puttysoftware.platform.Platform;

class HistoryManager {
    // Fields
    private JFrame helpFrame;
    private boolean inited;

    // Constructors
    public HistoryManager() {
        this.inited = false;
    }

    // Methods
    void showHelp() {
        this.initHelp();
        this.helpFrame.setVisible(true);
    }

    private void initHelp() {
        if (!this.inited) {
            String ver = Support.getVersionString();
            String title;
            if (Support.inDebugMode()) {
                title = "What's New in DungeonDiver3 Version " + ver
                        + " (DEBUG)";
            } else {
                title = "What's New in DungeonDiver3 Version " + ver;
            }
            String rt = Support.getReleaseType();
            URL histDoc = HistoryManager.class
                    .getResource("/com/puttysoftware/dungeondiver3/resources/help/history/"
                            + rt + "history.xhtml");
            XHTMLHelpViewer hv = new XHTMLHelpViewer(histDoc);
            this.helpFrame = new JFrame(title);
            Platform.hookFrameIcon(this.helpFrame, Application.getIconLogo());
            this.helpFrame
                    .setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            this.helpFrame.setLayout(new BorderLayout());
            this.helpFrame.add(hv.getHelp(), BorderLayout.CENTER);
            hv.setHelpSize(GraphicsConstants.MAX_WINDOW_SIZE,
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
}
