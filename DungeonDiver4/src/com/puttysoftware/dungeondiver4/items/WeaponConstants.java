/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.items;

import com.puttysoftware.dungeondiver4.creatures.castes.CasteConstants;
import com.puttysoftware.dungeondiver4.names.NamesConstants;
import com.puttysoftware.dungeondiver4.names.NamesManager;

class WeaponConstants {
    // Constants
    private static String[] WEAPON_1H = null;
    private static String[] WEAPON_2H = null;
    private static final String[] WEAPON_CHOICES = { "One-Handed Weapons",
            "Two-Handed Weapons" };
    private static String[] HAND_CHOICES = null;

    // Private Constructor
    private WeaponConstants() {
        // Do nothing
    }

    // Methods
    static String[] getWeaponChoices() {
        return WEAPON_CHOICES;
    }

    static String[] getHandChoices() {
        if (HAND_CHOICES == null) {
            String[] temp = EquipmentSlotConstants.getSlotNames();
            String[] temp2 = new String[2];
            temp2[0] = temp[EquipmentSlotConstants.SLOT_MAINHAND];
            temp2[1] = temp[EquipmentSlotConstants.SLOT_OFFHAND];
            HAND_CHOICES = temp2;
        }
        return HAND_CHOICES;
    }

    static String[] get1HWeapons() {
        if (WEAPON_1H == null) {
            String[] temp = new String[CasteConstants.CASTES_COUNT];
            for (int x = 0; x < temp.length; x++) {
                temp[x] = NamesManager.getName(
                        NamesConstants.SECTION_EQUIP_WEAPONS_1H,
                        NamesConstants.SECTION_ARRAY_WEAPONS_1H[x]);
            }
            WEAPON_1H = temp;
        }
        return WEAPON_1H;
    }

    static String[] get2HWeapons() {
        if (WEAPON_2H == null) {
            String[] temp = new String[CasteConstants.CASTES_COUNT];
            for (int x = 0; x < temp.length; x++) {
                temp[x] = NamesManager.getName(
                        NamesConstants.SECTION_EQUIP_WEAPONS_2H,
                        NamesConstants.SECTION_ARRAY_WEAPONS_2H[x]);
            }
            WEAPON_2H = temp;
        }
        return WEAPON_2H;
    }
}
