/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.puttysoftware.dungeondiver3.support.Support;
import com.puttysoftware.dungeondiver3.support.map.generic.MapObject;
import com.puttysoftware.dungeondiver3.support.map.generic.MapObjectList;
import com.puttysoftware.dungeondiver3.support.map.objects.Empty;
import com.puttysoftware.dungeondiver3.support.prefs.LocalPreferencesManager;
import com.puttysoftware.dungeondiver3.support.scenario.FormatConstants;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptArea;
import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class Map implements MapConstants {
    // Properties
    private LayeredTower mapData;
    private int startW;
    private int locW;
    private int saveW;
    private int levelCount;
    private int activeLevel;
    private String mapTitle;
    private PrefixIO xmlPrefixHandler;
    private SuffixIO xmlSuffixHandler;
    private final String mapBasePath;
    private static final int MAX_LEVELS = Integer.MAX_VALUE;

    // Constructors
    public Map() {
        this.mapData = null;
        this.levelCount = 0;
        this.startW = 0;
        this.locW = 0;
        this.saveW = 0;
        this.activeLevel = 0;
        this.xmlPrefixHandler = null;
        this.xmlSuffixHandler = null;
        this.mapTitle = "Untitled Map";
        this.mapBasePath = Support.getScenario().getBasePath() + File.separator
                + "maps" + File.separator;
    }

    // Methods
    public Map createMaps() {
        final File mapDir = new File(this.mapBasePath);
        if (!mapDir.exists()) {
            mapDir.mkdirs();
        }
        return this;
    }

    public static Map getTemporaryBattleCopy() {
        final Map temp = new Map();
        temp.addLevel(Support.getBattleMapSize(), Support.getBattleMapSize(),
                1);
        final MapObjectList list = new MapObjectList();
        final MapObject[] glo = list.getAllGroundLayerObjects();
        final RandomRange gen = new RandomRange(0, glo.length - 1);
        final MapObject rand = glo[gen.generate()];
        if (LocalPreferencesManager.getRandomBattleEnvironment()) {
            temp.fillLevelRandomlyInBattle(rand, new Empty());
        } else {
            temp.fillLevel(rand, new Empty());
        }
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

    public int getActiveLevelNumber() {
        return this.activeLevel;
    }

    public boolean isLevelOffsetValid(final int level) {
        return this.activeLevel + level >= 0;
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

    public boolean doesLevelExistOffset(final int level) {
        if (this.activeLevel + level < 0) {
            return false;
        } else if (this.activeLevel + level >= this.levelCount) {
            return false;
        } else {
            return true;
        }
    }

    public boolean doesFloorExist(final int floor) {
        return this.mapData.doesFloorExist(floor);
    }

    public boolean isFloorAbove(final int floor) {
        return this.mapData.isFloorAbove(floor);
    }

    public static boolean isFloorBelow(final int floor) {
        return LayeredTower.isFloorBelow(floor);
    }

    public boolean isMoveOK(final int x, final int y, final int z) {
        return this.mapData.isMoveOK(x, y, z);
    }

    public void resetVisibleSquares() {
        this.mapData.resetVisibleSquares();
    }

    public void updateVisibleSquares(final int xp, final int yp, final int zp) {
        this.mapData.updateVisibleSquares(xp, yp, zp);
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

    public int getPlayerLocationX() {
        return this.mapData.getPlayerRow();
    }

    public int getPlayerLocationY() {
        return this.mapData.getPlayerColumn();
    }

    public int getPlayerLocationZ() {
        return this.mapData.getPlayerFloor();
    }

    public int getPlayerLocationW() {
        return this.locW;
    }

    public void savePlayerLocation() {
        this.saveW = this.locW;
        this.mapData.savePlayerLocation();
    }

    public void restorePlayerLocation() {
        this.locW = this.saveW;
        this.mapData.restorePlayerLocation();
    }

    public void setPlayerLocation(final int x, final int y, final int z,
            final int w) {
        this.setPlayerLocationX(x);
        this.setPlayerLocationY(y);
        this.setPlayerLocationZ(z);
        this.setPlayerLocationW(w);
    }

    private void setPlayerLocationX(final int newPlayerRow) {
        this.mapData.setPlayerRow(newPlayerRow);
    }

    private void setPlayerLocationY(final int newPlayerColumn) {
        this.mapData.setPlayerColumn(newPlayerColumn);
    }

    private void setPlayerLocationZ(final int newPlayerFloor) {
        this.mapData.setPlayerFloor(newPlayerFloor);
    }

    public void setPlayerLocationW(final int newPlayerLevel) {
        this.locW = newPlayerLevel;
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

    public boolean hasNote(final int x, final int y, final int z) {
        return this.mapData.hasNote(y, x, z);
    }

    public void createNote(final int x, final int y, final int z) {
        this.mapData.createNote(y, x, z);
    }

    public MapNote getNote(final int x, final int y, final int z) {
        return this.mapData.getNote(y, x, z);
    }

    public boolean doesPlayerExist() {
        return this.mapData.doesPlayerExist();
    }

    public void findStart() {
        this.mapData.findStart();
    }

    public void findAllObjectPairsAndSwap(final MapObject o1,
            final MapObject o2) {
        this.mapData.findAllObjectPairsAndSwap(o1, o2);
    }

    public boolean isSquareVisible(final int x1, final int y1, final int x2,
            final int y2) {
        return this.mapData.isSquareVisible(x1, y1, x2, y2);
    }

    public void setBattleCell(final MapObject mo, final int row,
            final int col) {
        this.mapData.setCell(mo, row, col, 0, MapConstants.LAYER_OBJECT);
    }

    public void setCell(final MapObject mo, final int row, final int col,
            final int floor, final int extra) {
        this.mapData.setCell(mo, row, col, floor, extra);
    }

    public void offsetPlayerLocationX(final int newPlayerRow) {
        this.mapData.offsetPlayerRow(newPlayerRow);
    }

    public void offsetPlayerLocationY(final int newPlayerColumn) {
        this.mapData.offsetPlayerColumn(newPlayerColumn);
    }

    public void offsetPlayerLocationZ(final int newPlayerFloor) {
        this.mapData.offsetPlayerFloor(newPlayerFloor);
    }

    private void fillLevel(final MapObject bottom, final MapObject top) {
        this.mapData.fill(bottom, top);
    }

    public void fillLevelRandomly(final MapObject pass1FillBottom,
            final MapObject pass1FillTop) {
        this.mapData.fillRandomly(this, this.activeLevel, pass1FillBottom,
                pass1FillTop);
    }

    private void fillLevelRandomlyInBattle(final MapObject pass1FillBottom,
            final MapObject pass1FillTop) {
        this.mapData.fillRandomlyInBattle(this, this.activeLevel,
                pass1FillBottom, pass1FillTop);
    }

    public void save() {
        this.mapData.save();
    }

    public void restore() {
        this.mapData.restore();
    }

    public ArrayList<InternalScriptArea> getScriptAreasAtPoint(final Point p,
            final int z) {
        return this.mapData.getScriptAreasAtPoint(p, z);
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
        int ver = FormatConstants.LATEST_SCENARIO_FORMAT;
        if (this.xmlPrefixHandler != null) {
            ver = this.xmlPrefixHandler.readPrefix(reader);
        }
        final int levels = reader.readInt();
        this.levelCount = levels;
        this.startW = reader.readInt();
        this.locW = reader.readInt();
        this.saveW = reader.readInt();
        this.mapTitle = reader.readString();
        if (this.xmlSuffixHandler != null) {
            this.xmlSuffixHandler.readSuffix(reader, ver);
        }
        return ver;
    }

    private void readMapLevelX(final XDataReader reader) throws IOException {
        this.readMapLevelX(reader, FormatConstants.LATEST_SCENARIO_FORMAT);
    }

    private void readMapLevelX(final XDataReader reader,
            final int formatVersion) throws IOException {
        if (formatVersion == FormatConstants.SCENARIO_FORMAT_1
                || formatVersion == FormatConstants.SCENARIO_FORMAT_2) {
            this.mapData = LayeredTower.readXLayeredTower(reader,
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
        writer.writeInt(this.locW);
        writer.writeInt(this.saveW);
        writer.writeString(this.mapTitle);
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
