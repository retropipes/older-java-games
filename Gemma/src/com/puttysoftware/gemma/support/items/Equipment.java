/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.items;

import java.io.IOException;

import com.puttysoftware.gemma.support.creatures.faiths.FaithConstants;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class Equipment extends Item {
    // Properties
    private final int equipCat;
    private final int materialID;
    private int firstSlotUsed;
    private int secondSlotUsed;
    private boolean conditionalSlot;
    private int[] faithPowersApplied;
    private String[] faithPowerName;

    // Constructors
    private Equipment(Item i, int equipCategory, int newMaterialID) {
        super(i.getName(), i.getInitialUses(), i.getWeightPerUse());
        this.equipCat = equipCategory;
        this.materialID = newMaterialID;
        this.firstSlotUsed = EquipmentSlotConstants.SLOT_NONE;
        this.secondSlotUsed = EquipmentSlotConstants.SLOT_NONE;
        this.conditionalSlot = false;
        this.initFaithPowers();
    }

    protected Equipment(final String itemName, final int cost) {
        super(itemName, 0, 0);
        this.equipCat = EquipmentCategoryConstants.EQUIPMENT_CATEGORY_ARMOR;
        this.materialID = ArmorMaterialConstants.MATERIAL_NONE;
        this.firstSlotUsed = EquipmentSlotConstants.SLOT_SOCKS;
        this.secondSlotUsed = EquipmentSlotConstants.SLOT_NONE;
        this.conditionalSlot = false;
        this.setBuyPrice(cost);
        this.initFaithPowers();
    }

    Equipment(final String itemName, final int itemInitialUses,
            final int itemWeightPerUse, int equipCategory,
            final int newMaterialID) {
        super(itemName, itemInitialUses, itemWeightPerUse);
        this.equipCat = equipCategory;
        this.materialID = newMaterialID;
        this.firstSlotUsed = EquipmentSlotConstants.SLOT_NONE;
        this.secondSlotUsed = EquipmentSlotConstants.SLOT_NONE;
        this.conditionalSlot = false;
        this.initFaithPowers();
    }

    Equipment(final Equipment e) {
        super(e.getItemName(), e);
        this.equipCat = e.equipCat;
        this.materialID = e.materialID;
        this.firstSlotUsed = e.firstSlotUsed;
        this.secondSlotUsed = e.secondSlotUsed;
        this.conditionalSlot = e.conditionalSlot;
        this.initFaithPowers();
        System.arraycopy(e.faithPowersApplied, 0, this.faithPowersApplied, 0,
                e.faithPowersApplied.length);
        System.arraycopy(e.faithPowerName, 0, this.faithPowerName, 0,
                e.faithPowerName.length);
    }

    // Methods
    private void initFaithPowers() {
        this.faithPowersApplied = new int[FaithConstants.getFaithsCount()];
        this.faithPowerName = new String[FaithConstants.getFaithsCount()];
        for (int z = 0; z < this.faithPowerName.length; z++) {
            this.faithPowerName[z] = "";
        }
    }

    @Override
    public String getName() {
        StringBuilder faithBuilder = new StringBuilder();
        int fc = FaithConstants.getFaithsCount();
        for (int z = 0; z < fc; z++) {
            if (!this.faithPowerName[z].isEmpty()) {
                faithBuilder.append(this.faithPowerName[z]);
            }
        }
        return faithBuilder.toString() + super.getName();
    }

    private String getItemName() {
        return super.getName();
    }

    final void enchantName(int bonus) {
        String oldName = this.getName();
        // Check - is name enchanted already?
        if (oldName.charAt(oldName.length() - 2) == '+') {
            // Yes - remove old enchantment
            oldName = oldName.substring(0, oldName.length() - 3);
        }
        String newName = oldName + " +" + bonus;
        this.setName(newName);
    }

    final void applyFaithPower(int fid, int bonus) {
        String fpName = FaithConstants.getFaithPowerName(fid, bonus);
        this.faithPowerName[fid] = fpName + " ";
        this.faithPowersApplied[fid]++;
    }

    final int getFirstSlotUsed() {
        return this.firstSlotUsed;
    }

    final void setFirstSlotUsed(int newFirstSlotUsed) {
        this.firstSlotUsed = newFirstSlotUsed;
    }

    final int getSecondSlotUsed() {
        return this.secondSlotUsed;
    }

    final void setSecondSlotUsed(int newSecondSlotUsed) {
        this.secondSlotUsed = newSecondSlotUsed;
    }

    final void setConditionalSlot(boolean newConditionalSlot) {
        this.conditionalSlot = newConditionalSlot;
    }

    public final int getEquipCategory() {
        return this.equipCat;
    }

    final int getMaterial() {
        return this.materialID;
    }

    public final int getFaithPowerLevel(int fid) {
        return this.faithPowersApplied[fid];
    }

    final boolean isTwoHanded() {
        return this.firstSlotUsed == EquipmentSlotConstants.SLOT_MAINHAND
                && this.secondSlotUsed == EquipmentSlotConstants.SLOT_OFFHAND
                && !this.conditionalSlot;
    }

    static Equipment readEquipment(XDataReader dr) throws IOException {
        Item i = Item.readItem(dr);
        if (i == null) {
            // Abort
            return null;
        }
        int matID = dr.readInt();
        int eCat = dr.readInt();
        Equipment ei = new Equipment(i, eCat, matID);
        ei.firstSlotUsed = dr.readInt();
        ei.secondSlotUsed = dr.readInt();
        ei.conditionalSlot = dr.readBoolean();
        int fc = FaithConstants.getFaithsCount();
        for (int z = 0; z < fc; z++) {
            ei.faithPowerName[z] = dr.readString();
            ei.faithPowersApplied[z] = dr.readInt();
        }
        return ei;
    }

    final void writeEquipment(XDataWriter dw) throws IOException {
        super.writeItem(dw);
        dw.writeInt(this.materialID);
        dw.writeInt(this.equipCat);
        dw.writeInt(this.firstSlotUsed);
        dw.writeInt(this.secondSlotUsed);
        dw.writeBoolean(this.conditionalSlot);
        int fc = FaithConstants.getFaithsCount();
        for (int z = 0; z < fc; z++) {
            dw.writeString(this.faithPowerName[z]);
            dw.writeInt(this.faithPowersApplied[z]);
        }
    }
}
