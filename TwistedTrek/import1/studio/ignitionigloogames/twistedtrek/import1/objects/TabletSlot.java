/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericInfiniteLock;

public class TabletSlot extends GenericInfiniteLock {
    // Constructors
    public TabletSlot() {
        super(new Tablet());
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need a tablet");
        }
        // Play move failed sound, if it's enabled
        if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveFailedSound();
        }
    }

    @Override
    public String getName() {
        return "Tablet Slot";
    }

    @Override
    public String getPluralName() {
        return "Tablet Slots";
    }

    @Override
    public byte getObjectID() {
        return (byte) 27;
    }

    @Override
    public String getDescription() {
        return "Tablet Slots disappear when filled with a Tablet.";
    }
}