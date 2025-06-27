/*  DDRemix: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.items;

import com.puttysoftware.ddremix.shops.Shop;

public class EquipmentFactory {
        // Private constructor
        private EquipmentFactory() {
                // Do nothing
        }

        // Methods
        public static Equipment createWeapon(final int material,
                        final int weaponType, final int bonus) {
                final Equipment e = new Equipment(
                                WeaponMaterialConstants.MATERIAL_COMMON_NAMES[material] + " "
                                                + WeaponConstants.getWeapons()[weaponType],
                                0, 0, EquipmentCategoryConstants.EQUIPMENT_CATEGORY_WEAPON,
                                material);
                e.setFirstSlotUsed(EquipmentSlotConstants.SLOT_MAINHAND);
                e.setConditionalSlot(false);
                e.setPotency(
                                material * WeaponMaterialConstants.MATERIALS_POWER_MULTIPLIER
                                                + bonus);
                e.setBuyPrice(Shop.getEquipmentCost(material) * 2);
                e.setSellPrice(Shop.getEquipmentCost(material));
                return e;
        }

        public static Equipment createArmor(final int material, final int armorType,
                        final int bonus) {
                final Equipment e = new Equipment(
                                ArmorMaterialConstants.MATERIAL_COMMON_NAMES[material] + " "
                                                + ArmorConstants.getArmor()[armorType + 1],
                                0, 0, EquipmentCategoryConstants.EQUIPMENT_CATEGORY_ARMOR,
                                material);
                e.setFirstSlotUsed(armorType + 1);
                e.setConditionalSlot(false);
                e.setPotency(
                                material * ArmorMaterialConstants.MATERIALS_POWER_MULTIPLIER
                                                + bonus);
                e.setBuyPrice(Shop.getEquipmentCost(material));
                e.setSellPrice(Shop.getEquipmentCost(material) / 2);
                return e;
        }

        public static String[] createWeaponNames(final int weaponType) {
                final String[] res = new String[WeaponMaterialConstants.MATERIALS_COUNT];
                for (int x = 0; x < res.length; x++) {
                        res[x] = WeaponMaterialConstants.MATERIAL_COMMON_NAMES[x] + " "
                                        + WeaponConstants.getWeapons()[weaponType];
                }
                return res;
        }

        public static String[] createArmorNames(final int armorType) {
                final String[] res = new String[ArmorMaterialConstants.MATERIALS_COUNT];
                for (int x = 0; x < res.length; x++) {
                        res[x] = ArmorMaterialConstants.MATERIAL_COMMON_NAMES[x] + " "
                                        + ArmorConstants.getArmor()[armorType + 1];
                }
                return res;
        }
}
