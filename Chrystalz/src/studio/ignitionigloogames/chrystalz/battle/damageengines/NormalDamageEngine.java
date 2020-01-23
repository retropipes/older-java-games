/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.battle.damageengines;

import studio.ignitionigloogames.chrystalz.creatures.AbstractCreature;
import studio.ignitionigloogames.chrystalz.creatures.StatConstants;
import studio.ignitionigloogames.common.random.RandomRange;

class NormalDamageEngine extends AbstractDamageEngine {
    private static final int MULTIPLIER_MIN = 7500;
    private static final int MULTIPLIER_MAX = 15000;
    private static final int MULTIPLIER_MIN_CRIT = 20000;
    private static final int MULTIPLIER_MAX_CRIT = 30000;
    private static final int FUMBLE_CHANCE = 500;
    private static final int PIERCE_CHANCE = 1000;
    private static final int CRIT_CHANCE = 1000;
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
                        rDamage = new RandomRange(
                                NormalDamageEngine.MULTIPLIER_MIN_CRIT,
                                NormalDamageEngine.MULTIPLIER_MAX_CRIT);
                    } else {
                        rDamage = new RandomRange(
                                NormalDamageEngine.MULTIPLIER_MIN,
                                NormalDamageEngine.MULTIPLIER_MAX);
                    }
                    final int multiplier = rDamage.generate();
                    return (int) (rawDamage * multiplier
                            / CommonDamageEngineParts.MULTIPLIER_DIVIDE);
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
        this.pierce = CommonDamageEngineParts
                .didSpecial(NormalDamageEngine.PIERCE_CHANCE);
    }

    private void didCrit() {
        this.crit = CommonDamageEngineParts
                .didSpecial(NormalDamageEngine.CRIT_CHANCE);
    }

    private void didFumble() {
        this.fumble = CommonDamageEngineParts
                .didSpecial(NormalDamageEngine.FUMBLE_CHANCE);
    }
}