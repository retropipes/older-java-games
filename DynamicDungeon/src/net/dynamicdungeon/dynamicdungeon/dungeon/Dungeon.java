/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon;

import java.io.File;
import java.io.IOException;

import net.dynamicdungeon.commondialogs.CommonDialogs;
import net.dynamicdungeon.dbio.DatabaseReader;
import net.dynamicdungeon.dbio.DatabaseWriter;
import net.dynamicdungeon.dynamicdungeon.VersionException;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractDungeonObject;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Monster;
import net.dynamicdungeon.dynamicdungeon.utilities.DynamicProperties;
import net.dynamicdungeon.randomrange.RandomLongRange;

public class Dungeon {
    // Properties
    private LayeredTower mazeData;
    private int startW;
    private int locW;
    private int saveW;
    private int levelCount;
    private int activeLevel;
    private String basePath;
    private PrefixIO prefixHandler;
    private SuffixIO suffixHandler;
    private final int[] savedStart;
    private static final int MIN_LEVELS = 1;
    private static final int MAX_LEVELS = 15;

    // Constructors
    public Dungeon() {
        this.mazeData = null;
        this.levelCount = 0;
        this.startW = 0;
        this.locW = 0;
        this.saveW = 0;
        this.activeLevel = 0;
        this.savedStart = new int[4];
        final long random = new RandomLongRange(0, Long.MAX_VALUE).generate();
        final String randomID = Long.toHexString(random);
        this.basePath = DynamicProperties.getCachesDirectory() + File.separator
                + "DynamicDungeonTemp" + File.separator + randomID + ".maze";
        final File base = new File(this.basePath);
        final boolean success = base.mkdirs();
        if (!success) {
            CommonDialogs.showErrorDialog(
                    "Dungeon temporary folder creation failed!",
                    "DynamicDungeon");
        }
    }

    // Static methods
    public static String getDungeonTempFolder() {
        return DynamicProperties.getCachesDirectory() + File.separator
                + "DynamicDungeonTemp";
    }

    public static int getMinLevels() {
        return Dungeon.MIN_LEVELS;
    }

    public static int getMaxLevels() {
        return Dungeon.MAX_LEVELS;
    }

    public static int getMaxFloors() {
        return LayeredTower.getMaxFloors();
    }

    public static int getMaxColumns() {
        return LayeredTower.getMaxColumns();
    }

    public static int getMaxRows() {
        return LayeredTower.getMaxRows();
    }

    // Methods
    public final int getBombs() {
        return this.mazeData.getBombs();
    }

    public final int getHammers() {
        return this.mazeData.getHammers();
    }

    public final int getKeys() {
        return this.mazeData.getKeys();
    }

    public final int getStones() {
        return this.mazeData.getStones();
    }

    public final int getTablets() {
        return this.mazeData.getTablets();
    }

    public final void addBomb() {
        this.mazeData.addBomb();
    }

    public final void addHammer() {
        this.mazeData.addHammer();
    }

    public final void addKey() {
        this.mazeData.addKey();
    }

    public final void addStone() {
        this.mazeData.addStone();
    }

    public final void addTablet() {
        this.mazeData.addTablet();
    }

    public final void useBomb() {
        this.mazeData.useBomb();
    }

    public final void useHammer() {
        this.mazeData.useHammer();
    }

    public final void useKey() {
        this.mazeData.useKey();
    }

    public final int getStoneCount() {
        return this.mazeData.getStoneCount();
    }

    public final void useTablet() {
        this.mazeData.useTablet();
    }

    public int getLevels() {
        return this.levelCount;
    }

    public void updateMonsterPosition(final int move, final int xLoc,
            final int yLoc, final Monster monster) {
        this.mazeData.updateMonsterPosition(move, xLoc, yLoc, monster);
    }

    public void postBattle(final Monster m, final int xLoc, final int yLoc,
            final boolean player) {
        this.mazeData.postBattle(m, xLoc, yLoc, player);
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

    public void tickTimers(final int floor) {
        this.mazeData.tickTimers(floor);
    }

    public void resetVisibleSquares() {
        this.mazeData.resetVisibleSquares();
    }

    public void updateVisibleSquares(final int xp, final int yp, final int zp) {
        this.mazeData.updateVisibleSquares(xp, yp, zp);
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
                try (DatabaseWriter writer = this.getLevelWriter()) {
                    // Save old level
                    this.writeDungeonLevel(writer);
                } catch (final IOException io) {
                    // Ignore
                }
            }
            this.activeLevel = level;
            try (DatabaseReader reader = this.getLevelReader()) {
                // Load new level
                this.readDungeonLevel(reader);
            } catch (final IOException io) {
                // Ignore
            }
        }
    }

    public boolean doesLevelExistOffset(final int level) {
        return this.activeLevel + level < this.levelCount
                && this.activeLevel + level >= 0;
    }

    public boolean addLevel(final int rows, final int cols, final int floors) {
        if (this.levelCount < Dungeon.getMaxLevels()) {
            if (this.mazeData != null) {
                try (DatabaseWriter writer = this.getLevelWriter()) {
                    // Save old level
                    this.writeDungeonLevel(writer);
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

    public boolean hasNote(final int x, final int y, final int z) {
        return this.mazeData.hasNote(x, y, z);
    }

    public void createNote(final int x, final int y, final int z) {
        this.mazeData.createNote(x, y, z);
    }

    public DungeonNote getNote(final int x, final int y, final int z) {
        return this.mazeData.getNote(x, y, z);
    }

    public AbstractDungeonObject getCell(final int row, final int col,
            final int floor, final int extra) {
        return this.mazeData.getCell(row, col, floor, extra);
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

    public boolean doesPlayerExist() {
        return this.mazeData.doesPlayerExist();
    }

    public boolean isSquareVisible(final int x1, final int y1, final int x2,
            final int y2) {
        return this.mazeData.isSquareVisible(x1, y1, x2, y2);
    }

    public void setCell(final AbstractDungeonObject mo, final int row,
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

    public void offsetPlayerLocationX(final int newPlayerRow) {
        this.mazeData.offsetPlayerRow(newPlayerRow);
    }

    public void offsetPlayerLocationY(final int newPlayerColumn) {
        this.mazeData.offsetPlayerColumn(newPlayerColumn);
    }

    public void offsetPlayerLocationW(final int newPlayerLevel) {
        this.locW += newPlayerLevel;
    }

    public void fillLevelRandomly() {
        this.mazeData.fillRandomly(this, this.activeLevel);
    }

    public void fullScanButton(final int l) {
        this.mazeData.fullScanButton(l);
    }

    public void fullScanExit() {
        this.mazeData.fullScanExit();
    }

    public void save() {
        this.mazeData.save();
    }

    public void restore() {
        this.mazeData.restore();
    }

    public Dungeon readDungeon() throws IOException {
        final Dungeon m = new Dungeon();
        // Attach handlers
        m.setPrefixHandler(this.prefixHandler);
        m.setSuffixHandler(this.suffixHandler);
        // Make base paths the same
        m.basePath = this.basePath;
        int version = 0;
        // Create metafile reader
        try (DatabaseReader metaReader = new DatabaseReader(
                m.basePath + File.separator + "metafile.xml")) {
            // Read metafile
            version = m.readDungeonMetafile(metaReader);
        } catch (final IOException ioe) {
            throw ioe;
        }
        // Create data reader
        try (DatabaseReader dataReader = m.getLevelReader()) {
            // Read data
            m.readDungeonLevel(dataReader, version);
            return m;
        } catch (final IOException ioe) {
            throw ioe;
        }
    }

    private DatabaseReader getLevelReader() throws IOException {
        return new DatabaseReader(this.basePath + File.separator + "level"
                + this.activeLevel + ".xml");
    }

    private int readDungeonMetafile(final DatabaseReader reader)
            throws IOException {
        int ver = FormatConstants.MAZE_FORMAT_LATEST;
        if (this.prefixHandler != null) {
            ver = this.prefixHandler.readPrefix(reader);
        }
        this.levelCount = reader.readInt();
        this.startW = reader.readInt();
        this.locW = reader.readInt();
        this.saveW = reader.readInt();
        this.activeLevel = reader.readInt();
        for (int y = 0; y < 4; y++) {
            this.savedStart[y] = reader.readInt();
        }
        if (this.suffixHandler != null) {
            this.suffixHandler.readSuffix(reader, ver);
        }
        return ver;
    }

    private void readDungeonLevel(final DatabaseReader reader)
            throws IOException {
        this.readDungeonLevel(reader, FormatConstants.MAZE_FORMAT_LATEST);
    }

    private void readDungeonLevel(final DatabaseReader reader,
            final int formatVersion) throws IOException {
        if (formatVersion == FormatConstants.MAZE_FORMAT_LATEST) {
            this.mazeData = LayeredTower.readLayeredDungeonV1(reader);
            this.mazeData.readSavedDungeonState(reader, formatVersion);
        } else {
            throw new VersionException(
                    "Unknown maze format version: " + formatVersion + "!");
        }
    }

    public void writeDungeon() throws IOException {
        try {
            // Create metafile writer
            try (DatabaseWriter metaWriter = new DatabaseWriter(
                    this.basePath + File.separator + "metafile.xml")) {
                // Write metafile
                this.writeDungeonMetafile(metaWriter);
            }
            // Create data writer
            try (DatabaseWriter dataWriter = this.getLevelWriter()) {
                // Write data
                this.writeDungeonLevel(dataWriter);
            }
        } catch (final IOException ioe) {
            throw ioe;
        }
    }

    private DatabaseWriter getLevelWriter() throws IOException {
        return new DatabaseWriter(this.basePath + File.separator + "level"
                + this.activeLevel + ".xml");
    }

    private void writeDungeonMetafile(final DatabaseWriter writer)
            throws IOException {
        if (this.prefixHandler != null) {
            this.prefixHandler.writePrefix(writer);
        }
        writer.writeInt(this.levelCount);
        writer.writeInt(this.startW);
        writer.writeInt(this.locW);
        writer.writeInt(this.saveW);
        writer.writeInt(this.activeLevel);
        for (int y = 0; y < 4; y++) {
            writer.writeInt(this.savedStart[y]);
        }
        if (this.suffixHandler != null) {
            this.suffixHandler.writeSuffix(writer);
        }
    }

    private void writeDungeonLevel(final DatabaseWriter writer)
            throws IOException {
        // Write the level
        this.mazeData.writeLayeredDungeon(writer);
        this.mazeData.writeSavedDungeonState(writer);
    }
}
