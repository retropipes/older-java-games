package net.worldwizard.dungeondiver.creatures.buffs;

import net.worldwizard.dungeondiver.creatures.Creature;
import net.worldwizard.dungeondiver.creatures.StatConstants;

public class DamageBuff extends Buff {
    private static final long serialVersionUID = 4693443300030L;

    // Constructor
    public DamageBuff(final String buffName, final int HPReduction,
            final int newRounds, final double factor, final int scaleStat,
            final double decay) {
        super(buffName, newRounds);
        this.setAffectedStat(StatConstants.STAT_CURRENT_HP);
        this.setEffect(Buff.EFFECT_ADD, HPReduction, factor, scaleStat);
        this.setDecayRate(decay);
    }

    public DamageBuff(final String buffName, final int HPReduction,
            final int newRounds, final double factor, final int scaleStat,
            final double decay, final double rScaleFactor,
            final int rScaleStat) {
        super(buffName, newRounds, rScaleFactor, rScaleStat);
        this.setAffectedStat(StatConstants.STAT_CURRENT_HP);
        this.setEffect(Buff.EFFECT_ADD, HPReduction, factor, scaleStat);
        this.setDecayRate(decay);
    }

    @Override
    public void customUseLogic(final Creature c) {
        if (c.isAlive()) {
            c.doDamage((int) this.getEffect(Buff.EFFECT_ADD, c));
        }
    }
}
