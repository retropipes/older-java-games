/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.items;

import java.io.IOException;
import java.util.Arrays;

import net.worldwizard.support.creatures.Creature;
import net.worldwizard.support.creatures.StatConstants;
import net.worldwizard.support.items.combat.CombatItemList;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class ItemInventory {
    // Properties
    private final Item[] inventory;
    private final int[] quantity;
    private final int[] uses;
    private final int[] initialUses;
    private final CombatItemList cil;
    private final Equipment[] equipment;
    private Socks socks;
    private static final int MAX_ITEMS = 200;

    // Constructors
    public ItemInventory() {
        this.cil = new CombatItemList();
        this.inventory = this.cil.getAllItems();
        this.uses = new int[ItemInventory.MAX_ITEMS];
        this.quantity = new int[ItemInventory.MAX_ITEMS];
        this.initialUses = this.cil.getAllInitialUses();
        this.equipment = new Equipment[EquipmentSlotConstants.MAX_SLOTS];
        this.socks = null;
    }

    // Methods
    private int indexOf(final Item i) {
        final Item[] items = this.cil.getAllItems();
        for (int x = 0; x < items.length; x++) {
            if (items[x].getName().equals(i.getName())) {
                return x;
            }
        }
        return -1;
    }

    public void addItem(final Item i) {
        final int existIndex = this.indexOf(i);
        if (existIndex != -1) {
            this.quantity[existIndex]++;
            if (this.uses[existIndex] <= 0) {
                this.uses[existIndex] = this.initialUses[existIndex];
            }
        }
    }

    public int getUses(final Item i) {
        final int existIndex = this.indexOf(i);
        if (existIndex != -1) {
            return this.uses[existIndex];
        } else {
            return 0;
        }
    }

    public void equipOneHandedWeapon(final Equipment ei, final boolean useFirst) {
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
    }

    public void equipTwoHandedWeapon(final Equipment ei) {
        // Equip it in first slot
        this.equipment[ei.getFirstSlotUsed()] = ei;
        // Equip it in second slot
        this.equipment[ei.getSecondSlotUsed()] = ei;
    }

    public void equipArmor(final Equipment ei) {
        // Check for socks
        if (ei instanceof Socks) {
            this.socks = (Socks) ei;
        } else {
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
        }
    }

    public String[] generateInventoryStringArray() {
        final String[] result = new String[this.inventory.length];
        StringBuilder sb;
        for (int x = 0; x < result.length; x++) {
            sb = new StringBuilder();
            sb.append("Slot ");
            sb.append(x + 1);
            sb.append(": ");
            if (this.inventory[x] == null) {
                sb.append("Nothing (Qty: 0, Uses: 0)");
            } else {
                sb.append(this.inventory[x].getName());
                sb.append(" (Qty: ");
                sb.append(this.quantity[x]);
                sb.append(", Uses: ");
                sb.append(this.uses[x]);
                sb.append(")");
            }
            result[x] = sb.toString();
        }
        return result;
    }

    public String[] generateCombatUsableStringArray() {
        int combatUsableCounter = 0;
        for (final Item element : this.inventory) {
            if (element != null) {
                if (element.isCombatUsable()) {
                    combatUsableCounter++;
                }
            }
        }
        if (combatUsableCounter == 0) {
            return null;
        }
        final String[] result = new String[combatUsableCounter];
        StringBuilder sb;
        combatUsableCounter = 0;
        for (final Item element : this.inventory) {
            if (element != null) {
                if (element.isCombatUsable()) {
                    sb = new StringBuilder();
                    sb.append(element.getName());
                    result[combatUsableCounter] = sb.toString();
                    combatUsableCounter++;
                }
            }
        }
        return result;
    }

    public String[] generateCombatUsableDisplayStringArray() {
        int combatUsableCounter = 0;
        for (final Item element : this.inventory) {
            if (element != null) {
                if (element.isCombatUsable()) {
                    combatUsableCounter++;
                }
            }
        }
        if (combatUsableCounter == 0) {
            return null;
        }
        final String[] result = new String[combatUsableCounter];
        StringBuilder sb;
        combatUsableCounter = 0;
        for (int x = 0; x < this.inventory.length; x++) {
            if (this.inventory[x] != null) {
                if (this.inventory[x].isCombatUsable()) {
                    sb = new StringBuilder();
                    sb.append(this.inventory[x].getName());
                    sb.append(" (Qty: ");
                    sb.append(this.quantity[x]);
                    sb.append(", Uses: ");
                    sb.append(this.uses[x]);
                    sb.append(")");
                    result[combatUsableCounter] = sb.toString();
                    combatUsableCounter++;
                }
            }
        }
        return result;
    }

    public String[] generateEquipmentStringArray() {
        final String[] result = new String[this.equipment.length + 1];
        StringBuilder sb;
        for (int x = 0; x < result.length - 1; x++) {
            sb = new StringBuilder();
            sb.append(EquipmentSlotConstants.SLOT_NAMES[x]);
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
        // Append Socks
        sb = new StringBuilder();
        sb.append("Socks: ");
        if (this.socks == null) {
            sb.append("None");
        } else {
            sb.append(this.socks.getName());
        }
        result[result.length - 1] = sb.toString();
        return result;
    }

    public void fireStepActions(final Creature wearer) {
        if (this.socks != null) {
            this.socks.stepAction(wearer);
        }
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
        if (this.equipment[EquipmentSlotConstants.SLOT_HEAD] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_HEAD]
                    .getPotency();
        }
        if (this.equipment[EquipmentSlotConstants.SLOT_NECK] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_NECK]
                    .getPotency();
        }
        if (this.equipment[EquipmentSlotConstants.SLOT_OFFHAND] != null) {
            if (this.equipment[EquipmentSlotConstants.SLOT_OFFHAND]
                    .getEquipCategory() == EquipmentCategoryConstants.EQUIPMENT_CATEGORY_ARMOR) {
                total += this.equipment[EquipmentSlotConstants.SLOT_OFFHAND]
                        .getPotency();
            }
        }
        if (this.equipment[EquipmentSlotConstants.SLOT_BODY] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_BODY]
                    .getPotency();
        }
        if (this.equipment[EquipmentSlotConstants.SLOT_BACK] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_BACK]
                    .getPotency();
        }
        if (this.equipment[EquipmentSlotConstants.SLOT_UPPER_TORSO] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_UPPER_TORSO]
                    .getPotency();
        }
        if (this.equipment[EquipmentSlotConstants.SLOT_ARMS] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_ARMS]
                    .getPotency();
        }
        if (this.equipment[EquipmentSlotConstants.SLOT_HANDS] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_HANDS]
                    .getPotency();
        }
        if (this.equipment[EquipmentSlotConstants.SLOT_FINGERS] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_FINGERS]
                    .getPotency();
        }
        if (this.equipment[EquipmentSlotConstants.SLOT_LOWER_TORSO] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_LOWER_TORSO]
                    .getPotency();
        }
        if (this.equipment[EquipmentSlotConstants.SLOT_LEGS] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_LEGS]
                    .getPotency();
        }
        if (this.equipment[EquipmentSlotConstants.SLOT_FEET] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_FEET]
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
        for (final Item element : this.inventory) {
            if (element != null) {
                total += element.getEffectiveWeight();
            }
        }
        return total;
    }

    public static ItemInventory readItemInventoryXML(final XDataReader dr)
            throws IOException {
        final ItemInventory ii = new ItemInventory();
        final int z = dr.readInt();
        for (int x = 0; x < z; x++) {
            ii.quantity[x] = dr.readInt();
            ii.uses[x] = dr.readInt();
        }
        for (int x = 0; x < ii.equipment.length; x++) {
            final Equipment ei = Equipment.readEquipmentXML(dr);
            if (ei != null) {
                ii.equipment[x] = ei;
            }
        }
        return ii;
    }

    public void writeItemInventoryXML(final XDataWriter dw) throws IOException {
        dw.writeInt(this.quantity.length);
        for (int x = 0; x < this.quantity.length; x++) {
            dw.writeInt(this.quantity[x]);
            dw.writeInt(this.uses[x]);
        }
        for (final Equipment ei : this.equipment) {
            if (ei != null) {
                ei.writeEquipmentXML(dw);
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
        result = prime * result + Arrays.hashCode(this.initialUses);
        result = prime * result + Arrays.hashCode(this.inventory);
        result = prime * result + Arrays.hashCode(this.quantity);
        result = prime * result
                + (this.socks == null ? 0 : this.socks.hashCode());
        result = prime * result + Arrays.hashCode(this.uses);
        return result;
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
        if (!Arrays.equals(this.initialUses, other.initialUses)) {
            return false;
        }
        if (!Arrays.equals(this.inventory, other.inventory)) {
            return false;
        }
        if (!Arrays.equals(this.quantity, other.quantity)) {
            return false;
        }
        if (this.socks == null) {
            if (other.socks != null) {
                return false;
            }
        } else if (!this.socks.equals(other.socks)) {
            return false;
        }
        if (!Arrays.equals(this.uses, other.uses)) {
            return false;
        }
        return true;
    }
}
