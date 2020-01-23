package net.worldwizard.support.map.generic;

import net.worldwizard.support.map.Map;

public interface RandomGenerationRule {
    public static final int NO_LIMIT = 0;

    public boolean shouldGenerateObject(Map map, int row, int col, int floor,
            int level, int layer);

    public int getMinimumRequiredQuantity(Map map);

    public int getMaximumRequiredQuantity(Map map);

    public boolean isRequired();
}
