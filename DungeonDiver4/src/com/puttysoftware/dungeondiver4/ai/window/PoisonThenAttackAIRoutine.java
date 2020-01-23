package com.puttysoftware.dungeondiver4.ai.window;

import com.puttysoftware.dungeondiver4.creatures.AbstractCreature;
import com.puttysoftware.dungeondiver4.spells.Spell;
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
    public int getNextAction(AbstractCreature c) {
        if (this.poisonRounds > 0) {
            this.poisonRounds--;
        }
        Spell poison = c.getSpellBook().getSpellByID(0);
        int cost = poison.getCost();
        int currMP = c.getCurrentMP();
        if (cost <= currMP && this.poisonRounds == 0) {
            RandomRange chance = new RandomRange(1, 100);
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
