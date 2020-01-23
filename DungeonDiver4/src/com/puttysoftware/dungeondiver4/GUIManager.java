/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4;

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

import com.puttysoftware.dungeondiver4.creatures.party.PartyManager;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonManager;
import com.puttysoftware.dungeondiver4.prefs.PreferencesManager;
import com.puttysoftware.dungeondiver4.resourcemanagers.LogoManager;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.xio.DirectoryUtilities;

public class GUIManager {
    // Fields
    private JFrame guiFrame;
    private JLabel logoLabel;

    // Constructors
    public GUIManager() {
        CloseHandler cHandler = new CloseHandler();
        this.guiFrame = new JFrame("DungeonDiver4");
        Container guiPane = this.guiFrame.getContentPane();
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
    public JFrame getGUIFrame() {
        if (this.guiFrame.isVisible()) {
            return this.guiFrame;
        } else {
            return null;
        }
    }

    public void showGUI() {
        Application app = DungeonDiver4.getApplication();
        app.setInGUI();
        this.guiFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
        this.guiFrame.setVisible(true);
        app.getMenuManager().setMainMenus();
        app.getMenuManager().checkFlags();
    }

    public void hideGUI() {
        this.guiFrame.setVisible(false);
    }

    public void hideGUITemporarily() {
        this.guiFrame.setVisible(false);
    }

    public void updateLogo() {
        final BufferedImageIcon logo = LogoManager.getLogo();
        this.logoLabel.setIcon(logo);
        final Image iconlogo = DungeonDiver4.getApplication().getIconLogo();
        this.guiFrame.setIconImage(iconlogo);
        this.guiFrame.pack();
    }

    public boolean quitHandler() {
        DungeonManager mm = DungeonDiver4.getApplication().getDungeonManager();
        boolean saved = true;
        int status = JOptionPane.DEFAULT_OPTION;
        if (mm.getDirty()) {
            status = mm.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = mm.saveDungeon();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                mm.setDirty(false);
            }
        }
        if (saved) {
            PreferencesManager.writePrefs();
            if (PreferencesManager.areCharacterChangesPermanent()) {
                PartyManager.writebackCharacters();
            }
            // Run cleanup task
            try {
                File dirToDelete = new File(Dungeon.getDungeonTempFolder());
                DirectoryUtilities.removeDirectory(dirToDelete);
            } catch (Throwable t) {
                // Ignore
            }
        }
        return saved;
    }

    private class CloseHandler implements WindowListener {
        CloseHandler() {
            // Do nothing
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
