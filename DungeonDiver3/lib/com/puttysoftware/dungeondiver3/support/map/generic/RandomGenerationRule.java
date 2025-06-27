/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.generic;

import com.puttysoftware.dungeondiver3.support.map.Map;

public interface RandomGenerationRule {
        int NO_LIMIT = 0;

        boolean shouldGenerateObject(Map map, int row, int col, int floor,
                        int level, int layer);

        int getMinimumRequiredQuantity(Map map);

        int getMaximumRequiredQuantity(Map map);

        boolean isRequired();

        boolean shouldGenerateObjectInBattle(Map map, int row, int col, int floor,
                        int level, int layer);

        int getMinimumRequiredQuantityInBattle(Map map);

        int getMaximumRequiredQuantityInBattle(Map map);

        boolean isRequiredInBattle();
}
