package studio.ignitionigloogames.dungeondiver1.creatures.buffs;

import studio.ignitionigloogames.dungeondiver1.creatures.Creature;
import studio.ignitionigloogames.dungeondiver1.creatures.StatConstants;

public class RegeneratingBuff extends Buff {
    private static final long serialVersionUID = 2346493628355023L;

    // Constructor
    public RegeneratingBuff(final String buffName, final int MPAddition,
            final int newRounds, final double factor, final int scaleStat,
            final double decay) {
        super(buffName, newRounds);
        this.setAffectedStat(StatConstants.STAT_CURRENT_MP);
        this.setEffect(Buff.EFFECT_ADD, MPAddition, factor, scaleStat);
        this.setDecayRate(decay);
    }

    public RegeneratingBuff(final String buffName, final int MPAddition,
            final int newRounds, final double factor, final int scaleStat,
            final double decay, final double rScaleFactor,
            final int rScaleStat) {
        super(buffName, newRounds, rScaleFactor, rScaleStat);
        this.setAffectedStat(StatConstants.STAT_CURRENT_MP);
        this.setEffect(Buff.EFFECT_ADD, MPAddition, factor, scaleStat);
        this.setDecayRate(decay);
    }

    @Override
    public void customUseLogic(final Creature c) {
        if (c.isAlive()) {
            c.regenerate((int) this.getEffect(Buff.EFFECT_ADD, c));
        }
    }
}
