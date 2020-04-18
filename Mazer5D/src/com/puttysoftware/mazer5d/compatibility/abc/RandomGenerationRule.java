package com.puttysoftware.mazer5d.compatibility.abc;

import com.puttysoftware.mazer5d.compatibility.maze.MazeModel;

public interface RandomGenerationRule {
    int NO_LIMIT = 0;

    boolean shouldGenerateObject(MazeModel maze, int row, int col, int floor,
            int level, int layer);

    int getMinimumRequiredQuantity(MazeModel maze);

    int getMaximumRequiredQuantity(MazeModel maze);

    boolean isRequired();
}
