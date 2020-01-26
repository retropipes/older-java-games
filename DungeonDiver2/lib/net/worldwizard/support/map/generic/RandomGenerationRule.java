package net.worldwizard.support.map.generic;

import net.worldwizard.support.map.Map;

public interface RandomGenerationRule {
    int NO_LIMIT = 0;

    boolean shouldGenerateObject(Map map, int row, int col, int floor,
            int level, int layer);

    int getMinimumRequiredQuantity(Map map);

    int getMaximumRequiredQuantity(Map map);

    boolean isRequired();
}
