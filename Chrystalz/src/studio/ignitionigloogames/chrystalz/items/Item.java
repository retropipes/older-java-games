/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.items;

import java.io.IOException;

import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;

public class Item {
    // Properties
    private final String name;
    private final int buyPrice;
    private final int weight;
    private final int potency;

    // Constructors
    public Item(final String itemName, final int buyFor, final int grams,
            final int power) {
        super();
        this.name = itemName;
        this.buyPrice = buyFor;
        this.weight = grams;
        this.potency = power;
    }

    protected Item(final Item i) {
        super();
        this.name = i.getName();
        this.buyPrice = i.buyPrice;
        this.weight = i.weight;
        this.potency = i.potency;
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
        result = prime * result
                + (this.name == null ? 0 : this.name.hashCode());
        result = prime * result + this.potency;
        return prime * result + this.weight;
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
        if (this.weight != other.weight) {
            return false;
        }
        return true;
    }

    public String getName() {
        return this.name;
    }

    public final int getBuyPrice() {
        return this.buyPrice;
    }

    public final int getPotency() {
        return this.potency;
    }

    public final int getWeight() {
        return this.weight;
    }

    protected static Item readItem(final FileIOReader dr) throws IOException {
        final String itemName = dr.readString();
        if (itemName.equals("null")) {
            // Abort
            return null;
        }
        final int buyFor = dr.readInt();
        final int grams = dr.readInt();
        final int power = dr.readInt();
        return new Item(itemName, buyFor, grams, power);
    }

    final void writeItem(final FileIOWriter dw) throws IOException {
        dw.writeString(this.name);
        dw.writeInt(this.buyPrice);
        dw.writeInt(this.weight);
        dw.writeInt(this.potency);
    }
}
