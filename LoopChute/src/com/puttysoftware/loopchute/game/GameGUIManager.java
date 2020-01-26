/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.loopchute.Application;
import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.MazeObject;
import com.puttysoftware.loopchute.maze.Maze;
import com.puttysoftware.loopchute.maze.MazeConstants;
import com.puttysoftware.loopchute.objects.EmptyVoid;
import com.puttysoftware.loopchute.objects.Player;
import com.puttysoftware.loopchute.prefs.PreferencesManager;
import com.puttysoftware.loopchute.resourcemanagers.GraphicsManager;
import com.puttysoftware.loopchute.resourcemanagers.MusicManager;

class GameGUIManager {
    // Fields
    private JFrame outputFrame;
    private Container outputPane, borderPane, progressPane;
    private JLabel messageLabel;
    private JProgressBar autoFinishProgress, alternateAutoFinishProgress;
    private GameViewingWindowManager vwMgr;
    private JLabel[][] drawGrid;
    private boolean knm;
    private boolean deferredRedraw;

    // Constructors
    public GameGUIManager() {
        this.deferredRedraw = false;
    }

    // Methods
    void initViewManager() {
        if (this.vwMgr == null) {
            this.vwMgr = LoopChute.getApplication().getGameManager()
                    .getViewManager();
            this.setUpGUI();
        }
    }

    void viewingWindowSizeChanged() {
        this.initViewManager();
        this.partialSetUpGUI();
        this.deferredRedraw = true;
    }

    public JFrame getOutputFrame() {
        return this.outputFrame;
    }

    public void showOutput() {
        final Application app = LoopChute.getApplication();
        app.getMenuManager().setGameMenus();
        if (PreferencesManager
                .getMusicEnabled(PreferencesManager.MUSIC_EXPLORING)) {
            if (!MusicManager.isMusicPlaying()) {
                MusicManager.playMusic("exploring");
            }
        }
        this.outputFrame.setVisible(true);
        this.outputFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
        if (this.deferredRedraw) {
            this.deferredRedraw = false;
            this.redrawMaze();
        }
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

    public void setStatusMessage(final String msg) {
        this.messageLabel.setText(msg);
    }

    void updateAutoFinishProgress(final int progress) {
        this.autoFinishProgress.setValue(progress);
        this.autoFinishProgress
                .setString((int) ((double) this.autoFinishProgress.getValue()
                        / (double) this.autoFinishProgress.getMaximum() * 100.0)
                        + "%");
    }

    void updateAlternateAutoFinishProgress(final int progress) {
        this.alternateAutoFinishProgress.setValue(progress);
        this.alternateAutoFinishProgress.setString(
                (int) ((double) this.alternateAutoFinishProgress.getValue()
                        / (double) this.alternateAutoFinishProgress.getMaximum()
                        * 100.0) + "%");
    }

    void setAutoFinishMax(final int max) {
        this.autoFinishProgress.setMaximum(max);
    }

    void setAlternateAutoFinishMax(final int max) {
        this.alternateAutoFinishProgress.setMaximum(max);
    }

    void resetBorderPane() {
        this.borderPane.removeAll();
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        this.borderPane.add(this.progressPane, BorderLayout.WEST);
    }

    public void redrawMaze() {
        // Draw the maze, if it is visible
        if (this.outputFrame.isVisible()) {
            // Rebuild draw grid
            final EmptyBorder eb = new EmptyBorder(0, 0, 0, 0);
            this.outputPane.removeAll();
            for (int x = 0; x < this.vwMgr.getViewingWindowSizeX(); x++) {
                for (int y = 0; y < this.vwMgr.getViewingWindowSizeY(); y++) {
                    this.drawGrid[x][y] = new JLabel();
                    // Fix to make draw grid line up properly
                    this.drawGrid[x][y].setBorder(eb);
                    this.outputPane.add(this.drawGrid[x][y]);
                }
            }
            this.redrawMazeNoRebuild();
        }
    }

    public void redrawMazeNoRebuild() {
        // Draw the maze, if it is visible
        if (this.outputFrame.isVisible()) {
            final Application app = LoopChute.getApplication();
            final Maze m = app.getMazeManager().getMaze();
            int x, y, u, v;
            int xFix, yFix;
            boolean visible;
            u = m.getPlayerLocationX();
            v = m.getPlayerLocationY();
            for (x = this.vwMgr.getViewingWindowLocationX(); x <= this.vwMgr
                    .getLowerRightViewingWindowLocationX(); x++) {
                for (y = this.vwMgr.getViewingWindowLocationY(); y <= this.vwMgr
                        .getLowerRightViewingWindowLocationY(); y++) {
                    xFix = x - this.vwMgr.getViewingWindowLocationX();
                    yFix = y - this.vwMgr.getViewingWindowLocationY();
                    visible = app.getMazeManager().getMaze().isSquareVisible(u,
                            v, y, x);
                    try {
                        if (visible) {
                            final MazeObject obj1 = app.getMazeManager()
                                    .getMaze().getCell(y, x,
                                            m.getPlayerLocationZ(),
                                            MazeConstants.LAYER_GROUND);
                            final MazeObject obj2 = app.getMazeManager()
                                    .getMaze().getCell(y, x,
                                            m.getPlayerLocationZ(),
                                            MazeConstants.LAYER_OBJECT);
                            final BufferedImageIcon img1 = GraphicsManager
                                    .getImage(
                                            obj1.gameRenderHook(y, x,
                                                    m.getPlayerLocationZ()),
                                            obj1.getGameBaseName(),
                                            obj1.getGameTemplateColor(),
                                            obj1.getGameAttributeName(),
                                            obj1.getGameAttributeTemplateColor());
                            final BufferedImageIcon img2 = GraphicsManager
                                    .getImage(
                                            obj2.gameRenderHook(y, x,
                                                    m.getPlayerLocationZ()),
                                            obj2.getGameBaseName(),
                                            obj2.getGameTemplateColor(),
                                            obj2.getGameAttributeName(),
                                            obj2.getGameAttributeTemplateColor());
                            if (u == y && v == x) {
                                final MazeObject obj3 = new Player();
                                final BufferedImageIcon img3 = GraphicsManager
                                        .getImage(
                                                obj3.gameRenderHook(y, x,
                                                        m.getPlayerLocationZ()),
                                                obj3.getGameBaseName(),
                                                obj3.getGameTemplateColor(),
                                                obj3.getGameAttributeName(),
                                                obj3.getGameAttributeTemplateColor());
                                this.drawGrid[xFix][yFix]
                                        .setIcon(GraphicsManager
                                                .getVirtualCompositeImage(img1,
                                                        img2, img3));
                            } else {
                                this.drawGrid[xFix][yFix]
                                        .setIcon(GraphicsManager
                                                .getCompositeImage(img1, img2));
                            }
                        } else {
                            this.drawGrid[xFix][yFix].setIcon(GraphicsManager
                                    .getImage("Darkness", "Darkness",
                                            ColorConstants.COLOR_NONE, "",
                                            ColorConstants.COLOR_NONE));
                        }
                    } catch (final ArrayIndexOutOfBoundsException ae) {
                        final EmptyVoid ev = new EmptyVoid();
                        this.drawGrid[xFix][yFix]
                                .setIcon(GraphicsManager.getImage(
                                        ev.gameRenderHook(y, x,
                                                m.getPlayerLocationZ()),
                                        ev.getGameBaseName(),
                                        ev.getGameTemplateColor(),
                                        ev.getGameAttributeName(),
                                        ev.getGameAttributeTemplateColor()));
                    } catch (final NullPointerException np) {
                        final EmptyVoid ev = new EmptyVoid();
                        this.drawGrid[xFix][yFix]
                                .setIcon(GraphicsManager.getImage(
                                        ev.gameRenderHook(y, x,
                                                m.getPlayerLocationZ()),
                                        ev.getGameBaseName(),
                                        ev.getGameTemplateColor(),
                                        ev.getGameAttributeName(),
                                        ev.getGameAttributeTemplateColor()));
                    }
                }
            }
            if (this.knm) {
                this.knm = false;
            } else {
                this.setStatusMessage(" ");
            }
            this.outputFrame.pack();
        }
    }

    void redrawOneSquare(final int x, final int y, final boolean useDelay,
            final MazeObject obj3) {
        // Draw the square, if the maze is visible
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        if (this.outputFrame.isVisible()) {
            int xFix, yFix;
            boolean visible;
            xFix = y - this.vwMgr.getViewingWindowLocationX();
            yFix = x - this.vwMgr.getViewingWindowLocationY();
            visible = app.getMazeManager().getMaze().isSquareVisible(
                    m.getPlayerLocationX(), m.getPlayerLocationY(), x, y);
            try {
                if (visible) {
                    MazeObject obj1, obj2;
                    obj1 = m.getCell(x, y, m.getPlayerLocationZ(),
                            MazeConstants.LAYER_GROUND);
                    obj2 = m.getCell(x, y, m.getPlayerLocationZ(),
                            MazeConstants.LAYER_OBJECT);
                    final BufferedImageIcon img1 = GraphicsManager.getImage(
                            obj1.gameRenderHook(y, x, m.getPlayerLocationZ()),
                            obj1.getGameBaseName(), obj1.getGameTemplateColor(),
                            obj1.getGameAttributeName(),
                            obj1.getGameAttributeTemplateColor());
                    final BufferedImageIcon img2 = GraphicsManager.getImage(
                            obj2.gameRenderHook(y, x, m.getPlayerLocationZ()),
                            obj2.getGameBaseName(), obj2.getGameTemplateColor(),
                            obj2.getGameAttributeName(),
                            obj2.getGameAttributeTemplateColor());
                    final BufferedImageIcon img3 = GraphicsManager.getImage(
                            obj3.gameRenderHook(y, x, m.getPlayerLocationZ()),
                            obj3.getGameBaseName(), obj3.getGameTemplateColor(),
                            obj3.getGameAttributeName(),
                            obj3.getGameAttributeTemplateColor());
                    this.drawGrid[xFix][yFix].setIcon(GraphicsManager
                            .getVirtualCompositeImage(img1, img2, img3));
                } else {
                    this.drawGrid[xFix][yFix].setIcon(GraphicsManager.getImage(
                            "Darkness", "Darkness", ColorConstants.COLOR_NONE,
                            "", ColorConstants.COLOR_NONE));
                }
                this.drawGrid[xFix][yFix].repaint();
            } catch (final ArrayIndexOutOfBoundsException ae) {
                // Do nothing
            } catch (final NullPointerException np) {
                // Do nothing
            }
            this.outputFrame.pack();
            if (useDelay) {
                // Delay, for animation purposes
                try {
                    Thread.sleep(60);
                } catch (final InterruptedException ie) {
                    // Ignore
                }
            }
        }
    }

    public void keepNextMessage() {
        this.knm = true;
    }

    private void setUpGUI() {
        final EventHandler handler = new EventHandler();
        this.borderPane = new Container();
        this.borderPane.setLayout(new BorderLayout());
        this.progressPane = new Container();
        this.progressPane
                .setLayout(new BoxLayout(this.progressPane, BoxLayout.Y_AXIS));
        this.autoFinishProgress = new JProgressBar(SwingConstants.VERTICAL);
        this.autoFinishProgress.setStringPainted(true);
        this.alternateAutoFinishProgress = new JProgressBar(
                SwingConstants.VERTICAL);
        this.alternateAutoFinishProgress.setStringPainted(true);
        this.progressPane.add(this.autoFinishProgress);
        this.progressPane.add(this.alternateAutoFinishProgress);
        this.messageLabel = new JLabel(" ");
        this.messageLabel.setOpaque(true);
        this.outputFrame = new JFrame("loopchute");
        final Image iconlogo = LoopChute.getApplication().getIconLogo();
        this.outputFrame.setIconImage(iconlogo);
        this.outputPane = new Container();
        this.outputFrame.setContentPane(this.borderPane);
        this.outputFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.outputPane
                .setLayout(new GridLayout(this.vwMgr.getViewingWindowSizeX(),
                        this.vwMgr.getViewingWindowSizeY()));
        this.outputFrame.setResizable(false);
        this.outputFrame.addKeyListener(handler);
        this.outputFrame.addWindowListener(handler);
        this.outputPane.addMouseListener(handler);
        this.drawGrid = new JLabel[this.vwMgr
                .getViewingWindowSizeX()][this.vwMgr.getViewingWindowSizeY()];
        for (int x = 0; x < this.vwMgr.getViewingWindowSizeX(); x++) {
            for (int y = 0; y < this.vwMgr.getViewingWindowSizeY(); y++) {
                this.drawGrid[x][y] = new JLabel();
                // Mac OS X-specific fix to make draw grid line up properly
                if (System.getProperty("os.name").startsWith("Mac OS X")) {
                    this.drawGrid[x][y].setBorder(new EmptyBorder(0, 0, 0, 0));
                }
                this.outputPane.add(this.drawGrid[x][y]);
            }
        }
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        this.borderPane.add(this.progressPane, BorderLayout.WEST);
    }

    private void partialSetUpGUI() {
        final EventHandler handler = new EventHandler();
        this.borderPane = new Container();
        this.borderPane.setLayout(new BorderLayout());
        this.messageLabel = new JLabel(" ");
        this.messageLabel.setOpaque(true);
        this.outputFrame = new JFrame("loopchute");
        final Image iconlogo = LoopChute.getApplication().getIconLogo();
        this.outputFrame.setIconImage(iconlogo);
        this.outputPane = new Container();
        this.outputFrame.setContentPane(this.borderPane);
        this.outputFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.outputPane
                .setLayout(new GridLayout(this.vwMgr.getViewingWindowSizeX(),
                        this.vwMgr.getViewingWindowSizeY()));
        this.outputFrame.setResizable(false);
        this.outputFrame.addKeyListener(handler);
        this.outputFrame.addWindowListener(handler);
        this.outputPane.addMouseListener(handler);
        this.drawGrid = new JLabel[this.vwMgr
                .getViewingWindowSizeX()][this.vwMgr.getViewingWindowSizeY()];
        for (int x = 0; x < this.vwMgr.getViewingWindowSizeX(); x++) {
            for (int y = 0; y < this.vwMgr.getViewingWindowSizeY(); y++) {
                this.drawGrid[x][y] = new JLabel();
                // Mac OS X-specific fix to make draw grid line up properly
                if (System.getProperty("os.name").startsWith("Mac OS X")) {
                    this.drawGrid[x][y].setBorder(new EmptyBorder(0, 0, 0, 0));
                }
                this.outputPane.add(this.drawGrid[x][y]);
            }
        }
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        this.borderPane.add(this.progressPane, BorderLayout.WEST);
    }

    private class EventHandler
            implements KeyListener, WindowListener, MouseListener {
        EventHandler() {
            // Do nothing
        }

        @Override
        public void keyPressed(final KeyEvent e) {
            if (!PreferencesManager.oneMove()) {
                if (e.isAltDown()) {
                    this.handleArrows(e);
                } else {
                    this.handleMovement(e);
                }
            }
        }

        @Override
        public void keyReleased(final KeyEvent e) {
            if (PreferencesManager.oneMove()) {
                if (e.isAltDown()) {
                    this.handleArrows(e);
                } else {
                    this.handleMovement(e);
                }
            }
        }

        @Override
        public void keyTyped(final KeyEvent e) {
            // Do nothing
        }

        public void handleMovement(final KeyEvent e) {
            try {
                final GameLogicManager glm = LoopChute.getApplication()
                        .getGameManager();
                final int keyCode = e.getKeyCode();
                if (e.isShiftDown()) {
                    glm.setPullInProgress(true);
                }
                switch (keyCode) {
                case KeyEvent.VK_NUMPAD4:
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.updatePositionRelative(-1, 0);
                    }
                    break;
                case KeyEvent.VK_NUMPAD2:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_X:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.updatePositionRelative(0, 1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD6:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.updatePositionRelative(1, 0);
                    }
                    break;
                case KeyEvent.VK_NUMPAD8:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.updatePositionRelative(0, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD7:
                case KeyEvent.VK_Q:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.updatePositionRelative(-1, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD9:
                case KeyEvent.VK_E:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.updatePositionRelative(1, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD3:
                case KeyEvent.VK_C:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.updatePositionRelative(1, 1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD1:
                case KeyEvent.VK_Z:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.updatePositionRelative(-1, 1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD5:
                case KeyEvent.VK_S:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.updatePositionRelative(0, 0);
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    if (glm.isTeleporting()) {
                        glm.setTeleporting(false);
                        LoopChute.getApplication().showMessage(" ");
                    }
                    if (glm.usingAnItem()) {
                        glm.setUsingAnItem(false);
                        LoopChute.getApplication().showMessage(" ");
                    }
                    break;
                default:
                    break;
                }
                if (glm.isPullInProgress()) {
                    glm.setPullInProgress(false);
                }
            } catch (final Exception ex) {
                LoopChute.getErrorLogger().logError(ex);
            }
        }

        public void handleArrows(final KeyEvent e) {
            try {
                final GameLogicManager glm = LoopChute.getApplication()
                        .getGameManager();
                final int keyCode = e.getKeyCode();
                if (e.isShiftDown()) {
                    glm.setPullInProgress(true);
                }
                switch (keyCode) {
                case KeyEvent.VK_NUMPAD4:
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.fireArrow(-1, 0);
                    }
                    break;
                case KeyEvent.VK_NUMPAD2:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_X:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.fireArrow(0, 1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD6:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.fireArrow(1, 0);
                    }
                    break;
                case KeyEvent.VK_NUMPAD8:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.fireArrow(0, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD7:
                case KeyEvent.VK_Q:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.fireArrow(-1, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD9:
                case KeyEvent.VK_E:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.fireArrow(1, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD3:
                case KeyEvent.VK_C:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.fireArrow(1, 1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD1:
                case KeyEvent.VK_Z:
                    if (!glm.usingAnItem() && !glm.isTeleporting()) {
                        glm.fireArrow(-1, 1);
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    if (glm.usingAnItem()) {
                        glm.setUsingAnItem(false);
                        LoopChute.getApplication().showMessage(" ");
                    } else if (glm.isTeleporting()) {
                        glm.setTeleporting(false);
                        LoopChute.getApplication().showMessage(" ");
                    }
                    break;
                default:
                    break;
                }
                if (glm.isPullInProgress()) {
                    glm.setPullInProgress(false);
                }
            } catch (final Exception ex) {
                LoopChute.getErrorLogger().logError(ex);
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
                final Application app = LoopChute.getApplication();
                boolean success = false;
                int status = 0;
                if (app.getMazeManager().getDirty()) {
                    status = app.getMazeManager().showSaveDialog();
                    if (status == JOptionPane.YES_OPTION) {
                        success = app.getMazeManager().saveMaze();
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
                LoopChute.getErrorLogger().logError(ex);
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
                final GameLogicManager gm = LoopChute.getApplication()
                        .getGameManager();
                if (e.isShiftDown()) {
                    final int x = e.getX();
                    final int y = e.getY();
                    gm.identifyObject(x, y);
                } else if (gm.usingAnItem()) {
                    final int x = e.getX();
                    final int y = e.getY();
                    gm.useItemHandler(x, y);
                    gm.setUsingAnItem(false);
                } else if (gm.isTeleporting()) {
                    final int x = e.getX();
                    final int y = e.getY();
                    gm.controllableTeleportHandler(x, y);
                }
            } catch (final Exception ex) {
                LoopChute.getErrorLogger().logError(ex);
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
