package com.puttysoftware.mazerunner2.maze.utilities;

import com.puttysoftware.mazerunner2.maze.Maze;

public interface RandomGenerationRule {
        int NO_LIMIT = 0;

        boolean shouldGenerateObject(Maze maze, int row, int col, int floor,
                        int level, int layer);

        int getMinimumRequiredQuantity(Maze maze);

        int getMaximumRequiredQuantity(Maze maze);

        boolean isRequired();

        boolean shouldGenerateObjectInBattle(Maze maze, int row, int col, int floor,
                        int level, int layer);

        int getMinimumRequiredQuantityInBattle(Maze maze);

        int getMaximumRequiredQuantityInBattle(Maze maze);

        boolean isRequiredInBattle();
}
