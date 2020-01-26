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

public class VariableHurtTrap extends GenericTrap {
    // Fields
    private RandomRange damageDealt;
    private static final int MIN_DAMAGE = 1;
    private int maxDamage;

    // Constructors
    public VariableHurtTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Variable Hurt Trap";
    }

    @Override
    public String getPluralName() {
        return "Variable Hurt Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        this.maxDamage = PartyManager.getParty().getLeader().getMaximumHP()
                / 10;
        if (this.maxDamage < VariableHurtTrap.MIN_DAMAGE) {
            this.maxDamage = VariableHurtTrap.MIN_DAMAGE;
        }
        this.damageDealt = new RandomRange(VariableHurtTrap.MIN_DAMAGE,
                this.maxDamage);
        PartyManager.getParty().getLeader()
                .doDamage(this.damageDealt.generate());
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            SoundManager.playSound("barrier");
        }
        Worldz.getApplication().getGameManager().decay();
    }

    @Override
    public String getDescription() {
        return "Variable Hurt Traps hurt you when stepped on, then disappear.";
    }
}