/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.battle.map;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.DrawGrid;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.battle.AbstractBattle;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.objects.Darkness;
import com.puttysoftware.dungeondiver4.dungeon.objects.EmptyVoid;
import com.puttysoftware.dungeondiver4.prefs.PreferencesManager;
import com.puttysoftware.dungeondiver4.resourcemanagers.ImageTransformer;
import com.puttysoftware.dungeondiver4.resourcemanagers.LogoManager;
import com.puttysoftware.dungeondiver4.resourcemanagers.MusicManager;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageManager;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.platform.Platform;

class MapBattleGUI {
    // Fields
    private JFrame battleFrame;
    private MapBattleDraw battlePane;
    private JLabel messageLabel;
    private final MapBattleViewingWindowManager vwMgr;
    private final MapBattleStats bs;
    private final MapBattleEffects be;
    private DrawGrid drawGrid;
    boolean eventHandlersOn;

    // Constructors
    MapBattleGUI() {
        this.vwMgr = new MapBattleViewingWindowManager();
        this.bs = new MapBattleStats();
        this.be = new MapBattleEffects();
        this.setUpGUI();
        this.eventHandlersOn = true;
    }

    // Methods
    JFrame getOutputFrame() {
        return this.battleFrame;
    }

    MapBattleViewingWindowManager getViewManager() {
        return this.vwMgr;
    }

    void clearStatusMessage() {
        this.messageLabel.setText(" ");
    }

    void setStatusMessage(final String msg) {
        if (!msg.isEmpty() && !msg.matches("\\s+")) {
            this.messageLabel.setText(msg);
        }
    }

    void showBattle() {
        DungeonDiver4.getApplication().getGameManager().hideOutput();
        DungeonDiver4.getApplication().getMenuManager().setBattleMenus();
        if (PreferencesManager
                .getMusicEnabled(PreferencesManager.MUSIC_BATTLE)) {
            MusicManager.stopMusic();
            MusicManager.playMusic("battle");
        }
        this.battleFrame.setVisible(true);
        this.battleFrame.setJMenuBar(DungeonDiver4.getApplication()
                .getMenuManager().getMainMenuBar());
    }

    void hideBattle() {
        if (MusicManager.isMusicPlaying()) {
            MusicManager.stopMusic();
        }
        if (this.battleFrame != null) {
            this.battleFrame.setVisible(false);
        }
    }

    void redrawBattle(final MapBattleDefinitions bd) {
        // Draw the battle, if it is visible
        if (this.battleFrame.isVisible()) {
            int x, y;
            int xFix, yFix;
            final int xView = this.vwMgr.getViewingWindowLocationX();
            final int yView = this.vwMgr.getViewingWindowLocationY();
            final int xlView = this.vwMgr.getLowerRightViewingWindowLocationX();
            final int ylView = this.vwMgr.getLowerRightViewingWindowLocationY();
            for (x = xView; x <= xlView; x++) {
                for (y = yView; y <= ylView; y++) {
                    xFix = x - xView;
                    yFix = y - yView;
                    try {
                        final BufferedImageIcon icon1 = bd.getBattleDungeon()
                                .getBattleGround(y, x).battleRenderHook();
                        final BufferedImageIcon icon2 = bd.getBattleDungeon()
                                .getBattleCell(y, x).battleRenderHook();
                        this.drawGrid.setImageCell(ImageTransformer
                                .getCompositeImage(icon1, icon2), xFix, yFix);
                    } catch (final ArrayIndexOutOfBoundsException ae) {
                        final EmptyVoid ev = new EmptyVoid();
                        this.drawGrid.setImageCell(ev.battleRenderHook(), xFix,
                                yFix);
                    } catch (final NullPointerException np) {
                        final EmptyVoid ev = new EmptyVoid();
                        this.drawGrid.setImageCell(ev.battleRenderHook(), xFix,
                                yFix);
                    }
                }
            }
            this.battlePane.repaint();
            this.battleFrame.pack();
        }
    }

    void redrawOneBattleSquare(final MapBattleDefinitions bd, final int x,
            final int y, final AbstractDungeonObject obj3) {
        // Draw the battle, if it is visible
        if (this.battleFrame.isVisible()) {
            try {
                int xFix, yFix;
                final int xView = this.vwMgr.getViewingWindowLocationX();
                final int yView = this.vwMgr.getViewingWindowLocationY();
                xFix = y - xView;
                yFix = x - yView;
                final BufferedImageIcon icon1 = bd.getBattleDungeon()
                        .getBattleGround(x, y).battleRenderHook();
                final BufferedImageIcon icon2 = bd.getBattleDungeon()
                        .getBattleCell(x, y).battleRenderHook();
                final BufferedImageIcon icon3 = obj3.battleRenderHook();
                this.drawGrid.setImageCell(ImageTransformer
                        .getVirtualCompositeImage(icon1, icon2, icon3), xFix,
                        yFix);
                this.battlePane.repaint();
            } catch (final ArrayIndexOutOfBoundsException ae) {
                // Do nothing
            } catch (final NullPointerException np) {
                // Do nothing
            }
            this.battleFrame.pack();
        }
    }

    void updateStatsAndEffects(final MapBattleDefinitions bd) {
        this.bs.updateStats(bd.getActiveCharacter());
        this.be.updateEffects(bd.getActiveCharacter());
    }

    private void setUpGUI() {
        final EventHandler handler = new EventHandler();
        final Container borderPane = new Container();
        borderPane.setLayout(new BorderLayout());
        this.messageLabel = new JLabel(" ");
        this.messageLabel.setOpaque(true);
        if (DungeonDiver4.inDebugMode()) {
            this.battleFrame = new JFrame("Battle (DEBUG)");
        } else {
            this.battleFrame = new JFrame("Battle");
        }
        this.battleFrame.setContentPane(borderPane);
        Platform.hookFrameIcon(this.battleFrame, LogoManager.getIconLogo());
        this.battleFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.battleFrame.setResizable(false);
        this.drawGrid = new DrawGrid(
                MapBattleViewingWindowManager.getViewingWindowSize());
        for (int x = 0; x < MapBattleViewingWindowManager
                .getViewingWindowSize(); x++) {
            for (int y = 0; y < MapBattleViewingWindowManager
                    .getViewingWindowSize(); y++) {
                final AbstractDungeonObject dark = new Darkness()
                        .gameRenderHook(y, x, 0);
                this.drawGrid.setImageCell(ObjectImageManager.getImage(
                        dark.getName(), dark.getGameBaseID(),
                        dark.getGameTemplateColor(), dark.getGameAttributeID(),
                        dark.getGameAttributeTemplateColor()), x, y);
            }
        }
        this.battlePane = new MapBattleDraw(this.drawGrid);
        borderPane.add(this.battlePane, BorderLayout.CENTER);
        borderPane.add(this.messageLabel, BorderLayout.NORTH);
        borderPane.add(this.bs.getStatsPane(), BorderLayout.EAST);
        borderPane.add(this.be.getEffectsPane(), BorderLayout.SOUTH);
        this.battleFrame.addKeyListener(handler);
    }

    void turnEventHandlersOff() {
        this.eventHandlersOn = false;
    }

    void turnEventHandlersOn() {
        this.eventHandlersOn = true;
    }

    boolean areEventHandlersOn() {
        return this.eventHandlersOn;
    }

    private class EventHandler implements KeyListener {
        public EventHandler() {
            // Do nothing
        }

        @Override
        public void keyPressed(final KeyEvent e) {
            if (!PreferencesManager.oneMove()) {
                if (e.isShiftDown()) {
                    this.handleArrows(e);
                } else {
                    this.handleMovement(e);
                }
            }
        }

        @Override
        public void keyReleased(final KeyEvent e) {
            if (PreferencesManager.oneMove()) {
                if (e.isShiftDown()) {
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

        private void handleMovement(final KeyEvent e) {
            try {
                if (System.getProperty("os.name")
                        .equalsIgnoreCase("Mac OS X")) {
                    if (e.isMetaDown()) {
                        return;
                    }
                } else {
                    if (e.isControlDown()) {
                        return;
                    }
                }
                final AbstractBattle bl = DungeonDiver4.getApplication()
                        .getBattle();
                final MapBattleGUI bg = MapBattleGUI.this;
                if (bg.eventHandlersOn) {
                    final int keyCode = e.getKeyCode();
                    switch (keyCode) {
                    case KeyEvent.VK_NUMPAD4:
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        bl.updatePosition(-1, 0);
                        break;
                    case KeyEvent.VK_NUMPAD2:
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_X:
                        bl.updatePosition(0, 1);
                        break;
                    case KeyEvent.VK_NUMPAD6:
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        bl.updatePosition(1, 0);
                        break;
                    case KeyEvent.VK_NUMPAD8:
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        bl.updatePosition(0, -1);
                        break;
                    case KeyEvent.VK_NUMPAD7:
                    case KeyEvent.VK_Q:
                        bl.updatePosition(-1, -1);
                        break;
                    case KeyEvent.VK_NUMPAD9:
                    case KeyEvent.VK_E:
                        bl.updatePosition(1, -1);
                        break;
                    case KeyEvent.VK_NUMPAD3:
                    case KeyEvent.VK_C:
                        bl.updatePosition(1, 1);
                        break;
                    case KeyEvent.VK_NUMPAD1:
                    case KeyEvent.VK_Z:
                        bl.updatePosition(-1, 1);
                        break;
                    case KeyEvent.VK_NUMPAD5:
                    case KeyEvent.VK_S:
                        // Confirm before attacking self
                        final int res = CommonDialogs.showConfirmDialog(
                                "Are you sure you want to attack yourself?",
                                "Battle");
                        if (res == JOptionPane.YES_OPTION) {
                            bl.updatePosition(0, 0);
                        }
                        break;
                    default:
                        break;
                    }
                }
            } catch (final Exception ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
            }
        }

        private void handleArrows(final KeyEvent e) {
            try {
                if (System.getProperty("os.name")
                        .equalsIgnoreCase("Mac OS X")) {
                    if (e.isMetaDown()) {
                        return;
                    }
                } else {
                    if (e.isControlDown()) {
                        return;
                    }
                }
                final AbstractBattle bl = DungeonDiver4.getApplication()
                        .getBattle();
                final MapBattleGUI bg = MapBattleGUI.this;
                if (bg.eventHandlersOn) {
                    final int keyCode = e.getKeyCode();
                    switch (keyCode) {
                    case KeyEvent.VK_NUMPAD4:
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        bl.fireArrow(-1, 0);
                        break;
                    case KeyEvent.VK_NUMPAD2:
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_X:
                        bl.fireArrow(0, 1);
                        break;
                    case KeyEvent.VK_NUMPAD6:
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        bl.fireArrow(1, 0);
                        break;
                    case KeyEvent.VK_NUMPAD8:
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        bl.fireArrow(0, -1);
                        break;
                    case KeyEvent.VK_NUMPAD7:
                    case KeyEvent.VK_Q:
                        bl.fireArrow(-1, -1);
                        break;
                    case KeyEvent.VK_NUMPAD9:
                    case KeyEvent.VK_E:
                        bl.fireArrow(1, -1);
                        break;
                    case KeyEvent.VK_NUMPAD3:
                    case KeyEvent.VK_C:
                        bl.fireArrow(1, 1);
                        break;
                    case KeyEvent.VK_NUMPAD1:
                    case KeyEvent.VK_Z:
                        bl.fireArrow(-1, 1);
                        break;
                    case KeyEvent.VK_NUMPAD5:
                    case KeyEvent.VK_S:
                        // Confirm before attacking self
                        final int res = CommonDialogs.showConfirmDialog(
                                "Are you sure you want to attack yourself?",
                                "Battle");
                        if (res == JOptionPane.YES_OPTION) {
                            bl.fireArrow(0, 0);
                        }
                        break;
                    default:
                        break;
                    }
                }
            } catch (final Exception ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
            }
        }
    }
}
