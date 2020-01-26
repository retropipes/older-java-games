package com.puttysoftware.dungeondiver4.dungeon.utilities;

import com.puttysoftware.dungeondiver4.dungeon.Dungeon;

public interface RandomGenerationRule {
    int NO_LIMIT = 0;

    boolean shouldGenerateObject(Dungeon dungeon, int row, int col, int floor,
            int level, int layer);

    int getMinimumRequiredQuantity(Dungeon dungeon);

    int getMaximumRequiredQuantity(Dungeon dungeon);

    boolean isRequired();

    boolean shouldGenerateObjectInBattle(Dungeon dungeon, int row, int col,
            int floor, int level, int layer);

    int getMinimumRequiredQuantityInBattle(Dungeon dungeon);

    int getMaximumRequiredQuantityInBattle(Dungeon dungeon);

    boolean isRequiredInBattle();
}
