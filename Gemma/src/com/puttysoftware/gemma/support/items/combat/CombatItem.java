/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.items.combat;

import com.puttysoftware.gemma.support.creatures.BattleTarget;
import com.puttysoftware.gemma.support.effects.Effect;
import com.puttysoftware.gemma.support.items.Item;

public class CombatItem extends Item {
    // Fields
    private final BattleTarget target;
    protected Effect e;
    protected int sound;

    // Constructors
    public CombatItem() {
        super();
        this.target = null;
    }

    public CombatItem(final String itemName, final int itemBuyPrice,
            final BattleTarget itemTarget) {
        super(itemName, 1, 0);
        this.setCombatUsable(true);
        this.setBuyPrice(itemBuyPrice);
        this.target = itemTarget;
        this.defineFields();
    }

    // Methods
    final BattleTarget getTarget() {
        return this.target;
    }

    final Effect getEffect() {
        return this.e;
    }

    final int getSound() {
        return this.sound;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.e == null) ? 0 : this.e.hashCode());
        result = prime * result + this.sound;
        return prime * result
                + ((this.target == null) ? 0 : this.target.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof CombatItem)) {
            return false;
        }
        CombatItem other = (CombatItem) obj;
        if (this.e == null) {
            if (other.e != null) {
                return false;
            }
        } else if (!this.e.equals(other.e)) {
            return false;
        }
        if (this.sound != other.sound) {
            return false;
        }
        if (this.target != other.target) {
            return false;
        }
        return true;
    }

    protected void defineFields() {
        // Do nothing
    }
}