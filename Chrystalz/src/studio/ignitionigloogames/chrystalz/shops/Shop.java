/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.shops;

import javax.swing.JOptionPane;

import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyMember;
import studio.ignitionigloogames.chrystalz.dialogs.ListWithImageDialog;
import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.items.ArmorConstants;
import studio.ignitionigloogames.chrystalz.items.Equipment;
import studio.ignitionigloogames.chrystalz.items.EquipmentFactory;
import studio.ignitionigloogames.chrystalz.items.WeaponConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.ArmorImageManager;
import studio.ignitionigloogames.chrystalz.manager.asset.MusicConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.MusicManager;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundManager;
import studio.ignitionigloogames.chrystalz.manager.asset.WeaponImageManager;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;
import studio.ignitionigloogames.common.images.BufferedImageIcon;

public class Shop {
    // Fields
    private static final String[] SHOP_NAMES = { "Weapons", "Armor", "Healer",
            "Regenerator", "Spells" };
    final ShopType type;
    int index;
    int defaultChoice;
    String[] choices;
    String result;
    int cost;
    String[] typeChoices;
    int typeDefault;
    String typeResult;
    int typeIndex;
    BufferedImageIcon[] imageChoices;
    private final ShopDialogGUI defaultUI;
    private final ShopImageDialogGUI imageUI;
    static final int MAX_ENHANCEMENTS = 9;

    // Constructors
    public Shop(final ShopType shopType) {
        super();
        this.defaultUI = new ShopDialogGUI();
        this.imageUI = new ShopImageDialogGUI();
        this.type = shopType;
        this.index = 0;
    }

    // Methods
    public static int getEquipmentCost(final int x) {
        return 10 * x * x * x + 10 * x * x + 10 * x + 10;
    }

    static int getHealingCost(final int x, final int y, final int z) {
        return (int) (Math.log10(x) * (z - y));
    }

    static int getRegenerationCost(final int x, final int y, final int z) {
        final int diff = z - y;
        if (diff == 0) {
            return 0;
        } else {
            final int cost = (int) (Math.log(x) / Math.log(2) * diff);
            if (cost < 1) {
                return 1;
            } else {
                return cost;
            }
        }
    }

    static int getSpellCost(final int i) {
        if (i == -1) {
            return 0;
        } else {
            return 20 * i * i + 20;
        }
    }

    String getShopNameFromType() {
        return Shop.SHOP_NAMES[this.type.ordinal()];
    }

    public void showShop() {
        if (MusicManager.isMusicPlaying()) {
            MusicManager.stopMusic();
        }
        if (this.type == ShopType.ARMOR || this.type == ShopType.WEAPONS) {
            MusicManager.playMusic(MusicConstants.MUSIC_FORGE);
            this.imageUI.showShop();
        } else {
            MusicManager.playMusic(MusicConstants.MUSIC_SHOP);
            this.defaultUI.showShop();
        }
        MusicManager.stopMusic();
        int zoneID = PartyManager.getParty().getZone();
        if (zoneID == Dungeon.getMaxLevels() - 1) {
            MusicManager.playMusic(MusicConstants.MUSIC_LAIR);
        } else {
            MusicManager.playMusic(MusicConstants.MUSIC_DUNGEON);
        }
    }

    private class ShopDialogGUI {
        public ShopDialogGUI() {
            // Do nothing
        }

        public void showShop() {
            Shop.this.index = 0;
            Shop.this.defaultChoice = 0;
            Shop.this.choices = null;
            Shop.this.result = null;
            Shop.this.cost = 0;
            boolean valid = this.shopStage1();
            if (valid) {
                valid = this.shopStage2();
            }
            if (valid) {
                valid = this.shopStage3();
            }
            if (valid) {
                valid = this.shopStage4();
            }
            if (valid) {
                this.shopStage5();
            }
        }

        private boolean shopStage1() {
            // Stage 1
            // Play enter shop sound
            SoundManager.playSound(SoundConstants.SHOP);
            final PartyMember playerCharacter = PartyManager.getParty()
                    .getLeader();
            if (Shop.this.type == ShopType.HEALER
                    || Shop.this.type == ShopType.REGENERATOR) {
                Shop.this.choices = new String[10];
                int x;
                for (x = 0; x < Shop.this.choices.length; x++) {
                    Shop.this.choices[x] = Integer.toString((x + 1) * 10) + "%";
                }
                Shop.this.defaultChoice = 9;
            } else if (Shop.this.type == ShopType.SPELLS) {
                Shop.this.choices = playerCharacter.getSpellBook()
                        .getAllSpellsToLearnNames();
                if (Shop.this.choices == null) {
                    Shop.this.choices = new String[1];
                    Shop.this.choices[0] = "No Spells Left To Learn";
                }
            } else {
                // Invalid shop type
                return false;
            }
            return true;
        }

        private boolean shopStage2() {
            // Stage 2
            final PartyMember playerCharacter = PartyManager.getParty()
                    .getLeader();
            // Check
            if (Shop.this.type == ShopType.HEALER && playerCharacter
                    .getCurrentHP() == playerCharacter.getMaximumHP()) {
                CommonDialogs.showDialog("You don't need healing.");
                return false;
            } else if (Shop.this.type == ShopType.REGENERATOR && playerCharacter
                    .getCurrentMP() == playerCharacter.getMaximumMP()) {
                CommonDialogs.showDialog("You don't need regeneration.");
                return false;
            } else if (Shop.this.type == ShopType.SPELLS && playerCharacter
                    .getSpellBook().getSpellsKnownCount() == playerCharacter
                            .getSpellBook().getMaximumSpellsKnownCount()) {
                CommonDialogs.showDialog("There are no more spells to learn.");
                return false;
            }
            Shop.this.result = CommonDialogs.showInputDialog("Select",
                    Shop.this.getShopNameFromType(), Shop.this.choices,
                    Shop.this.choices[Shop.this.defaultChoice]);
            if (Shop.this.result == null) {
                return false;
            }
            if (Shop.this.index == -1) {
                return false;
            }
            Shop.this.index = 0;
            if (Shop.this.type != ShopType.SPELLS) {
                for (Shop.this.index = 0; Shop.this.index < Shop.this.choices.length; Shop.this.index++) {
                    if (Shop.this.result
                            .equals(Shop.this.choices[Shop.this.index])) {
                        break;
                    }
                }
            } else {
                Shop.this.index = playerCharacter.getSpellBook()
                        .getSpellIDByName(Shop.this.result);
            }
            return true;
        }

        private boolean shopStage3() {
            // Stage 3
            final PartyMember playerCharacter = PartyManager.getParty()
                    .getLeader();
            Shop.this.cost = 0;
            if (Shop.this.type == ShopType.HEALER) {
                Shop.this.cost = Shop.getHealingCost(playerCharacter.getLevel(),
                        playerCharacter.getCurrentHP(),
                        playerCharacter.getMaximumHP());
            } else if (Shop.this.type == ShopType.REGENERATOR) {
                Shop.this.cost = Shop.getRegenerationCost(
                        playerCharacter.getLevel(),
                        playerCharacter.getCurrentMP(),
                        playerCharacter.getMaximumMP());
            } else if (Shop.this.type == ShopType.SPELLS) {
                Shop.this.cost = Shop.getSpellCost(Shop.this.index);
            }
            // Confirm
            final int stage4Confirm = CommonDialogs.showConfirmDialog(
                    "This will cost " + Shop.this.cost + " Gold. Are you sure?",
                    Shop.this.getShopNameFromType());
            if (stage4Confirm == JOptionPane.NO_OPTION
                    || stage4Confirm == JOptionPane.CLOSED_OPTION) {
                return false;
            }
            return true;
        }

        private boolean shopStage4() {
            // Stage 4
            final PartyMember playerCharacter = PartyManager.getParty()
                    .getLeader();
            if (playerCharacter.getGold() < Shop.this.cost) {
                CommonDialogs.showErrorDialog("Not Enough Gold!",
                        Shop.this.getShopNameFromType());
                return false;
            }
            return true;
        }

        private void shopStage5() {
            // Stage 5
            final PartyMember playerCharacter = PartyManager.getParty()
                    .getLeader();
            // Play transact sound
            SoundManager.playSound(SoundConstants.TRANSACT);
            if (Shop.this.type == ShopType.HEALER) {
                playerCharacter.offsetGold(-Shop.this.cost);
                playerCharacter.healPercentage((Shop.this.index + 1) * 10);
            } else if (Shop.this.type == ShopType.REGENERATOR) {
                playerCharacter.offsetGold(-Shop.this.cost);
                playerCharacter
                        .regeneratePercentage((Shop.this.index + 1) * 10);
            } else if (Shop.this.type == ShopType.SPELLS) {
                playerCharacter.offsetGold(-Shop.this.cost);
                if (Shop.this.index != -1) {
                    playerCharacter.getSpellBook()
                            .learnSpellByID(Shop.this.index);
                }
            }
        }
    }

    private class ShopImageDialogGUI {
        public ShopImageDialogGUI() {
            // Do nothing
        }

        public void showShop() {
            Shop.this.index = 0;
            Shop.this.defaultChoice = 0;
            Shop.this.choices = null;
            Shop.this.result = null;
            Shop.this.cost = 0;
            boolean valid = this.shopStage1();
            if (valid) {
                valid = this.shopStage2();
            }
            if (valid) {
                valid = this.shopStage3();
            }
            if (valid) {
                valid = this.shopStage4();
            }
            if (valid) {
                this.shopStage5();
            }
        }

        private boolean shopStage1() {
            // Stage 1
            // Play enter shop sound
            SoundManager.playSound(SoundConstants.SHOP);
            final int zoneID = PartyManager.getParty().getZone();
            if (Shop.this.type == ShopType.WEAPONS) {
                Shop.this.imageChoices = new BufferedImageIcon[WeaponConstants.TYPE_COUNT];
                for (int i = 0; i < WeaponConstants.TYPE_COUNT; i++) {
                    Shop.this.imageChoices[i] = WeaponImageManager.getImage(i,
                            zoneID);
                }
                Shop.this.typeChoices = WeaponConstants.getWeaponChoices();
                Shop.this.typeDefault = 0;
            } else if (Shop.this.type == ShopType.ARMOR) {
                Shop.this.imageChoices = new BufferedImageIcon[ArmorConstants.TYPE_COUNT];
                for (int i = 0; i < ArmorConstants.TYPE_COUNT; i++) {
                    Shop.this.imageChoices[i] = ArmorImageManager.getImage(i,
                            zoneID);
                }
                Shop.this.typeChoices = ArmorConstants.getArmorChoices();
                Shop.this.typeDefault = 0;
            } else {
                // Invalid shop type
                return false;
            }
            if (Shop.this.typeChoices != null) {
                Shop.this.typeResult = ListWithImageDialog.showDialog(
                        "Select Type", Shop.this.getShopNameFromType(),
                        Shop.this.typeChoices[Shop.this.typeDefault],
                        Shop.this.typeChoices,
                        Shop.this.imageChoices[Shop.this.typeDefault],
                        Shop.this.imageChoices);
                if (Shop.this.typeResult == null) {
                    return false;
                }
                for (Shop.this.typeIndex = 0; Shop.this.typeIndex < Shop.this.typeChoices.length; Shop.this.typeIndex++) {
                    if (Shop.this.typeResult.equals(
                            Shop.this.typeChoices[Shop.this.typeIndex])) {
                        break;
                    }
                }
                if (Shop.this.typeIndex == Shop.this.typeChoices.length) {
                    return false;
                }
            }
            return true;
        }

        private boolean shopStage2() {
            // Stage 2
            Shop.this.index = PartyManager.getParty().getZone();
            if (Shop.this.type == ShopType.WEAPONS) {
                Shop.this.result = EquipmentFactory
                        .getWeaponName(Shop.this.index, Shop.this.typeIndex);
            } else if (Shop.this.type == ShopType.ARMOR) {
                Shop.this.result = EquipmentFactory
                        .getArmorName(Shop.this.index, Shop.this.typeIndex);
            }
            return true;
        }

        private boolean shopStage3() {
            // Stage 3
            Shop.this.cost = 0;
            Shop.this.cost = Shop.getEquipmentCost(Shop.this.index);
            // Confirm
            final int stage4Confirm = CommonDialogs.showConfirmDialog(
                    "This will cost " + Shop.this.cost + " Gold. Are you sure?",
                    Shop.this.getShopNameFromType());
            if (stage4Confirm == JOptionPane.NO_OPTION
                    || stage4Confirm == JOptionPane.CLOSED_OPTION) {
                return false;
            }
            return true;
        }

        private boolean shopStage4() {
            // Stage 4
            final PartyMember playerCharacter = PartyManager.getParty()
                    .getLeader();
            if (playerCharacter.getGold() < Shop.this.cost) {
                CommonDialogs.showErrorDialog("Not Enough Gold!",
                        Shop.this.getShopNameFromType());
                return false;
            }
            return true;
        }

        private void shopStage5() {
            // Stage 5
            final PartyMember playerCharacter = PartyManager.getParty()
                    .getLeader();
            // Play transact sound
            SoundManager.playSound(SoundConstants.TRANSACT);
            if (Shop.this.type == ShopType.WEAPONS) {
                playerCharacter.offsetGold(-Shop.this.cost);
                final Equipment bought = EquipmentFactory
                        .createWeapon(Shop.this.index, Shop.this.typeIndex);
                playerCharacter.getItems().equip(playerCharacter, bought, true);
            } else if (Shop.this.type == ShopType.ARMOR) {
                playerCharacter.offsetGold(-Shop.this.cost);
                final Equipment bought = EquipmentFactory
                        .createArmor(Shop.this.index, Shop.this.typeIndex);
                playerCharacter.getItems().equip(playerCharacter, bought, true);
            }
        }
    }
}
