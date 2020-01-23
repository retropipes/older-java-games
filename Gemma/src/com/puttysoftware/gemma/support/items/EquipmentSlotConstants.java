/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.items;

import com.puttysoftware.gemma.support.names.NamesConstants;
import com.puttysoftware.gemma.support.names.NamesManager;

public class EquipmentSlotConstants {
    static final int SLOT_SOCKS = -2;
    static final int SLOT_NONE = -1;
    public static final int SLOT_MAINHAND = 0;
    public static final int SLOT_OFFHAND = 1;
    static final int MAX_SLOTS = 2;
    private static String[] SLOT_NAMES = null;
    private static String[] ARMOR_SLOT_NAMES = null;

    static String[] getSlotNames() {
        if (SLOT_NAMES == null) {
            String[] temp = new String[MAX_SLOTS];
            for (int x = 0; x < temp.length; x++) {
                temp[x] = NamesManager.getName(
                        NamesConstants.SECTION_EQUIP_SLOT,
                        NamesConstants.SECTION_ARRAY_EQUIP_SLOTS[x]);
            }
            SLOT_NAMES = temp;
        }
        return SLOT_NAMES;
    }

    static String[] getArmorSlotNames() {
        if (ARMOR_SLOT_NAMES == null) {
            if (SLOT_NAMES == null) {
                EquipmentSlotConstants.getSlotNames();
            }
            String[] temp = SLOT_NAMES;
            String[] temp2 = new String[temp.length - 1];
            int offset = 0;
            for (int x = 0; x < temp.length; x++) {
                if (x == SLOT_MAINHAND) {
                    offset++;
                } else {
                    temp2[x - offset] = temp[x];
                }
            }
            ARMOR_SLOT_NAMES = temp2;
        }
        return ARMOR_SLOT_NAMES;
    }
}
