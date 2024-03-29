/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.creatures.monsters;

import com.puttysoftware.dungeondiver3.support.creatures.PartyManager;
import com.puttysoftware.dungeondiver3.support.creatures.StatConstants;
import com.puttysoftware.randomrange.RandomRange;

abstract class BothRandomScalingBaseMonster extends BothRandomBaseMonster {
    // Constructors
    BothRandomScalingBaseMonster() {
        super();
    }

    @Override
    public void loadMonster() {
        final int newLevel = PartyManager.getParty().getDungeonLevel();
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
        this.setAttacksPerRound(1);
        this.setSpellsPerRound(1);
        this.image = this.getInitialImage();
    }

    @Override
    public boolean scales() {
        return true;
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
                * BaseMonster.MINIMUM_EXPERIENCE_RANDOM_VARIANCE);
        maxvar = (int) (this.getLevel()
                * BaseMonster.MAXIMUM_EXPERIENCE_RANDOM_VARIANCE);
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
        final int max = this.getToughness()
                * BaseMonster.GOLD_TOUGHNESS_MULTIPLIER;
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
