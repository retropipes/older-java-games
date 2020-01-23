/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.battle.damageengines;

import com.puttysoftware.gemma.support.creatures.Creature;
import com.puttysoftware.gemma.support.creatures.StatConstants;
import com.puttysoftware.gemma.support.creatures.faiths.FaithConstants;
import com.puttysoftware.gemma.support.items.Equipment;
import com.puttysoftware.gemma.support.items.EquipmentCategoryConstants;
import com.puttysoftware.gemma.support.items.EquipmentSlotConstants;
import com.puttysoftware.randomrange.RandomRange;

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
    public int computeDamage(Creature enemy, Creature acting) {
        // Compute Damage
        double attack = acting.getEffectedAttack();
        double defense = enemy.getEffectedStat(StatConstants.STAT_DEFENSE);
        double absorb = (PercentDamageEngine.ABSORB - enemy.getArmorBlock())
                / PercentDamageEngine.ABSORB;
        this.didPierce();
        double rawDamage;
        if (this.pierce) {
            rawDamage = Math.max(1.0, attack);
        } else {
            rawDamage = Math.max(1.0, (attack - defense) * absorb);
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
                    if (offHand != null && offHand
                            .getEquipCategory() != EquipmentCategoryConstants.EQUIPMENT_CATEGORY_ARMOR) {
                        for (int z = 0; z < fc; z++) {
                            int fpl = offHand.getFaithPowerLevel(z);
                            faithMultiplier += faithIncrement * fpl;
                        }
                    }
                }
                // Armor Faith Power Boost
                double faithDR = 0;
                double faithDRInc = 1.0;
                if (offHand != null && offHand
                        .getEquipCategory() == EquipmentCategoryConstants.EQUIPMENT_CATEGORY_ARMOR) {
                    for (int z = 0; z < fc; z++) {
                        int fpl = offHand.getFaithPowerLevel(z);
                        faithDR += faithDRInc * fpl;
                    }
                }
                int unadjustedDamage = (int) ((rawDamage * multiplier
                        * faithMultiplier)
                        / PercentDamageEngine.MULTIPLIER_DIVIDE);
                return Math.max(1,
                        unadjustedDamage - ((int) Math.ceil(faithDR)));
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
}