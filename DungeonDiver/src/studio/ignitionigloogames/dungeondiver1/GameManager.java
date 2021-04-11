package studio.ignitionigloogames.dungeondiver1;

import studio.ignitionigloogames.dungeondiver1.creatures.ClassManager;
import studio.ignitionigloogames.dungeondiver1.creatures.spells.SpellBookManager;

public class GameManager {
    // Constructors
    public GameManager() {
        // Do nothing
    }

    // Methods
    public boolean newGame() {
        final int c = SpellBookManager.selectClass();
        if (c != 0) {
            DungeonDiver.getHoldingBag()
                    .setPlayer(ClassManager.getNewPlayerInstance(c));
            return true;
        } else {
            return false;
        }
    }

    public boolean endGame() {
        return true;
    }
}
