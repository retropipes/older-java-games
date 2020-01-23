/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz;

import java.awt.FlowLayout;
import java.awt.Image;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import net.worldwizard.help.HTMLHelpViewer;
import net.worldwizard.worldz.resourcemanagers.GraphicsManager;
import net.worldwizard.worldz.resourcemanagers.HelpManager;

public class GeneralHelpManager {
    // Fields
    private JFrame helpFrame;
    private HTMLHelpViewer hv;
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
            this.helpFrame = new JFrame("Worldz Help");
            final Image iconlogo = Worldz.getApplication().getIconLogo();
            this.helpFrame.setIconImage(iconlogo);
            this.helpFrame
                    .setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            this.helpFrame.setLayout(new FlowLayout());
            this.helpFrame.add(this.hv.getHelp());
            this.hv.setHelpSize(GraphicsManager.MAX_WINDOW_SIZE,
                    GraphicsManager.MAX_WINDOW_SIZE);
            this.helpFrame.pack();
            this.helpFrame.setResizable(false);
            this.inited = true;
        }
        this.helpFrame.setVisible(true);
    }
}