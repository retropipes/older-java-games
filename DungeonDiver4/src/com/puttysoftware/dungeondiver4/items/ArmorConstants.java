/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.items;

import java.util.Arrays;

import com.puttysoftware.dungeondiver4.names.NamesConstants;
import com.puttysoftware.dungeondiver4.names.NamesManager;

class ArmorConstants {
    private static String[] ARMOR = null;
    private static String[] ARMOR_CHOICES = null;

    static String[] getArmorChoices() {
        if (ArmorConstants.ARMOR_CHOICES == null) {
            final String[] temp1 = EquipmentSlotConstants.getArmorSlotNames();
            final String[] temp2 = new String[temp1.length];
            System.arraycopy(temp1, 0, temp2, 0, temp1.length);
            temp2[EquipmentSlotConstants.SLOT_OFFHAND - 1] = NamesManager
                    .getName(NamesConstants.SECTION_EQUIP_ARMOR,
                            NamesConstants.ARMOR_SHIELD);
            ArmorConstants.ARMOR_CHOICES = temp2;
        }
        return ArmorConstants.ARMOR_CHOICES;
    }

    static String[] getArmor() {
        if (ArmorConstants.ARMOR == null) {
            final String[] temp1 = ArmorConstants.getArmorChoices();
            final String[] temp2 = new String[temp1.length + 1];
            Arrays.fill(temp2, "");
            for (int x = 0; x < temp2.length; x++) {
                if (x > EquipmentSlotConstants.SLOT_MAINHAND) {
                    temp2[x] = temp1[x - 1];
                } else if (x < EquipmentSlotConstants.SLOT_MAINHAND) {
                    temp2[x] = temp1[x];
                }
            }
            ArmorConstants.ARMOR = temp2;
        }
        return ArmorConstants.ARMOR;
    }
}
