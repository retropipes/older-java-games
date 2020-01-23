/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import com.puttysoftware.riskyrescue.Application;
import com.puttysoftware.riskyrescue.RiskyRescue;
import com.puttysoftware.riskyrescue.Support;
import com.puttysoftware.riskyrescue.assets.ImageManager;
import com.puttysoftware.riskyrescue.assets.ObjectImage;
import com.puttysoftware.riskyrescue.assets.modifiers.ImageComposer;
import com.puttysoftware.riskyrescue.creatures.party.PartyManager;
import com.puttysoftware.riskyrescue.map.Map;
import com.puttysoftware.riskyrescue.map.MapConstants;
import com.puttysoftware.riskyrescue.map.objects.MapObject;
import com.puttysoftware.riskyrescue.prefs.PreferencesManager;
import com.puttysoftware.riskyrescue.scenario.ScenarioManager;
import com.puttysoftware.riskyrescue.utilities.DrawGrid;

class GameGUI {
    // Fields
    private JFrame outputFrame;
    private Container borderPane;
    private DungeonDraw outputPane;
    private JLabel messageLabel;
    private final GameViewingWindowManager vwMgr;
    private final StatGUI sg;
    private DrawGrid drawGrid;

    // Constructors
    public GameGUI() {
        this.vwMgr = new GameViewingWindowManager();
        this.sg = new StatGUI();
        this.setUpGUI();
    }

    // Methods
    public void updateStats() {
        this.getStatGUI().updateStats();
    }

    GameViewingWindowManager getViewManager() {
        return this.vwMgr;
    }

    private StatGUI getStatGUI() {
        return this.sg;
    }

    public void setStatusMessage(final String msg) {
        this.messageLabel.setText(msg);
    }

    public void redrawMap() {
        // Draw the map, if it is visible
        if (this.outputFrame.isVisible()) {
            final Application app = RiskyRescue.getApplication();
            int x, y, u, v;
            int xFix, yFix;
            boolean visible;
            final Map m = RiskyRescue.getApplication().getScenarioManager()
                    .getMap();
            u = m.getPlayerLocationX();
            v = m.getPlayerLocationY();
            for (x = this.vwMgr.getViewingWindowLocationX(); x <= this.vwMgr
                    .getLowerRightViewingWindowLocationX(); x++) {
                for (y = this.vwMgr.getViewingWindowLocationY(); y <= this.vwMgr
                        .getLowerRightViewingWindowLocationY(); y++) {
                    xFix = x - this.vwMgr.getViewingWindowLocationX();
                    yFix = y - this.vwMgr.getViewingWindowLocationY();
                    visible = app.getScenarioManager().getMap()
                            .isSquareVisible(u, v, y, x);
                    try {
                        if (visible) {
                            MapObject obj1, obj2;
                            obj1 = m.getCell(y, x, m.getPlayerLocationZ(),
                                    MapConstants.LAYER_GROUND);
                            obj2 = m.getCell(y, x, m.getPlayerLocationZ(),
                                    MapConstants.LAYER_OBJECT);
                            final boolean hasNote = m.hasNote(y, x,
                                    m.getPlayerLocationZ());
                            final boolean isPlayer = u == y && v == x;
                            if (hasNote) {
                                if (isPlayer) {
                                    this.drawGrid.setImageCell(
                                            ImageComposer.getCompositeImage(
                                                    obj1.getImage(),
                                                    obj2.getImage(),
                                                    PartyManager.getParty()
                                                            .getPlayer()
                                                            .getImage(),
                                                    ImageManager.getObjectImage(
                                                            0,
                                                            ObjectImage.NOTE)),
                                            xFix, yFix);
                                } else {
                                    this.drawGrid.setImageCell(
                                            ImageComposer.getCompositeImage(
                                                    obj1.getImage(),
                                                    obj2.getImage(),
                                                    ImageManager.getObjectImage(
                                                            0,
                                                            ObjectImage.NOTE)),
                                            xFix, yFix);
                                }
                            } else {
                                if (isPlayer) {
                                    this.drawGrid.setImageCell(
                                            ImageComposer.getCompositeImage(
                                                    obj1.getImage(),
                                                    obj2.getImage(),
                                                    PartyManager.getParty()
                                                            .getPlayer()
                                                            .getImage()),
                                            xFix, yFix);
                                } else {
                                    this.drawGrid.setImageCell(
                                            ImageComposer.getCompositeImage(
                                                    obj1.getImage(),
                                                    obj2.getImage()),
                                            xFix, yFix);
                                }
                            }
                        } else {
                            this.drawGrid
                                    .setImageCell(
                                            ImageManager.getObjectImage(0,
                                                    ObjectImage.DARKNESS),
                                            xFix, yFix);
                        }
                    } catch (final ArrayIndexOutOfBoundsException ae) {
                        this.drawGrid.setImageCell(ImageManager.getObjectImage(
                                0, ObjectImage.SEALING_WALL), xFix, yFix);
                    }
                }
            }
            this.setStatusMessage(" ");
            this.outputPane.updateGrid(this.drawGrid);
            this.outputPane.repaint();
            this.outputFrame.pack();
        }
    }

    public void resetViewingWindow() {
        final Map m = RiskyRescue.getApplication().getScenarioManager()
                .getMap();
        this.vwMgr.setViewingWindowLocationX(m.getPlayerLocationY()
                - GameViewingWindowManager.getOffsetFactor());
        this.vwMgr.setViewingWindowLocationY(m.getPlayerLocationX()
                - GameViewingWindowManager.getOffsetFactor());
    }

    public JFrame getOutputFrame() {
        return this.outputFrame;
    }

    void updateGameGUI() {
        this.borderPane.removeAll();
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        this.borderPane.add(this.getStatGUI().getStatsPane(),
                BorderLayout.EAST);
        this.getStatGUI().updateImages();
        this.getStatGUI().updateStats();
    }

    public void showOutput() {
        final Application app = RiskyRescue.getApplication();
        app.getMenuManager().setGameMenus();
        this.outputFrame.setVisible(true);
        app.attachMenus(this.outputFrame);
    }

    public void hideOutput() {
        this.outputFrame.setVisible(false);
    }

    private void setUpGUI() {
        final EventHandler handler = new EventHandler();
        this.borderPane = new Container();
        this.borderPane.setLayout(new BorderLayout());
        this.messageLabel = new JLabel(" ");
        this.messageLabel.setOpaque(true);
        if (Support.inDebugMode()) {
            this.outputFrame = new JFrame("Dungeon (DEBUG)");
        } else {
            this.outputFrame = new JFrame("Dungeon");
        }
        this.outputPane = new DungeonDraw();
        this.outputFrame.setContentPane(this.borderPane);
        this.outputFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.outputPane.setLayout(
                new GridLayout(GameViewingWindowManager.getViewingWindowSize(),
                        GameViewingWindowManager.getViewingWindowSize()));
        this.outputFrame.setResizable(false);
        this.outputFrame.addKeyListener(handler);
        this.outputFrame.addWindowListener(handler);
        this.drawGrid = new DrawGrid(
                GameViewingWindowManager.getViewingWindowSize());
        for (int x = 0; x < GameViewingWindowManager
                .getViewingWindowSize(); x++) {
            for (int y = 0; y < GameViewingWindowManager
                    .getViewingWindowSize(); y++) {
                this.drawGrid.setImageCell(
                        ImageManager.getObjectImage(0, ObjectImage.DARKNESS), x,
                        y);
            }
        }
        this.outputPane.updateGrid(this.drawGrid);
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        this.borderPane.add(this.getStatGUI().getStatsPane(),
                BorderLayout.EAST);
    }

    private class EventHandler implements KeyListener, WindowListener {
        public EventHandler() {
            // Do nothing
        }

        @Override
        public void keyPressed(final KeyEvent e) {
            if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
                if (e.isMetaDown()) {
                    return;
                }
            } else {
                if (e.isControlDown()) {
                    return;
                }
            }
            if (!PreferencesManager.oneMove()) {
                this.handleMovement(e);
            }
        }

        @Override
        public void keyReleased(final KeyEvent e) {
            if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
                if (e.isMetaDown()) {
                    return;
                }
            } else {
                if (e.isControlDown()) {
                    return;
                }
            }
            if (PreferencesManager.oneMove()) {
                this.handleMovement(e);
            }
        }

        @Override
        public void keyTyped(final KeyEvent e) {
            // Do nothing
        }

        public void handleMovement(final KeyEvent e) {
            try {
                final GameLogic gm = RiskyRescue.getApplication()
                        .getGameManager();
                final int keyCode = e.getKeyCode();
                switch (keyCode) {
                case KeyEvent.VK_NUMPAD4:
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    gm.updatePositionRelative(-1, 0, 0);
                    break;
                case KeyEvent.VK_NUMPAD2:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_X:
                    gm.updatePositionRelative(0, 1, 0);
                    break;
                case KeyEvent.VK_NUMPAD6:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    gm.updatePositionRelative(1, 0, 0);
                    break;
                case KeyEvent.VK_NUMPAD8:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    gm.updatePositionRelative(0, -1, 0);
                    break;
                case KeyEvent.VK_NUMPAD7:
                case KeyEvent.VK_Q:
                    gm.updatePositionRelative(-1, -1, 0);
                    break;
                case KeyEvent.VK_NUMPAD9:
                case KeyEvent.VK_E:
                    gm.updatePositionRelative(1, -1, 0);
                    break;
                case KeyEvent.VK_NUMPAD3:
                case KeyEvent.VK_C:
                    gm.updatePositionRelative(1, 1, 0);
                    break;
                case KeyEvent.VK_NUMPAD1:
                case KeyEvent.VK_Z:
                    gm.updatePositionRelative(-1, 1, 0);
                    break;
                case KeyEvent.VK_NUMPAD5:
                case KeyEvent.VK_S:
                    gm.updatePositionRelative(0, 0, 0);
                    break;
                default:
                    break;
                }
            } catch (final Exception ex) {
                RiskyRescue.logError(ex);
            }
        }

        // Handle windows
        @Override
        public void windowActivated(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowClosed(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowClosing(final WindowEvent we) {
            try {
                final Application app = RiskyRescue.getApplication();
                boolean success = false;
                int status = 0;
                if (app.getScenarioManager().getDirty()) {
                    status = ScenarioManager.showSaveDialog();
                    if (status == JOptionPane.YES_OPTION) {
                        success = app.getScenarioManager().saveGame();
                        if (success) {
                            app.getGameManager().exitGame();
                        }
                    } else if (status == JOptionPane.NO_OPTION) {
                        app.getGameManager().exitGame();
                    }
                } else {
                    app.getGameManager().exitGame();
                }
            } catch (final Exception ex) {
                RiskyRescue.logError(ex);
            }
        }

        @Override
        public void windowDeactivated(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowDeiconified(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowIconified(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowOpened(final WindowEvent we) {
            // Do nothing
        }
    }
}
