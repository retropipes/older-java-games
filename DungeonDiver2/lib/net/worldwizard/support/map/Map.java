/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.support.Support;
import net.worldwizard.support.map.generic.MapObject;
import net.worldwizard.support.map.generic.MapObjectList;
import net.worldwizard.support.map.objects.Empty;
import net.worldwizard.support.scripts.game.GameScriptArea;
import net.worldwizard.support.variables.FormatConstants;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class Map implements MapConstants {
    // Properties
    private LayeredTower mapData;
    private int startW;
    private int resultLevel;
    private int levelCount;
    private int activeLevel;
    private PrefixIO xmlPrefixHandler;
    private SuffixIO xmlSuffixHandler;
    private final int[] savedStart;
    private final String mapBasePath;
    private static final int MAX_LEVELS = Integer.MAX_VALUE;

    // Constructors
    public Map() {
        this.mapData = null;
        this.levelCount = 0;
        this.startW = 0;
        this.activeLevel = 0;
        this.xmlPrefixHandler = null;
        this.xmlSuffixHandler = null;
        this.savedStart = new int[4];
        this.mapBasePath = Support.getVariables().getBasePath() + File.separator
                + "maps" + File.separator;
        final File mapDir = new File(this.mapBasePath);
        if (!mapDir.exists()) {
            mapDir.mkdirs();
        }
    }

    // Methods
    public boolean doMapsExist() {
        return new File(this.mapBasePath).exists();
    }

    public Map getTemporaryBattleCopy() {
        final Map temp = new Map();
        temp.addLevel(16, 16, 1);
        final MapObjectList list = new MapObjectList();
        final RandomRange gen = new RandomRange(0,
                list.getAllGroundLayerObjects().length - 1);
        final MapObject rand = list.getAllGroundLayerObjects()[gen.generate()];
        temp.fillLevel(rand, new Empty());
        return temp;
    }

    public void setXPrefixHandler(final PrefixIO xph) {
        this.xmlPrefixHandler = xph;
    }

    public void setXSuffixHandler(final SuffixIO xsh) {
        this.xmlSuffixHandler = xsh;
    }

    public int getRegionSize() {
        return this.mapData.getRegionSize();
    }

    public void setGeneratorRandomness(final int value, final int max) {
        this.mapData.setGeneratorRandomness(value, max);
    }

    public boolean doesFloorExist(final int floor) {
        return this.mapData.doesFloorExist(floor);
    }

    public boolean isFloorAbove(final int floor) {
        return this.mapData.isFloorAbove(floor);
    }

    public boolean isFloorBelow(final int floor) {
        return this.mapData.isFloorBelow(floor);
    }

    public int getActiveLevelNumber() {
        return this.activeLevel;
    }

    public boolean isLevelOffsetValid(final int level) {
        return this.activeLevel - level >= 0;
    }

    public void switchLevel(final int level) {
        this.switchLevelInternal(level);
    }

    public void switchLevelOffset(final int level) {
        this.switchLevelInternal(this.activeLevel + level);
    }

    private void switchLevelInternal(final int level) {
        if (this.activeLevel != level) {
            if (this.mapData != null) {
                try {
                    // Save old level
                    final XDataWriter writer = this.getLevelWriterX();
                    this.writeMapLevelX(writer);
                    writer.close();
                } catch (final IOException io) {
                    // Ignore
                }
            }
            this.activeLevel = level;
            try {
                // Load new level
                final XDataReader reader = this.getLevelReaderX();
                this.readMapLevelX(reader);
                reader.close();
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

    public boolean isCutBlocked() {
        return this.levelCount <= 1;
    }

    public boolean addLevel(final int rows, final int cols, final int floors) {
        if (this.levelCount < Map.MAX_LEVELS) {
            if (this.mapData != null) {
                try {
                    // Save old level
                    final XDataWriter writer = this.getLevelWriterX();
                    this.writeMapLevelX(writer);
                    writer.close();
                } catch (final IOException io) {
                    // Ignore
                }
            }
            this.mapData = new LayeredTower(rows, cols, floors);
            this.levelCount++;
            this.activeLevel = this.levelCount - 1;
            return true;
        } else {
            return false;
        }
    }

    public MapObject getBattleCell(final int row, final int col) {
        return this.mapData.getCell(row, col, 0, MapConstants.LAYER_OBJECT);
    }

    public MapObject getBattleGround(final int row, final int col) {
        return this.mapData.getCell(row, col, 0, MapConstants.LAYER_GROUND);
    }

    public MapObject getCell(final int row, final int col, final int floor,
            final int extra) {
        return this.mapData.getCell(row, col, floor, extra);
    }

    public int getFindResultRow() {
        return this.mapData.getFindResultRow();
    }

    public int getFindResultColumn() {
        return this.mapData.getFindResultColumn();
    }

    public int getFindResultFloor() {
        return this.mapData.getFindResultFloor();
    }

    public int getFindResultLevel() {
        return this.resultLevel;
    }

    public int getStartRow() {
        return this.mapData.getStartRow();
    }

    public int getStartColumn() {
        return this.mapData.getStartColumn();
    }

    public int getStartFloor() {
        return this.mapData.getStartFloor();
    }

    public int getStartLevel() {
        return this.startW;
    }

    public int getRows() {
        return this.mapData.getRows();
    }

    public int getColumns() {
        return this.mapData.getColumns();
    }

    public int getFloors() {
        return this.mapData.getFloors();
    }

    public int getLevels() {
        return this.levelCount;
    }

    public int getVisionRadius() {
        return this.mapData.getVisionRadius();
    }

    public boolean doesPlayerExist() {
        return this.mapData.doesPlayerExist();
    }

    public void findStart() {
        this.mapData.findStart();
    }

    public boolean findPlayer() {
        if (this.activeLevel > this.levelCount) {
            return false;
        } else {
            return this.mapData.findPlayer();
        }
    }

    public void findAllObjectPairsAndSwap(final MapObject o1,
            final MapObject o2) {
        this.mapData.findAllObjectPairsAndSwap(o1, o2);
    }

    public boolean isSquareVisible(final int x1, final int y1, final int x2,
            final int y2, final int zLoc) {
        return this.mapData.isSquareVisible(x1, y1, x2, y2, zLoc);
    }

    public void setBattleCell(final MapObject mo, final int row,
            final int col) {
        this.mapData.setCell(mo, row, col, 0, MapConstants.LAYER_OBJECT);
    }

    public void setCell(final MapObject mo, final int row, final int col,
            final int floor, final int extra) {
        this.mapData.setCell(mo, row, col, floor, extra);
    }

    public void setStartRow(final int newStartRow) {
        this.mapData.setStartRow(newStartRow);
    }

    public void setStartColumn(final int newStartColumn) {
        this.mapData.setStartColumn(newStartColumn);
    }

    public void setStartFloor(final int newStartFloor) {
        this.mapData.setStartFloor(newStartFloor);
    }

    public void setStartLevel(final int newStartLevel) {
        this.startW = newStartLevel;
    }

    public void setVisionRadius(final int newVR) {
        this.mapData.setVisionRadius(newVR);
    }

    public void fillLevel(final MapObject bottom, final MapObject top) {
        this.mapData.fill(bottom, top);
        this.mapData.save();
    }

    public void fillLevelRandomly(final MapObject pass1FillBottom) {
        this.mapData.fillRandomly(this, this.activeLevel, pass1FillBottom);
        this.mapData.save();
    }

    public void save() {
        this.mapData.save();
    }

    public void restore() {
        this.mapData.restore();
    }

    public void saveStart() {
        this.savedStart[0] = this.startW;
        this.savedStart[1] = this.mapData.getStartRow();
        this.savedStart[2] = this.mapData.getStartColumn();
        this.savedStart[3] = this.mapData.getStartFloor();
    }

    public void restoreStart() {
        this.startW = this.savedStart[0];
        this.mapData.setStartRow(this.savedStart[1]);
        this.mapData.setStartColumn(this.savedStart[2]);
        this.mapData.setStartFloor(this.savedStart[3]);
    }

    public String[] getScriptAreaNames() {
        return this.mapData.getScriptAreaNames();
    }

    public ArrayList<GameScriptArea> getScriptAreasAtPoint(final Point p) {
        return this.mapData.getScriptAreasAtPoint(p);
    }

    public Map readMapX() throws IOException {
        final Map m = new Map();
        // Attach handlers
        m.setXPrefixHandler(this.xmlPrefixHandler);
        m.setXSuffixHandler(this.xmlSuffixHandler);
        // Create metafile reader
        final XDataReader metaReader = new XDataReader(
                this.mapBasePath + File.separator + "metafile.xml", "map");
        // Read metafile
        final int version = m.readMapMetafileX(metaReader);
        metaReader.close();
        // Create data reader
        final XDataReader dataReader = m.getLevelReaderX();
        // Read data
        m.readMapLevelX(dataReader, version);
        // Close reader
        dataReader.close();
        return m;
    }

    private XDataReader getLevelReaderX() throws IOException {
        return new XDataReader(this.mapBasePath + File.separator + "level"
                + this.activeLevel + ".xml", "level");
    }

    private int readMapMetafileX(final XDataReader reader) throws IOException {
        int ver = FormatConstants.SCENARIO_FORMAT_1;
        if (this.xmlPrefixHandler != null) {
            ver = this.xmlPrefixHandler.readPrefix(reader);
        }
        final int levels = reader.readInt();
        this.levelCount = levels;
        this.startW = reader.readInt();
        if (this.xmlSuffixHandler != null) {
            this.xmlSuffixHandler.readSuffix(reader, ver);
        }
        return ver;
    }

    private void readMapLevelX(final XDataReader reader) throws IOException {
        this.readMapLevelX(reader, FormatConstants.SCENARIO_FORMAT_1);
    }

    private void readMapLevelX(final XDataReader reader,
            final int formatVersion) throws IOException {
        if (formatVersion == FormatConstants.SCENARIO_FORMAT_1) {
            this.mapData = LayeredTower.readXLayeredTowerV1(reader,
                    formatVersion);
            this.mapData.readSavedTowerStateX(reader, formatVersion);
        } else {
            throw new IOException("Unknown map format version!");
        }
    }

    public void writeMapX() throws IOException {
        // Create metafile writer
        final XDataWriter metaWriter = new XDataWriter(
                this.mapBasePath + File.separator + "metafile.xml", "map");
        // Write metafile
        this.writeMapMetafileX(metaWriter);
        // Close writer
        metaWriter.close();
        // Create data writer
        final XDataWriter dataWriter = this.getLevelWriterX();
        // Write data
        this.writeMapLevelX(dataWriter);
        // Close writer
        dataWriter.close();
    }

    private XDataWriter getLevelWriterX() throws IOException {
        return new XDataWriter(this.mapBasePath + File.separator + "level"
                + this.activeLevel + ".xml", "level");
    }

    private void writeMapMetafileX(final XDataWriter writer)
            throws IOException {
        if (this.xmlPrefixHandler != null) {
            this.xmlPrefixHandler.writePrefix(writer);
        }
        writer.writeInt(this.levelCount);
        writer.writeInt(this.startW);
        if (this.xmlSuffixHandler != null) {
            this.xmlSuffixHandler.writeSuffix(writer);
        }
    }

    private void writeMapLevelX(final XDataWriter writer) throws IOException {
        // Write the level
        this.mapData.writeXLayeredTower(writer);
        this.mapData.writeSavedTowerStateX(writer);
    }
}
