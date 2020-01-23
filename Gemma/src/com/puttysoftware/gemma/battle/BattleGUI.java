/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.battle;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.gemma.DrawGrid;
import com.puttysoftware.gemma.Gemma;
import com.puttysoftware.gemma.prefs.PreferencesManager;
import com.puttysoftware.gemma.support.Support;
import com.puttysoftware.gemma.support.battle.BattleDefinitions;
import com.puttysoftware.gemma.support.map.generic.MapObject;
import com.puttysoftware.gemma.support.resourcemanagers.ImageManager;
import com.puttysoftware.gemma.support.resourcemanagers.MusicConstants;
import com.puttysoftware.gemma.support.resourcemanagers.MusicManager;
import com.puttysoftware.gemma.support.resourcemodifiers.ImageTransformer;
import com.puttysoftware.images.BufferedImageIcon;

class BattleGUI {
    // Fields
    private JFrame battleFrame;
    private BattleDraw battlePane;
    private JLabel messageLabel;
    private final BattleViewingWindowManager vwMgr;
    private final BattleStats bs;
    private final BattleEffects be;
    private DrawGrid drawGrid;
    boolean eventHandlersOn;

    // Constructors
    BattleGUI() {
        this.vwMgr = new BattleViewingWindowManager();
        this.bs = new BattleStats();
        this.be = new BattleEffects();
        this.setUpGUI();
        this.eventHandlersOn = true;
    }

    // Methods
    JFrame getOutputFrame() {
        return this.battleFrame;
    }

    BattleViewingWindowManager getViewManager() {
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

    void showBattle(final boolean isBoss) {
        Gemma.getApplication().getMenuManager().setBattleMenus();
        if (PreferencesManager
                .getMusicEnabled(PreferencesManager.MUSIC_BATTLE)) {
            MusicManager.stopMusic();
            if (isBoss) {
                MusicManager.playMusic(MusicConstants.MUSIC_BOSS, 0);
            } else {
                MusicManager.playMusic(MusicConstants.MUSIC_BATTLE, 0);
            }
        }
        this.battleFrame.setVisible(true);
        this.battleFrame.setJMenuBar(
                Gemma.getApplication().getMenuManager().getMainMenuBar());
    }

    void hideBattle() {
        if (MusicManager.isMusicPlaying()) {
            MusicManager.stopMusic();
        }
        if (this.battleFrame != null) {
            this.battleFrame.setVisible(false);
        }
    }

    void redrawBattle(BattleDefinitions bd) {
        // Draw the battle, if it is visible
        if (this.battleFrame.isVisible()) {
            int x, y;
            int xFix, yFix;
            int xView = this.vwMgr.getViewingWindowLocationX();
            int yView = this.vwMgr.getViewingWindowLocationY();
            int xlView = this.vwMgr.getLowerRightViewingWindowLocationX();
            int ylView = this.vwMgr.getLowerRightViewingWindowLocationY();
            for (x = xView; x <= xlView; x++) {
                for (y = yView; y <= ylView; y++) {
                    xFix = x - xView;
                    yFix = y - yView;
                    try {
                        BufferedImageIcon icon1 = bd.getBattleMap()
                                .getBattleGround(y, x).battleRenderHook();
                        BufferedImageIcon icon2 = bd.getBattleMap()
                                .getBattleCell(y, x).battleRenderHook();
                        this.drawGrid.setImageCell(ImageTransformer
                                .getCompositeImage(icon1, icon2), xFix, yFix);
                    } catch (final ArrayIndexOutOfBoundsException
                            | NullPointerException ae) {
                        this.drawGrid.setImageCell(
                                ImageManager.getMapImage("Void", "Void", null),
                                xFix, yFix);
                    }
                }
            }
            this.battlePane.updateGrid(this.drawGrid);
            this.battlePane.repaint();
            this.battleFrame.pack();
        }
    }

    void redrawOneBattleSquare(BattleDefinitions bd, int x, int y,
            MapObject obj3) {
        // Draw the battle, if it is visible
        if (this.battleFrame.isVisible()) {
            try {
                int xFix, yFix;
                int xView = this.vwMgr.getViewingWindowLocationX();
                int yView = this.vwMgr.getViewingWindowLocationY();
                xFix = y - xView;
                yFix = x - yView;
                BufferedImageIcon icon1 = bd.getBattleMap()
                        .getBattleGround(x, y).battleRenderHook();
                BufferedImageIcon icon2 = bd.getBattleMap().getBattleCell(x, y)
                        .battleRenderHook();
                BufferedImageIcon icon3 = obj3.battleRenderHook();
                this.drawGrid.setImageCell(
                        ImageTransformer.getCompositeImage(icon1, icon2, icon3),
                        xFix, yFix);
                this.battlePane.repaint();
            } catch (final ArrayIndexOutOfBoundsException
                    | NullPointerException ae) {
                // Do nothing
            }
            this.battleFrame.pack();
        }
    }

    void updateStatsAndEffects(BattleDefinitions bd) {
        this.bs.updateStats(bd.getActiveCharacter());
        this.be.updateEffects(bd.getActiveCharacter());
    }

    private void setUpGUI() {
        EventHandler handler = new EventHandler();
        Container borderPane = new Container();
        borderPane.setLayout(new BorderLayout());
        this.messageLabel = new JLabel(" ");
        this.messageLabel.setOpaque(true);
        if (Support.inDebugMode()) {
            this.battleFrame = new JFrame("Battle (DEBUG)");
        } else {
            this.battleFrame = new JFrame("Battle");
        }
        this.battlePane = new BattleDraw();
        this.battleFrame.setContentPane(borderPane);
        this.battleFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.battlePane.setLayout(new GridLayout(
                BattleViewingWindowManager.getViewingWindowSize(),
                BattleViewingWindowManager.getViewingWindowSize()));
        this.battleFrame.setResizable(false);
        this.drawGrid = new DrawGrid(
                BattleViewingWindowManager.getViewingWindowSize());
        for (int x = 0; x < BattleViewingWindowManager
                .getViewingWindowSize(); x++) {
            for (int y = 0; y < BattleViewingWindowManager
                    .getViewingWindowSize(); y++) {
                this.drawGrid.setImageCell(
                        ImageManager.getMapImage("Darkness", "Darkness", null),
                        x, y);
            }
        }
        this.battlePane.updateGrid(this.drawGrid);
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
            BattleGUI bg = BattleGUI.this;
            if (bg.eventHandlersOn) {
                if (!PreferencesManager.oneMove()) {
                    if (e.isShiftDown()) {
                        this.handleArrows(e);
                    } else {
                        this.handleMovement(e);
                    }
                }
            }
        }

        @Override
        public void keyReleased(final KeyEvent e) {
            BattleGUI bg = BattleGUI.this;
            if (bg.eventHandlersOn) {
                if (PreferencesManager.oneMove()) {
                    if (e.isShiftDown()) {
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
                BattleLogic bl = Gemma.getApplication().getBattle();
                BattleGUI bg = BattleGUI.this;
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
                        int res = CommonDialogs.showConfirmDialog(
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
            } catch (Exception ex) {
                Gemma.getErrorLogger().logError(ex);
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
                BattleLogic bl = Gemma.getApplication().getBattle();
                BattleGUI bg = BattleGUI.this;
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
                        int res = CommonDialogs.showConfirmDialog(
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
            } catch (Exception ex) {
                Gemma.getErrorLogger().logError(ex);
            }
        }
    }
}
