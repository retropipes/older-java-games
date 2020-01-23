/*  TAP: A Text Adventure Parser
Copyright (C) 2010 Eric Ahnell

Any questions should be directed to the author via email at: tap@worldwizard.net
 */
package net.worldwizard.tap;

import java.awt.Image;

import javax.swing.JFrame;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.tap.adventure.AdventureManager;
import net.worldwizard.tap.resourcemanagers.GraphicsManager;

public class Application {
    // Fields
    private AboutDialog about;
    private AdventureManager AdventureMgr;
    private MenuManager menuMgr;
    private GUIManager guiMgr;
    private BufferedImageIcon microLogo;
    private Image iconlogo;
    private static final int VERSION_MAJOR = 3;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_BUGFIX = 0;

    // Constructors
    public Application() {
        // Do nothing
    }

    // Methods
    void postConstruct() {
        // Cache Icon Logo
        this.iconlogo = GraphicsManager.getIconLogo();
        // Create Managers
        this.about = new AboutDialog(Application.getVersionString());
        this.guiMgr = new GUIManager();
        this.menuMgr = new MenuManager();
        // Cache Micro Logo
        this.microLogo = GraphicsManager.getMicroLogo();
    }

    public MenuManager getMenuManager() {
        return this.menuMgr;
    }

    public GUIManager getGUIManager() {
        return this.guiMgr;
    }

    public AdventureManager getAdventureManager() {
        if (this.AdventureMgr == null) {
            this.AdventureMgr = new AdventureManager();
        }
        return this.AdventureMgr;
    }

    public AboutDialog getAboutDialog() {
        return this.about;
    }

    public BufferedImageIcon getMicroLogo() {
        return this.microLogo;
    }

    public Image getIconLogo() {
        return this.iconlogo;
    }

    private static String getVersionString() {
        return "" + Application.VERSION_MAJOR + "." + Application.VERSION_MINOR
                + "." + Application.VERSION_BUGFIX;
    }

    public JFrame getOutputFrame() {
        return this.getGUIManager().getGUIFrame();
    }
}
