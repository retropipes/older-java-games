/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon;

import java.io.IOException;
import java.util.Arrays;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.creatures.party.PartyManager;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractLightModifier;
import com.puttysoftware.dungeondiver4.dungeon.objects.BarrierGenerator;
import com.puttysoftware.dungeondiver4.dungeon.objects.CrackedWall;
import com.puttysoftware.dungeondiver4.dungeon.objects.Crevasse;
import com.puttysoftware.dungeondiver4.dungeon.objects.DarkGem;
import com.puttysoftware.dungeondiver4.dungeon.objects.Dirt;
import com.puttysoftware.dungeondiver4.dungeon.objects.Empty;
import com.puttysoftware.dungeondiver4.dungeon.objects.EnragedBarrierGenerator;
import com.puttysoftware.dungeondiver4.dungeon.objects.ForceField;
import com.puttysoftware.dungeondiver4.dungeon.objects.HotRock;
import com.puttysoftware.dungeondiver4.dungeon.objects.Ice;
import com.puttysoftware.dungeondiver4.dungeon.objects.IcedBarrierGenerator;
import com.puttysoftware.dungeondiver4.dungeon.objects.LightGem;
import com.puttysoftware.dungeondiver4.dungeon.objects.MonsterObject;
import com.puttysoftware.dungeondiver4.dungeon.objects.MovingBlock;
import com.puttysoftware.dungeondiver4.dungeon.objects.PoisonedBarrierGenerator;
import com.puttysoftware.dungeondiver4.dungeon.objects.ShockedBarrierGenerator;
import com.puttysoftware.dungeondiver4.dungeon.objects.Slime;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DirectionResolver;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectList;
import com.puttysoftware.dungeondiver4.dungeon.utilities.RandomGenerationRule;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.prefs.PreferencesManager;
import com.puttysoftware.llds.LowLevelFlagDataStore;
import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

class LayeredTower implements Cloneable {
    // Properties
    private LowLevelADODataStore data;
    private LowLevelADODataStore savedTowerState;
    private LowLevelFlagDataStore visionData;
    private final LowLevelNoteDataStore noteData;
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
    private int visionMode;
    private int visionModeExploreRadius;
    private int visionRadius;
    private int poisonPower;
    private int oldPoisonPower;
    private int regionSize;
    private static int NEW_PLAYER_ROW;
    private static int NEW_PLAYER_COL;
    private static final int MAX_POISON_POWER = 10;
    private static final int MAX_VISION_RADIUS = 16;
    private static final int MIN_VISION_RADIUS = 1;
    private static final int MAX_FLOORS = 16;
    private static final int MIN_FLOORS = 1;
    private static final int MAX_COLUMNS = 256;
    private static final int MIN_COLUMNS = 2;
    private static final int MAX_ROWS = 256;
    private static final int MIN_ROWS = 2;
    private static final String TOWER_GROUP = "tower";
    private static final String TOWER_SIZE_GROUP = "size";
    private static final String TOWER_OBJECTS_GROUP = "objects";
    private static final String TOWER_SETTINGS_GROUP = "settings";

    // Constructors
    public LayeredTower(final int rows, final int cols, final int floors) {
        this.data = new LowLevelADODataStore(cols, rows, floors,
                DungeonConstants.LAYER_COUNT);
        this.savedTowerState = new LowLevelADODataStore(cols, rows, floors,
                DungeonConstants.LAYER_COUNT);
        this.visionData = new LowLevelFlagDataStore(cols, rows, floors);
        this.noteData = new LowLevelNoteDataStore(cols, rows, floors);
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
        this.visionMode = DungeonConstants.VISION_MODE_EXPLORE_AND_LOS;
        this.visionModeExploreRadius = 2;
        this.visionRadius = LayeredTower.MAX_VISION_RADIUS;
        this.regionSize = 8;
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
        copy.data = (LowLevelADODataStore) this.data.clone();
        copy.visionData = (LowLevelFlagDataStore) this.visionData.clone();
        copy.savedTowerState = (LowLevelADODataStore) this.savedTowerState
                .clone();
        System.arraycopy(this.playerStartData, 0, copy.playerStartData, 0,
                this.playerStartData.length);
        System.arraycopy(this.findResult, 0, copy.findResult, 0,
                this.findResult.length);
        copy.horizontalWraparoundEnabled = this.horizontalWraparoundEnabled;
        copy.verticalWraparoundEnabled = this.verticalWraparoundEnabled;
        copy.thirdDimensionWraparoundEnabled = this.thirdDimensionWraparoundEnabled;
        return copy;
    }

    public void updateMovingBlockPosition(final int move, final int xLoc,
            final int yLoc, final MovingBlock block) {
        final int[] dirMove = DirectionResolver
                .unresolveRelativeDirection(move);
        final int xLocP = this.getPlayerRow();
        final int yLocP = this.getPlayerColumn();
        final int zLoc = this.getPlayerFloor();
        try {
            final AbstractDungeonObject there = this.getCell(xLoc + dirMove[0],
                    yLoc + dirMove[1], zLoc, DungeonConstants.LAYER_OBJECT);
            final AbstractDungeonObject ground = this.getCell(xLoc + dirMove[0],
                    yLoc + dirMove[1], zLoc, DungeonConstants.LAYER_GROUND);
            if (!there.isSolid() && xLoc + dirMove[0] != xLocP
                    && yLoc + dirMove[1] != yLocP) {
                this.setCell(block.getSavedObject(), xLoc, yLoc, zLoc,
                        DungeonConstants.LAYER_OBJECT);
                // Move the block
                block.setSavedObject(there);
                this.setCell(block, xLoc + dirMove[0], yLoc + dirMove[1], zLoc,
                        DungeonConstants.LAYER_OBJECT);
                // Does the ground have friction?
                if (!ground.hasFriction()) {
                    // No - move the block again
                    this.updateMovingBlockPosition(move, xLoc + dirMove[0],
                            yLoc + dirMove[1], block);
                }
            }
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
    }

    public void updateMonsterPosition(final int move, final int xLoc,
            final int yLoc, final MonsterObject monster) {
        final int[] dirMove = DirectionResolver
                .unresolveRelativeDirection(move);
        final int pLocX = this.getPlayerRow();
        final int pLocY = this.getPlayerColumn();
        final int zLoc = this.getPlayerFloor();
        try {
            final AbstractDungeonObject there = this.getCell(xLoc + dirMove[0],
                    yLoc + dirMove[1], zLoc, DungeonConstants.LAYER_OBJECT);
            final AbstractDungeonObject ground = this.getCell(xLoc + dirMove[0],
                    yLoc + dirMove[1], zLoc, DungeonConstants.LAYER_GROUND);
            if (!there.isSolid() && !there.getName().equals("Monster")) {
                if (this.radialScan(xLoc, yLoc, 0, pLocX, pLocY)) {
                    if (DungeonDiver4.getApplication()
                            .getMode() != Application.STATUS_BATTLE) {
                        DungeonDiver4.getApplication().getBattle().doBattle();
                        this.postBattle(monster, xLoc, yLoc, false);
                    }
                } else {
                    // Move the monster
                    this.setCell(monster.getSavedObject(), xLoc, yLoc, zLoc,
                            DungeonConstants.LAYER_OBJECT);
                    monster.setSavedObject(there);
                    this.setCell(monster, xLoc + dirMove[0], yLoc + dirMove[1],
                            zLoc, DungeonConstants.LAYER_OBJECT);
                    // Does the ground have friction?
                    if (!ground.hasFriction()) {
                        // No - move the monster again
                        this.updateMonsterPosition(move, xLoc + dirMove[0],
                                yLoc + dirMove[1], monster);
                    }
                }
            }
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
    }

    public void postBattle(final MonsterObject m, final int xLoc,
            final int yLoc, final boolean player) {
        final AbstractDungeonObject saved = m.getSavedObject();
        final int zLoc = this.getPlayerFloor();
        if (player) {
            DungeonDiver4.getApplication().getGameManager()
                    .setSavedDungeonObject(saved);
        } else {
            this.setCell(saved, xLoc, yLoc, zLoc,
                    DungeonConstants.LAYER_OBJECT);
        }
        this.generateOneMonster();
    }

    public void generateOneMonster() {
        final RandomRange row = new RandomRange(0, this.getRows() - 1);
        final RandomRange column = new RandomRange(0, this.getColumns() - 1);
        final int zLoc = this.getPlayerFloor();
        int randomRow, randomColumn;
        randomRow = row.generate();
        randomColumn = column.generate();
        AbstractDungeonObject currObj = this.getCell(randomRow, randomColumn,
                zLoc, DungeonConstants.LAYER_OBJECT);
        if (!currObj.isSolid()) {
            final MonsterObject m = new MonsterObject();
            m.setSavedObject(currObj);
            this.setCell(m, randomRow, randomColumn, zLoc,
                    DungeonConstants.LAYER_OBJECT);
        } else {
            while (currObj.isSolid()) {
                randomRow = row.generate();
                randomColumn = column.generate();
                currObj = this.getCell(randomRow, randomColumn, zLoc,
                        DungeonConstants.LAYER_OBJECT);
            }
            final MonsterObject m = new MonsterObject();
            m.setSavedObject(currObj);
            this.setCell(m, randomRow, randomColumn, zLoc,
                    DungeonConstants.LAYER_OBJECT);
        }
    }

    public void radialScanTimerAction(final int x, final int y, final int z,
            final int l, final int r, final String targetName,
            final int timerMod) {
        int u, v;
        u = v = 0;
        // Perform the scan, and do the action
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                try {
                    final String testName = this.getCell(u, v, z, l).getName();
                    if (testName.equals(targetName)) {
                        this.getCell(u, v, z, l).extendTimer(timerMod);
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Do nothing
                }
            }
        }
    }

    public void radialScanKillMonsters(final int x, final int y, final int z,
            final int l, final int r) {
        int u, v;
        u = v = 0;
        // Perform the scan, and do the action
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                try {
                    final String testName = this.getCell(u, v, z, l).getName();
                    if (testName.equals("Monster")) {
                        // Kill the monster
                        final MonsterObject m = (MonsterObject) this.getCell(u,
                                v, z, l);
                        this.setCell(m.getSavedObject(), u, v, z, l);
                        // Reward player for monster death
                        DungeonDiver4.getApplication().getBattle()
                                .doBattleByProxy();
                        // Respawn it
                        this.generateOneMonster();
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Do nothing
                }
            }
        }
    }

    public int getVisionRadius() {
        return this.visionRadius;
    }

    public int getPoisonPower() {
        return this.poisonPower;
    }

    public void setPoisonPower(final int pp) {
        int fPP = pp;
        if (fPP < 0) {
            fPP = 0;
        }
        if (fPP > 10) {
            fPP = 10;
        }
        this.poisonPower = fPP;
    }

    public void doPoisonousAmulet() {
        this.oldPoisonPower = this.poisonPower;
        if (this.poisonPower != 0) {
            this.poisonPower -= 5;
            if (this.poisonPower < 1) {
                this.poisonPower = 1;
            }
        }
    }

    public void doCounterpoisonAmulet() {
        this.oldPoisonPower = this.poisonPower;
        if (this.poisonPower != 0) {
            this.poisonPower += 5;
            if (this.poisonPower > 10) {
                this.poisonPower = 10;
            }
        }
    }

    public void undoPoisonAmulets() {
        this.poisonPower = this.oldPoisonPower;
    }

    public String getPoisonString() {
        if (this.poisonPower == 0) {
            return "Poison Power: None";
        } else if (this.poisonPower == 1) {
            return "Poison Power: 1 health / 1 step";
        } else {
            return "Poison Power: 1 health / " + this.poisonPower + " steps";
        }
    }

    public static int getMaxPoisonPower() {
        return LayeredTower.MAX_POISON_POWER;
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
            throw new IllegalArgumentException("Title cannot be null!");
        }
        this.levelTitle = title;
    }

    public String getLevelStartMessage() {
        return this.levelStartMessage;
    }

    public void setLevelStartMessage(final String msg) {
        if (msg == null) {
            throw new IllegalArgumentException("Message cannot be null!");
        }
        this.levelStartMessage = msg;
    }

    public String getLevelEndMessage() {
        return this.levelEndMessage;
    }

    public void setLevelEndMessage(final String msg) {
        if (msg == null) {
            throw new IllegalArgumentException("Message cannot be null!");
        }
        this.levelEndMessage = msg;
    }

    public boolean hasNote(final int x, final int y, final int z) {
        return this.noteData.getNote(y, x, z) != null;
    }

    public void createNote(final int x, final int y, final int z) {
        this.noteData.setNote(new DungeonNote(), y, x, z);
    }

    public DungeonNote getNote(final int x, final int y, final int z) {
        return this.noteData.getNote(y, x, z);
    }

    public AbstractDungeonObject getCell(final int row, final int col,
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

    public void findAllObjectPairsAndSwap(final AbstractDungeonObject o1,
            final AbstractDungeonObject o2) {
        int y, x, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final AbstractDungeonObject mo = this.getCell(y, x, z,
                            DungeonConstants.LAYER_OBJECT);
                    if (mo != null) {
                        if (mo.getName().equals(o1.getName())) {
                            this.setCell(o2, y, x, z,
                                    DungeonConstants.LAYER_OBJECT);
                        } else if (mo.getName().equals(o2.getName())) {
                            this.setCell(o1, y, x, z,
                                    DungeonConstants.LAYER_OBJECT);
                        }
                    }
                }
            }
        }
    }

    public void resize(final int x, final int y, final int z) {
        // Allocate temporary storage array
        final LowLevelADODataStore tempStorage = new LowLevelADODataStore(y, x,
                z, DungeonConstants.LAYER_COUNT);
        // Copy existing dungeon into temporary array
        int u, v, w, e;
        for (u = 0; u < y; u++) {
            for (v = 0; v < x; v++) {
                for (w = 0; w < z; w++) {
                    for (e = 0; e < DungeonConstants.LAYER_COUNT; e++) {
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
        this.savedTowerState = new LowLevelADODataStore(y, x, z,
                DungeonConstants.LAYER_COUNT);
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
        if ((this.visionMode
                | DungeonConstants.VISION_MODE_EXPLORE) == this.visionMode) {
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
                                | DungeonConstants.VISION_MODE_LOS) == this.visionMode) {
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
        if (this.visionMode == DungeonConstants.VISION_MODE_NONE) {
            return this.isSquareVisibleRadius(x1, y1, x2, y2);
        } else {
            boolean result = false;
            if ((this.visionMode
                    | DungeonConstants.VISION_MODE_EXPLORE) == this.visionMode) {
                result = result || this.isSquareVisibleExplore(x2, y2);
                if (result && (this.visionMode
                        | DungeonConstants.VISION_MODE_LOS) == this.visionMode) {
                    if (this.areCoordsInBounds(x1, y1, x2, y2)) {
                        // In bounds
                        result = result
                                || this.isSquareVisibleLOS(x1, y1, x2, y2);
                        result = result
                                && this.isSquareVisibleRadius(x1, y1, x2, y2);
                    } else {
                        // Out of bounds
                        result = result
                                && this.isSquareVisibleLOS(x1, y1, x2, y2);
                        result = result
                                && this.isSquareVisibleRadius(x1, y1, x2, y2);
                    }
                }
            } else {
                if (this.areCoordsInBounds(x1, y1, x2, y2)) {
                    // In bounds
                    result = result || this.isSquareVisibleLOS(x1, y1, x2, y2);
                    result = result
                            && this.isSquareVisibleRadius(x1, y1, x2, y2);
                } else {
                    // Out of bounds
                    result = result && this.isSquareVisibleLOS(x1, y1, x2, y2);
                    result = result
                            && this.isSquareVisibleRadius(x1, y1, x2, y2);
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

    private boolean isSquareVisibleRadius(final int x1, final int y1,
            final int x2, final int y2) {
        final LightGem lg = new LightGem();
        final DarkGem dg = new DarkGem();
        boolean result = true;
        final int zLoc = this.getPlayerFloor();
        final int xDist = this.pointDistance(x1, x2, 1);
        final int yDist = this.pointDistance(y1, y2, 2);
        if (xDist <= this.visionRadius && yDist <= this.visionRadius) {
            if (this.radialScanVision(x2, y2, zLoc,
                    DungeonConstants.LAYER_OBJECT,
                    AbstractLightModifier.getEffectRadius(), dg.getName())) {
                result = false;
            } else {
                result = true;
            }
        } else {
            if (this.radialScanVision(x2, y2, zLoc,
                    DungeonConstants.LAYER_OBJECT,
                    AbstractLightModifier.getEffectRadius(), lg.getName())) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
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
                final AbstractDungeonObject obj = this.getCell(fx1, fy1, zLoc,
                        DungeonConstants.LAYER_OBJECT);
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

    private int pointDistance(final int u, final int v, final int w) {
        if (w == 1) {
            if (this.horizontalWraparoundEnabled) {
                final int max = this.getRows();
                return Math.min(Math.abs(u - v), max - Math.abs(u - v));
            } else {
                return Math.abs(u - v);
            }
        } else if (w == 2) {
            if (this.verticalWraparoundEnabled) {
                final int max = this.getColumns();
                return Math.min(Math.abs(u - v), max - Math.abs(u - v));
            } else {
                return Math.abs(u - v);
            }
        } else if (w == 3) {
            if (this.thirdDimensionWraparoundEnabled) {
                final int max = this.getFloors();
                return Math.min(Math.abs(u - v), max - Math.abs(u - v));
            } else {
                return Math.abs(u - v);
            }
        } else {
            return Math.abs(u - v);
        }
    }

    public void setCell(final AbstractDungeonObject mo, final int row,
            final int col, final int floor, final int extra) {
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
                this.savedPlayerLocationData, 0,
                this.playerLocationData.length);
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

    public void fill(final AbstractDungeonObject bottom,
            final AbstractDungeonObject top) {
        int y, x, z, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < DungeonConstants.LAYER_COUNT; e++) {
                        if (e == DungeonConstants.LAYER_GROUND) {
                            this.setCell(bottom, y, x, z, e);
                        } else {
                            this.setCell(top, y, x, z, e);
                        }
                    }
                }
            }
        }
    }

    public void fillFloor(final AbstractDungeonObject bottom,
            final AbstractDungeonObject top, final int z) {
        int x, y, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (e = 0; e < DungeonConstants.LAYER_COUNT; e++) {
                    if (e == DungeonConstants.LAYER_GROUND) {
                        this.setCell(bottom, y, x, z, e);
                    } else {
                        this.setCell(top, y, x, z, e);
                    }
                }
            }
        }
    }

    public void fillRandomly(final Dungeon dungeon, final int w) {
        for (int z = 0; z < this.getFloors(); z++) {
            this.fillFloorRandomly(dungeon, z, w);
        }
    }

    public void fillRandomlyCustom(final Dungeon dungeon, final int w) {
        for (int z = 0; z < this.getFloors(); z++) {
            this.fillFloorRandomlyCustom(dungeon, z, w);
        }
    }

    public void fillFloorRandomly(final Dungeon dungeon, final int z,
            final int w) {
        // Pre-Pass
        final DungeonObjectList objects = DungeonDiver4.getApplication()
                .getObjects();
        final AbstractDungeonObject pass1FillBottom = PreferencesManager
                .getEditorDefaultFill();
        final AbstractDungeonObject pass1FillTop = new Empty();
        RandomRange r = null;
        int x, y, e;
        // Pass 1
        this.fillFloor(pass1FillBottom, pass1FillTop, z);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        for (e = 0; e < DungeonConstants.LAYER_COUNT; e++) {
            final AbstractDungeonObject[] objectsWithoutPrerequisites = objects
                    .getAllWithoutPrerequisiteAndNotRequired(e);
            if (objectsWithoutPrerequisites != null) {
                r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
                for (x = 0; x < columns; x++) {
                    for (y = 0; y < rows; y++) {
                        final AbstractDungeonObject placeObj = objectsWithoutPrerequisites[r
                                .generate()];
                        final boolean okay = placeObj
                                .shouldGenerateObject(dungeon, x, y, z, w, e);
                        if (okay) {
                            this.setCell(objects.getNewInstanceByName(
                                    placeObj.getName()), y, x, z, e);
                            placeObj.editorGenerateHook(y, x, z);
                        }
                    }
                }
            }
        }
        // Pass 3
        for (int layer = 0; layer < DungeonConstants.LAYER_COUNT; layer++) {
            final AbstractDungeonObject[] requiredObjects = objects
                    .getAllRequired(layer);
            if (requiredObjects != null) {
                final RandomRange row = new RandomRange(0, this.getRows() - 1);
                final RandomRange column = new RandomRange(0,
                        this.getColumns() - 1);
                int randomColumn, randomRow;
                for (x = 0; x < requiredObjects.length; x++) {
                    randomRow = row.generate();
                    randomColumn = column.generate();
                    final AbstractDungeonObject currObj = requiredObjects[x];
                    final int min = currObj.getMinimumRequiredQuantity(dungeon);
                    int max = currObj.getMaximumRequiredQuantity(dungeon);
                    if (max == RandomGenerationRule.NO_LIMIT) {
                        // Maximum undefined, so define it relative to this
                        // dungeon
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
                        if (currObj.shouldGenerateObject(dungeon, randomRow,
                                randomColumn, z, w, layer)) {
                            this.setCell(
                                    objects.getNewInstanceByName(
                                            currObj.getName()),
                                    randomColumn, randomRow, z, layer);
                            currObj.editorGenerateHook(y, x, z);
                        } else {
                            while (!currObj.shouldGenerateObject(dungeon,
                                    randomColumn, randomRow, z, w, layer)) {
                                randomRow = row.generate();
                                randomColumn = column.generate();
                            }
                            this.setCell(
                                    objects.getNewInstanceByName(
                                            currObj.getName()),
                                    randomColumn, randomRow, z, layer);
                            currObj.editorGenerateHook(y, x, z);
                        }
                    }
                }
            }
        }
    }

    public void fillFloorRandomlyCustom(final Dungeon dungeon, final int z,
            final int w) {
        // Pre-Pass
        final DungeonObjectList objects = DungeonDiver4.getApplication()
                .getObjects();
        final AbstractDungeonObject pass1FillBottom = PreferencesManager
                .getEditorDefaultFill();
        final AbstractDungeonObject pass1FillTop = new Empty();
        final AbstractDungeonObject[] withoutRuleSets = objects
                .getAllObjectsWithoutRuleSets();
        final AbstractDungeonObject[] withRuleSets = objects
                .getAllObjectsWithRuleSets();
        RandomRange r = null;
        int x, y, e;
        // Pass 1
        this.fillFloor(pass1FillBottom, pass1FillTop, z);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        if (withoutRuleSets != null) {
            for (e = 0; e < DungeonConstants.LAYER_COUNT; e++) {
                final AbstractDungeonObject[] objectsWithoutPrerequisites = DungeonObjectList
                        .getAllWithoutPrerequisiteAndNotRequiredSubset(
                                withoutRuleSets, e);
                if (objectsWithoutPrerequisites != null
                        && objectsWithoutPrerequisites.length > 0) {
                    r = new RandomRange(0,
                            objectsWithoutPrerequisites.length - 1);
                    for (x = 0; x < columns; x++) {
                        for (y = 0; y < rows; y++) {
                            final AbstractDungeonObject placeObj = objectsWithoutPrerequisites[r
                                    .generate()];
                            final boolean okay = placeObj.shouldGenerateObject(
                                    dungeon, y, x, z, w, e);
                            if (okay) {
                                this.setCell(
                                        objects.getNewInstanceByName(
                                                placeObj.getName()),
                                        y, x, z, e);
                                placeObj.editorGenerateHook(y, x, z);
                            }
                        }
                    }
                }
            }
            // Pass 3
            for (int layer = 0; layer < DungeonConstants.LAYER_COUNT; layer++) {
                final AbstractDungeonObject[] requiredObjects = DungeonObjectList
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
                        final AbstractDungeonObject currObj = requiredObjects[x];
                        final int min = currObj
                                .getMinimumRequiredQuantity(dungeon);
                        int max = currObj.getMaximumRequiredQuantity(dungeon);
                        if (max == RandomGenerationRule.NO_LIMIT) {
                            // Maximum undefined, so define it relative to this
                            // dungeon
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
                            if (currObj.shouldGenerateObject(dungeon, randomRow,
                                    randomColumn, z, w, layer)) {
                                this.setCell(
                                        objects.getNewInstanceByName(
                                                currObj.getName()),
                                        randomColumn, randomRow, z, layer);
                                currObj.editorGenerateHook(y, x, z);
                            } else {
                                while (!currObj.shouldGenerateObject(dungeon,
                                        randomColumn, randomRow, z, w, layer)) {
                                    randomRow = row.generate();
                                    randomColumn = column.generate();
                                }
                                this.setCell(
                                        objects.getNewInstanceByName(
                                                currObj.getName()),
                                        randomColumn, randomRow, z, layer);
                                currObj.editorGenerateHook(y, x, z);
                            }
                        }
                    }
                }
            }
        }
        if (withRuleSets != null) {
            // Pass 4
            for (e = 0; e < DungeonConstants.LAYER_COUNT; e++) {
                final AbstractDungeonObject[] objectsWithoutPrerequisites = DungeonObjectList
                        .getAllWithoutPrerequisiteAndNotRequiredSubset(
                                withRuleSets, e);
                if (objectsWithoutPrerequisites != null) {
                    r = new RandomRange(0,
                            objectsWithoutPrerequisites.length - 1);
                    for (x = 0; x < columns; x++) {
                        for (y = 0; y < rows; y++) {
                            final AbstractDungeonObject placeObj = objectsWithoutPrerequisites[r
                                    .generate()];
                            final boolean okay = placeObj.getRuleSet()
                                    .shouldGenerateObject(dungeon, y, x, z, w,
                                            e);
                            if (okay) {
                                this.setCell(
                                        objects.getNewInstanceByName(
                                                placeObj.getName()),
                                        y, x, z, e);
                                placeObj.editorGenerateHook(y, x, z);
                            }
                        }
                    }
                }
            }
            // Pass 5
            for (int layer = 0; layer < DungeonConstants.LAYER_COUNT; layer++) {
                final AbstractDungeonObject[] requiredObjects = DungeonObjectList
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
                        final AbstractDungeonObject currObj = requiredObjects[x];
                        final int min = currObj.getRuleSet()
                                .getMinimumRequiredQuantity(dungeon);
                        int max = currObj.getRuleSet()
                                .getMaximumRequiredQuantity(dungeon);
                        if (max == RandomGenerationRule.NO_LIMIT) {
                            // Maximum undefined, so define it relative to this
                            // dungeon
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
                            if (currObj.getRuleSet().shouldGenerateObject(
                                    dungeon, randomColumn, randomRow, z, w,
                                    layer)) {
                                this.setCell(
                                        objects.getNewInstanceByName(
                                                currObj.getName()),
                                        randomColumn, randomRow, z, layer);
                                currObj.editorGenerateHook(y, x, z);
                            } else {
                                while (!currObj.getRuleSet()
                                        .shouldGenerateObject(dungeon,
                                                randomRow, randomColumn, z, w,
                                                layer)) {
                                    randomRow = row.generate();
                                    randomColumn = column.generate();
                                }
                                this.setCell(
                                        objects.getNewInstanceByName(
                                                currObj.getName()),
                                        randomColumn, randomRow, z, layer);
                                currObj.editorGenerateHook(y, x, z);
                            }
                        }
                    }
                }
            }
        }
    }

    public void fillLayer(final AbstractDungeonObject fillWith, final int e) {
        int y, x, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    this.setCell(fillWith, y, x, z, e);
                }
            }
        }
    }

    public void fillFloorAndLayer(final AbstractDungeonObject fillWith,
            final int z, final int e) {
        int x, y;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                this.setCell(fillWith, y, x, z, e);
            }
        }
    }

    public void fillLayerRandomly(final Dungeon dungeon, final int w,
            final int e) {
        for (int z = 0; z < this.getFloors(); z++) {
            this.fillFloorAndLayerRandomly(dungeon, z, w, e);
        }
    }

    public void fillLayerRandomlyCustom(final Dungeon dungeon, final int w,
            final int e) {
        for (int z = 0; z < this.getFloors(); z++) {
            this.fillFloorAndLayerRandomlyCustom(dungeon, z, w, e);
        }
    }

    public void fillFloorAndLayerRandomly(final Dungeon dungeon, final int z,
            final int w, final int layer) {
        // Pre-Pass
        final DungeonObjectList objects = DungeonDiver4.getApplication()
                .getObjects();
        final AbstractDungeonObject pass1Fill = PreferencesManager
                .getEditorDefaultFill(layer);
        RandomRange r = null;
        int x, y;
        // Pass 1
        this.fillFloorAndLayer(pass1Fill, z, layer);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        final AbstractDungeonObject[] objectsWithoutPrerequisites = objects
                .getAllWithoutPrerequisiteAndNotRequired(layer);
        if (objectsWithoutPrerequisites != null) {
            r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
            for (x = 0; x < columns; x++) {
                for (y = 0; y < rows; y++) {
                    final AbstractDungeonObject placeObj = objectsWithoutPrerequisites[r
                            .generate()];
                    final boolean okay = placeObj.shouldGenerateObject(dungeon,
                            x, y, z, w, layer);
                    if (okay) {
                        this.setCell(objects.getNewInstanceByName(
                                placeObj.getName()), y, x, z, layer);
                        placeObj.editorGenerateHook(y, x, z);
                    }
                }
            }
        }
        // Pass 3
        final AbstractDungeonObject[] requiredObjects = objects
                .getAllRequired(layer);
        if (requiredObjects != null) {
            final RandomRange row = new RandomRange(0, this.getRows() - 1);
            final RandomRange column = new RandomRange(0,
                    this.getColumns() - 1);
            int randomColumn, randomRow;
            for (x = 0; x < requiredObjects.length; x++) {
                randomRow = row.generate();
                randomColumn = column.generate();
                final AbstractDungeonObject currObj = requiredObjects[x];
                final int min = currObj.getMinimumRequiredQuantity(dungeon);
                int max = currObj.getMaximumRequiredQuantity(dungeon);
                if (max == RandomGenerationRule.NO_LIMIT) {
                    // Maximum undefined, so define it relative to this dungeon
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
                    if (currObj.shouldGenerateObject(dungeon, randomRow,
                            randomColumn, z, w, layer)) {
                        this.setCell(
                                objects.getNewInstanceByName(currObj.getName()),
                                randomColumn, randomRow, z, layer);
                        currObj.editorGenerateHook(y, x, z);
                    } else {
                        while (!currObj.shouldGenerateObject(dungeon,
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

    public void fillFloorAndLayerRandomlyCustom(final Dungeon dungeon,
            final int z, final int w, final int layer) {
        // Pre-Pass
        final DungeonObjectList objects = DungeonDiver4.getApplication()
                .getObjects();
        final AbstractDungeonObject pass1Fill = PreferencesManager
                .getEditorDefaultFill(layer);
        final AbstractDungeonObject[] withoutRuleSets = objects
                .getAllObjectsWithoutRuleSets();
        final AbstractDungeonObject[] withRuleSets = objects
                .getAllObjectsWithRuleSets();
        RandomRange r = null;
        int x, y;
        // Pass 1
        this.fillFloorAndLayer(pass1Fill, z, layer);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        if (withoutRuleSets != null) {
            final AbstractDungeonObject[] objectsWithoutPrerequisites = DungeonObjectList
                    .getAllWithoutPrerequisiteAndNotRequiredSubset(
                            withoutRuleSets, layer);
            if (objectsWithoutPrerequisites != null
                    && objectsWithoutPrerequisites.length > 0) {
                r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
                for (x = 0; x < columns; x++) {
                    for (y = 0; y < rows; y++) {
                        final AbstractDungeonObject placeObj = objectsWithoutPrerequisites[r
                                .generate()];
                        final boolean okay = placeObj.shouldGenerateObject(
                                dungeon, y, x, z, w, layer);
                        if (okay) {
                            this.setCell(
                                    objects.getNewInstanceByName(
                                            placeObj.getName()),
                                    y, x, z, layer);
                            placeObj.editorGenerateHook(y, x, z);
                        }
                    }
                }
            }
            // Pass 3
            final AbstractDungeonObject[] requiredObjects = DungeonObjectList
                    .getAllRequiredSubset(withoutRuleSets, layer);
            if (requiredObjects != null) {
                final RandomRange row = new RandomRange(0, this.getRows() - 1);
                final RandomRange column = new RandomRange(0,
                        this.getColumns() - 1);
                int randomColumn, randomRow;
                for (x = 0; x < requiredObjects.length; x++) {
                    randomRow = row.generate();
                    randomColumn = column.generate();
                    final AbstractDungeonObject currObj = requiredObjects[x];
                    final int min = currObj.getMinimumRequiredQuantity(dungeon);
                    int max = currObj.getMaximumRequiredQuantity(dungeon);
                    if (max == RandomGenerationRule.NO_LIMIT) {
                        // Maximum undefined, so define it relative to this
                        // dungeon
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
                        if (currObj.shouldGenerateObject(dungeon, randomRow,
                                randomColumn, z, w, layer)) {
                            this.setCell(
                                    objects.getNewInstanceByName(
                                            currObj.getName()),
                                    randomColumn, randomRow, z, layer);
                            currObj.editorGenerateHook(y, x, z);
                        } else {
                            while (!currObj.shouldGenerateObject(dungeon,
                                    randomColumn, randomRow, z, w, layer)) {
                                randomRow = row.generate();
                                randomColumn = column.generate();
                            }
                            this.setCell(
                                    objects.getNewInstanceByName(
                                            currObj.getName()),
                                    randomColumn, randomRow, z, layer);
                            currObj.editorGenerateHook(y, x, z);
                        }
                    }
                }
            }
        }
        if (withRuleSets != null) {
            // Pass 4
            final AbstractDungeonObject[] objectsWithoutPrerequisites = DungeonObjectList
                    .getAllWithoutPrerequisiteAndNotRequiredSubset(withRuleSets,
                            layer);
            if (objectsWithoutPrerequisites != null) {
                r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
                for (x = 0; x < columns; x++) {
                    for (y = 0; y < rows; y++) {
                        final AbstractDungeonObject placeObj = objectsWithoutPrerequisites[r
                                .generate()];
                        final boolean okay = placeObj.getRuleSet()
                                .shouldGenerateObject(dungeon, y, x, z, w,
                                        layer);
                        if (okay) {
                            this.setCell(
                                    objects.getNewInstanceByName(
                                            placeObj.getName()),
                                    y, x, z, layer);
                            placeObj.editorGenerateHook(y, x, z);
                        }
                    }
                }
            }
            // Pass 5
            final AbstractDungeonObject[] requiredObjects = DungeonObjectList
                    .getAllRequiredSubset(withRuleSets, layer);
            if (requiredObjects != null) {
                final RandomRange row = new RandomRange(0, this.getRows() - 1);
                final RandomRange column = new RandomRange(0,
                        this.getColumns() - 1);
                int randomColumn, randomRow;
                for (x = 0; x < requiredObjects.length; x++) {
                    randomRow = row.generate();
                    randomColumn = column.generate();
                    final AbstractDungeonObject currObj = requiredObjects[x];
                    final int min = currObj.getRuleSet()
                            .getMinimumRequiredQuantity(dungeon);
                    int max = currObj.getRuleSet()
                            .getMaximumRequiredQuantity(dungeon);
                    if (max == RandomGenerationRule.NO_LIMIT) {
                        // Maximum undefined, so define it relative to this
                        // dungeon
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
                        if (currObj.getRuleSet().shouldGenerateObject(dungeon,
                                randomColumn, randomRow, z, w, layer)) {
                            this.setCell(
                                    objects.getNewInstanceByName(
                                            currObj.getName()),
                                    randomColumn, randomRow, z, layer);
                            currObj.editorGenerateHook(y, x, z);
                        } else {
                            while (!currObj.getRuleSet().shouldGenerateObject(
                                    dungeon, randomRow, randomColumn, z, w,
                                    layer)) {
                                randomRow = row.generate();
                                randomColumn = column.generate();
                            }
                            this.setCell(
                                    objects.getNewInstanceByName(
                                            currObj.getName()),
                                    randomColumn, randomRow, z, layer);
                            currObj.editorGenerateHook(y, x, z);
                        }
                    }
                }
            }
        }
    }

    public void fillRandomlyInBattle(final Dungeon map, final int w,
            final AbstractDungeonObject pass1FillBottom,
            final AbstractDungeonObject pass1FillTop) {
        for (int z = 0; z < this.getFloors(); z++) {
            this.fillFloorRandomlyInBattle(map, z, w, pass1FillBottom,
                    pass1FillTop);
        }
    }

    private void fillFloorRandomlyInBattle(final Dungeon map, final int z,
            final int w, final AbstractDungeonObject pass1FillBottom,
            final AbstractDungeonObject pass1FillTop) {
        // Pre-Pass
        final DungeonObjectList objects = new DungeonObjectList();
        RandomRange r = null;
        int x, y, e, u, v;
        // Pass 1
        this.fillFloor(pass1FillBottom, pass1FillTop, z);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        for (e = 0; e < DungeonConstants.LAYER_COUNT; e++) {
            final AbstractDungeonObject[] objectsWithoutPrerequisites = objects
                    .getAllNotRequiredInBattle(e);
            if (objectsWithoutPrerequisites != null) {
                r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
                for (x = 0; x < columns; x++) {
                    for (y = 0; y < rows; y++) {
                        if (e == DungeonConstants.LAYER_GROUND) {
                            for (x = 0; x < columns; x += this.regionSize) {
                                for (y = 0; y < rows; y += this.regionSize) {
                                    final AbstractDungeonObject placeObj = objectsWithoutPrerequisites[r
                                            .generate()];
                                    final boolean okay = placeObj
                                            .shouldGenerateObjectInBattle(map,
                                                    y, x, z, w, e);
                                    if (okay) {
                                        for (u = 0; u < this.regionSize; u++) {
                                            for (v = 0; v < this.regionSize; v++) {
                                                this.setCell(objects
                                                        .getNewInstanceByName(
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
                                    final AbstractDungeonObject placeObj = objectsWithoutPrerequisites[r
                                            .generate()];
                                    final boolean okay = placeObj
                                            .shouldGenerateObjectInBattle(map,
                                                    y, x, z, w, e);
                                    if (okay) {
                                        this.setCell(
                                                objects.getNewInstanceByName(
                                                        placeObj.getName()),
                                                x, y, z, e);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // Pass 3
        for (int layer = 0; layer < DungeonConstants.LAYER_COUNT; layer++) {
            final AbstractDungeonObject[] requiredObjects = objects
                    .getAllRequiredInBattle(layer);
            if (requiredObjects != null) {
                final RandomRange row = new RandomRange(0, this.getRows() - 1);
                final RandomRange column = new RandomRange(0,
                        this.getColumns() - 1);
                int randomColumn, randomRow;
                for (x = 0; x < requiredObjects.length; x++) {
                    randomRow = row.generate();
                    randomColumn = column.generate();
                    final AbstractDungeonObject currObj = requiredObjects[x];
                    final int min = currObj
                            .getMinimumRequiredQuantityInBattle(map);
                    int max = currObj.getMaximumRequiredQuantityInBattle(map);
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
                            while (!currObj.shouldGenerateObjectInBattle(map,
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

    private void fillNulls() {
        final AbstractDungeonObject bottom = PreferencesManager
                .getEditorDefaultFill();
        final AbstractDungeonObject top = new Empty();
        int y, x, z, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < DungeonConstants.LAYER_COUNT; e++) {
                        if (this.getCell(y, x, z, e) == null) {
                            if (e == DungeonConstants.LAYER_GROUND) {
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

    public void generateStart() {
        final int z = 0;
        final RandomRange row = new RandomRange(0, this.getRows() - 1);
        final RandomRange column = new RandomRange(0, this.getColumns() - 1);
        int randomColumn, randomRow;
        randomRow = row.generate();
        randomColumn = column.generate();
        AbstractDungeonObject currObj = this.getCell(randomRow, randomColumn, z,
                DungeonConstants.LAYER_OBJECT);
        randomRow = row.generate();
        randomColumn = column.generate();
        if (!currObj.isSolid()) {
            this.setStartRow(randomRow);
            this.setStartColumn(randomColumn);
            this.setStartFloor(z);
        } else {
            while (currObj.isSolid()) {
                randomRow = row.generate();
                randomColumn = column.generate();
                currObj = this.getCell(randomRow, randomColumn, z,
                        DungeonConstants.LAYER_OBJECT);
            }
            this.setStartRow(randomRow);
            this.setStartColumn(randomColumn);
            this.setStartFloor(z);
        }
    }

    public void save() {
        int y, x, z, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < DungeonConstants.LAYER_COUNT; e++) {
                        this.savedTowerState.setCell(this.getCell(y, x, z, e),
                                x, y, z, e);
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
                    for (e = 0; e < DungeonConstants.LAYER_COUNT; e++) {
                        this.setCell(this.savedTowerState.getCell(x, y, z, e),
                                y, x, z, e);
                    }
                }
            }
        }
    }

    public void hotGround(final int x, final int y, final int z) {
        this.setCell(new HotRock(), x, y, z, DungeonConstants.LAYER_GROUND);
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

    public void findAllMatchingObjectsAndDecay(final AbstractDungeonObject o) {
        int y, x, z;
        final AbstractDungeonObject decayTo = new Empty();
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final AbstractDungeonObject mo = this.getCell(y, x, z,
                            DungeonConstants.LAYER_OBJECT);
                    if (mo != null) {
                        if (mo.getName().equals(o.getName())) {
                            this.setCell(decayTo, y, x, z,
                                    DungeonConstants.LAYER_OBJECT);
                        }
                    }
                }
            }
        }
    }

    public void masterTrapTrigger() {
        int y, x, z;
        final AbstractDungeonObject decayTo = new Empty();
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final AbstractDungeonObject mo = this.getCell(y, x, z,
                            DungeonConstants.LAYER_OBJECT);
                    if (mo != null) {
                        if (mo.isOfType(TypeConstants.TYPE_WALL_TRAP) || mo
                                .isOfType(TypeConstants.TYPE_TRAPPED_WALL)) {
                            this.setCell(decayTo, y, x, z,
                                    DungeonConstants.LAYER_OBJECT);
                        }
                    }
                }
            }
        }
    }

    public void tickTimers(final int floor) {
        int x, y;
        // Tick all DungeonObject timers
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                final AbstractDungeonObject mo = this.getCell(y, x, floor,
                        DungeonConstants.LAYER_OBJECT);
                if (mo != null) {
                    mo.tickTimer(y, x);
                }
            }
        }
    }

    public boolean rotateRadiusClockwise(final int x, final int y, final int z,
            final int r) {
        int u, v, l;
        u = v = l = 0;
        int uFix, vFix, uRot, vRot, uAdj, vAdj;
        uFix = vFix = uRot = vRot = uAdj = vAdj = 0;
        final int cosineTheta = 0;
        final int sineTheta = 1;
        final AbstractDungeonObject[][][] tempStorage = new AbstractDungeonObject[2
                * r + 1][2 * r + 1][DungeonConstants.LAYER_COUNT];
        try {
            for (u = x - r; u <= x + r; u++) {
                for (v = y - r; v <= y + r; v++) {
                    for (l = 0; l < DungeonConstants.LAYER_COUNT; l++) {
                        uFix = u - x;
                        vFix = v - y;
                        uRot = uFix * cosineTheta - vFix * sineTheta;
                        vRot = uFix * sineTheta + vFix * cosineTheta;
                        uAdj = uRot + r;
                        vAdj = vRot + r;
                        tempStorage[uAdj][vAdj][l] = this.getCell(u, v, z, l);
                    }
                }
            }
            for (u = x - r; u <= x + r; u++) {
                for (v = y - r; v <= y + r; v++) {
                    for (l = 0; l < DungeonConstants.LAYER_COUNT; l++) {
                        uFix = u - (x - r);
                        vFix = v - (y - r);
                        this.setCell(tempStorage[uFix][vFix][l], u, v, z, l);
                    }
                }
            }
            return true;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            return false;
        }
    }

    public boolean rotateRadiusCounterclockwise(final int x, final int y,
            final int z, final int r) {
        int u, v, l;
        u = v = l = 0;
        int uFix, vFix, uRot, vRot, uAdj, vAdj;
        uFix = vFix = uRot = vRot = uAdj = vAdj = 0;
        final int cosineTheta = 0;
        final int sineTheta = 1;
        final AbstractDungeonObject[][][] tempStorage = new AbstractDungeonObject[2
                * r + 1][2 * r + 1][DungeonConstants.LAYER_COUNT];
        try {
            for (u = x - r; u <= x + r; u++) {
                for (v = y - r; v <= y + r; v++) {
                    for (l = 0; l < DungeonConstants.LAYER_COUNT; l++) {
                        uFix = u - x;
                        vFix = v - y;
                        uRot = uFix * cosineTheta + vFix * sineTheta;
                        vRot = -uFix * sineTheta + vFix * cosineTheta;
                        uAdj = uRot + r;
                        vAdj = vRot + r;
                        tempStorage[uAdj][vAdj][l] = this.getCell(u, v, z, l);
                    }
                }
            }
            for (u = x - r; u <= x + r; u++) {
                for (v = y - r; v <= y + r; v++) {
                    for (l = 0; l < DungeonConstants.LAYER_COUNT; l++) {
                        uFix = u - (x - r);
                        vFix = v - (y - r);
                        this.setCell(tempStorage[uFix][vFix][l], u, v, z, l);
                    }
                }
            }
            return true;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            return false;
        }
    }

    public boolean radialScan(final int cx, final int cy, final int r,
            final int tx, final int ty) {
        return Math.abs(tx - cx) <= r && Math.abs(ty - cy) <= r;
    }

    public boolean radialScanVision(final int x, final int y, final int z,
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

    public void radialScanFreezeObjects(final int x, final int y, final int z,
            final int r) {
        int u, v;
        u = v = 0;
        // Perform the scan, and do the action
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                try {
                    final boolean reactsToIce = this
                            .getCell(u, v, z, DungeonConstants.LAYER_OBJECT)
                            .isOfType(TypeConstants.TYPE_REACTS_TO_ICE);
                    if (reactsToIce) {
                        final AbstractDungeonObject there = this.getCell(u, v,
                                z, DungeonConstants.LAYER_OBJECT);
                        if (there.getClass() == BarrierGenerator.class) {
                            // Freeze the generator
                            this.setCell(new IcedBarrierGenerator(), u, v, z,
                                    DungeonConstants.LAYER_OBJECT);
                        } else {
                            // Assume object is already iced, and extend its
                            // timer
                            there.extendTimerByInitialValue();
                        }
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Do nothing
                }
            }
        }
    }

    public void radialScanFreezeGround(final int x, final int y, final int z,
            final int r) {
        int u, v;
        u = v = 0;
        // Perform the scan, and do the action
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                try {
                    // Freeze the ground
                    this.setCell(new Ice(), u, v, z,
                            DungeonConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Do nothing
                }
            }
        }
    }

    public void radialScanEnrageObjects(final int x, final int y, final int z,
            final int r) {
        int u, v;
        u = v = 0;
        // Perform the scan, and do the action
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                try {
                    final boolean reactsToFire = this
                            .getCell(u, v, z, DungeonConstants.LAYER_OBJECT)
                            .isOfType(TypeConstants.TYPE_REACTS_TO_FIRE);
                    if (reactsToFire) {
                        final AbstractDungeonObject there = this.getCell(u, v,
                                z, DungeonConstants.LAYER_OBJECT);
                        if (there.getClass() == BarrierGenerator.class) {
                            // Enrage the generator
                            this.setCell(new EnragedBarrierGenerator(), u, v, z,
                                    DungeonConstants.LAYER_OBJECT);
                        } else {
                            // Assume object is already enraged, and reset its
                            // timer
                            there.resetTimer();
                        }
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Do nothing
                }
            }
        }
    }

    public void radialScanBurnGround(final int x, final int y, final int z,
            final int r) {
        int u, v;
        u = v = 0;
        // Perform the scan, and do the action
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                try {
                    // Burn the ground
                    this.setCell(new Dirt(), u, v, z,
                            DungeonConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Do nothing
                }
            }
        }
    }

    public void radialScanPoisonObjects(final int x, final int y, final int z,
            final int r) {
        int u, v;
        u = v = 0;
        // Perform the scan, and do the action
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                try {
                    final boolean reactsToPoison = this
                            .getCell(u, v, z, DungeonConstants.LAYER_OBJECT)
                            .isOfType(TypeConstants.TYPE_REACTS_TO_POISON);
                    if (reactsToPoison) {
                        final AbstractDungeonObject there = this.getCell(u, v,
                                z, DungeonConstants.LAYER_OBJECT);
                        if (there.getClass() == BarrierGenerator.class) {
                            // Weaken the generator
                            this.setCell(new PoisonedBarrierGenerator(), u, v,
                                    z, DungeonConstants.LAYER_OBJECT);
                        } else {
                            // Assume object is already poisoned, and reset its
                            // timer
                            there.resetTimer();
                        }
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Do nothing
                }
            }
        }
    }

    public void radialScanPoisonGround(final int x, final int y, final int z,
            final int r) {
        int u, v;
        u = v = 0;
        // Perform the scan, and do the action
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                try {
                    // Poison the ground
                    this.setCell(new Slime(), u, v, z,
                            DungeonConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Do nothing
                }
            }
        }
    }

    public void radialScanShockObjects(final int x, final int y, final int z,
            final int r) {
        int u, v;
        u = v = 0;
        // Perform the scan, and do the action
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                try {
                    final boolean reactsToShock = this
                            .getCell(u, v, z, DungeonConstants.LAYER_OBJECT)
                            .isOfType(TypeConstants.TYPE_REACTS_TO_SHOCK);
                    if (reactsToShock) {
                        final AbstractDungeonObject there = this.getCell(u, v,
                                z, DungeonConstants.LAYER_OBJECT);
                        if (there.getClass() == BarrierGenerator.class) {
                            // Shock the generator
                            this.setCell(new ShockedBarrierGenerator(), u, v, z,
                                    DungeonConstants.LAYER_OBJECT);
                        } else {
                            // Assume object is already shocked, and reset its
                            // timer
                            there.resetTimer();
                        }
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Do nothing
                }
            }
        }
    }

    public void radialScanShockGround(final int x, final int y, final int z,
            final int r) {
        int u, v;
        u = v = 0;
        // Perform the scan, and do the action
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                try {
                    // Shock the ground
                    this.setCell(new ForceField(), u, v, z,
                            DungeonConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Do nothing
                }
            }
        }
    }

    public void radialScanWarpObjects(final int x, final int y, final int z,
            final int l, final int r) {
        int u, v;
        u = v = 0;
        // Perform the scan, and do the action
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                try {
                    // If the object here isn't an empty apsce
                    if (!this.getCell(u, v, z, l).getName().equals("Empty")) {
                        // Warp the object
                        this.warpObject(this.getCell(u, v, z, l), u, v, z, l);
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Do nothing
                }
            }
        }
    }

    public void radialScanShuffleObjects(final int x, final int y, final int z,
            final int r) {
        int u, v, l, uFix, vFix;
        final int px = this.getPlayerRow();
        final int py = this.getPlayerColumn();
        final boolean needsFix = px >= x - r && px <= x + r && py >= y - r
                && py <= y + r;
        final AbstractDungeonObject[][][] preShuffle = new AbstractDungeonObject[2
                * r + 1][2 * r + 1][DungeonConstants.LAYER_COUNT];
        // Load the preShuffle array
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                for (l = 0; l < DungeonConstants.LAYER_COUNT; l++) {
                    uFix = u - (x - r);
                    vFix = v - (y - r);
                    try {
                        preShuffle[uFix][vFix][l] = this.getCell(u, v, z, l);
                    } catch (final ArrayIndexOutOfBoundsException aioob) {
                        preShuffle[uFix][vFix][l] = null;
                    }
                }
            }
        }
        // Do the shuffle
        final AbstractDungeonObject[][][] postShuffle = LayeredTower
                .shuffleObjects(preShuffle, r, px - (x - r), py - (y - r),
                        needsFix);
        // Load the dungeon with the postShuffle array
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                for (l = 0; l < DungeonConstants.LAYER_COUNT; l++) {
                    uFix = u - (x - r);
                    vFix = v - (y - r);
                    if (postShuffle[uFix][vFix][l] != null) {
                        this.setCell(postShuffle[uFix][vFix][l], u, v, z, l);
                    }
                }
            }
        }
        // Final check
        if (needsFix) {
            this.setPlayerRow(LayeredTower.NEW_PLAYER_ROW + x - r);
            this.setPlayerColumn(LayeredTower.NEW_PLAYER_COL + y - r);
        }
    }

    public void radialScanQuakeBomb(final int x, final int y, final int z,
            final int r) {
        int u, v;
        u = v = 0;
        // Perform the scan, and do the action
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                try {
                    final boolean isEmpty = this
                            .getCell(u, v, z, DungeonConstants.LAYER_OBJECT)
                            .isOfType(TypeConstants.TYPE_EMPTY_SPACE);
                    if (isEmpty) {
                        final RandomRange rr = new RandomRange(1, 5);
                        final int chance = rr.generate();
                        if (chance == 1) {
                            // Grow a crevasse
                            this.setCell(new Crevasse(), u, v, z,
                                    DungeonConstants.LAYER_OBJECT);
                        }
                    }
                    final boolean isBreakable = this
                            .getCell(u, v, z, DungeonConstants.LAYER_OBJECT)
                            .isOfType(TypeConstants.TYPE_BREAKABLE_WALL);
                    if (isBreakable) {
                        // Destroy the wall
                        this.setCell(new Empty(), u, v, z,
                                DungeonConstants.LAYER_OBJECT);
                    }
                    final boolean isWall = this
                            .getCell(u, v, z, DungeonConstants.LAYER_OBJECT)
                            .isOfType(TypeConstants.TYPE_PLAIN_WALL);
                    if (isWall) {
                        // Crack the wall
                        this.setCell(new CrackedWall(), u, v, z,
                                DungeonConstants.LAYER_OBJECT);
                    }
                    final boolean isCharacter = this
                            .getCell(u, v, z, DungeonConstants.LAYER_OBJECT)
                            .isOfType(TypeConstants.TYPE_CHARACTER);
                    if (isCharacter) {
                        final Application app = DungeonDiver4.getApplication();
                        app.getGameManager().keepNextMessage();
                        app.showMessage(
                                "You find yourself caught in the quake, and fall over, hurting yourself a bit.");
                        PartyManager.getParty().getLeader()
                                .doDamagePercentage(2);
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Do nothing
                }
            }
        }
    }

    public void warpObject(final AbstractDungeonObject mo, final int x,
            final int y, final int z, final int l) {
        final RandomRange row = new RandomRange(0, this.getRows() - 1);
        final RandomRange column = new RandomRange(0, this.getColumns() - 1);
        int randomColumn, randomRow;
        randomRow = row.generate();
        randomColumn = column.generate();
        final AbstractDungeonObject currObj = this.getCell(randomRow,
                randomColumn, z, DungeonConstants.LAYER_OBJECT);
        if (!currObj.isSolid()) {
            this.setCell(new Empty(), x, y, z, l);
            this.setCell(mo, randomRow, randomColumn, z,
                    DungeonConstants.LAYER_OBJECT);
        } else {
            while (currObj.isSolid()) {
                randomRow = row.generate();
                randomColumn = column.generate();
                this.getCell(randomRow, randomColumn, z,
                        DungeonConstants.LAYER_OBJECT);
            }
            this.setCell(new Empty(), x, y, z, l);
            this.setCell(mo, randomRow, randomColumn, z,
                    DungeonConstants.LAYER_OBJECT);
        }
    }

    private static AbstractDungeonObject[][][] shuffleObjects(
            final AbstractDungeonObject[][][] preShuffle, final int r,
            final int opx, final int opy, final boolean needsFix) {
        final AbstractDungeonObject[][][] postShuffle = new AbstractDungeonObject[2
                * r + 1][2 * r + 1][DungeonConstants.LAYER_COUNT];
        int[][] randomLocations = new int[(2 * r + 1) * (2 * r + 1)][2];
        // Populate randomLocations array
        int counter = 0;
        for (int x = 0; x < preShuffle.length; x++) {
            for (int y = 0; y < preShuffle[x].length; y++) {
                if (preShuffle[x][y] == null) {
                    randomLocations[counter][0] = -1;
                    randomLocations[counter][1] = -1;
                } else {
                    randomLocations[counter][0] = x;
                    randomLocations[counter][1] = y;
                }
                counter++;
            }
        }
        // Shuffle locations array
        randomLocations = LayeredTower.shuffleArray(randomLocations, opx, opy,
                needsFix);
        // Populate postShuffle array
        counter = 0;
        for (int x = 0; x < preShuffle.length; x++) {
            for (int y = 0; y < preShuffle[x].length; y++) {
                for (int z = 0; z < DungeonConstants.LAYER_COUNT; z++) {
                    if (preShuffle[x][y][z] == null) {
                        postShuffle[x][y][z] = null;
                    } else {
                        postShuffle[x][y][z] = preShuffle[randomLocations[counter][0]][randomLocations[counter][1]][z];
                    }
                }
                counter++;
            }
        }
        return postShuffle;
    }

    private static int[][] shuffleArray(final int[][] src, final int opx,
            final int opy, final boolean needsFix) {
        int temp = 0;
        final int minSwaps = (int) Math.sqrt(src.length);
        final int maxSwaps = src.length - 1;
        int oldLocX, newLocX, oldLocY, newLocY;
        int npx = opx;
        int npy = opy;
        final RandomRange rSwap = new RandomRange(minSwaps, maxSwaps);
        final RandomRange locSwapX = new RandomRange(0, src.length - 1);
        final int swaps = rSwap.generate();
        for (int s = 0; s < swaps; s++) {
            do {
                oldLocX = locSwapX.generate();
                newLocX = locSwapX.generate();
            } while (src[oldLocX][0] == -1 || src[newLocX][0] == -1);
            final RandomRange locSwapY = new RandomRange(0, src[0].length - 1);
            do {
                oldLocY = locSwapY.generate();
                newLocY = locSwapY.generate();
            } while (src[oldLocX][0] == -1 || src[newLocX][0] == -1);
            // Swap
            temp = src[newLocX][newLocY];
            src[newLocX][newLocY] = src[oldLocX][oldLocY];
            src[oldLocX][oldLocY] = temp;
            // Did row change?
            if (needsFix && npx == oldLocX) {
                npx = newLocX;
            }
            // Did column change?
            if (needsFix && npy == oldLocY) {
                npy = newLocY;
            }
        }
        if (needsFix) {
            LayeredTower.NEW_PLAYER_ROW = npx;
            LayeredTower.NEW_PLAYER_ROW = npy;
        }
        return src;
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

    public void setVisionRadiusToMinimum() {
        this.visionRadius = LayeredTower.MIN_VISION_RADIUS;
    }

    public void incrementVisionRadius() {
        if (this.visionRadius < LayeredTower.MAX_VISION_RADIUS) {
            this.visionRadius++;
        }
    }

    public void decrementVisionRadius() {
        if (this.visionRadius > LayeredTower.MIN_VISION_RADIUS) {
            this.visionRadius--;
        }
    }

    public void writeLayeredTower(final XDataWriter writer) throws IOException {
        int y, x, z, e;
        writer.writeOpeningGroup(LayeredTower.TOWER_GROUP);
        writer.writeOpeningGroup(LayeredTower.TOWER_SIZE_GROUP);
        writer.writeInt(this.getColumns());
        writer.writeInt(this.getRows());
        writer.writeInt(this.getFloors());
        writer.writeClosingGroup(LayeredTower.TOWER_SIZE_GROUP);
        writer.writeOpeningGroup(LayeredTower.TOWER_OBJECTS_GROUP);
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < DungeonConstants.LAYER_COUNT; e++) {
                        this.getCell(y, x, z, e).writeDungeonObject(writer);
                    }
                    writer.writeBoolean(this.visionData.getCell(y, x, z));
                    final boolean hasNote = this.noteData.getNote(y, x,
                            z) != null;
                    writer.writeBoolean(hasNote);
                    if (hasNote) {
                        this.noteData.getNote(y, x, z).writeNote(writer);
                    }
                }
            }
        }
        writer.writeClosingGroup(LayeredTower.TOWER_OBJECTS_GROUP);
        writer.writeOpeningGroup(LayeredTower.TOWER_SETTINGS_GROUP);
        for (y = 0; y < 3; y++) {
            writer.writeInt(this.playerStartData[y]);
            writer.writeInt(this.playerLocationData[y]);
            writer.writeInt(this.savedPlayerLocationData[y]);
            writer.writeInt(this.findResult[y]);
        }
        writer.writeBoolean(this.horizontalWraparoundEnabled);
        writer.writeBoolean(this.verticalWraparoundEnabled);
        writer.writeBoolean(this.thirdDimensionWraparoundEnabled);
        writer.writeString(this.levelTitle);
        writer.writeString(this.levelStartMessage);
        writer.writeString(this.levelEndMessage);
        writer.writeInt(this.visionMode);
        writer.writeInt(this.visionModeExploreRadius);
        writer.writeInt(this.visionMode);
        writer.writeInt(this.visionModeExploreRadius);
        writer.writeInt(this.visionRadius);
        writer.writeInt(this.poisonPower);
        writer.writeInt(this.oldPoisonPower);
        writer.writeInt(this.regionSize);
        writer.writeClosingGroup(LayeredTower.TOWER_SETTINGS_GROUP);
        writer.writeClosingGroup(LayeredTower.TOWER_GROUP);
    }

    public static LayeredTower readLayeredTowerV1(final XDataReader reader)
            throws IOException {
        int y, x, z, e, dungeonSizeX, dungeonSizeY, dungeonSizeZ;
        reader.readOpeningGroup(LayeredTower.TOWER_GROUP);
        reader.readOpeningGroup(LayeredTower.TOWER_SIZE_GROUP);
        dungeonSizeX = reader.readInt();
        dungeonSizeY = reader.readInt();
        dungeonSizeZ = reader.readInt();
        reader.readClosingGroup(LayeredTower.TOWER_SIZE_GROUP);
        reader.readOpeningGroup(LayeredTower.TOWER_OBJECTS_GROUP);
        final LayeredTower lt = new LayeredTower(dungeonSizeX, dungeonSizeY,
                dungeonSizeZ);
        for (x = 0; x < lt.getColumns(); x++) {
            for (y = 0; y < lt.getRows(); y++) {
                for (z = 0; z < lt.getFloors(); z++) {
                    for (e = 0; e < DungeonConstants.LAYER_COUNT; e++) {
                        lt.setCell(DungeonDiver4.getApplication().getObjects()
                                .readDungeonObject(reader,
                                        FormatConstants.DUNGEON_FORMAT_1),
                                y, x, z, e);
                        if (lt.getCell(y, x, z, e) == null) {
                            return null;
                        }
                    }
                    lt.visionData.setCell(reader.readBoolean(), y, x, z);
                    final boolean hasNote = reader.readBoolean();
                    if (hasNote) {
                        final DungeonNote mn = DungeonNote.readNote(reader);
                        lt.noteData.setNote(mn, y, x, z);
                    }
                }
            }
        }
        reader.readClosingGroup(LayeredTower.TOWER_OBJECTS_GROUP);
        reader.readOpeningGroup(LayeredTower.TOWER_SETTINGS_GROUP);
        for (y = 0; y < 3; y++) {
            lt.playerStartData[y] = reader.readInt();
            lt.playerLocationData[y] = reader.readInt();
            lt.savedPlayerLocationData[y] = reader.readInt();
            lt.findResult[y] = reader.readInt();
        }
        lt.horizontalWraparoundEnabled = reader.readBoolean();
        lt.verticalWraparoundEnabled = reader.readBoolean();
        lt.thirdDimensionWraparoundEnabled = reader.readBoolean();
        lt.levelTitle = reader.readString();
        lt.levelStartMessage = reader.readString();
        lt.levelEndMessage = reader.readString();
        lt.visionMode = reader.readInt();
        lt.visionModeExploreRadius = reader.readInt();
        lt.visionMode = reader.readInt();
        lt.visionModeExploreRadius = reader.readInt();
        lt.visionRadius = reader.readInt();
        lt.poisonPower = reader.readInt();
        lt.oldPoisonPower = reader.readInt();
        lt.regionSize = reader.readInt();
        reader.readClosingGroup(LayeredTower.TOWER_SETTINGS_GROUP);
        reader.readClosingGroup(LayeredTower.TOWER_GROUP);
        return lt;
    }

    public void writeSavedTowerState(final XDataWriter writer)
            throws IOException {
        int x, y, z, e;
        writer.writeInt(this.getColumns());
        writer.writeInt(this.getRows());
        writer.writeInt(this.getFloors());
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < DungeonConstants.LAYER_COUNT; e++) {
                        this.savedTowerState.getCell(y, x, z, e)
                                .writeDungeonObject(writer);
                    }
                }
            }
        }
    }

    public void readSavedTowerState(final XDataReader reader,
            final int formatVersion) throws IOException {
        int x, y, z, e, sizeX, sizeY, sizeZ;
        sizeX = reader.readInt();
        sizeY = reader.readInt();
        sizeZ = reader.readInt();
        this.savedTowerState = new LowLevelADODataStore(sizeY, sizeX, sizeZ,
                DungeonConstants.LAYER_COUNT);
        for (x = 0; x < sizeY; x++) {
            for (y = 0; y < sizeX; y++) {
                for (z = 0; z < sizeZ; z++) {
                    for (e = 0; e < DungeonConstants.LAYER_COUNT; e++) {
                        this.savedTowerState.setCell(DungeonDiver4
                                .getApplication().getObjects()
                                .readDungeonObject(reader, formatVersion), y, x,
                                z, e);
                    }
                }
            }
        }
    }
}
