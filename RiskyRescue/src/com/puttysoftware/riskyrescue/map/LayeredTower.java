/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.map;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.riskyrescue.creatures.party.PartyManager;
import com.puttysoftware.riskyrescue.map.objects.MapObject;
import com.puttysoftware.riskyrescue.map.objects.MapObjectList;
import com.puttysoftware.riskyrescue.map.objects.RandomGenerationRule;
import com.puttysoftware.riskyrescue.map.objects.StairsUp;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptArea;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptEntry;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptEntryArgument;
import com.puttysoftware.storage.FlagStorage;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

class LayeredTower {
    // Properties
    private final LowLevelDataStore data;
    private final FlagStorage visionData;
    private final LowLevelNoteDataStore noteData;
    private final int[] playerLocationData;
    private final int[] savedPlayerLocationData;
    private boolean horizontalWraparoundEnabled;
    private boolean verticalWraparoundEnabled;
    private boolean thirdDimensionWraparoundEnabled;
    private String levelTitle;
    private int visionMode;
    private int visionModeExploreRadius;
    private final ArrayList<InternalScriptArea> scriptAreas;
    private final int regionSize;

    // Constructors
    LayeredTower(final int rows, final int cols, final int floors) {
        this.data = new LowLevelDataStore(cols, rows, floors,
                MapConstants.LAYER_COUNT);
        this.noteData = new LowLevelNoteDataStore(cols, rows, floors);
        this.visionData = new FlagStorage(cols, rows, floors);
        this.playerLocationData = new int[3];
        Arrays.fill(this.playerLocationData, -1);
        this.savedPlayerLocationData = new int[3];
        Arrays.fill(this.savedPlayerLocationData, -1);
        this.horizontalWraparoundEnabled = false;
        this.verticalWraparoundEnabled = false;
        this.thirdDimensionWraparoundEnabled = false;
        this.levelTitle = "Untitled Level";
        this.scriptAreas = new ArrayList<>();
        this.visionMode = VisionModeConstants.VISION_MODE_EXPLORE;
        this.visionModeExploreRadius = 3;
        this.regionSize = 8;
        this.rebuildGSA(0);
    }

    // Methods
    final void rebuildGSA(final int mod) {
        // Rebuild and add global script area
        final InternalScriptArea globalScriptArea = new InternalScriptArea();
        globalScriptArea.setUpperLeft(new Point(0, 0));
        globalScriptArea.setLowerRight(
                new Point(this.getRows() - 1, this.getColumns() - 1));
        final InternalScriptEntry act0 = new InternalScriptEntry();
        act0.setActionCode(InternalScriptActionCode.RANDOM_CHANCE);
        act0.addActionArg(
                new InternalScriptEntryArgument(Math.max(500 + mod * 250, 0)));
        act0.finalizeActionArgs();
        globalScriptArea.addAction(act0);
        final InternalScriptEntry act1 = new InternalScriptEntry();
        act1.setActionCode(InternalScriptActionCode.BATTLE);
        globalScriptArea.addAction(act1);
        globalScriptArea.finalizeActions();
        this.scriptAreas.clear();
        this.scriptAreas.add(globalScriptArea);
    }

    int getRegionSize() {
        return this.regionSize;
    }

    MapObject getCell(final int row, final int col, final int floor,
            final int extra) {
        int fR = row;
        int fC = col;
        int fF = floor;
        if (this.verticalWraparoundEnabled) {
            fC = this.normalizeColumn(fC);
        }
        if (this.horizontalWraparoundEnabled) {
            fR = this.normalizeRow(fR);
        }
        if (this.thirdDimensionWraparoundEnabled) {
            fF = this.normalizeFloor(fF);
        }
        return this.data.getMapCell(fC, fR, fF, extra);
    }

    int getPlayerRow() {
        return this.playerLocationData[1];
    }

    int getPlayerColumn() {
        return this.playerLocationData[0];
    }

    int getPlayerFloor() {
        return this.playerLocationData[2];
    }

    int getRows() {
        return this.data.getShape()[1];
    }

    int getColumns() {
        return this.data.getShape()[0];
    }

    int getFloors() {
        return this.data.getShape()[2];
    }

    boolean hasNote(final int x, final int y, final int z) {
        return this.noteData.getNote(y, x, z) != null;
    }

    void createNote(final int x, final int y, final int z) {
        this.noteData.setNote(new MapNote(), y, x, z);
    }

    MapNote getNote(final int x, final int y, final int z) {
        return this.noteData.getNote(y, x, z);
    }

    void findAllObjectPairsAndSwap(final MapObject o1, final MapObject o2) {
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

    void resetVisibleSquares() {
        for (int x = 0; x < this.getRows(); x++) {
            for (int y = 0; y < this.getColumns(); y++) {
                for (int z = 0; z < this.getFloors(); z++) {
                    this.visionData.setCell(false, x, y, z);
                }
            }
        }
    }

    void updateVisibleSquares(final int xp, final int yp, final int zp) {
        if ((this.visionMode
                | VisionModeConstants.VISION_MODE_EXPLORE) == this.visionMode) {
            for (int x = xp - this.visionModeExploreRadius; x <= xp
                    + this.visionModeExploreRadius; x++) {
                for (int y = yp - this.visionModeExploreRadius; y <= yp
                        + this.visionModeExploreRadius; y++) {
                    int fx, fy;
                    if (this.isHorizontalWraparoundEnabled()) {
                        fx = this.normalizeColumn(x);
                    } else {
                        fx = x;
                    }
                    if (this.isVerticalWraparoundEnabled()) {
                        fy = this.normalizeRow(y);
                    } else {
                        fy = y;
                    }
                    boolean alreadyVisible = false;
                    try {
                        alreadyVisible = this.visionData.getCell(fx, fy, zp);
                    } catch (final ArrayIndexOutOfBoundsException aioobe) {
                        // Ignore
                    }
                    if (!alreadyVisible) {
                        if ((this.visionMode
                                | VisionModeConstants.VISION_MODE_LOS) == this.visionMode) {
                            if (this.isSquareVisibleLOS(x, y, xp, yp)) {
                                try {
                                    this.visionData.setCell(true, fx, fy, zp);
                                } catch (final ArrayIndexOutOfBoundsException aioobe) {
                                    // Ignore
                                }
                            }
                        } else {
                            try {
                                this.visionData.setCell(true, fx, fy, zp);
                            } catch (final ArrayIndexOutOfBoundsException aioobe) {
                                // Ignore
                            }
                        }
                    }
                }
            }
        }
    }

    boolean isSquareVisible(final int x1, final int y1, final int x2,
            final int y2) {
        if (this.visionMode == VisionModeConstants.VISION_MODE_NONE) {
            return LayeredTower.isSquareVisibleNone();
        } else {
            boolean result = false;
            if ((this.visionMode
                    | VisionModeConstants.VISION_MODE_EXPLORE) == this.visionMode) {
                result = result || this.isSquareVisibleExplore(x2, y2);
                if (result && (this.visionMode
                        | VisionModeConstants.VISION_MODE_LOS) == this.visionMode) {
                    if (this.areCoordsInBounds(x1, y1, x2, y2)) {
                        // In bounds
                        result = result
                                && this.isSquareVisibleLOS(x1, y1, x2, y2);
                    } else {
                        // Out of bounds
                        result = result
                                && this.isSquareVisibleLOS(x1, y1, x2, y2);
                    }
                }
            } else {
                if (this.areCoordsInBounds(x1, y1, x2, y2)) {
                    // In bounds
                    result = result || this.isSquareVisibleLOS(x1, y1, x2, y2);
                } else {
                    // Out of bounds
                    result = result && this.isSquareVisibleLOS(x1, y1, x2, y2);
                }
            }
            return result;
        }
    }

    private boolean areCoordsInBounds(final int x1, final int y1, final int x2,
            final int y2) {
        int fx1, fx2, fy1, fy2;
        if (this.isHorizontalWraparoundEnabled()) {
            fx1 = this.normalizeColumn(x1);
            fx2 = this.normalizeColumn(x2);
        } else {
            fx1 = x1;
            fx2 = x2;
        }
        if (this.isVerticalWraparoundEnabled()) {
            fy1 = this.normalizeRow(y1);
            fy2 = this.normalizeRow(y2);
        } else {
            fy1 = y1;
            fy2 = y2;
        }
        return fx1 >= 0 && fx1 <= this.getRows() && fx2 >= 0
                && fx2 <= this.getRows() && fy1 >= 0 && fy1 <= this.getColumns()
                && fy2 >= 0 && fy2 <= this.getColumns();
    }

    private static boolean isSquareVisibleNone() {
        return true;
    }

    private boolean isSquareVisibleExplore(final int x2, final int y2) {
        final int zLoc = this.getPlayerFloor();
        int fx2, fy2;
        if (this.isHorizontalWraparoundEnabled()) {
            fx2 = this.normalizeColumn(x2);
        } else {
            fx2 = x2;
        }
        if (this.isVerticalWraparoundEnabled()) {
            fy2 = this.normalizeRow(y2);
        } else {
            fy2 = y2;
        }
        try {
            return this.visionData.getCell(fx2, fy2, zLoc);
        } catch (final ArrayIndexOutOfBoundsException aioobe) {
            return true;
        }
    }

    private boolean isSquareVisibleLOS(final int x1, final int y1, final int x2,
            final int y2) {
        int fx1, fx2, fy1, fy2;
        fx1 = x1;
        fx2 = x2;
        fy1 = y1;
        fy2 = y2;
        final int zLoc = this.getPlayerFloor();
        final int dx = Math.abs(fx2 - fx1);
        final int dy = Math.abs(fy2 - fy1);
        int sx, sy;
        if (fx1 < fx2) {
            sx = 1;
        } else {
            sx = -1;
        }
        if (fy1 < fy2) {
            sy = 1;
        } else {
            sy = -1;
        }
        int err = dx - dy;
        int e2 = 2 * err;
        do {
            if (fx1 == fx2 && fy1 == fy2) {
                break;
            }
            // Does object block LOS?
            try {
                final MapObject obj = this.getCell(fx1, fy1, zLoc,
                        MapConstants.LAYER_OBJECT);
                if (obj.isSightBlocking()) {
                    // This object blocks LOS
                    if (fx1 != x1 || fy1 != y1) {
                        return false;
                    }
                }
            } catch (final ArrayIndexOutOfBoundsException aioobe) {
                // Sealing Walls block LOS
                return false;
            }
            e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                fx1 = fx1 + sx;
            }
            if (e2 < dx) {
                err = err + dx;
                fy1 = fy1 + sy;
            }
        } while (true);
        // No objects block LOS
        return true;
    }

    void setCell(final MapObject mo, final int row, final int col,
            final int floor, final int extra) {
        int fR = row;
        int fC = col;
        int fF = floor;
        if (this.verticalWraparoundEnabled) {
            fC = this.normalizeColumn(fC);
        }
        if (this.horizontalWraparoundEnabled) {
            fR = this.normalizeRow(fR);
        }
        if (this.thirdDimensionWraparoundEnabled) {
            fF = this.normalizeFloor(fF);
        }
        this.data.setMapCell(mo, fC, fR, fF, extra);
    }

    void savePlayerLocation() {
        System.arraycopy(this.playerLocationData, 0,
                this.savedPlayerLocationData, 0,
                this.playerLocationData.length);
    }

    void restorePlayerLocation() {
        System.arraycopy(this.savedPlayerLocationData, 0,
                this.playerLocationData, 0, this.playerLocationData.length);
    }

    void offsetPlayerRow(final int newPlayerRow) {
        this.playerLocationData[1] += newPlayerRow;
    }

    void offsetPlayerColumn(final int newPlayerColumn) {
        this.playerLocationData[0] += newPlayerColumn;
    }

    void offsetPlayerFloor(final int newPlayerFloor) {
        this.playerLocationData[2] += newPlayerFloor;
    }

    void fill(final MapObject bottom, final MapObject top) {
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

    private void fillFloor(final MapObject bottom, final MapObject top,
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

    void fillRandomly(final Map map, final int w,
            final MapObject pass1FillBottom, final MapObject pass1FillTop) {
        for (int z = 0; z < this.getFloors(); z++) {
            this.fillFloorRandomly(map, z, w, pass1FillBottom, pass1FillTop);
        }
    }

    private void fillFloorRandomly(final Map map, final int z, final int w,
            final MapObject pass1FillBottom, final MapObject pass1FillTop) {
        // Pre-Pass
        final MapObjectList objects = new MapObjectList();
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
                for (x = 0; x < columns; x++) {
                    for (y = 0; y < rows; y++) {
                        if (e == MapConstants.LAYER_GROUND) {
                            for (x = 0; x < columns; x += this.regionSize) {
                                for (y = 0; y < rows; y += this.regionSize) {
                                    final MapObject currObj = objectsWithoutPrerequisites[r
                                            .generate()];
                                    final boolean okay = currObj
                                            .shouldGenerateObject(map, y, x, z,
                                                    w, e);
                                    if (okay) {
                                        for (u = 0; u < this.regionSize; u++) {
                                            for (v = 0; v < this.regionSize; v++) {
                                                this.setCell(currObj, v + x,
                                                        u + y, z, e);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            for (x = 0; x < columns; x++) {
                                for (y = 0; y < rows; y++) {
                                    final MapObject currObj = objectsWithoutPrerequisites[r
                                            .generate()];
                                    final boolean okay = currObj
                                            .shouldGenerateObject(map, y, x, z,
                                                    w, e);
                                    if (okay) {
                                        this.setCell(currObj, x, y, z, e);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        final RandomRange row = new RandomRange(0, this.getRows() - 1);
        final RandomRange column = new RandomRange(0, this.getColumns() - 1);
        // Pass 4
        for (int layer = 0; layer < MapConstants.LAYER_COUNT; layer++) {
            final MapObject[] requiredObjects = objects.getAllRequired(layer,
                    w);
            if (requiredObjects != null) {
                int randomColumn = -1, randomRow = -1;
                for (x = 0; x < requiredObjects.length; x++) {
                    final MapObject currObj = requiredObjects[x];
                    int min = currObj.getMinimumRequiredQuantity(map, w);
                    int max = currObj.getMaximumRequiredQuantity(map, w);
                    if (min == RandomGenerationRule.NO_LIMIT) {
                        // Minimum undefined, so define it relative to this map
                        min = this.getRows() * this.getColumns() / 100;
                        // Make sure min is valid
                        if (min < 0) {
                            min = 0;
                        }
                    }
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
                            this.setCell(currObj, randomColumn, randomRow, z,
                                    layer);
                        } else {
                            while (!currObj.shouldGenerateObject(map,
                                    randomColumn, randomRow, z, w, layer)) {
                                randomRow = row.generate();
                                randomColumn = column.generate();
                            }
                            this.setCell(currObj, randomColumn, randomRow, z,
                                    layer);
                        }
                    }
                    if (currObj instanceof StairsUp) {
                        // The player will spawn here upon entering the level
                        this.playerLocationData[1] = randomRow;
                        this.playerLocationData[0] = randomColumn;
                        this.playerLocationData[2] = z;
                    }
                }
            }
        }
        // Add buddy
        if (w == Map.getLastLevelNumber()) {
            final MapObject currObj = PartyManager.getParty().getBuddy();
            final int layer = currObj.getLayer();
            int randomRow = row.generate();
            int randomColumn = column.generate();
            if (currObj.shouldGenerateObject(map, randomRow, randomColumn, z, w,
                    layer)) {
                this.setCell(currObj, randomColumn, randomRow, z, layer);
            } else {
                while (!currObj.shouldGenerateObject(map, randomColumn,
                        randomRow, z, w, layer)) {
                    randomRow = row.generate();
                    randomColumn = column.generate();
                }
                this.setCell(currObj, randomColumn, randomRow, z, layer);
            }
        }
    }

    private int normalizeRow(final int row) {
        int fR = row;
        if (fR < 0) {
            fR += this.getRows();
            while (fR < 0) {
                fR += this.getRows();
            }
        } else if (fR > this.getRows() - 1) {
            fR -= this.getRows();
            while (fR > this.getRows() - 1) {
                fR -= this.getRows();
            }
        }
        return fR;
    }

    private int normalizeColumn(final int column) {
        int fC = column;
        if (fC < 0) {
            fC += this.getColumns();
            while (fC < 0) {
                fC += this.getColumns();
            }
        } else if (fC > this.getColumns() - 1) {
            fC -= this.getColumns();
            while (fC > this.getColumns() - 1) {
                fC -= this.getColumns();
            }
        }
        return fC;
    }

    private int normalizeFloor(final int floor) {
        int fF = floor;
        if (fF < 0) {
            fF += this.getFloors();
            while (fF < 0) {
                fF += this.getFloors();
            }
        } else if (fF > this.getFloors() - 1) {
            fF -= this.getFloors();
            while (fF > this.getFloors() - 1) {
                fF -= this.getFloors();
            }
        }
        return fF;
    }

    private boolean isHorizontalWraparoundEnabled() {
        return this.horizontalWraparoundEnabled;
    }

    private boolean isVerticalWraparoundEnabled() {
        return this.verticalWraparoundEnabled;
    }

    ArrayList<InternalScriptArea> getScriptAreasAtPoint(final Point p,
            final int z) {
        final ArrayList<InternalScriptArea> retVal = new ArrayList<>();
        for (final InternalScriptArea isa : this.scriptAreas) {
            if (p.x >= isa.getUpperLeft().x && p.x <= isa.getLowerRight().x
                    && p.y >= isa.getUpperLeft().y
                    && p.y <= isa.getLowerRight().y
                    && this.getPlayerFloor() == z) {
                retVal.add(isa);
            }
        }
        return retVal;
    }

    void writeXLayeredTower(final XDataWriter writer) throws IOException {
        int y, x, z, e;
        writer.writeInt(this.getColumns());
        writer.writeInt(this.getRows());
        writer.writeInt(this.getFloors());
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < MapConstants.LAYER_COUNT; e++) {
                        this.getCell(y, x, z, e).writeMapObject(writer);
                    }
                    writer.writeBoolean(this.visionData.getCell(y, x, z));
                    final boolean hasNote = this.hasNote(x, y, z);
                    writer.writeBoolean(hasNote);
                    if (hasNote) {
                        this.noteData.getNote(y, x, z).writeNote(writer);
                    }
                }
            }
        }
        for (y = 0; y < 3; y++) {
            writer.writeInt(this.playerLocationData[y]);
        }
        writer.writeBoolean(this.horizontalWraparoundEnabled);
        writer.writeBoolean(this.verticalWraparoundEnabled);
        writer.writeBoolean(this.thirdDimensionWraparoundEnabled);
        writer.writeString(this.levelTitle);
        writer.writeInt(this.visionMode);
        writer.writeInt(this.visionModeExploreRadius);
    }

    static LayeredTower readXLayeredTower(final XDataReader reader,
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
                    lt.visionData.setCell(reader.readBoolean(), y, x, z);
                    final boolean hasNote = reader.readBoolean();
                    if (hasNote) {
                        lt.noteData.setNote(MapNote.readNote(reader), y, x, z);
                    }
                }
            }
        }
        for (y = 0; y < 3; y++) {
            lt.playerLocationData[y] = reader.readInt();
        }
        lt.horizontalWraparoundEnabled = reader.readBoolean();
        lt.verticalWraparoundEnabled = reader.readBoolean();
        lt.thirdDimensionWraparoundEnabled = reader.readBoolean();
        lt.levelTitle = reader.readString();
        lt.visionMode = reader.readInt();
        lt.visionModeExploreRadius = reader.readInt();
        return lt;
    }
}
