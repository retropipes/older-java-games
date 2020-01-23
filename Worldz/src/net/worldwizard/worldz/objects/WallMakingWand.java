/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.GenericWand;

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
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(new Wall(), x, y, z);
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playUseSound();
        }
    }

    @Override
    public String getUseSoundName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Wall-Making Wands will create an ordinary wall in the target square when used.";
    }
}
