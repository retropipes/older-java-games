/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.items;

import net.dynamicdungeon.dynamicdungeon.creatures.castes.CasteConstants;
import net.dynamicdungeon.dynamicdungeon.names.NamesConstants;
import net.dynamicdungeon.dynamicdungeon.names.NamesManager;

public class WeaponConstants {
    // Constants
    private static String[] WEAPON_NAMES = null;

    // Private Constructor
    private WeaponConstants() {
        // Do nothing
    }

    // Methods
    public static synchronized String[] getWeapons() {
        if (WeaponConstants.WEAPON_NAMES == null) {
            final String[] temp = new String[CasteConstants.CASTES_COUNT];
            for (int x = 0; x < temp.length; x++) {
                temp[x] = NamesManager.getName(
                        NamesConstants.SECTION_EQUIP_WEAPONS,
                        NamesConstants.SECTION_ARRAY_WEAPONS[x]);
            }
            WeaponConstants.WEAPON_NAMES = temp;
        }
        return WeaponConstants.WEAPON_NAMES;
    }
}
