package net.worldwizard.mazerunner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

public class MazeRunnerEventHandler
        implements KeyListener, ActionListener, WindowListener, MouseListener {
    @Override
    public void keyPressed(final KeyEvent e) {
        final MazeRunner app = MazeRunner.getApplication();
        final int keyCode = e.getKeyCode();
        if (e.isShiftDown()) {
            app.setPullInProgress(true);
        }
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                if (!app.usingAnItem()) {
                    app.updatePosition(-1, 0);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (!app.usingAnItem()) {
                    app.updatePosition(0, 1);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!app.usingAnItem()) {
                    app.updatePosition(1, 0);
                }
                break;
            case KeyEvent.VK_UP:
                if (!app.usingAnItem()) {
                    app.updatePosition(0, -1);
                }
                break;
            case KeyEvent.VK_F1:
                if (!app.usingAnItem()) {
                    app.showInventoryDialog();
                }
                break;
            case KeyEvent.VK_F2:
                if (!app.usingAnItem()) {
                    app.saveMaze();
                }
                break;
            case KeyEvent.VK_F3:
                if (!app.usingAnItem()) {
                    app.setUsingAnItem(true);
                    app.showUseDialog();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                if (app.usingAnItem()) {
                    app.setUsingAnItem(false);
                    Messager.showMessage(" ");
                }
                break;
            default:
                break;
        }
        if (app.isPullInProgress()) {
            app.setPullInProgress(false);
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        // Do nothing
    }

    @Override
    public void keyTyped(final KeyEvent e) {
        // Do nothing
    }

    // Handle buttons and menus
    @Override
    public void actionPerformed(final ActionEvent e) {
        final MazeRunner app = MazeRunner.getApplication();
        boolean loaded = false;
        final String cmd = e.getActionCommand();
        if (cmd.equals("OK")) {
            app.setPrefs();
        } else if (cmd.equals("Cancel")) {
            app.hidePrefs();
        } else if (cmd.equals("New...")) {
            loaded = MazeMaker.newMaze();
            app.setLoaded(loaded);
        } else if (cmd.equals("Open...")) {
            loaded = app.loadMaze();
            app.setLoaded(loaded);
        } else if (cmd.equals("Close")) {
            // Close the window
            boolean saved = true;
            int status = 0;
            if (app.getDirty()) {
                status = app.showSaveDialog();
                if (status == JOptionPane.YES_OPTION) {
                    saved = app.saveMaze();
                } else if (status == JOptionPane.CANCEL_OPTION) {
                    saved = false;
                } else {
                    app.setDirty(false);
                }
            }
            if (saved) {
                app.getOutputFrame().dispose();
                app.showMenu();
            }
        } else if (cmd.equals("Save...")) {
            if (app.getLoaded()) {
                app.saveMaze();
            } else {
                Messager.showDialog("No Maze Opened");
            }
        } else if (cmd.equals("Save As...")) {
            if (app.getLoaded()) {
                app.saveMaze();
            } else {
                Messager.showDialog("No Maze Opened");
            }
        } else if (cmd.equals("Exit")) {
            // Exit program
            System.exit(0);
        } else if (cmd.equals("Undo")) {
            // Undo most recent action
            MazeMaker.undo();
        } else if (cmd.equals("Redo")) {
            // Redo most recent undone action
            MazeMaker.redo();
        } else if (cmd.equals("Cut")) {
            // Do nothing - not applicable to Maze Runner
        } else if (cmd.equals("Copy")) {
            // Do nothing - not applicable to Maze Runner
        } else if (cmd.equals("Paste")) {
            // Do nothing - not applicable to Maze Runner
        } else if (cmd.equals("Preferences...")) {
            // Show preferences dialog
            app.showPrefs();
        } else if (cmd.equals("Clear History")) {
            // Clear undo/redo history
            MazeMaker.clearHistory();
        } else if (cmd.equals("Play Maze")) {
            // Play the current maze
            app.playMaze();
        } else if (cmd.equals("Edit Maze")) {
            // Edit the current maze
            MazeMaker.editMaze();
        } else if (cmd.equals("Show Inventory...")) {
            if (!app.usingAnItem()) {
                app.showInventoryDialog();
            }
        } else if (cmd.equals("Use an Item...")) {
            if (!app.usingAnItem()) {
                app.setUsingAnItem(true);
                app.showUseDialog();
            }
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
        final MazeRunner app = MazeRunner.getApplication();
        boolean success = false;
        int status = 0;
        if (app.getDirty()) {
            status = app.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                success = app.saveMaze();
                if (success) {
                    app.solvedMaze();
                }
            } else if (status == JOptionPane.NO_OPTION) {
                app.solvedMaze();
            }
        } else {
            app.solvedMaze();
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
        final MazeRunner app = MazeRunner.getApplication();
        if (app.usingAnItem()) {
            final int x = e.getX();
            final int y = e.getY();
            app.useItemHandler(x, y);
            app.setUsingAnItem(false);
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