/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericInventoryModifier;

public class NoBoots extends GenericInventoryModifier {
    // Constructors
    public NoBoots() {
        super();
    }

    @Override
    public String getName() {
        return "No Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of No Boots";
    }

    @Override
    public byte getObjectID() {
        return (byte) 1;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        Import1.getApplication().getGameManager().decay();
        inv.removeAllBoots();
        if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveSuccessSound();
        }
    }

    @Override
    public String getDescription() {
        return "No Boots remove any boots worn when picked up.";
    }
}
