/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.support.map.generic.MapObject;
import net.worldwizard.support.map.generic.MapObjectList;
import net.worldwizard.support.map.generic.RandomGenerationRule;
import net.worldwizard.support.map.objects.DarkGem;
import net.worldwizard.support.map.objects.Empty;
import net.worldwizard.support.map.objects.LightGem;
import net.worldwizard.support.scripts.game.GameActionCode;
import net.worldwizard.support.scripts.game.GameScriptArea;
import net.worldwizard.support.scripts.game.GameScriptEntry;
import net.worldwizard.support.scripts.game.GameScriptEntryArgument;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

class LayeredTower implements Cloneable {
    // Properties
    private LowLevelDataStore data;
    private SavedTowerState savedTowerState;
    private final int[] playerData;
    private final int[] findResult;
    private int visionRadius;
    private int regionSize;
    private final ArrayList<GameScriptArea> scriptAreas;
    private static final int MAX_VISION_RADIUS = 9;
    private static final int DEFAULT_VISION_RADIUS = 7;
    private static final int MIN_VISION_RADIUS = 1;

    // Constructors
    LayeredTower(final int rows, final int cols, final int floors) {
        this.data = new LowLevelDataStore(cols, rows, floors,
                MapConstants.LAYER_COUNT);
        this.savedTowerState = new SavedTowerState(rows, cols, floors);
        this.playerData = new int[3];
        Arrays.fill(this.playerData, -1);
        this.findResult = new int[3];
        Arrays.fill(this.findResult, -1);
        this.visionRadius = LayeredTower.DEFAULT_VISION_RADIUS;
        this.regionSize = 8;
        this.scriptAreas = new ArrayList<>();
        // Build and add global script area
        final GameScriptArea globalScriptArea = new GameScriptArea();
        globalScriptArea.setUpperLeft(new Point(0, 0));
        globalScriptArea.setLowerRight(new Point(rows - 1, cols - 1));
        final GameScriptEntry act0 = new GameScriptEntry();
        act0.setActionCode(GameActionCode.RANDOM_CHANCE);
        act0.addActionArg(new GameScriptEntryArgument(1000));
        act0.finalizeActionArgs();
        globalScriptArea.addAction(act0);
        final GameScriptEntry act1 = new GameScriptEntry();
        act1.setActionCode(GameActionCode.BATTLE);
        globalScriptArea.addAction(act1);
        globalScriptArea.finalizeActions();
        this.scriptAreas.add(globalScriptArea);
    }

    // Methods
    @Override
    public LayeredTower clone() {
        final LayeredTower copy = new LayeredTower(this.getRows(),
                this.getColumns(), this.getFloors());
        copy.data = (LowLevelDataStore) this.data.clone();
        copy.savedTowerState = this.savedTowerState.clone();
        System.arraycopy(this.playerData, 0, copy.playerData, 0,
                this.playerData.length);
        System.arraycopy(this.findResult, 0, copy.findResult, 0,
                this.findResult.length);
        copy.visionRadius = this.visionRadius;
        copy.regionSize = this.regionSize;
        return copy;
    }

    public int getRegionSize() {
        return this.regionSize;
    }

    public void setGeneratorRandomness(final int value, final int max) {
        this.regionSize = 2 ^ max - value;
    }

    public boolean doesFloorExist(final int floor) {
        return floor < this.getFloors() && floor >= 0;
    }

    public boolean isFloorAbove(final int floor) {
        return floor + 1 < this.getFloors();
    }

    public boolean isFloorBelow(final int floor) {
        return floor - 1 >= 0;
    }

    public MapObject getCell(final int row, final int col, final int floor,
            final int extra) {
        return this.data.getCell(col, row, floor, extra);
    }

    public int getFindResultRow() {
        return this.findResult[1];
    }

    public int getFindResultColumn() {
        return this.findResult[0];
    }

    public int getFindResultFloor() {
        return this.findResult[2];
    }

    public int getStartRow() {
        return this.playerData[1];
    }

    public int getStartColumn() {
        return this.playerData[0];
    }

    public int getStartFloor() {
        return this.playerData[2];
    }

    public int getRows() {
        return this.data.getShape()[1];
    }

    public int getColumns() {
        return this.data.getShape()[0];
    }

    public int getFloors() {
        return this.data.getShape()[2];
    }

    public int getVisionRadius() {
        return this.visionRadius;
    }

    public boolean doesPlayerExist() {
        boolean res = true;
        for (final int element : this.playerData) {
            res = res && element != -1;
        }
        return res;
    }

    public void findStart() {
        int y, x, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final MapObject mo = this.getCell(y, x, z,
                            MapConstants.LAYER_OBJECT);
                    if (mo != null) {
                        if (mo.getName().equals("Player")) {
                            this.playerData[1] = x;
                            this.playerData[0] = y;
                            this.playerData[2] = z;
                        }
                    }
                }
            }
        }
    }

    public boolean findPlayer() {
        int y, x, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final MapObject mo = this.getCell(y, x, z,
                            MapConstants.LAYER_OBJECT);
                    if (mo != null) {
                        if (mo.getName().equals("Player")) {
                            this.findResult[1] = x;
                            this.findResult[0] = y;
                            this.findResult[2] = z;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void findAllObjectPairsAndSwap(final MapObject o1,
            final MapObject o2) {
        int y, x, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final MapObject mo = this.getCell(y, x, z,
                            MapConstants.LAYER_OBJECT);
                    if (mo != null) {
                        if (mo.getName().equals(o1.getName())) {
                            this.setCell(o2, y, x, z,
                                    MapConstants.LAYER_OBJECT);
                        } else if (mo.getName().equals(o2.getName())) {
                            this.setCell(o1, y, x, z,
                                    MapConstants.LAYER_OBJECT);
                        }
                    }
                }
            }
        }
    }

    public boolean radialScan(final int x, final int y, final int z,
            final int l, final int r, final String targetName) {
        int u, v;
        u = v = 0;
        // Perform the scan
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                try {
                    final String testName = this.getCell(u, v, z, l).getName();
                    if (testName.equals(targetName)) {
                        return true;
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Do nothing
                }
            }
        }
        return false;
    }

    public boolean isSquareVisible(final int x1, final int y1, final int x2,
            final int y2, final int zLoc) {
        final LightGem lg = new LightGem();
        final DarkGem dg = new DarkGem();
        boolean result = true;
        final int xDist = LayeredTower.pointDistance(x1, x2);
        final int yDist = LayeredTower.pointDistance(y1, y2);
        if (xDist <= this.visionRadius && yDist <= this.visionRadius) {
            if (this.radialScan(x2, y2, zLoc, MapConstants.LAYER_OBJECT,
                    dg.getEffectRadius(), dg.getName())) {
                result = false;
            } else {
                result = true;
            }
        } else {
            if (this.radialScan(x2, y2, zLoc, MapConstants.LAYER_OBJECT,
                    lg.getEffectRadius(), lg.getName())) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
    }

    private static int pointDistance(final int u, final int v) {
        return Math.abs(u - v);
    }

    public void setCell(final MapObject mo, final int row, final int col,
            final int floor, final int extra) {
        this.data.setCell(mo, col, row, floor, extra);
    }

    public void setStartRow(final int newStartRow) {
        this.playerData[1] = newStartRow;
    }

    public void setStartColumn(final int newStartColumn) {
        this.playerData[0] = newStartColumn;
    }

    public void setStartFloor(final int newStartFloor) {
        this.playerData[2] = newStartFloor;
    }

    public void setVisionRadius(final int newVR) {
        int fVR = newVR;
        if (fVR > LayeredTower.MAX_VISION_RADIUS) {
            fVR = LayeredTower.MAX_VISION_RADIUS;
        }
        if (fVR < LayeredTower.MIN_VISION_RADIUS) {
            fVR = LayeredTower.MIN_VISION_RADIUS;
        }
        this.visionRadius = fVR;
    }

    public void setVisionRadiusToMaximum() {
        this.visionRadius = LayeredTower.MAX_VISION_RADIUS;
    }

    public void fill(final MapObject bottom, final MapObject top) {
        int y, x, z, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < MapConstants.LAYER_COUNT; e++) {
                        if (e == MapConstants.LAYER_GROUND) {
                            this.setCell(bottom, y, x, z, e);
                        } else {
                            this.setCell(top, y, x, z, e);
                        }
                    }
                }
            }
        }
    }

    public void fillFloor(final MapObject bottom, final MapObject top,
            final int z) {
        int x, y, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (e = 0; e < MapConstants.LAYER_COUNT; e++) {
                    if (e == MapConstants.LAYER_GROUND) {
                        this.setCell(bottom, y, x, z, e);
                    } else {
                        this.setCell(top, y, x, z, e);
                    }
                }
            }
        }
    }

    public void fillRandomly(final Map map, final int w,
            final MapObject pass1FillBottom) {
        for (int z = 0; z < this.getFloors(); z++) {
            this.fillFloorRandomly(map, z, w, pass1FillBottom);
        }
    }

    public void fillFloorRandomly(final Map map, final int z, final int w,
            final MapObject pass1FillBottom) {
        // Pre-Pass
        final MapObjectList objects = new MapObjectList();
        final MapObject pass1FillTop = new Empty();
        RandomRange r = null;
        int x, y, e, u, v;
        // Pass 1
        this.fillFloor(pass1FillBottom, pass1FillTop, z);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        for (e = 0; e < MapConstants.LAYER_COUNT; e++) {
            final MapObject[] objectsWithoutPrerequisites = objects
                    .getAllNotRequired(e);
            if (objectsWithoutPrerequisites != null) {
                r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
                if (e == MapConstants.LAYER_GROUND) {
                    for (x = 0; x < columns; x += this.regionSize) {
                        for (y = 0; y < rows; y += this.regionSize) {
                            final MapObject placeObj = objectsWithoutPrerequisites[r
                                    .generate()];
                            final boolean okay = placeObj
                                    .shouldGenerateObject(map, x, y, z, w, e);
                            if (okay) {
                                for (u = 0; u < this.regionSize; u++) {
                                    for (v = 0; v < this.regionSize; v++) {
                                        this.setCell(
                                                objects.getNewInstanceByName(
                                                        placeObj.getName()),
                                                v + x, u + y, z, e);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (x = 0; x < columns; x++) {
                        for (y = 0; y < rows; y++) {
                            final MapObject placeObj = objectsWithoutPrerequisites[r
                                    .generate()];
                            final boolean okay = placeObj
                                    .shouldGenerateObject(map, x, y, z, w, e);
                            if (okay) {
                                this.setCell(
                                        objects.getNewInstanceByName(
                                                placeObj.getName()),
                                        y, x, z, e);
                            }
                        }
                    }
                }
            }
        }
        // Pass 3
        for (int layer = 0; layer < MapConstants.LAYER_COUNT; layer++) {
            final MapObject[] requiredObjects = objects.getAllRequired(layer);
            if (requiredObjects != null) {
                final RandomRange row = new RandomRange(0, this.getRows() - 1);
                final RandomRange column = new RandomRange(0,
                        this.getColumns() - 1);
                int randomColumn, randomRow;
                for (x = 0; x < requiredObjects.length; x++) {
                    randomRow = row.generate();
                    randomColumn = column.generate();
                    final MapObject currObj = requiredObjects[x];
                    final int min = currObj.getMinimumRequiredQuantity(map);
                    int max = currObj.getMaximumRequiredQuantity(map);
                    if (max == RandomGenerationRule.NO_LIMIT) {
                        // Maximum undefined, so define it relative to this map
                        max = this.getRows() * this.getColumns() / 10;
                        // Make sure max is valid
                        if (max < min) {
                            max = min;
                        }
                    }
                    final RandomRange howMany = new RandomRange(min, max);
                    final int generateHowMany = howMany.generate();
                    for (y = 0; y < generateHowMany; y++) {
                        randomRow = row.generate();
                        randomColumn = column.generate();
                        if (currObj.shouldGenerateObject(map, randomRow,
                                randomColumn, z, w, layer)) {
                            this.setCell(
                                    objects.getNewInstanceByName(
                                            currObj.getName()),
                                    randomColumn, randomRow, z, layer);
                        } else {
                            while (!currObj.shouldGenerateObject(map,
                                    randomColumn, randomRow, z, w, layer)) {
                                randomRow = row.generate();
                                randomColumn = column.generate();
                            }
                            this.setCell(
                                    objects.getNewInstanceByName(
                                            currObj.getName()),
                                    randomColumn, randomRow, z, layer);
                        }
                    }
                }
            }
        }
    }

    public void save() {
        int y, x, z, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < MapConstants.LAYER_COUNT; e++) {
                        this.savedTowerState.setDataCell(
                                this.getCell(y, x, z, e), x, y, z, e);
                    }
                }
            }
        }
    }

    public void restore() {
        int y, x, z, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < MapConstants.LAYER_COUNT; e++) {
                        this.setCell(
                                this.savedTowerState.getDataCell(x, y, z, e), y,
                                x, z, e);
                    }
                }
            }
        }
    }

    public String[] getScriptAreaNames() {
        final ArrayList<String> temp = new ArrayList<>();
        for (final GameScriptArea gsa : this.scriptAreas) {
            temp.add(gsa.getName());
        }
        return temp.toArray(new String[temp.size()]);
    }

    public ArrayList<GameScriptArea> getScriptAreasAtPoint(final Point p) {
        final ArrayList<GameScriptArea> retVal = new ArrayList<>();
        for (final GameScriptArea gsa : this.scriptAreas) {
            if (p.x >= gsa.getUpperLeft().x && p.x <= gsa.getLowerRight().x
                    && p.y >= gsa.getUpperLeft().y
                    && p.y <= gsa.getLowerRight().y) {
                retVal.add(gsa);
            }
        }
        return retVal;
    }

    public void writeXLayeredTower(final XDataWriter writer)
            throws IOException {
        int y, x, z, e;
        writer.writeInt(this.getColumns());
        writer.writeInt(this.getRows());
        writer.writeInt(this.getFloors());
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < MapConstants.LAYER_COUNT; e++) {
                        this.getCell(y, x, z, e).writeMapObjectX(writer);
                    }
                }
            }
        }
        for (y = 0; y < 3; y++) {
            writer.writeInt(this.playerData[y]);
        }
        writer.writeInt(this.visionRadius);
        writer.writeInt(this.scriptAreas.size());
        for (final GameScriptArea gsa : this.scriptAreas) {
            gsa.write(writer);
        }
    }

    public static LayeredTower readXLayeredTowerV1(final XDataReader reader,
            final int ver) throws IOException {
        final MapObjectList objects = new MapObjectList();
        int y, x, z, e, mapSizeX, mapSizeY, mapSizeZ;
        mapSizeX = reader.readInt();
        mapSizeY = reader.readInt();
        mapSizeZ = reader.readInt();
        final LayeredTower lt = new LayeredTower(mapSizeX, mapSizeY, mapSizeZ);
        for (x = 0; x < lt.getColumns(); x++) {
            for (y = 0; y < lt.getRows(); y++) {
                for (z = 0; z < lt.getFloors(); z++) {
                    for (e = 0; e < MapConstants.LAYER_COUNT; e++) {
                        lt.setCell(objects.readMapObjectX(reader, ver), y, x, z,
                                e);
                        if (lt.getCell(y, x, z, e) == null) {
                            return null;
                        }
                    }
                }
            }
        }
        for (y = 0; y < 3; y++) {
            lt.playerData[y] = reader.readInt();
        }
        lt.visionRadius = reader.readInt();
        final int size = reader.readInt();
        for (int q = 0; q < size; q++) {
            lt.scriptAreas.add(GameScriptArea.read(reader));
        }
        return lt;
    }

    public void writeSavedTowerStateX(final XDataWriter writer)
            throws IOException {
        this.savedTowerState.writeSavedTowerStateX(writer);
    }

    public void readSavedTowerStateX(final XDataReader reader,
            final int formatVersion) throws IOException {
        this.savedTowerState = SavedTowerState.readSavedTowerStateX(reader,
                formatVersion);
    }
}
