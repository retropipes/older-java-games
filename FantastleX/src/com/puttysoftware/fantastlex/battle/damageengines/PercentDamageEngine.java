/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.battle.damageengines;

import com.puttysoftware.fantastlex.creatures.AbstractCreature;
import com.puttysoftware.fantastlex.creatures.StatConstants;
import com.puttysoftware.fantastlex.creatures.faiths.FaithConstants;
import com.puttysoftware.fantastlex.items.Equipment;
import com.puttysoftware.fantastlex.items.EquipmentSlotConstants;
import com.puttysoftware.randomrange.RandomRange;

class PercentDamageEngine extends AbstractDamageEngine {
    private static final double ABSORB = 1000.0;
    private static final int MULTIPLIER_MIN = 7500;
    private static final int MULTIPLIER_MAX = 15000;
    private static final int MULTIPLIER_MAX_CRIT = 30000;
    private static final int MULTIPLIER_DIVIDE = 100000;
    private static final int FUMBLE_CHANCE = 500;
    private static final int PIERCE_CHANCE = 1000;
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
        final double absorb = (PercentDamageEngine.ABSORB
                - enemy.getArmorBlock()) / PercentDamageEngine.ABSORB;
        final int power = acting.getItems().getTotalPower();
        this.didFumble();
        if (this.fumble) {
            // Fumble!
            return new RandomRange(1, Math.max(1, power / 10)).generate();
        } else {
            this.didPierce();
            double rawDamage;
            if (this.pierce) {
                rawDamage = attack;
            } else {
                rawDamage = (attack - defense) * absorb;
            }
            final int rHit = new RandomRange(0, 10000).generate();
            final int aHit = acting.getHit();
            if (rHit > aHit) {
                // Weapon missed
                this.missed = true;
                this.dodged = false;
                this.crit = false;
                return 0;
            } else {
                final int rEvade = new RandomRange(0, 10000).generate();
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
                    // Weapon Faith Power Boost
                    double faithMultiplier = 1.0;
                    final double faithIncrement = 0.01;
                    final double faithIncrement2H = 0.03;
                    final int fc = FaithConstants.getFaithsCount();
                    final Equipment mainHand = acting.getItems()
                            .getEquipmentInSlot(
                                    EquipmentSlotConstants.SLOT_MAINHAND);
                    final Equipment offHand = acting.getItems()
                            .getEquipmentInSlot(
                                    EquipmentSlotConstants.SLOT_OFFHAND);
                    if (mainHand != null && mainHand.equals(offHand)) {
                        for (int z = 0; z < fc; z++) {
                            final int fpl = mainHand.getFaithPowerLevel(z);
                            faithMultiplier += faithIncrement2H * fpl;
                        }
                    } else {
                        if (mainHand != null) {
                            for (int z = 0; z < fc; z++) {
                                final int fpl = mainHand.getFaithPowerLevel(z);
                                faithMultiplier += faithIncrement * fpl;
                            }
                        }
                        if (offHand != null) {
                            for (int z = 0; z < fc; z++) {
                                final int fpl = offHand.getFaithPowerLevel(z);
                                faithMultiplier += faithIncrement * fpl;
                            }
                        }
                    }
                    // Armor Faith Power Boost
                    double faithDR = 0;
                    final double faithDRInc = 0.1;
                    final Equipment hat = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_HEAD);
                    if (hat != null) {
                        for (int z = 0; z < fc; z++) {
                            final int fpl = hat.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    final Equipment necklace = acting.getItems()
                            .getEquipmentInSlot(
                                    EquipmentSlotConstants.SLOT_NECK);
                    if (necklace != null) {
                        for (int z = 0; z < fc; z++) {
                            final int fpl = necklace.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    final Equipment armor = acting.getItems()
                            .getEquipmentInSlot(
                                    EquipmentSlotConstants.SLOT_BODY);
                    if (armor != null) {
                        for (int z = 0; z < fc; z++) {
                            final int fpl = armor.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    final Equipment cape = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_BACK);
                    if (cape != null) {
                        for (int z = 0; z < fc; z++) {
                            final int fpl = cape.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    final Equipment shirt = acting.getItems()
                            .getEquipmentInSlot(
                                    EquipmentSlotConstants.SLOT_UPPER_TORSO);
                    if (shirt != null) {
                        for (int z = 0; z < fc; z++) {
                            final int fpl = shirt.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    final Equipment bracers = acting.getItems()
                            .getEquipmentInSlot(
                                    EquipmentSlotConstants.SLOT_ARMS);
                    if (bracers != null) {
                        for (int z = 0; z < fc; z++) {
                            final int fpl = bracers.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    final Equipment gloves = acting.getItems()
                            .getEquipmentInSlot(
                                    EquipmentSlotConstants.SLOT_HANDS);
                    if (gloves != null) {
                        for (int z = 0; z < fc; z++) {
                            final int fpl = gloves.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    final Equipment ring = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_FINGERS);
                    if (ring != null) {
                        for (int z = 0; z < fc; z++) {
                            final int fpl = ring.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    final Equipment belt = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_LOWER_TORSO);
                    if (belt != null) {
                        for (int z = 0; z < fc; z++) {
                            final int fpl = belt.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    final Equipment pants = acting.getItems()
                            .getEquipmentInSlot(
                                    EquipmentSlotConstants.SLOT_LEGS);
                    if (pants != null) {
                        for (int z = 0; z < fc; z++) {
                            final int fpl = pants.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    final Equipment boots = acting.getItems()
                            .getEquipmentInSlot(
                                    EquipmentSlotConstants.SLOT_FEET);
                    if (boots != null) {
                        for (int z = 0; z < fc; z++) {
                            final int fpl = boots.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    final int unadjustedDamage = (int) (rawDamage * multiplier
                            * faithMultiplier
                            / PercentDamageEngine.MULTIPLIER_DIVIDE);
                    return Math.max(0,
                            unadjustedDamage - (int) Math.ceil(faithDR));
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

    private void didFumble() {
        final int rFumble = new RandomRange(0, 10000).generate();
        final int aFumble = PercentDamageEngine.FUMBLE_CHANCE;
        if (rFumble < aFumble) {
            this.fumble = true;
        } else {
            this.fumble = false;
        }
    }
}