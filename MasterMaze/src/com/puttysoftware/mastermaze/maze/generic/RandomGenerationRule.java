package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.maze.Maze;

public interface RandomGenerationRule {
    public static final int NO_LIMIT = 0;

    public boolean shouldGenerateObject(Maze maze, int row, int col, int floor,
            int level, int layer);

    public int getMinimumRequiredQuantity(Maze maze);

    public int getMaximumRequiredQuantity(Maze maze);

    public boolean isRequired();

    public boolean shouldGenerateObjectInBattle(Maze maze, int row, int col,
            int floor, int level, int layer);

    public int getMinimumRequiredQuantityInBattle(Maze maze);

    public int getMaximumRequiredQuantityInBattle(Maze maze);

    public boolean isRequiredInBattle();
}
