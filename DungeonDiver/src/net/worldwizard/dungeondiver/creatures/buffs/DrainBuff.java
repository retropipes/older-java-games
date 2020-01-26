package net.worldwizard.dungeondiver.creatures.buffs;

import net.worldwizard.dungeondiver.creatures.Creature;
import net.worldwizard.dungeondiver.creatures.StatConstants;

public class DrainBuff extends Buff {
    private static final long serialVersionUID = 72352332230582L;

    // Constructor
    public DrainBuff(final String buffName, final int MPReduction,
            final int newRounds, final double factor, final int scaleStat,
            final double decay) {
        super(buffName, newRounds);
        this.setAffectedStat(StatConstants.STAT_CURRENT_MP);
        this.setEffect(Buff.EFFECT_ADD, MPReduction, factor, scaleStat);
        this.setDecayRate(decay);
    }

    public DrainBuff(final String buffName, final int MPReduction,
            final int newRounds, final double factor, final int scaleStat,
            final double decay, final double rScaleFactor,
            final int rScaleStat) {
        super(buffName, newRounds, rScaleFactor, rScaleStat);
        this.setAffectedStat(StatConstants.STAT_CURRENT_MP);
        this.setEffect(Buff.EFFECT_ADD, MPReduction, factor, scaleStat);
        this.setDecayRate(decay);
    }

    @Override
    public void customUseLogic(final Creature c) {
        if (c.isAlive()) {
            c.drain((int) this.getEffect(Buff.EFFECT_ADD, c));
        }
    }
}
