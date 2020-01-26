package net.worldwizard.worldz.effects;

import net.worldwizard.worldz.creatures.Creature;
import net.worldwizard.worldz.creatures.StatConstants;

public class DamageEffect extends Effect {
    // Constructor
    public DamageEffect(final String buffName, final int HPReduction,
            final int newRounds, final double factor, final int scaleStat,
            final double decay) {
        super(buffName, newRounds);
        this.setAffectedStat(StatConstants.STAT_CURRENT_HP);
        this.setEffect(Effect.EFFECT_ADD, HPReduction, factor, scaleStat);
        this.setDecayRate(decay);
    }

    public DamageEffect(final String buffName, final int HPReduction,
            final int newRounds, final double factor, final int scaleStat,
            final double decay, final double rScaleFactor,
            final int rScaleStat) {
        super(buffName, newRounds, rScaleFactor, rScaleStat);
        this.setAffectedStat(StatConstants.STAT_CURRENT_HP);
        this.setEffect(Effect.EFFECT_ADD, HPReduction, factor, scaleStat);
        this.setDecayRate(decay);
    }

    @Override
    public void customUseLogic(final Creature source, final Creature target) {
        if (target.isAlive()) {
            final int srcfid = source.getFaith().getFaithID();
            final double dmgmult = target.getFaith()
                    .getMultiplierForOtherFaith(srcfid);
            final double damage = this.getEffect(Effect.EFFECT_ADD) * dmgmult;
            target.doDamage((int) damage);
        }
    }
}
