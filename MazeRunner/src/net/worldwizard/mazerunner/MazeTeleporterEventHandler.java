package net.worldwizard.mazerunner;

import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MazeTeleporterEventHandler
        implements AdjustmentListener, MouseListener, KeyListener {
    // handle scroll bars
    @Override
    public void adjustmentValueChanged(final AdjustmentEvent e) {
        final MazeRunner app = MazeRunner.getApplication();
        final Adjustable src = e.getAdjustable();
        final int dir = src.getOrientation();
        final int value = src.getValue();
        int relValue = 0;
        switch (dir) {
            case Adjustable.HORIZONTAL:
                relValue = value - app.getViewingWindowLocation(true);
                MazeMaker.updateEditorPosition(0, relValue, 0, 0);
                break;
            case Adjustable.VERTICAL:
                relValue = value - app.getViewingWindowLocation(false);
                MazeMaker.updateEditorPosition(relValue, 0, 0, 0);
                break;
            default:
                break;
        }
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
        final int x = e.getX();
        final int y = e.getY();
        MazeMaker.setTeleporterDestination(x, y);
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
        // Do nothing
    }

    @Override
    public void mouseExited(final MouseEvent e) {
        // Do nothing
    }

    // handle keyboard
    @Override
    public void keyPressed(final KeyEvent e) {
        // Do nothing
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        final int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_UP:
                MazeMaker.updateEditorPosition(0, 0, 1, 0);
                break;
            case KeyEvent.VK_DOWN:
                MazeMaker.updateEditorPosition(0, 0, -1, 0);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyTyped(final KeyEvent e) {
        // Do nothing
    }
}