/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericSingleLock;

public class YellowLock extends GenericSingleLock {
    // Constructors
    public YellowLock() {
        super(new YellowKey());
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need a yellow key");
        }
        // Play move failed sound, if it's enabled
        if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveFailedSound();
        }
    }

    @Override
    public String getName() {
        return "Yellow Lock";
    }

    @Override
    public String getPluralName() {
        return "Yellow Locks";
    }

    @Override
    public byte getObjectID() {
        return (byte) 10;
    }

    @Override
    public String getDescription() {
        return "Yellow Locks require Yellow Keys to open.";
    }
}