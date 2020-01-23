/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.GenericGem;
import net.worldwizard.worldz.generic.WorldObject;

public class BrightnessGem extends GenericGem {
    // Constructors
    public BrightnessGem() {
        super();
    }

    @Override
    public String getName() {
        return "Brightness Gem";
    }

    @Override
    public String getPluralName() {
        return "Brightness Gems";
    }

    @Override
    public void postMoveActionHook() {
        Worldz.getApplication().getWorldManager().getWorld()
                .setVisionRadiusToMaximum();
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            WorldObject.playLightSound();
        }
    }

    @Override
    public String getMoveSuccessSoundName() {
        return "light";
    }

    @Override
    public String getDescription() {
        return "Brightness Gems increase the visible area to its maximum.";
    }
}
