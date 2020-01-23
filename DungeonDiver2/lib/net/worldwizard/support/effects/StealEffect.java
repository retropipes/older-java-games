package net.worldwizard.support.effects;

import net.worldwizard.support.creatures.Creature;
import net.worldwizard.support.creatures.StatConstants;

public class StealEffect extends Effect {
    // Constructor
    public StealEffect(final String buffName, final int GPAddition,
            final int newRounds, final double factor, final int scaleStat,
            final double decay) {
        super(buffName, newRounds);
        this.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_GOLD, GPAddition,
                factor, scaleStat);
        this.setDecayRate(StatConstants.STAT_GOLD, decay);
    }

    @Override
    public void customUseLogic(final Creature source, final Creature target) {
        if (target.isAlive()) {
            target.offsetGold((int) this.getEffect(Effect.EFFECT_ADD,
                    StatConstants.STAT_GOLD));
        }
    }
}
