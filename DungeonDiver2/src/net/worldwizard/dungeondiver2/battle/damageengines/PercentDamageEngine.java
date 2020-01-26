package net.worldwizard.dungeondiver2.battle.damageengines;

import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.support.creatures.Creature;
import net.worldwizard.support.creatures.StatConstants;

public class PercentDamageEngine extends DamageEngine {
    private static final double ABSORB = 1000.0;
    private static final int MULTIPLIER_MIN = 7500;
    private static final int MULTIPLIER_MAX = 15000;
    private static final int MULTIPLIER_DIVIDE = 10000;
    private boolean dodged = false;
    private boolean missed = false;

    @Override
    public int computeDamage(final Creature enemy, final Creature acting) {
        // Compute Damage
        if (this.fumble(acting)) {
            // Fumble!
            this.missed = false;
            this.dodged = false;
            final RandomRange fumDamage = new RandomRange(1,
                    Math.max(acting.getUnfactoredWeaponPower(), 1));
            return -fumDamage.generate();
        } else {
            final double attack = acting.getEffectedUnfactoredAttack();
            final double block = enemy
                    .getEffectedStat(StatConstants.STAT_BLOCK);
            final double absorb = (PercentDamageEngine.ABSORB
                    - enemy.getArmorBlock()) / PercentDamageEngine.ABSORB;
            final double rawDamage = Math.max(1.0, (attack - block) * absorb);
            final int rHit = new RandomRange(0, 10000).generate();
            final int aHit = acting.getHit();
            if (rHit > aHit) {
                // Weapon missed
                this.missed = true;
                this.dodged = false;
                return 0;
            } else {
                final int rEvade = new RandomRange(0, 10000).generate();
                final int aEvade = enemy.getEvade();
                if (rEvade < aEvade) {
                    // Enemy dodged
                    this.missed = false;
                    this.dodged = true;
                    return 0;
                } else {
                    // Hit
                    this.missed = false;
                    this.dodged = false;
                    final RandomRange rDamage = new RandomRange(
                            PercentDamageEngine.MULTIPLIER_MIN,
                            PercentDamageEngine.MULTIPLIER_MAX);
                    final double multiplier = rDamage.generate();
                    return (int) (rawDamage * multiplier
                            / PercentDamageEngine.MULTIPLIER_DIVIDE);
                }
            }
        }
    }

    @Override
    protected boolean fumble(final Creature fumbler) {
        final RandomRange fum = new RandomRange(1, 100);
        final int fumChance = fum.generate();
        return fumChance <= fumbler
                .getEffectedStat(StatConstants.STAT_FUMBLE_CHANCE);
    }

    @Override
    public boolean enemyDodged() {
        return this.dodged;
    }

    @Override
    public boolean weaponMissed() {
        return this.missed;
    }
}