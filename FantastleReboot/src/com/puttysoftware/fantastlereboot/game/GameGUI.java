/*  FantastleReboot: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlereboot.game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.puttysoftware.diane.gui.MainWindow;
import com.puttysoftware.fantastlereboot.FantastleReboot;
import com.puttysoftware.fantastlereboot.effects.EffectManager;
import com.puttysoftware.fantastlereboot.files.FileStateManager;
import com.puttysoftware.fantastlereboot.files.GameFileManager;
import com.puttysoftware.fantastlereboot.gui.Prefs;
import com.puttysoftware.fantastlereboot.loaders.ImageConstants;

class GameGUI {
  // Fields
  private static MainWindow outputFrame;
  private static JPanel borderPane;
  private static JLabel messageLabel;
  private static GameCanvas outputPane;
  private static final EventHandler handler = new EventHandler();
  private static boolean knm;
  private static boolean deferredRedraw = false;
  private static boolean eventFlag = true;

  // Constructors
  private GameGUI() {
    // Do nothing
  }

  // Methods
  public static void updateStats() {
    StatGUI.updateStats();
  }

  public static void enableEvents() {
    GameGUI.borderPane.setEnabled(true);
    GameGUI.eventFlag = true;
  }

  public static void disableEvents() {
    GameGUI.borderPane.setEnabled(false);
    GameGUI.eventFlag = false;
  }

  static void viewingWindowSizeChanged() {
    GameGUI.setUpGUI();
    GameGUI.updateGameGUI();
    GameGUI.deferredRedraw = true;
  }

  public static void showOutput() {
    GameGUI.outputFrame = MainWindow.getOutputFrame();
    GameGUI.outputFrame.setTitle("Game");
    GameGUI.outputFrame.attachContent(GameGUI.borderPane);
    GameGUI.outputFrame.addKeyListener(GameGUI.handler);
    GameGUI.outputFrame.addWindowListener(GameGUI.handler);
    if (GameGUI.deferredRedraw) {
      GameGUI.deferredRedraw = false;
      GameGUI.redrawWorld();
    }
    GameGUI.updateStats();
  }

  public static void hideOutput() {
    GameGUI.outputFrame.removeWindowListener(GameGUI.handler);
    GameGUI.outputFrame.removeKeyListener(GameGUI.handler);
  }

  public static void setStatusMessage(final String msg) {
    GameGUI.messageLabel.setText(msg);
  }

  private static void resetBorderPane() {
    GameGUI.borderPane.removeAll();
    GameGUI.borderPane.add(GameGUI.outputPane, BorderLayout.CENTER);
    GameGUI.borderPane.add(GameGUI.messageLabel, BorderLayout.NORTH);
    GameGUI.borderPane.add(StatGUI.getStatsPane(), BorderLayout.EAST);
    GameGUI.borderPane.add(EffectManager.getEffectMessageJPanel(),
        BorderLayout.SOUTH);
  }

  public static void redrawWorld() {
    // Draw the world
    GameGUI.outputPane.repaint();
    if (GameGUI.knm) {
      GameGUI.knm = false;
    } else {
      GameGUI.setStatusMessage(" ");
    }
    GameGUI.outputFrame.pack();
  }

  public static void keepNextMessage() {
    GameGUI.knm = true;
  }

  static void updateGameGUI() {
    GameGUI.resetBorderPane();
    StatGUI.updateImages();
    StatGUI.updateStats();
  }

  private static void setUpGUI() {
    GameGUI.borderPane = new JPanel();
    GameGUI.borderPane.setLayout(new BorderLayout());
    GameGUI.messageLabel = new JLabel(" ");
    GameGUI.messageLabel.setOpaque(true);
    GameGUI.outputPane = new GameCanvas();
    final int vSize = Prefs.getViewingWindowSize();
    final int gSize = ImageConstants.SIZE;
    GameGUI.outputPane
        .setPreferredSize(new Dimension(vSize * gSize, vSize * gSize));
  }

  private static class EventHandler implements KeyListener, WindowListener {
    EventHandler() {
      // Do nothing
    }

    @Override
    public void keyPressed(final KeyEvent e) {
      if (GameGUI.eventFlag) {
        if (!Prefs.oneMove()) {
          EventHandler.handleMovement(e);
        }
      }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
      if (GameGUI.eventFlag) {
        if (Prefs.oneMove()) {
          EventHandler.handleMovement(e);
        }
      }
    }

    @Override
    public void keyTyped(final KeyEvent e) {
      // Do nothing
    }

    public static void handleMovement(final KeyEvent e) {
      try {
        final int keyCode = e.getKeyCode();
        switch (keyCode) {
        case KeyEvent.VK_LEFT:
          if (e.isShiftDown()) {
            Game.updatePositionRelative(-1, -1, 0);
          } else {
            Game.updatePositionRelative(-1, 0, 0);
          }
          break;
        case KeyEvent.VK_DOWN:
          if (e.isShiftDown()) {
            Game.updatePositionRelative(-1, 1, 0);
          } else {
            Game.updatePositionRelative(0, 1, 0);
          }
          break;
        case KeyEvent.VK_RIGHT:
          if (e.isShiftDown()) {
            Game.updatePositionRelative(1, 1, 0);
          } else {
            Game.updatePositionRelative(1, 0, 0);
          }
          break;
        case KeyEvent.VK_UP:
          if (e.isShiftDown()) {
            Game.updatePositionRelative(1, -1, 0);
          } else {
            Game.updatePositionRelative(0, -1, 0);
          }
          break;
        case KeyEvent.VK_ENTER:
          if (e.isShiftDown()) {
            Game.updatePositionRelative(0, 0, 0);
          }
          break;
        case KeyEvent.VK_NUMPAD7:
        case KeyEvent.VK_Q:
          Game.updatePositionRelative(-1, -1, 0);
          break;
        case KeyEvent.VK_NUMPAD8:
        case KeyEvent.VK_W:
          Game.updatePositionRelative(0, -1, 0);
          break;
        case KeyEvent.VK_NUMPAD9:
        case KeyEvent.VK_E:
          Game.updatePositionRelative(1, -1, 0);
          break;
        case KeyEvent.VK_NUMPAD4:
        case KeyEvent.VK_A:
          Game.updatePositionRelative(-1, 0, 0);
          break;
        case KeyEvent.VK_NUMPAD5:
        case KeyEvent.VK_S:
          Game.updatePositionRelative(0, 0, 0);
          break;
        case KeyEvent.VK_NUMPAD6:
        case KeyEvent.VK_D:
          Game.updatePositionRelative(1, 0, 0);
          break;
        case KeyEvent.VK_NUMPAD1:
        case KeyEvent.VK_Z:
          Game.updatePositionRelative(-1, 1, 0);
          break;
        case KeyEvent.VK_NUMPAD2:
        case KeyEvent.VK_X:
          Game.updatePositionRelative(0, 1, 0);
          break;
        case KeyEvent.VK_NUMPAD3:
        case KeyEvent.VK_C:
          Game.updatePositionRelative(1, 1, 0);
          break;
        default:
          break;
        }
      } catch (final Exception ex) {
        FantastleReboot.exception(ex);
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
        boolean success = false;
        int status = 0;
        if (FileStateManager.getDirty()) {
          status = FileStateManager.showSaveDialog();
          if (status == JOptionPane.YES_OPTION) {
            success = GameFileManager.suspendGame();
            if (success) {
              Game.exitGame();
            }
          } else if (status == JOptionPane.NO_OPTION) {
            Game.exitGame();
          }
        } else {
          Game.exitGame();
        }
      } catch (final Exception ex) {
        FantastleReboot.exception(ex);
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
