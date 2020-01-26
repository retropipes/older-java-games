/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell


 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.items;

import java.io.IOException;
import java.util.Arrays;

import com.puttysoftware.riskyrescue.assets.SoundConstants;
import com.puttysoftware.riskyrescue.assets.SoundManager;
import com.puttysoftware.riskyrescue.creatures.Creature;
import com.puttysoftware.riskyrescue.creatures.StatConstants;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class ItemInventory {
    // Properties
    private final Equipment[] equipment;

    // Constructors
    public ItemInventory() {
        this.equipment = new Equipment[EquipmentSlotConstants.MAX_SLOTS];
    }

    // Methods
    void equipOneHandedWeapon(final Creature pc, final Equipment ei,
            final boolean useFirst, final boolean playSound) {
        // Fix character load, changing weapons
        if (this.equipment[EquipmentSlotConstants.SLOT_MAINHAND] != null
                && useFirst) {
            pc.offsetLoad(-this.equipment[EquipmentSlotConstants.SLOT_MAINHAND]
                    .getEffectiveWeight());
        } else if (this.equipment[EquipmentSlotConstants.SLOT_OFFHAND] != null
                && !useFirst) {
            pc.offsetLoad(-this.equipment[EquipmentSlotConstants.SLOT_OFFHAND]
                    .getEffectiveWeight());
        }
        pc.offsetLoad(ei.getEffectiveWeight());
        // Check for two-handed weapon
        if (this.equipment[EquipmentSlotConstants.SLOT_MAINHAND] != null) {
            if (this.equipment[EquipmentSlotConstants.SLOT_MAINHAND]
                    .getEquipCategory() == EquipmentCategoryConstants.EQUIPMENT_CATEGORY_TWO_HANDED_WEAPON) {
                // Two-handed weapon currently equipped, unequip it
                this.equipment[EquipmentSlotConstants.SLOT_MAINHAND] = null;
                this.equipment[EquipmentSlotConstants.SLOT_OFFHAND] = null;
            }
        }
        if (useFirst) {
            // Equip it in first slot
            this.equipment[ei.getFirstSlotUsed()] = ei;
        } else {
            // Equip it in second slot
            this.equipment[ei.getSecondSlotUsed()] = ei;
        }
        if (playSound) {
            SoundManager.playSound(SoundConstants.EQUIP);
        }
    }

    void equipTwoHandedWeapon(final Creature pc, final Equipment ei,
            final boolean playSound) {
        // Fix character load, changing weapons
        if (this.equipment[EquipmentSlotConstants.SLOT_MAINHAND] != null) {
            pc.offsetLoad(-this.equipment[EquipmentSlotConstants.SLOT_MAINHAND]
                    .getEffectiveWeight());
        }
        pc.offsetLoad(ei.getEffectiveWeight());
        // Equip it in first slot
        this.equipment[ei.getFirstSlotUsed()] = ei;
        // Equip it in second slot
        this.equipment[ei.getSecondSlotUsed()] = ei;
        if (playSound) {
            SoundManager.playSound(SoundConstants.EQUIP);
        }
    }

    void equipArmor(final Creature pc, final Equipment ei,
            final boolean playSound) {
        // Fix character load, changing armor
        if (ei.getFirstSlotUsed() == EquipmentSlotConstants.SLOT_OFFHAND) {
            // Check for two-handed weapon
            if (this.equipment[EquipmentSlotConstants.SLOT_MAINHAND] != null) {
                if (this.equipment[EquipmentSlotConstants.SLOT_MAINHAND]
                        .getEquipCategory() == EquipmentCategoryConstants.EQUIPMENT_CATEGORY_TWO_HANDED_WEAPON) {
                    pc.offsetLoad(
                            -this.equipment[EquipmentSlotConstants.SLOT_MAINHAND]
                                    .getEffectiveWeight());
                }
            }
        }
        if (this.equipment[ei.getFirstSlotUsed()] != null) {
            pc.offsetLoad(-this.equipment[ei.getFirstSlotUsed()]
                    .getEffectiveWeight());
        }
        pc.offsetLoad(ei.getEffectiveWeight());
        // Check for shield
        if (ei.getFirstSlotUsed() == EquipmentSlotConstants.SLOT_OFFHAND) {
            // Check for two-handed weapon
            if (this.equipment[EquipmentSlotConstants.SLOT_MAINHAND] != null) {
                if (this.equipment[EquipmentSlotConstants.SLOT_MAINHAND]
                        .getEquipCategory() == EquipmentCategoryConstants.EQUIPMENT_CATEGORY_TWO_HANDED_WEAPON) {
                    // Two-handed weapon currently equipped, unequip it
                    this.equipment[EquipmentSlotConstants.SLOT_MAINHAND] = null;
                    this.equipment[EquipmentSlotConstants.SLOT_OFFHAND] = null;
                }
            }
        }
        // Equip it in first slot
        this.equipment[ei.getFirstSlotUsed()] = ei;
        if (playSound) {
            SoundManager.playSound(SoundConstants.EQUIP);
        }
    }

    public String[] generateEquipmentStringArray() {
        final String[] result = new String[this.equipment.length + 1];
        StringBuilder sb;
        for (int x = 0; x < result.length - 1; x++) {
            sb = new StringBuilder();
            sb.append(EquipmentSlotConstants.getSlotNames()[x]);
            sb.append(": ");
            if (this.equipment[x] == null) {
                sb.append("Nothing (0)");
            } else {
                sb.append(this.equipment[x].getName());
                sb.append(" (");
                sb.append(this.equipment[x].getPotency());
                sb.append(")");
            }
            result[x] = sb.toString();
        }
        return result;
    }

    public int getTotalPower() {
        int total = 0;
        if (this.equipment[EquipmentSlotConstants.SLOT_MAINHAND] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_MAINHAND]
                    .getPotency();
        }
        if (this.equipment[EquipmentSlotConstants.SLOT_OFFHAND] != null) {
            if (this.equipment[EquipmentSlotConstants.SLOT_OFFHAND]
                    .getEquipCategory() == EquipmentCategoryConstants.EQUIPMENT_CATEGORY_ONE_HANDED_WEAPON) {
                total += this.equipment[EquipmentSlotConstants.SLOT_OFFHAND]
                        .getPotency();
            } else if (this.equipment[EquipmentSlotConstants.SLOT_OFFHAND]
                    .getEquipCategory() == EquipmentCategoryConstants.EQUIPMENT_CATEGORY_TWO_HANDED_WEAPON) {
                total += this.equipment[EquipmentSlotConstants.SLOT_OFFHAND]
                        .getPotency();
                total *= StatConstants.FACTOR_TWO_HANDED_BONUS;
            }
        }
        return total;
    }

    public int getTotalAbsorb() {
        int total = 0;
        if (this.equipment[EquipmentSlotConstants.SLOT_OFFHAND] != null) {
            if (this.equipment[EquipmentSlotConstants.SLOT_OFFHAND]
                    .getEquipCategory() == EquipmentCategoryConstants.EQUIPMENT_CATEGORY_ARMOR) {
                total += this.equipment[EquipmentSlotConstants.SLOT_OFFHAND]
                        .getPotency();
            }
        }
        return total;
    }

    public int getTotalEquipmentWeight() {
        int total = 0;
        for (int x = 0; x < EquipmentSlotConstants.MAX_SLOTS; x++) {
            if (this.equipment[x] != null) {
                total += this.equipment[x].getEffectiveWeight();
            }
        }
        return total;
    }

    public static ItemInventory readItemInventory(final XDataReader dr)
            throws IOException {
        final ItemInventory ii = new ItemInventory();
        for (int x = 0; x < ii.equipment.length; x++) {
            final Equipment ei = Equipment.readEquipment(dr);
            ii.equipment[x] = ei;
        }
        return ii;
    }

    public void writeItemInventory(final XDataWriter dw) throws IOException {
        for (final Equipment ei : this.equipment) {
            if (ei != null) {
                ei.writeEquipment(dw);
            } else {
                dw.writeString("null");
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        final int result = 1;
        return prime * result + Arrays.hashCode(this.equipment);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ItemInventory)) {
            return false;
        }
        final ItemInventory other = (ItemInventory) obj;
        if (!Arrays.equals(this.equipment, other.equipment)) {
            return false;
        }
        return true;
    }
}
