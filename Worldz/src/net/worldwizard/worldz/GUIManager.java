/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.worldz.resourcemanagers.GraphicsManager;

public class GUIManager {
    // Fields
    private final JFrame guiFrame;
    private final Container guiPane;
    private final JLabel logoLabel;

    // Constructors
    public GUIManager() {
        this.guiFrame = new JFrame("Worldz");
        this.guiPane = this.guiFrame.getContentPane();
        this.guiFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.guiFrame.setLayout(new GridLayout(1, 1));
        this.logoLabel = new JLabel("", null, SwingConstants.CENTER);
        this.logoLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.guiPane.add(this.logoLabel);
        this.guiFrame.setResizable(false);
    }

    // Methods
    public JFrame getGUIFrame() {
        if (this.guiFrame.isVisible()) {
            return this.guiFrame;
        } else {
            return null;
        }
    }

    public void showGUI() {
        final Application app = Worldz.getApplication();
        app.setInGUI(true);
        this.guiFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
        this.guiFrame.setVisible(true);
        app.getMenuManager().setMainMenus();
        app.getMenuManager().checkFlags();
    }

    public void hideGUI() {
        final Application app = Worldz.getApplication();
        app.setInGUI(false);
        this.guiFrame.setVisible(false);
    }

    public void hideGUITemporarily() {
        this.guiFrame.setVisible(false);
    }

    public void updateLogo() {
        final BufferedImageIcon logo = GraphicsManager.getLogo();
        this.logoLabel.setIcon(logo);
        final Image iconlogo = Worldz.getApplication().getIconLogo();
        this.guiFrame.setIconImage(iconlogo);
        this.guiFrame.pack();
    }
}
