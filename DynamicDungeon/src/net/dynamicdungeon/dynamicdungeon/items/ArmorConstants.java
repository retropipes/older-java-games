/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.items;

public class ArmorConstants {
    private static String[] ARMOR = null;
    private static String[] ARMOR_CHOICES = null;

    public static synchronized String[] getArmorChoices() {
        if (ArmorConstants.ARMOR_CHOICES == null) {
            final String[] temp1 = EquipmentSlotConstants.getArmorSlotNames();
            final String[] temp2 = new String[temp1.length];
            System.arraycopy(temp1, 0, temp2, 0, temp1.length);
            ArmorConstants.ARMOR_CHOICES = temp2;
        }
        return ArmorConstants.ARMOR_CHOICES;
    }

    public static synchronized String[] getArmor() {
        if (ArmorConstants.ARMOR == null) {
            final String[] temp1 = ArmorConstants.getArmorChoices();
            final String[] temp2 = new String[temp1.length + 1];
            temp2[EquipmentSlotConstants.SLOT_MAINHAND] = "";
            temp2[EquipmentSlotConstants.SLOT_BODY] = temp1[0];
            ArmorConstants.ARMOR = temp2;
        }
        return ArmorConstants.ARMOR;
    }
}
