/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.creatures.PartyManager;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.generic.GenericTrap;
import net.worldwizard.worldz.resourcemanagers.SoundManager;

public class VariableHealTrap extends GenericTrap {
    // Fields
    private RandomRange healingGiven;
    private static final int MIN_HEALING = 1;
    private int maxHealing;

    // Constructors
    public VariableHealTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Variable Heal Trap";
    }

    @Override
    public String getPluralName() {
        return "Variable Heal Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        this.maxHealing = PartyManager.getParty().getLeader().getMaximumHP() / 10;
        if (this.maxHealing < VariableHealTrap.MIN_HEALING) {
            this.maxHealing = VariableHealTrap.MIN_HEALING;
        }
        this.healingGiven = new RandomRange(VariableHealTrap.MIN_HEALING,
                this.maxHealing);
        PartyManager.getParty().getLeader().heal(this.healingGiven.generate());
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            SoundManager.playSound("barrier");
        }
        Worldz.getApplication().getGameManager().decay();
    }

    @Override
    public String getDescription() {
        return "Variable Heal Traps heal you when stepped on, then disappear.";
    }
}