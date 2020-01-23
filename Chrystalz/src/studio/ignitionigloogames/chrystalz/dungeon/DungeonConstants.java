/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon;

public class DungeonConstants {
    public static final int LAYER_GROUND = 0;
    public static final int LAYER_OBJECT = 1;
    public static final int LAYER_COUNT = 2;
    public static final int VIRTUAL_LAYER_CHARACTER = 2;
    public static final int VISION_MODE_NONE = 0;
    public static final int VISION_MODE_EXPLORE = 1;
    public static final int VISION_MODE_LOS = 2;
    public static final int VISION_MODE_EXPLORE_AND_LOS = 3;

    private DungeonConstants() {
        // Do nothing
    }
}
