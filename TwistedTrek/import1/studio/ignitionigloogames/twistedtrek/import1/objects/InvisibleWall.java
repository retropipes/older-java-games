/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWall;

public class InvisibleWall extends GenericWall {
    // Constructors
    public InvisibleWall() {
        super();
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        // Display invisible wall message, if it's enabled
        final Application app = Import1.getApplication();
        Messager.showMessage("Invisible Wall!");
        // Play move failed sound, if it's enabled
        if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveFailedSound();
        }
    }

    @Override
    public String getName() {
        return "Invisible Wall";
    }

    @Override
    public String getGameName() {
        return "Empty";
    }

    @Override
    public String getPluralName() {
        return "Invisible Walls";
    }

    @Override
    public byte getObjectID() {
        return (byte) 3;
    }

    @Override
    public String getDescription() {
        return "Invisible Walls look like any other open space, but block any attempt at moving into them.";
    }
}