/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze;

import java.io.File;
import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.maze.objects.Empty;
import com.puttysoftware.fantastlex.maze.objects.Monster;
import com.puttysoftware.fantastlex.maze.objects.MovingBlock;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectList;
import com.puttysoftware.fantastlex.prefs.PreferencesManager;
import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.xio.DirectoryUtilities;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

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
    private PrefixIO prefixHandler;
    private SuffixIO suffixHandler;
    private final int[] savedStart;
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
        this.mazeTitle = "Untitled Maze";
        this.mazeStartMessage = "Let's Solve The Maze!";
        this.mazeEndMessage = "Maze Solved!";
        this.savedStart = new int[4];
        final long random = new RandomRange(0, Long.MAX_VALUE).generateLong();
        final String randomID = Long.toHexString(random);
        this.basePath = System.getProperty("java.io.tmpdir") + File.separator
                + "FantastleX" + File.separator + randomID + ".maze";
        final File base = new File(this.basePath);
        final boolean success = base.mkdirs();
        if (!success) {
            CommonDialogs.showErrorDialog(
                    "Maze temporary folder creation failed!", "FantastleX");
        }
    }

    // Static methods
    public static String getMazeTempFolder() {
        return System.getProperty("java.io.tmpdir") + File.separator
                + "FantastleX";
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
        final Maze temp = new Maze();
        temp.addLevel(FantastleX.getBattleMazeSize(),
                FantastleX.getBattleMazeSize(), 1);
        final MazeObjectList list = new MazeObjectList();
        final AbstractMazeObject[] glo = list.getAllGroundLayerObjects();
        final RandomRange gen = new RandomRange(0, glo.length - 1);
        final AbstractMazeObject rand = glo[gen.generate()];
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

    public void postBattle(final Monster m, final int xLoc, final int yLoc,
            final boolean player) {
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

    public void setPrefixHandler(final PrefixIO xph) {
        this.prefixHandler = xph;
    }

    public void setSuffixHandler(final SuffixIO xsh) {
        this.suffixHandler = xsh;
    }

    public String getMazeTitle() {
        return this.mazeTitle;
    }

    public void setMazeTitle(final String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null!");
        }
        this.mazeTitle = title;
    }

    public String getMazeStartMessage() {
        return this.mazeStartMessage;
    }

    public void setMazeStartMessage(final String msg) {
        if (msg == null) {
            throw new IllegalArgumentException("Message cannot be null!");
        }
        this.mazeStartMessage = msg;
    }

    public String getMazeEndMessage() {
        return this.mazeEndMessage;
    }

    public void setMazeEndMessage(final String msg) {
        if (msg == null) {
            throw new IllegalArgumentException("Message cannot be null!");
        }
        this.mazeEndMessage = msg;
    }

    public String getLevelTitle() {
        return this.mazeData.getLevelTitle();
    }

    public void setLevelTitle(final String title) {
        this.mazeData.setLevelTitle(title);
    }

    public String getLevelStartMessage() {
        return this.mazeData.getLevelStartMessage();
    }

    public void setLevelStartMessage(final String msg) {
        this.mazeData.setLevelStartMessage(msg);
    }

    public String getLevelEndMessage() {
        return this.mazeData.getLevelEndMessage();
    }

    public void setLevelEndMessage(final String msg) {
        this.mazeData.setLevelEndMessage(msg);
    }

    public int getFinishMoveSpeed() {
        return this.mazeData.getFinishMoveSpeed();
    }

    public void setFinishMoveSpeed(final int value) {
        this.mazeData.setFinishMoveSpeed(value);
    }

    public void setFirstMovingFinishX(final int value) {
        this.mazeData.setFirstMovingFinishX(value);
    }

    public void setFirstMovingFinishY(final int value) {
        this.mazeData.setFirstMovingFinishY(value);
    }

    public void setFirstMovingFinishZ(final int value) {
        this.mazeData.setFirstMovingFinishZ(value);
    }

    public int getPoisonPower() {
        return this.mazeData.getPoisonPower();
    }

    public void setPoisonPower(final int pp) {
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

    public void setAutoFinishEnabled(final boolean afte) {
        this.mazeData.setAutoFinishEnabled(afte);
    }

    public void setAutoFinishThreshold(final int aft) {
        this.mazeData.setAutoFinishThreshold(aft);
    }

    public boolean getAlternateAutoFinishEnabled() {
        return this.mazeData.getAlternateAutoFinishEnabled();
    }

    public void setAlternateAutoFinishEnabled(final boolean aafte) {
        this.mazeData.setAlternateAutoFinishEnabled(aafte);
    }

    public void setAlternateAutoFinishThreshold(final int aaft) {
        this.mazeData.setAlternateAutoFinishThreshold(aaft);
    }

    public int getVisionRadius() {
        return this.mazeData.getVisionRadius();
    }

    public void setVisionRadius(final int vr) {
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

    public void warpObject(final AbstractMazeObject mo, final int x,
            final int y, final int z, final int l) {
        this.mazeData.warpObject(mo, x, y, z, l);
    }

    public final String getTimeString() {
        return this.mazeData.getTimeString();
    }

    public final boolean isTimerActive() {
        return this.mazeData.isTimerActive();
    }

    public final void activateTimer(final int ticks) {
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

    public void setExploreRadius(final int newER) {
        this.mazeData.setExploreRadius(newER);
    }

    public int getVisionMode() {
        return this.mazeData.getVisionMode();
    }

    public void setVisionMode(final int newVM) {
        this.mazeData.setVisionMode(newVM);
    }

    public void resetVisibleSquares() {
        this.mazeData.resetVisibleSquares();
    }

    public void updateVisibleSquares(final int xp, final int yp, final int zp) {
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

    public void setUseOffset(final boolean uo) {
        this.mazeData.setUseOffset(uo);
    }

    public void setNextLevel(final int nl) {
        this.mazeData.setNextLevel(nl);
    }

    public void setNextLevelOffset(final int nlo) {
        this.mazeData.setNextLevelOffset(nlo);
    }

    public int getAlternateNextLevel() {
        return this.mazeData.getAlternateNextLevel();
    }

    public boolean useAlternateOffset() {
        return this.mazeData.useAlternateOffset();
    }

    public void setUseAlternateOffset(final boolean uao) {
        this.mazeData.setUseAlternateOffset(uao);
    }

    public void setAlternateNextLevel(final int anl) {
        this.mazeData.setAlternateNextLevel(anl);
    }

    public void setAlternateNextLevelOffset(final int anlo) {
        this.mazeData.setAlternateNextLevelOffset(anlo);
    }

    public int getActiveLevelNumber() {
        return this.activeLevel;
    }

    public void switchLevel(final int level) {
        this.switchLevelInternal(level);
    }

    public void switchLevelOffset(final int level) {
        this.switchLevelInternal(this.activeLevel + level);
    }

    private void switchLevelInternal(final int level) {
        if (this.activeLevel != level) {
            if (this.mazeData != null) {
                try (XDataWriter writer = this.getLevelWriter()) {
                    // Save old level
                    this.writeMazeLevel(writer);
                } catch (final IOException io) {
                    // Ignore
                }
            }
            this.activeLevel = level;
            try (XDataReader reader = this.getLevelReader()) {
                // Load new level
                this.readMazeLevel(reader);
            } catch (final IOException io) {
                // Ignore
            }
        }
    }

    public boolean doesLevelExist(final int level) {
        return level < this.levelCount && level >= 0;
    }

    public boolean doesLevelExistOffset(final int level) {
        return this.activeLevel + level < this.levelCount
                && this.activeLevel + level >= 0;
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
            FantastleX.getApplication().getMazeManager().setDirty(true);
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
                } catch (final IOException io) {
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
                final boolean delSuccess = this.getLevelFile(this.activeLevel)
                        .delete();
                if (!delSuccess) {
                    return false;
                }
                // Shift all higher-numbered levels down
                for (int x = this.activeLevel; x < this.levelCount - 1; x++) {
                    final File sourceLocation = this.getLevelFile(x + 1);
                    final File targetLocation = this.getLevelFile(x);
                    try {
                        DirectoryUtilities.moveFile(sourceLocation,
                                targetLocation);
                    } catch (final IOException io) {
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

    public boolean hasNote(final int x, final int y, final int z) {
        return this.mazeData.hasNote(x, y, z);
    }

    public void createNote(final int x, final int y, final int z) {
        this.mazeData.createNote(x, y, z);
    }

    public MazeNote getNote(final int x, final int y, final int z) {
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

    public boolean isSquareVisible(final int x1, final int y1, final int x2,
            final int y2) {
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
        final AbstractMazeObject bottom = PreferencesManager
                .getEditorDefaultFill();
        final AbstractMazeObject top = new Empty();
        this.mazeData.fill(bottom, top);
    }

    public void fillFloor(final int floor) {
        final AbstractMazeObject bottom = PreferencesManager
                .getEditorDefaultFill();
        final AbstractMazeObject top = new Empty();
        this.mazeData.fillFloor(bottom, top, floor);
    }

    public void fillLevel(final AbstractMazeObject bottom,
            final AbstractMazeObject top) {
        this.mazeData.fill(bottom, top);
    }

    public void fillLevelRandomly() {
        this.mazeData.fillRandomly(this, this.activeLevel);
    }

    public void fillFloorRandomly(final int z) {
        this.mazeData.fillFloorRandomly(this, z, this.activeLevel);
    }

    public void fillLevelRandomlyCustom() {
        this.mazeData.fillRandomlyCustom(this, this.activeLevel);
    }

    public void fillFloorRandomlyCustom(final int z) {
        this.mazeData.fillFloorRandomlyCustom(this, z, this.activeLevel);
    }

    public void fillLayer(final int layer) {
        final AbstractMazeObject fillWith = PreferencesManager
                .getEditorDefaultFill(layer);
        this.mazeData.fillLayer(fillWith, layer);
    }

    public void fillFloorAndLayer(final int floor, final int layer) {
        final AbstractMazeObject fillWith = PreferencesManager
                .getEditorDefaultFill(layer);
        this.mazeData.fillFloorAndLayer(fillWith, floor, layer);
    }

    public void fillLevelAndLayerRandomly(final int layer) {
        this.mazeData.fillLayerRandomly(this, this.activeLevel, layer);
    }

    public void fillFloorAndLayerRandomly(final int z, final int layer) {
        this.mazeData.fillFloorAndLayerRandomly(this, z, this.activeLevel,
                layer);
    }

    public void fillLevelAndLayerRandomlyCustom(final int layer) {
        this.mazeData.fillLayerRandomlyCustom(this, this.activeLevel, layer);
    }

    public void fillFloorAndLayerRandomlyCustom(final int z, final int layer) {
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

    public void hotGround(final int x, final int y, final int z) {
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
        final Maze m = new Maze();
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
        } catch (final IOException ioe) {
            throw ioe;
        }
        // Create data reader
        try (XDataReader dataReader = m.getLevelReader()) {
            // Read data
            m.readMazeLevel(dataReader, version);
            return m;
        } catch (final IOException ioe) {
            throw ioe;
        }
    }

    private XDataReader getLevelReader() throws IOException {
        return new XDataReader(this.basePath + File.separator + "level"
                + this.activeLevel + ".xml", "level");
    }

    private int readMazeMetafile(final XDataReader reader) throws IOException {
        int ver = FormatConstants.MAZE_FORMAT_1;
        reader.readOpeningGroup(Maze.MAZE_PREFIX_GROUP);
        if (this.prefixHandler != null) {
            ver = this.prefixHandler.readPrefix(reader);
        }
        reader.readClosingGroup(Maze.MAZE_PREFIX_GROUP);
        reader.readOpeningGroup(Maze.MAZE_SETTINGS_GROUP);
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
        reader.readClosingGroup(Maze.MAZE_SETTINGS_GROUP);
        reader.readOpeningGroup(Maze.MAZE_SUFFIX_GROUP);
        if (this.suffixHandler != null) {
            this.suffixHandler.readSuffix(reader, ver);
        }
        reader.readClosingGroup(Maze.MAZE_SUFFIX_GROUP);
        return ver;
    }

    private void readMazeLevel(final XDataReader reader) throws IOException {
        this.readMazeLevel(reader, FormatConstants.MAZE_FORMAT_LATEST);
    }

    private void readMazeLevel(final XDataReader reader, final int formatVersion)
            throws IOException {
        if (formatVersion == FormatConstants.MAZE_FORMAT_1) {
            this.mazeData = LayeredTower.readLayeredTowerV1(reader);
            this.mazeData.readSavedTowerState(reader, formatVersion);
        } else {
            throw new IOException("Unknown maze format version!");
        }
    }

    private File getLevelFile(final int level) {
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
        } catch (final IOException ioe) {
            throw ioe;
        }
    }

    private XDataWriter getLevelWriter() throws IOException {
        return new XDataWriter(this.basePath + File.separator + "level"
                + this.activeLevel + ".xml", "level");
    }

    private void writeMazeMetafile(final XDataWriter writer) throws IOException {
        writer.writeOpeningGroup(Maze.MAZE_PREFIX_GROUP);
        if (this.prefixHandler != null) {
            this.prefixHandler.writePrefix(writer);
        }
        writer.writeClosingGroup(Maze.MAZE_PREFIX_GROUP);
        writer.writeOpeningGroup(Maze.MAZE_SETTINGS_GROUP);
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
        writer.writeClosingGroup(Maze.MAZE_SETTINGS_GROUP);
        writer.writeOpeningGroup(Maze.MAZE_SUFFIX_GROUP);
        if (this.suffixHandler != null) {
            this.suffixHandler.writeSuffix(writer);
        }
        writer.writeClosingGroup(Maze.MAZE_SUFFIX_GROUP);
    }

    private void writeMazeLevel(final XDataWriter writer) throws IOException {
        // Write the level
        this.mazeData.writeLayeredTower(writer);
        this.mazeData.writeSavedTowerState(writer);
    }
}
