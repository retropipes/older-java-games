package com.puttysoftware.mazerunner2.ai.window;

import com.puttysoftware.mazerunner2.creatures.AbstractCreature;
import com.puttysoftware.mazerunner2.spells.Spell;
import com.puttysoftware.randomrange.RandomRange;

public class PoisonThenAttackAIRoutine extends AbstractWindowAIRoutine {
    // Fields
    private int poisonRounds;
    private static final int POISON_CHANCE = 75;

    // Constructors
    public PoisonThenAttackAIRoutine() {
        this.poisonRounds = 0;
    }

    @Override
    public int getNextAction(final AbstractCreature c) {
        if (this.poisonRounds > 0) {
            this.poisonRounds--;
        }
        final Spell poison = c.getSpellBook().getSpellByID(0);
        final int cost = poison.getCost();
        final int currMP = c.getCurrentMP();
        if (cost <= currMP && this.poisonRounds == 0) {
            final RandomRange chance = new RandomRange(1, 100);
            if (chance.generate() <= PoisonThenAttackAIRoutine.POISON_CHANCE) {
                this.poisonRounds = poison.getEffect().getInitialRounds();
                this.spell = poison;
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
