/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.battle.damageengines;

import com.puttysoftware.mazerunner2.creatures.AbstractCreature;
import com.puttysoftware.mazerunner2.creatures.StatConstants;
import com.puttysoftware.mazerunner2.creatures.faiths.FaithConstants;
import com.puttysoftware.mazerunner2.items.Equipment;
import com.puttysoftware.mazerunner2.items.EquipmentSlotConstants;
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
    public int computeDamage(AbstractCreature enemy, AbstractCreature acting) {
        // Compute Damage
        double attack = acting.getEffectedAttack();
        double defense = enemy.getEffectedStat(StatConstants.STAT_DEFENSE);
        double absorb = (PercentDamageEngine.ABSORB - enemy.getArmorBlock())
                / PercentDamageEngine.ABSORB;
        int power = acting.getItems().getTotalPower();
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
            int rHit = new RandomRange(0, 10000).generate();
            int aHit = acting.getHit();
            if (rHit > aHit) {
                // Weapon missed
                this.missed = true;
                this.dodged = false;
                this.crit = false;
                return 0;
            } else {
                int rEvade = new RandomRange(0, 10000).generate();
                int aEvade = enemy.getEvade();
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
                    int multiplier = rDamage.generate();
                    // Weapon Faith Power Boost
                    double faithMultiplier = 1.0;
                    double faithIncrement = 0.01;
                    double faithIncrement2H = 0.03;
                    int fc = FaithConstants.getFaithsCount();
                    Equipment mainHand = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_MAINHAND);
                    Equipment offHand = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_OFFHAND);
                    if (mainHand != null && mainHand.equals(offHand)) {
                        for (int z = 0; z < fc; z++) {
                            int fpl = mainHand.getFaithPowerLevel(z);
                            faithMultiplier += faithIncrement2H * fpl;
                        }
                    } else {
                        if (mainHand != null) {
                            for (int z = 0; z < fc; z++) {
                                int fpl = mainHand.getFaithPowerLevel(z);
                                faithMultiplier += faithIncrement * fpl;
                            }
                        }
                        if (offHand != null) {
                            for (int z = 0; z < fc; z++) {
                                int fpl = offHand.getFaithPowerLevel(z);
                                faithMultiplier += faithIncrement * fpl;
                            }
                        }
                    }
                    // Armor Faith Power Boost
                    double faithDR = 0;
                    double faithDRInc = 0.1;
                    Equipment hat = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_HEAD);
                    if (hat != null) {
                        for (int z = 0; z < fc; z++) {
                            int fpl = hat.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    Equipment necklace = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_NECK);
                    if (necklace != null) {
                        for (int z = 0; z < fc; z++) {
                            int fpl = necklace.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    Equipment armor = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_BODY);
                    if (armor != null) {
                        for (int z = 0; z < fc; z++) {
                            int fpl = armor.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    Equipment cape = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_BACK);
                    if (cape != null) {
                        for (int z = 0; z < fc; z++) {
                            int fpl = cape.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    Equipment shirt = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_UPPER_TORSO);
                    if (shirt != null) {
                        for (int z = 0; z < fc; z++) {
                            int fpl = shirt.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    Equipment bracers = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_ARMS);
                    if (bracers != null) {
                        for (int z = 0; z < fc; z++) {
                            int fpl = bracers.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    Equipment gloves = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_HANDS);
                    if (gloves != null) {
                        for (int z = 0; z < fc; z++) {
                            int fpl = gloves.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    Equipment ring = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_FINGERS);
                    if (ring != null) {
                        for (int z = 0; z < fc; z++) {
                            int fpl = ring.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    Equipment belt = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_LOWER_TORSO);
                    if (belt != null) {
                        for (int z = 0; z < fc; z++) {
                            int fpl = belt.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    Equipment pants = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_LEGS);
                    if (pants != null) {
                        for (int z = 0; z < fc; z++) {
                            int fpl = pants.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    Equipment boots = acting.getItems().getEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_FEET);
                    if (boots != null) {
                        for (int z = 0; z < fc; z++) {
                            int fpl = boots.getFaithPowerLevel(z);
                            faithDR += fpl * faithDRInc;
                        }
                    }
                    int unadjustedDamage = (int) ((rawDamage * multiplier * faithMultiplier) / PercentDamageEngine.MULTIPLIER_DIVIDE);
                    return unadjustedDamage - ((int) Math.ceil(faithDR));
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
        int rPierce = new RandomRange(0, 10000).generate();
        int aPierce = PIERCE_CHANCE;
        if (rPierce < aPierce) {
            this.pierce = true;
        } else {
            this.pierce = false;
        }
    }

    private void didCrit() {
        int rCrit = new RandomRange(0, 10000).generate();
        int aCrit = CRIT_CHANCE;
        if (rCrit < aCrit) {
            this.crit = true;
        } else {
            this.crit = false;
        }
    }

    private void didFumble() {
        int rFumble = new RandomRange(0, 10000).generate();
        int aFumble = FUMBLE_CHANCE;
        if (rFumble < aFumble) {
            this.fumble = true;
        } else {
            this.fumble = false;
        }
    }
}