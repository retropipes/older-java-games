/*  TAP: A Text Adventure Parser
Copyright (C) 2010 Eric Ahnell

Any questions should be directed to the author via email at: tap@worldwizard.net
 */
package net.worldwizard.tap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuManager {
    // Fields
    private JMenuBar mainMenuBar;
    private JMenu fileMenu, helpMenu;
    private JMenuItem fileOpen, fileClose, fileExit;
    private JMenuItem helpAbout;
    private KeyStroke fileOpenAccel, fileCloseAccel;
    private final EventHandler handler;

    // Constructors
    public MenuManager() {
        this.handler = new EventHandler();
        this.createAccelerators();
        this.createMenus();
        this.setInitialMenuState();
    }

    // Methods
    public JMenuBar getMainMenuBar() {
        return this.mainMenuBar;
    }

    public void checkFlags() {
        final Application app = TAP.getApplication();
        if (app.getAdventureManager().getLoaded()) {
            this.setMenusLoadedOn();
        } else {
            this.setMenusLoadedOff();
        }
    }

    private void setMenusLoadedOn() {
        this.fileClose.setEnabled(true);
    }

    private void setMenusLoadedOff() {
        this.fileClose.setEnabled(false);
    }

    private void createAccelerators() {
        int modKey;
        if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            modKey = InputEvent.META_DOWN_MASK;
        } else {
            modKey = InputEvent.CTRL_DOWN_MASK;
        }
        this.fileOpenAccel = KeyStroke.getKeyStroke(KeyEvent.VK_O, modKey);
        this.fileCloseAccel = KeyStroke.getKeyStroke(KeyEvent.VK_W, modKey);
    }

    private void createMenus() {
        this.mainMenuBar = new JMenuBar();
        this.fileMenu = new JMenu("File");
        this.helpMenu = new JMenu("Help");
        this.fileOpen = new JMenuItem("Open Adventure...");
        this.fileOpen.setAccelerator(this.fileOpenAccel);
        this.fileClose = new JMenuItem("Close Adventure");
        this.fileClose.setAccelerator(this.fileCloseAccel);
        this.fileExit = new JMenuItem("Exit");
        this.helpAbout = new JMenuItem("About TAP...");
        this.fileOpen.addActionListener(this.handler);
        this.fileClose.addActionListener(this.handler);
        this.fileExit.addActionListener(this.handler);
        this.helpAbout.addActionListener(this.handler);
        this.fileMenu.add(this.fileOpen);
        this.fileMenu.add(this.fileClose);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            this.fileMenu.add(this.fileExit);
        }
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            this.helpMenu.add(this.helpAbout);
        }
        this.mainMenuBar.add(this.fileMenu);
        this.mainMenuBar.add(this.helpMenu);
    }

    private void setInitialMenuState() {
        this.fileOpen.setEnabled(true);
        this.fileClose.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.helpAbout.setEnabled(true);
    }

    private class EventHandler implements ActionListener {
        public EventHandler() {
            // TODO Auto-generated constructor stub
        }

        // Handle menus
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                final Application app = TAP.getApplication();
                boolean loaded = false;
                final String cmd = e.getActionCommand();
                if (cmd.equals("Open Adventure...")) {
                    loaded = app.getAdventureManager().loadAdventure();
                    app.getAdventureManager().setLoaded(loaded);
                } else if (cmd.equals("Close Adventure")) {
                    app.getAdventureManager().setLoaded(false);
                } else if (cmd.equals("Exit")) {
                    // Exit program
                    System.exit(0);
                } else if (cmd.equals("About TAP...")) {
                    app.getAboutDialog().showAboutDialog();
                }
                MenuManager.this.checkFlags();
            } catch (final Exception ex) {
                TAP.getDebug().debug(ex);
            }
        }
    }
}
