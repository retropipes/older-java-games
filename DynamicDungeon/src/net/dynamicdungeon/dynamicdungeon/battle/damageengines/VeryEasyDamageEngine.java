/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.battle.damageengines;

import net.dynamicdungeon.dynamicdungeon.creatures.AbstractCreature;
import net.dynamicdungeon.dynamicdungeon.creatures.StatConstants;
import net.dynamicdungeon.randomrange.RandomRange;

class VeryEasyDamageEngine extends AbstractDamageEngine {
    private static final int MULTIPLIER_MIN = 9000;
    private static final int MULTIPLIER_MAX = 18000;
    private static final int MULTIPLIER_MIN_CRIT = 25000;
    private static final int MULTIPLIER_MAX_CRIT = 50000;
    private static final int FUMBLE_CHANCE = 0;
    private static final int PIERCE_CHANCE = 2000;
    private static final int CRIT_CHANCE = 2000;
    private boolean dodged = false;
    private boolean missed = false;
    private boolean crit = false;
    private boolean pierce = false;
    private boolean fumble = false;

    @Override
    public int computeDamage(final AbstractCreature enemy,
	    final AbstractCreature acting) {
	// Compute Damage
	final double attack = acting.getEffectedAttack();
	final double defense = enemy
		.getEffectedStat(StatConstants.STAT_DEFENSE);
	final int power = acting.getItems().getTotalPower();
	this.didFumble();
	if (this.fumble) {
	    // Fumble!
	    return CommonDamageEngineParts.fumbleDamage(power);
	} else {
	    this.didPierce();
	    this.didCrit();
	    double rawDamage;
	    if (this.pierce) {
		rawDamage = attack;
	    } else {
		rawDamage = attack - defense;
	    }
	    final int rHit = CommonDamageEngineParts.chance();
	    int aHit = acting.getHit();
	    if (this.crit || this.pierce) {
		// Critical hits and piercing hits
		// always connect
		aHit = CommonDamageEngineParts.ALWAYS;
	    }
	    if (rHit > aHit) {
		// Weapon missed
		this.missed = true;
		this.dodged = false;
		this.crit = false;
		return 0;
	    } else {
		final int rEvade = CommonDamageEngineParts.chance();
		final int aEvade = enemy.getEvade();
		if (rEvade < aEvade) {
		    // Enemy dodged
		    this.missed = false;
		    this.dodged = true;
		    this.crit = false;
		    return 0;
		} else {
		    // Hit
		    this.missed = false;
		    this.dodged = false;
		    RandomRange rDamage;
		    if (this.crit) {
			rDamage = new RandomRange(MULTIPLIER_MIN_CRIT,
				MULTIPLIER_MAX_CRIT);
		    } else {
			rDamage = new RandomRange(MULTIPLIER_MIN,
				MULTIPLIER_MAX);
		    }
		    final int multiplier = rDamage.generate();
		    return (int) ((rawDamage * multiplier) / CommonDamageEngineParts.MULTIPLIER_DIVIDE);
		}
	    }
	}
    }

    @Override
    public boolean enemyDodged() {
	return this.dodged;
    }

    @Override
    public boolean weaponMissed() {
	return this.missed;
    }

    @Override
    public boolean weaponCrit() {
	return this.crit;
    }

    @Override
    public boolean weaponPierce() {
	return this.pierce;
    }

    @Override
    public boolean weaponFumble() {
	return this.fumble;
    }

    private void didPierce() {
	this.pierce = CommonDamageEngineParts.didSpecial(PIERCE_CHANCE);
    }

    private void didCrit() {
	this.crit = CommonDamageEngineParts.didSpecial(CRIT_CHANCE);
    }

    private void didFumble() {
	this.fumble = CommonDamageEngineParts.didSpecial(FUMBLE_CHANCE);
    }
}