/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericField;

public class ForceField extends GenericField {
    // Constructors
    public ForceField() {
        super(new EnergySphere());
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        final Application app = Import1.getApplication();
        Messager.showMessage("You'll get zapped");
        if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveFailedSound();
        }
    }

    @Override
    public String getName() {
        return "Force Field";
    }

    @Override
    public String getPluralName() {
        return "Force Fields";
    }

    @Override
    public byte getObjectID() {
        return (byte) 2;
    }

    @Override
    public String getMoveFailedSoundName() {
        return "forcefld";
    }

    @Override
    public String getDescription() {
        return "Force Fields block movement without an Energy Sphere.";
    }
}
