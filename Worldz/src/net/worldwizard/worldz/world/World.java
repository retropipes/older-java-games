/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.world;

import java.io.File;
import java.io.IOException;

import net.worldwizard.io.DataConstants;
import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;
import net.worldwizard.io.DirectoryUtilities;
import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.creatures.PartyManager;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.objects.Empty;
import net.worldwizard.worldz.objects.MovingBlock;

public class World implements WorldConstants {
    // Properties
    private LayeredTower worldData;
    private LayeredTower clipboard;
    private int startW;
    private int resultLevel;
    private int levelCount;
    private int activeLevel;
    private String worldTitle;
    private String worldStartMessage;
    private String worldEndMessage;
    private String basePath;
    private PrefixIO prefixHandler;
    private SuffixIO suffixHandler;
    private final int[] savedStart;
    private static final int MIN_LEVELS = 1;
    private static final int MAX_LEVELS = Integer.MAX_VALUE;

    // Constructors
    private World(final int rows, final int cols, final int floor,
            final LayeredTower source) {
        this.worldData = new LayeredTower(rows, cols, floor, source);
        this.clipboard = null;
        this.levelCount = 1;
        this.startW = 0;
        this.prefixHandler = null;
        this.suffixHandler = null;
        this.worldTitle = null;
        this.worldStartMessage = null;
        this.worldEndMessage = null;
        this.savedStart = new int[4];
        this.basePath = null;
    }

    public World() {
        this.worldData = null;
        this.clipboard = null;
        this.levelCount = 0;
        this.startW = 0;
        this.activeLevel = 0;
        this.prefixHandler = null;
        this.suffixHandler = null;
        this.worldTitle = "Untitled World";
        this.worldStartMessage = "Let's Solve The World!";
        this.worldEndMessage = "World Solved!";
        this.savedStart = new int[4];
        final long random = new RandomRange(0, Long.MAX_VALUE).generateLong();
        final String randomID = Long.toHexString(random);
        this.basePath = System.getProperty("java.io.tmpdir") + File.separator
                + "Worldz" + File.separator + randomID + ".world";
        final File base = new File(this.basePath);
        base.mkdirs();
    }

    // Cleanup hook
    static {
        Runtime.getRuntime().addShutdownHook(new TempDirCleanup());
    }

    // Static methods
    public static int getMinLevels() {
        return World.MIN_LEVELS;
    }

    public static int getMaxLevels() {
        return World.MAX_LEVELS;
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
    public World getTemporaryBattleCopy(final int row, final int col,
            final int floor) {
        final World temp = new World(this.getRows(), this.getColumns(), floor,
                this.worldData);
        temp.setBattleCell(new Empty(), row, col);
        return temp;
    }

    public void doPoisonDamage() {
        PartyManager.getParty().hurtParty(1);
    }

    public String getBasePath() {
        return this.basePath;
    }

    public void setPrefixHandler(final PrefixIO ph) {
        this.prefixHandler = ph;
    }

    public void setSuffixHandler(final SuffixIO sh) {
        this.suffixHandler = sh;
    }

    public String getWorldTitle() {
        return this.worldTitle;
    }

    public void setWorldTitle(final String title) {
        if (title == null) {
            throw new NullPointerException("Title cannot be null!");
        }
        this.worldTitle = title;
    }

    public String getWorldStartMessage() {
        return this.worldStartMessage;
    }

    public void setWorldStartMessage(final String msg) {
        if (msg == null) {
            throw new NullPointerException("Message cannot be null!");
        }
        this.worldStartMessage = msg;
    }

    public String getWorldEndMessage() {
        return this.worldEndMessage;
    }

    public void setWorldEndMessage(final String msg) {
        if (msg == null) {
            throw new NullPointerException("Message cannot be null!");
        }
        this.worldEndMessage = msg;
    }

    public int getPoisonPower() {
        return this.worldData.getPoisonPower();
    }

    public void setPoisonPower(final int pp) {
        this.worldData.setPoisonPower(pp);
    }

    public String getPoisonString() {
        return this.worldData.getPoisonString();
    }

    public static int getMaxPoisonPower() {
        return LayeredTower.getMaxPoisonPower();
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
            if (this.worldData != null) {
                try {
                    // Save old level
                    this.writeWorldLevel(this.getLevelWriter());
                } catch (final IOException io) {
                    // Ignore
                }
            }
            this.activeLevel = level;
            try {
                // Load new level
                this.readWorldLevel(this.getLevelReader());
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
            this.clipboard = this.worldData;
            this.removeLevel();
        }
    }

    public void copyLevel() {
        this.clipboard = this.worldData.clone();
    }

    public void pasteLevel() {
        if (this.clipboard != null) {
            this.worldData = this.clipboard.clone();
            Worldz.getApplication().getWorldManager().setDirty(true);
        }
    }

    public boolean isPasteBlocked() {
        return this.clipboard == null;
    }

    public boolean isCutBlocked() {
        return this.levelCount <= 1;
    }

    public boolean insertLevelFromClipboard() {
        if (this.levelCount < World.MAX_LEVELS) {
            this.worldData = this.clipboard.clone();
            this.levelCount++;
            return true;
        } else {
            return false;
        }
    }

    public boolean addLevel(final int rows, final int cols, final int floors) {
        if (this.levelCount < World.MAX_LEVELS) {
            if (this.worldData != null) {
                try {
                    // Save old level
                    this.writeWorldLevel(this.getLevelWriter());
                } catch (final IOException io) {
                    // Ignore
                }
            }
            this.worldData = new LayeredTower(rows, cols, floors);
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
                this.worldData = null;
                // Delete file corresponding to current level
                this.getLevelFile(this.activeLevel).delete();
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

    public WorldObject getBattleCell(final int row, final int col) {
        return this.worldData.getCell(row, col, 0, WorldConstants.LAYER_OBJECT);
    }

    public WorldObject getBattleGround(final int row, final int col) {
        return this.worldData.getCell(row, col, 0, WorldConstants.LAYER_GROUND);
    }

    public WorldObject getCell(final int row, final int col, final int floor,
            final int extra) {
        return this.worldData.getCell(row, col, floor, extra);
    }

    public int getFindResultRow() {
        return this.worldData.getFindResultRow();
    }

    public int getFindResultColumn() {
        return this.worldData.getFindResultColumn();
    }

    public int getFindResultFloor() {
        return this.worldData.getFindResultFloor();
    }

    public int getFindResultLevel() {
        return this.resultLevel;
    }

    public int getStartRow() {
        return this.worldData.getStartRow();
    }

    public int getStartColumn() {
        return this.worldData.getStartColumn();
    }

    public int getStartFloor() {
        return this.worldData.getStartFloor();
    }

    public int getStartLevel() {
        return this.startW;
    }

    public int getRows() {
        return this.worldData.getRows();
    }

    public int getColumns() {
        return this.worldData.getColumns();
    }

    public int getFloors() {
        return this.worldData.getFloors();
    }

    public int getLevels() {
        return this.levelCount;
    }

    public int getVisionRadius() {
        return this.worldData.getVisionRadius();
    }

    public boolean doesPlayerExist() {
        return this.worldData.doesPlayerExist();
    }

    public void findStart() {
        this.worldData.findStart();
    }

    public boolean findPlayer() {
        if (this.activeLevel > this.levelCount) {
            return false;
        } else {
            return this.worldData.findPlayer();
        }
    }

    public void findAllObjectPairsAndSwap(final WorldObject o1,
            final WorldObject o2) {
        this.worldData.findAllObjectPairsAndSwap(o1, o2);
    }

    public void findAllMatchingObjectsAndDecay(final WorldObject o) {
        this.worldData.findAllMatchingObjectsAndDecay(o);
    }

    public void masterTrapTrigger() {
        this.worldData.masterTrapTrigger();
    }

    public void tickTimers(final int floor) {
        this.worldData.tickTimers(floor);
    }

    public boolean rotateRadiusClockwise(final int x, final int y, final int z,
            final int r) {
        return this.worldData.rotateRadiusClockwise(x, y, z, r);
    }

    public boolean rotateRadiusCounterclockwise(final int x, final int y,
            final int z, final int r) {
        return this.worldData.rotateRadiusCounterclockwise(x, y, z, r);
    }

    public void resize(final int x, final int y, final int z) {
        this.worldData.resize(x, y, z);
    }

    public boolean radialScan(final int x, final int y, final int z,
            final int l, final int r, final String targetName) {
        return this.worldData.radialScan(x, y, z, l, r, targetName);
    }

    public void radialScanFreezeObjects(final int x, final int y, final int z,
            final int r) {
        this.worldData.radialScanFreezeObjects(x, y, z, r);
    }

    public void radialScanFreezeGround(final int x, final int y, final int z,
            final int r) {
        this.worldData.radialScanFreezeGround(x, y, z, r);
    }

    public void radialScanEnrageObjects(final int x, final int y, final int z,
            final int r) {
        this.worldData.radialScanEnrageObjects(x, y, z, r);
    }

    public void radialScanBurnGround(final int x, final int y, final int z,
            final int r) {
        this.worldData.radialScanBurnGround(x, y, z, r);
    }

    public void radialScanPoisonObjects(final int x, final int y, final int z,
            final int r) {
        this.worldData.radialScanPoisonObjects(x, y, z, r);
    }

    public void radialScanPoisonGround(final int x, final int y, final int z,
            final int r) {
        this.worldData.radialScanPoisonGround(x, y, z, r);
    }

    public void radialScanShockObjects(final int x, final int y, final int z,
            final int r) {
        this.worldData.radialScanShockObjects(x, y, z, r);
    }

    public void radialScanShockGround(final int x, final int y, final int z,
            final int r) {
        this.worldData.radialScanShockGround(x, y, z, r);
    }

    public void radialScanWarpObjects(final int x, final int y, final int z,
            final int l, final int r) {
        this.worldData.radialScanWarpObjects(x, y, z, l, r);
    }

    public void radialScanShuffleObjects(final int x, final int y, final int z,
            final int r) {
        this.worldData.radialScanShuffleObjects(x, y, z, r);
    }

    public boolean isSquareVisible(final int x1, final int y1, final int x2,
            final int y2) {
        return this.worldData.isSquareVisible(x1, y1, x2, y2);
    }

    public void setBattleCell(final WorldObject mo, final int row,
            final int col) {
        this.worldData.setCell(mo, row, col, 0, WorldConstants.LAYER_OBJECT);
    }

    public void setCell(final WorldObject mo, final int row, final int col,
            final int floor, final int extra) {
        this.worldData.setCell(mo, row, col, floor, extra);
    }

    public void setStartRow(final int newStartRow) {
        this.worldData.setStartRow(newStartRow);
    }

    public void setStartColumn(final int newStartColumn) {
        this.worldData.setStartColumn(newStartColumn);
    }

    public void setStartFloor(final int newStartFloor) {
        this.worldData.setStartFloor(newStartFloor);
    }

    public void setStartLevel(final int newStartLevel) {
        this.startW = newStartLevel;
    }

    public void setVisionRadiusToMaximum() {
        this.worldData.setVisionRadiusToMaximum();
    }

    public void setVisionRadiusToMinimum() {
        this.worldData.setVisionRadiusToMinimum();
    }

    public void incrementVisionRadius() {
        this.worldData.incrementVisionRadius();
    }

    public void decrementVisionRadius() {
        this.worldData.decrementVisionRadius();
    }

    public void fillLevelDefault() {
        final WorldObject bottom = Worldz.getApplication().getPrefsManager()
                .getEditorDefaultFill();
        final WorldObject top = new Empty();
        this.worldData.fill(bottom, top);
    }

    public void fillFloorDefault(final int floor) {
        final WorldObject bottom = Worldz.getApplication().getPrefsManager()
                .getEditorDefaultFill();
        final WorldObject top = new Empty();
        this.worldData.fillFloor(bottom, top, floor);
    }

    public void fillLevel(final WorldObject bottom, final WorldObject top) {
        this.worldData.fill(bottom, top);
    }

    public void fillLevelRandomly() {
        this.worldData.fillRandomly(this, this.activeLevel);
    }

    public void fillFloorRandomly(final int z) {
        this.worldData.fillFloorRandomly(this, z, this.activeLevel);
    }

    public void fillLevelRandomlyCustom() {
        this.worldData.fillRandomlyCustom(this, this.activeLevel);
    }

    public void fillFloorRandomlyCustom(final int z) {
        this.worldData.fillFloorRandomlyCustom(this, z, this.activeLevel);
    }

    public void save() {
        this.worldData.save();
    }

    public void restore() {
        this.worldData.restore();
    }

    public void saveLevel() {
        this.worldData.save();
    }

    public void restoreLevel() {
        this.worldData.restore();
    }

    public void saveStart() {
        this.savedStart[0] = this.startW;
        this.savedStart[1] = this.worldData.getStartRow();
        this.savedStart[2] = this.worldData.getStartColumn();
        this.savedStart[3] = this.worldData.getStartFloor();
    }

    public void restoreStart() {
        this.startW = this.savedStart[0];
        this.worldData.setStartRow(this.savedStart[1]);
        this.worldData.setStartColumn(this.savedStart[2]);
        this.worldData.setStartFloor(this.savedStart[3]);
    }

    public void updateMovingBlockPosition(final int move, final int xLoc,
            final int yLoc, final MovingBlock block) {
        this.worldData.updateMovingBlockPosition(move, xLoc, yLoc, block);
    }

    public void warpObject(final WorldObject mo, final int x, final int y,
            final int z, final int l) {
        this.worldData.warpObject(mo, x, y, z, l);
    }

    public void enableHorizontalWraparound() {
        this.worldData.enableHorizontalWraparound();
    }

    public void disableHorizontalWraparound() {
        this.worldData.disableHorizontalWraparound();
    }

    public void enableVerticalWraparound() {
        this.worldData.enableVerticalWraparound();
    }

    public void disableVerticalWraparound() {
        this.worldData.disableVerticalWraparound();
    }

    public void enable3rdDimensionWraparound() {
        this.worldData.enable3rdDimensionWraparound();
    }

    public void disable3rdDimensionWraparound() {
        this.worldData.disable3rdDimensionWraparound();
    }

    public boolean isHorizontalWraparoundEnabled() {
        return this.worldData.isHorizontalWraparoundEnabled();
    }

    public boolean isVerticalWraparoundEnabled() {
        return this.worldData.isVerticalWraparoundEnabled();
    }

    public boolean is3rdDimensionWraparoundEnabled() {
        return this.worldData.is3rdDimensionWraparoundEnabled();
    }

    public World readWorld() throws IOException {
        final World m = new World();
        // Attach handlers
        m.setPrefixHandler(this.prefixHandler);
        m.setSuffixHandler(this.suffixHandler);
        // Make base paths the same
        m.basePath = this.basePath;
        // Create metafile reader
        final DataReader metaReader = new DataReader(
                m.basePath + File.separator + "world.metafile",
                DataConstants.DATA_MODE_BINARY);
        // Read metafile
        m.readWorldMetafile(metaReader);
        metaReader.close();
        // Create data reader
        final DataReader dataReader = m.getLevelReader();
        // Read data
        m.readWorldLevel(dataReader);
        dataReader.close();
        return m;
    }

    private DataReader getLevelReader() throws IOException {
        return new DataReader(
                this.basePath + File.separator + this.activeLevel + ".level",
                DataConstants.DATA_MODE_BINARY);
    }

    private void readWorldMetafile(final DataReader reader) throws IOException {
        if (this.prefixHandler != null) {
            this.prefixHandler.readPrefix(reader);
        }
        final int levels = reader.readInt();
        this.levelCount = levels;
        this.startW = reader.readInt();
        this.worldTitle = reader.readString();
        this.worldStartMessage = reader.readString();
        this.worldEndMessage = reader.readString();
        if (this.suffixHandler != null) {
            this.suffixHandler.readSuffix(reader);
        }
    }

    private void readWorldLevel(final DataReader reader) throws IOException {
        this.worldData = LayeredTower.readLayeredTower(reader);
        this.worldData.readSavedTowerState(reader,
                FormatConstants.WORLD_FORMAT_1);
    }

    public void writeWorld() throws IOException {
        // Create metafile writer
        final DataWriter metaWriter = new DataWriter(
                this.basePath + File.separator + "world.metafile",
                DataConstants.DATA_MODE_BINARY);
        // Write metafile
        this.writeWorldMetafile(metaWriter);
        metaWriter.close();
        // Create data writer
        final DataWriter dataWriter = this.getLevelWriter();
        // Write data
        this.writeWorldLevel(dataWriter);
        dataWriter.close();
    }

    private File getLevelFile(final int level) {
        return new File(this.basePath + File.separator + level + ".level");
    }

    private DataWriter getLevelWriter() throws IOException {
        return new DataWriter(
                this.basePath + File.separator + this.activeLevel + ".level",
                DataConstants.DATA_MODE_BINARY);
    }

    private void writeWorldMetafile(final DataWriter writer)
            throws IOException {
        if (this.prefixHandler != null) {
            this.prefixHandler.writePrefix(writer);
        }
        writer.writeInt(this.levelCount);
        writer.writeInt(this.startW);
        writer.writeString(this.worldTitle);
        writer.writeString(this.worldStartMessage);
        writer.writeString(this.worldEndMessage);
        if (this.suffixHandler != null) {
            this.suffixHandler.writeSuffix(writer);
        }
    }

    private void writeWorldLevel(final DataWriter writer) throws IOException {
        this.worldData.writeLayeredTower(writer);
        this.worldData.writeSavedTowerState(writer);
    }
}
