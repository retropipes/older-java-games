/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.screens;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import studio.ignitionigloogames.twistedtrek.Creature;
import studio.ignitionigloogames.twistedtrek.constants.Constants;
import studio.ignitionigloogames.twistedtrek.panels.GuiPanel;
import studio.ignitionigloogames.twistedtrek.panels.MessagePanel;

public abstract class TargetBasedScreen implements Screen {
    protected Creature player;
    protected String caption;
    private final int sx;
    private final int sy;
    private int x;
    private int y;

    public TargetBasedScreen(final Creature newPlayer, final String newCaption, final int nsx, final int nsy) {
	this.player = newPlayer;
	this.caption = newCaption;
	this.sx = nsx;
	this.sy = nsy;
    }

    @Override
    public void displayOutput(final GuiPanel terminal, final MessagePanel messages) {
	messages.write(this.caption);
    }

    @Override
    public Screen respondToUserInput(final KeyEvent key, final MouseEvent mouse) {
	final int px = this.x;
	final int py = this.y;
	if (key != null) {
	    switch (key.getKeyCode()) {
	    case KeyEvent.VK_LEFT:
	    case KeyEvent.VK_H:
	    case KeyEvent.VK_NUMPAD4:
		this.x--;
		break;
	    case KeyEvent.VK_RIGHT:
	    case KeyEvent.VK_L:
	    case KeyEvent.VK_NUMPAD6:
		this.x++;
		break;
	    case KeyEvent.VK_UP:
	    case KeyEvent.VK_J:
	    case KeyEvent.VK_NUMPAD8:
		this.y--;
		break;
	    case KeyEvent.VK_DOWN:
	    case KeyEvent.VK_K:
	    case KeyEvent.VK_NUMPAD2:
		this.y++;
		break;
	    case KeyEvent.VK_Y:
	    case KeyEvent.VK_NUMPAD7:
		this.x--;
		this.y--;
		break;
	    case KeyEvent.VK_NUMPAD9:
	    case KeyEvent.VK_U:
		this.x++;
		this.y--;
		break;
	    case KeyEvent.VK_NUMPAD1:
	    case KeyEvent.VK_B:
		this.x--;
		this.y++;
		break;
	    case KeyEvent.VK_NUMPAD3:
	    case KeyEvent.VK_N:
		this.x++;
		this.y++;
		break;
	    case KeyEvent.VK_ENTER:
		this.selectWorldCoordinate(this.player.x + this.x, this.player.y + this.y, this.sx + this.x,
			this.sy + this.y);
		return null;
	    case KeyEvent.VK_ESCAPE:
		return null;
	    default:
		break;
	    }
	}
	if (mouse != null) {
	    this.x = mouse.getX() / Constants.TILE_SIZE_IN_PIXELS - this.sx - px;
	    this.y = mouse.getY() / Constants.TILE_SIZE_IN_PIXELS - this.sy - py;
	    if (!this.isAcceptable(this.player.x + this.x, this.player.y + this.y)) {
		this.x = px;
		this.y = py;
		return this;
	    } else {
		this.selectWorldCoordinate(this.player.x + this.x, this.player.y + this.y, this.sx + this.x,
			this.sy + this.y);
		return null;
	    }
	}
	if (!this.isAcceptable(this.player.x + this.x, this.player.y + this.y)) {
	    this.x = px;
	    this.y = py;
	}
	this.enterWorldCoordinate(this.player.x + this.x, this.player.y + this.y, this.sx + this.x, this.sy + this.y);
	return this;
    }

    public boolean isAcceptable(final int nx, final int ny) {
	return true;
    }

    public void enterWorldCoordinate(final int nx, final int ny, final int screenX, final int screenY) {
    }

    public void selectWorldCoordinate(final int nx, final int ny, final int screenX, final int screenY) {
    }
}
