/*  DynamicDungeon: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DynamicDungeon@worldwizard.net
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.creatures.party.PartyManager;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractTrap;
import net.dynamicdungeon.dynamicdungeon.game.GameLogicManager;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;
import net.dynamicdungeon.randomrange.RandomRange;

public class VariableHurtTrap extends AbstractTrap {
    // Fields
    private static final int MIN_DAMAGE = 1;

    // Constructors
    public VariableHurtTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_VARIABLE_HURT_TRAP);
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
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        int maxDamage = PartyManager.getParty().getLeader().getMaximumHP() / 5;
        if (maxDamage < VariableHurtTrap.MIN_DAMAGE) {
            maxDamage = VariableHurtTrap.MIN_DAMAGE;
        }
        final RandomRange damageDealt = new RandomRange(
                VariableHurtTrap.MIN_DAMAGE, maxDamage);
        PartyManager.getParty().getLeader().doDamage(damageDealt.generate());
        SoundManager.playSound(SoundConstants.SOUND_BARRIER);
        DynamicDungeon.getApplication().getGameManager();
        GameLogicManager.decay();
    }

    @Override
    public String getDescription() {
        return "Variable Hurt Traps hurt you when stepped on, then disappear.";
    }
}