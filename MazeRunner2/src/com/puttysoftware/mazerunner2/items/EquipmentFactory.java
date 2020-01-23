/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.items;

import com.puttysoftware.mazerunner2.creatures.faiths.FaithConstants;

class EquipmentFactory {
    // Private constructor
    private EquipmentFactory() {
        // Do nothing
    }

    // Methods
    static Equipment createOneHandedWeapon(int material, int weaponType,
            int bonus) {
        Equipment e = new Equipment(
                WeaponMaterialConstants.MATERIAL_COMMON_NAMES[material] + " "
                        + WeaponConstants.get1HWeapons()[weaponType],
                0,
                0,
                EquipmentCategoryConstants.EQUIPMENT_CATEGORY_ONE_HANDED_WEAPON,
                material);
        e.setFirstSlotUsed(EquipmentSlotConstants.SLOT_MAINHAND);
        e.setSecondSlotUsed(EquipmentSlotConstants.SLOT_OFFHAND);
        e.setConditionalSlot(true);
        e.setPotency(material
                * WeaponMaterialConstants.MATERIALS_POWER_MULTIPLIER + bonus);
        e.setBuyPrice(Shop.getEquipmentCost(material));
        e.setSellPrice(Shop.getEquipmentCost(material) / 2);
        return e;
    }

    static Equipment createTwoHandedWeapon(int material, int weaponType,
            int bonus) {
        Equipment e = new Equipment(
                WeaponMaterialConstants.MATERIAL_COMMON_NAMES[material] + " "
                        + WeaponConstants.get2HWeapons()[weaponType],
                0,
                0,
                EquipmentCategoryConstants.EQUIPMENT_CATEGORY_TWO_HANDED_WEAPON,
                material);
        e.setFirstSlotUsed(EquipmentSlotConstants.SLOT_MAINHAND);
        e.setSecondSlotUsed(EquipmentSlotConstants.SLOT_OFFHAND);
        e.setConditionalSlot(false);
        e.setPotency(material
                * WeaponMaterialConstants.MATERIALS_POWER_MULTIPLIER + bonus);
        e.setBuyPrice(Shop.getEquipmentCost(material) * 2);
        e.setSellPrice(Shop.getEquipmentCost(material));
        return e;
    }

    static Equipment createArmor(int material, int armorType, int bonus) {
        Equipment e = new Equipment(
                ArmorMaterialConstants.MATERIAL_COMMON_NAMES[material] + " "
                        + ArmorConstants.getArmor()[armorType], 0, 0,
                EquipmentCategoryConstants.EQUIPMENT_CATEGORY_ARMOR, material);
        e.setFirstSlotUsed(armorType);
        e.setConditionalSlot(false);
        e.setPotency(material
                * ArmorMaterialConstants.MATERIALS_POWER_MULTIPLIER + bonus);
        e.setBuyPrice(Shop.getEquipmentCost(material));
        e.setSellPrice(Shop.getEquipmentCost(material) / 2);
        return e;
    }

    static Equipment createEnhancedEquipment(Equipment oldE, int bonus) {
        Equipment e = new Equipment(oldE);
        e.setPotency(oldE.getMaterial()
                * ArmorMaterialConstants.MATERIALS_POWER_MULTIPLIER + bonus);
        e.enchantName(bonus);
        return e;
    }

    static Equipment createFaithPoweredEquipment(Equipment oldE, int faithID,
            int bonus) {
        Equipment e = new Equipment(oldE);
        String fpName = FaithConstants.getFaithPowerName(faithID, bonus);
        e.applyFaithPower(faithID, fpName);
        return e;
    }

    static Socks createSocks(int actionType, int price) {
        String[] names = EquipmentFactory.createSocksNames();
        return new Socks(names[actionType - 1], price, actionType, 1);
    }

    static String[] createOneHandedWeaponNames(int weaponType) {
        String[] res = new String[WeaponMaterialConstants.MATERIALS_COUNT];
        for (int x = 0; x < res.length; x++) {
            res[x] = WeaponMaterialConstants.MATERIAL_COMMON_NAMES[x] + " "
                    + WeaponConstants.get1HWeapons()[weaponType];
        }
        return res;
    }

    static String[] createTwoHandedWeaponNames(int weaponType) {
        String[] res = new String[WeaponMaterialConstants.MATERIALS_COUNT];
        for (int x = 0; x < res.length; x++) {
            res[x] = WeaponMaterialConstants.MATERIAL_COMMON_NAMES[x] + " "
                    + WeaponConstants.get2HWeapons()[weaponType];
        }
        return res;
    }

    static String[] createArmorNames(int armorType) {
        String[] res = new String[ArmorMaterialConstants.MATERIALS_COUNT];
        for (int x = 0; x < res.length; x++) {
            res[x] = ArmorMaterialConstants.MATERIAL_COMMON_NAMES[x] + " "
                    + ArmorConstants.getArmor()[armorType];
        }
        return res;
    }

    static String[] createSocksNames() {
        return new String[] { "Heal Socks", "Regen Socks", "Experience Socks",
                "Money Socks" };
    }
}
