/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.items;

import studio.ignitionigloogames.chrystalz.manager.string.LocalizedFile;
import studio.ignitionigloogames.chrystalz.manager.string.StringManager;

public class EquipmentSlotConstants {
    static final int SLOT_NONE = -1;
    public static final int SLOT_HEAD = 0;
    public static final int SLOT_NECK = 1;
    public static final int SLOT_ARMS = 2;
    public static final int SLOT_WEAPON = 3;
    public static final int SLOT_HANDS = 4;
    public static final int SLOT_BODY = 5;
    public static final int SLOT_FEET = 6;
    static final int MAX_SLOTS = 7;
    private static String[] SLOT_NAMES = null;

    static synchronized String[] getSlotNames() {
        if (EquipmentSlotConstants.SLOT_NAMES == null) {
            final String[] temp = new String[EquipmentSlotConstants.MAX_SLOTS];
            for (int x = 0; x < temp.length; x++) {
                temp[x] = StringManager.getLocalizedString(LocalizedFile.SLOTS,
                        x);
            }
            EquipmentSlotConstants.SLOT_NAMES = temp;
        }
        return EquipmentSlotConstants.SLOT_NAMES;
    }

    public static int getArmorSlotForType(final int armorType) {
        if (armorType >= EquipmentSlotConstants.SLOT_WEAPON) {
            return armorType + 1;
        } else {
            return armorType;
        }
    }
}
