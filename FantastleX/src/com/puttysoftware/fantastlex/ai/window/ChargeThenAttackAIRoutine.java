package com.puttysoftware.fantastlex.ai.window;

import com.puttysoftware.fantastlex.creatures.AbstractCreature;
import com.puttysoftware.fantastlex.spells.Spell;
import com.puttysoftware.randomrange.RandomRange;

public class ChargeThenAttackAIRoutine extends AbstractWindowAIRoutine {
    // Fields
    private int chargeRounds;
    private static final int CHARGE_CHANCE = 60;

    // Constructors
    public ChargeThenAttackAIRoutine() {
        this.chargeRounds = 0;
    }

    @Override
    public int getNextAction(final AbstractCreature c) {
        if (this.chargeRounds > 0) {
            this.chargeRounds--;
        }
        Spell charge = null;
        final RandomRange whichSpell = new RandomRange(1, 2);
        if (whichSpell.generate() == 1) {
            charge = c.getSpellBook().getSpellByID(4);
        } else {
            charge = c.getSpellBook().getSpellByID(5);
        }
        final int cost = charge.getCost();
        final int currMP = c.getCurrentMP();
        if (cost <= currMP && this.chargeRounds == 0) {
            final RandomRange chance = new RandomRange(1, 100);
            if (chance.generate() <= ChargeThenAttackAIRoutine.CHARGE_CHANCE) {
                this.chargeRounds = charge.getEffect().getInitialRounds();
                this.spell = charge;
                return AbstractWindowAIRoutine.ACTION_CAST_SPELL;
            } else {
                this.spell = null;
                return AbstractWindowAIRoutine.ACTION_ATTACK;
            }
        } else {
            this.spell = null;
            return AbstractWindowAIRoutine.ACTION_ATTACK;
        }
    }
}
