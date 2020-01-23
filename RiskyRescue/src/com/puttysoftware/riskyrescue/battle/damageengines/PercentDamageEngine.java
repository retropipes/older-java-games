/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.battle.damageengines;

import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.riskyrescue.creatures.Creature;
import com.puttysoftware.riskyrescue.creatures.StatConstants;

class PercentDamageEngine extends DamageEngine {
    private static final double ABSORB = 1000.0;
    private static final int MULTIPLIER_MIN = 7500;
    private static final int MULTIPLIER_MAX = 15000;
    private static final int MULTIPLIER_MAX_CRIT = 30000;
    private static final int MULTIPLIER_DIVIDE = 10000;
    private static final int PIERCE_CHANCE = 1000;
    private static final int CRIT_CHANCE = 2000;
    private boolean dodged = false;
    private boolean missed = false;
    private boolean crit = false;
    private boolean pierce = false;

    @Override
    public int computeDamage(final Creature enemy, final Creature acting) {
        this.dodged = false;
        this.missed = false;
        this.crit = false;
        this.pierce = false;
        // Compute Damage
        final double attack = acting.getEffectedAttack();
        final double defense = enemy
                .getEffectedStat(StatConstants.STAT_DEFENSE);
        final double absorb = (PercentDamageEngine.ABSORB
                - enemy.getArmorBlock()) / PercentDamageEngine.ABSORB;
        this.didPierce();
        double rawDamage;
        if (this.pierce) {
            rawDamage = Math.max(1.0, attack);
        } else {
            rawDamage = Math.max(1.0, (attack - defense) * absorb);
        }
        final int rHit = new RandomRange(0, 10000).generate();
        final int aHit = acting.getHit();
        if (rHit > aHit) {
            // Weapon missed
            this.missed = true;
            return 0;
        } else {
            final int rEvade = new RandomRange(0, 10000).generate();
            final int aEvade = enemy.getEvade();
            if (rEvade < aEvade) {
                // Enemy dodged
                this.dodged = true;
                return 0;
            } else {
                // Hit
                RandomRange rDamage;
                this.didCrit();
                if (this.crit) {
                    rDamage = new RandomRange(
                            PercentDamageEngine.MULTIPLIER_MAX,
                            PercentDamageEngine.MULTIPLIER_MAX_CRIT);
                } else {
                    rDamage = new RandomRange(
                            PercentDamageEngine.MULTIPLIER_MIN,
                            PercentDamageEngine.MULTIPLIER_MAX);
                }
                final int multiplier = rDamage.generate();
                final int unadjustedDamage = (int) (rawDamage * multiplier
                        / PercentDamageEngine.MULTIPLIER_DIVIDE);
                return Math.max(1, unadjustedDamage);
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

    private void didPierce() {
        final int rPierce = new RandomRange(0, 10000).generate();
        final int aPierce = PercentDamageEngine.PIERCE_CHANCE;
        if (rPierce < aPierce) {
            this.pierce = true;
        } else {
            this.pierce = false;
        }
    }

    private void didCrit() {
        final int rCrit = new RandomRange(0, 10000).generate();
        final int aCrit = PercentDamageEngine.CRIT_CHANCE;
        if (rCrit < aCrit) {
            this.crit = true;
        } else {
            this.crit = false;
        }
    }
}