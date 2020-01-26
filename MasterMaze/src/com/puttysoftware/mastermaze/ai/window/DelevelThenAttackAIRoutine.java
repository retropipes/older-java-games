package com.puttysoftware.mastermaze.ai.window;

import com.puttysoftware.mastermaze.creatures.Creature;
import com.puttysoftware.mastermaze.spells.Spell;
import com.puttysoftware.randomrange.RandomRange;

public class DelevelThenAttackAIRoutine extends WindowAIRoutine {
    // Fields
    private int delevelRounds;
    private static final int DELEVEL_CHANCE = 60;

    // Constructors
    public DelevelThenAttackAIRoutine() {
        this.delevelRounds = 0;
    }

    @Override
    public int getNextAction(final Creature c) {
        if (this.delevelRounds > 0) {
            this.delevelRounds--;
        }
        Spell delevel = null;
        final RandomRange whichSpell = new RandomRange(1, 2);
        if (whichSpell.generate() == 1) {
            delevel = c.getSpellBook().getSpellByID(2);
        } else {
            delevel = c.getSpellBook().getSpellByID(3);
        }
        final int cost = delevel.getCost();
        final int currMP = c.getCurrentMP();
        if (cost <= currMP && this.delevelRounds == 0) {
            final RandomRange chance = new RandomRange(1, 100);
            if (chance
                    .generate() <= DelevelThenAttackAIRoutine.DELEVEL_CHANCE) {
                this.delevelRounds = delevel.getEffect().getInitialRounds();
                this.spell = delevel;
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
}
