/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.world.WorldConstants;

public abstract class GenericToggleWall extends WorldObject {
    // Constructors
    protected GenericToggleWall(final boolean solidState) {
        super(solidState);
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final Application app = Worldz.getApplication();
        Messager.showMessage("Can't go that way");
        // Play move failed sound, if it's enabled
        if (app.getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveFailedSound();
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
        this.type.set(TypeConstants.TYPE_TOGGLE_WALL);
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
