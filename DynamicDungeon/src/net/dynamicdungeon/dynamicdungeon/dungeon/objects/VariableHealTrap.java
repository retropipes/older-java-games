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

public class VariableHealTrap extends AbstractTrap {
    // Fields
    private static final int MIN_HEALING = 1;

    // Constructors
    public VariableHealTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_VARIABLE_HEAL_TRAP);
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
            final int dirY) {
        int maxHealing = PartyManager.getParty().getLeader().getMaximumHP() / 5;
        if (maxHealing < VariableHealTrap.MIN_HEALING) {
            maxHealing = VariableHealTrap.MIN_HEALING;
        }
        final RandomRange healingGiven = new RandomRange(
                VariableHealTrap.MIN_HEALING, maxHealing);
        PartyManager.getParty().getLeader().heal(healingGiven.generate());
        SoundManager.playSound(SoundConstants.SOUND_HEAL);
        DynamicDungeon.getApplication().getGameManager();
        GameLogicManager.decay();
    }

    @Override
    public String getDescription() {
        return "Variable Heal Traps heal you when stepped on, then disappear.";
    }
}