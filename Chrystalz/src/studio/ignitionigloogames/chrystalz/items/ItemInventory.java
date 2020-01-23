/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.


All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.items;

import java.io.IOException;
import java.util.Arrays;

import studio.ignitionigloogames.chrystalz.creatures.AbstractCreature;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundManager;
import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;

public class ItemInventory {
    // Properties
    private Equipment[] equipment;

    // Constructors
    public ItemInventory() {
        this.equipment = new Equipment[EquipmentSlotConstants.MAX_SLOTS];
    }

    // Methods
    public void resetInventory() {
        Arrays.fill(this.equipment, null);
    }

    public void equip(final AbstractCreature pc, final Equipment ei,
            final boolean playSound) {
        // Fix character load, changing gear
        if (this.equipment[ei.getSlotUsed()] != null) {
            pc.offsetLoad(-this.equipment[ei.getSlotUsed()].getWeight());
        }
        pc.offsetLoad(ei.getWeight());
        // Equip it
        this.equipment[ei.getSlotUsed()] = ei;
        if (playSound) {
            SoundManager.playSound(SoundConstants.EQUIP);
        }
    }

    public int getWeaponHitSound(final AbstractCreature pc) {
        Equipment weapon = this.equipment[EquipmentSlotConstants.SLOT_WEAPON];
        if (weapon != null) {
            return weapon.getHitSound();
        }
        if (pc.getTeamID() == AbstractCreature.TEAM_PARTY) {
            return SoundConstants.ATTACK_HIT;
        }
        return SoundConstants.MONSTER_HIT;
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
        if (this.equipment[EquipmentSlotConstants.SLOT_WEAPON] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_WEAPON]
                    .getPotency();
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
        if (this.equipment[EquipmentSlotConstants.SLOT_ARMS] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_ARMS]
                    .getPotency();
        }
        if (this.equipment[EquipmentSlotConstants.SLOT_HANDS] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_HANDS]
                    .getPotency();
        }
        if (this.equipment[EquipmentSlotConstants.SLOT_BODY] != null) {
            total += this.equipment[EquipmentSlotConstants.SLOT_BODY]
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
                total += this.equipment[x].getWeight();
            }
        }
        return total;
    }

    public static ItemInventory readItemInventory(final FileIOReader dr)
            throws IOException {
        final ItemInventory ii = new ItemInventory();
        for (int x = 0; x < ii.equipment.length; x++) {
            final Equipment ei = Equipment.readEquipment(dr);
            if (ei != null) {
                ii.equipment[x] = ei;
            }
        }
        return ii;
    }

    public void writeItemInventory(final FileIOWriter dw) throws IOException {
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
        final int result = 1;
        return prime * result + Arrays.hashCode(this.equipment);
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
        return true;
    }
}
