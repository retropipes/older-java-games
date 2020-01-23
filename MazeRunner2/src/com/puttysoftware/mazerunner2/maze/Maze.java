/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze;

import java.io.File;
import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.maze.legacy.LegacyFormatConstants;
import com.puttysoftware.mazerunner2.maze.legacy.LegacyPrefixIO;
import com.puttysoftware.mazerunner2.maze.legacy.LegacySuffixIO;
import com.puttysoftware.mazerunner2.maze.objects.Empty;
import com.puttysoftware.mazerunner2.maze.objects.Monster;
import com.puttysoftware.mazerunner2.maze.objects.MovingBlock;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectList;
import com.puttysoftware.mazerunner2.prefs.PreferencesManager;
import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.xio.DirectoryUtilities;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;
import com.puttysoftware.xio.legacy.XLegacyDataReader;

public class Maze implements MazeConstants {
    // Properties
    private LayeredTower mazeData;
    private LayeredTower clipboard;
    private int startW;
    private int locW;
    private int saveW;
    private int levelCount;
    private int activeLevel;
    private String mazeTitle;
    private String mazeStartMessage;
    private String mazeEndMessage;
    private String basePath;
    private LegacyPrefixIO legacyPrefixHandler;
    private LegacySuffixIO legacySuffixHandler;
    private PrefixIO prefixHandler;
    private SuffixIO suffixHandler;
    private int[] savedStart;
    private static final int MIN_LEVELS = 1;
    private static final int MAX_LEVELS = Integer.MAX_VALUE;
    private static final String MAZE_PREFIX_GROUP = "prefix";
    private static final String MAZE_SETTINGS_GROUP = "settings";
    private static final String MAZE_SUFFIX_GROUP = "suffix";

    // Constructors
    public Maze() {
        this.mazeData = null;
        this.clipboard = null;
        this.levelCount = 0;
        this.startW = 0;
        this.locW = 0;
        this.saveW = 0;
        this.activeLevel = 0;
        this.legacyPrefixHandler = null;
        this.legacySuffixHandler = null;
        this.mazeTitle = "Untitled Maze";
        this.mazeStartMessage = "Let's Solve The Maze!";
        this.mazeEndMessage = "Maze Solved!";
        this.savedStart = new int[4];
        long random = new RandomRange(0, Long.MAX_VALUE).generateLong();
        String randomID = Long.toHexString(random);
        this.basePath = System.getProperty("java.io.tmpdir") + File.separator
                + "MazeRunnerII" + File.separator + randomID + ".maze";
        File base = new File(this.basePath);
        boolean success = base.mkdirs();
        if (!success) {
            CommonDialogs.showErrorDialog(
                    "Maze temporary folder creation failed!", "MazeRunnerII");
        }
    }

    // Static methods
    public static String getMazeTempFolder() {
        return System.getProperty("java.io.tmpdir") + File.separator
                + "MazeRunnerII";
    }

    public static int getMinLevels() {
        return Maze.MIN_LEVELS;
    }

    public static int getMaxLevels() {
        return Maze.MAX_LEVELS;
    }

    public static int getMinFloors() {
        return LayeredTower.getMinFloors();
    }

    public static int getMaxFloors() {
        return LayeredTower.getMaxFloors();
    }

    public static int getMinColumns() {
        return LayeredTower.getMinColumns();
    }

    public static int getMaxColumns() {
        return LayeredTower.getMaxColumns();
    }

    public static int getMinRows() {
        return LayeredTower.getMinRows();
    }

    public static int getMaxRows() {
        return LayeredTower.getMaxRows();
    }

    // Methods
    public static Maze getTemporaryBattleCopy() {
        Maze temp = new Maze();
        temp.addLevel(MazeRunnerII.getBattleMazeSize(),
                MazeRunnerII.getBattleMazeSize(), 1);
        MazeObjectList list = new MazeObjectList();
        AbstractMazeObject[] glo = list.getAllGroundLayerObjects();
        RandomRange gen = new RandomRange(0, glo.length - 1);
        AbstractMazeObject rand = glo[gen.generate()];
        if (PreferencesManager.getRandomBattleEnvironment()) {
            temp.fillLevelRandomlyInBattle(rand, new Empty());
        } else {
            temp.fillLevel(rand, new Empty());
        }
        return temp;
    }

    public void updateMonsterPosition(final int move, final int xLoc,
            final int yLoc, final Monster monster) {
        this.mazeData.updateMonsterPosition(move, xLoc, yLoc, monster);
    }

    public void postBattle(Monster m, final int xLoc, final int yLoc,
            boolean player) {
        this.mazeData.postBattle(m, xLoc, yLoc, player);
    }

    public void radialScanTimerAction(final int x, final int y, final int z,
            final int l, final int r, final String targetName,
            final int timerMod) {
        this.mazeData
                .radialScanTimerAction(x, y, z, l, r, targetName, timerMod);
    }

    public void radialScanKillMonsters(final int x, final int y, final int z,
            final int l, final int r) {
        this.mazeData.radialScanKillMonsters(x, y, z, l, r);
    }

    public String getBasePath() {
        return this.basePath;
    }

    public void setLegacyPrefixHandler(LegacyPrefixIO xph) {
        this.legacyPrefixHandler = xph;
    }

    public void setLegacySuffixHandler(LegacySuffixIO xsh) {
        this.legacySuffixHandler = xsh;
    }

    public void setPrefixHandler(PrefixIO xph) {
        this.prefixHandler = xph;
    }

    public void setSuffixHandler(SuffixIO xsh) {
        this.suffixHandler = xsh;
    }

    public String getMazeTitle() {
        return this.mazeTitle;
    }

    public void setMazeTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null!");
        }
        this.mazeTitle = title;
    }

    public String getMazeStartMessage() {
        return this.mazeStartMessage;
    }

    public void setMazeStartMessage(String msg) {
        if (msg == null) {
            throw new IllegalArgumentException("Message cannot be null!");
        }
        this.mazeStartMessage = msg;
    }

    public String getMazeEndMessage() {
        return this.mazeEndMessage;
    }

    public void setMazeEndMessage(String msg) {
        if (msg == null) {
            throw new IllegalArgumentException("Message cannot be null!");
        }
        this.mazeEndMessage = msg;
    }

    public String getLevelTitle() {
        return this.mazeData.getLevelTitle();
    }

    public void setLevelTitle(String title) {
        this.mazeData.setLevelTitle(title);
    }

    public String getLevelStartMessage() {
        return this.mazeData.getLevelStartMessage();
    }

    public void setLevelStartMessage(String msg) {
        this.mazeData.setLevelStartMessage(msg);
    }

    public String getLevelEndMessage() {
        return this.mazeData.getLevelEndMessage();
    }

    public void setLevelEndMessage(String msg) {
        this.mazeData.setLevelEndMessage(msg);
    }

    public int getFinishMoveSpeed() {
        return this.mazeData.getFinishMoveSpeed();
    }

    public void setFinishMoveSpeed(int value) {
        this.mazeData.setFinishMoveSpeed(value);
    }

    public void setFirstMovingFinishX(int value) {
        this.mazeData.setFirstMovingFinishX(value);
    }

    public void setFirstMovingFinishY(int value) {
        this.mazeData.setFirstMovingFinishY(value);
    }

    public void setFirstMovingFinishZ(int value) {
        this.mazeData.setFirstMovingFinishZ(value);
    }

    public int getPoisonPower() {
        return this.mazeData.getPoisonPower();
    }

    public void setPoisonPower(int pp) {
        this.mazeData.setPoisonPower(pp);
    }

    public void doPoisonousAmulet() {
        this.mazeData.doPoisonousAmulet();
    }

    public void doCounterpoisonAmulet() {
        this.mazeData.doCounterpoisonAmulet();
    }

    public void undoPoisonAmulets() {
        this.mazeData.undoPoisonAmulets();
    }

    public String getPoisonString() {
        return this.mazeData.getPoisonString();
    }

    public static int getMaxPoisonPower() {
        return LayeredTower.getMaxPoisonPower();
    }

    public boolean getAutoFinishEnabled() {
        return this.mazeData.getAutoFinishEnabled();
    }

    public void setAutoFinishEnabled(boolean afte) {
        this.mazeData.setAutoFinishEnabled(afte);
    }

    public void setAutoFinishThreshold(int aft) {
        this.mazeData.setAutoFinishThreshold(aft);
    }

    public boolean getAlternateAutoFinishEnabled() {
        return this.mazeData.getAlternateAutoFinishEnabled();
    }

    public void setAlternateAutoFinishEnabled(boolean aafte) {
        this.mazeData.setAlternateAutoFinishEnabled(aafte);
    }

    public void setAlternateAutoFinishThreshold(int aaft) {
        this.mazeData.setAlternateAutoFinishThreshold(aaft);
    }

    public int getVisionRadius() {
        return this.mazeData.getVisionRadius();
    }

    public void setVisionRadius(int vr) {
        this.mazeData.setVisionRadius(vr);
    }

    public void masterTrapTrigger() {
        this.mazeData.masterTrapTrigger();
    }

    public void tickTimers(final int floor) {
        this.mazeData.tickTimers(floor);
    }

    public boolean rotateRadiusClockwise(final int x, final int y, final int z,
            final int r) {
        return this.mazeData.rotateRadiusClockwise(x, y, z, r);
    }

    public boolean rotateRadiusCounterclockwise(final int x, final int y,
            final int z, final int r) {
        return this.mazeData.rotateRadiusCounterclockwise(x, y, z, r);
    }

    public boolean radialScan(final int cx, final int cy, final int r,
            final int tx, final int ty) {
        return this.mazeData.radialScan(cx, cy, r, tx, ty);
    }

    public void radialScanFreezeObjects(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanFreezeObjects(x, y, z, r);
    }

    public void radialScanFreezeGround(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanFreezeGround(x, y, z, r);
    }

    public void radialScanEnrageObjects(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanEnrageObjects(x, y, z, r);
    }

    public void radialScanBurnGround(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanBurnGround(x, y, z, r);
    }

    public void radialScanPoisonObjects(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanPoisonObjects(x, y, z, r);
    }

    public void radialScanPoisonGround(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanPoisonGround(x, y, z, r);
    }

    public void radialScanShockObjects(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanShockObjects(x, y, z, r);
    }

    public void radialScanShockGround(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanShockGround(x, y, z, r);
    }

    public void radialScanWarpObjects(final int x, final int y, final int z,
            final int l, final int r) {
        this.mazeData.radialScanWarpObjects(x, y, z, l, r);
    }

    public void radialScanShuffleObjects(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanShuffleObjects(x, y, z, r);
    }

    public void radialScanQuakeBomb(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanQuakeBomb(x, y, z, r);
    }

    public void resetVisionRadius() {
        this.mazeData.resetVisionRadius();
    }

    public void setVisionRadiusToMaximum() {
        this.mazeData.setVisionRadiusToMaximum();
    }

    public void setVisionRadiusToMinimum() {
        this.mazeData.setVisionRadiusToMinimum();
    }

    public void incrementVisionRadius() {
        this.mazeData.incrementVisionRadius();
    }

    public void decrementVisionRadius() {
        this.mazeData.decrementVisionRadius();
    }

    public void deactivateAllMovingFinishes() {
        this.mazeData.deactivateAllMovingFinishes();
    }

    public void activateFirstMovingFinish() {
        this.mazeData.activateFirstMovingFinish();
    }

    public void updateMovingBlockPosition(final int move, final int xLoc,
            final int yLoc, final MovingBlock block) {
        this.mazeData.updateMovingBlockPosition(move, xLoc, yLoc, block);
    }

    public void warpObject(AbstractMazeObject mo, int x, int y, int z, int l) {
        this.mazeData.warpObject(mo, x, y, z, l);
    }

    public final String getTimeString() {
        return this.mazeData.getTimeString();
    }

    public final boolean isTimerActive() {
        return this.mazeData.isTimerActive();
    }

    public final void activateTimer(int ticks) {
        this.mazeData.activateTimer(ticks);
    }

    public final void deactivateTimer() {
        this.mazeData.deactivateTimer();
    }

    public final void resetTimer() {
        this.mazeData.resetTimer();
    }

    public final int getTimerValue() {
        return this.mazeData.getTimerValue();
    }

    public final void extendTimerByInitialValue() {
        this.mazeData.extendTimerByInitialValue();
    }

    public final void extendTimerByInitialValueTripled() {
        this.mazeData.extendTimerByInitialValueTripled();
    }

    public final void extendTimerByInitialValueDoubled() {
        this.mazeData.extendTimerByInitialValueDoubled();
    }

    public int getExploreRadius() {
        return this.mazeData.getExploreRadius();
    }

    public void setExploreRadius(int newER) {
        this.mazeData.setExploreRadius(newER);
    }

    public int getVisionMode() {
        return this.mazeData.getVisionMode();
    }

    public void setVisionMode(int newVM) {
        this.mazeData.setVisionMode(newVM);
    }

    public void resetVisibleSquares() {
        this.mazeData.resetVisibleSquares();
    }

    public void updateVisibleSquares(int xp, int yp, int zp) {
        this.mazeData.updateVisibleSquares(xp, yp, zp);
    }

    public int getAutoFinishThreshold() {
        return this.mazeData.getAutoFinishThreshold();
    }

    public int getAlternateAutoFinishThreshold() {
        return this.mazeData.getAlternateAutoFinishThreshold();
    }

    public int getNextLevel() {
        return this.mazeData.getNextLevel();
    }

    public boolean useOffset() {
        return this.mazeData.useOffset();
    }

    public void setUseOffset(boolean uo) {
        this.mazeData.setUseOffset(uo);
    }

    public void setNextLevel(int nl) {
        this.mazeData.setNextLevel(nl);
    }

    public void setNextLevelOffset(int nlo) {
        this.mazeData.setNextLevelOffset(nlo);
    }

    public int getAlternateNextLevel() {
        return this.mazeData.getAlternateNextLevel();
    }

    public boolean useAlternateOffset() {
        return this.mazeData.useAlternateOffset();
    }

    public void setUseAlternateOffset(boolean uao) {
        this.mazeData.setUseAlternateOffset(uao);
    }

    public void setAlternateNextLevel(int anl) {
        this.mazeData.setAlternateNextLevel(anl);
    }

    public void setAlternateNextLevelOffset(int anlo) {
        this.mazeData.setAlternateNextLevelOffset(anlo);
    }

    public int getActiveLevelNumber() {
        return this.activeLevel;
    }

    public void switchLevel(int level) {
        this.switchLevelInternal(level);
    }

    public void switchLevelOffset(int level) {
        this.switchLevelInternal(this.activeLevel + level);
    }

    private void switchLevelInternal(int level) {
        if (this.activeLevel != level) {
            if (this.mazeData != null) {
                try (XDataWriter writer = this.getLevelWriter()) {
                    // Save old level
                    this.writeMazeLevel(writer);
                } catch (IOException io) {
                    // Ignore
                }
            }
            this.activeLevel = level;
            try (XDataReader reader = this.getLevelReader()) {
                // Load new level
                this.readMazeLevel(reader);
            } catch (IOException io) {
                // Ignore
            }
        }
    }

    public boolean doesLevelExist(int level) {
        return (level < this.levelCount && level >= 0);
    }

    public boolean doesLevelExistOffset(int level) {
        return (this.activeLevel + level < this.levelCount && this.activeLevel
                + level >= 0);
    }

    public void cutLevel() {
        if (this.levelCount > 1) {
            this.clipboard = this.mazeData;
            this.removeLevel();
        }
    }

    public void copyLevel() {
        this.clipboard = this.mazeData.clone();
    }

    public void pasteLevel() {
        if (this.clipboard != null) {
            this.mazeData = this.clipboard.clone();
            MazeRunnerII.getApplication().getMazeManager().setDirty(true);
        }
    }

    public boolean isPasteBlocked() {
        return this.clipboard == null;
    }

    public boolean isCutBlocked() {
        return this.levelCount <= 1;
    }

    public boolean insertLevelFromClipboard() {
        if (this.levelCount < Maze.MAX_LEVELS) {
            this.mazeData = this.clipboard.clone();
            this.levelCount++;
            return true;
        } else {
            return false;
        }
    }

    public boolean addLevel(final int rows, final int cols, final int floors) {
        if (this.levelCount < Maze.MAX_LEVELS) {
            if (this.mazeData != null) {
                try (XDataWriter writer = this.getLevelWriter()) {
                    // Save old level
                    this.writeMazeLevel(writer);
                } catch (IOException io) {
                    // Ignore
                }
            }
            this.mazeData = new LayeredTower(rows, cols, floors);
            this.levelCount++;
            this.activeLevel = this.levelCount - 1;
            return true;
        } else {
            return false;
        }
    }

    public boolean removeLevel() {
        if (this.levelCount > 1) {
            if (this.activeLevel >= 1 && this.activeLevel <= this.levelCount) {
                this.mazeData = null;
                // Delete file corresponding to current level
                boolean delSuccess = this.getLevelFile(this.activeLevel)
                        .delete();
                if (!delSuccess) {
                    return false;
                }
                // Shift all higher-numbered levels down
                for (int x = this.activeLevel; x < this.levelCount - 1; x++) {
                    File sourceLocation = this.getLevelFile(x + 1);
                    File targetLocation = this.getLevelFile(x);
                    try {
                        DirectoryUtilities.moveFile(sourceLocation,
                                targetLocation);
                    } catch (IOException io) {
                        // Ignore
                    }
                }
                // Load level 1
                this.switchLevel(0);
                this.levelCount--;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean hasNote(int x, int y, int z) {
        return this.mazeData.hasNote(x, y, z);
    }

    public void createNote(int x, int y, int z) {
        this.mazeData.createNote(x, y, z);
    }

    public MazeNote getNote(int x, int y, int z) {
        return this.mazeData.getNote(x, y, z);
    }

    public AbstractMazeObject getCell(final int row, final int col,
            final int floor, final int extra) {
        return this.mazeData.getCell(row, col, floor, extra);
    }

    public AbstractMazeObject getBattleCell(final int row, final int col) {
        return this.mazeData.getCell(row, col, 0, MazeConstants.LAYER_OBJECT);
    }

    public AbstractMazeObject getBattleGround(final int row, final int col) {
        return this.mazeData.getCell(row, col, 0, MazeConstants.LAYER_GROUND);
    }

    public int getStartRow() {
        return this.mazeData.getStartRow();
    }

    public int getStartColumn() {
        return this.mazeData.getStartColumn();
    }

    public int getPlayerLocationX() {
        return this.mazeData.getPlayerRow();
    }

    public int getPlayerLocationY() {
        return this.mazeData.getPlayerColumn();
    }

    public int getPlayerLocationZ() {
        return this.mazeData.getPlayerFloor();
    }

    public int getPlayerLocationW() {
        return this.locW;
    }

    public int getStartLevel() {
        return this.startW;
    }

    public int getRows() {
        return this.mazeData.getRows();
    }

    public int getColumns() {
        return this.mazeData.getColumns();
    }

    public int getFloors() {
        return this.mazeData.getFloors();
    }

    public int getLevels() {
        return this.levelCount;
    }

    public boolean doesPlayerExist() {
        return this.mazeData.doesPlayerExist();
    }

    public void findAllMatchingObjectsAndDecay(final AbstractMazeObject o) {
        this.mazeData.findAllMatchingObjectsAndDecay(o);
    }

    public void findAllObjectPairsAndSwap(final AbstractMazeObject o1,
            final AbstractMazeObject o2) {
        this.mazeData.findAllObjectPairsAndSwap(o1, o2);
    }

    public void resize(final int x, final int y, final int z) {
        this.mazeData.resize(x, y, z);
    }

    public boolean isSquareVisible(int x1, int y1, int x2, int y2) {
        return this.mazeData.isSquareVisible(x1, y1, x2, y2);
    }

    public void setBattleCell(final AbstractMazeObject mo, final int row,
            final int col) {
        this.mazeData.setCell(mo, row, col, 0, MazeConstants.LAYER_OBJECT);
    }

    public void setCell(final AbstractMazeObject mo, final int row,
            final int col, final int floor, final int extra) {
        this.mazeData.setCell(mo, row, col, floor, extra);
    }

    public void setStartRow(final int newStartRow) {
        this.mazeData.setStartRow(newStartRow);
    }

    public void setStartColumn(final int newStartColumn) {
        this.mazeData.setStartColumn(newStartColumn);
    }

    public void setStartFloor(final int newStartFloor) {
        this.mazeData.setStartFloor(newStartFloor);
    }

    public void setStartLevel(final int newStartLevel) {
        this.startW = newStartLevel;
    }

    public void savePlayerLocation() {
        this.saveW = this.locW;
        this.mazeData.savePlayerLocation();
    }

    public void restorePlayerLocation() {
        this.locW = this.saveW;
        this.mazeData.restorePlayerLocation();
    }

    public void setPlayerToStart() {
        this.mazeData.setPlayerToStart();
    }

    public void setPlayerLocationX(final int newPlayerRow) {
        this.mazeData.setPlayerRow(newPlayerRow);
    }

    public void setPlayerLocationY(final int newPlayerColumn) {
        this.mazeData.setPlayerColumn(newPlayerColumn);
    }

    public void setPlayerLocationZ(final int newPlayerFloor) {
        this.mazeData.setPlayerFloor(newPlayerFloor);
    }

    public void setPlayerLocationW(final int newPlayerLevel) {
        this.locW = newPlayerLevel;
    }

    public void offsetPlayerLocationX(final int newPlayerRow) {
        this.mazeData.offsetPlayerRow(newPlayerRow);
    }

    public void offsetPlayerLocationY(final int newPlayerColumn) {
        this.mazeData.offsetPlayerColumn(newPlayerColumn);
    }

    public void updateThresholds() {
        this.mazeData.updateThresholds();
    }

    public void fill() {
        AbstractMazeObject bottom = PreferencesManager.getEditorDefaultFill();
        AbstractMazeObject top = new Empty();
        this.mazeData.fill(bottom, top);
    }

    public void fillFloor(int floor) {
        AbstractMazeObject bottom = PreferencesManager.getEditorDefaultFill();
        AbstractMazeObject top = new Empty();
        this.mazeData.fillFloor(bottom, top, floor);
    }

    public void fillLevel(AbstractMazeObject bottom, AbstractMazeObject top) {
        this.mazeData.fill(bottom, top);
    }

    public void fillLevelRandomly() {
        this.mazeData.fillRandomly(this, this.activeLevel);
    }

    public void fillFloorRandomly(int z) {
        this.mazeData.fillFloorRandomly(this, z, this.activeLevel);
    }

    public void fillLevelRandomlyCustom() {
        this.mazeData.fillRandomlyCustom(this, this.activeLevel);
    }

    public void fillFloorRandomlyCustom(int z) {
        this.mazeData.fillFloorRandomlyCustom(this, z, this.activeLevel);
    }

    public void fillLayer(int layer) {
        AbstractMazeObject fillWith = PreferencesManager
                .getEditorDefaultFill(layer);
        this.mazeData.fillLayer(fillWith, layer);
    }

    public void fillFloorAndLayer(int floor, int layer) {
        AbstractMazeObject fillWith = PreferencesManager
                .getEditorDefaultFill(layer);
        this.mazeData.fillFloorAndLayer(fillWith, floor, layer);
    }

    public void fillLevelAndLayerRandomly(int layer) {
        this.mazeData.fillLayerRandomly(this, this.activeLevel, layer);
    }

    public void fillFloorAndLayerRandomly(int z, int layer) {
        this.mazeData.fillFloorAndLayerRandomly(this, z, this.activeLevel,
                layer);
    }

    public void fillLevelAndLayerRandomlyCustom(int layer) {
        this.mazeData.fillLayerRandomlyCustom(this, this.activeLevel, layer);
    }

    public void fillFloorAndLayerRandomlyCustom(int z, int layer) {
        this.mazeData.fillFloorAndLayerRandomlyCustom(this, z,
                this.activeLevel, layer);
    }

    private void fillLevelRandomlyInBattle(
            final AbstractMazeObject pass1FillBottom,
            final AbstractMazeObject pass1FillTop) {
        this.mazeData.fillRandomlyInBattle(this, this.activeLevel,
                pass1FillBottom, pass1FillTop);
    }

    public void save() {
        this.mazeData.save();
    }

    public void restore() {
        this.mazeData.restore();
    }

    public void saveStart() {
        this.savedStart[0] = this.startW;
        this.savedStart[1] = this.mazeData.getStartRow();
        this.savedStart[2] = this.mazeData.getStartColumn();
        this.savedStart[3] = this.mazeData.getStartFloor();
    }

    public void restoreStart() {
        this.startW = this.savedStart[0];
        this.mazeData.setStartRow(this.savedStart[1]);
        this.mazeData.setStartColumn(this.savedStart[2]);
        this.mazeData.setStartFloor(this.savedStart[3]);
    }

    public void hotGround(int x, int y, int z) {
        this.mazeData.hotGround(x, y, z);
    }

    public void enableHorizontalWraparound() {
        this.mazeData.enableHorizontalWraparound();
    }

    public void disableHorizontalWraparound() {
        this.mazeData.disableHorizontalWraparound();
    }

    public void enableVerticalWraparound() {
        this.mazeData.enableVerticalWraparound();
    }

    public void disableVerticalWraparound() {
        this.mazeData.disableVerticalWraparound();
    }

    public void enable3rdDimensionWraparound() {
        this.mazeData.enable3rdDimensionWraparound();
    }

    public void disable3rdDimensionWraparound() {
        this.mazeData.disable3rdDimensionWraparound();
    }

    public boolean isHorizontalWraparoundEnabled() {
        return this.mazeData.isHorizontalWraparoundEnabled();
    }

    public boolean isVerticalWraparoundEnabled() {
        return this.mazeData.isVerticalWraparoundEnabled();
    }

    public boolean is3rdDimensionWraparoundEnabled() {
        return this.mazeData.is3rdDimensionWraparoundEnabled();
    }

    public Maze readMaze() throws IOException {
        Maze m = new Maze();
        // Attach handlers
        m.setPrefixHandler(this.prefixHandler);
        m.setSuffixHandler(this.suffixHandler);
        // Make base paths the same
        m.basePath = this.basePath;
        int version = 0;
        // Create metafile reader
        try (XDataReader metaReader = new XDataReader(m.basePath
                + File.separator + "metafile.xml", "maze")) {
            // Read metafile
            version = m.readMazeMetafile(metaReader);
        } catch (IOException ioe) {
            throw ioe;
        }
        // Create data reader
        try (XDataReader dataReader = m.getLevelReader()) {
            // Read data
            m.readMazeLevel(dataReader, version);
            return m;
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    private XDataReader getLevelReader() throws IOException {
        return new XDataReader(this.basePath + File.separator + "level"
                + this.activeLevel + ".xml", "level");
    }

    private int readMazeMetafile(XDataReader reader) throws IOException {
        int ver = FormatConstants.MAZE_FORMAT_1;
        reader.readOpeningGroup(MAZE_PREFIX_GROUP);
        if (this.prefixHandler != null) {
            ver = this.prefixHandler.readPrefix(reader);
        }
        reader.readClosingGroup(MAZE_PREFIX_GROUP);
        reader.readOpeningGroup(MAZE_SETTINGS_GROUP);
        this.levelCount = reader.readInt();
        this.startW = reader.readInt();
        this.mazeTitle = reader.readString();
        this.mazeStartMessage = reader.readString();
        this.mazeEndMessage = reader.readString();
        this.locW = reader.readInt();
        this.saveW = reader.readInt();
        this.activeLevel = reader.readInt();
        for (int y = 0; y < 4; y++) {
            this.savedStart[y] = reader.readInt();
        }
        reader.readClosingGroup(MAZE_SETTINGS_GROUP);
        reader.readOpeningGroup(MAZE_SUFFIX_GROUP);
        if (this.suffixHandler != null) {
            this.suffixHandler.readSuffix(reader, ver);
        }
        reader.readClosingGroup(MAZE_SUFFIX_GROUP);
        return ver;
    }

    private void readMazeLevel(XDataReader reader) throws IOException {
        this.readMazeLevel(reader, FormatConstants.MAZE_FORMAT_LATEST);
    }

    private void readMazeLevel(XDataReader reader, int formatVersion)
            throws IOException {
        if (formatVersion == FormatConstants.MAZE_FORMAT_1) {
            this.mazeData = LayeredTower.readLayeredTowerV1(reader);
            this.mazeData.readSavedTowerState(reader, formatVersion);
        } else {
            throw new IOException("Unknown maze format version!");
        }
    }

    public Maze readLegacyMaze() throws IOException {
        Maze m = new Maze();
        // Attach handlers
        m.setLegacyPrefixHandler(this.legacyPrefixHandler);
        m.setLegacySuffixHandler(this.legacySuffixHandler);
        // Make base paths the same
        m.basePath = this.basePath;
        int version = 0;
        // Create metafile reader
        try (XLegacyDataReader metaReader = new XLegacyDataReader(m.basePath
                + File.separator + "metafile.xml", "maze")) {
            // Read metafile
            version = m.readMazeLegacyMetafile(metaReader);
            // Create data reader
            try (XLegacyDataReader dataReader = m.getLegacyLevelReader()) {
                // Read data
                m.readLegacyMazeLevel(dataReader, version);
                return m;
            }
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    private XLegacyDataReader getLegacyLevelReader() throws IOException {
        return new XLegacyDataReader(this.basePath + File.separator + "level"
                + this.activeLevel + ".xml", "level");
    }

    private int readMazeLegacyMetafile(XLegacyDataReader reader)
            throws IOException {
        int ver = LegacyFormatConstants.LEGACY_MAZE_FORMAT_1;
        if (this.legacyPrefixHandler != null) {
            ver = this.legacyPrefixHandler.readPrefix(reader);
        }
        int levels = reader.readInt();
        this.levelCount = levels;
        this.startW = reader.readInt();
        this.mazeTitle = reader.readString();
        this.mazeStartMessage = reader.readString();
        this.mazeEndMessage = reader.readString();
        if (this.legacySuffixHandler != null) {
            this.legacySuffixHandler.readSuffix(reader, ver);
        }
        return ver;
    }

    private void readLegacyMazeLevel(XLegacyDataReader reader, int formatVersion)
            throws IOException {
        if (formatVersion == LegacyFormatConstants.LEGACY_MAZE_FORMAT_1) {
            this.mazeData = LayeredTower.readLegacyLayeredTowerV1(reader);
            this.mazeData.readLegacySavedTowerState(reader, formatVersion);
        } else if (formatVersion == LegacyFormatConstants.LEGACY_MAZE_FORMAT_2) {
            this.mazeData = LayeredTower.readLegacyLayeredTowerV2(reader);
            this.mazeData.readLegacySavedTowerState(reader, formatVersion);
        } else {
            throw new IOException("Unknown maze format version!");
        }
    }

    private File getLevelFile(int level) {
        return new File(this.basePath + File.separator + level + ".level");
    }

    public void writeMaze() throws IOException {
        try {
            // Create metafile writer
            try (XDataWriter metaWriter = new XDataWriter(this.basePath
                    + File.separator + "metafile.xml", "maze")) {
                // Write metafile
                this.writeMazeMetafile(metaWriter);
            }
            // Create data writer
            try (XDataWriter dataWriter = this.getLevelWriter()) {
                // Write data
                this.writeMazeLevel(dataWriter);
            }
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    private XDataWriter getLevelWriter() throws IOException {
        return new XDataWriter(this.basePath + File.separator + "level"
                + this.activeLevel + ".xml", "level");
    }

    private void writeMazeMetafile(XDataWriter writer) throws IOException {
        writer.writeOpeningGroup(MAZE_PREFIX_GROUP);
        if (this.prefixHandler != null) {
            this.prefixHandler.writePrefix(writer);
        }
        writer.writeClosingGroup(MAZE_PREFIX_GROUP);
        writer.writeOpeningGroup(MAZE_SETTINGS_GROUP);
        writer.writeInt(this.levelCount);
        writer.writeInt(this.startW);
        writer.writeString(this.mazeTitle);
        writer.writeString(this.mazeStartMessage);
        writer.writeString(this.mazeEndMessage);
        writer.writeInt(this.locW);
        writer.writeInt(this.saveW);
        writer.writeInt(this.activeLevel);
        for (int y = 0; y < 4; y++) {
            writer.writeInt(this.savedStart[y]);
        }
        writer.writeClosingGroup(MAZE_SETTINGS_GROUP);
        writer.writeOpeningGroup(MAZE_SUFFIX_GROUP);
        if (this.suffixHandler != null) {
            this.suffixHandler.writeSuffix(writer);
        }
        writer.writeClosingGroup(MAZE_SUFFIX_GROUP);
    }

    private void writeMazeLevel(XDataWriter writer) throws IOException {
        // Write the level
        this.mazeData.writeLayeredTower(writer);
        this.mazeData.writeSavedTowerState(writer);
    }
}
