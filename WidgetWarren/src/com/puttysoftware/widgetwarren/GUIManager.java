/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.widgetwarren.maze.MazeManager;
import com.puttysoftware.widgetwarren.maze.TempDirCleanup;
import com.puttysoftware.widgetwarren.prefs.PreferencesManager;
import com.puttysoftware.widgetwarren.resourcemanagers.LogoManager;

public class GUIManager {
    // Fields
    private final JFrame guiFrame;
    private final Container guiPane;
    private final JLabel logoLabel;
    private final CloseHandler cHandler;

    // Constructors
    public GUIManager() {
        this.cHandler = new CloseHandler();
        this.guiFrame = new JFrame("WidgetWarren");
        this.guiPane = this.guiFrame.getContentPane();
        this.guiFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.guiFrame.setLayout(new GridLayout(1, 1));
        this.logoLabel = new JLabel("", null, SwingConstants.CENTER);
        this.logoLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.guiPane.add(this.logoLabel);
        this.guiFrame.setResizable(false);
        this.guiFrame.addWindowListener(this.cHandler);
        final BufferedImageIcon logo = LogoManager.getLogo();
        this.logoLabel.setIcon(logo);
        final Image iconlogo = LogoManager.getLogo();
        this.guiFrame.setIconImage(iconlogo);
        this.guiFrame.pack();
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
        final Application app = WidgetWarren.getApplication();
        app.setInGUI(true);
        this.guiFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
        this.guiFrame.setVisible(true);
        app.getMenuManager().setMainMenus();
        app.getMenuManager().checkFlags();
    }

    public void hideGUI() {
        final Application app = WidgetWarren.getApplication();
        app.setInGUI(false);
        this.guiFrame.setVisible(false);
    }

    public void hideGUITemporarily() {
        this.guiFrame.setVisible(false);
    }

    public boolean quitHandler() {
        final MazeManager mm = WidgetWarren.getApplication().getMazeManager();
        boolean saved = true;
        int status = JOptionPane.DEFAULT_OPTION;
        if (mm.getDirty()) {
            status = mm.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = mm.saveMaze();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                mm.setDirty(false);
            }
        }
        if (saved) {
            PreferencesManager.writePrefs();
            // Run cleanup task
            new TempDirCleanup().start();
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
