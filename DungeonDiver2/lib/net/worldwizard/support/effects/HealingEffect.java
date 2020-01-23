package net.worldwizard.support.effects;

import net.worldwizard.support.creatures.Creature;
import net.worldwizard.support.creatures.StatConstants;

public class HealingEffect extends Effect {
    // Constructor
    public HealingEffect(final String buffName, final int HPAddition,
            final int newRounds, final double factor, final int scaleStat,
            final double decay) {
        super(buffName, newRounds);
        this.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                HPAddition, factor, scaleStat);
        this.setDecayRate(StatConstants.STAT_CURRENT_HP, decay);
    }

    @Override
    public void customUseLogic(final Creature source, final Creature target) {
        if (target.isAlive()) {
            target.heal((int) this.getEffect(Effect.EFFECT_ADD,
                    StatConstants.STAT_CURRENT_HP));
        }
    }
}
