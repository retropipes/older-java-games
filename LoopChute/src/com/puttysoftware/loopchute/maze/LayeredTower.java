/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.maze;

import java.io.IOException;
import java.util.Arrays;

import com.puttysoftware.llds.LowLevelFlagDataStore;
import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.generic.MazeObject;
import com.puttysoftware.loopchute.generic.MazeObjectList;
import com.puttysoftware.loopchute.generic.RandomGenerationRule;
import com.puttysoftware.loopchute.objects.Empty;
import com.puttysoftware.loopchute.objects.HotRock;
import com.puttysoftware.loopchute.prefs.PreferencesManager;
import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

class LayeredTower implements Cloneable {
    // Properties
    private LowLevelDataStore data;
    private SavedTowerState savedTowerState;
    private LowLevelFlagDataStore visionData;
    private final int[] playerStartData;
    private final int[] playerLocationData;
    private final int[] savedPlayerLocationData;
    private final int[] findResult;
    private boolean horizontalWraparoundEnabled;
    private boolean verticalWraparoundEnabled;
    private boolean thirdDimensionWraparoundEnabled;
    private String levelTitle;
    private String levelStartMessage;
    private String levelEndMessage;
    private int autoFinishThreshold;
    private int alternateAutoFinishThreshold;
    private int nextLevel;
    private int nextLevelOffset;
    private boolean useOffset;
    private int alternateNextLevel;
    private int alternateNextLevelOffset;
    private boolean useAlternateOffset;
    private int visionMode;
    private int visionModeExploreRadius;
    private static final int MAX_FLOORS = 16;
    private static final int MIN_FLOORS = 1;
    private static final int MAX_COLUMNS = 256;
    private static final int MIN_COLUMNS = 2;
    private static final int MAX_ROWS = 256;
    private static final int MIN_ROWS = 2;

    // Constructors
    public LayeredTower(final int rows, final int cols, final int floors) {
        this.data = new LowLevelDataStore(cols, rows, floors,
                MazeConstants.LAYER_COUNT);
        this.savedTowerState = new SavedTowerState(rows, cols, floors);
        this.visionData = new LowLevelFlagDataStore(cols, rows, floors);
        this.playerStartData = new int[3];
        Arrays.fill(this.playerStartData, -1);
        this.playerLocationData = new int[3];
        Arrays.fill(this.playerLocationData, -1);
        this.savedPlayerLocationData = new int[3];
        Arrays.fill(this.savedPlayerLocationData, -1);
        this.findResult = new int[3];
        Arrays.fill(this.findResult, -1);
        this.horizontalWraparoundEnabled = false;
        this.verticalWraparoundEnabled = false;
        this.thirdDimensionWraparoundEnabled = false;
        this.levelTitle = "Untitled Level";
        this.levelStartMessage = "Let's Solve The Level!";
        this.levelEndMessage = "Level Solved!";
        this.autoFinishThreshold = 0;
        this.alternateAutoFinishThreshold = 0;
        this.nextLevel = 0;
        this.nextLevelOffset = 1;
        this.useOffset = true;
        this.visionMode = MazeConstants.VISION_MODE_EXPLORE_AND_LOS;
        this.visionModeExploreRadius = 2;
    }

    // Static methods
    public static int getMaxFloors() {
        return LayeredTower.MAX_FLOORS;
    }

    public static int getMaxColumns() {
        return LayeredTower.MAX_COLUMNS;
    }

    public static int getMaxRows() {
        return LayeredTower.MAX_ROWS;
    }

    public static int getMinFloors() {
        return LayeredTower.MIN_FLOORS;
    }

    public static int getMinColumns() {
        return LayeredTower.MIN_COLUMNS;
    }

    public static int getMinRows() {
        return LayeredTower.MIN_ROWS;
    }

    // Methods
    @Override
    public LayeredTower clone() {
        final LayeredTower copy = new LayeredTower(this.getRows(),
                this.getColumns(), this.getFloors());
        copy.data = (LowLevelDataStore) this.data.clone();
        copy.visionData = (LowLevelFlagDataStore) this.visionData.clone();
        copy.savedTowerState = this.savedTowerState.clone();
        System.arraycopy(this.playerStartData, 0, copy.playerStartData, 0,
                this.playerStartData.length);
        System.arraycopy(this.findResult, 0, copy.findResult, 0,
                this.findResult.length);
        copy.horizontalWraparoundEnabled = this.horizontalWraparoundEnabled;
        copy.verticalWraparoundEnabled = this.verticalWraparoundEnabled;
        copy.thirdDimensionWraparoundEnabled = this.thirdDimensionWraparoundEnabled;
        return copy;
    }

    public int getExploreRadius() {
        return this.visionModeExploreRadius;
    }

    public void setExploreRadius(final int newER) {
        this.visionModeExploreRadius = newER;
    }

    public int getVisionMode() {
        return this.visionMode;
    }

    public void setVisionMode(final int newVM) {
        this.visionMode = newVM;
    }

    public String getLevelTitle() {
        return this.levelTitle;
    }

    public void setLevelTitle(final String title) {
        if (title == null) {
            throw new NullPointerException("Title cannot be null!");
        }
        this.levelTitle = title;
    }

    public String getLevelStartMessage() {
        return this.levelStartMessage;
    }

    public void setLevelStartMessage(final String msg) {
        if (msg == null) {
            throw new NullPointerException("Message cannot be null!");
        }
        this.levelStartMessage = msg;
    }

    public String getLevelEndMessage() {
        return this.levelEndMessage;
    }

    public void setLevelEndMessage(final String msg) {
        if (msg == null) {
            throw new NullPointerException("Message cannot be null!");
        }
        this.levelEndMessage = msg;
    }

    public int getAutoFinishThreshold() {
        return this.autoFinishThreshold;
    }

    public int getAlternateAutoFinishThreshold() {
        return this.alternateAutoFinishThreshold;
    }

    public int getNextLevel() {
        if (this.useOffset) {
            return this.nextLevelOffset;
        } else {
            return this.nextLevel;
        }
    }

    public boolean useOffset() {
        return this.useOffset;
    }

    public void setUseOffset(final boolean uo) {
        this.useOffset = uo;
    }

    public void setNextLevel(final int nl) {
        this.nextLevel = nl;
    }

    public void setNextLevelOffset(final int nlo) {
        this.nextLevelOffset = nlo;
    }

    public int getAlternateNextLevel() {
        if (this.useAlternateOffset) {
            return this.alternateNextLevelOffset;
        } else {
            return this.alternateNextLevel;
        }
    }

    public boolean useAlternateOffset() {
        return this.useAlternateOffset;
    }

    public void setUseAlternateOffset(final boolean uao) {
        this.useAlternateOffset = uao;
    }

    public void setAlternateNextLevel(final int anl) {
        this.alternateNextLevel = anl;
    }

    public void setAlternateNextLevelOffset(final int anlo) {
        this.alternateNextLevelOffset = anlo;
    }

    public void tickTimers(final int floor) {
        int x, y;
        // Tick all MazeObject timers
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                final MazeObject mo = this.getCell(y, x, floor,
                        MazeConstants.LAYER_OBJECT);
                if (mo != null) {
                    mo.tickTimer(y, x);
                }
            }
        }
    }

    public MazeObject getCell(final int row, final int col, final int floor,
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
        return this.data.getCell(fC, fR, fF, extra);
    }

    public int getStartRow() {
        return this.playerStartData[1];
    }

    public int getStartColumn() {
        return this.playerStartData[0];
    }

    public int getStartFloor() {
        return this.playerStartData[2];
    }

    public int getPlayerRow() {
        return this.playerLocationData[1];
    }

    public int getPlayerColumn() {
        return this.playerLocationData[0];
    }

    public int getPlayerFloor() {
        return this.playerLocationData[2];
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

    public boolean doesPlayerExist() {
        boolean res = true;
        for (final int element : this.playerStartData) {
            res = res && element != -1;
        }
        return res;
    }

    public void findStart() {
        int y, x, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final MazeObject mo = this.getCell(y, x, z,
                            MazeConstants.LAYER_OBJECT);
                    if (mo != null) {
                        if (mo.getName().equals("Player")) {
                            this.playerStartData[1] = x;
                            this.playerStartData[0] = y;
                            this.playerStartData[2] = z;
                        }
                    }
                }
            }
        }
    }

    public void findAllObjectPairsAndSwap(final MazeObject o1,
            final MazeObject o2) {
        int y, x, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final MazeObject mo = this.getCell(y, x, z,
                            MazeConstants.LAYER_OBJECT);
                    if (mo != null) {
                        if (mo.getName().equals(o1.getName())) {
                            this.setCell(o2, y, x, z,
                                    MazeConstants.LAYER_OBJECT);
                        } else if (mo.getName().equals(o2.getName())) {
                            this.setCell(o1, y, x, z,
                                    MazeConstants.LAYER_OBJECT);
                        }
                    }
                }
            }
        }
    }

    public void resize(final int x, final int y, final int z) {
        // Allocate temporary storage array
        final LowLevelDataStore tempStorage = new LowLevelDataStore(y, x, z,
                MazeConstants.LAYER_COUNT);
        // Copy existing maze into temporary array
        int u, v, w, e;
        for (u = 0; u < y; u++) {
            for (v = 0; v < x; v++) {
                for (w = 0; w < z; w++) {
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                        try {
                            tempStorage.setCell(this.getCell(v, u, w, e), u, v,
                                    w, e);
                        } catch (final ArrayIndexOutOfBoundsException aioob) {
                            // Do nothing
                        }
                    }
                }
            }
        }
        // Set the current data to the temporary array
        this.data = tempStorage;
        // Fill any blanks
        this.fillNulls();
        // Recreate saved tower state
        this.savedTowerState = new SavedTowerState(y, x, z);
    }

    public void resetVisibleSquares() {
        for (int x = 0; x < this.getRows(); x++) {
            for (int y = 0; y < this.getColumns(); y++) {
                for (int z = 0; z < this.getFloors(); z++) {
                    this.visionData.setCell(false, x, y, z);
                }
            }
        }
    }

    public void updateVisibleSquares(final int xp, final int yp, final int zp) {
        if ((this.visionMode | MazeConstants.VISION_MODE_EXPLORE) == this.visionMode) {
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
                        if ((this.visionMode | MazeConstants.VISION_MODE_LOS) == this.visionMode) {
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

    public boolean isSquareVisible(final int x1, final int y1, final int x2,
            final int y2) {
        if (this.visionMode == MazeConstants.VISION_MODE_NONE) {
            return LayeredTower.isSquareVisibleNone();
        } else {
            boolean result = false;
            if ((this.visionMode | MazeConstants.VISION_MODE_EXPLORE) == this.visionMode) {
                result = result || this.isSquareVisibleExplore(x2, y2);
                if (result
                        && (this.visionMode | MazeConstants.VISION_MODE_LOS) == this.visionMode) {
                    if (this.areCoordsInBounds(x1, y1, x2, y2)) {
                        // In bounds
                        result = result
                                || this.isSquareVisibleLOS(x1, y1, x2, y2);
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
                && fx2 <= this.getRows() && fy1 >= 0
                && fy1 <= this.getColumns() && fy2 >= 0
                && fy2 <= this.getColumns();
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

    private boolean isSquareVisibleLOS(final int x1, final int y1,
            final int x2, final int y2) {
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
                final MazeObject obj = this.getCell(fx1, fy1, zLoc,
                        MazeConstants.LAYER_OBJECT);
                if (obj.isSightBlocking()) {
                    // This object blocks LOS
                    if (fx1 != x1 || fy1 != y1) {
                        return false;
                    }
                }
            } catch (final ArrayIndexOutOfBoundsException aioobe) {
                // Void blocks LOS
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

    public void setCell(final MazeObject mo, final int row, final int col,
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
        this.data.setCell(mo, fC, fR, fF, extra);
    }

    public void savePlayerLocation() {
        System.arraycopy(this.playerLocationData, 0,
                this.savedPlayerLocationData, 0, this.playerLocationData.length);
    }

    public void restorePlayerLocation() {
        System.arraycopy(this.savedPlayerLocationData, 0,
                this.playerLocationData, 0, this.playerLocationData.length);
    }

    public void setPlayerToStart() {
        System.arraycopy(this.playerStartData, 0, this.playerLocationData, 0,
                this.playerStartData.length);
    }

    public void setStartRow(final int newStartRow) {
        this.playerStartData[1] = newStartRow;
    }

    public void setStartColumn(final int newStartColumn) {
        this.playerStartData[0] = newStartColumn;
    }

    public void setStartFloor(final int newStartFloor) {
        this.playerStartData[2] = newStartFloor;
    }

    public void setPlayerRow(final int newPlayerRow) {
        this.playerLocationData[1] = newPlayerRow;
    }

    public void setPlayerColumn(final int newPlayerColumn) {
        this.playerLocationData[0] = newPlayerColumn;
    }

    public void setPlayerFloor(final int newPlayerFloor) {
        this.playerLocationData[2] = newPlayerFloor;
    }

    public void offsetPlayerRow(final int newPlayerRow) {
        this.playerLocationData[1] += newPlayerRow;
    }

    public void offsetPlayerColumn(final int newPlayerColumn) {
        this.playerLocationData[0] += newPlayerColumn;
    }

    public void updateThresholds() {
        int y, x, z;
        this.autoFinishThreshold = 0;
        this.alternateAutoFinishThreshold = 0;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final MazeObject obj = this.getCell(y, x, z,
                            MazeConstants.LAYER_OBJECT);
                    if (obj.getName().equals("Sun Stone")) {
                        this.autoFinishThreshold++;
                    } else if (obj.getName().equals("Moon Stone")) {
                        this.alternateAutoFinishThreshold++;
                    }
                }
            }
        }
    }

    public void fill(final MazeObject bottom, final MazeObject top) {
        int y, x, z, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                        if (e == MazeConstants.LAYER_GROUND) {
                            this.setCell(bottom, y, x, z, e);
                        } else {
                            this.setCell(top, y, x, z, e);
                        }
                    }
                }
            }
        }
    }

    public void fillFloor(final MazeObject bottom, final MazeObject top,
            final int z) {
        int x, y, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                    if (e == MazeConstants.LAYER_GROUND) {
                        this.setCell(bottom, y, x, z, e);
                    } else {
                        this.setCell(top, y, x, z, e);
                    }
                }
            }
        }
    }

    public void fillRandomly(final Maze maze, final int w) {
        for (int z = 0; z < this.getFloors(); z++) {
            this.fillFloorRandomly(maze, z, w);
        }
    }

    public void fillRandomlyCustom(final Maze maze, final int w) {
        for (int z = 0; z < this.getFloors(); z++) {
            this.fillFloorRandomlyCustom(maze, z, w);
        }
    }

    public void fillFloorRandomly(final Maze maze, final int z, final int w) {
        // Pre-Pass
        final MazeObjectList objects = LoopChute.getApplication().getObjects();
        final MazeObject pass1FillBottom = PreferencesManager
                .getEditorDefaultFill();
        final MazeObject pass1FillTop = new Empty();
        RandomRange r = null;
        int x, y, e;
        // Pass 1
        this.fillFloor(pass1FillBottom, pass1FillTop, z);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
            final MazeObject[] objectsWithoutPrerequisites = objects
                    .getAllWithoutPrerequisiteAndNotRequired(e);
            if (objectsWithoutPrerequisites != null) {
                r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
                for (x = 0; x < columns; x++) {
                    for (y = 0; y < rows; y++) {
                        final MazeObject placeObj = objectsWithoutPrerequisites[r
                                .generate()];
                        final boolean okay = placeObj.shouldGenerateObject(
                                maze, x, y, z, w, e);
                        if (okay) {
                            this.setCell(objects.getNewInstanceByName(placeObj
                                    .getName()), y, x, z, e);
                            placeObj.editorGenerateHook(y, x, z);
                        }
                    }
                }
            }
        }
        // Pass 3
        for (int layer = 0; layer < MazeConstants.LAYER_COUNT; layer++) {
            final MazeObject[] requiredObjects = objects.getAllRequired(layer);
            if (requiredObjects != null) {
                final RandomRange row = new RandomRange(0, this.getRows() - 1);
                final RandomRange column = new RandomRange(0,
                        this.getColumns() - 1);
                int randomColumn, randomRow;
                for (x = 0; x < requiredObjects.length; x++) {
                    randomRow = row.generate();
                    randomColumn = column.generate();
                    final MazeObject currObj = requiredObjects[x];
                    final int min = currObj.getMinimumRequiredQuantity(maze);
                    int max = currObj.getMaximumRequiredQuantity(maze);
                    if (max == RandomGenerationRule.NO_LIMIT) {
                        // Maximum undefined, so define it relative to this maze
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
                        if (currObj.shouldGenerateObject(maze, randomRow,
                                randomColumn, z, w, layer)) {
                            this.setCell(objects.getNewInstanceByName(currObj
                                    .getName()), randomColumn, randomRow, z,
                                    layer);
                            currObj.editorGenerateHook(y, x, z);
                        } else {
                            while (!currObj.shouldGenerateObject(maze,
                                    randomColumn, randomRow, z, w, layer)) {
                                randomRow = row.generate();
                                randomColumn = column.generate();
                            }
                            this.setCell(objects.getNewInstanceByName(currObj
                                    .getName()), randomColumn, randomRow, z,
                                    layer);
                            currObj.editorGenerateHook(y, x, z);
                        }
                    }
                }
            }
        }
    }

    public void fillFloorRandomlyCustom(final Maze maze, final int z,
            final int w) {
        // Pre-Pass
        final MazeObjectList objects = LoopChute.getApplication().getObjects();
        final MazeObject pass1FillBottom = PreferencesManager
                .getEditorDefaultFill();
        final MazeObject pass1FillTop = new Empty();
        final MazeObject[] withoutRuleSets = objects
                .getAllObjectsWithoutRuleSets();
        final MazeObject[] withRuleSets = objects.getAllObjectsWithRuleSets();
        RandomRange r = null;
        int x, y, e;
        // Pass 1
        this.fillFloor(pass1FillBottom, pass1FillTop, z);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        if (withoutRuleSets != null) {
            for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                final MazeObject[] objectsWithoutPrerequisites = MazeObjectList
                        .getAllWithoutPrerequisiteAndNotRequiredSubset(
                                withoutRuleSets, e);
                if (objectsWithoutPrerequisites != null
                        && objectsWithoutPrerequisites.length > 0) {
                    r = new RandomRange(0,
                            objectsWithoutPrerequisites.length - 1);
                    for (x = 0; x < columns; x++) {
                        for (y = 0; y < rows; y++) {
                            final MazeObject placeObj = objectsWithoutPrerequisites[r
                                    .generate()];
                            final boolean okay = placeObj.shouldGenerateObject(
                                    maze, y, x, z, w, e);
                            if (okay) {
                                this.setCell(objects
                                        .getNewInstanceByName(placeObj
                                                .getName()), y, x, z, e);
                                placeObj.editorGenerateHook(y, x, z);
                            }
                        }
                    }
                }
            }
            // Pass 3
            for (int layer = 0; layer < MazeConstants.LAYER_COUNT; layer++) {
                final MazeObject[] requiredObjects = MazeObjectList
                        .getAllRequiredSubset(withoutRuleSets, layer);
                if (requiredObjects != null) {
                    final RandomRange row = new RandomRange(0,
                            this.getRows() - 1);
                    final RandomRange column = new RandomRange(0,
                            this.getColumns() - 1);
                    int randomColumn, randomRow;
                    for (x = 0; x < requiredObjects.length; x++) {
                        randomRow = row.generate();
                        randomColumn = column.generate();
                        final MazeObject currObj = requiredObjects[x];
                        final int min = currObj
                                .getMinimumRequiredQuantity(maze);
                        int max = currObj.getMaximumRequiredQuantity(maze);
                        if (max == RandomGenerationRule.NO_LIMIT) {
                            // Maximum undefined, so define it relative to this
                            // maze
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
                            if (currObj.shouldGenerateObject(maze, randomRow,
                                    randomColumn, z, w, layer)) {
                                this.setCell(
                                        objects.getNewInstanceByName(currObj
                                                .getName()), randomColumn,
                                        randomRow, z, layer);
                                currObj.editorGenerateHook(y, x, z);
                            } else {
                                while (!currObj.shouldGenerateObject(maze,
                                        randomColumn, randomRow, z, w, layer)) {
                                    randomRow = row.generate();
                                    randomColumn = column.generate();
                                }
                                this.setCell(
                                        objects.getNewInstanceByName(currObj
                                                .getName()), randomColumn,
                                        randomRow, z, layer);
                                currObj.editorGenerateHook(y, x, z);
                            }
                        }
                    }
                }
            }
        }
        if (withRuleSets != null) {
            // Pass 4
            for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                final MazeObject[] objectsWithoutPrerequisites = MazeObjectList
                        .getAllWithoutPrerequisiteAndNotRequiredSubset(
                                withRuleSets, e);
                if (objectsWithoutPrerequisites != null) {
                    r = new RandomRange(0,
                            objectsWithoutPrerequisites.length - 1);
                    for (x = 0; x < columns; x++) {
                        for (y = 0; y < rows; y++) {
                            final MazeObject placeObj = objectsWithoutPrerequisites[r
                                    .generate()];
                            final boolean okay = placeObj.getRuleSet()
                                    .shouldGenerateObject(maze, y, x, z, w, e);
                            if (okay) {
                                this.setCell(objects
                                        .getNewInstanceByName(placeObj
                                                .getName()), y, x, z, e);
                                placeObj.editorGenerateHook(y, x, z);
                            }
                        }
                    }
                }
            }
            // Pass 5
            for (int layer = 0; layer < MazeConstants.LAYER_COUNT; layer++) {
                final MazeObject[] requiredObjects = MazeObjectList
                        .getAllRequiredSubset(withRuleSets, layer);
                if (requiredObjects != null) {
                    final RandomRange row = new RandomRange(0,
                            this.getRows() - 1);
                    final RandomRange column = new RandomRange(0,
                            this.getColumns() - 1);
                    int randomColumn, randomRow;
                    for (x = 0; x < requiredObjects.length; x++) {
                        randomRow = row.generate();
                        randomColumn = column.generate();
                        final MazeObject currObj = requiredObjects[x];
                        final int min = currObj.getRuleSet()
                                .getMinimumRequiredQuantity(maze);
                        int max = currObj.getRuleSet()
                                .getMaximumRequiredQuantity(maze);
                        if (max == RandomGenerationRule.NO_LIMIT) {
                            // Maximum undefined, so define it relative to this
                            // maze
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
                            if (currObj.getRuleSet().shouldGenerateObject(maze,
                                    randomColumn, randomRow, z, w, layer)) {
                                this.setCell(
                                        objects.getNewInstanceByName(currObj
                                                .getName()), randomColumn,
                                        randomRow, z, layer);
                                currObj.editorGenerateHook(y, x, z);
                            } else {
                                while (!currObj.getRuleSet()
                                        .shouldGenerateObject(maze, randomRow,
                                                randomColumn, z, w, layer)) {
                                    randomRow = row.generate();
                                    randomColumn = column.generate();
                                }
                                this.setCell(
                                        objects.getNewInstanceByName(currObj
                                                .getName()), randomColumn,
                                        randomRow, z, layer);
                                currObj.editorGenerateHook(y, x, z);
                            }
                        }
                    }
                }
            }
        }
    }

    public void fillLayer(final MazeObject fillWith, final int e) {
        int y, x, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    this.setCell(fillWith, y, x, z, e);
                }
            }
        }
    }

    public void fillFloorAndLayer(final MazeObject fillWith, final int z,
            final int e) {
        int x, y;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                this.setCell(fillWith, y, x, z, e);
            }
        }
    }

    public void fillLayerRandomly(final Maze maze, final int w, final int e) {
        for (int z = 0; z < this.getFloors(); z++) {
            this.fillFloorAndLayerRandomly(maze, z, w, e);
        }
    }

    public void fillLayerRandomlyCustom(final Maze maze, final int w,
            final int e) {
        for (int z = 0; z < this.getFloors(); z++) {
            this.fillFloorAndLayerRandomlyCustom(maze, z, w, e);
        }
    }

    public void fillFloorAndLayerRandomly(final Maze maze, final int z,
            final int w, final int layer) {
        // Pre-Pass
        final MazeObjectList objects = LoopChute.getApplication().getObjects();
        final MazeObject pass1Fill = PreferencesManager
                .getEditorDefaultFill(layer);
        RandomRange r = null;
        int x, y;
        // Pass 1
        this.fillFloorAndLayer(pass1Fill, z, layer);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        final MazeObject[] objectsWithoutPrerequisites = objects
                .getAllWithoutPrerequisiteAndNotRequired(layer);
        if (objectsWithoutPrerequisites != null) {
            r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
            for (x = 0; x < columns; x++) {
                for (y = 0; y < rows; y++) {
                    final MazeObject placeObj = objectsWithoutPrerequisites[r
                            .generate()];
                    final boolean okay = placeObj.shouldGenerateObject(maze, x,
                            y, z, w, layer);
                    if (okay) {
                        this.setCell(objects.getNewInstanceByName(placeObj
                                .getName()), y, x, z, layer);
                        placeObj.editorGenerateHook(y, x, z);
                    }
                }
            }
        }
        // Pass 3
        final MazeObject[] requiredObjects = objects.getAllRequired(layer);
        if (requiredObjects != null) {
            final RandomRange row = new RandomRange(0, this.getRows() - 1);
            final RandomRange column = new RandomRange(0, this.getColumns() - 1);
            int randomColumn, randomRow;
            for (x = 0; x < requiredObjects.length; x++) {
                randomRow = row.generate();
                randomColumn = column.generate();
                final MazeObject currObj = requiredObjects[x];
                final int min = currObj.getMinimumRequiredQuantity(maze);
                int max = currObj.getMaximumRequiredQuantity(maze);
                if (max == RandomGenerationRule.NO_LIMIT) {
                    // Maximum undefined, so define it relative to this maze
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
                    if (currObj.shouldGenerateObject(maze, randomRow,
                            randomColumn, z, w, layer)) {
                        this.setCell(
                                objects.getNewInstanceByName(currObj.getName()),
                                randomColumn, randomRow, z, layer);
                        currObj.editorGenerateHook(y, x, z);
                    } else {
                        while (!currObj.shouldGenerateObject(maze,
                                randomColumn, randomRow, z, w, layer)) {
                            randomRow = row.generate();
                            randomColumn = column.generate();
                        }
                        this.setCell(
                                objects.getNewInstanceByName(currObj.getName()),
                                randomColumn, randomRow, z, layer);
                        currObj.editorGenerateHook(y, x, z);
                    }
                }
            }
        }
    }

    public void fillFloorAndLayerRandomlyCustom(final Maze maze, final int z,
            final int w, final int layer) {
        // Pre-Pass
        final MazeObjectList objects = LoopChute.getApplication().getObjects();
        final MazeObject pass1Fill = PreferencesManager
                .getEditorDefaultFill(layer);
        final MazeObject[] withoutRuleSets = objects
                .getAllObjectsWithoutRuleSets();
        final MazeObject[] withRuleSets = objects.getAllObjectsWithRuleSets();
        RandomRange r = null;
        int x, y;
        // Pass 1
        this.fillFloorAndLayer(pass1Fill, z, layer);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        if (withoutRuleSets != null) {
            final MazeObject[] objectsWithoutPrerequisites = MazeObjectList
                    .getAllWithoutPrerequisiteAndNotRequiredSubset(
                            withoutRuleSets, layer);
            if (objectsWithoutPrerequisites != null
                    && objectsWithoutPrerequisites.length > 0) {
                r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
                for (x = 0; x < columns; x++) {
                    for (y = 0; y < rows; y++) {
                        final MazeObject placeObj = objectsWithoutPrerequisites[r
                                .generate()];
                        final boolean okay = placeObj.shouldGenerateObject(
                                maze, y, x, z, w, layer);
                        if (okay) {
                            this.setCell(objects.getNewInstanceByName(placeObj
                                    .getName()), y, x, z, layer);
                            placeObj.editorGenerateHook(y, x, z);
                        }
                    }
                }
            }
            // Pass 3
            final MazeObject[] requiredObjects = MazeObjectList
                    .getAllRequiredSubset(withoutRuleSets, layer);
            if (requiredObjects != null) {
                final RandomRange row = new RandomRange(0, this.getRows() - 1);
                final RandomRange column = new RandomRange(0,
                        this.getColumns() - 1);
                int randomColumn, randomRow;
                for (x = 0; x < requiredObjects.length; x++) {
                    randomRow = row.generate();
                    randomColumn = column.generate();
                    final MazeObject currObj = requiredObjects[x];
                    final int min = currObj.getMinimumRequiredQuantity(maze);
                    int max = currObj.getMaximumRequiredQuantity(maze);
                    if (max == RandomGenerationRule.NO_LIMIT) {
                        // Maximum undefined, so define it relative to this
                        // maze
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
                        if (currObj.shouldGenerateObject(maze, randomRow,
                                randomColumn, z, w, layer)) {
                            this.setCell(objects.getNewInstanceByName(currObj
                                    .getName()), randomColumn, randomRow, z,
                                    layer);
                            currObj.editorGenerateHook(y, x, z);
                        } else {
                            while (!currObj.shouldGenerateObject(maze,
                                    randomColumn, randomRow, z, w, layer)) {
                                randomRow = row.generate();
                                randomColumn = column.generate();
                            }
                            this.setCell(objects.getNewInstanceByName(currObj
                                    .getName()), randomColumn, randomRow, z,
                                    layer);
                            currObj.editorGenerateHook(y, x, z);
                        }
                    }
                }
            }
        }
        if (withRuleSets != null) {
            // Pass 4
            final MazeObject[] objectsWithoutPrerequisites = MazeObjectList
                    .getAllWithoutPrerequisiteAndNotRequiredSubset(
                            withRuleSets, layer);
            if (objectsWithoutPrerequisites != null) {
                r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
                for (x = 0; x < columns; x++) {
                    for (y = 0; y < rows; y++) {
                        final MazeObject placeObj = objectsWithoutPrerequisites[r
                                .generate()];
                        final boolean okay = placeObj.getRuleSet()
                                .shouldGenerateObject(maze, y, x, z, w, layer);
                        if (okay) {
                            this.setCell(objects.getNewInstanceByName(placeObj
                                    .getName()), y, x, z, layer);
                            placeObj.editorGenerateHook(y, x, z);
                        }
                    }
                }
            }
            // Pass 5
            final MazeObject[] requiredObjects = MazeObjectList
                    .getAllRequiredSubset(withRuleSets, layer);
            if (requiredObjects != null) {
                final RandomRange row = new RandomRange(0, this.getRows() - 1);
                final RandomRange column = new RandomRange(0,
                        this.getColumns() - 1);
                int randomColumn, randomRow;
                for (x = 0; x < requiredObjects.length; x++) {
                    randomRow = row.generate();
                    randomColumn = column.generate();
                    final MazeObject currObj = requiredObjects[x];
                    final int min = currObj.getRuleSet()
                            .getMinimumRequiredQuantity(maze);
                    int max = currObj.getRuleSet().getMaximumRequiredQuantity(
                            maze);
                    if (max == RandomGenerationRule.NO_LIMIT) {
                        // Maximum undefined, so define it relative to this
                        // maze
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
                        if (currObj.getRuleSet().shouldGenerateObject(maze,
                                randomColumn, randomRow, z, w, layer)) {
                            this.setCell(objects.getNewInstanceByName(currObj
                                    .getName()), randomColumn, randomRow, z,
                                    layer);
                            currObj.editorGenerateHook(y, x, z);
                        } else {
                            while (!currObj.getRuleSet().shouldGenerateObject(
                                    maze, randomRow, randomColumn, z, w, layer)) {
                                randomRow = row.generate();
                                randomColumn = column.generate();
                            }
                            this.setCell(objects.getNewInstanceByName(currObj
                                    .getName()), randomColumn, randomRow, z,
                                    layer);
                            currObj.editorGenerateHook(y, x, z);
                        }
                    }
                }
            }
        }
    }

    private void fillNulls() {
        final MazeObject bottom = PreferencesManager.getEditorDefaultFill();
        final MazeObject top = new Empty();
        int y, x, z, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                        if (this.getCell(y, x, z, e) == null) {
                            if (e == MazeConstants.LAYER_GROUND) {
                                this.setCell(bottom, y, x, z, e);
                            } else {
                                this.setCell(top, y, x, z, e);
                            }
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
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
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
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                        this.setCell(
                                this.savedTowerState.getDataCell(x, y, z, e),
                                y, x, z, e);
                    }
                }
            }
        }
    }

    public void hotGround(final int x, final int y, final int z) {
        this.setCell(new HotRock(), x, y, z, MazeConstants.LAYER_GROUND);
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

    public void enableHorizontalWraparound() {
        this.horizontalWraparoundEnabled = true;
    }

    public void disableHorizontalWraparound() {
        this.horizontalWraparoundEnabled = false;
    }

    public void enableVerticalWraparound() {
        this.verticalWraparoundEnabled = true;
    }

    public void disableVerticalWraparound() {
        this.verticalWraparoundEnabled = false;
    }

    public void enable3rdDimensionWraparound() {
        this.thirdDimensionWraparoundEnabled = true;
    }

    public void disable3rdDimensionWraparound() {
        this.thirdDimensionWraparoundEnabled = false;
    }

    public boolean isHorizontalWraparoundEnabled() {
        return this.horizontalWraparoundEnabled;
    }

    public boolean isVerticalWraparoundEnabled() {
        return this.verticalWraparoundEnabled;
    }

    public boolean is3rdDimensionWraparoundEnabled() {
        return this.thirdDimensionWraparoundEnabled;
    }

    public void writeLayeredTower(final XDataWriter writer) throws IOException {
        int y, x, z, e;
        writer.writeInt(this.getColumns());
        writer.writeInt(this.getRows());
        writer.writeInt(this.getFloors());
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                        this.getCell(y, x, z, e).writeMazeObject(writer);
                    }
                }
            }
        }
        for (y = 0; y < 3; y++) {
            writer.writeInt(this.playerStartData[y]);
        }
        writer.writeBoolean(this.horizontalWraparoundEnabled);
        writer.writeBoolean(this.verticalWraparoundEnabled);
        writer.writeBoolean(this.thirdDimensionWraparoundEnabled);
        writer.writeString(this.levelTitle);
        writer.writeString(this.levelStartMessage);
        writer.writeString(this.levelEndMessage);
        writer.writeInt(this.autoFinishThreshold);
        writer.writeBoolean(this.useOffset);
        writer.writeInt(this.nextLevel);
        writer.writeInt(this.nextLevelOffset);
        writer.writeInt(this.visionMode);
        writer.writeInt(this.visionModeExploreRadius);
        writer.writeInt(this.alternateAutoFinishThreshold);
        writer.writeBoolean(this.useAlternateOffset);
        writer.writeInt(this.alternateNextLevel);
        writer.writeInt(this.alternateNextLevelOffset);
    }

    public static LayeredTower readLayeredTower(final XDataReader reader,
            final int ver) throws IOException {
        int y, x, z, e, mazeSizeX, mazeSizeY, mazeSizeZ;
        mazeSizeX = reader.readInt();
        mazeSizeY = reader.readInt();
        mazeSizeZ = reader.readInt();
        final LayeredTower lt = new LayeredTower(mazeSizeX, mazeSizeY,
                mazeSizeZ);
        for (x = 0; x < lt.getColumns(); x++) {
            for (y = 0; y < lt.getRows(); y++) {
                for (z = 0; z < lt.getFloors(); z++) {
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                        lt.setCell(LoopChute.getApplication().getObjects()
                                .readMazeObject(reader, ver), y, x, z, e);
                        if (lt.getCell(y, x, z, e) == null) {
                            return null;
                        }
                    }
                }
            }
        }
        for (y = 0; y < 3; y++) {
            lt.playerStartData[y] = reader.readInt();
        }
        lt.horizontalWraparoundEnabled = reader.readBoolean();
        lt.verticalWraparoundEnabled = reader.readBoolean();
        lt.thirdDimensionWraparoundEnabled = reader.readBoolean();
        lt.levelTitle = reader.readString();
        lt.levelStartMessage = reader.readString();
        lt.levelEndMessage = reader.readString();
        lt.autoFinishThreshold = reader.readInt();
        lt.useOffset = reader.readBoolean();
        lt.nextLevel = reader.readInt();
        lt.nextLevelOffset = reader.readInt();
        lt.visionMode = reader.readInt();
        lt.visionModeExploreRadius = reader.readInt();
        lt.alternateAutoFinishThreshold = reader.readInt();
        lt.useAlternateOffset = reader.readBoolean();
        lt.alternateNextLevel = reader.readInt();
        lt.alternateNextLevelOffset = reader.readInt();
        return lt;
    }

    public void writeSavedTowerState(final XDataWriter writer)
            throws IOException {
        this.savedTowerState.writeSavedTowerState(writer);
    }

    public void readSavedTowerState(final XDataReader reader,
            final int formatVersion) throws IOException {
        this.savedTowerState = SavedTowerState.readSavedTowerState(reader,
                formatVersion);
    }
}
