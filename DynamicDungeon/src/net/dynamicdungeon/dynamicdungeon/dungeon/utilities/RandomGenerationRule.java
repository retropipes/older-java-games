package net.dynamicdungeon.dynamicdungeon.dungeon.utilities;

import net.dynamicdungeon.dynamicdungeon.dungeon.Dungeon;

public interface RandomGenerationRule {
    int NO_LIMIT = 0;

    boolean shouldGenerateObject(Dungeon maze, int row, int col, int floor,
            int level, int layer);

    int getMinimumRequiredQuantity(Dungeon maze);

    int getMaximumRequiredQuantity(Dungeon maze);

    boolean isRequired();
}
