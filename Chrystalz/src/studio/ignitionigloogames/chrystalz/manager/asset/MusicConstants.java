/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

public class MusicConstants {
    // Public Music Constants
    public static final int MUSIC_BATTLE = 0;
    public static final int MUSIC_BOSS = 1;
    public static final int MUSIC_CREATE = 2;
    public static final int MUSIC_DUNGEON = 3;
    public static final int MUSIC_FORGE = 4;
    public static final int MUSIC_LAIR = 5;
    public static final int MUSIC_SHOP = 6;
    public static final int MUSIC_TITLE = 7;
    private static final String[] MUSIC_NAMES = MusicDataManager.getMusicData();

    // Private constructor
    private MusicConstants() {
        // Do nothing
    }

    static String getMusicName(final int ID) {
        return MusicConstants.MUSIC_NAMES[ID];
    }
}