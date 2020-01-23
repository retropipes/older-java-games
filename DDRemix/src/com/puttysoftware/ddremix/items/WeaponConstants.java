/*  DDRemix: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.items;

import com.puttysoftware.ddremix.creatures.castes.CasteConstants;
import com.puttysoftware.ddremix.names.NamesConstants;
import com.puttysoftware.ddremix.names.NamesManager;

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
