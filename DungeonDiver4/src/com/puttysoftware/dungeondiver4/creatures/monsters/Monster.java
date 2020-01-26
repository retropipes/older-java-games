/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.creatures.monsters;

import com.puttysoftware.dungeondiver4.ai.map.AbstractMapAIRoutine;
import com.puttysoftware.dungeondiver4.ai.map.RandomMapAIRoutinePicker;
import com.puttysoftware.dungeondiver4.ai.window.AbstractWindowAIRoutine;
import com.puttysoftware.dungeondiver4.ai.window.RandomWindowAIRoutinePicker;
import com.puttysoftware.dungeondiver4.creatures.AbstractCreature;
import com.puttysoftware.dungeondiver4.creatures.StatConstants;
import com.puttysoftware.dungeondiver4.creatures.faiths.Faith;
import com.puttysoftware.dungeondiver4.creatures.faiths.FaithManager;
import com.puttysoftware.dungeondiver4.creatures.party.PartyManager;
import com.puttysoftware.dungeondiver4.creatures.party.PartyMember;
import com.puttysoftware.dungeondiver4.items.Shop;
import com.puttysoftware.dungeondiver4.resourcemanagers.MonsterImageManager;
import com.puttysoftware.dungeondiver4.resourcemanagers.MonsterNames;
import com.puttysoftware.dungeondiver4.spells.SpellBook;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.randomrange.RandomRange;

public class Monster extends AbstractCreature {
    // Fields
    private String type;
    private Element element;
    private final int perfectBonusGold;
    private static final double MINIMUM_EXPERIENCE_RANDOM_VARIANCE = -5.0 / 2.0;
    private static final double MAXIMUM_EXPERIENCE_RANDOM_VARIANCE = 5.0 / 2.0;
    private static final int GOLD_TOUGHNESS_MULTIPLIER = 6;
    private static final int BATTLES_SCALE_FACTOR = 2;
    private static final int BATTLES_START = 2;

    // Constructors
    Monster() {
        super(true);
        this.setMapAI(Monster.getInitialMapAI());
        this.setWindowAI(Monster.getInitialWindowAI());
        this.element = new Element(FaithManager.getFaith(0));
        final SpellBook spells = new MonsterSpellBook();
        spells.learnAllSpells();
        this.setSpellBook(spells);
        this.perfectBonusGold = this.getInitialPerfectBonusGold();
        this.element = Monster.getInitialElement();
        this.image = this.getInitialImage();
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

    private double adjustForLevelDifference() {
        return this.getLevelDifference() / 4.0 + 1.0;
    }

    @Override
    protected BufferedImageIcon getInitialImage() {
        if (this.getLevel() == 0) {
            return null;
        } else {
            final String[] types = MonsterNames.getAllNames();
            final RandomRange r = new RandomRange(0, types.length - 1);
            this.setType(types[r.generate()]);
            return MonsterImageManager.getImage(this.getType(),
                    this.getElement());
        }
    }

    private static Element getInitialElement() {
        return new Element(FaithManager.getRandomFaith());
    }

    public void loadMonster() {
        this.image = this.getInitialImage();
        final int newLevel = PartyManager.getParty().getPartyMeanLevel();
        this.setLevel(newLevel);
        this.setVitality(this.getInitialVitality());
        this.setCurrentHP(this.getMaximumHP());
        this.setIntelligence(this.getInitialIntelligence());
        this.setCurrentMP(this.getMaximumMP());
        this.setStrength(this.getInitialStrength());
        this.setBlock(this.getInitialBlock());
        this.setAgility(this.getInitialAgility());
        this.setLuck(this.getInitialLuck());
        this.setGold(this.getInitialGold());
        this.setExperience((long) (this.getInitialExperience()
                * this.adjustForLevelDifference()));
        this.setAttacksPerRound(1);
        this.setSpellsPerRound(1);
        this.image = this.getInitialImage();
    }

    // Helper Methods
    private int getInitialStrength() {
        final RandomRange r = new RandomRange(1,
                Math.max(this.getLevel() * StatConstants.GAIN_STRENGTH, 1));
        return r.generate();
    }

    private int getInitialBlock() {
        final RandomRange r = new RandomRange(0,
                this.getLevel() * StatConstants.GAIN_BLOCK);
        return r.generate();
    }

    private long getInitialExperience() {
        int minvar, maxvar;
        minvar = (int) (this.getLevel()
                * Monster.MINIMUM_EXPERIENCE_RANDOM_VARIANCE);
        maxvar = (int) (this.getLevel()
                * Monster.MAXIMUM_EXPERIENCE_RANDOM_VARIANCE);
        final RandomRange r = new RandomRange(minvar, maxvar);
        final long expbase = PartyManager.getParty().getPartyMaxToNextLevel();
        final long factor = this.getBattlesToNextLevel();
        return expbase / factor + r.generateLong();
    }

    private int getToughness() {
        return this.getStrength() + this.getBlock() + this.getAgility()
                + this.getVitality() + this.getIntelligence() + this.getLuck();
    }

    private int getInitialGold() {
        final int min = 0;
        final int max = this.getToughness() * Monster.GOLD_TOUGHNESS_MULTIPLIER;
        final RandomRange r = new RandomRange(min, max);
        return r.generate();
    }

    private int getInitialAgility() {
        final RandomRange r = new RandomRange(1,
                Math.max(this.getLevel() * StatConstants.GAIN_AGILITY, 1));
        return r.generate();
    }

    private int getInitialVitality() {
        final RandomRange r = new RandomRange(1,
                Math.max(this.getLevel() * StatConstants.GAIN_VITALITY, 1));
        return r.generate();
    }

    private int getInitialIntelligence() {
        final RandomRange r = new RandomRange(0,
                this.getLevel() * StatConstants.GAIN_INTELLIGENCE);
        return r.generate();
    }

    private int getInitialLuck() {
        final RandomRange r = new RandomRange(0,
                this.getLevel() * StatConstants.GAIN_LUCK);
        return r.generate();
    }

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
        if (!(obj instanceof Monster)) {
            return false;
        }
        final Monster other = (Monster) obj;
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

    private final int getBattlesToNextLevel() {
        return Monster.BATTLES_START
                + (this.getLevel() + 1) * Monster.BATTLES_SCALE_FACTOR;
    }
}
