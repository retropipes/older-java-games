package com.puttysoftware.mazerunner2.ai.window;

import com.puttysoftware.mazerunner2.creatures.AbstractCreature;
import com.puttysoftware.mazerunner2.spells.Spell;
import com.puttysoftware.randomrange.RandomRange;

public class PoisonAndHealAIRoutine extends AbstractWindowAIRoutine {
    // Fields
    private int poisonRounds;
    private static final int POISON_CHANCE = 75;
    private static final int HEAL_CHANCE = 95;
    private static final double HEAL_PERCENT = 0.25;

    // Constructors
    public PoisonAndHealAIRoutine() {
        this.poisonRounds = 0;
    }

    @Override
    public int getNextAction(AbstractCreature c) {
        if (this.poisonRounds > 0) {
            this.poisonRounds--;
        }
        RandomRange whichAction = new RandomRange(1, 2);
        int action = whichAction.generate();
        Spell which = null;
        if (action == 1) {
            which = c.getSpellBook().getSpellByID(0);
        } else {
            which = c.getSpellBook().getSpellByID(1);
        }
        int cost = which.getCost();
        int currMP = c.getCurrentMP();
        if (action == 1) {
            if (cost <= currMP && this.poisonRounds == 0) {
                RandomRange chance = new RandomRange(1, 100);
                if (chance.generate() <= PoisonAndHealAIRoutine.POISON_CHANCE) {
                    this.poisonRounds = which.getEffect().getInitialRounds();
                    this.spell = which;
                    return AbstractWindowAIRoutine.ACTION_CAST_SPELL;
                } else {
                    this.spell = null;
                    return AbstractWindowAIRoutine.ACTION_ATTACK;
                }
            } else {
                this.spell = null;
                return AbstractWindowAIRoutine.ACTION_ATTACK;
            }
        } else {
            if (cost <= currMP) {
                int currHP = c.getCurrentHP();
                int targetHP = (int) (currHP * PoisonAndHealAIRoutine.HEAL_PERCENT);
                if (currHP <= targetHP) {
                    RandomRange chance = new RandomRange(1, 100);
                    if (chance.generate() <= PoisonAndHealAIRoutine.HEAL_CHANCE) {
                        this.spell = which;
                        return AbstractWindowAIRoutine.ACTION_CAST_SPELL;
                    } else {
                        this.spell = null;
                        return AbstractWindowAIRoutine.ACTION_ATTACK;
                    }
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
}
