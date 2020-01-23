package net.worldwizard.dungeondiver.items;

import net.worldwizard.dungeondiver.creatures.PlayerClasses;

public class ItemNames implements ShopTypes {
    // Constants
    private static final String CLASS_FIGHTER_WEAPON = "Axe";
    private static final String CLASS_FIGHTER_ARMOR = "Suit";
    private static final String CLASS_MAGE_WEAPON = "Wand";
    private static final String CLASS_MAGE_ARMOR = "Cap";
    private static final String CLASS_THIEF_WEAPON = "Dagger";
    private static final String CLASS_THIEF_ARMOR = "Shield";
    private static final String MATERIAL_1 = "Copper";
    private static final String MATERIAL_2 = "Bronze";
    private static final String MATERIAL_3 = "Iron";
    private static final String MATERIAL_4 = "Steel";
    private static final String MATERIAL_5 = "Titanium";
    private static final String MATERIAL_6 = "Silver";
    private static final String MATERIAL_7 = "Gold";
    private static final String MATERIAL_8 = "Platinum";
    private static final String MATERIAL_9 = "Obsidian";
    private static final String MATERIAL_10 = "Quartz";
    private static final String MATERIAL_11 = "Sapphire";
    private static final String MATERIAL_12 = "Ruby";
    private static final String MATERIAL_13 = "Diamond";
    private static final String UNKNOWN = "Unknown";

    // Private constructor
    private ItemNames() {
        // Do nothing
    }

    // Methods
    public static String getItemName(final int type, final int level,
            final int playerClass) {
        return ItemNames.getItemMaterial(level) + " "
                + ItemNames.getItemType(type, playerClass);
    }

    public static String getItemMaterial(final int level) {
        switch (level) {
        case 1:
            return ItemNames.MATERIAL_1;
        case 2:
            return ItemNames.MATERIAL_2;
        case 3:
            return ItemNames.MATERIAL_3;
        case 4:
            return ItemNames.MATERIAL_4;
        case 5:
            return ItemNames.MATERIAL_5;
        case 6:
            return ItemNames.MATERIAL_6;
        case 7:
            return ItemNames.MATERIAL_7;
        case 8:
            return ItemNames.MATERIAL_8;
        case 9:
            return ItemNames.MATERIAL_9;
        case 10:
            return ItemNames.MATERIAL_10;
        case 11:
            return ItemNames.MATERIAL_11;
        case 12:
            return ItemNames.MATERIAL_12;
        case 13:
            return ItemNames.MATERIAL_13;
        default:
            break;
        }
        return ItemNames.UNKNOWN;
    }

    public static String getItemType(final int type, final int playerClass) {
        switch (playerClass) {
        case PlayerClasses.CLASS_FIGHTER:
            switch (type) {
            case ShopTypes.WEAPONS:
                return ItemNames.CLASS_FIGHTER_WEAPON;
            case ShopTypes.ARMOR:
                return ItemNames.CLASS_FIGHTER_ARMOR;
            default:
                break;
            }
            break;
        case PlayerClasses.CLASS_MAGE:
            switch (type) {
            case ShopTypes.WEAPONS:
                return ItemNames.CLASS_MAGE_WEAPON;
            case ShopTypes.ARMOR:
                return ItemNames.CLASS_MAGE_ARMOR;
            default:
                break;
            }
            break;
        case PlayerClasses.CLASS_THIEF:
            switch (type) {
            case ShopTypes.WEAPONS:
                return ItemNames.CLASS_THIEF_WEAPON;
            case ShopTypes.ARMOR:
                return ItemNames.CLASS_THIEF_ARMOR;
            default:
                break;
            }
            break;
        default:
            break;
        }
        return ItemNames.UNKNOWN;
    }
}
