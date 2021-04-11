package studio.ignitionigloogames.dungeondiver1.creatures.spells;

import studio.ignitionigloogames.dungeondiver1.creatures.buffs.Buff;

public class Spell {
    // Fields
    private final Buff effect;
    private final int cost;
    private final char target;

    // Constructors
    public Spell(final Buff newEffect, final int newCost,
            final char newTarget) {
        this.effect = newEffect;
        this.cost = newCost;
        this.target = newTarget;
    }

    public Buff getEffect() {
        return this.effect;
    }

    public int getCost() {
        return this.cost;
    }

    public char getTarget() {
        return this.target;
    }
}