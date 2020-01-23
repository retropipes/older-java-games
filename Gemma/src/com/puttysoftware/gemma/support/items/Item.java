/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.items;

import java.io.IOException;

import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class Item {
    // Properties
    private String name;
    private final int initialUses;
    private final int weightPerUse;
    private int uses;
    private int buyPrice;
    private int sellPrice;
    private int weight;
    private int potency;
    private boolean combatUsable;

    // Constructors
    public Item() {
        super();
        this.name = "Un-named Item";
        this.initialUses = 0;
        this.uses = 0;
        this.weightPerUse = 0;
        this.buyPrice = 0;
        this.sellPrice = 0;
        this.weight = 0;
        this.potency = 0;
        this.combatUsable = false;
    }

    public Item(final String itemName, final int itemInitialUses,
            final int itemWeightPerUse) {
        super();
        this.name = itemName;
        this.initialUses = itemInitialUses;
        this.uses = itemInitialUses;
        this.weightPerUse = itemWeightPerUse;
        this.buyPrice = 0;
        this.sellPrice = 0;
        this.weight = 0;
        this.potency = 0;
        this.combatUsable = false;
    }

    protected Item(final String iName, final Item i) {
        super();
        this.name = iName;
        this.initialUses = i.initialUses;
        this.uses = i.uses;
        this.weightPerUse = i.weightPerUse;
        this.buyPrice = i.buyPrice;
        this.sellPrice = i.sellPrice;
        this.weight = i.weight;
        this.potency = i.potency;
        this.combatUsable = false;
    }

    // Methods
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.buyPrice;
        result = prime * result + (this.combatUsable ? 1231 : 1237);
        result = prime * result + this.initialUses;
        result = prime * result
                + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + this.potency;
        result = prime * result + this.sellPrice;
        result = prime * result + this.weight;
        return prime * result + this.weightPerUse;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Item other = (Item) obj;
        if (this.buyPrice != other.buyPrice) {
            return false;
        }
        if (this.combatUsable != other.combatUsable) {
            return false;
        }
        if (this.initialUses != other.initialUses) {
            return false;
        }
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        if (this.potency != other.potency) {
            return false;
        }
        if (this.sellPrice != other.sellPrice) {
            return false;
        }
        if (this.weight != other.weight) {
            return false;
        }
        if (this.weightPerUse != other.weightPerUse) {
            return false;
        }
        return true;
    }

    final void setName(String newName) {
        this.name = newName;
    }

    final void setPotency(int newPotency) {
        this.potency = newPotency;
    }

    public final void setBuyPrice(int newBuyPrice) {
        this.buyPrice = newBuyPrice;
    }

    final void setSellPrice(int newSellPrice) {
        this.sellPrice = newSellPrice;
    }

    public final void setCombatUsable(boolean isCombatUsable) {
        this.combatUsable = isCombatUsable;
    }

    public String getName() {
        return this.name;
    }

    final int getBuyPrice() {
        return this.buyPrice;
    }

    final int getPotency() {
        return this.potency;
    }

    private final int getBaseWeight() {
        return this.weight;
    }

    public final int getWeightPerUse() {
        return this.weightPerUse;
    }

    final int getEffectiveWeight() {
        return this.getBaseWeight() + this.getUses() * this.getWeightPerUse();
    }

    public final int getInitialUses() {
        return this.initialUses;
    }

    private final int getUses() {
        return this.uses;
    }

    private final boolean isUsable() {
        return this.uses > 0;
    }

    public final boolean use() {
        if (this.uses > 0) {
            this.uses--;
            return true;
        } else {
            return false;
        }
    }

    final boolean isCombatUsable() {
        return this.isUsable() && this.combatUsable;
    }

    protected static Item readItem(XDataReader dr) throws IOException {
        String itemName = dr.readString();
        if (itemName.equals("null")) {
            // Abort
            return null;
        }
        int itemInitialUses = dr.readInt();
        int itemWeightPerUse = dr.readInt();
        Item i = new Item(itemName, itemInitialUses, itemWeightPerUse);
        i.uses = dr.readInt();
        i.buyPrice = dr.readInt();
        i.sellPrice = dr.readInt();
        i.weight = dr.readInt();
        i.potency = dr.readInt();
        i.combatUsable = dr.readBoolean();
        return i;
    }

    final void writeItem(XDataWriter dw) throws IOException {
        dw.writeString(this.name);
        dw.writeInt(this.initialUses);
        dw.writeInt(this.weightPerUse);
        dw.writeInt(this.uses);
        dw.writeInt(this.buyPrice);
        dw.writeInt(this.sellPrice);
        dw.writeInt(this.weight);
        dw.writeInt(this.potency);
        dw.writeBoolean(this.combatUsable);
    }
}
