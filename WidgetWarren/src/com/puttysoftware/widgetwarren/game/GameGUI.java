/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.game;

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
import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.generic.MazeObject;
import com.puttysoftware.widgetwarren.maze.MazeConstants;
import com.puttysoftware.widgetwarren.maze.effects.MazeEffectManager;
import com.puttysoftware.widgetwarren.objects.Darkness;
import com.puttysoftware.widgetwarren.objects.EmptyVoid;
import com.puttysoftware.widgetwarren.prefs.PreferencesManager;
import com.puttysoftware.widgetwarren.resourcemanagers.ImageModifier;
import com.puttysoftware.widgetwarren.resourcemanagers.LogoManager;
import com.puttysoftware.widgetwarren.resourcemanagers.ObjectImageManager;

class GameGUI {
    // Fields
    private JFrame outputFrame;
    private Container outputPane, borderPane, progressPane;
    private JLabel messageLabel;
    private JProgressBar autoFinishProgress, alternateAutoFinishProgress;
    private EventHandler handler;
    private final GameViewingWindowManager vwMgr;
    private final StatGUI sg;
    private boolean knm;
    private boolean trueSightFlag;
    boolean arrowActive;
    private JLabel[][] drawGrid;

    // Constructors
    public GameGUI(final MazeEffectManager em) {
        this.vwMgr = new GameViewingWindowManager();
        this.sg = new StatGUI();
        this.knm = false;
        this.trueSightFlag = false;
        this.arrowActive = false;
        this.setUpGUI(em);
    }

    // Methods
    public boolean newGame() {
        return true;
    }

    private StatGUI getStatGUI() {
        return this.sg;
    }

    GameViewingWindowManager getViewManager() {
        return this.vwMgr;
    }

    void setArrowActive(final boolean value) {
        this.arrowActive = value;
    }

    public void enableTrueSight(final int u, final int v, final int z) {
        this.trueSightFlag = true;
        this.redrawMaze(u, v, z);
    }

    public void disableTrueSight(final int u, final int v, final int z) {
        this.trueSightFlag = false;
        this.redrawMaze(u, v, z);
    }

    void initAutoFinishProgress() {
        this.autoFinishProgress.setValue(0);
        this.autoFinishProgress.setString("0%");
    }

    void initAlternateAutoFinishProgress() {
        this.alternateAutoFinishProgress.setValue(0);
        this.alternateAutoFinishProgress.setString("0%");
    }

    void setMaxAutoFinishProgress(final int ssCount) {
        this.autoFinishProgress.setMaximum(ssCount);
    }

    void setMaxAlternateAutoFinishProgress(final int msCount) {
        this.alternateAutoFinishProgress.setMaximum(msCount);
    }

    void updateAutoFinishProgress(final int ssCount) {
        this.autoFinishProgress.setValue(ssCount);
        this.autoFinishProgress
                .setString((int) ((double) this.autoFinishProgress.getValue()
                        / (double) this.autoFinishProgress.getMaximum() * 100.0)
                        + "%");
    }

    void updateAlternateAutoFinishProgress(final int msCount) {
        this.alternateAutoFinishProgress.setValue(msCount);
        this.alternateAutoFinishProgress.setString(
                (int) ((double) this.alternateAutoFinishProgress.getValue()
                        / (double) this.alternateAutoFinishProgress.getMaximum()
                        * 100.0) + "%");
    }

    void updateImages() {
        this.getStatGUI().updateImages();
    }

    void updateStats() {
        this.getStatGUI().updateStats();
    }

    public void setStatusMessage(final String msg) {
        this.messageLabel.setText(msg);
    }

    public void rebuildGrid(final int u, final int v, final int z) {
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
            this.redrawMaze(u, v, z);
        }
    }

    public void redrawMaze(final int u, final int v, final int z) {
        // Draw the maze, if it is visible
        if (this.outputFrame.isVisible()) {
            final Application app = WidgetWarren.getApplication();
            int x, y;
            int xFix, yFix;
            boolean visible;
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
                            MazeObject obj1, obj2;
                            obj1 = app.getMazeManager().getMaze().getCell(y, x,
                                    z, MazeConstants.LAYER_GROUND);
                            final BufferedImageIcon icon1 = ObjectImageManager
                                    .getObjectImage(obj1, !this.trueSightFlag);
                            obj2 = app.getMazeManager().getMaze().getCell(y, x,
                                    z, MazeConstants.LAYER_OBJECT);
                            if (obj2.getSavedObject() != null) {
                                final BufferedImageIcon icon2 = ObjectImageManager
                                        .getObjectImage(obj2.getSavedObject(),
                                                !this.trueSightFlag);
                                final BufferedImageIcon icon3 = ObjectImageManager
                                        .getObjectImage(obj2,
                                                !this.trueSightFlag);
                                this.drawGrid[xFix][yFix].setIcon(
                                        ImageModifier.getCompositeImage(icon1,
                                                icon2, icon3));
                            } else {
                                final BufferedImageIcon icon2 = ObjectImageManager
                                        .getObjectImage(obj2,
                                                !this.trueSightFlag);
                                this.drawGrid[xFix][yFix].setIcon(ImageModifier
                                        .getCompositeImage(icon1, icon2));
                            }
                        } else {
                            this.drawGrid[xFix][yFix].setIcon(ObjectImageManager
                                    .getObjectImage(new Darkness(),
                                            !this.trueSightFlag));
                        }
                    } catch (final ArrayIndexOutOfBoundsException ae) {
                        this.drawGrid[xFix][yFix]
                                .setIcon(ObjectImageManager.getObjectImage(
                                        new EmptyVoid(), !this.trueSightFlag));
                    } catch (final NullPointerException np) {
                        this.drawGrid[xFix][yFix]
                                .setIcon(ObjectImageManager.getObjectImage(
                                        new EmptyVoid(), !this.trueSightFlag));
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
            final MazeObject obj3, final int u, final int v, final int z) {
        // Draw the square, if the maze is visible
        final Application app = WidgetWarren.getApplication();
        if (this.outputFrame.isVisible()) {
            int xFix, yFix;
            boolean visible;
            xFix = y - this.vwMgr.getViewingWindowLocationX();
            yFix = x - this.vwMgr.getViewingWindowLocationY();
            visible = app.getMazeManager().getMaze().isSquareVisible(u, v, x,
                    y);
            try {
                if (visible) {
                    MazeObject obj1, obj2;
                    obj1 = app.getMazeManager().getMaze().getCell(x, y, z,
                            MazeConstants.LAYER_GROUND);
                    final BufferedImageIcon icon1 = ObjectImageManager
                            .getObjectImage(obj1, !this.trueSightFlag);
                    obj2 = app.getMazeManager().getMaze().getCell(x, y, z,
                            MazeConstants.LAYER_OBJECT);
                    final BufferedImageIcon icon2 = ObjectImageManager
                            .getObjectImage(obj2, !this.trueSightFlag);
                    final BufferedImageIcon icon3 = ObjectImageManager
                            .getObjectImage(obj3, !this.trueSightFlag);
                    this.drawGrid[xFix][yFix].setIcon(ImageModifier
                            .getCompositeImage(icon1, icon2, icon3));
                } else {
                    this.drawGrid[xFix][yFix].setIcon(
                            ObjectImageManager.getObjectImage(new Darkness(),
                                    !this.trueSightFlag));
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

    public void resetViewingWindow(final int u, final int v) {
        this.vwMgr.setViewingWindowLocationX(v - this.vwMgr.getOffsetFactorX());
        this.vwMgr.setViewingWindowLocationY(u - this.vwMgr.getOffsetFactorY());
    }

    public JFrame getOutputFrame() {
        return this.outputFrame;
    }

    public void keepNextMessage() {
        this.knm = true;
    }

    void configureBorderPane(final MazeEffectManager em) {
        // Make sure message area is attached to the border pane
        this.borderPane.removeAll();
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        this.borderPane.add(this.getStatGUI().getStatsPane(),
                BorderLayout.EAST);
        this.borderPane.add(this.progressPane, BorderLayout.WEST);
        this.borderPane.add(em.getEffectMessageContainer(), BorderLayout.SOUTH);
    }

    public void showOutput() {
        final Application app = WidgetWarren.getApplication();
        app.getMenuManager().setGameMenus();
        this.outputFrame.setVisible(true);
        this.outputFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
    }

    public void hideOutput() {
        if (this.outputFrame != null) {
            this.outputFrame.setVisible(false);
        }
    }

    private void setUpGUI(final MazeEffectManager em) {
        this.handler = new EventHandler();
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
        this.outputFrame = new JFrame("Game");
        final Image iconlogo = LogoManager.getLogo();
        this.outputFrame.setIconImage(iconlogo);
        this.outputPane = new Container();
        this.outputFrame.setContentPane(this.borderPane);
        this.outputFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.outputPane
                .setLayout(new GridLayout(this.vwMgr.getViewingWindowSizeX(),
                        this.vwMgr.getViewingWindowSizeY()));
        this.outputFrame.setResizable(false);
        this.outputFrame.addKeyListener(this.handler);
        this.outputFrame.addWindowListener(this.handler);
        this.outputPane.addMouseListener(this.handler);
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
        this.borderPane.add(em.getEffectMessageContainer(), BorderLayout.SOUTH);
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
            if (!GameGUI.this.arrowActive) {
                if (!PreferencesManager.oneMove()) {
                    if (e.isAltDown()) {
                        this.handleArrows(e);
                    } else {
                        this.handleMovement(e);
                    }
                }
            }
        }

        @Override
        public void keyReleased(final KeyEvent e) {
            if (!GameGUI.this.arrowActive) {
                if (PreferencesManager.oneMove()) {
                    if (e.isAltDown()) {
                        this.handleArrows(e);
                    } else {
                        this.handleMovement(e);
                    }
                }
            }
        }

        @Override
        public void keyTyped(final KeyEvent e) {
            // Do nothing
        }

        public void handleMovement(final KeyEvent e) {
            try {
                final GameManager gm = WidgetWarren.getApplication()
                        .getGameManager();
                final int keyCode = e.getKeyCode();
                if (e.isShiftDown()) {
                    gm.setPullInProgress(true);
                }
                switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        if (e.isShiftDown()) {
                            gm.updatePositionRelative(-1, -1);
                        } else if (e.isMetaDown() || e.isControlDown()) {
                            gm.updatePositionRelative(-1, 1);
                        } else {
                            gm.updatePositionRelative(-1, 0);
                        }
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        if (e.isShiftDown()) {
                            gm.updatePositionRelative(1, 1);
                        } else if (e.isMetaDown() || e.isControlDown()) {
                            gm.updatePositionRelative(1, -1);
                        } else {
                            gm.updatePositionRelative(1, 0);
                        }
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        if (e.isShiftDown()) {
                            gm.updatePositionRelative(-1, -1);
                        } else if (e.isMetaDown() || e.isControlDown()) {
                            gm.updatePositionRelative(1, -1);
                        } else {
                            gm.updatePositionRelative(0, -1);
                        }
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        if (e.isShiftDown()) {
                            gm.updatePositionRelative(1, 1);
                        } else if (e.isMetaDown() || e.isControlDown()) {
                            gm.updatePositionRelative(-1, 1);
                        } else {
                            gm.updatePositionRelative(0, 1);
                        }
                    }
                    break;
                case KeyEvent.VK_NUMPAD4:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(-1, 0);
                    }
                    break;
                case KeyEvent.VK_NUMPAD2:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(0, 1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD6:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(1, 0);
                    }
                    break;
                case KeyEvent.VK_NUMPAD8:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(0, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD7:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(-1, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD9:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(1, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD3:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(1, 1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD1:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(-1, 1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD5:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(0, 0);
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    if (e.isShiftDown() && !gm.usingAnItem()
                            && !gm.isTeleporting()) {
                        gm.updatePositionRelative(0, 0);
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    if (gm.usingAnItem()) {
                        gm.setUsingAnItem(false);
                        WidgetWarren.getApplication().showMessage(" ");
                    } else if (gm.isTeleporting()) {
                        gm.setTeleportingOff();
                        WidgetWarren.getApplication().showMessage(" ");
                    }
                    break;
                default:
                    break;
                }
                if (gm.isPullInProgress()) {
                    gm.setPullInProgress(false);
                }
            } catch (final Exception ex) {
                WidgetWarren.getErrorLogger().logError(ex);
            }
        }

        public void handleArrows(final KeyEvent e) {
            try {
                final GameManager gm = WidgetWarren.getApplication()
                        .getGameManager();
                final int keyCode = e.getKeyCode();
                if (e.isShiftDown()) {
                    gm.setPullInProgress(true);
                }
                switch (keyCode) {
                case KeyEvent.VK_NUMPAD4:
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.fireArrow(-1, 0);
                    }
                    break;
                case KeyEvent.VK_NUMPAD2:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_X:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.fireArrow(0, 1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD6:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.fireArrow(1, 0);
                    }
                    break;
                case KeyEvent.VK_NUMPAD8:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.fireArrow(0, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD7:
                case KeyEvent.VK_Q:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.fireArrow(-1, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD9:
                case KeyEvent.VK_E:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.fireArrow(1, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD3:
                case KeyEvent.VK_C:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.fireArrow(1, 1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD1:
                case KeyEvent.VK_Z:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.fireArrow(-1, 1);
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    if (gm.usingAnItem()) {
                        gm.setUsingAnItem(false);
                        WidgetWarren.getApplication().showMessage(" ");
                    } else if (gm.isTeleporting()) {
                        gm.setTeleportingOff();
                        WidgetWarren.getApplication().showMessage(" ");
                    }
                    break;
                default:
                    break;
                }
                if (gm.isPullInProgress()) {
                    gm.setPullInProgress(false);
                }
            } catch (final Exception ex) {
                WidgetWarren.getErrorLogger().logError(ex);
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
                final Application app = WidgetWarren.getApplication();
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
                WidgetWarren.getErrorLogger().logError(ex);
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
                final GameManager gm = WidgetWarren.getApplication()
                        .getGameManager();
                if (gm.usingAnItem()) {
                    final int x = e.getX();
                    final int y = e.getY();
                    gm.useItemHandler(x, y);
                    gm.setUsingAnItem(false);
                } else if (e.isShiftDown()) {
                    final int x = e.getX();
                    final int y = e.getY();
                    gm.identifyObject(x, y);
                } else if (gm.isTeleporting()) {
                    final int x = e.getX();
                    final int y = e.getY();
                    gm.controllableTeleportHandler(x, y);
                }
            } catch (final Exception ex) {
                WidgetWarren.getErrorLogger().logError(ex);
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
