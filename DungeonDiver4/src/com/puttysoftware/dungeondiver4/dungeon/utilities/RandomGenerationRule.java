package com.puttysoftware.dungeondiver4.dungeon.utilities;

import com.puttysoftware.dungeondiver4.dungeon.Dungeon;

public interface RandomGenerationRule {
    public static final int NO_LIMIT = 0;

    public boolean shouldGenerateObject(Dungeon dungeon, int row, int col,
            int floor, int level, int layer);

    public int getMinimumRequiredQuantity(Dungeon dungeon);

    public int getMaximumRequiredQuantity(Dungeon dungeon);

    public boolean isRequired();

    public boolean shouldGenerateObjectInBattle(Dungeon dungeon, int row,
            int col, int floor, int level, int layer);

    public int getMinimumRequiredQuantityInBattle(Dungeon dungeon);

    public int getMaximumRequiredQuantityInBattle(Dungeon dungeon);

    public boolean isRequiredInBattle();
}
