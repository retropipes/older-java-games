/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.creatures.PartyManager;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.generic.GenericTrap;
import net.worldwizard.worldz.resourcemanagers.SoundManager;

public class HealTrap extends GenericTrap {
    // Fields
    private int healing;

    // Constructors
    public HealTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Heal Trap";
    }

    @Override
    public String getPluralName() {
        return "Heal Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        this.healing = PartyManager.getParty().getLeader().getMaximumHP() / 50;
        if (this.healing < 1) {
            this.healing = 1;
        }
        PartyManager.getParty().getLeader().heal(this.healing);
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            SoundManager.playSound("barrier");
        }
    }

    @Override
    public String getDescription() {
        return "Heal Traps heal you when stepped on.";
    }
}