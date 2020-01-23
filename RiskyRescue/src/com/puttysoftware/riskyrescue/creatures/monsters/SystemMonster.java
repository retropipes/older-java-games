/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.creatures.monsters;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.riskyrescue.ai.AIRoutine;
import com.puttysoftware.riskyrescue.ai.RandomAIRoutinePicker;
import com.puttysoftware.riskyrescue.assets.ImageManager;
import com.puttysoftware.riskyrescue.assets.MonsterNames;
import com.puttysoftware.riskyrescue.creatures.Creature;
import com.puttysoftware.riskyrescue.creatures.StatConstants;
import com.puttysoftware.riskyrescue.creatures.party.PartyManager;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScript;
import com.puttysoftware.riskyrescue.spells.SpellBook;

public class SystemMonster extends Creature {
    // Fields
    private String type;
    protected static final long MINIMUM_EXPERIENCE_RANDOM_VARIANCE = -2;
    protected static final long MAXIMUM_EXPERIENCE_RANDOM_VARIANCE = 2;
    protected static final int GOLD_TOUGHNESS_MULTIPLIER = 1;
    private static final int BATTLES_SCALE_FACTOR = 2;
    private static final int BATTLES_START = 2;

    // Constructors
    public SystemMonster() {
        super();
        this.setAI(SystemMonster.getInitialAI());
        final SpellBook spells = new SystemMonsterSpellBook();
        spells.learnAllSpells();
        this.setSpellBook(spells);
        this.image = this.getInitialImage();
        final int newLevel = PartyManager.getDungeonLevel();
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
        this.setExperience(this.getInitialExperience());
    }

    // Methods
    @Override
    public String getName() {
        return this.type;
    }

    @Override
    public boolean checkLevelUp() {
        return false;
    }

    @Override
    protected InternalScript levelUpHook() {
        return null;
    }

    private final void setType(final String newType) {
        this.type = newType;
    }

    @Override
    protected BufferedImageIcon getInitialImage() {
        if (this.getLevel() == 0) {
            return null;
        } else {
            final int dungeonIndex = PartyManager.getMapLevel();
            final String[] types = MonsterNames.getAllNames(dungeonIndex);
            final RandomRange r = new RandomRange(0, types.length - 1);
            final int nameIndex = r.generate();
            this.setType(types[nameIndex]);
            return ImageManager.getMonsterImage(dungeonIndex, nameIndex);
        }
    }

    public void loadMonster() {
        this.image = this.getInitialImage();
    }

    // Helper Methods
    private static AIRoutine getInitialAI() {
        return RandomAIRoutinePicker.getNextRoutine();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        final int result = super.hashCode();
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
        if (!(obj instanceof SystemMonster)) {
            return false;
        }
        final SystemMonster other = (SystemMonster) obj;
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
        return SystemMonster.BATTLES_START
                + (PartyManager.getParty().getLeader().getLevel() + 1)
                        * SystemMonster.BATTLES_SCALE_FACTOR;
    }

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
                * SystemMonster.MINIMUM_EXPERIENCE_RANDOM_VARIANCE);
        maxvar = (int) (this.getLevel()
                * SystemMonster.MAXIMUM_EXPERIENCE_RANDOM_VARIANCE);
        final RandomRange r = new RandomRange(minvar, maxvar);
        final long expbase = PartyManager.getParty().getPartyMaxToNextLevel();
        final long factor = SystemMonster.getBattlesToNextLevel();
        return expbase / factor + r.generate();
    }

    private int getToughness() {
        return this.getStrength() + this.getBlock() + this.getAgility()
                + this.getVitality() + this.getIntelligence() + this.getLuck();
    }

    private int getInitialGold() {
        final int min = 0;
        final int max = this.getToughness()
                * SystemMonster.GOLD_TOUGHNESS_MULTIPLIER;
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
}
