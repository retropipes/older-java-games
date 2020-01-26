/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.generic.GenericInventoryModifier;

public class NoBoots extends GenericInventoryModifier {
    // Constructors
    public NoBoots() {
        super();
    }

    @Override
    public String getName() {
        return "No Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of No Boots";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        Worldz.getApplication().getGameManager().decay();
        inv.removeAllBoots();
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveSuccessSound();
        }
    }

    @Override
    public String getDescription() {
        return "No Boots remove any boots worn when picked up.";
    }
}
