/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.maze;

import java.io.IOException;

import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.objects.Monster;
import studio.ignitionigloogames.twistedtrek.import1.objects.MovingBlock;
import studio.ignitionigloogames.xio.XDataReader;
import studio.ignitionigloogames.xio.XDataWriter;

public interface Maze extends FormatConstants {
    // Constants
    int LAYER_GROUND = 0;
    int LAYER_OBJECT = 1;
    int LAYER_COUNT = 2;
    int SIZE_CURRENT = -1;

    // Methods
    int getMinLevels();

    int getMaxLevels();

    void cutLevel(int level);

    void copyLevel(int level);

    void pasteLevel(int level);

    boolean isPasteBlocked();

    boolean isCutBlocked();

    boolean insertLevelFromClipboard();

    boolean addLevel(final int rows, final int cols, final int floors);

    boolean removeLevel(int level);

    MazeObject getCell(final int row, final int col, final int floor, final int level, final int extra);

    int getFindResultRow(int level);

    int getFindResultColumn(int level);

    int getFindResultFloor(int level);

    int getFindResultLevel();

    int getStartRow(final int level);

    int getStartColumn(final int level);

    int getStartFloor(final int level);

    int getStartLevel();

    int getRows(int level);

    int getColumns(int level);

    int getFloors(int level);

    int getRows();

    int getColumns();

    int getFloors();

    int getLevels();

    int getVisionRadius(int level);

    void findAllStarts();

    boolean findPlayerOnLevel(final int level);

    boolean findNthMazeObjectOnLevel(final int level, final MazeObject obj, final int N);

    void findAllObjectPairsAndSwap(final int level, final MazeObject o1, final MazeObject o2);

    void findAllMatchingObjectsAndDecay(final int level, final MazeObject o);

    void masterTrapTrigger(final int level);

    void tickTimers(final int level, final int floor);

    boolean rotateRadiusClockwise(final int x, final int y, final int z, final int w, final int r);

    boolean rotateRadiusCounterclockwise(final int x, final int y, final int z, final int w, final int r);

    void resize(final int x, final int y, final int z, final int w);

    boolean radialScan(final int x, final int y, final int z, final int w, final int l, final int r,
            final String targetName);

    void radialScanTimerAction(final int x, final int y, final int z, final int w, final int l, final int r,
            final String targetName, final int timerMod);

    void radialScanKillMonsters(final int x, final int y, final int z, final int w, final int l, final int r);

    void radialScanFreezeObjects(final int x, final int y, final int z, final int w, final int l, final int r);

    void radialScanWarpObjects(final int x, final int y, final int z, final int w, final int l, final int r);

    void radialScanShuffleObjects(final int x, final int y, final int z, final int w, final int r);

    boolean isSquareVisible(int x1, int y1, int x2, int y2, int w);

    void setCell(final MazeObject mo, final int row, final int col, final int floor, final int level, final int extra);

    void setStartRow(final int level, final int newStartRow);

    void setStartColumn(final int level, final int newStartColumn);

    void setStartFloor(final int level, final int newStartFloor);

    void setStartLevel(final int newStartLevel);

    void setVisionRadiusToMaximum(int level);

    void setVisionRadiusToMinimum(int level);

    void incrementVisionRadius(int level);

    void decrementVisionRadius(int level);

    void fill(MazeObject bottom, MazeObject top);

    void fillLevel(int level, MazeObject bottom, MazeObject top);

    void save();

    void restore();

    void saveLevel(final int level);

    void restoreLevel(final int level);

    void updateMonsterPosition(final int move, final int xLoc, final int yLoc, final Monster monster);

    void updateMovingBlockPosition(final int move, final int xLoc, final int yLoc, final MovingBlock block);

    void postBattle(final Monster monster, final int xLoc, final int yLoc, final boolean player);

    void generateOneMonster();

    void warpObject(MazeObject mo, int x, int y, int z, int w, int l);

    void enableHorizontalWraparound(int level);

    void disableHorizontalWraparound(int level);

    void enableVerticalWraparound(int level);

    void disableVerticalWraparound(int level);

    void enable3rdDimensionWraparound(int level);

    void disable3rdDimensionWraparound(int level);

    boolean isHorizontalWraparoundEnabled(int level);

    boolean isVerticalWraparoundEnabled(int level);

    boolean is3rdDimensionWraparoundEnabled(int level);

    void writeMaze(XDataWriter writer) throws IOException;

    Maze readMaze(XDataReader reader, int formatVersion) throws IOException;

    void writeSavedMazeState(XDataWriter writer) throws IOException;

    void readSavedMazeState(XDataReader reader, int formatVersion) throws IOException;
}