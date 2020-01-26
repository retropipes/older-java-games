/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.objects.Empty;
import net.worldwizard.worldz.resourcemanagers.SoundManager;
import net.worldwizard.worldz.world.WorldConstants;

public abstract class GenericGem extends WorldObject {
    // Constructors
    protected GenericGem() {
        super(false);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        Worldz.getApplication().getGameManager().decay();
        this.postMoveActionHook();
        Worldz.getApplication().getGameManager().redrawWorld();
    }

    public abstract void postMoveActionHook();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_GEM);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public int getLayer() {
        return WorldConstants.LAYER_OBJECT;
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        Worldz.getApplication().getGameManager().morph(new Empty(), locX, locY,
                locZ);
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            SoundManager.playSound("shatter");
        }
        return false;
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
