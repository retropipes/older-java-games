/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.creatures.monsters;

import com.puttysoftware.mazerunner2.ai.map.AbstractMapAIRoutine;
import com.puttysoftware.mazerunner2.ai.map.RandomMapAIRoutinePicker;
import com.puttysoftware.mazerunner2.ai.window.AbstractWindowAIRoutine;
import com.puttysoftware.mazerunner2.ai.window.RandomWindowAIRoutinePicker;
import com.puttysoftware.mazerunner2.creatures.AbstractCreature;
import com.puttysoftware.mazerunner2.creatures.faiths.Faith;
import com.puttysoftware.mazerunner2.creatures.faiths.FaithManager;
import com.puttysoftware.mazerunner2.creatures.party.PartyManager;
import com.puttysoftware.mazerunner2.creatures.party.PartyMember;
import com.puttysoftware.mazerunner2.items.Shop;
import com.puttysoftware.mazerunner2.spells.SpellBook;
import com.puttysoftware.randomrange.RandomRange;

public abstract class AbstractMonster extends AbstractCreature {
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
    AbstractMonster() {
        super(true);
        this.setMapAI(AbstractMonster.getInitialMapAI());
        this.setWindowAI(AbstractMonster.getInitialWindowAI());
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
    private static AbstractMapAIRoutine getInitialMapAI() {
        return RandomMapAIRoutinePicker.getNextRoutine();
    }

    private static AbstractWindowAIRoutine getInitialWindowAI() {
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
        if (!(obj instanceof AbstractMonster)) {
            return false;
        }
        final AbstractMonster other = (AbstractMonster) obj;
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
        return AbstractMonster.BATTLES_START
                + (this.getLevel() + 1) * AbstractMonster.BATTLES_SCALE_FACTOR;
    }
}
