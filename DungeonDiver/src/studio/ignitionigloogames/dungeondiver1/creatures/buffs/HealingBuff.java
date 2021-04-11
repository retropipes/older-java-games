package studio.ignitionigloogames.dungeondiver1.creatures.buffs;

import studio.ignitionigloogames.dungeondiver1.creatures.Creature;
import studio.ignitionigloogames.dungeondiver1.creatures.StatConstants;

public class HealingBuff extends Buff {
    private static final long serialVersionUID = 2346493628355023L;

    // Constructor
    public HealingBuff(final String buffName, final int HPAddition,
            final int newRounds, final double factor, final int scaleStat,
            final double decay) {
        super(buffName, newRounds);
        this.setAffectedStat(StatConstants.STAT_CURRENT_HP);
        this.setEffect(Buff.EFFECT_ADD, HPAddition, factor, scaleStat);
        this.setDecayRate(decay);
    }

    public HealingBuff(final String buffName, final int HPAddition,
            final int newRounds, final double factor, final int scaleStat,
            final double decay, final double rScaleFactor,
            final int rScaleStat) {
        super(buffName, newRounds, rScaleFactor, rScaleStat);
        this.setAffectedStat(StatConstants.STAT_CURRENT_HP);
        this.setEffect(Buff.EFFECT_ADD, HPAddition, factor, scaleStat);
        this.setDecayRate(decay);
    }

    @Override
    public void customUseLogic(final Creature c) {
        if (c.isAlive()) {
            c.heal((int) this.getEffect(Buff.EFFECT_ADD, c));
        }
    }
}
