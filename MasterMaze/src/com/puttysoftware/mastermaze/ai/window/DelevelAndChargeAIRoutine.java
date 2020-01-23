package com.puttysoftware.mastermaze.ai.window;

import com.puttysoftware.mastermaze.creatures.Creature;
import com.puttysoftware.mastermaze.spells.Spell;
import com.puttysoftware.randomrange.RandomRange;

public class DelevelAndChargeAIRoutine extends WindowAIRoutine {
    // Fields
    private int delevelRounds;
    private int chargeRounds;
    private static final int DELEVEL_CHANCE = 80;
    private static final int CHARGE_CHANCE = 80;

    // Constructors
    public DelevelAndChargeAIRoutine() {
        this.delevelRounds = 0;
        this.chargeRounds = 0;
    }

    @Override
    public int getNextAction(final Creature c) {
        if (this.delevelRounds > 0) {
            this.delevelRounds--;
        }
        if (this.chargeRounds > 0) {
            this.chargeRounds--;
        }
        Spell which = null;
        final RandomRange whichAction = new RandomRange(1, 2);
        final RandomRange whichSpell = new RandomRange(1, 2);
        final int action = whichAction.generate();
        if (action == 1) {
            if (whichSpell.generate() == 1) {
                which = c.getSpellBook().getSpellByID(2);
            } else {
                which = c.getSpellBook().getSpellByID(3);
            }
        } else {
            if (whichSpell.generate() == 1) {
                which = c.getSpellBook().getSpellByID(4);
            } else {
                which = c.getSpellBook().getSpellByID(5);
            }
        }
        final int cost = which.getCost();
        final int currMP = c.getCurrentMP();
        if (cost <= currMP) {
            final RandomRange chance = new RandomRange(1, 100);
            if (action == 1) {
                if (this.delevelRounds == 0) {
                    if (chance.generate() <= DelevelAndChargeAIRoutine.DELEVEL_CHANCE) {
                        this.delevelRounds = which.getEffect()
                                .getInitialRounds();
                        this.spell = which;
                        return WindowAIRoutine.ACTION_CAST_SPELL;
                    } else {
                        this.spell = null;
                        return WindowAIRoutine.ACTION_ATTACK;
                    }
                } else {
                    this.spell = null;
                    return WindowAIRoutine.ACTION_ATTACK;
                }
            } else {
                if (this.chargeRounds == 0) {
                    if (chance.generate() <= DelevelAndChargeAIRoutine.CHARGE_CHANCE) {
                        this.chargeRounds = which.getEffect()
                                .getInitialRounds();
                        this.spell = which;
                        return WindowAIRoutine.ACTION_CAST_SPELL;
                    } else {
                        this.spell = null;
                        return WindowAIRoutine.ACTION_ATTACK;
                    }
                } else {
                    this.spell = null;
                    return WindowAIRoutine.ACTION_ATTACK;
                }
            }
        } else {
            this.spell = null;
            return WindowAIRoutine.ACTION_ATTACK;
        }
    }
}
