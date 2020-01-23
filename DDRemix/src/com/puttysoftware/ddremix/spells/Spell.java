/*  DDRemix: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.spells;

import com.puttysoftware.ddremix.battle.BattleTarget;
import com.puttysoftware.ddremix.effects.Effect;

public class Spell {
    // Fields
    private final Effect effect;
    private final int cost;
    private final BattleTarget target;
    private final int soundEffect;

    // Constructors
    public Spell() {
        super();
        this.effect = null;
        this.cost = 0;
        this.target = null;
        this.soundEffect = -1;
    }

    public Spell(final Effect newEffect, final int newCost,
            final BattleTarget newTarget, final int sfx) {
        super();
        this.effect = newEffect;
        this.cost = newCost;
        this.target = newTarget;
        this.soundEffect = sfx;
    }

    public Effect getEffect() {
        return this.effect;
    }

    public int getCost() {
        return this.cost;
    }

    BattleTarget getTarget() {
        return this.target;
    }

    int getSound() {
        return this.soundEffect;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.cost;
        result = prime * result
                + (this.effect == null ? 0 : this.effect.hashCode());
        result = prime * result + this.soundEffect;
        return prime * result
                + (this.target == null ? 0 : this.target.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Spell)) {
            return false;
        }
        final Spell other = (Spell) obj;
        if (this.cost != other.cost) {
            return false;
        }
        if (this.effect == null) {
            if (other.effect != null) {
                return false;
            }
        } else if (!this.effect.equals(other.effect)) {
            return false;
        }
        if (this.soundEffect != other.soundEffect) {
            return false;
        }
        if (this.target != other.target) {
            return false;
        }
        return true;
    }
}
