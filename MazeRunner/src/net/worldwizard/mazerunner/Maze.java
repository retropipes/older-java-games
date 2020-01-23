package net.worldwizard.mazerunner;

import java.io.Serializable;

public class Maze implements Serializable {
    // Properties
    private final MazeObject[][][][] mazeData;
    private final MazeObject[][][][] savedLevel;
    private final MazeObject[][][][] savedObject;
    private final int[][] playerData;
    private int startW;
    // Serialization
    private static final long serialVersionUID = 8000L;

    // Constructors
    public Maze() {
        this.mazeData = new MazeObject[1][1][1][1];
        this.savedLevel = new MazeObject[1][1][1][1];
        this.savedObject = new MazeObject[1][1][1][1];
        this.playerData = new int[1][3];
    }

    public Maze(final int rows, final int cols, final int floors,
            final int levels) {
        this.mazeData = new MazeObject[cols][rows][floors][levels];
        this.savedLevel = new MazeObject[cols][rows][floors][levels];
        this.savedObject = new MazeObject[cols][rows][floors][levels];
        this.playerData = new int[levels][3];
    }

    // Accessors
    public MazeObject getCell(final int row, final int col, final int floor,
            final int level) {
        return this.mazeData[col][row][floor][level];
    }

    public int getStartRow(final int level) {
        return this.playerData[level][1];
    }

    public int getStartColumn(final int level) {
        return this.playerData[level][0];
    }

    public int getStartFloor(final int level) {
        return this.playerData[level][2];
    }

    public int getStartLevel() {
        return this.startW;
    }

    public int getRows() {
        return this.mazeData[0].length;
    }

    public int getColumns() {
        return this.mazeData.length;
    }

    public int getFloors() {
        return this.mazeData[0][0].length;
    }

    public int getLevels() {
        return this.mazeData[0][0][0].length;
    }

    public boolean findPlayerOnLevel(final int level) {
        int x, y, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    if (this.mazeData[x][y][z][level].getName()
                            .equals("Player")) {
                        this.playerData[level][1] = x;
                        this.playerData[level][0] = y;
                        this.playerData[level][2] = z;
                        this.startW = level;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Transformers
    public void setCell(final MazeObject mo, final int row, final int col,
            final int floor, final int level) {
        this.mazeData[col][row][floor][level] = mo;
    }

    public void setStartRow(final int level, final int newStartRow) {
        this.playerData[level][1] = newStartRow;
    }

    public void setStartColumn(final int level, final int newStartColumn) {
        this.playerData[level][0] = newStartColumn;
    }

    public void setStartFloor(final int level, final int newStartFloor) {
        this.playerData[level][2] = newStartFloor;
    }

    public void setStartLevel(final int newStartLevel) {
        this.startW = newStartLevel;
    }

    public void saveLevel(final int level) {
        int x, y, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    this.savedLevel[x][y][z][level] = this.mazeData[x][y][z][level];
                    if (this.mazeData[x][y][z][level].isPushable()
                            || this.mazeData[x][y][z][level].isPullable()) {
                        final MazeGenericMovableObject pb = (MazeGenericMovableObject) this.mazeData[x][y][z][level];
                        this.savedObject[x][y][z][level] = pb.getSavedObject();
                    }
                }
            }
        }
    }

    public void restoreLevel(final int level) {
        int x, y, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    this.mazeData[x][y][z][level] = this.savedLevel[x][y][z][level];
                    if (this.savedLevel[x][y][z][level].isPushable()
                            || this.savedLevel[x][y][z][level].isPullable()) {
                        final MazeGenericMovableObject pb = (MazeGenericMovableObject) this.savedLevel[x][y][z][level];
                        pb.setSavedObject(this.savedObject[x][y][z][level]);
                        this.mazeData[x][y][z][level] = pb;
                    }
                }
            }
        }
    }
}