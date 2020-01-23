/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.GenericWand;

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
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(new Empty(), x, y, z);
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playUseSound();
        }
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
