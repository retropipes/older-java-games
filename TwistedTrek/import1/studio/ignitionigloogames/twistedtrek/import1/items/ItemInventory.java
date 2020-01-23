/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.items;

import java.io.IOException;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.items.combat.CombatItemList;
import studio.ignitionigloogames.xio.XDataReader;
import studio.ignitionigloogames.xio.XDataWriter;

public class ItemInventory {
    // Properties
    private final Item[] inventory;
    private final int[] quantity;
    private final int[] uses;
    private final int[] initialUses;
    private final CombatItemList cil;
    private final Equipment[] equipment;
    private static final int MAX_ITEMS = 200;

    // Constructors
    public ItemInventory() {
	this.cil = Import1.getApplication().getCombatItems();
	this.inventory = this.cil.getAllItems();
	this.uses = new int[ItemInventory.MAX_ITEMS];
	this.quantity = new int[ItemInventory.MAX_ITEMS];
	this.initialUses = this.cil.getAllInitialUses();
	this.equipment = new Equipment[EquipmentSlotConstants.MAX_SLOTS];
    }

    // Methods
    public int getItemCount(final Item i) {
	final int index = this.indexOf(i);
	if (index != -1) {
	    return this.quantity[index];
	} else {
	    return 0;
	}
    }

    private int indexOf(final Item i) {
	final Item[] items = this.cil.getAllItems();
	for (int x = 0; x < items.length; x++) {
	    if (items[x].getName().equals(i.getName())) {
		return x;
	    }
	}
	return -1;
    }

    public boolean isItemThere(final Item i) {
	return this.indexOf(i) != -1;
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

    public void useItem(final Item i) {
	final int existIndex = this.indexOf(i);
	if (existIndex != -1) {
	    this.uses[existIndex]--;
	    if (this.uses[existIndex] == 0) {
		if (this.quantity[existIndex] > 0) {
		    this.quantity[existIndex]--;
		    if (this.quantity[existIndex] > 0) {
			this.uses[existIndex] = this.initialUses[existIndex];
		    }
		}
	    }
	}
    }

    public void removeItem(final Item i) {
	// Find item to remove
	final int index = this.indexOf(i);
	if (index == -1) {
	    // Didn't find the item
	    return;
	}
	if (this.quantity[index] < 0) {
	    // Quantity messed up
	    this.quantity[index] = 0;
	    // Remove item at index
	    this.uses[index] = 0;
	    return;
	}
	// Decrement quantity
	this.quantity[index]--;
	// If quantity is now zero...
	if (this.quantity[index] == 0) {
	    // Remove item at index
	    this.uses[index] = 0;
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

    public boolean isAnythingEquippedInFirstSlot(final Equipment ei) {
	boolean result = false;
	final int first = ei.getFirstSlotUsed();
	result = this.equipment[first] != null;
	return result;
    }

    public boolean isAnythingEquippedInSecondSlot(final Equipment ei) {
	boolean result = false;
	final int second = ei.getSecondSlotUsed();
	result = this.equipment[second] != null;
	return result;
    }

    public boolean isEquipmentInSlot(final int slot) {
	return this.equipment[slot] != null;
    }

    public String[] generateInventoryStringArray(final int offset) {
	final String[] result = new String[this.inventory.length];
	final StringBuilder[] sb = new StringBuilder[this.inventory.length];
	for (int x = 0; x < result.length; x++) {
	    sb[x] = new StringBuilder();
	    sb[x].append("Slot ");
	    sb[x].append(x + offset + 1);
	    sb[x].append(": ");
	    if (this.inventory[x] == null) {
		sb[x].append("Nothing (Qty: 0, Uses: 0)");
	    } else {
		sb[x].append(this.inventory[x].getName());
		sb[x].append(" (Qty: ");
		sb[x].append(this.quantity[x]);
		sb[x].append(", Uses: ");
		sb[x].append(this.uses[x]);
		sb[x].append(")");
	    }
	    result[x] = sb[x].toString();
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
	final StringBuilder[] sb = new StringBuilder[combatUsableCounter];
	combatUsableCounter = 0;
	for (final Item element : this.inventory) {
	    if (element != null) {
		if (element.isCombatUsable()) {
		    sb[combatUsableCounter] = new StringBuilder();
		    sb[combatUsableCounter].append(element.getName());
		    result[combatUsableCounter] = sb[combatUsableCounter].toString();
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
	final StringBuilder[] sb = new StringBuilder[combatUsableCounter];
	combatUsableCounter = 0;
	for (int x = 0; x < this.inventory.length; x++) {
	    if (this.inventory[x] != null) {
		if (this.inventory[x].isCombatUsable()) {
		    sb[combatUsableCounter] = new StringBuilder();
		    sb[combatUsableCounter].append(this.inventory[x].getName());
		    sb[combatUsableCounter].append(" (Qty: ");
		    sb[combatUsableCounter].append(this.quantity[x]);
		    sb[combatUsableCounter].append(", Uses: ");
		    sb[combatUsableCounter].append(this.uses[x]);
		    sb[combatUsableCounter].append(")");
		    result[combatUsableCounter] = sb[combatUsableCounter].toString();
		    combatUsableCounter++;
		}
	    }
	}
	return result;
    }

    public String[] generateEquipmentStringArray() {
	final String[] result = new String[this.equipment.length];
	final StringBuilder[] sb = new StringBuilder[this.equipment.length];
	for (int x = 0; x < result.length; x++) {
	    sb[x] = new StringBuilder();
	    sb[x].append(EquipmentSlotConstants.SLOT_NAMES[x]);
	    sb[x].append(": ");
	    if (this.equipment[x] == null) {
		sb[x].append("Nothing (0)");
	    } else {
		sb[x].append(this.equipment[x].getName());
		sb[x].append(" (");
		sb[x].append(this.equipment[x].getPotency());
		sb[x].append(")");
	    }
	    result[x] = sb[x].toString();
	}
	return result;
    }

    public int getTotalPower() {
	int total = 0;
	if (this.equipment[EquipmentSlotConstants.SLOT_MAINHAND] != null) {
	    total += this.equipment[EquipmentSlotConstants.SLOT_MAINHAND].getPotency();
	}
	if (this.equipment[EquipmentSlotConstants.SLOT_OFFHAND] != null) {
	    if (this.equipment[EquipmentSlotConstants.SLOT_OFFHAND]
		    .getCategory() == EquipmentCategoryConstants.EQUIPMENT_CATEGORY_ONE_HANDED_WEAPON) {
		total += this.equipment[EquipmentSlotConstants.SLOT_OFFHAND].getPotency();
	    } else if (this.equipment[EquipmentSlotConstants.SLOT_OFFHAND]
		    .getCategory() == EquipmentCategoryConstants.EQUIPMENT_CATEGORY_TWO_HANDED_WEAPON) {
		total += this.equipment[EquipmentSlotConstants.SLOT_OFFHAND].getPotency();
		total *= StatConstants.FACTOR_TWO_HANDED_BONUS;
	    }
	}
	return total;
    }

    public int getTotalAbsorb() {
	int total = 0;
	if (this.equipment[EquipmentSlotConstants.SLOT_HEAD] != null) {
	    total += this.equipment[EquipmentSlotConstants.SLOT_HEAD].getPotency();
	}
	if (this.equipment[EquipmentSlotConstants.SLOT_NECK] != null) {
	    total += this.equipment[EquipmentSlotConstants.SLOT_NECK].getPotency();
	}
	if (this.equipment[EquipmentSlotConstants.SLOT_OFFHAND] != null) {
	    if (this.equipment[EquipmentSlotConstants.SLOT_OFFHAND]
		    .getEquipCategory() == EquipmentCategoryConstants.EQUIPMENT_CATEGORY_ARMOR) {
		total += this.equipment[EquipmentSlotConstants.SLOT_OFFHAND].getPotency();
	    }
	}
	if (this.equipment[EquipmentSlotConstants.SLOT_BODY] != null) {
	    total += this.equipment[EquipmentSlotConstants.SLOT_BODY].getPotency();
	}
	if (this.equipment[EquipmentSlotConstants.SLOT_BACK] != null) {
	    total += this.equipment[EquipmentSlotConstants.SLOT_BACK].getPotency();
	}
	if (this.equipment[EquipmentSlotConstants.SLOT_UPPER_TORSO] != null) {
	    total += this.equipment[EquipmentSlotConstants.SLOT_UPPER_TORSO].getPotency();
	}
	if (this.equipment[EquipmentSlotConstants.SLOT_ARMS] != null) {
	    total += this.equipment[EquipmentSlotConstants.SLOT_ARMS].getPotency();
	}
	if (this.equipment[EquipmentSlotConstants.SLOT_HANDS] != null) {
	    total += this.equipment[EquipmentSlotConstants.SLOT_HANDS].getPotency();
	}
	if (this.equipment[EquipmentSlotConstants.SLOT_FINGERS] != null) {
	    total += this.equipment[EquipmentSlotConstants.SLOT_FINGERS].getPotency();
	}
	if (this.equipment[EquipmentSlotConstants.SLOT_LOWER_TORSO] != null) {
	    total += this.equipment[EquipmentSlotConstants.SLOT_LOWER_TORSO].getPotency();
	}
	if (this.equipment[EquipmentSlotConstants.SLOT_LEGS] != null) {
	    total += this.equipment[EquipmentSlotConstants.SLOT_LEGS].getPotency();
	}
	if (this.equipment[EquipmentSlotConstants.SLOT_FEET] != null) {
	    total += this.equipment[EquipmentSlotConstants.SLOT_FEET].getPotency();
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

    public static ItemInventory readItemInventory(final XDataReader dr) throws IOException {
	final ItemInventory ii = new ItemInventory();
	final int z = dr.readInt();
	for (int x = 0; x < z; x++) {
	    ii.quantity[x] = dr.readInt();
	    ii.uses[x] = dr.readInt();
	}
	for (int x = 0; x < ii.equipment.length; x++) {
	    final Equipment ei = Equipment.readEquipment(dr);
	    if (ei != null) {
		ii.equipment[x] = ei;
	    }
	}
	return ii;
    }

    public void writeItemInventory(final XDataWriter dw) throws IOException {
	dw.writeInt(this.quantity.length);
	for (int x = 0; x < this.quantity.length; x++) {
	    dw.writeInt(this.quantity[x]);
	    dw.writeInt(this.uses[x]);
	}
	for (final Equipment ei : this.equipment) {
	    if (ei != null) {
		ei.writeItem(dw);
	    } else {
		dw.writeString("null");
	    }
	}
    }
}
