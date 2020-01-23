/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.generic.GenericTrap;

public class WallMakingTrap extends GenericTrap {
    public WallMakingTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Wall-Making Trap";
    }

    @Override
    public String getPluralName() {
        return "Wall-Making Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveSuccessSound();
        }
        Worldz.getApplication().getGameManager().delayedDecayTo(new Wall());
    }

    @Override
    public String getDescription() {
        return "Wall-Making Traps create a Wall when you step OFF them.";
    }
}
