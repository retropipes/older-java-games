/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.creatures.monsters;

import com.puttysoftware.gemma.support.creatures.PartyManager;
import com.puttysoftware.gemma.support.creatures.StatConstants;
import com.puttysoftware.randomrange.RandomRange;

abstract class DefiniteScalingBaseMonster extends DefiniteBaseMonster {
    // Constructors
    DefiniteScalingBaseMonster() {
        super();
    }

    @Override
    public void loadMonster() {
        super.loadMonster();
        int newLevel = PartyManager.getParty().getLeader().getLevel();
        this.setLevel(newLevel);
        this.setVitality(this.getInitialVitality());
        this.setCurrentHP(this.getMaximumHP());
        this.setIntelligence(this.getInitialIntelligence());
        this.setCurrentMP(this.getMaximumMP());
        this.setStrength(this.getInitialStrength());
        this.setBlock(this.getInitialBlock());
        this.setAgility(this.getInitialAgility());
        this.setLuck(this.getInitialLuck());
        this.setGold(DefiniteScalingBaseMonster.getInitialGold());
        this.setExperience(DefiniteScalingBaseMonster.getInitialExperience());
        this.image = this.getInitialImage();
    }

    @Override
    public boolean scales() {
        return true;
    }

    private int getInitialStrength() {
        int base = this.getLevel() * StatConstants.GAIN_STRENGTH;
        final RandomRange r = new RandomRange(base * 2 / 3, base);
        return r.generate();
    }

    private int getInitialBlock() {
        int base = this.getLevel() * StatConstants.GAIN_BLOCK;
        final RandomRange r = new RandomRange(base * 2 / 3, base);
        return r.generate();
    }

    private static long getInitialExperience() {
        return 0;
    }

    private static int getInitialGold() {
        return 0;
    }

    private int getInitialAgility() {
        int base = this.getLevel() * StatConstants.GAIN_AGILITY;
        final RandomRange r = new RandomRange(base * 2 / 3, base);
        return r.generate();
    }

    private int getInitialVitality() {
        int base = this.getLevel() * StatConstants.GAIN_VITALITY;
        final RandomRange r = new RandomRange(base * 2 / 3, base);
        return r.generate();
    }

    private int getInitialIntelligence() {
        int base = this.getLevel() * StatConstants.GAIN_INTELLIGENCE;
        final RandomRange r = new RandomRange(base * 2 / 3, base);
        return r.generate();
    }

    private int getInitialLuck() {
        int base = this.getLevel() * StatConstants.GAIN_LUCK;
        final RandomRange r = new RandomRange(base * 2 / 3, base);
        return r.generate();
    }
}
