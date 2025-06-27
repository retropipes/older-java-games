/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWand;

public class WallBreakingWand extends GenericWand {
    // Constructors
    public WallBreakingWand() {
        super();
    }

    @Override
    public String getName() {
        return "Wall-Breaking Wand";
    }

    @Override
    public String getPluralName() {
        return "Wall-Breaking Wands";
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
        return (byte) 5;
    }

    @Override
    public String getUseSoundName() {
        return "destroy";
    }

    @Override
    public String getDescription() {
        return "Wall-Breaking Wands will destroy one wall when used, if aimed at a wall.";
    }
}
