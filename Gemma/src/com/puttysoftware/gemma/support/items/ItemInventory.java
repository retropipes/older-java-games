/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell


 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.items;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.puttysoftware.gemma.support.creatures.Creature;
import com.puttysoftware.gemma.support.creatures.StatConstants;
import com.puttysoftware.gemma.support.items.combat.CombatItemList;
import com.puttysoftware.gemma.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.gemma.support.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class ItemInventory {
    // Properties
    private final ItemUseQuantity[] entries;
    private final Equipment[] equipment;

    // Constructors
    public ItemInventory(boolean hasCombatItems) {
        if (hasCombatItems) {
            CombatItemList cil = new CombatItemList();
            Item[] items = cil.getAllItems();
            this.entries = new ItemUseQuantity[items.length];
            for (int x = 0; x < items.length; x++) {
                this.entries[x] = new ItemUseQuantity(items[x], 0, 0);
            }
        } else {
            this.entries = null;
        }
        this.equipment = new Equipment[EquipmentSlotConstants.MAX_SLOTS];
    }

    // Methods
    void addItem(Item i) {
        for (ItemUseQuantity iqu : this.entries) {
            Item item = iqu.getItem();
            if (i.getName().equals(item.getName())) {
                iqu.incrementQuantity();
                iqu.setUses(item.getInitialUses());
                return;
            }
        }
    }

    public int getUses(Item i) {
        for (ItemUseQuantity iqu : this.entries) {
            Item item = iqu.getItem();
            if (i.getName().equals(item.getName())) {
                return iqu.getUses();
            }
        }
        return 0;
    }

    void equipOneHandedWeapon(Creature pc, Equipment ei, boolean useFirst,
            boolean playSound) {
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
            SoundManager.playSound(GameSoundConstants.SOUND_EQUIP);
        }
    }

    void equipTwoHandedWeapon(Creature pc, Equipment ei, boolean playSound) {
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
            SoundManager.playSound(GameSoundConstants.SOUND_EQUIP);
        }
    }

    void equipArmor(Creature pc, Equipment ei, boolean playSound) {
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
            SoundManager.playSound(GameSoundConstants.SOUND_EQUIP);
        }
    }

    public Equipment getEquipmentInSlot(int slot) {
        return this.equipment[slot];
    }

    void setEquipmentInSlot(int slot, Equipment e) {
        this.equipment[slot] = e;
    }

    public String[] generateInventoryStringArray() {
        ArrayList<String> result = new ArrayList<>();
        StringBuilder sb;
        int counter = 0;
        for (ItemUseQuantity iqu : this.entries) {
            sb = new StringBuilder();
            sb.append("Slot ");
            sb.append(counter + 1);
            sb.append(": ");
            sb.append(iqu.getItem().getName());
            sb.append(" (Qty: ");
            sb.append(iqu.getQuantity());
            sb.append(", Uses: ");
            sb.append(iqu.getUses());
            sb.append(")");
            result.add(sb.toString());
            counter++;
        }
        return result.toArray(new String[result.size()]);
    }

    public String[] generateCombatUsableStringArray() {
        ArrayList<String> result = new ArrayList<>();
        StringBuilder sb;
        for (ItemUseQuantity iqu : this.entries) {
            if (iqu.getItem().isCombatUsable()) {
                sb = new StringBuilder();
                sb.append(iqu.getItem().getName());
                result.add(sb.toString());
            }
        }
        return result.toArray(new String[result.size()]);
    }

    public String[] generateCombatUsableDisplayStringArray() {
        ArrayList<String> result = new ArrayList<>();
        StringBuilder sb;
        int counter = 0;
        for (ItemUseQuantity iqu : this.entries) {
            if (iqu.getItem().isCombatUsable()) {
                sb = new StringBuilder();
                sb.append("Slot ");
                sb.append(counter + 1);
                sb.append(": ");
                sb.append(iqu.getItem().getName());
                sb.append(" (Qty: ");
                sb.append(iqu.getQuantity());
                sb.append(", Uses: ");
                sb.append(iqu.getUses());
                sb.append(")");
                result.add(sb.toString());
                counter++;
            }
        }
        return result.toArray(new String[result.size()]);
    }

    String[] generateEquipmentEnhancementStringArray() {
        String[] result = new String[this.equipment.length];
        StringBuilder sb;
        for (int x = 0; x < result.length - 1; x++) {
            sb = new StringBuilder();
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

    public String[] generateEquipmentStringArray() {
        String[] result = new String[this.equipment.length + 1];
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

    public int getTotalInventoryWeight() {
        int total = 0;
        for (ItemUseQuantity iqu : this.entries) {
            total += iqu.getItem().getEffectiveWeight();
        }
        return total;
    }

    public static ItemInventory readItemInventory(XDataReader dr)
            throws IOException {
        ItemInventory ii = new ItemInventory(true);
        for (ItemUseQuantity iqu : ii.entries) {
            iqu.setQuantity(dr.readInt());
            iqu.setUses(dr.readInt());
        }
        for (int x = 0; x < ii.equipment.length; x++) {
            Equipment ei = Equipment.readEquipment(dr);
            ii.equipment[x] = ei;
        }
        return ii;
    }

    public void writeItemInventory(XDataWriter dw) throws IOException {
        for (ItemUseQuantity iqu : this.entries) {
            dw.writeInt(iqu.getQuantity());
            dw.writeInt(iqu.getUses());
        }
        for (int x = 0; x < this.equipment.length; x++) {
            Equipment ei = this.equipment[x];
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
        int result = 1;
        result = prime * result + Arrays.hashCode(this.equipment);
        return prime * result + Arrays.hashCode(this.entries);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ItemInventory)) {
            return false;
        }
        ItemInventory other = (ItemInventory) obj;
        if (!Arrays.equals(this.equipment, other.equipment)) {
            return false;
        }
        if (this.entries == null) {
            if (other.entries != null) {
                return false;
            }
        } else if (!Arrays.deepEquals(this.entries, other.entries)) {
            return false;
        }
        return true;
    }
}
