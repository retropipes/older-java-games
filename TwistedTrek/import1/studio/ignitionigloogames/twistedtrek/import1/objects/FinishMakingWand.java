/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWand;

public class FinishMakingWand extends GenericWand {
    public FinishMakingWand() {
        super();
    }

    @Override
    public String getName() {
        return "Finish-Making Wand";
    }

    @Override
    public String getPluralName() {
        return "Finish-Making Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z, final int w) {
        this.useAction(new Finish(), x, y, z, w);
        if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playUseSound();
        }
    }

    @Override
    public String getUseSoundName() {
        return "create";
    }

    @Override
    public byte getObjectID() {
        return (byte) 2;
    }

    @Override
    public String getDescription() {
        return "Finish-Making Wands will create a finish when used.";
    }
}
