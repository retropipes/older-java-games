package com.puttysoftware.mazer5d.mazemodel;

import java.io.IOException;

import com.puttysoftware.mazer5d.files.PrefixIO;
import com.puttysoftware.mazer5d.files.SuffixIO;
import com.puttysoftware.mazer5d.objectmodel.MazeObjectModel;

public interface MazeModel {
    // Methods
    int getCurrentHP();

    int getMaximumHP();

    String getHPString();

    void setMaximumHP(int max);

    boolean isAlive();

    void doPoisonDamage();

    void doDamage(int amount);

    void doDamagePercentage(int percent);

    void heal(int amount);

    void healFully();

    void healPercentage(int percent);

    String getBasePath();

    void setPrefixHandler(PrefixIO xph);

    void setSuffixHandler(SuffixIO xsh);

    String getMazeTitle();

    void setMazeTitle(String title);

    String getMazeStartMessage();

    void setMazeStartMessage(String msg);

    String getMazeEndMessage();

    void setMazeEndMessage(String msg);

    String getLevelTitle();

    void setLevelTitle(String title);

    String getLevelStartMessage();

    void setLevelStartMessage(String msg);

    String getLevelEndMessage();

    void setLevelEndMessage(String msg);

    int getExploreRadius();

    void setExploreRadius(int newER);

    void addVisionMode(int newMode);

    void removeVisionMode(int newMode);

    void updateVisibleSquares(int xp, int yp, int zp);

    int getFinishMoveSpeed();

    void setFinishMoveSpeed(int value);

    void setFirstMovingFinishX(int value);

    void setFirstMovingFinishY(int value);

    void setFirstMovingFinishZ(int value);

    int getPoisonPower();

    void setPoisonPower(int pp);

    void doPoisonousAmulet();

    void doCounterpoisonAmulet();

    void undoPoisonAmulets();

    String getPoisonString();

    boolean getAutoFinishThresholdEnabled();

    void setAutoFinishThresholdEnabled(boolean afte);

    int getAutoFinishThreshold();

    void setAutoFinishThreshold(int aft);

    int getAlternateAutoFinishThreshold();

    void setAlternateAutoFinishThreshold(int aaft);

    int getNextLevel();

    boolean useOffset();

    void setUseOffset(boolean uo);

    void setNextLevel(int nl);

    void setNextLevelOffset(int nlo);

    int getAlternateNextLevel();

    boolean useAlternateOffset();

    void setUseAlternateOffset(boolean uao);

    void setAlternateNextLevel(int anl);

    void setAlternateNextLevelOffset(int anlo);

    int getActiveLevelNumber();

    void switchLevel(int level);

    void switchLevelOffset(int level);

    boolean doesLevelExist(int level);

    boolean doesLevelExistOffset(int level);

    void cutLevel();

    void copyLevel();

    void pasteLevel();

    boolean isPasteBlocked();

    boolean isCutBlocked();

    boolean insertLevelFromClipboard();

    boolean addLevel(int rows, int cols, int floors);

    boolean removeLevel();

    MazeObjectModel getCell(int row, int col, int floor, int extra);

    int getFindResultRow();

    int getFindResultColumn();

    int getFindResultFloor();

    int getStartRow();

    int getStartColumn();

    int getStartFloor();

    int getStartLevel();

    int getRows();

    int getColumns();

    int getFloors();

    int getLevels();

    int getVisionRadius();

    boolean doesPlayerExist();

    void findStart();

    boolean findPlayer();

    void findAllObjectPairsAndSwap(MazeObjectModel o1, MazeObjectModel o2);

    void findAllMatchingObjectsAndDecay(MazeObjectModel o);

    void masterTrapTrigger();

    void tickTimers(int floor);

    boolean rotateRadiusClockwise(int x, int y, int z, int r);

    boolean rotateRadiusCounterclockwise(int x, int y, int z, int r);

    void resize(int x, int y, int z);

    boolean radialScan(int x, int y, int z, int l, int r, String targetName);

    void radialScanFreezeObjects(int x, int y, int z, int r);

    void radialScanFreezeGround(int x, int y, int z, int r);

    void radialScanEnrageObjects(int x, int y, int z, int r);

    void radialScanBurnGround(int x, int y, int z, int r);

    void radialScanPoisonObjects(int x, int y, int z, int r);

    void radialScanPoisonGround(int x, int y, int z, int r);

    void radialScanShockObjects(int x, int y, int z, int r);

    void radialScanShockGround(int x, int y, int z, int r);

    void radialScanWarpObjects(int x, int y, int z, int l, int r);

    void radialScanShuffleObjects(int x, int y, int z, int r);

    void radialScanQuakeBomb(int x, int y, int z, int r);

    boolean isSquareVisible(int x1, int y1, int x2, int y2);

    void setCell(MazeObjectModel mo, int row, int col, int floor, int extra);

    void setStartRow(int newStartRow);

    void setStartColumn(int newStartColumn);

    void setStartFloor(int newStartFloor);

    void setStartLevel(int newStartLevel);

    void resetVisionRadius();

    void setVisionRadius(int newVR);

    void setVisionRadiusToMaximum();

    void setVisionRadiusToMinimum();

    void incrementVisionRadius();

    void decrementVisionRadius();

    void deactivateAllMovingFinishes();

    void activateFirstMovingFinish();

    void fillLevelDefault();

    void fillFloorDefault(int floor);

    void fillLevel(MazeObjectModel bottom, MazeObjectModel top);

    void fillLevelRandomly();

    void fillFloorRandomly(int z);

    void fillLevelRandomlyCustom();

    void fillFloorRandomlyCustom(int z);

    void save();

    void restore();

    void saveStart();

    void restoreStart();

    void updateMovingBlockPosition(int move, int xLoc, int yLoc,
            MazeObjectModel block);

    void warpObject(MazeObjectModel mo, int x, int y, int z, int l);

    void hotGround(int x, int y, int z);

    void enableHorizontalWraparound();

    void disableHorizontalWraparound();

    void enableVerticalWraparound();

    void disableVerticalWraparound();

    void enable3rdDimensionWraparound();

    void disable3rdDimensionWraparound();

    boolean isHorizontalWraparoundEnabled();

    boolean isVerticalWraparoundEnabled();

    boolean is3rdDimensionWraparoundEnabled();

    String getTimeString();

    boolean isTimerActive();

    void activateTimer(int ticks);

    void deactivateTimer();

    void resetTimer();

    int getTimerValue();

    void extendTimerByInitialValue();

    void extendTimerByInitialValueHalved();

    void extendTimerByInitialValueDoubled();

    MazeModel readMaze() throws IOException;

    void writeMaze() throws IOException;
}