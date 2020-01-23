/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.items;

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
        return WeaponConstants.WEAPON_CHOICES;
    }

    static String[] getHandChoices() {
        if (WeaponConstants.HAND_CHOICES == null) {
            final String[] temp = EquipmentSlotConstants.getSlotNames();
            final String[] temp2 = new String[2];
            temp2[0] = temp[EquipmentSlotConstants.SLOT_MAINHAND];
            temp2[1] = temp[EquipmentSlotConstants.SLOT_OFFHAND];
            WeaponConstants.HAND_CHOICES = temp2;
        }
        return WeaponConstants.HAND_CHOICES;
    }

    static String get1HWeapons() {
        return WeaponConstants.WEAPON_1H;
    }

    static String get2HWeapons() {
        return WeaponConstants.WEAPON_2H;
    }
}
