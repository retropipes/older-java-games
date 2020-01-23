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

public class PushableBlockThrice extends GenericMovableObject {
    // Constructors
    public PushableBlockThrice() {
        super(true, false);
    }

    @Override
    public String getName() {
        return "Pushable Block Thrice";
    }

    @Override
    public String getPluralName() {
        return "Pushable Blocks Thrice";
    }

    @Override
    public void pushAction(final ObjectInventory inv, final WorldObject mo,
            final int x, final int y, final int pushX, final int pushY) {
        final Application app = Worldz.getApplication();
        app.getGameManager().updatePushedPosition(x, y, pushX, pushY, this);
        if (app.getPrefsManager().getSoundEnabled(
                PreferencesManager.SOUNDS_GAME)) {
            WorldObject.playPushSuccessSound();
        }
        app.getGameManager().morphOther(new PushableBlockTwice(), pushX, pushY,
                WorldConstants.LAYER_OBJECT);
    }

    @Override
    public String getDescription() {
        return "Pushable Blocks Thrice can only be pushed three times, before turning into a wall.";
    }
}