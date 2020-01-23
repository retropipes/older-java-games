package net.worldwizard.support.effects;

import net.worldwizard.support.creatures.Creature;
import net.worldwizard.support.creatures.StatConstants;

public class DrainEffect extends Effect {
    // Constructor
    public DrainEffect(final String buffName, final int MPReduction,
            final int newRounds, final double factor, final int scaleStat,
            final double decay) {
        super(buffName, newRounds);
        this.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_MP,
                MPReduction, factor, scaleStat);
        this.setDecayRate(StatConstants.STAT_CURRENT_MP, decay);
    }

    @Override
    public void customUseLogic(final Creature source, final Creature target) {
        if (target.isAlive()) {
            target.drain((int) this.getEffect(Effect.EFFECT_ADD,
                    StatConstants.STAT_CURRENT_MP));
        }
    }
}
