/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.items;

import java.io.IOException;

import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;

public class Equipment extends Item {
    // Properties
    private final int materialID;
    private final int slotUsed;
    private final int hitSound;

    // Constructors
    Equipment(final String itemName, final int buyFor, final int grams,
            final int power, final int slot, final int newMaterialID,
            final int hitSoundID) {
        super(itemName, buyFor, grams, power);
        this.materialID = newMaterialID;
        this.slotUsed = slot;
        this.hitSound = hitSoundID;
    }

    Equipment(final Equipment e) {
        super(e);
        this.materialID = e.materialID;
        this.slotUsed = e.slotUsed;
        this.hitSound = e.hitSound;
    }

    // Methods
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + this.slotUsed;
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
        if (this.slotUsed != other.slotUsed) {
            return false;
        }
        if (this.materialID != other.materialID) {
            return false;
        }
        return true;
    }

    public final int getHitSound() {
        return this.hitSound;
    }

    public final int getSlotUsed() {
        return this.slotUsed;
    }

    public final int getMaterial() {
        return this.materialID;
    }

    static Equipment readEquipment(final FileIOReader dr) throws IOException {
        final Item i = Item.readItem(dr);
        if (i == null) {
            // Abort
            return null;
        }
        final int matID = dr.readInt();
        final int slot = dr.readInt();
        final int hs = dr.readInt();
        return new Equipment(i.getName(), i.getBuyPrice(), i.getWeight(),
                i.getPotency(), slot, matID, hs);
    }

    final void writeEquipment(final FileIOWriter dw) throws IOException {
        super.writeItem(dw);
        dw.writeInt(this.materialID);
        dw.writeInt(this.slotUsed);
        dw.writeInt(this.hitSound);
    }
}
