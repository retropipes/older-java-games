package net.worldwizard.support.items;

import java.io.IOException;

import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class Equipment extends Item {
    // Properties
    private final int equipCat;
    private final int materialID;
    private int firstSlotUsed;
    private int secondSlotUsed;
    private boolean conditionalSlot;

    // Constructors
    private Equipment(final Item i, final int equipCategory,
            final int newMaterialID) {
        super(i.getName(), i.getInitialUses(), i.getWeightPerUse());
        this.equipCat = equipCategory;
        this.materialID = newMaterialID;
        this.firstSlotUsed = EquipmentSlotConstants.SLOT_NONE;
        this.secondSlotUsed = EquipmentSlotConstants.SLOT_NONE;
        this.conditionalSlot = false;
    }

    protected Equipment(final String itemName, final int cost) {
        super(itemName, 0, 0);
        this.equipCat = EquipmentCategoryConstants.EQUIPMENT_CATEGORY_ARMOR;
        this.materialID = ArmorMaterialConstants.MATERIAL_NONE;
        this.firstSlotUsed = EquipmentSlotConstants.SLOT_SOCKS;
        this.secondSlotUsed = EquipmentSlotConstants.SLOT_NONE;
        this.conditionalSlot = false;
        this.setBuyPrice(cost);
    }

    Equipment(final String itemName, final int itemInitialUses,
            final int itemWeightPerUse, final int equipCategory,
            final int newMaterialID) {
        super(itemName, itemInitialUses, itemWeightPerUse);
        this.equipCat = equipCategory;
        this.materialID = newMaterialID;
        this.firstSlotUsed = EquipmentSlotConstants.SLOT_NONE;
        this.secondSlotUsed = EquipmentSlotConstants.SLOT_NONE;
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
        result = prime * result + this.materialID;
        result = prime * result + this.secondSlotUsed;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
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
        if (this.secondSlotUsed != other.secondSlotUsed) {
            return false;
        }
        return true;
    }

    public int getFirstSlotUsed() {
        return this.firstSlotUsed;
    }

    public void setFirstSlotUsed(final int newFirstSlotUsed) {
        this.firstSlotUsed = newFirstSlotUsed;
    }

    public int getSecondSlotUsed() {
        return this.secondSlotUsed;
    }

    public void setSecondSlotUsed(final int newSecondSlotUsed) {
        this.secondSlotUsed = newSecondSlotUsed;
    }

    public void setConditionalSlot(final boolean newConditionalSlot) {
        this.conditionalSlot = newConditionalSlot;
    }

    public int getEquipCategory() {
        return this.equipCat;
    }

    public static Equipment readEquipmentXML(final XDataReader dr)
            throws IOException {
        final Item i = Item.read(dr);
        if (i == null) {
            // Abort
            return null;
        }
        final int matID = dr.readInt();
        final int eCat = dr.readInt();
        final Equipment ei = new Equipment(i, eCat, matID);
        ei.firstSlotUsed = dr.readInt();
        ei.secondSlotUsed = dr.readInt();
        ei.conditionalSlot = dr.readBoolean();
        return ei;
    }

    public void writeEquipmentXML(final XDataWriter dw) throws IOException {
        super.write(dw);
        dw.writeInt(this.materialID);
        dw.writeInt(this.equipCat);
        dw.writeInt(this.firstSlotUsed);
        dw.writeInt(this.secondSlotUsed);
        dw.writeBoolean(this.conditionalSlot);
    }
}
