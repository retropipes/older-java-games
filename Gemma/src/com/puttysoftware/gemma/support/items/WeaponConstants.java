/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.items;

class WeaponConstants {
    // Constants
    private static String WEAPON_1H = "Dagger";
    private static String WEAPON_2H = "Hammer";
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

    static String get1HWeapons() {
        return WEAPON_1H;
    }

    static String get2HWeapons() {
        return WEAPON_2H;
    }
}
