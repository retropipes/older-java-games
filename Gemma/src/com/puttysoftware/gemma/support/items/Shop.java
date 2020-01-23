/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.items;

import javax.swing.JOptionPane;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.gemma.support.Support;
import com.puttysoftware.gemma.support.creatures.PartyManager;
import com.puttysoftware.gemma.support.creatures.PartyMember;
import com.puttysoftware.gemma.support.creatures.faiths.FaithConstants;
import com.puttysoftware.gemma.support.items.combat.CombatItemList;
import com.puttysoftware.gemma.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.gemma.support.resourcemanagers.SoundManager;

public class Shop implements ShopTypes {
    // Fields
    private final int type;
    private int index;
    private int defaultChoice;
    private String[] choices;
    private String[] typeChoices;
    private int typeDefault;
    private String typeResult;
    private int typeIndex;
    private boolean handIndex;
    private String result;
    private int cost;
    private final int inflationRate;
    private Item item;
    private boolean twoHanded;
    private final CombatItemList itemList;
    private static final int MAX_ENHANCEMENTS = 9;

    // Constructors
    public Shop(final int shopType) {
        super();
        this.type = shopType;
        this.itemList = new CombatItemList();
        this.index = 0;
        this.inflationRate = 100;
        this.twoHanded = false;
    }

    // Methods
    static int getEquipmentCost(final int x) {
        return (9 * (x + 1) * (x + 2)) / 2;
    }

    private static int getHealingCost(final int x, final int y, final int z) {
        return (int) (Math.log10(x) * ((z - y)));
    }

    private static int getRegenerationCost(final int x, final int y,
            final int z) {
        int diff = z - y;
        if (diff == 0) {
            return 0;
        } else {
            int cost = (int) ((Math.log(x) / Math.log(2)) * diff);
            if (cost < 1) {
                return 1;
            } else {
                return cost;
            }
        }
    }

    private static int getEnhancementCost(final int i, final int x) {
        if (i > Shop.MAX_ENHANCEMENTS) {
            return 0;
        }
        int cost = 15 * i * i + 15 * i
                + (int) (Math.sqrt(Shop.getEquipmentCost(x)));
        if (cost < 0) {
            return 0;
        } else {
            return cost;
        }
    }

    private String getShopNameFromType() {
        if (Support.inDebugMode()) {
            return ShopTypes.SHOP_NAMES[this.type - 1] + " (DEBUG)";
        } else {
            return ShopTypes.SHOP_NAMES[this.type - 1];
        }
    }

    public void showShop() {
        this.index = 0;
        this.defaultChoice = 0;
        this.choices = null;
        this.typeChoices = null;
        this.typeDefault = 0;
        this.typeResult = null;
        this.typeIndex = 0;
        this.handIndex = false;
        this.result = null;
        this.cost = 0;
        this.twoHanded = false;
        boolean valid = this.shopStage1();
        if (valid) {
            valid = this.shopStage2();
            if (valid) {
                valid = this.shopStage3();
                if (valid) {
                    valid = this.shopStage4();
                    if (valid) {
                        valid = this.shopStage5();
                        if (valid) {
                            this.shopStage6();
                        }
                    }
                }
            }
        }
    }

    private boolean shopStage1() {
        // Stage 1
        // Play enter shop sound
        SoundManager.playSound(GameSoundConstants.SOUND_SHOP);
        if (this.type == ShopTypes.SHOP_TYPE_WEAPONS) {
            this.typeChoices = WeaponConstants.getWeaponChoices();
            this.typeDefault = 0;
        } else if (this.type == ShopTypes.SHOP_TYPE_ARMOR) {
            this.typeIndex = EquipmentSlotConstants.SLOT_OFFHAND;
        } else if (this.type == ShopTypes.SHOP_TYPE_FAITH_POWERS) {
            this.typeChoices = FaithConstants.getFaithDisplayNames();
            this.typeDefault = 0;
        }
        if (this.typeChoices != null) {
            this.typeResult = CommonDialogs.showInputDialog("Select Type",
                    this.getShopNameFromType(), this.typeChoices,
                    this.typeChoices[this.typeDefault]);
            if (this.typeResult == null) {
                return false;
            }
            this.typeIndex = 0;
            for (this.typeIndex = 0; this.typeIndex < this.typeChoices.length; this.typeIndex++) {
                if (this.typeResult.equals(this.typeChoices[this.typeIndex])) {
                    break;
                }
            }
            if (this.typeIndex == this.typeChoices.length) {
                return false;
            }
            if (this.type == ShopTypes.SHOP_TYPE_ARMOR && this.typeIndex > 1) {
                // Adjust typeIndex, position 2 is blank
                this.typeIndex++;
            }
        }
        return true;
    }

    private boolean shopStage2() {
        // Stage 2
        if (this.type == ShopTypes.SHOP_TYPE_WEAPONS) {
            if (this.typeResult.equals(this.typeChoices[0])) {
                this.choices = EquipmentFactory.createOneHandedWeaponNames();
                // Choose Hand
                String[] handChoices = WeaponConstants.getHandChoices();
                int handDefault = 0;
                String handResult = CommonDialogs.showInputDialog("Select Hand",
                        this.getShopNameFromType(), handChoices,
                        handChoices[handDefault]);
                if (handResult == null) {
                    return false;
                }
                if (handResult.equals(handChoices[0])) {
                    this.handIndex = true;
                } else {
                    this.handIndex = false;
                }
            } else {
                this.choices = EquipmentFactory.createTwoHandedWeaponNames();
            }
        } else if (this.type == ShopTypes.SHOP_TYPE_ARMOR) {
            this.choices = EquipmentFactory.createArmorNames(this.typeIndex);
        } else if (this.type == ShopTypes.SHOP_TYPE_HEALER
                || this.type == ShopTypes.SHOP_TYPE_REGENERATOR) {
            this.choices = new String[10];
            int x;
            for (x = 0; x < this.choices.length; x++) {
                this.choices[x] = Integer.toString((x + 1) * 10) + "%";
            }
            this.defaultChoice = 9;
        } else if (this.type == ShopTypes.SHOP_TYPE_ITEMS) {
            this.choices = this.itemList.getAllNames();
            if (this.choices == null) {
                this.choices = new String[1];
                this.choices[0] = "No Items To Buy";
                this.index = -1;
            } else if (this.choices.length == 0) {
                this.choices = new String[1];
                this.choices[0] = "No Items To Buy";
                this.index = -1;
            }
        } else if (this.type == ShopTypes.SHOP_TYPE_ENHANCEMENTS) {
            this.choices = PartyManager.getParty().getLeader().getItems()
                    .generateEquipmentEnhancementStringArray();
            this.index = 0;
        } else if (this.type == ShopTypes.SHOP_TYPE_FAITH_POWERS) {
            this.choices = PartyManager.getParty().getLeader().getItems()
                    .generateEquipmentEnhancementStringArray();
            this.index = 0;
        } else {
            // Invalid shop type
            return false;
        }
        return true;
    }

    private boolean shopStage3() {
        // Stage 3
        PartyMember playerCharacter = PartyManager.getParty().getLeader();
        // Check
        if (this.type == ShopTypes.SHOP_TYPE_HEALER && playerCharacter
                .getCurrentHP() == playerCharacter.getMaximumHP()) {
            CommonDialogs.showDialog("You don't need healing.");
            return false;
        } else if (this.type == ShopTypes.SHOP_TYPE_REGENERATOR
                && playerCharacter.getCurrentMP() == playerCharacter
                        .getMaximumMP()) {
            CommonDialogs.showDialog("You don't need regeneration.");
            return false;
        }
        this.result = CommonDialogs.showInputDialog("Select",
                this.getShopNameFromType(), this.choices,
                this.choices[this.defaultChoice]);
        if (this.result == null) {
            return false;
        }
        if (this.index == -1) {
            return false;
        }
        this.index = 0;
        for (this.index = 0; this.index < this.choices.length; this.index++) {
            if (this.result.equals(this.choices[this.index])) {
                break;
            }
        }
        return true;
    }

    private boolean shopStage4() {
        // Stage 4
        PartyMember playerCharacter = PartyManager.getParty().getLeader();
        this.cost = 0;
        if ((this.type == ShopTypes.SHOP_TYPE_WEAPONS)
                || (this.type == ShopTypes.SHOP_TYPE_ARMOR)) {
            this.cost = Shop.getEquipmentCost(this.index);
            if (this.type == ShopTypes.SHOP_TYPE_WEAPONS) {
                if (this.typeResult != null) {
                    if (this.typeIndex == 1) {
                        // Two-Handed Weapon, price doubled
                        this.cost *= 2;
                    }
                }
            }
        } else if (this.type == ShopTypes.SHOP_TYPE_HEALER) {
            this.cost = Shop.getHealingCost(playerCharacter.getLevel(),
                    playerCharacter.getCurrentHP(),
                    playerCharacter.getMaximumHP());
        } else if (this.type == ShopTypes.SHOP_TYPE_REGENERATOR) {
            this.cost = Shop.getRegenerationCost(playerCharacter.getLevel(),
                    playerCharacter.getCurrentMP(),
                    playerCharacter.getMaximumMP());
        } else if (this.type == ShopTypes.SHOP_TYPE_ITEMS) {
            this.item = this.itemList.getAllItems()[this.index];
            this.cost = this.item.getBuyPrice();
        } else if (this.type == ShopTypes.SHOP_TYPE_ENHANCEMENTS) {
            Equipment old = playerCharacter.getItems()
                    .getEquipmentInSlot(this.index);
            if (old != null) {
                if (old.isTwoHanded()) {
                    this.twoHanded = true;
                }
                int power = old.getPotency();
                int bonus = (power % (Shop.MAX_ENHANCEMENTS + 1)) + 1;
                this.item = EquipmentFactory.createEnhancedEquipment(old,
                        bonus);
                this.cost = Shop.getEnhancementCost(bonus, power);
                if (this.cost == 0) {
                    // Equipment is maxed out on enhancements
                    CommonDialogs.showErrorDialog(
                            "That equipment cannot be enhanced any further, sorry!",
                            this.getShopNameFromType());
                    return false;
                }
            } else {
                CommonDialogs.showErrorDialog("Nothing is equipped there!",
                        this.getShopNameFromType());
                return false;
            }
        } else if (this.type == ShopTypes.SHOP_TYPE_FAITH_POWERS) {
            Equipment old = playerCharacter.getItems()
                    .getEquipmentInSlot(this.index);
            if (old != null) {
                if (old.isTwoHanded()) {
                    this.twoHanded = true;
                }
                int power = old.getPotency();
                int bonus = old.getFaithPowerLevel(this.typeIndex);
                this.item = EquipmentFactory.createFaithPoweredEquipment(old,
                        this.typeIndex, bonus);
                this.cost = Shop.getEnhancementCost(bonus, power);
                if (this.cost == 0) {
                    // Equipment is maxed out on Faith Powers
                    CommonDialogs.showErrorDialog(
                            "That equipment cannot be Faith Powered any further, sorry!",
                            this.getShopNameFromType());
                    return false;
                }
            } else {
                CommonDialogs.showErrorDialog("Nothing is equipped there!",
                        this.getShopNameFromType());
                return false;
            }
        }
        // Handle inflation
        double actualInflation = this.inflationRate / 100.0;
        double inflatedCost = this.cost * actualInflation;
        this.cost = (int) inflatedCost;
        // Confirm
        final int stage4Confirm = CommonDialogs.showConfirmDialog(
                "This will cost " + this.cost + " Gold. Are you sure?",
                this.getShopNameFromType());
        if (stage4Confirm == JOptionPane.NO_OPTION
                || stage4Confirm == JOptionPane.CLOSED_OPTION) {
            return false;
        }
        if (Support.inDebugMode()) {
            CommonDialogs.showTitledDialog(
                    "Debug mode is enabled, so this purchase is free.",
                    this.getShopNameFromType());
            this.cost = 0;
        }
        return true;
    }

    private boolean shopStage5() {
        // Stage 5
        PartyMember playerCharacter = PartyManager.getParty().getLeader();
        if (playerCharacter.getGold() < this.cost) {
            CommonDialogs.showErrorDialog("Not Enough Gold!",
                    this.getShopNameFromType());
            return false;
        }
        return true;
    }

    private void shopStage6() {
        // Stage 6
        PartyMember playerCharacter = PartyManager.getParty().getLeader();
        // Play transact sound
        SoundManager.playSound(GameSoundConstants.SOUND_TRANSACT);
        if (this.type == ShopTypes.SHOP_TYPE_WEAPONS) {
            playerCharacter.offsetGold(-this.cost);
            if (this.typeResult.equals(this.typeChoices[0])) {
                Equipment bought = EquipmentFactory
                        .createOneHandedWeapon(this.index, 0);
                playerCharacter.getItems().equipOneHandedWeapon(playerCharacter,
                        bought, this.handIndex, true);
            } else {
                Equipment bought = EquipmentFactory
                        .createTwoHandedWeapon(this.index, 0);
                playerCharacter.getItems().equipTwoHandedWeapon(playerCharacter,
                        bought, true);
            }
        } else if (this.type == ShopTypes.SHOP_TYPE_ARMOR) {
            playerCharacter.offsetGold(-this.cost);
            Equipment bought = EquipmentFactory.createArmor(this.index,
                    this.typeIndex, 0);
            playerCharacter.getItems().equipArmor(playerCharacter, bought,
                    true);
        } else if (this.type == ShopTypes.SHOP_TYPE_HEALER) {
            playerCharacter.offsetGold(-this.cost);
            playerCharacter.healPercentage((this.index + 1) * 10);
        } else if (this.type == ShopTypes.SHOP_TYPE_REGENERATOR) {
            playerCharacter.offsetGold(-this.cost);
            playerCharacter.regeneratePercentage((this.index + 1) * 10);
        } else if (this.type == ShopTypes.SHOP_TYPE_ITEMS) {
            playerCharacter.offsetGold(-this.cost);
            playerCharacter.getItems().addItem(this.item);
        } else if (this.type == ShopTypes.SHOP_TYPE_ENHANCEMENTS) {
            playerCharacter.offsetGold(-this.cost);
            playerCharacter.getItems().setEquipmentInSlot(this.index,
                    (Equipment) this.item);
            if (this.twoHanded) {
                if (this.index == EquipmentSlotConstants.SLOT_MAINHAND) {
                    playerCharacter.getItems().setEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_OFFHAND,
                            (Equipment) this.item);
                } else if (this.index == EquipmentSlotConstants.SLOT_OFFHAND) {
                    playerCharacter.getItems().setEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_MAINHAND,
                            (Equipment) this.item);
                }
            }
        } else if (this.type == ShopTypes.SHOP_TYPE_FAITH_POWERS) {
            playerCharacter.offsetGold(-this.cost);
            playerCharacter.getItems().setEquipmentInSlot(this.index,
                    (Equipment) this.item);
            if (this.twoHanded) {
                if (this.index == EquipmentSlotConstants.SLOT_MAINHAND) {
                    playerCharacter.getItems().setEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_OFFHAND,
                            (Equipment) this.item);
                } else if (this.index == EquipmentSlotConstants.SLOT_OFFHAND) {
                    playerCharacter.getItems().setEquipmentInSlot(
                            EquipmentSlotConstants.SLOT_MAINHAND,
                            (Equipment) this.item);
                }
            }
        }
    }
}
