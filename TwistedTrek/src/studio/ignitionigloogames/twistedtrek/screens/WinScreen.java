/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.screens;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import studio.ignitionigloogames.twistedtrek.panels.GuiPanel;
import studio.ignitionigloogames.twistedtrek.panels.MessagePanel;
import studio.ignitionigloogames.twistedtrek.sound.Sound;

public class WinScreen implements Screen {
	@Override
	public void displayOutput(final GuiPanel terminal, final MessagePanel messages) {
		Sound.winGame();
		messages.clear();
		messages.write("You won.");
		messages.write("-- press [enter] or click to restart --");
	}

	@Override
	public Screen respondToUserInput(final KeyEvent key, final MouseEvent mouse) {
		if (key != null) {
			return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
		}
		if (mouse != null) {
			return new PlayScreen();
		}
		return this;
	}
}
