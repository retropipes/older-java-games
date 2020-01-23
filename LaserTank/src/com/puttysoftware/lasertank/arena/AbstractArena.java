/*  LaserTank: An Arena-Solving Game
 Copyright (C) 2008-2013 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.lasertank.arena;

import java.io.File;
import java.io.IOException;

import com.puttysoftware.lasertank.arena.abstractobjects.AbstractArenaObject;
import com.puttysoftware.lasertank.arena.abstractobjects.AbstractButton;
import com.puttysoftware.lasertank.arena.abstractobjects.AbstractButtonDoor;
import com.puttysoftware.lasertank.arena.abstractobjects.AbstractCharacter;
import com.puttysoftware.lasertank.arena.abstractobjects.AbstractTunnel;
import com.puttysoftware.lasertank.stringmanagers.StringConstants;
import com.puttysoftware.lasertank.stringmanagers.StringLoader;
import com.puttysoftware.lasertank.utilities.Direction;

public abstract class AbstractArena {
    // Constants
    private static final int MIN_LEVELS = 1;
    protected static final int MAX_LEVELS = Integer.MAX_VALUE;
    protected static final int ERA_COUNT = 5;

    // Constructors
    public AbstractArena() throws IOException {
        // Do nothing
    }

    // Static methods
    public static String getArenaTempFolder() {
        return System
                .getProperty(StringLoader.loadString(
                        StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_TEMP_DIR))
                + File.separator
                + StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_PROGRAM_NAME);
    }

    public static int getMinLevels() {
        return AbstractArena.MIN_LEVELS;
    }

    public static int getMaxLevels() {
        return AbstractArena.MAX_LEVELS;
    }

    public static int getMaxFloors() {
        return AbstractArenaData.getMaxFloors();
    }

    public static int getMinFloors() {
        return AbstractArenaData.getMinFloors();
    }

    public static int getMinRows() {
        return AbstractArenaData.getMinRows();
    }

    public static int getMinColumns() {
        return AbstractArenaData.getMinColumns();
    }

    // Methods
    public abstract String getArenaTempMusicFolder();

    public abstract boolean isMoveShootAllowed();

    public abstract boolean isMoveShootAllowedGlobally();

    public abstract boolean isMoveShootAllowedThisLevel();

    public abstract void setMoveShootAllowedGlobally(boolean value);

    public abstract void setMoveShootAllowedThisLevel(boolean value);

    public abstract String getMusicFilename();

    public abstract void setMusicFilename(final String newMusicFilename);

    public abstract String getName();

    public abstract void setName(String newName);

    public abstract String getHint();

    public abstract void setHint(String newHint);

    public abstract String getAuthor();

    public abstract void setAuthor(String newAuthor);

    public abstract int getDifficulty();

    public abstract void setDifficulty(int newDifficulty);

    public abstract String getBasePath();

    public abstract void setPrefixHandler(AbstractPrefixIO xph);

    public abstract void setSuffixHandler(AbstractSuffixIO xsh);

    public abstract int getActiveLevelNumber();

    public abstract int getActiveEraNumber();

    public final boolean switchToNextLevelWithDifficulty(
            final int[] difficulty) {
        boolean keepGoing = true;
        while (keepGoing) {
            final int diff = this.getDifficulty();
            for (final int element : difficulty) {
                if (diff - 1 == element) {
                    keepGoing = false;
                    return true;
                }
            }
            if (!this.doesLevelExistOffset(1)) {
                keepGoing = false;
                return false;
            }
            if (keepGoing) {
                this.switchLevelOffset(1);
            }
        }
        return false;
    }

    public final boolean switchToPreviousLevelWithDifficulty(
            final int[] difficulty) {
        boolean keepGoing = true;
        while (keepGoing) {
            final int diff = this.getDifficulty();
            for (final int element : difficulty) {
                if (diff - 1 == element) {
                    keepGoing = false;
                    return true;
                }
            }
            if (!this.doesLevelExistOffset(-1)) {
                keepGoing = false;
                return false;
            }
            if (keepGoing) {
                this.switchLevelOffset(-1);
            }
        }
        return false;
    }

    public abstract String[] getLevelInfoList();

    public abstract void generateLevelInfoList();

    public abstract void switchLevel(int level);

    public abstract void switchLevelOffset(int level);
    
    public abstract void switchEra(final int era);

    public abstract void switchEraOffset(final int era);

    protected abstract void switchInternal(int level, int era);

    public abstract boolean doesLevelExist(int level);

    public abstract boolean doesLevelExistOffset(int level);

    public abstract void cutLevel();

    public abstract void copyLevel();

    public abstract void pasteLevel();

    public abstract boolean isPasteBlocked();

    public abstract boolean isCutBlocked();

    public abstract boolean insertLevelFromClipboard();

    public abstract boolean addLevel();

    public final boolean removeLevel(final int num) {
        final int saveLevel = this.getActiveLevelNumber();
        this.switchLevel(num);
        final boolean success = this.removeActiveLevel();
        if (success) {
            if (saveLevel == 0) {
                // Was at first level
                this.switchLevel(0);
            } else {
                // Was at level other than first
                if (saveLevel > num) {
                    // Saved level was shifted down
                    this.switchLevel(saveLevel - 1);
                } else if (saveLevel < num) {
                    // Saved level was NOT shifted down
                    this.switchLevel(saveLevel);
                } else {
                    // Saved level was deleted
                    this.switchLevel(0);
                }
            }
        } else {
            this.switchLevel(saveLevel);
        }
        return success;
    }

    protected abstract boolean removeActiveLevel();

    public abstract boolean isCellDirty(final int row, final int col,
            final int floor);

    public abstract AbstractArenaObject getCell(final int row, final int col,
            final int floor, final int layer);

    public abstract AbstractArenaObject getVirtualCell(final int row,
            final int col, final int floor, final int layer);

    public abstract int getStartRow(final int pi);

    public abstract int getStartColumn(final int pi);

    public abstract int getStartFloor(final int pi);

    public static int getStartLevel() {
        return 0;
    }

    public abstract int getRows();

    public abstract int getColumns();

    public abstract int getFloors();

    public abstract int getLevels();

    public abstract boolean doesPlayerExist(final int pi);

    public abstract int[] findPlayer(final int number);

    public abstract void tickTimers(final int floor, final int actionType);

    public abstract void checkForEnemies(final int floor, final int ex,
            final int ey, final AbstractCharacter e);

    public abstract int checkForMagnetic(int floor, int centerX, int centerY,
            Direction dir);

    public abstract int[] circularScan(final int x, final int y, final int z,
            final int maxR, final String targetName, final boolean moved);

    public abstract int[] circularScanTunnel(final int x, final int y,
            final int z, final int maxR, final int tx, final int ty,
            final AbstractTunnel target, final boolean moved);

    public abstract void circularScanRange(final int x, final int y,
            final int z, final int maxR, final int rangeType,
            final int forceUnits);

    public abstract int[] findObject(int z, String targetName);

    public abstract boolean circularScanTank(final int x, final int y,
            final int z, final int maxR);

    public abstract void fullScanKillTanks();

    public abstract void fullScanFreezeGround();

    public abstract void fullScanAllButtonOpen(int z, AbstractButton source);

    public abstract void fullScanAllButtonClose(int z, AbstractButton source);

    public abstract void fullScanButtonBind(int dx, int dy, int z,
            AbstractButtonDoor source);

    public abstract void fullScanButtonCleanup(int px, int py, int z,
            AbstractButton button);

    public abstract void fullScanFindButtonLostDoor(int z,
            AbstractButtonDoor door);

    public abstract void setCell(final AbstractArenaObject mo, final int row,
            final int col, final int floor, final int layer);

    public abstract void setVirtualCell(final AbstractArenaObject mo,
            final int row, final int col, final int floor, final int layer);

    public abstract void markAsDirty(final int row, final int col,
            final int floor);

    public abstract void clearDirtyFlags(int floor);

    public abstract void setDirtyFlags(int floor);

    public abstract void clearVirtualGrid();

    public abstract void setStartRow(final int pi, final int newStartRow);

    public abstract void setStartColumn(final int pi, final int newStartColumn);

    public abstract void setStartFloor(final int pi, final int newStartFloor);

    public abstract void fillDefault();

    public abstract void save();

    public abstract void restore();

    public abstract void resize(int z, AbstractArenaObject nullFill);

    public abstract void setData(AbstractArenaData newData, int count);

    public abstract void enableHorizontalWraparound();

    public abstract void disableHorizontalWraparound();

    public abstract void enableVerticalWraparound();

    public abstract void disableVerticalWraparound();

    public abstract void enableThirdDimensionWraparound();

    public abstract void disableThirdDimensionWraparound();

    public abstract boolean isHorizontalWraparoundEnabled();

    public abstract boolean isVerticalWraparoundEnabled();

    public abstract boolean isThirdDimensionWraparoundEnabled();

    public abstract AbstractArena readArena() throws IOException;

    public abstract void writeArena() throws IOException;

    public abstract void undo();

    public abstract void redo();

    public abstract boolean tryUndo();

    public abstract boolean tryRedo();

    public abstract void updateUndoHistory(final HistoryStatus whatIs);

    public abstract void updateRedoHistory(final HistoryStatus whatIs);

    public abstract HistoryStatus getWhatWas();

    public abstract void resetHistoryEngine();
}