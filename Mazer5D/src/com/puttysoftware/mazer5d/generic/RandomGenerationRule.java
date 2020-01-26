package com.puttysoftware.mazer5d.generic;

import com.puttysoftware.mazer5d.maze.Maze;

public interface RandomGenerationRule {
    int NO_LIMIT = 0;

    boolean shouldGenerateObject(Maze maze, int row, int col, int floor,
            int level, int layer);

    int getMinimumRequiredQuantity(Maze maze);

    int getMaximumRequiredQuantity(Maze maze);

    boolean isRequired();
}
