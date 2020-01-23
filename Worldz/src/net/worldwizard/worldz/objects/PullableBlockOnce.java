/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.generic.GenericMovableObject;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.world.WorldConstants;

public class PullableBlockOnce extends GenericMovableObject {
    // Constructors
    public PullableBlockOnce() {
        super(false, true);
    }

    @Override
    public String getName() {
        return "Pullable Block Once";
    }

    @Override
    public String getPluralName() {
        return "Pullable Blocks Once";
    }

    @Override
    public void pullAction(final ObjectInventory inv, final WorldObject mo,
            final int x, final int y, final int pushX, final int pushY) {
        final Application app = Worldz.getApplication();
        app.getGameManager().updatePulledPosition(x, y, pushX, pushY, this);
        if (app.getPrefsManager().getSoundEnabled(
                PreferencesManager.SOUNDS_GAME)) {
            WorldObject.playPullSuccessSound();
        }
        app.getGameManager().morphOther(new Wall(), pushX, pushY,
                WorldConstants.LAYER_OBJECT);
    }

    @Override
    public String getDescription() {
        return "Pullable Blocks Once can only be pulled once, before turning into a wall.";
    }
}