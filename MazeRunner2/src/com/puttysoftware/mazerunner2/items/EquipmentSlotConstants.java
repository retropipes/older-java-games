/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.items;

import com.puttysoftware.mazerunner2.names.NamesConstants;
import com.puttysoftware.mazerunner2.names.NamesManager;

public class EquipmentSlotConstants {
    static final int SLOT_SOCKS = -2;
    static final int SLOT_NONE = -1;
    public static final int SLOT_HEAD = 0;
    public static final int SLOT_NECK = 1;
    public static final int SLOT_MAINHAND = 2;
    public static final int SLOT_OFFHAND = 3;
    public static final int SLOT_BODY = 4;
    public static final int SLOT_BACK = 5;
    public static final int SLOT_UPPER_TORSO = 6;
    public static final int SLOT_ARMS = 7;
    public static final int SLOT_HANDS = 8;
    public static final int SLOT_FINGERS = 9;
    public static final int SLOT_LOWER_TORSO = 10;
    public static final int SLOT_LEGS = 11;
    public static final int SLOT_FEET = 12;
    static final int MAX_SLOTS = 13;
    private static String[] SLOT_NAMES = null;
    private static String[] ARMOR_SLOT_NAMES = null;

    static String[] getSlotNames() {
        if (EquipmentSlotConstants.SLOT_NAMES == null) {
            final String[] temp = new String[EquipmentSlotConstants.MAX_SLOTS];
            for (int x = 0; x < temp.length; x++) {
                temp[x] = NamesManager.getName(
                        NamesConstants.SECTION_EQUIP_SLOT,
                        NamesConstants.SECTION_ARRAY_EQUIP_SLOTS[x]);
            }
            EquipmentSlotConstants.SLOT_NAMES = temp;
        }
        return EquipmentSlotConstants.SLOT_NAMES;
    }

    static String[] getArmorSlotNames() {
        if (EquipmentSlotConstants.ARMOR_SLOT_NAMES == null) {
            if (EquipmentSlotConstants.SLOT_NAMES == null) {
                EquipmentSlotConstants.getSlotNames();
            }
            final String[] temp = EquipmentSlotConstants.SLOT_NAMES;
            final String[] temp2 = new String[temp.length - 1];
            int offset = 0;
            for (int x = 0; x < temp.length; x++) {
                if (x == EquipmentSlotConstants.SLOT_MAINHAND) {
                    offset++;
                } else {
                    temp2[x - offset] = temp[x];
                }
            }
            EquipmentSlotConstants.ARMOR_SLOT_NAMES = temp2;
        }
        return EquipmentSlotConstants.ARMOR_SLOT_NAMES;
    }
}
