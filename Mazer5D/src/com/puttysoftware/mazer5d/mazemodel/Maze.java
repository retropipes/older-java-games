/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2020 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.mazemodel;

import java.io.File;
import java.io.IOException;

import com.puttysoftware.fileutils.FileUtilities;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.compatibility.files.TempDirCleanup;
import com.puttysoftware.mazer5d.files.PrefixIO;
import com.puttysoftware.mazer5d.files.SuffixIO;
import com.puttysoftware.mazer5d.files.io.XDataReader;
import com.puttysoftware.mazer5d.files.io.XDataWriter;
import com.puttysoftware.mazer5d.files.versions.MazeVersion;
import com.puttysoftware.mazer5d.objectmodel.GameObjects;
import com.puttysoftware.mazer5d.objectmodel.MazeObjectModel;
import com.puttysoftware.randomrange.RandomLongRange;

class Maze implements MazeModel {
    // Properties
    private MazeData mazeData;
    private MazeData clipboard;
    private int startW;
    private int levelCount;
    private int activeLevel;
    private int currHP;
    private int maxHP;
    private String mazeTitle;
    private String mazeStartMessage;
    private String mazeEndMessage;
    private String basePath;
    private PrefixIO xmlPrefixHandler;
    private SuffixIO xmlSuffixHandler;
    private final int[] savedStart;
    private static final int FULL_HEAL_PERCENTAGE = 100;
    private static final int MIN_LEVELS = 1;
    private static final int MAX_LEVELS = Integer.MAX_VALUE;

    // Constructors
    public Maze() {
        this.mazeData = null;
        this.clipboard = null;
        this.levelCount = 0;
        this.startW = 0;
        this.activeLevel = 0;
        this.currHP = 1000;
        this.maxHP = 1000;
        this.xmlPrefixHandler = null;
        this.xmlSuffixHandler = null;
        this.mazeTitle = "Untitled Maze";
        this.mazeStartMessage = "Let's Solve The Maze!";
        this.mazeEndMessage = "Maze Solved!";
        this.savedStart = new int[4];
        final long random = new RandomLongRange(0, Long.MAX_VALUE).generate();
        final String randomID = Long.toHexString(random);
        this.basePath = System.getProperty("java.io.tmpdir") + File.separator
                + "Mazer5D" + File.separator + randomID + ".maze";
        final File base = new File(this.basePath);
        base.mkdirs();
    }

    // Cleanup hook
    static {
        Runtime.getRuntime().addShutdownHook(new TempDirCleanup());
    }

    // Static methods
    public static String getMazeTempFolder() {
        return System.getProperty("java.io.tmpdir") + File.separator
                + "Mazer5D";
    }

    public static int getMinLevels() {
        return Maze.MIN_LEVELS;
    }

    public static int getMaxLevels() {
        return Maze.MAX_LEVELS;
    }

    public static int getMinFloors() {
        return MazeData.getMinFloors();
    }

    public static int getMaxFloors() {
        return MazeData.getMaxFloors();
    }

    public static int getMinColumns() {
        return MazeData.getMinColumns();
    }

    public static int getMaxColumns() {
        return MazeData.getMaxColumns();
    }

    public static int getMinRows() {
        return MazeData.getMinRows();
    }

    public static int getMaxRows() {
        return MazeData.getMaxRows();
    }

    // Methods
    @Override
    public int getCurrentHP() {
        return this.currHP;
    }

    @Override
    public int getMaximumHP() {
        return this.maxHP;
    }

    @Override
    public String getHPString() {
        return "Health: " + this.currHP + "/" + this.maxHP;
    }

    @Override
    public void setMaximumHP(final int max) {
        this.maxHP = max;
        if (this.currHP > this.maxHP) {
            this.currHP = this.maxHP;
        }
    }

    @Override
    public boolean isAlive() {
        return this.currHP > 0;
    }

    @Override
    public void doPoisonDamage() {
        this.doDamage(1);
    }

    @Override
    public void doDamage(final int amount) {
        this.currHP -= amount;
        if (this.currHP < 0) {
            this.currHP = 0;
        }
        if (this.currHP > this.maxHP) {
            this.currHP = this.maxHP;
        }
    }

    @Override
    public void doDamagePercentage(final int percent) {
        int fP = percent;
        if (fP > Maze.FULL_HEAL_PERCENTAGE) {
            fP = Maze.FULL_HEAL_PERCENTAGE;
        }
        if (fP < 0) {
            fP = 0;
        }
        final double fPMultiplier = fP / (double) Maze.FULL_HEAL_PERCENTAGE;
        int modValue = (int) (this.maxHP * fPMultiplier);
        if (modValue <= 0) {
            modValue = 1;
        }
        this.currHP -= modValue;
        if (this.currHP < 0) {
            this.currHP = 0;
        }
        if (this.currHP > this.maxHP) {
            this.currHP = this.maxHP;
        }
    }

    @Override
    public void heal(final int amount) {
        this.currHP += amount;
        if (this.currHP < 0) {
            this.currHP = 0;
        }
        if (this.currHP > this.maxHP) {
            this.currHP = this.maxHP;
        }
    }

    @Override
    public void healFully() {
        this.currHP = this.maxHP;
    }

    @Override
    public void healPercentage(final int percent) {
        int fP = percent;
        if (fP > Maze.FULL_HEAL_PERCENTAGE) {
            fP = Maze.FULL_HEAL_PERCENTAGE;
        }
        if (fP < 0) {
            fP = 0;
        }
        final double fPMultiplier = fP / (double) Maze.FULL_HEAL_PERCENTAGE;
        final int difference = this.maxHP - this.getCurrentHP();
        int modValue = (int) (difference * fPMultiplier);
        if (modValue <= 0) {
            modValue = 1;
        }
        this.currHP += modValue;
        if (this.currHP < 0) {
            this.currHP = 0;
        }
        if (this.currHP > this.maxHP) {
            this.currHP = this.maxHP;
        }
    }

    @Override
    public String getBasePath() {
        return this.basePath;
    }

    @Override
    public void setPrefixHandler(final PrefixIO xph) {
        this.xmlPrefixHandler = xph;
    }

    @Override
    public void setSuffixHandler(final SuffixIO xsh) {
        this.xmlSuffixHandler = xsh;
    }

    @Override
    public String getMazeTitle() {
        return this.mazeTitle;
    }

    @Override
    public void setMazeTitle(final String title) {
        if (title == null) {
            throw new NullPointerException("Title cannot be null!");
        }
        this.mazeTitle = title;
    }

    @Override
    public String getMazeStartMessage() {
        return this.mazeStartMessage;
    }

    @Override
    public void setMazeStartMessage(final String msg) {
        if (msg == null) {
            throw new NullPointerException("Message cannot be null!");
        }
        this.mazeStartMessage = msg;
    }

    @Override
    public String getMazeEndMessage() {
        return this.mazeEndMessage;
    }

    @Override
    public void setMazeEndMessage(final String msg) {
        if (msg == null) {
            throw new NullPointerException("Message cannot be null!");
        }
        this.mazeEndMessage = msg;
    }

    @Override
    public String getLevelTitle() {
        return this.mazeData.getLevelTitle();
    }

    @Override
    public void setLevelTitle(final String title) {
        this.mazeData.setLevelTitle(title);
    }

    @Override
    public String getLevelStartMessage() {
        return this.mazeData.getLevelStartMessage();
    }

    @Override
    public void setLevelStartMessage(final String msg) {
        this.mazeData.setLevelStartMessage(msg);
    }

    @Override
    public String getLevelEndMessage() {
        return this.mazeData.getLevelEndMessage();
    }

    @Override
    public void setLevelEndMessage(final String msg) {
        this.mazeData.setLevelEndMessage(msg);
    }

    @Override
    public int getExploreRadius() {
        return this.mazeData.getExploreRadius();
    }

    @Override
    public void setExploreRadius(final int newER) {
        this.mazeData.setExploreRadius(newER);
    }

    @Override
    public void addVisionMode(final int newMode) {
        this.mazeData.addVisionMode(newMode);
    }

    @Override
    public void removeVisionMode(final int newMode) {
        this.mazeData.removeVisionMode(newMode);
    }

    @Override
    public void updateVisibleSquares(final int xp, final int yp, final int zp) {
        this.mazeData.updateVisibleSquares(xp, yp, zp);
    }

    @Override
    public int getFinishMoveSpeed() {
        return this.mazeData.getFinishMoveSpeed();
    }

    @Override
    public void setFinishMoveSpeed(final int value) {
        this.mazeData.setFinishMoveSpeed(value);
    }

    @Override
    public void setFirstMovingFinishX(final int value) {
        this.mazeData.setFirstMovingFinishX(value);
    }

    @Override
    public void setFirstMovingFinishY(final int value) {
        this.mazeData.setFirstMovingFinishY(value);
    }

    @Override
    public void setFirstMovingFinishZ(final int value) {
        this.mazeData.setFirstMovingFinishZ(value);
    }

    @Override
    public int getPoisonPower() {
        return this.mazeData.getPoisonPower();
    }

    @Override
    public void setPoisonPower(final int pp) {
        this.mazeData.setPoisonPower(pp);
    }

    @Override
    public void doPoisonousAmulet() {
        this.mazeData.doPoisonousAmulet();
    }

    @Override
    public void doCounterpoisonAmulet() {
        this.mazeData.doCounterpoisonAmulet();
    }

    @Override
    public void undoPoisonAmulets() {
        this.mazeData.undoPoisonAmulets();
    }

    @Override
    public String getPoisonString() {
        return this.mazeData.getPoisonString();
    }

    public static int getMaxPoisonPower() {
        return MazeData.getMaxPoisonPower();
    }

    @Override
    public boolean getAutoFinishThresholdEnabled() {
        return this.mazeData.getAutoFinishThresholdEnabled();
    }

    @Override
    public void setAutoFinishThresholdEnabled(final boolean afte) {
        this.mazeData.setAutoFinishThresholdEnabled(afte);
    }

    @Override
    public int getAutoFinishThreshold() {
        return this.mazeData.getAutoFinishThreshold();
    }

    @Override
    public void setAutoFinishThreshold(final int aft) {
        this.mazeData.setAutoFinishThreshold(aft);
    }

    @Override
    public int getAlternateAutoFinishThreshold() {
        return this.mazeData.getAlternateAutoFinishThreshold();
    }

    @Override
    public void setAlternateAutoFinishThreshold(final int aaft) {
        this.mazeData.setAlternateAutoFinishThreshold(aaft);
    }

    @Override
    public int getNextLevel() {
        return this.mazeData.getNextLevel();
    }

    @Override
    public boolean useOffset() {
        return this.mazeData.useOffset();
    }

    @Override
    public void setUseOffset(final boolean uo) {
        this.mazeData.setUseOffset(uo);
    }

    @Override
    public void setNextLevel(final int nl) {
        this.mazeData.setNextLevel(nl);
    }

    @Override
    public void setNextLevelOffset(final int nlo) {
        this.mazeData.setNextLevelOffset(nlo);
    }

    @Override
    public int getAlternateNextLevel() {
        return this.mazeData.getAlternateNextLevel();
    }

    @Override
    public boolean useAlternateOffset() {
        return this.mazeData.useAlternateOffset();
    }

    @Override
    public void setUseAlternateOffset(final boolean uao) {
        this.mazeData.setUseAlternateOffset(uao);
    }

    @Override
    public void setAlternateNextLevel(final int anl) {
        this.mazeData.setAlternateNextLevel(anl);
    }

    @Override
    public void setAlternateNextLevelOffset(final int anlo) {
        this.mazeData.setAlternateNextLevelOffset(anlo);
    }

    @Override
    public int getActiveLevelNumber() {
        return this.activeLevel;
    }

    @Override
    public void switchLevel(final int level) {
        this.switchLevelInternal(level);
    }

    @Override
    public void switchLevelOffset(final int level) {
        this.switchLevelInternal(this.activeLevel + level);
    }

    private void switchLevelInternal(final int level) {
        if (this.activeLevel != level) {
            if (this.mazeData != null) {
                try (XDataWriter writer = this.getLevelWriter()) {
                    // Save old level
                    this.writeMazeLevel(writer);
                    writer.close();
                } catch (final IOException io) {
                    // Ignore
                }
            }
            this.activeLevel = level;
            try (XDataReader reader = this.getLevelReader()) {
                // Load new level
                this.readMazeLevel(reader);
                reader.close();
            } catch (final IOException io) {
                // Ignore
            }
        }
    }

    @Override
    public boolean doesLevelExist(final int level) {
        return level < this.levelCount && level >= 0;
    }

    @Override
    public boolean doesLevelExistOffset(final int level) {
        return this.activeLevel + level < this.levelCount && this.activeLevel
                + level >= 0;
    }

    @Override
    public void cutLevel() {
        if (this.levelCount > 1) {
            this.clipboard = this.mazeData;
            this.removeLevel();
        }
    }

    @Override
    public void copyLevel() {
        this.clipboard = new MazeData(this.mazeData);
    }

    @Override
    public void pasteLevel() {
        if (this.clipboard != null) {
            this.mazeData = new MazeData(this.clipboard);
            Mazer5D.getBagOStuff().getMazeManager().setDirty(true);
        }
    }

    @Override
    public boolean isPasteBlocked() {
        return this.clipboard == null;
    }

    @Override
    public boolean isCutBlocked() {
        return this.levelCount <= 1;
    }

    @Override
    public boolean insertLevelFromClipboard() {
        if (this.levelCount < Maze.MAX_LEVELS) {
            this.mazeData = new MazeData(this.clipboard);
            this.levelCount++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addLevel(final int rows, final int cols, final int floors) {
        if (this.levelCount < Maze.MAX_LEVELS) {
            if (this.mazeData != null) {
                try (XDataWriter writer = this.getLevelWriter()) {
                    // Save old level
                    this.writeMazeLevel(writer);
                    writer.close();
                } catch (final IOException io) {
                    // Ignore
                }
            }
            this.mazeData = new MazeData(rows, cols, floors);
            this.levelCount++;
            this.activeLevel = this.levelCount - 1;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeLevel() {
        if (this.levelCount > 1) {
            if (this.activeLevel >= 1 && this.activeLevel <= this.levelCount) {
                this.mazeData = null;
                // Delete file corresponding to current level
                this.getLevelFile(this.activeLevel).delete();
                // Shift all higher-numbered levels down
                for (int x = this.activeLevel; x < this.levelCount - 1; x++) {
                    final File sourceLocation = this.getLevelFile(x + 1);
                    final File targetLocation = this.getLevelFile(x);
                    try {
                        FileUtilities.moveFile(sourceLocation, targetLocation);
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

    @Override
    public MazeObjectModel getCell(final int row, final int col,
            final int floor, final int extra) {
        return this.mazeData.getCell(row, col, floor, extra);
    }

    @Override
    public int getFindResultRow() {
        return this.mazeData.getFindResultRow();
    }

    @Override
    public int getFindResultColumn() {
        return this.mazeData.getFindResultColumn();
    }

    @Override
    public int getFindResultFloor() {
        return this.mazeData.getFindResultFloor();
    }

    @Override
    public int getStartRow() {
        return this.mazeData.getStartRow();
    }

    @Override
    public int getStartColumn() {
        return this.mazeData.getStartColumn();
    }

    @Override
    public int getStartFloor() {
        return this.mazeData.getStartFloor();
    }

    @Override
    public int getStartLevel() {
        return this.startW;
    }

    @Override
    public int getRows() {
        return this.mazeData.getRows();
    }

    @Override
    public int getColumns() {
        return this.mazeData.getColumns();
    }

    @Override
    public int getFloors() {
        return this.mazeData.getFloors();
    }

    @Override
    public int getLevels() {
        return this.levelCount;
    }

    @Override
    public int getVisionRadius() {
        return this.mazeData.getVisionRadius();
    }

    @Override
    public boolean doesPlayerExist() {
        return this.mazeData.doesPlayerExist();
    }

    @Override
    public void findStart() {
        this.mazeData.findStart();
    }

    @Override
    public boolean findPlayer() {
        if (this.activeLevel > this.levelCount) {
            return false;
        } else {
            return this.mazeData.findPlayer();
        }
    }

    @Override
    public void findAllObjectPairsAndSwap(final MazeObjectModel o1,
            final MazeObjectModel o2) {
        this.mazeData.findAllObjectPairsAndSwap(o1, o2);
    }

    @Override
    public void findAllMatchingObjectsAndDecay(final MazeObjectModel o) {
        this.mazeData.findAllMatchingObjectsAndDecay(o);
    }

    @Override
    public void masterTrapTrigger() {
        this.mazeData.masterTrapTrigger();
    }

    @Override
    public void tickTimers(final int floor) {
        this.mazeData.tickTimers(floor);
    }

    @Override
    public boolean rotateRadiusClockwise(final int x, final int y, final int z,
            final int r) {
        return this.mazeData.rotateRadiusClockwise(x, y, z, r);
    }

    @Override
    public boolean rotateRadiusCounterclockwise(final int x, final int y,
            final int z, final int r) {
        return this.mazeData.rotateRadiusCounterclockwise(x, y, z, r);
    }

    @Override
    public void resize(final int x, final int y, final int z) {
        this.mazeData.resize(x, y, z);
    }

    @Override
    public boolean radialScan(final int x, final int y, final int z,
            final int l, final int r, final String targetName) {
        return this.mazeData.radialScan(x, y, z, l, r, targetName);
    }

    @Override
    public void radialScanFreezeObjects(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanFreezeObjects(x, y, z, r);
    }

    @Override
    public void radialScanFreezeGround(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanFreezeGround(x, y, z, r);
    }

    @Override
    public void radialScanEnrageObjects(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanEnrageObjects(x, y, z, r);
    }

    @Override
    public void radialScanBurnGround(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanBurnGround(x, y, z, r);
    }

    @Override
    public void radialScanPoisonObjects(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanPoisonObjects(x, y, z, r);
    }

    @Override
    public void radialScanPoisonGround(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanPoisonGround(x, y, z, r);
    }

    @Override
    public void radialScanShockObjects(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanShockObjects(x, y, z, r);
    }

    @Override
    public void radialScanShockGround(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanShockGround(x, y, z, r);
    }

    @Override
    public void radialScanWarpObjects(final int x, final int y, final int z,
            final int l, final int r) {
        this.mazeData.radialScanWarpObjects(x, y, z, l, r);
    }

    @Override
    public void radialScanShuffleObjects(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanShuffleObjects(x, y, z, r);
    }

    @Override
    public void radialScanQuakeBomb(final int x, final int y, final int z,
            final int r) {
        this.mazeData.radialScanQuakeBomb(x, y, z, r);
    }

    @Override
    public boolean isSquareVisible(final int x1, final int y1, final int x2,
            final int y2) {
        return this.mazeData.isSquareVisible(x1, y1, x2, y2);
    }

    @Override
    public void setCell(final MazeObjectModel mo, final int row, final int col,
            final int floor, final int extra) {
        this.mazeData.setCell(mo, row, col, floor, extra);
    }

    @Override
    public void setStartRow(final int newStartRow) {
        this.mazeData.setStartRow(newStartRow);
    }

    @Override
    public void setStartColumn(final int newStartColumn) {
        this.mazeData.setStartColumn(newStartColumn);
    }

    @Override
    public void setStartFloor(final int newStartFloor) {
        this.mazeData.setStartFloor(newStartFloor);
    }

    @Override
    public void setStartLevel(final int newStartLevel) {
        this.startW = newStartLevel;
    }

    @Override
    public void resetVisionRadius() {
        this.mazeData.resetVisionRadius();
    }

    @Override
    public void setVisionRadius(final int newVR) {
        this.mazeData.setVisionRadius(newVR);
    }

    @Override
    public void setVisionRadiusToMaximum() {
        this.mazeData.setVisionRadiusToMaximum();
    }

    @Override
    public void setVisionRadiusToMinimum() {
        this.mazeData.setVisionRadiusToMinimum();
    }

    @Override
    public void incrementVisionRadius() {
        this.mazeData.incrementVisionRadius();
    }

    @Override
    public void decrementVisionRadius() {
        this.mazeData.decrementVisionRadius();
    }

    @Override
    public void deactivateAllMovingFinishes() {
        this.mazeData.deactivateAllMovingFinishes();
    }

    @Override
    public void activateFirstMovingFinish() {
        this.mazeData.activateFirstMovingFinish();
    }

    @Override
    public void fillLevelDefault() {
        // FIXME: Use editor default fill
        final MazeObjectModel bottom = null;
        final MazeObjectModel top = GameObjects.getEmptySpace();
        this.mazeData.fill(bottom, top);
    }

    @Override
    public void fillFloorDefault(final int floor) {
        // FIXME: Use editor default fill
        final MazeObjectModel bottom = null;
        final MazeObjectModel top = GameObjects.getEmptySpace();
        this.mazeData.fillFloor(bottom, top, floor);
    }

    @Override
    public void fillLevel(final MazeObjectModel bottom,
            final MazeObjectModel top) {
        this.mazeData.fill(bottom, top);
    }

    @Override
    public void fillLevelRandomly() {
        this.mazeData.fillRandomly(this, this.activeLevel);
    }

    @Override
    public void fillFloorRandomly(final int z) {
        this.mazeData.fillFloorRandomly(this, z, this.activeLevel);
    }

    @Override
    public void fillLevelRandomlyCustom() {
        this.mazeData.fillRandomlyCustom(this, this.activeLevel);
    }

    @Override
    public void fillFloorRandomlyCustom(final int z) {
        this.mazeData.fillFloorRandomlyCustom(this, z, this.activeLevel);
    }

    @Override
    public void save() {
        this.mazeData.save();
    }

    @Override
    public void restore() {
        this.mazeData.restore();
    }

    @Override
    public void saveStart() {
        this.savedStart[0] = this.startW;
        this.savedStart[1] = this.mazeData.getStartRow();
        this.savedStart[2] = this.mazeData.getStartColumn();
        this.savedStart[3] = this.mazeData.getStartFloor();
    }

    @Override
    public void restoreStart() {
        this.startW = this.savedStart[0];
        this.mazeData.setStartRow(this.savedStart[1]);
        this.mazeData.setStartColumn(this.savedStart[2]);
        this.mazeData.setStartFloor(this.savedStart[3]);
    }

    @Override
    public void updateMovingBlockPosition(final int move, final int xLoc,
            final int yLoc, final MazeObjectModel block) {
        this.mazeData.updateMovingBlockPosition(move, xLoc, yLoc, block);
    }

    @Override
    public void warpObject(final MazeObjectModel mo, final int x, final int y,
            final int z, final int l) {
        this.mazeData.warpObject(mo, x, y, z, l);
    }

    @Override
    public void hotGround(final int x, final int y, final int z) {
        this.mazeData.hotGround(x, y, z);
    }

    @Override
    public void enableHorizontalWraparound() {
        this.mazeData.enableHorizontalWraparound();
    }

    @Override
    public void disableHorizontalWraparound() {
        this.mazeData.disableHorizontalWraparound();
    }

    @Override
    public void enableVerticalWraparound() {
        this.mazeData.enableVerticalWraparound();
    }

    @Override
    public void disableVerticalWraparound() {
        this.mazeData.disableVerticalWraparound();
    }

    @Override
    public void enable3rdDimensionWraparound() {
        this.mazeData.enable3rdDimensionWraparound();
    }

    @Override
    public void disable3rdDimensionWraparound() {
        this.mazeData.disable3rdDimensionWraparound();
    }

    @Override
    public boolean isHorizontalWraparoundEnabled() {
        return this.mazeData.isHorizontalWraparoundEnabled();
    }

    @Override
    public boolean isVerticalWraparoundEnabled() {
        return this.mazeData.isVerticalWraparoundEnabled();
    }

    @Override
    public boolean is3rdDimensionWraparoundEnabled() {
        return this.mazeData.is3rdDimensionWraparoundEnabled();
    }

    @Override
    public final String getTimeString() {
        return this.mazeData.getTimeString();
    }

    @Override
    public final boolean isTimerActive() {
        return this.mazeData.isTimerActive();
    }

    @Override
    public final void activateTimer(final int ticks) {
        this.mazeData.activateTimer(ticks);
    }

    @Override
    public final void deactivateTimer() {
        this.mazeData.deactivateTimer();
    }

    @Override
    public final void resetTimer() {
        this.mazeData.resetTimer();
    }

    @Override
    public final int getTimerValue() {
        return this.mazeData.getTimerValue();
    }

    @Override
    public final void extendTimerByInitialValue() {
        this.mazeData.extendTimerByInitialValue();
    }

    @Override
    public final void extendTimerByInitialValueHalved() {
        this.mazeData.extendTimerByInitialValueHalved();
    }

    @Override
    public final void extendTimerByInitialValueDoubled() {
        this.mazeData.extendTimerByInitialValueDoubled();
    }

    @Override
    public MazeModel readMaze() throws IOException {
        final Maze m = new Maze();
        // Attach handlers
        m.setPrefixHandler(this.xmlPrefixHandler);
        m.setSuffixHandler(this.xmlSuffixHandler);
        // Make base paths the same
        m.basePath = this.basePath;
        // Create metafile reader
        try (XDataReader metaReader = new XDataReader(m.basePath
                + File.separator + "metafile.xml", "maze")) {
            // Read metafile
            final MazeVersion version = m.readMazeMetafile(metaReader);
            // Set compatibility flags
            if (version == MazeVersion.V1) {
                Mazer5D.getBagOStuff().getMazeManager().setMazeXML1Compatible(
                        true);
                Mazer5D.getBagOStuff().getMazeManager().setMazeXML2Compatible(
                        true);
                Mazer5D.getBagOStuff().getMazeManager().setMazeXML4Compatible(
                        true);
            } else if (version == MazeVersion.V2) {
                Mazer5D.getBagOStuff().getMazeManager().setMazeXML1Compatible(
                        false);
                Mazer5D.getBagOStuff().getMazeManager().setMazeXML2Compatible(
                        true);
                Mazer5D.getBagOStuff().getMazeManager().setMazeXML4Compatible(
                        true);
            } else if (version == MazeVersion.V3 || version == MazeVersion.V4) {
                Mazer5D.getBagOStuff().getMazeManager().setMazeXML1Compatible(
                        false);
                Mazer5D.getBagOStuff().getMazeManager().setMazeXML2Compatible(
                        false);
                Mazer5D.getBagOStuff().getMazeManager().setMazeXML4Compatible(
                        true);
            } else {
                Mazer5D.getBagOStuff().getMazeManager().setMazeXML1Compatible(
                        false);
                Mazer5D.getBagOStuff().getMazeManager().setMazeXML2Compatible(
                        false);
                Mazer5D.getBagOStuff().getMazeManager().setMazeXML4Compatible(
                        false);
            }
            // Create data reader
            try (XDataReader dataReader = m.getLevelReader()) {
                // Read data
                m.readMazeLevel(dataReader, version);
            }
        }
        return m;
    }

    private XDataReader getLevelReader() throws IOException {
        return new XDataReader(this.basePath + File.separator + "level"
                + this.activeLevel + ".xml", "level");
    }

    private MazeVersion readMazeMetafile(final XDataReader reader)
            throws IOException {
        MazeVersion ver = MazeVersion.V1;
        if (this.xmlPrefixHandler != null) {
            ver = this.xmlPrefixHandler.readPrefix(reader);
        }
        final int levels = reader.readInt();
        this.levelCount = levels;
        this.startW = reader.readInt();
        this.currHP = reader.readInt();
        this.maxHP = reader.readInt();
        this.mazeTitle = reader.readString();
        this.mazeStartMessage = reader.readString();
        this.mazeEndMessage = reader.readString();
        if (this.xmlSuffixHandler != null) {
            this.xmlSuffixHandler.readSuffix(reader, ver);
        }
        return ver;
    }

    private void readMazeLevel(final XDataReader reader) throws IOException {
        this.readMazeLevel(reader, MazeVersion.V5);
    }

    private void readMazeLevel(final XDataReader reader,
            final MazeVersion formatVersion) throws IOException {
        if (formatVersion == MazeVersion.V1) {
            this.mazeData = MazeData.readMazeDataV1(reader);
            this.mazeData.readSavedTowerState(reader, formatVersion);
        } else if (formatVersion == MazeVersion.V2) {
            this.mazeData = MazeData.readMazeDataV2(reader);
            this.mazeData.readSavedTowerState(reader, formatVersion);
        } else if (formatVersion == MazeVersion.V3) {
            this.mazeData = MazeData.readMazeDataV3(reader);
            this.mazeData.readSavedTowerState(reader, formatVersion);
        } else if (formatVersion == MazeVersion.V4) {
            this.mazeData = MazeData.readMazeDataV4(reader);
            this.mazeData.readSavedTowerState(reader, formatVersion);
        } else if (formatVersion == MazeVersion.V5) {
            this.mazeData = MazeData.readMazeDataV5(reader);
            this.mazeData.readSavedTowerState(reader, formatVersion);
        } else {
            throw new IOException("Unknown maze format version!");
        }
    }

    private File getLevelFile(final int level) {
        return new File(this.basePath + File.separator + level + ".level");
    }

    @Override
    public void writeMaze() throws IOException {
        // Clear compatibility flag
        Mazer5D.getBagOStuff().getMazeManager().setMazeXML1Compatible(false);
        // Clear 2 compatibility flag
        Mazer5D.getBagOStuff().getMazeManager().setMazeXML2Compatible(false);
        // Clear 4 compatibility flag
        Mazer5D.getBagOStuff().getMazeManager().setMazeXML4Compatible(false);
        // Create metafile writer
        try (XDataWriter metaWriter = new XDataWriter(this.basePath
                + File.separator + "metafile.xml", "maze")) {
            // Write metafile
            this.writeMazeMetafile(metaWriter);
            // Create data writer
            try (XDataWriter dataWriter = this.getLevelWriter()) {
                // Write data
                this.writeMazeLevel(dataWriter);
            }
        }
    }

    private XDataWriter getLevelWriter() throws IOException {
        return new XDataWriter(this.basePath + File.separator + "level"
                + this.activeLevel + ".xml", "level");
    }

    private void writeMazeMetafile(final XDataWriter writer)
            throws IOException {
        if (this.xmlPrefixHandler != null) {
            this.xmlPrefixHandler.writePrefix(writer);
        }
        writer.writeInt(this.levelCount);
        writer.writeInt(this.startW);
        writer.writeInt(this.currHP);
        writer.writeInt(this.maxHP);
        writer.writeString(this.mazeTitle);
        writer.writeString(this.mazeStartMessage);
        writer.writeString(this.mazeEndMessage);
        if (this.xmlSuffixHandler != null) {
            this.xmlSuffixHandler.writeSuffix(writer);
        }
    }

    private void writeMazeLevel(final XDataWriter writer) throws IOException {
        // Clear compatibility flag
        Mazer5D.getBagOStuff().getMazeManager().setMazeXML1Compatible(false);
        // Clear 2 compatibility flag
        Mazer5D.getBagOStuff().getMazeManager().setMazeXML2Compatible(false);
        // Clear 4 compatibility flag
        Mazer5D.getBagOStuff().getMazeManager().setMazeXML4Compatible(false);
        // Write the level
        this.mazeData.writeMazeData(writer);
        this.mazeData.writeSavedTowerState(writer);
    }
}
