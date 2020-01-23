package net.worldwizard.support.effects;

import net.worldwizard.support.creatures.Creature;
import net.worldwizard.support.creatures.StatConstants;

public class DamageEffect extends Effect {
    // Constructors
    public DamageEffect(final String buffName, final int HPReduction,
            final int newRounds, final double factor, final int scaleStat,
            final double decay) {
        super(buffName, newRounds);
        this.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                HPReduction, factor, scaleStat);
        this.setDecayRate(StatConstants.STAT_CURRENT_HP, decay);
    }

    @Override
    public void customUseLogic(final Creature source, final Creature target) {
        if (target.isAlive()) {
            final int srcfid = source.getFaith().getFaithID();
            final double dmgmult = target.getFaith()
                    .getMultiplierForOtherFaith(srcfid);
            final double damage = this.getEffect(Effect.EFFECT_ADD,
                    StatConstants.STAT_CURRENT_HP) * dmgmult;
            target.doDamage((int) damage);
        }
    }
}
