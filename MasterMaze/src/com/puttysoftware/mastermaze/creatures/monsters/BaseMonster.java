/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.creatures.monsters;

import com.puttysoftware.mastermaze.ai.map.MapAIRoutine;
import com.puttysoftware.mastermaze.ai.map.RandomMapAIRoutinePicker;
import com.puttysoftware.mastermaze.ai.window.RandomWindowAIRoutinePicker;
import com.puttysoftware.mastermaze.ai.window.WindowAIRoutine;
import com.puttysoftware.mastermaze.creatures.Creature;
import com.puttysoftware.mastermaze.creatures.PartyManager;
import com.puttysoftware.mastermaze.creatures.PartyMember;
import com.puttysoftware.mastermaze.creatures.faiths.Faith;
import com.puttysoftware.mastermaze.creatures.faiths.FaithManager;
import com.puttysoftware.mastermaze.items.Shop;
import com.puttysoftware.mastermaze.spells.SpellBook;
import com.puttysoftware.randomrange.RandomRange;

public abstract class BaseMonster extends Creature {
    // Fields
    private String type;
    protected Element element;
    private final int perfectBonusGold;
    protected static final double MINIMUM_EXPERIENCE_RANDOM_VARIANCE = -5.0
            / 2.0;
    protected static final double MAXIMUM_EXPERIENCE_RANDOM_VARIANCE = 5.0
            / 2.0;
    protected static final int GOLD_TOUGHNESS_MULTIPLIER = 6;
    private static final int BATTLES_SCALE_FACTOR = 2;
    private static final int BATTLES_START = 2;

    // Constructors
    BaseMonster() {
        super(true);
        this.setMapAI(BaseMonster.getInitialMapAI());
        this.setWindowAI(BaseMonster.getInitialWindowAI());
        this.element = new Element(FaithManager.getFaith(0));
        final SpellBook spells = new SystemMonsterSpellBook();
        spells.learnAllSpells();
        this.setSpellBook(spells);
        this.perfectBonusGold = this.getInitialPerfectBonusGold();
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

    public int getPerfectBonusGold() {
        return this.perfectBonusGold;
    }

    private int getInitialPerfectBonusGold() {
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        final int needed = Shop.getEquipmentCost(playerCharacter.getLevel() + 1)
                * 24;
        final int factor = this.getBattlesToNextLevel();
        final int min = needed / factor / 4;
        final int max = needed / factor / 2;
        final RandomRange r = new RandomRange(min, max);
        return (int) (r.generate() * this.adjustForLevelDifference());
    }

    public int getLevelDifference() {
        return this.getLevel() - PartyManager.getParty().getLeader().getLevel();
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

    public abstract boolean dynamic();

    public abstract void loadMonster();

    protected double adjustForLevelDifference() {
        return this.getLevelDifference() / 4.0 + 1.0;
    }

    // Helper Methods
    private static MapAIRoutine getInitialMapAI() {
        return RandomMapAIRoutinePicker.getNextRoutine();
    }

    private static WindowAIRoutine getInitialWindowAI() {
        return RandomWindowAIRoutinePicker.getNextRoutine();
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

    protected final int getBattlesToNextLevel() {
        return BaseMonster.BATTLES_START
                + (this.getLevel() + 1) * BaseMonster.BATTLES_SCALE_FACTOR;
    }
}
