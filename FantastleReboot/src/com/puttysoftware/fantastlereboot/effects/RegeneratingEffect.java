package com.puttysoftware.fantastlereboot.effects;

import com.puttysoftware.fantastlereboot.creatures.Creature;
import com.puttysoftware.fantastlereboot.creatures.StatConstants;

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

  @Override
  public void customUseLogic(final Creature c) {
    if (c.isAlive()) {
      c.regenerate((int) this.getEffect(Effect.EFFECT_ADD));
    }
  }
}
