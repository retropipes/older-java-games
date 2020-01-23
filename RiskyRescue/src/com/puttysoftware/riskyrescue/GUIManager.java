/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue;

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

import com.puttysoftware.fileutils.DirectoryUtilities;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.riskyrescue.assets.LogoManager;
import com.puttysoftware.riskyrescue.prefs.PreferencesManager;
import com.puttysoftware.riskyrescue.scenario.ScenarioManager;

public class GUIManager {
    // Fields
    private final JFrame guiFrame;
    private final JLabel logoLabel;

    // Constructors
    public GUIManager() {
        final CloseHandler cHandler = new CloseHandler();
        if (Support.inDebugMode()) {
            this.guiFrame = new JFrame(
                    RiskyRescue.getProgramName() + " (DEBUG)");
        } else {
            this.guiFrame = new JFrame(RiskyRescue.getProgramName());
        }
        final Container guiPane = this.guiFrame.getContentPane();
        this.guiFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.guiFrame.setLayout(new GridLayout(1, 1));
        this.logoLabel = new JLabel("", null, SwingConstants.CENTER);
        this.logoLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
        guiPane.add(this.logoLabel);
        this.guiFrame.setResizable(false);
        this.guiFrame.addWindowListener(cHandler);
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
        final Application app = RiskyRescue.getApplication();
        app.setInGUI();
        app.attachMenus(this.guiFrame);
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
        final ScenarioManager mm = RiskyRescue.getApplication()
                .getScenarioManager();
        boolean saved = true;
        int status;
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
                final File dirToDelete = new File(
                        System.getProperty("java.io.tmpdir") + File.separator
                                + "RiskyRescue");
                DirectoryUtilities.removeDirectory(dirToDelete);
            } catch (final Throwable t) {
                // Ignore
            }
        }
        return saved;
    }

    private class CloseHandler implements WindowListener {
        public CloseHandler() {
            // Do nothing
        }

        @Override
        public void windowActivated(final WindowEvent arg0) {
            // Do nothing
        }

        @Override
        public void windowClosed(final WindowEvent arg0) {
            // Do nothing
        }

        @Override
        public void windowClosing(final WindowEvent arg0) {
            if (GUIManager.this.quitHandler()) {
                System.exit(0);
            }
        }

        @Override
        public void windowDeactivated(final WindowEvent arg0) {
            // Do nothing
        }

        @Override
        public void windowDeiconified(final WindowEvent arg0) {
            // Do nothing
        }

        @Override
        public void windowIconified(final WindowEvent arg0) {
            // Do nothing
        }

        @Override
        public void windowOpened(final WindowEvent arg0) {
            // Do nothing
        }
    }
}
