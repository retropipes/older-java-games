/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWand;

public class WallMakingWand extends GenericWand {
    public WallMakingWand() {
	super();
    }

    @Override
    public String getName() {
	return "Wall-Making Wand";
    }

    @Override
    public String getPluralName() {
	return "Wall-Making Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z, final int w) {
	this.useAction(new Wall(), x, y, z, w);
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
	return (byte) 4;
    }

    @Override
    public String getDescription() {
	return "Wall-Making Wands will create an ordinary wall in the target square when used.";
    }
}
