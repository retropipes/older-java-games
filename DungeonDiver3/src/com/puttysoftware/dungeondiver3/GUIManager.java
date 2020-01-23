/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.puttysoftware.dungeondiver3.prefs.PreferencesManager;
import com.puttysoftware.dungeondiver3.resourcemanagers.LogoManager;
import com.puttysoftware.dungeondiver3.scenario.ScenarioManager;
import com.puttysoftware.dungeondiver3.support.Support;
import com.puttysoftware.dungeondiver3.support.creatures.PartyManager;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.platform.Platform;
import com.puttysoftware.xio.DirectoryUtilities;

public class GUIManager {
    // Fields
    private final JFrame guiFrame;
    private final JLabel logoLabel;

    // Constructors
    public GUIManager() {
        CloseHandler cHandler = new CloseHandler();
        if (Support.inDebugMode()) {
            this.guiFrame = new JFrame(DungeonDiver3.getProgramName()
                    + " (DEBUG)");
        } else {
            this.guiFrame = new JFrame(DungeonDiver3.getProgramName());
        }
        Container guiPane = this.guiFrame.getContentPane();
        this.guiFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.guiFrame.setLayout(new GridLayout(1, 1));
        this.logoLabel = new JLabel("", null, SwingConstants.CENTER);
        this.logoLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
        guiPane.add(this.logoLabel);
        this.guiFrame.setResizable(false);
        this.guiFrame.addWindowListener(cHandler);
        Platform.hookFrameIcon(this.guiFrame, Application.getIconLogo());
    }

    // Methods
    JFrame getGUIFrame() {
        if (this.guiFrame.isVisible()) {
            return this.guiFrame;
        } else {
            return null;
        }
    }

    public void showGUI() {
        Application app = DungeonDiver3.getApplication();
        app.setInGUI();
        this.guiFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
        this.guiFrame.setVisible(true);
        app.getMenuManager().setMainMenus();
        app.getMenuManager().checkFlags();
    }

    public void hideGUI() {
        this.guiFrame.setVisible(false);
    }

    void updateLogo() {
        final BufferedImageIcon logo = LogoManager.getLogo();
        this.logoLabel.setIcon(logo);
        final Image iconlogo = LogoManager.getIconLogo();
        this.guiFrame.setIconImage(iconlogo);
        this.guiFrame.pack();
    }

    public boolean quitHandler() {
        // Check character writeback
        if (PreferencesManager.areCharacterChangesPermanent()) {
            PartyManager.writebackCharacters();
        }
        ScenarioManager mm = DungeonDiver3.getApplication()
                .getScenarioManager();
        boolean saved = true;
        int status = JOptionPane.DEFAULT_OPTION;
        if (mm.getDirty()) {
            status = ScenarioManager.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = mm.saveGame();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                mm.setDirty(false);
            }
        }
        if (saved) {
            PreferencesManager.writePrefs();
            // Run cleanup task
            try {
                File dirToDelete = new File(
                        System.getProperty("java.io.tmpdir") + File.separator
                                + "DungeonDiver3");
                DirectoryUtilities.removeDirectory(dirToDelete);
            } catch (Throwable t) {
                // Ignore
            }
        }
        return saved;
    }

    private class CloseHandler implements WindowListener {
        public CloseHandler() {
            // TODO Auto-generated constructor stub
        }

        @Override
        public void windowActivated(WindowEvent arg0) {
            // Do nothing
        }

        @Override
        public void windowClosed(WindowEvent arg0) {
            // Do nothing
        }

        @Override
        public void windowClosing(WindowEvent arg0) {
            if (GUIManager.this.quitHandler()) {
                System.exit(0);
            }
        }

        @Override
        public void windowDeactivated(WindowEvent arg0) {
            // Do nothing
        }

        @Override
        public void windowDeiconified(WindowEvent arg0) {
            // Do nothing
        }

        @Override
        public void windowIconified(WindowEvent arg0) {
            // Do nothing
        }

        @Override
        public void windowOpened(WindowEvent arg0) {
            // Do nothing
        }
    }
}
