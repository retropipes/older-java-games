/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

public class ObjectImageConstants {
    public static final int NONE = -1;
    public static final int ARMOR_SHOP = 0;
    public static final int CLOSED_DOOR = 1;
    public static final int DARKNESS = 2;
    public static final int EMPTY = 3;
    public static final int HEAL_SHOP = 4;
    public static final int ICE = 5;
    public static final int OPEN_DOOR = 6;
    public static final int REGENERATOR = 7;
    public static final int SPELL_SHOP = 8;
    public static final int BOSS = 9;
    public static final int FINAL_BOSS = 10;
    public static final int TILE = 11;
    public static final int WALL = 12;
    public static final int WEAPONS_SHOP = 13;
    private static final String[] NAMES = ImageDataManager
            .getObjectGraphicsData();

    static String getObjectImageName(final int ID) {
        if (ID == ObjectImageConstants.NONE) {
            return ObjectImageConstants.NAMES[ObjectImageConstants.EMPTY];
        } else {
            return ObjectImageConstants.NAMES[ID];
        }
    }
}
