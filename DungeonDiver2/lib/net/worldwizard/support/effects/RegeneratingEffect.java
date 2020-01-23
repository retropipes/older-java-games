package net.worldwizard.support.effects;

import net.worldwizard.support.creatures.Creature;
import net.worldwizard.support.creatures.StatConstants;

public class RegeneratingEffect extends Effect {
    // Constructor
    public RegeneratingEffect(final String buffName, final int MPAddition,
            final int newRounds, final double factor, final int scaleStat,
            final double decay) {
        super(buffName, newRounds);
        this.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_MP,
                MPAddition, factor, scaleStat);
        this.setDecayRate(StatConstants.STAT_CURRENT_MP, decay);
    }

    @Override
    public void customUseLogic(final Creature source, final Creature target) {
        if (target.isAlive()) {
            target.regenerate((int) this.getEffect(Effect.EFFECT_ADD,
                    StatConstants.STAT_CURRENT_MP));
        }
    }
}
