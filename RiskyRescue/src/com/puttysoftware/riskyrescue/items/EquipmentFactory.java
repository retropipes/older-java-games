/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.items;

class EquipmentFactory {
    // Private constructor
    private EquipmentFactory() {
        // Do nothing
    }

    // Methods
    static Equipment createOneHandedWeapon(final int material,
            final int bonus) {
        final Equipment e = new Equipment(
                WeaponMaterialConstants.MATERIAL_COMMON_NAMES[material] + " "
                        + WeaponConstants.get1HWeapons(),
                0, 0,
                EquipmentCategoryConstants.EQUIPMENT_CATEGORY_ONE_HANDED_WEAPON,
                material);
        e.setFirstSlotUsed(EquipmentSlotConstants.SLOT_MAINHAND);
        e.setSecondSlotUsed(EquipmentSlotConstants.SLOT_OFFHAND);
        e.setConditionalSlot(true);
        e.setPotency(
                material * WeaponMaterialConstants.MATERIALS_POWER_MULTIPLIER
                        + bonus);
        e.setBuyPrice(Shop.getEquipmentCost(material));
        e.setSellPrice(Shop.getEquipmentCost(material) / 2);
        return e;
    }

    static Equipment createTwoHandedWeapon(final int material,
            final int bonus) {
        final Equipment e = new Equipment(
                WeaponMaterialConstants.MATERIAL_COMMON_NAMES[material] + " "
                        + WeaponConstants.get2HWeapons(),
                0, 0,
                EquipmentCategoryConstants.EQUIPMENT_CATEGORY_TWO_HANDED_WEAPON,
                material);
        e.setFirstSlotUsed(EquipmentSlotConstants.SLOT_MAINHAND);
        e.setSecondSlotUsed(EquipmentSlotConstants.SLOT_OFFHAND);
        e.setConditionalSlot(false);
        e.setPotency(
                material * WeaponMaterialConstants.MATERIALS_POWER_MULTIPLIER
                        + bonus);
        e.setBuyPrice(Shop.getEquipmentCost(material) * 2);
        e.setSellPrice(Shop.getEquipmentCost(material));
        return e;
    }

    static Equipment createArmor(final int material, final int armorType,
            final int bonus) {
        final Equipment e = new Equipment(
                ArmorMaterialConstants.MATERIAL_COMMON_NAMES[material] + " "
                        + ArmorConstants.getArmor()[armorType],
                0, 0, EquipmentCategoryConstants.EQUIPMENT_CATEGORY_ARMOR,
                material);
        e.setFirstSlotUsed(armorType);
        e.setConditionalSlot(false);
        e.setPotency(
                material * ArmorMaterialConstants.MATERIALS_POWER_MULTIPLIER
                        + bonus);
        e.setBuyPrice(Shop.getEquipmentCost(material));
        e.setSellPrice(Shop.getEquipmentCost(material) / 2);
        return e;
    }

    static String[] createOneHandedWeaponNames() {
        final String[] res = new String[WeaponMaterialConstants.MATERIALS_COUNT];
        for (int x = 0; x < res.length; x++) {
            res[x] = WeaponMaterialConstants.MATERIAL_COMMON_NAMES[x] + " "
                    + WeaponConstants.get1HWeapons();
        }
        return res;
    }

    static String[] createTwoHandedWeaponNames() {
        final String[] res = new String[WeaponMaterialConstants.MATERIALS_COUNT];
        for (int x = 0; x < res.length; x++) {
            res[x] = WeaponMaterialConstants.MATERIAL_COMMON_NAMES[x] + " "
                    + WeaponConstants.get2HWeapons();
        }
        return res;
    }

    static String[] createArmorNames(final int armorType) {
        final String[] res = new String[ArmorMaterialConstants.MATERIALS_COUNT];
        for (int x = 0; x < res.length; x++) {
            res[x] = ArmorMaterialConstants.MATERIAL_COMMON_NAMES[x] + " "
                    + ArmorConstants.getArmor()[armorType];
        }
        return res;
    }
}
