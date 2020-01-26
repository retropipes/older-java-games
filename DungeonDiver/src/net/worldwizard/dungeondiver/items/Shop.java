package net.worldwizard.dungeondiver.items;

import javax.swing.JOptionPane;

import net.worldwizard.dungeondiver.DungeonDiver;
import net.worldwizard.dungeondiver.creatures.Boss;
import net.worldwizard.dungeondiver.creatures.Player;

public class Shop implements ShopTypes {
    // Fields
    private final int type;

    // Constructors
    public Shop(final int shopType) {
        this.type = shopType;
    }

    public static int getEquipmentCost(final int x) {
        return 2 * x * x * x + 16 * x * x + 30 * x;
    }

    private String getGoldTotals() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        if (this.type == ShopTypes.BANK) {
            return "Gold on Hand: " + player.getGold() + "\nGold in Bank: "
                    + player.getGoldInBank() + "\n";
        } else {
            return "";
        }
    }

    public static int getHealingCost(final int x, final int y, final int z,
            final int k) {
        return (int) (Math.log10(x) * (z - y + Math.log10((double) k + 1)));
    }

    public static int getRegenerationCost(final int x, final int y, final int z,
            final int k) {
        final int diff = z - y;
        if (diff == 0) {
            return 0;
        } else {
            final int cost = (int) (Math.log(x) / Math.log(2)
                    * (z - y + Math.log((double) k + 1) / Math.log(2)));
            if (cost < 1) {
                return 1;
            } else {
                return cost;
            }
        }
    }

    private static String getShopNameFromType(final int type) {
        switch (type) {
        case WEAPONS:
            return "Weapons";
        case ARMOR:
            return "Armor";
        case HEALER:
            return "Healer";
        case BANK:
            return "Bank";
        case REGENERATOR:
            return "Regenerator";
        default:
            return null;
        }
    }

    public void showShop() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        // Stage 1
        String[] stage1Choices = null;
        int stage1Default = 0;
        if (this.type == ShopTypes.WEAPONS || this.type == ShopTypes.ARMOR) {
            stage1Choices = new String[Boss.FIGHT_LEVEL];
            int x;
            for (x = 0; x < stage1Choices.length; x++) {
                stage1Choices[x] = ItemNames.getItemName(this.type, x + 1,
                        player.getPlayerClass());
            }
        } else if (this.type == ShopTypes.HEALER
                || this.type == ShopTypes.REGENERATOR) {
            stage1Choices = new String[10];
            int x;
            for (x = 0; x < stage1Choices.length; x++) {
                stage1Choices[x] = Integer.toString((x + 1) * 10) + "%";
            }
            stage1Default = 9;
        } else if (this.type == ShopTypes.BANK) {
            stage1Choices = new String[2];
            stage1Choices[0] = "Deposit";
            stage1Choices[1] = "Withdraw";
        } else {
            // Invalid shop type
            return;
        }
        final String stage1Result = (String) JOptionPane.showInputDialog(null,
                this.getGoldTotals() + "Select",
                Shop.getShopNameFromType(this.type),
                JOptionPane.QUESTION_MESSAGE, null, stage1Choices,
                stage1Choices[stage1Default]);
        if (stage1Result == null) {
            return;
        }
        int stage1Index = 0;
        for (stage1Index = 0; stage1Index < stage1Choices.length; stage1Index++) {
            if (stage1Result.equals(stage1Choices[stage1Index])) {
                break;
            }
        }
        // Stage 2
        int cost = 0;
        if (this.type == ShopTypes.WEAPONS || this.type == ShopTypes.ARMOR) {
            cost = Shop.getEquipmentCost(stage1Index + 1);
        } else if (this.type == ShopTypes.HEALER) {
            cost = Shop.getHealingCost(player.getLevel(), player.getCurrentHP(),
                    player.getMaximumHP(), player.getKills());
        } else if (this.type == ShopTypes.REGENERATOR) {
            cost = Shop.getRegenerationCost(player.getLevel(),
                    player.getCurrentMP(), player.getMaximumMP(),
                    player.getKills());
        } else if (this.type == ShopTypes.BANK) {
            String stage2Result = "";
            while (stage2Result.equals("")) {
                stage2Result = JOptionPane.showInputDialog(null,
                        this.getGoldTotals() + stage1Result + " How Much?",
                        Shop.getShopNameFromType(this.type),
                        JOptionPane.QUESTION_MESSAGE);
                if (stage2Result == null) {
                    return;
                }
                try {
                    cost = Integer.parseInt(stage2Result);
                    if (cost <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (final NumberFormatException nf) {
                    JOptionPane.showMessageDialog(null,
                            "Amount must be greater than zero.",
                            Shop.getShopNameFromType(this.type),
                            JOptionPane.INFORMATION_MESSAGE);
                    stage2Result = "";
                }
            }
        }
        if (this.type != ShopTypes.BANK) {
            final int stage2Confirm = JOptionPane.showConfirmDialog(null,
                    "This will cost " + cost + " Gold. Are you sure?",
                    Shop.getShopNameFromType(this.type),
                    JOptionPane.YES_NO_OPTION);
            if (stage2Confirm == JOptionPane.NO_OPTION
                    || stage2Confirm == JOptionPane.CLOSED_OPTION) {
                return;
            }
        }
        if (this.type == ShopTypes.WEAPONS) {
            final int currentWeapon = player.getWeaponPower();
            if (stage1Index + 1 <= currentWeapon) {
                final int stage2SecondConfirm = JOptionPane.showConfirmDialog(
                        null,
                        "This purchase will downgrade your weapon.\n"
                                + "Are you REALLY sure you want to buy this?",
                        Shop.getShopNameFromType(this.type),
                        JOptionPane.YES_NO_OPTION);
                if (stage2SecondConfirm == JOptionPane.NO_OPTION
                        || stage2SecondConfirm == JOptionPane.CLOSED_OPTION) {
                    return;
                }
            }
        } else if (this.type == ShopTypes.ARMOR) {
            final int currentArmor = player.getArmorBlock();
            if (stage1Index + 1 <= currentArmor) {
                final int stage2SecondConfirm = JOptionPane.showConfirmDialog(
                        null,
                        "This purchase will downgrade your armor.\n"
                                + "Are you REALLY sure you want to buy this?",
                        Shop.getShopNameFromType(this.type),
                        JOptionPane.YES_NO_OPTION);
                if (stage2SecondConfirm == JOptionPane.NO_OPTION
                        || stage2SecondConfirm == JOptionPane.CLOSED_OPTION) {
                    return;
                }
            }
        }
        // Stage 3
        if (this.type == ShopTypes.BANK) {
            if (stage1Index == 0) {
                if (player.getGold() < cost) {
                    JOptionPane.showMessageDialog(null, "Not Enough Gold!",
                            Shop.getShopNameFromType(this.type),
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            } else {
                if (player.getGoldInBank() < cost) {
                    JOptionPane.showMessageDialog(null, "Not Enough Gold!",
                            Shop.getShopNameFromType(this.type),
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        } else {
            if (player.getGold() < cost) {
                JOptionPane.showMessageDialog(null, "Not Enough Gold!",
                        Shop.getShopNameFromType(this.type),
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        // Stage 4
        if (this.type == ShopTypes.WEAPONS) {
            player.removeGold(cost);
            player.setWeaponPower(stage1Index + 1);
        } else if (this.type == ShopTypes.ARMOR) {
            player.removeGold(cost);
            player.setArmorBlock(stage1Index + 1);
        } else if (this.type == ShopTypes.HEALER) {
            player.removeGold(cost);
            player.healPercentage((stage1Index + 1) * 10);
        } else if (this.type == ShopTypes.REGENERATOR) {
            player.removeGold(cost);
            player.regeneratePercentage((stage1Index + 1) * 10);
        } else if (this.type == ShopTypes.BANK) {
            if (stage1Index == 0) {
                player.removeGold(cost);
                player.addGoldToBank(cost);
            } else {
                player.removeGoldFromBank(cost);
                player.addGold(cost);
            }
        }
    }
}
