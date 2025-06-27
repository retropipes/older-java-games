/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.game;

import java.awt.BorderLayout;
import java.awt.Color;
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

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.DrawGrid;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.Maze;
import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.maze.effects.MazeEffectManager;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.maze.objects.Darkness;
import com.puttysoftware.mastermaze.maze.objects.EmptyVoid;
import com.puttysoftware.mastermaze.maze.objects.MazeNoteObject;
import com.puttysoftware.mastermaze.maze.objects.Player;
import com.puttysoftware.mastermaze.prefs.PreferencesManager;
import com.puttysoftware.mastermaze.resourcemanagers.ImageTransformer;
import com.puttysoftware.mastermaze.resourcemanagers.MusicManager;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageManager;

class GameGUIManager {
    // Fields
    private JFrame outputFrame;
    private Container borderPane, progressPane;
    private JLabel messageLabel;
    private JProgressBar autoFinishProgress, alternateAutoFinishProgress;
    private GameViewingWindowManager vwMgr;
    private final StatGUI sg;
    private DrawGrid drawGrid;
    private GameDraw outputPane;
    private boolean knm;
    private boolean deferredRedraw;
    private boolean trueSightFlag;
    private static Darkness DARK = new Darkness();
    private static MazeNoteObject NOTE = new MazeNoteObject();

    // Constructors
    public GameGUIManager() {
        this.deferredRedraw = false;
        this.trueSightFlag = false;
        this.sg = new StatGUI();
    }

    // Methods
    public void updateStats() {
        this.sg.updateStats();
    }

    public void enableTrueSight() {
        this.trueSightFlag = true;
        this.redrawMaze();
    }

    public void disableTrueSight() {
        this.trueSightFlag = false;
        this.redrawMaze();
    }

    void initViewManager() {
        if (this.vwMgr == null) {
            this.vwMgr = MasterMaze.getApplication().getGameManager()
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
        final Application app = MasterMaze.getApplication();
        app.getMenuManager().setGameMenus();
        if (PreferencesManager
                .getMusicEnabled(PreferencesManager.MUSIC_EXPLORING)) {
            MusicManager.playMusic("exploring");
        }
        this.outputFrame.setVisible(true);
        this.outputFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
        if (this.deferredRedraw) {
            this.deferredRedraw = false;
            this.redrawMaze();
        }
        this.updateStats();
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

    private void resetBorderPane(final MazeEffectManager em) {
        this.borderPane.removeAll();
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        this.borderPane.add(this.progressPane, BorderLayout.WEST);
        this.borderPane.add(this.sg.getStatsPane(), BorderLayout.EAST);
        this.borderPane.add(em.getEffectMessageContainer(), BorderLayout.SOUTH);
    }

    public void redrawMaze() {
        // Draw the maze, if it is visible
        if (this.outputFrame.isVisible()) {
            final Application app = MasterMaze.getApplication();
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
                            if (this.trueSightFlag) {
                                final MazeObject obj1 = m
                                        .getCell(y, x, m.getPlayerLocationZ(),
                                                MazeConstants.LAYER_GROUND)
                                        .editorRenderHook(y, x,
                                                m.getPlayerLocationZ());
                                final MazeObject obj2 = m
                                        .getCell(y, x, m.getPlayerLocationZ(),
                                                MazeConstants.LAYER_OBJECT)
                                        .editorRenderHook(y, x,
                                                m.getPlayerLocationZ());
                                final BufferedImageIcon img1 = ObjectImageManager
                                        .getImage(obj1.getName(),
                                                obj1.getBaseID(),
                                                obj1.getTemplateColor(),
                                                obj1.getAttributeID(),
                                                obj1.getAttributeTemplateColor());
                                final BufferedImageIcon img2 = ObjectImageManager
                                        .getImage(obj2.getName(),
                                                obj2.getBaseID(),
                                                obj2.getTemplateColor(),
                                                obj2.getAttributeID(),
                                                obj2.getAttributeTemplateColor());
                                if (u == y && v == x) {
                                    final MazeObject obj3 = new Player()
                                            .editorRenderHook(y, x,
                                                    m.getPlayerLocationZ());
                                    final BufferedImageIcon img3 = ObjectImageManager
                                            .getImage(obj3.getName(),
                                                    obj3.getBaseID(),
                                                    obj3.getTemplateColor(),
                                                    obj3.getAttributeID(),
                                                    obj3.getAttributeTemplateColor());
                                    this.drawGrid.setImageCell(
                                            ImageTransformer
                                                    .getVirtualCompositeImage(
                                                            img1, img2, img3),
                                            xFix, yFix);
                                } else if (m.hasNote(x, y,
                                        m.getPlayerLocationZ())) {
                                    final MazeObject obj3 = GameGUIManager.NOTE
                                            .gameRenderHook(y, x,
                                                    m.getPlayerLocationZ());
                                    final BufferedImageIcon img3 = ObjectImageManager
                                            .getImage(obj3.getName(),
                                                    obj3.getGameBaseID(),
                                                    obj3.getGameTemplateColor(),
                                                    obj3.getGameAttributeID(),
                                                    obj3.getGameAttributeTemplateColor());
                                    this.drawGrid.setImageCell(
                                            ImageTransformer
                                                    .getVirtualCompositeImage(
                                                            img1, img2, img3),
                                            xFix, yFix);
                                } else {
                                    this.drawGrid.setImageCell(ImageTransformer
                                            .getCompositeImage(img1, img2),
                                            xFix, yFix);
                                }
                            } else {
                                final MazeObject obj1 = m
                                        .getCell(y, x, m.getPlayerLocationZ(),
                                                MazeConstants.LAYER_GROUND)
                                        .gameRenderHook(y, x,
                                                m.getPlayerLocationZ());
                                final MazeObject obj2 = m
                                        .getCell(y, x, m.getPlayerLocationZ(),
                                                MazeConstants.LAYER_OBJECT)
                                        .gameRenderHook(y, x,
                                                m.getPlayerLocationZ());
                                final BufferedImageIcon img1 = ObjectImageManager
                                        .getImage(obj1.getName(),
                                                obj1.getGameBaseID(),
                                                obj1.getGameTemplateColor(),
                                                obj1.getGameAttributeID(),
                                                obj1.getGameAttributeTemplateColor());
                                final BufferedImageIcon img2 = ObjectImageManager
                                        .getImage(obj2.getName(),
                                                obj2.getGameBaseID(),
                                                obj2.getGameTemplateColor(),
                                                obj2.getGameAttributeID(),
                                                obj2.getGameAttributeTemplateColor());
                                if (u == y && v == x) {
                                    final MazeObject obj3 = new Player()
                                            .gameRenderHook(y, x,
                                                    m.getPlayerLocationZ());
                                    final BufferedImageIcon img3 = ObjectImageManager
                                            .getImage(obj3.getName(),
                                                    obj3.getGameBaseID(),
                                                    obj3.getGameTemplateColor(),
                                                    obj3.getGameAttributeID(),
                                                    obj3.getGameAttributeTemplateColor());
                                    this.drawGrid.setImageCell(
                                            ImageTransformer
                                                    .getVirtualCompositeImage(
                                                            img1, img2, img3),
                                            xFix, yFix);
                                } else if (m.hasNote(x, y,
                                        m.getPlayerLocationZ())) {
                                    final MazeObject obj3 = GameGUIManager.NOTE
                                            .gameRenderHook(y, x,
                                                    m.getPlayerLocationZ());
                                    final BufferedImageIcon img3 = ObjectImageManager
                                            .getImage(obj3.getName(),
                                                    obj3.getGameBaseID(),
                                                    obj3.getGameTemplateColor(),
                                                    obj3.getGameAttributeID(),
                                                    obj3.getGameAttributeTemplateColor());
                                    this.drawGrid.setImageCell(
                                            ImageTransformer
                                                    .getVirtualCompositeImage(
                                                            img1, img2, img3),
                                            xFix, yFix);
                                } else {
                                    this.drawGrid.setImageCell(ImageTransformer
                                            .getCompositeImage(img1, img2),
                                            xFix, yFix);
                                }
                            }
                        } else {
                            this.drawGrid.setImageCell(
                                    ObjectImageManager.getImage(
                                            GameGUIManager.DARK.getName(),
                                            GameGUIManager.DARK.getBaseID(),
                                            ColorConstants.COLOR_NONE,
                                            GameGUIManager.DARK
                                                    .getAttributeID(),
                                            ColorConstants.COLOR_NONE),
                                    xFix, yFix);
                        }
                    } catch (final ArrayIndexOutOfBoundsException ae) {
                        final MazeObject ev = new EmptyVoid().gameRenderHook(y,
                                x, m.getPlayerLocationZ());
                        this.drawGrid.setImageCell(
                                ObjectImageManager.getImage(ev.getName(),
                                        ev.getGameBaseID(),
                                        ev.getGameTemplateColor(),
                                        ev.getGameAttributeID(),
                                        ev.getGameAttributeTemplateColor()),
                                xFix, yFix);
                    } catch (final NullPointerException np) {
                        final MazeObject ev = new EmptyVoid().gameRenderHook(y,
                                x, m.getPlayerLocationZ());
                        this.drawGrid.setImageCell(
                                ObjectImageManager.getImage(ev.getName(),
                                        ev.getGameBaseID(),
                                        ev.getGameTemplateColor(),
                                        ev.getGameAttributeID(),
                                        ev.getGameAttributeTemplateColor()),
                                xFix, yFix);
                    }
                }
            }
            if (this.knm) {
                this.knm = false;
            } else {
                this.setStatusMessage(" ");
            }
            this.outputPane.repaint();
            this.outputFrame.pack();
        }
    }

    void redrawOneSquare(final int x, final int y, final boolean useDelay,
            final MazeObject obj4) {
        // Draw the maze, if it is visible
        if (this.outputFrame.isVisible()) {
            final Application app = MasterMaze.getApplication();
            final Maze m = app.getMazeManager().getMaze();
            int u, v;
            int xFix, yFix;
            boolean visible;
            boolean onScreen;
            u = m.getPlayerLocationX();
            v = m.getPlayerLocationY();
            xFix = x - this.vwMgr.getViewingWindowLocationX();
            yFix = y - this.vwMgr.getViewingWindowLocationY();
            visible = app.getMazeManager().getMaze().isSquareVisible(u, v, y,
                    x);
            onScreen = xFix >= 0 && xFix < this.vwMgr.getViewingWindowSizeX()
                    && yFix >= 0 && yFix < this.vwMgr.getViewingWindowSizeY();
            try {
                if (visible) {
                    if (this.trueSightFlag) {
                        final MazeObject obj1 = app.getMazeManager().getMaze()
                                .getCell(y, x, m.getPlayerLocationZ(),
                                        MazeConstants.LAYER_GROUND)
                                .editorRenderHook(y, x, m.getPlayerLocationZ());
                        final MazeObject obj2 = app.getMazeManager().getMaze()
                                .getCell(y, x, m.getPlayerLocationZ(),
                                        MazeConstants.LAYER_OBJECT)
                                .editorRenderHook(y, x, m.getPlayerLocationZ());
                        final BufferedImageIcon img1 = ObjectImageManager
                                .getImage(obj1.getName(), obj1.getBaseID(),
                                        obj1.getTemplateColor(),
                                        obj1.getAttributeID(),
                                        obj1.getAttributeTemplateColor());
                        final BufferedImageIcon img2 = ObjectImageManager
                                .getImage(obj2.getName(), obj2.getBaseID(),
                                        obj2.getTemplateColor(),
                                        obj2.getAttributeID(),
                                        obj2.getAttributeTemplateColor());
                        if (u == y && v == x) {
                            final MazeObject obj3 = new Player()
                                    .editorRenderHook(y, x,
                                            m.getPlayerLocationZ());
                            final BufferedImageIcon img3 = ObjectImageManager
                                    .getImage(obj3.getName(), obj3.getBaseID(),
                                            obj3.getTemplateColor(),
                                            obj3.getAttributeID(),
                                            obj3.getAttributeTemplateColor());
                            this.drawGrid.setImageCell(ImageTransformer
                                    .getVirtualCompositeImage(img1, img2, img3),
                                    xFix, yFix);
                        } else {
                            this.drawGrid.setImageCell(ImageTransformer
                                    .getCompositeImage(img1, img2), xFix, yFix);
                        }
                    } else {
                        final MazeObject obj1 = app.getMazeManager().getMaze()
                                .getCell(y, x, m.getPlayerLocationZ(),
                                        MazeConstants.LAYER_GROUND)
                                .gameRenderHook(y, x, m.getPlayerLocationZ());
                        final MazeObject obj2 = app.getMazeManager().getMaze()
                                .getCell(y, x, m.getPlayerLocationZ(),
                                        MazeConstants.LAYER_OBJECT)
                                .gameRenderHook(y, x, m.getPlayerLocationZ());
                        final BufferedImageIcon img1 = ObjectImageManager
                                .getImage(obj1.getName(), obj1.getGameBaseID(),
                                        obj1.getGameTemplateColor(),
                                        obj1.getGameAttributeID(),
                                        obj1.getGameAttributeTemplateColor());
                        final BufferedImageIcon img2 = ObjectImageManager
                                .getImage(obj2.getName(), obj2.getGameBaseID(),
                                        obj2.getGameTemplateColor(),
                                        obj2.getGameAttributeID(),
                                        obj2.getGameAttributeTemplateColor());
                        if (u == y && v == x) {
                            final MazeObject obj3 = new Player().gameRenderHook(
                                    y, x, m.getPlayerLocationZ());
                            final BufferedImageIcon img3 = ObjectImageManager
                                    .getImage(obj3.getName(),
                                            obj3.getGameBaseID(),
                                            obj3.getGameTemplateColor(),
                                            obj3.getGameAttributeID(),
                                            obj3.getGameAttributeTemplateColor());
                            this.drawGrid.setImageCell(ImageTransformer
                                    .getVirtualCompositeImage(img1, img2, img3),
                                    xFix, yFix);
                        } else {
                            final MazeObject obj5 = obj4.gameRenderHook(y, x,
                                    m.getPlayerLocationZ());
                            final BufferedImageIcon img4 = ObjectImageManager
                                    .getImage(obj5.getName(),
                                            obj5.getGameBaseID(),
                                            obj5.getGameTemplateColor(),
                                            obj5.getGameAttributeID(),
                                            obj5.getGameAttributeTemplateColor());
                            this.drawGrid.setImageCell(ImageTransformer
                                    .getVirtualCompositeImage(img1, img2, img4),
                                    xFix, yFix);
                        }
                    }
                } else {
                    this.drawGrid
                            .setImageCell(
                                    ObjectImageManager.getImage(
                                            GameGUIManager.DARK.getName(),
                                            GameGUIManager.DARK.getBaseID(),
                                            ColorConstants.COLOR_NONE,
                                            GameGUIManager.DARK
                                                    .getAttributeID(),
                                            ColorConstants.COLOR_NONE),
                                    xFix, yFix);
                }
            } catch (final ArrayIndexOutOfBoundsException ae) {
                if (onScreen) {
                    final MazeObject ev = new EmptyVoid().gameRenderHook(y, x,
                            m.getPlayerLocationZ());
                    this.drawGrid.setImageCell(ObjectImageManager.getImage(
                            ev.getName(), ev.getGameBaseID(),
                            ev.getGameTemplateColor(), ev.getGameAttributeID(),
                            ev.getGameAttributeTemplateColor()), xFix, yFix);
                }
            } catch (final NullPointerException np) {
                if (onScreen) {
                    final MazeObject ev = new EmptyVoid().gameRenderHook(y, x,
                            m.getPlayerLocationZ());
                    this.drawGrid.setImageCell(ObjectImageManager.getImage(
                            ev.getName(), ev.getGameBaseID(),
                            ev.getGameTemplateColor(), ev.getGameAttributeID(),
                            ev.getGameAttributeTemplateColor()), xFix, yFix);
                }
            }
            if (this.knm) {
                this.knm = false;
            } else {
                this.setStatusMessage(" ");
            }
            this.outputPane.repaint();
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

    void updateGameGUI(final MazeEffectManager em) {
        this.resetBorderPane(em);
        this.sg.updateImages();
        this.sg.updateStats();
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
        this.outputFrame = new JFrame("MasterMaze");
        final Image iconlogo = MasterMaze.getApplication().getIconLogo();
        this.outputFrame.setIconImage(iconlogo);
        this.drawGrid = new DrawGrid(PreferencesManager.getViewingWindowSize());
        this.outputPane = new GameDraw(this.drawGrid);
        this.outputFrame.setContentPane(this.borderPane);
        this.outputFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.outputFrame.setResizable(false);
        this.outputFrame.addKeyListener(handler);
        this.outputFrame.addWindowListener(handler);
        this.outputPane.addMouseListener(handler);
        this.outputFrame.setBackground(new Color(223, 223, 223));
    }

    private void partialSetUpGUI() {
        final EventHandler handler = new EventHandler();
        this.borderPane = new Container();
        this.borderPane.setLayout(new BorderLayout());
        this.messageLabel = new JLabel(" ");
        this.messageLabel.setOpaque(true);
        this.outputFrame = new JFrame("MasterMaze");
        final Image iconlogo = MasterMaze.getApplication().getIconLogo();
        this.outputFrame.setIconImage(iconlogo);
        this.drawGrid = new DrawGrid(PreferencesManager.getViewingWindowSize());
        this.outputPane = new GameDraw(this.drawGrid);
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
        this.outputFrame.setBackground(new Color(223, 223, 223));
    }

    private class EventHandler
            implements KeyListener, WindowListener, MouseListener {
        EventHandler() {
            // Do nothing
        }

        @Override
        public void keyPressed(final KeyEvent e) {
            final GameLogicManager glm = MasterMaze.getApplication()
                    .getGameManager();
            if (!glm.isArrowActive()) {
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
            final GameLogicManager glm = MasterMaze.getApplication()
                    .getGameManager();
            if (!glm.isArrowActive()) {
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
                final GameLogicManager glm = MasterMaze.getApplication()
                        .getGameManager();
                final int keyCode = e.getKeyCode();
                if (e.isShiftDown()) {
                    glm.setPullInProgress(true);
                }
                switch (keyCode) {
                    case KeyEvent.VK_NUMPAD4:
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        if (!glm.isTeleporting()) {
                            glm.updatePositionRelative(-1, 0, 0);
                        }
                        break;
                    case KeyEvent.VK_NUMPAD2:
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_X:
                        if (!glm.isTeleporting()) {
                            glm.updatePositionRelative(0, 1, 0);
                        }
                        break;
                    case KeyEvent.VK_NUMPAD6:
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        if (!glm.isTeleporting()) {
                            glm.updatePositionRelative(1, 0, 0);
                        }
                        break;
                    case KeyEvent.VK_NUMPAD8:
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        if (!glm.isTeleporting()) {
                            glm.updatePositionRelative(0, -1, 0);
                        }
                        break;
                    case KeyEvent.VK_NUMPAD7:
                    case KeyEvent.VK_Q:
                        if (!glm.isTeleporting()) {
                            glm.updatePositionRelative(-1, -1, 0);
                        }
                        break;
                    case KeyEvent.VK_NUMPAD9:
                    case KeyEvent.VK_E:
                        if (!glm.isTeleporting()) {
                            glm.updatePositionRelative(1, -1, 0);
                        }
                        break;
                    case KeyEvent.VK_NUMPAD3:
                    case KeyEvent.VK_C:
                        if (!glm.isTeleporting()) {
                            glm.updatePositionRelative(1, 1, 0);
                        }
                        break;
                    case KeyEvent.VK_NUMPAD1:
                    case KeyEvent.VK_Z:
                        if (!glm.isTeleporting()) {
                            glm.updatePositionRelative(-1, 1, 0);
                        }
                        break;
                    case KeyEvent.VK_NUMPAD5:
                    case KeyEvent.VK_S:
                        if (!glm.isTeleporting()) {
                            glm.updatePositionRelative(0, 0, 0);
                        }
                        break;
                    case KeyEvent.VK_ESCAPE:
                        if (glm.isTeleporting()) {
                            glm.setTeleporting(false);
                            MasterMaze.getApplication().showMessage(" ");
                        }
                        break;
                    default:
                        break;
                }
                if (glm.isPullInProgress()) {
                    glm.setPullInProgress(false);
                }
            } catch (final Exception ex) {
                MasterMaze.getErrorLogger().logError(ex);
            }
        }

        public void handleArrows(final KeyEvent e) {
            try {
                final GameLogicManager glm = MasterMaze.getApplication()
                        .getGameManager();
                final int keyCode = e.getKeyCode();
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
                    default:
                        break;
                }
            } catch (final Exception ex) {
                MasterMaze.getErrorLogger().logError(ex);
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
                final Application app = MasterMaze.getApplication();
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
                MasterMaze.getErrorLogger().logError(ex);
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
                final GameLogicManager gm = MasterMaze.getApplication()
                        .getGameManager();
                if (gm.usingAnItem()) {
                    final int x = e.getX();
                    final int y = e.getY();
                    gm.useItemHandler(x, y);
                }
                if (e.isShiftDown()) {
                    final int x = e.getX();
                    final int y = e.getY();
                    gm.identifyObject(x, y);
                }
                if (gm.isTeleporting()) {
                    final int x = e.getX();
                    final int y = e.getY();
                    gm.controllableTeleportHandler(x, y);
                }
            } catch (final Exception ex) {
                MasterMaze.getErrorLogger().logError(ex);
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
