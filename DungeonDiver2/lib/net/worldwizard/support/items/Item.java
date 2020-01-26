package net.worldwizard.support.items;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import net.worldwizard.support.IDGenerator;
import net.worldwizard.support.Identifiable;
import net.worldwizard.support.Support;
import net.worldwizard.support.variables.Extension;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class Item extends Identifiable {
    // Properties
    private String name;
    private int initialUses;
    private int weightPerUse;
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
        super(true);
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
                + (this.name == null ? 0 : this.name.hashCode());
        result = prime * result + this.potency;
        result = prime * result + this.sellPrice;
        result = prime * result + this.weight;
        result = prime * result + this.weightPerUse;
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
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
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

    public void setName(final String newName) {
        this.name = newName;
    }

    public void setWeightPerUse(final int wpu) {
        this.weightPerUse = wpu;
    }

    public void setWeight(final int newWeight) {
        this.weight = newWeight;
    }

    public void setPotency(final int newPotency) {
        this.potency = newPotency;
    }

    public void setUses(final int newUses) {
        this.uses = newUses;
        this.initialUses = newUses;
    }

    public void setBuyPrice(final int newBuyPrice) {
        this.buyPrice = newBuyPrice;
    }

    public void setSellPrice(final int newSellPrice) {
        this.sellPrice = newSellPrice;
    }

    public void setCombatUsable(final boolean isCombatUsable) {
        this.combatUsable = isCombatUsable;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getBuyPrice() {
        return this.buyPrice;
    }

    public int getSellPrice() {
        return this.sellPrice;
    }

    public int getPotency() {
        return this.potency;
    }

    public int getBaseWeight() {
        return this.weight;
    }

    public int getWeightPerUse() {
        return this.weightPerUse;
    }

    public int getEffectiveWeight() {
        return this.getBaseWeight() + this.getUses() * this.getWeightPerUse();
    }

    public int getInitialUses() {
        return this.initialUses;
    }

    public int getUses() {
        return this.uses;
    }

    public boolean isUsable() {
        return this.uses > 0;
    }

    public boolean use() {
        if (this.uses > 0) {
            this.uses--;
            return true;
        } else {
            return false;
        }
    }

    public boolean isCombatUsable() {
        return this.isUsable() && this.combatUsable;
    }

    public static Item read(final XDataReader dr) throws IOException {
        final String itemName = dr.readString();
        if (itemName.equals("null")) {
            // Abort
            return null;
        }
        final int itemInitialUses = dr.readInt();
        final int itemWeightPerUse = dr.readInt();
        final Item i = new Item(itemName, itemInitialUses, itemWeightPerUse);
        i.uses = dr.readInt();
        i.buyPrice = dr.readInt();
        i.sellPrice = dr.readInt();
        i.weight = dr.readInt();
        i.potency = dr.readInt();
        i.combatUsable = dr.readBoolean();
        return i;
    }

    public void write(final XDataWriter dw) throws IOException {
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

    @Override
    public BigInteger computeLongHash() {
        BigInteger longHash = BigInteger.ZERO;
        longHash = longHash.add(
                IDGenerator.computeStringLongHash(this.getClass().getName())
                        .multiply(BigInteger.valueOf(2)));
        longHash = longHash.add(IDGenerator.computeStringLongHash(this.name)
                .multiply(BigInteger.valueOf(3)));
        longHash = longHash
                .add(IDGenerator.computeLongLongHash(this.initialUses)
                        .multiply(BigInteger.valueOf(4)));
        longHash = longHash
                .add(IDGenerator.computeLongLongHash(this.weightPerUse)
                        .multiply(BigInteger.valueOf(5)));
        longHash = longHash.add(IDGenerator.computeLongLongHash(this.buyPrice)
                .multiply(BigInteger.valueOf(6)));
        longHash = longHash.add(IDGenerator.computeLongLongHash(this.sellPrice)
                .multiply(BigInteger.valueOf(7)));
        longHash = longHash.add(IDGenerator.computeLongLongHash(this.weight)
                .multiply(BigInteger.valueOf(8)));
        longHash = longHash.add(IDGenerator.computeLongLongHash(this.potency)
                .multiply(BigInteger.valueOf(9)));
        longHash = longHash
                .add(IDGenerator.computeBooleanLongHash(this.combatUsable)
                        .multiply(BigInteger.TEN));
        return longHash;
    }

    @Override
    public void dumpContentsToFile() throws IOException {
        final File dir = new File(Support.getSystemVariables().getBasePath()
                + File.separator + "items");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final XDataWriter writer = new XDataWriter(
                Support.getSystemVariables().getBasePath() + File.separator
                        + "items" + File.separator + this.getID()
                        + Extension.getItemExtensionWithPeriod(),
                Extension.getItemExtension());
        this.write(writer);
        writer.close();
    }

    @Override
    public String getTypeName() {
        return "Item";
    }

    @Override
    public String getPluralTypeName() {
        return "items";
    }
}
