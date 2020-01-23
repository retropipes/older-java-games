/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon.utilities;

public class TypeConstants {
    public static final int TYPE_GROUND = 0;
    public static final int TYPE_FIELD = 1;
    public static final int TYPE_CHARACTER = 2;
    public static final int TYPE_PASS_THROUGH = 3;
    public static final int TYPE_TELEPORT = 12;
    public static final int TYPE_WALL = 16;
    public static final int TYPE_PLAIN_WALL = 23;
    public static final int TYPE_EMPTY_SPACE = 24;
    public static final int TYPE_SHOP = 33;
    public static final int TYPE_TRAP = 38;
    public static final int TYPE_DUNGEON = 45;
    public static final int TYPE_MP_MODIFIER = 60;
    public static final int TYPE_TRIGGER = 61;
    public static final int TYPES_COUNT = 63;

    private TypeConstants() {
        // Do nothing
    }
}
