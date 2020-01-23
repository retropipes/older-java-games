package net.worldwizard.worldz.generic;

import net.worldwizard.worldz.world.World;

public interface RandomGenerationRule {
    public static final int NO_LIMIT = 0;

    public boolean shouldGenerateObject(World world, int row, int col,
            int floor, int level, int layer);

    public int getMinimumRequiredQuantity(World world);

    public int getMaximumRequiredQuantity(World world);

    public boolean isRequired();
}
