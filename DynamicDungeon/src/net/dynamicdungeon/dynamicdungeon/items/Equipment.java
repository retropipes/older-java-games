/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.items;

import java.io.IOException;

import net.dynamicdungeon.dbio.DatabaseReader;
import net.dynamicdungeon.dbio.DatabaseWriter;

public class Equipment extends Item {
    // Properties
    private final int equipCat;
    private final int materialID;
    private int firstSlotUsed;
    private boolean conditionalSlot;

    // Constructors
    private Equipment(final Item i, final int equipCategory,
            final int newMaterialID) {
        super(i.getName(), i.getInitialUses(), i.getWeightPerUse());
        this.equipCat = equipCategory;
        this.materialID = newMaterialID;
        this.firstSlotUsed = EquipmentSlotConstants.SLOT_NONE;
        this.conditionalSlot = false;
    }

    Equipment(final String itemName, final int itemInitialUses,
            final int itemWeightPerUse, final int equipCategory,
            final int newMaterialID) {
        super(itemName, itemInitialUses, itemWeightPerUse);
        this.equipCat = equipCategory;
        this.materialID = newMaterialID;
        this.firstSlotUsed = EquipmentSlotConstants.SLOT_NONE;
        this.conditionalSlot = false;
    }

    // Methods
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.conditionalSlot ? 1231 : 1237);
        result = prime * result + this.equipCat;
        result = prime * result + this.firstSlotUsed;
        return prime * result + this.materialID;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Equipment)) {
            return false;
        }
        final Equipment other = (Equipment) obj;
        if (this.conditionalSlot != other.conditionalSlot) {
            return false;
        }
        if (this.equipCat != other.equipCat) {
            return false;
        }
        if (this.firstSlotUsed != other.firstSlotUsed) {
            return false;
        }
        if (this.materialID != other.materialID) {
            return false;
        }
        return true;
    }

    public final int getFirstSlotUsed() {
        return this.firstSlotUsed;
    }

    public final void setFirstSlotUsed(final int newFirstSlotUsed) {
        this.firstSlotUsed = newFirstSlotUsed;
    }

    public final void setConditionalSlot(final boolean newConditionalSlot) {
        this.conditionalSlot = newConditionalSlot;
    }

    public final int getEquipCategory() {
        return this.equipCat;
    }

    public final int getMaterial() {
        return this.materialID;
    }

    static Equipment readEquipment(final DatabaseReader dr) throws IOException {
        final Item i = Item.readItem(dr);
        if (i == null) {
            // Abort
            return null;
        }
        final int matID = dr.readInt();
        final int eCat = dr.readInt();
        final Equipment ei = new Equipment(i, eCat, matID);
        ei.firstSlotUsed = dr.readInt();
        ei.conditionalSlot = dr.readBoolean();
        return ei;
    }

    final void writeEquipment(final DatabaseWriter dw) throws IOException {
        super.writeItem(dw);
        dw.writeInt(this.materialID);
        dw.writeInt(this.equipCat);
        dw.writeInt(this.firstSlotUsed);
        dw.writeBoolean(this.conditionalSlot);
    }
}
