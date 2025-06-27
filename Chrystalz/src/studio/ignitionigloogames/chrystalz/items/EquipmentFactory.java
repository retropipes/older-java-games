/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.items;

import studio.ignitionigloogames.chrystalz.shops.Shop;

public class EquipmentFactory {
        // Private constructor
        private EquipmentFactory() {
                // Do nothing
        }

        // Methods
        public static Equipment createWeapon(final int material,
                        final int weaponType) {
                return new Equipment(
                                WeaponMaterialConstants.getWeaponMaterial(material) + " "
                                                + WeaponConstants.getWeapon(weaponType),
                                Shop.getEquipmentCost(material), material + 1, material + 1,
                                EquipmentSlotConstants.SLOT_WEAPON, material,
                                WeaponConstants.getWeaponTypeHitSound(weaponType));
        }

        public static Equipment createArmor(final int material,
                        final int armorType) {
                return new Equipment(
                                ArmorMaterialConstants.getArmorMaterial(material) + " "
                                                + ArmorConstants.getArmor(armorType),
                                Shop.getEquipmentCost(material), material + 1, material + 1,
                                EquipmentSlotConstants.getArmorSlotForType(armorType), material,
                                -1);
        }

        public static String getWeaponName(final int zoneID, final int weaponType) {
                return WeaponMaterialConstants.getWeaponMaterial(zoneID) + " "
                                + WeaponConstants.getWeapon(weaponType);
        }

        public static String getArmorName(final int zoneID, final int armorType) {
                return ArmorMaterialConstants.getArmorMaterial(zoneID) + " "
                                + ArmorConstants.getArmor(armorType);
        }
}
