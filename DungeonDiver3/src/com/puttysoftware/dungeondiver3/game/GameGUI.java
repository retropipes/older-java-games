/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import com.puttysoftware.dungeondiver3.Application;
import com.puttysoftware.dungeondiver3.DrawGrid;
import com.puttysoftware.dungeondiver3.DungeonDiver3;
import com.puttysoftware.dungeondiver3.prefs.PreferencesManager;
import com.puttysoftware.dungeondiver3.scenario.ScenarioManager;
import com.puttysoftware.dungeondiver3.support.Support;
import com.puttysoftware.dungeondiver3.support.map.Map;
import com.puttysoftware.dungeondiver3.support.map.MapConstants;
import com.puttysoftware.dungeondiver3.support.map.generic.MapObject;
import com.puttysoftware.dungeondiver3.support.map.objects.EmptyVoid;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.ImageManager;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.MusicConstants;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.MusicManager;
import com.puttysoftware.dungeondiver3.support.resourcemodifiers.ImageTransformer;
import com.puttysoftware.platform.Platform;

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
            final Application app = DungeonDiver3.getApplication();
            int x, y, u, v;
            int xFix, yFix;
            boolean visible;
            final Map m = DungeonDiver3.getApplication().getScenarioManager()
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
                            String name1, name2;
                            name1 = obj1.gameRenderHook(y, x,
                                    m.getPlayerLocationZ(),
                                    app.getScenarioManager().getMap());
                            name2 = obj2.gameRenderHook(y, x,
                                    m.getPlayerLocationZ(),
                                    app.getScenarioManager().getMap());
                            final boolean hasNote = m.hasNote(y, x,
                                    m.getPlayerLocationZ());
                            if (hasNote) {
                                this.drawGrid.setImageCell(
                                        ImageTransformer.getCompositeImage(
                                                ImageManager.getMapImage(name1,
                                                        obj1.getGameName(),
                                                        obj1.getGameTemplateTransform()),
                                                ImageManager.getMapImage(name2,
                                                        obj2.getGameName(),
                                                        obj2.getGameTemplateTransform()),
                                                ImageManager.getMapImage(
                                                        "Map Note", "Map Note",
                                                        null)),
                                        xFix, yFix);
                            } else {
                                this.drawGrid.setImageCell(
                                        ImageTransformer.getCompositeImage(
                                                ImageManager.getMapImage(name1,
                                                        obj1.getGameName(),
                                                        obj1.getGameTemplateTransform()),
                                                ImageManager.getMapImage(name2,
                                                        obj2.getGameName(),
                                                        obj2.getGameTemplateTransform())),
                                        xFix, yFix);
                            }
                        } else {
                            this.drawGrid.setImageCell(ImageManager.getMapImage(
                                    "Darkness", "Darkness", null), xFix, yFix);
                        }
                    } catch (final ArrayIndexOutOfBoundsException ae) {
                        this.drawGrid
                                .setImageCell(ImageManager.getMapImage(
                                        new EmptyVoid().gameRenderHook(y, x,
                                                m.getPlayerLocationZ(),
                                                app.getScenarioManager()
                                                        .getMap()),
                                        "Void", null), xFix, yFix);
                    } catch (final NullPointerException np) {
                        this.drawGrid
                                .setImageCell(ImageManager.getMapImage(
                                        new EmptyVoid().gameRenderHook(y, x,
                                                m.getPlayerLocationZ(),
                                                app.getScenarioManager()
                                                        .getMap()),
                                        "Void", null), xFix, yFix);
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
        final Map m = DungeonDiver3.getApplication().getScenarioManager()
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
        final Application app = DungeonDiver3.getApplication();
        app.getMenuManager().setGameMenus();
        if (PreferencesManager
                .getMusicEnabled(PreferencesManager.MUSIC_EXPLORING)) {
            MusicManager.stopMusic();
            MusicManager.playMusic(MusicConstants.MUSIC_EXPLORING);
        }
        this.outputFrame.setVisible(true);
        this.outputFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
    }

    public void hideOutput() {
        if (PreferencesManager
                .getMusicEnabled(PreferencesManager.MUSIC_EXPLORING)) {
            if (MusicManager.isMusicPlaying()) {
                MusicManager.stopMusic();
            }
        }
        if (this.outputFrame != null) {
            this.outputFrame.setVisible(false);
        }
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
        Platform.hookFrameIcon(this.outputFrame, Application.getIconLogo());
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
        this.outputPane.addMouseListener(handler);
        this.drawGrid = new DrawGrid(
                GameViewingWindowManager.getViewingWindowSize());
        for (int x = 0; x < GameViewingWindowManager
                .getViewingWindowSize(); x++) {
            for (int y = 0; y < GameViewingWindowManager
                    .getViewingWindowSize(); y++) {
                this.drawGrid.setImageCell(
                        ImageManager.getMapImage("Darkness", "Darkness", null),
                        x, y);
            }
        }
        this.outputPane.updateGrid(this.drawGrid);
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        this.borderPane.add(this.getStatGUI().getStatsPane(),
                BorderLayout.EAST);
    }

    private class EventHandler
            implements KeyListener, WindowListener, MouseListener {
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
                final GameLogic gm = DungeonDiver3.getApplication()
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
                DungeonDiver3.getErrorLogger().logError(ex);
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
                final Application app = DungeonDiver3.getApplication();
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
                DungeonDiver3.getErrorLogger().logError(ex);
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

        // handle mouse
        @Override
        public void mousePressed(final MouseEvent e) {
            // Do nothing
        }

        @Override
        public void mouseReleased(final MouseEvent e) {
            // Do nothing
        }

        @Override
        public void mouseClicked(final MouseEvent e) {
            try {
                final GameLogic gm = DungeonDiver3.getApplication()
                        .getGameManager();
                if (e.isShiftDown()) {
                    final int x = e.getX();
                    final int y = e.getY();
                    gm.identifyObject(x, y);
                }
            } catch (final Exception ex) {
                DungeonDiver3.getErrorLogger().logError(ex);
            }
        }

        @Override
        public void mouseEntered(final MouseEvent e) {
            // Do nothing
        }

        @Override
        public void mouseExited(final MouseEvent e) {
            // Do nothing
        }
    }
}
