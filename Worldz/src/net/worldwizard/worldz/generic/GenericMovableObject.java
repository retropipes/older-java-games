/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.world.WorldConstants;

public abstract class GenericMovableObject extends WorldObject {
    // Constructors
    protected GenericMovableObject(final boolean pushable,
            final boolean pullable) {
        super(true, pushable, false, false, pullable, false, false, true,
                false, 0);
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
    }

    @Override
    public void pullAction(final ObjectInventory inv, final WorldObject mo,
            final int x, final int y, final int pullX, final int pullY) {
        final Application app = Worldz.getApplication();
        app.getGameManager().updatePulledPosition(x, y, pullX, pullY, this);
        if (app.getPrefsManager().getSoundEnabled(
                PreferencesManager.SOUNDS_GAME)) {
            WorldObject.playPullSuccessSound();
        }
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return WorldConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_MOVABLE);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return WorldObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}