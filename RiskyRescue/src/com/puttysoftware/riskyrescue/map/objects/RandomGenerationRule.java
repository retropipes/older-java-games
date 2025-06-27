/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.map.objects;

import com.puttysoftware.riskyrescue.map.Map;

public interface RandomGenerationRule {
        int NO_LIMIT = 0;

        boolean shouldGenerateObject(Map map, int row, int col, int floor,
                        int level, int layer);

        int getMinimumRequiredQuantity(Map map, int level);

        int getMaximumRequiredQuantity(Map map, int level);

        boolean isRequired(int level);

        boolean shouldGenerateObjectInBattle(Map map, int row, int col, int floor,
                        int level, int layer);

        int getMinimumRequiredQuantityInBattle(Map map);

        int getMaximumRequiredQuantityInBattle(Map map);

        boolean isRequiredInBattle();
}
