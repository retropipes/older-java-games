/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon.utilities;

import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;

public interface RandomGenerationRule {
    int NO_LIMIT = 0;

    boolean shouldGenerateObject(Dungeon dungeon, int row, int col, int level,
            int layer);

    int getMinimumRequiredQuantity(Dungeon dungeon);

    int getMaximumRequiredQuantity(Dungeon dungeon);

    boolean isRequired(Dungeon dungeon);
}
