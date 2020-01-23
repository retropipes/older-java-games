package net.worldwizard.dungeondiver;

import net.worldwizard.dungeondiver.creatures.ClassManager;
import net.worldwizard.dungeondiver.creatures.spells.SpellBookManager;

public class GameManager {
    // Constructors
    public GameManager() {
        // Do nothing
    }

    // Methods
    public boolean newGame() {
        final int c = SpellBookManager.selectClass();
        if (c != 0) {
            DungeonDiver.getHoldingBag().setPlayer(
                    ClassManager.getNewPlayerInstance(c));
            return true;
        } else {
            return false;
        }
    }

    public boolean endGame() {
        return true;
    }
}
