/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.creatures.monsters;

import com.puttysoftware.gemma.support.ai.AIRoutine;
import com.puttysoftware.gemma.support.ai.RandomAIRoutinePicker;
import com.puttysoftware.gemma.support.creatures.Creature;
import com.puttysoftware.gemma.support.creatures.PartyManager;
import com.puttysoftware.gemma.support.creatures.faiths.Faith;
import com.puttysoftware.gemma.support.creatures.faiths.FaithManager;
import com.puttysoftware.gemma.support.scripts.internal.InternalScript;
import com.puttysoftware.gemma.support.spells.SpellBook;

public abstract class BaseMonster extends Creature {
    // Fields
    private String type;
    protected Element element;
    protected static final long MINIMUM_EXPERIENCE_RANDOM_VARIANCE = -2;
    protected static final long MAXIMUM_EXPERIENCE_RANDOM_VARIANCE = 2;
    protected static final int GOLD_TOUGHNESS_MULTIPLIER = 1;
    private static final int BATTLES_SCALE_FACTOR = 2;
    private static final int BATTLES_START = 2;

    // Constructors
    BaseMonster() {
        super(true);
        this.setAI(BaseMonster.getInitialAI());
        this.element = new Element(FaithManager.getFaith(0));
        final SpellBook spells = new SystemMonsterSpellBook();
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
    protected InternalScript levelUpHook() {
        return null;
    }

    final String getType() {
        return this.type;
    }

    final Element getElement() {
        return this.element;
    }

    final void setType(final String newType) {
        this.type = newType;
    }

    public abstract boolean randomAppearance();

    public abstract boolean randomFaith();

    public abstract boolean scales();

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
                + (this.element == null ? 0 : this.element.hashCode());
        return prime * result + (this.type == null ? 0 : this.type.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof BaseMonster)) {
            return false;
        }
        final BaseMonster other = (BaseMonster) obj;
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

    protected final static int getBattlesToNextLevel() {
        return BaseMonster.BATTLES_START
                + (PartyManager.getParty().getLeader().getLevel() + 1)
                        * BaseMonster.BATTLES_SCALE_FACTOR;
    }
}
