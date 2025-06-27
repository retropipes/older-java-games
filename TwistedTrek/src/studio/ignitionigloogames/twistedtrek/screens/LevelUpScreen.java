/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.screens;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import studio.ignitionigloogames.twistedtrek.Creature;
import studio.ignitionigloogames.twistedtrek.LevelUpController;
import studio.ignitionigloogames.twistedtrek.panels.GuiPanel;
import studio.ignitionigloogames.twistedtrek.panels.MessagePanel;

public class LevelUpScreen implements Screen {
	private final LevelUpController controller;
	private final Creature player;
	private int picks;

	public LevelUpScreen(final Creature newPlayer, final int newPicks) {
		this.controller = new LevelUpController();
		this.player = newPlayer;
		this.picks = newPicks;
	}

	@Override
	public void displayOutput(final GuiPanel terminal, final MessagePanel messages) {
		final List<String> options = this.controller.getLevelUpOptions();
		messages.clear();
		messages.write("Choose a level up bonus:");
		for (int i = 0; i < options.size(); i++) {
			messages.write(String.format("[%d] %s", i + 1, options.get(i)));
		}
	}

	@Override
	public Screen respondToUserInput(final KeyEvent key, final MouseEvent mouse) {
		if (key != null) {
			final List<String> options = this.controller.getLevelUpOptions();
			String chars = "";
			for (int i = 0; i < options.size(); i++) {
				chars = chars + Integer.toString(i + 1);
			}
			final int i = chars.indexOf(key.getKeyChar());
			if (i < 0) {
				return this;
			}
			this.controller.getLevelUpOption(options.get(i)).invoke(this.player);
			if (--this.picks < 1) {
				return null;
			} else {
				return this;
			}
		} else {
			return this;
		}
	}
}
