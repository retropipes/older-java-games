/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.creatures.monsters;

import com.puttysoftware.dungeondiver3.support.ai.AIRoutine;
import com.puttysoftware.dungeondiver3.support.ai.RandomAIRoutinePicker;
import com.puttysoftware.dungeondiver3.support.creatures.Creature;
import com.puttysoftware.dungeondiver3.support.creatures.faiths.Faith;
import com.puttysoftware.dungeondiver3.support.creatures.faiths.FaithManager;
import com.puttysoftware.dungeondiver3.support.spells.SpellBook;

public abstract class BaseMonster extends Creature {
    // Fields
    private String type;
    protected Element element;
    protected static final double MINIMUM_EXPERIENCE_RANDOM_VARIANCE = -5.0 / 2.0;
    protected static final double MAXIMUM_EXPERIENCE_RANDOM_VARIANCE = 5.0 / 2.0;
    protected static final int GOLD_TOUGHNESS_MULTIPLIER = 6;
    private static final int BATTLES_SCALE_FACTOR = 2;
    private static final int BATTLES_START = 2;

    // Constructors
    BaseMonster() {
        super(true);
        this.setAI(BaseMonster.getInitialAI());
        this.element = new Element(FaithManager.getFaith(0));
        SpellBook spells = new SystemMonsterSpellBook();
        spells.learnAllSpells();
        this.setSpellBook(spells);
    }

    // Methods
    @Override
    public String getName() {
        return this.element.getName() + " " + this.type;
    }

    @Override
    public Faith getFaith() {
        return this.element.getFaith();
    }

    @Override
    public boolean checkLevelUp() {
        return false;
    }

    @Override
    protected void levelUpHook() {
        // Do nothing
    }

    final String getType() {
        return this.type;
    }

    final Element getElement() {
        return this.element;
    }

    final void setType(String newType) {
        this.type = newType;
    }

    public abstract boolean randomAppearance();

    public abstract boolean randomFaith();

    public abstract boolean scales();

    public abstract boolean dynamic();

    public abstract void loadMonster();

    // Helper Methods
    private static AIRoutine getInitialAI() {
        return RandomAIRoutinePicker.getNextRoutine();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((this.element == null) ? 0 : this.element.hashCode());
        return prime * result
                + ((this.type == null) ? 0 : this.type.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof BaseMonster)) {
            return false;
        }
        BaseMonster other = (BaseMonster) obj;
        if (this.element == null) {
            if (other.element != null) {
                return false;
            }
        } else if (!this.element.equals(other.element)) {
            return false;
        }
        if (this.type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!this.type.equals(other.type)) {
            return false;
        }
        return true;
    }

    protected final int getBattlesToNextLevel() {
        return BaseMonster.BATTLES_START + (this.getLevel() + 1)
                * BaseMonster.BATTLES_SCALE_FACTOR;
    }
}
