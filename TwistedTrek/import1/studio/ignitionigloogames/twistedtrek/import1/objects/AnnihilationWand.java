/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWand;

public class AnnihilationWand extends GenericWand {
    // Constructors
    public AnnihilationWand() {
        super();
    }

    @Override
    public String getName() {
        return "Annihilation Wand";
    }

    @Override
    public String getPluralName() {
        return "Annihilation Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z, final int w) {
        this.useAction(new Empty(), x, y, z, w);
        if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playUseSound();
        }
    }

    @Override
    public byte getObjectID() {
        return (byte) 1;
    }

    @Override
    public String getUseSoundName() {
        return "destroy";
    }

    @Override
    public String getDescription() {
        return "Annihilation Wands will destroy any object (not ground) when used, except the Void or a Sealing Wall.";
    }
}
