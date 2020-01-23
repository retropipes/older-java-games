/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.map;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.puttysoftware.riskyrescue.Support;
import com.puttysoftware.riskyrescue.map.objects.Empty;
import com.puttysoftware.riskyrescue.map.objects.MapObject;
import com.puttysoftware.riskyrescue.map.objects.Tile;
import com.puttysoftware.riskyrescue.scenario.FormatConstants;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptArea;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class Map implements MapConstants {
    // Properties
    private final LayeredTower[] mapData;
    private int startW;
    private int locW;
    private int saveW;
    private int levelCount;
    private int activeLevel;
    private String mapTitle;
    private PrefixIO xmlPrefixHandler;
    private SuffixIO xmlSuffixHandler;
    private final String mapBasePath;
    private static final int MAX_LEVELS = 5;

    // Constructors
    public Map() {
        this.mapData = new LayeredTower[Map.MAX_LEVELS];
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

    public static int getLastLevelNumber() {
        return Map.MAX_LEVELS - 1;
    }

    public static Map getTemporaryBattleCopy() {
        final Map temp = new Map();
        temp.addLevel(Support.getBattleMapSize(), Support.getBattleMapSize(),
                Support.getBattleMapFloorSize());
        temp.fillLevel(new Tile(), new Empty());
        return temp;
    }

    public void rebuildGSA(final int mod) {
        this.mapData[this.activeLevel].rebuildGSA(mod);
    }

    public void setXPrefixHandler(final PrefixIO xph) {
        this.xmlPrefixHandler = xph;
    }

    public void setXSuffixHandler(final SuffixIO xsh) {
        this.xmlSuffixHandler = xsh;
    }

    public int getRegionSize() {
        return this.mapData[this.activeLevel].getRegionSize();
    }

    public boolean isLevelOffsetValid(final int level) {
        return this.activeLevel + level >= 0;
    }

    public void switchLevelOffset(final int level) {
        this.switchLevelInternal(this.activeLevel + level);
    }

    private void switchLevelInternal(final int level) {
        if (this.activeLevel != level) {
            this.activeLevel = level;
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

    public void resetVisibleSquares() {
        this.mapData[this.activeLevel].resetVisibleSquares();
    }

    public void updateVisibleSquares(final int xp, final int yp, final int zp) {
        this.mapData[this.activeLevel].updateVisibleSquares(xp, yp, zp);
    }

    public boolean addLevel(final int rows, final int cols, final int floors) {
        if (this.levelCount < Map.MAX_LEVELS) {
            this.levelCount++;
            this.activeLevel = this.levelCount - 1;
            this.mapData[this.activeLevel] = new LayeredTower(rows, cols,
                    floors);
            return true;
        } else {
            return false;
        }
    }

    public MapObject getBattleCell(final int row, final int col) {
        return this.mapData[this.activeLevel].getCell(row, col, 0,
                MapConstants.LAYER_OBJECT);
    }

    public MapObject getBattleGround(final int row, final int col) {
        return this.mapData[this.activeLevel].getCell(row, col, 0,
                MapConstants.LAYER_GROUND);
    }

    public MapObject getCell(final int row, final int col, final int floor,
            final int extra) {
        return this.mapData[this.activeLevel].getCell(row, col, floor, extra);
    }

    public int getPlayerLocationX() {
        return this.mapData[this.activeLevel].getPlayerRow();
    }

    public int getPlayerLocationY() {
        return this.mapData[this.activeLevel].getPlayerColumn();
    }

    public int getPlayerLocationZ() {
        return this.mapData[this.activeLevel].getPlayerFloor();
    }

    public int getPlayerLocationW() {
        return this.locW;
    }

    public void savePlayerLocation() {
        this.saveW = this.locW;
        this.mapData[this.activeLevel].savePlayerLocation();
    }

    public void restorePlayerLocation() {
        this.locW = this.saveW;
        this.mapData[this.activeLevel].restorePlayerLocation();
    }

    public int getRows() {
        return this.mapData[this.activeLevel].getRows();
    }

    public int getColumns() {
        return this.mapData[this.activeLevel].getColumns();
    }

    public boolean hasNote(final int x, final int y, final int z) {
        return this.mapData[this.activeLevel].hasNote(y, x, z);
    }

    public void createNote(final int x, final int y, final int z) {
        this.mapData[this.activeLevel].createNote(y, x, z);
    }

    public MapNote getNote(final int x, final int y, final int z) {
        return this.mapData[this.activeLevel].getNote(y, x, z);
    }

    public void findAllObjectPairsAndSwap(final MapObject o1,
            final MapObject o2) {
        this.mapData[this.activeLevel].findAllObjectPairsAndSwap(o1, o2);
    }

    public boolean isSquareVisible(final int x1, final int y1, final int x2,
            final int y2) {
        return this.mapData[this.activeLevel].isSquareVisible(x1, y1, x2, y2);
    }

    public void setBattleCell(final MapObject mo, final int row,
            final int col) {
        this.mapData[this.activeLevel].setCell(mo, row, col, 0,
                MapConstants.LAYER_OBJECT);
    }

    public void setCell(final MapObject mo, final int row, final int col,
            final int floor, final int extra) {
        this.mapData[this.activeLevel].setCell(mo, row, col, floor, extra);
    }

    public void offsetPlayerLocationX(final int newPlayerRow) {
        this.mapData[this.activeLevel].offsetPlayerRow(newPlayerRow);
    }

    public void offsetPlayerLocationY(final int newPlayerColumn) {
        this.mapData[this.activeLevel].offsetPlayerColumn(newPlayerColumn);
    }

    public void offsetPlayerLocationZ(final int newPlayerFloor) {
        this.mapData[this.activeLevel].offsetPlayerFloor(newPlayerFloor);
    }

    private void fillLevel(final MapObject bottom, final MapObject top) {
        this.mapData[this.activeLevel].fill(bottom, top);
    }

    public void fillLevelRandomly(final MapObject pass1FillBottom,
            final MapObject pass1FillTop) {
        this.mapData[this.activeLevel].fillRandomly(this, this.activeLevel,
                pass1FillBottom, pass1FillTop);
    }

    public ArrayList<InternalScriptArea> getScriptAreasAtPoint(final Point p,
            final int z) {
        return this.mapData[this.activeLevel].getScriptAreasAtPoint(p, z);
    }

    public Map readMapX() throws IOException {
        final Map m = new Map();
        // Attach handlers
        m.setXPrefixHandler(this.xmlPrefixHandler);
        m.setXSuffixHandler(this.xmlSuffixHandler);
        int version = 0;
        // Create metafile reader
        try (XDataReader metaReader = new XDataReader(
                this.mapBasePath + File.separator + "metafile.xml", "map")) {
            // Read metafile
            version = m.readMapMetafileX(metaReader);
        } catch (final IOException ioe) {
            throw ioe;
        }
        // Create data reader
        try (XDataReader dataReader = m.getLevelReaderX()) {
            // Read data
            m.readMapLevelX(dataReader, version);
        } catch (final IOException ioe) {
            throw ioe;
        }
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

    private void readMapLevelX(final XDataReader reader,
            final int formatVersion) throws IOException {
        if (formatVersion == FormatConstants.SCENARIO_FORMAT_1) {
            this.mapData[this.activeLevel] = LayeredTower
                    .readXLayeredTower(reader, formatVersion);
        } else {
            throw new IOException("Unknown map format version!");
        }
    }

    public void writeMapX() throws IOException {
        // Create metafile writer
        try (XDataWriter metaWriter = new XDataWriter(
                this.mapBasePath + File.separator + "metafile.xml", "map")) {
            // Write metafile
            this.writeMapMetafileX(metaWriter);
        } catch (final IOException ioe) {
            throw ioe;
        }
        // Create data writer
        try (XDataWriter dataWriter = this.getLevelWriterX()) {
            // Write data
            this.writeMapLevelX(dataWriter);
        } catch (final IOException ioe) {
            throw ioe;
        }
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
        this.mapData[this.activeLevel].writeXLayeredTower(writer);
    }
}
