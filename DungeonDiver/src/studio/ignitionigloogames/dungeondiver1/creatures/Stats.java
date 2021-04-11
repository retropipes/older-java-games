package studio.ignitionigloogames.dungeondiver1.creatures;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.items.ItemNames;
import studio.ignitionigloogames.dungeondiver1.items.ShopTypes;

public class Stats {
    // Constants
    public static final int MAX_STATS = 11;

    // Private constructor
    private Stats() {
        // Do nothing
    }

    // Static Methods
    public static String[] getStatString() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        final String[] statString = new String[Stats.MAX_STATS];
        statString[0] = "Level: " + player.getLevel();
        statString[1] = "HP: " + player.getHPString();
        statString[2] = "MP: " + player.getMPString();
        statString[3] = "Weapon: " + ItemNames.getItemName(ShopTypes.WEAPONS,
                player.getWeaponPower(), player.getPlayerClass());
        statString[4] = "Armor: " + ItemNames.getItemName(ShopTypes.ARMOR,
                player.getArmorBlock(), player.getPlayerClass());
        statString[5] = "Attack: " + player.getAttack();
        statString[6] = "Defense: " + player.getDefense();
        statString[7] = "Gold: " + player.getGold();
        statString[8] = "XP: " + player.getExperience();
        statString[9] = "Next: " + player.getToNextLevel();
        statString[10] = "Dungeon: " + player.getDungeonLevel();
        return statString;
    }
}
