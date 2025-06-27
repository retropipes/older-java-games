/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPassThroughObject;

public class FakeFinish extends GenericPassThroughObject {
    // Constructors
    public FakeFinish() {
        super();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        final Application app = Import1.getApplication();
        if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveFailedSound();
        }
        Messager.showMessage("Fake exit!");
    }

    @Override
    public String getName() {
        return "Fake Finish";
    }

    @Override
    public String getGameName() {
        return "Finish";
    }

    @Override
    public String getPluralName() {
        return "Fake Finishes";
    }

    @Override
    public byte getObjectID() {
        return (byte) 1;
    }

    @Override
    public String getDescription() {
        return "Fake Finishes look like regular finishes but don't lead anywhere.";
    }
}