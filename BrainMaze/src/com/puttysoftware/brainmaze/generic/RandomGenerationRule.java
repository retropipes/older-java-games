package com.puttysoftware.brainmaze.generic;

import com.puttysoftware.brainmaze.maze.Maze;

public interface RandomGenerationRule {
    int NO_LIMIT = 0;

    boolean shouldGenerateObject(Maze maze, int row, int col, int floor,
            int level, int layer);

    int getMinimumRequiredQuantity(Maze maze);

    int getMaximumRequiredQuantity(Maze maze);

    boolean isRequired();
}
