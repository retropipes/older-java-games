/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.items;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import net.dynamicdungeon.dbio.DatabaseReader;
import net.dynamicdungeon.dbio.DatabaseWriter;
import net.dynamicdungeon.dynamicdungeon.creatures.AbstractCreature;
import net.dynamicdungeon.dynamicdungeon.items.combat.CombatItemList;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;

public class ItemInventory {
    // Properties
    private ItemUseQuantity[] entries;
    private Equipment[] equipment;

    // Constructors
    public ItemInventory(final boolean hasCombatItems) {
        this.resetInventory(hasCombatItems);
    }

    // Methods
    public void resetInventory() {
        this.resetInventory(true);
    }

    private void resetInventory(final boolean hasCombatItems) {
        if (hasCombatItems) {
            final CombatItemList cil = new CombatItemList();
            final Item[] items = cil.getAllItems();
            this.entries = new ItemUseQuantity[items.length];
            for (int x = 0; x < items.length; x++) {
                this.entries[x] = new ItemUseQuantity(items[x], 0, 0);
            }
        } else {
            this.entries = null;
        }
        this.equipment = new Equipment[EquipmentSlotConstants.MAX_SLOTS];
    }

    public void addItem(final Item i) {
        for (final ItemUseQuantity iqu : this.entries) {
            final Item item = iqu.getItem();
            if (i.getName().equals(item.getName())) {
                iqu.incrementQuantity();
                iqu.setUses(item.getInitialUses());
                return;
            }
        }
    }

    public int getUses(final Item i) {
        for (final ItemUseQuantity iqu : this.entries) {
            final Item item = iqu.getItem();
            if (i.getName().equals(item.getName())) {
                return iqu.getUses();
            }
        }
        return 0;
    }

    public void equipWeapon(final AbstractCreature pc, final Equipment ei,
            final boolean playSound) {
        // Fix character load, changing weapons
        if (this.equipment[EquipmentSlotConstants.SLOT_MAINHAND] != null) {
            pc.offsetLoad(-this.equipment[EquipmentSlotConstants.SLOT_MAINHAND]
                    .getEffectiveWeight());
        }
        pc.offsetLoad(ei.getEffectiveWeight());
        // Equip it in first slot
        this.equipment[ei.getFirstSlotUsed()] = ei;
        if (playSound) {
            SoundManager.playSound(SoundConstants.SOUND_EQUIP);
        }
    }

    public void equipArmor(final AbstractCreature pc, final Equipment ei,
            final boolean playSound) {
        // Fix character load, changing armor
        if (this.equipment[ei.getFirstSlotUsed()] != null) {
            pc.offsetLoad(-this.equipment[ei.getFirstSlotUsed()]
                    .getEffectiveWeight());
        }
        pc.offsetLoad(ei.getEffectiveWeight());
        // Equip it in first slot
        this.equipment[ei.getFirstSlotUsed()] = ei;
        if (playSound) {
            SoundManager.playSound(SoundConstants.SOUND_EQUIP);
        }
    }

    public String[] generateInventoryStringArray() {
        final ArrayList<String> result = new ArrayList<>();
        StringBuilder sb;
        int counter = 0;
        for (final ItemUseQuantity iqu : this.entries) {
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
        final ArrayList<String> result = new ArrayList<>();
        StringBuilder sb;
        for (final ItemUseQuantity iqu : this.entries) {
            if (iqu.getItem().isCombatUsable()) {
                sb = new StringBuilder();
                sb.append(iqu.getItem().getName());
                result.add(sb.toString());
            }
        }
        return result.toArray(new String[result.size()]);
    }

    public String[] generateCombatUsableDisplayStringArray() {
        final ArrayList<String> result = new ArrayList<>();
        StringBuilder sb;
        int counter = 0;
        for (final ItemUseQuantity iqu : this.entries) {
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

    public String[] generateEquipmentStringArray() {
        final String[] result = new String[this.equipment.length];
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
        return total;
    }

    public int getTotalAbsorb() {
        int total = 0;
        if (this.equipment[EquipmentSlotConstants.SLOT_BODY] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_BODY]
                    .getPotency();
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
        for (final ItemUseQuantity iqu : this.entries) {
            total += iqu.getItem().getEffectiveWeight();
        }
        return total;
    }

    public static ItemInventory readItemInventory(final DatabaseReader dr)
            throws IOException {
        final ItemInventory ii = new ItemInventory(true);
        for (final ItemUseQuantity iqu : ii.entries) {
            iqu.setQuantity(dr.readInt());
            iqu.setUses(dr.readInt());
        }
        for (int x = 0; x < ii.equipment.length; x++) {
            final Equipment ei = Equipment.readEquipment(dr);
            if (ei != null) {
                ii.equipment[x] = ei;
            }
        }
        return ii;
    }

    public void writeItemInventory(final DatabaseWriter dw) throws IOException {
        for (final ItemUseQuantity iqu : this.entries) {
            dw.writeInt(iqu.getQuantity());
            dw.writeInt(iqu.getUses());
        }
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
        int result = 1;
        result = prime * result + Arrays.hashCode(this.equipment);
        return prime * result + Arrays.hashCode(this.entries);
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
