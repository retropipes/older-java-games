/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.GenericWand;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.world.WorldConstants;

public class WarpWand extends GenericWand {
    public WarpWand() {
        super();
    }

    @Override
    public String getName() {
        return "Warp Wand";
    }

    @Override
    public String getPluralName() {
        return "Warp Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(null, x, y, z);
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playUseSound();
        }
    }

    @Override
    public void useAction(final WorldObject mo, final int x, final int y,
            final int z) {
        final Application app = Worldz.getApplication();
        app.getWorldManager()
                .getWorld()
                .warpObject(
                        app.getWorldManager().getWorld()
                                .getCell(x, y, z, WorldConstants.LAYER_OBJECT),
                        x, y, z, WorldConstants.LAYER_OBJECT);
    }

    @Override
    public String getUseSoundName() {
        return "teleport";
    }

    @Override
    public String getDescription() {
        return "Warp Wands will teleport the object at the target square to a random location when used.";
    }
}
