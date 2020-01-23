/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.screens;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import studio.ignitionigloogames.twistedtrek.panels.GuiPanel;
import studio.ignitionigloogames.twistedtrek.panels.MessagePanel;

public interface Screen {
    void displayOutput(GuiPanel terminal, MessagePanel messages);

    Screen respondToUserInput(KeyEvent key, MouseEvent mouse);
}
