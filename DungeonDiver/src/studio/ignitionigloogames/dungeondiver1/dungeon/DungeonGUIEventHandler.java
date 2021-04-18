package studio.ignitionigloogames.dungeondiver1.dungeon;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.HoldingBag;

public class DungeonGUIEventHandler implements KeyListener, WindowListener {
    @Override
    public void keyPressed(final KeyEvent e) {
        try {
            final DungeonGUI app = DungeonDiver.getHoldingBag().getDungeonGUI();
            final HoldingBag menu = DungeonDiver.getHoldingBag();
            final int keyCode = e.getKeyCode();
            switch (keyCode) {
            case KeyEvent.VK_LEFT:
                app.updatePosition(-1, 0);
                break;
            case KeyEvent.VK_DOWN:
                app.updatePosition(0, 1);
                break;
            case KeyEvent.VK_RIGHT:
                app.updatePosition(1, 0);
                break;
            case KeyEvent.VK_UP:
                app.updatePosition(0, -1);
                break;
            case KeyEvent.VK_ESCAPE:
                menu.showGUI();
                break;
            default:
                break;
            }
        } catch (final Throwable t) {
            DungeonDiver.debug(t);
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
            final HoldingBag menu = DungeonDiver.getHoldingBag();
            final DungeonGUI app = menu.getDungeonGUI();
            app.hideDungeon();
            menu.showGUI();
        } catch (final Throwable t) {
            DungeonDiver.debug(t);
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