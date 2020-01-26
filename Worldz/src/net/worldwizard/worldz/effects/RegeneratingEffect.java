package net.worldwizard.worldz.effects;

import net.worldwizard.worldz.creatures.Creature;
import net.worldwizard.worldz.creatures.StatConstants;

public class RegeneratingEffect extends Effect {
    // Constructor
    public RegeneratingEffect(final String buffName, final int MPAddition,
            final int newRounds, final double factor, final int scaleStat,
            final double decay) {
        super(buffName, newRounds);
        this.setAffectedStat(StatConstants.STAT_CURRENT_MP);
        this.setEffect(Effect.EFFECT_ADD, MPAddition, factor, scaleStat);
        this.setDecayRate(decay);
    }

    public RegeneratingEffect(final String buffName, final int MPAddition,
            final int newRounds, final double factor, final int scaleStat,
            final double decay, final double rScaleFactor,
            final int rScaleStat) {
        super(buffName, newRounds, rScaleFactor, rScaleStat);
        this.setAffectedStat(StatConstants.STAT_CURRENT_MP);
        this.setEffect(Effect.EFFECT_ADD, MPAddition, factor, scaleStat);
        this.setDecayRate(decay);
    }

    @Override
    public void customUseLogic(final Creature source, final Creature target) {
        if (target.isAlive()) {
            target.regenerate((int) this.getEffect(Effect.EFFECT_ADD));
        }
    }
}
