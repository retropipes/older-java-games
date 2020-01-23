/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz;

import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import net.worldwizard.help.GraphicalHelpViewer;
import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.worldz.generic.WorldObjectList;
import net.worldwizard.worldz.resourcemanagers.GraphicsManager;

public class ObjectHelpManager {
    // Fields
    private JFrame helpFrame;
    private WorldObjectList objectList;
    private String[] objectNames;
    private BufferedImageIcon[] objectAppearances;
    private GraphicalHelpViewer hv;
    private boolean inited = false;

    // Constructors
    public ObjectHelpManager() {
        // Do nothing
    }

    // Methods
    public void showHelp() {
        if (!this.inited) {
            this.objectList = Worldz.getApplication().getObjects();
            this.objectNames = this.objectList.getAllDescriptions();
            this.objectAppearances = this.objectList.getAllEditorAppearances();
            this.hv = new GraphicalHelpViewer(this.objectAppearances,
                    this.objectNames);
            this.helpFrame = new JFrame("Worldz Object Help");
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
