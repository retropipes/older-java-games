/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericSingleLock;

public class CyanLock extends GenericSingleLock {
    // Constructors
    public CyanLock() {
        super(new CyanKey());
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need a cyan key");
        }
        // Play move failed sound, if it's enabled
        if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveFailedSound();
        }
    }

    @Override
    public String getName() {
        return "Cyan Lock";
    }

    @Override
    public String getPluralName() {
        return "Cyan Locks";
    }

    @Override
    public byte getObjectID() {
        return (byte) 11;
    }

    @Override
    public String getDescription() {
        return "Cyan Locks require Cyan Keys to open.";
    }
}