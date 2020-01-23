package com.puttysoftware.rulemazer.generic;

import com.puttysoftware.rulemazer.maze.Maze;

public interface RandomGenerationRule {
    public static final int NO_LIMIT = 0;

    public boolean shouldGenerateObject(Maze maze, int row, int col, int floor,
            int level, int layer);

    public int getMinimumRequiredQuantity(Maze maze);

    public int getMaximumRequiredQuantity(Maze maze);

    public boolean isRequired();
}
