package com.puttysoftware.dungeondiver4.ai.window;

import com.puttysoftware.dungeondiver4.creatures.AbstractCreature;
import com.puttysoftware.dungeondiver4.spells.Spell;
import com.puttysoftware.randomrange.RandomRange;

public class HealIfHealthLowAIRoutine extends AbstractWindowAIRoutine {
    // Constants
    private static final int HEAL_CHANCE = 90;
    private static final double HEAL_PERCENT = 0.2;

    @Override
    public int getNextAction(AbstractCreature c) {
        Spell heal = c.getSpellBook().getSpellByID(0);
        int cost = heal.getCost();
        int currMP = c.getCurrentMP();
        if (cost <= currMP) {
            int currHP = c.getCurrentHP();
            int targetHP = (int) (currHP * HealIfHealthLowAIRoutine.HEAL_PERCENT);
            if (currHP <= targetHP) {
                RandomRange chance = new RandomRange(1, 100);
                if (chance.generate() <= HealIfHealthLowAIRoutine.HEAL_CHANCE) {
                    this.spell = heal;
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
