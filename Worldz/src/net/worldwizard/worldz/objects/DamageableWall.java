/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.generic.GenericWall;

public class DamageableWall extends GenericWall {
    // Constructors
    public DamageableWall() {
        super();
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        this.moveFailedAction(true, locX, locY, inv);
        return false;
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        // Crack the wall
        final int pz = Worldz.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationZ();
        Worldz.getApplication().getGameManager().morph(new CrackedWall(), dirX,
                dirY, pz);
        // Play move failed sound, if it's enabled
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveFailedSound();
        }
    }

    @Override
    public String getName() {
        return "Damageable Wall";
    }

    @Override
    public String getPluralName() {
        return "Damageable Walls";
    }

    @Override
    public String getGameName() {
        return "Wall";
    }

    @Override
    public String getMoveFailedSoundName() {
        return "crack";
    }

    @Override
    public String getDescription() {
        return "Damageable Walls turn into Cracked Walls when hit.";
    }
}