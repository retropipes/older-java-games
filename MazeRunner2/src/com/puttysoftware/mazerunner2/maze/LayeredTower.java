/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze;

import java.io.IOException;
import java.util.Arrays;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.llds.LowLevelFlagDataStore;
import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.creatures.party.PartyManager;
import com.puttysoftware.mazerunner2.maze.abc.AbstractLightModifier;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.maze.legacy.LegacyFormatConstants;
import com.puttysoftware.mazerunner2.maze.objects.BarrierGenerator;
import com.puttysoftware.mazerunner2.maze.objects.CrackedWall;
import com.puttysoftware.mazerunner2.maze.objects.Crevasse;
import com.puttysoftware.mazerunner2.maze.objects.DarkGem;
import com.puttysoftware.mazerunner2.maze.objects.Dirt;
import com.puttysoftware.mazerunner2.maze.objects.Empty;
import com.puttysoftware.mazerunner2.maze.objects.EnragedBarrierGenerator;
import com.puttysoftware.mazerunner2.maze.objects.ForceField;
import com.puttysoftware.mazerunner2.maze.objects.HotRock;
import com.puttysoftware.mazerunner2.maze.objects.Ice;
import com.puttysoftware.mazerunner2.maze.objects.IcedBarrierGenerator;
import com.puttysoftware.mazerunner2.maze.objects.LightGem;
import com.puttysoftware.mazerunner2.maze.objects.Monster;
import com.puttysoftware.mazerunner2.maze.objects.MovingBlock;
import com.puttysoftware.mazerunner2.maze.objects.MovingFinish;
import com.puttysoftware.mazerunner2.maze.objects.Player;
import com.puttysoftware.mazerunner2.maze.objects.PoisonedBarrierGenerator;
import com.puttysoftware.mazerunner2.maze.objects.ShockedBarrierGenerator;
import com.puttysoftware.mazerunner2.maze.objects.Slime;
import com.puttysoftware.mazerunner2.maze.utilities.DirectionResolver;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectList;
import com.puttysoftware.mazerunner2.maze.utilities.RandomGenerationRule;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.prefs.PreferencesManager;
import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;
import com.puttysoftware.xio.legacy.XLegacyDataReader;

class LayeredTower implements Cloneable {
    // Properties
    private LowLevelAMODataStore data;
    private LowLevelAMODataStore savedTowerState;
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
    private boolean autoFinishEnabled;
    private int autoFinishThreshold;
    private boolean alternateAutoFinishEnabled;
    private int alternateAutoFinishThreshold;
    private int nextLevel;
    private int nextLevelOffset;
    private boolean useOffset;
    private int alternateNextLevel;
    private int alternateNextLevelOffset;
    private boolean useAlternateOffset;
    private int visionMode;
    private int visionModeExploreRadius;
    private int visionRadius;
    private int initialVisionRadius;
    private int poisonPower;
    private int oldPoisonPower;
    private int finishMoveSpeed;
    private int firstMovingFinishX;
    private int firstMovingFinishY;
    private int firstMovingFinishZ;
    private int regionSize;
    private int timerValue;
    private int initialTimerValue;
    private boolean timerActive;
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
        this.data = new LowLevelAMODataStore(cols, rows, floors,
                MazeConstants.LAYER_COUNT);
        this.savedTowerState = new LowLevelAMODataStore(cols, rows, floors,
                MazeConstants.LAYER_COUNT);
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
        this.autoFinishThreshold = 0;
        this.alternateAutoFinishThreshold = 0;
        this.nextLevel = 0;
        this.nextLevelOffset = 1;
        this.useOffset = true;
        this.visionMode = MazeConstants.VISION_MODE_EXPLORE_AND_LOS;
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
        copy.data = (LowLevelAMODataStore) this.data.clone();
        copy.visionData = (LowLevelFlagDataStore) this.visionData.clone();
        copy.savedTowerState = (LowLevelAMODataStore) this.savedTowerState
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
            final AbstractMazeObject there = this.getCell(xLoc + dirMove[0],
                    yLoc + dirMove[1], zLoc, MazeConstants.LAYER_OBJECT);
            final AbstractMazeObject ground = this.getCell(xLoc + dirMove[0],
                    yLoc + dirMove[1], zLoc, MazeConstants.LAYER_GROUND);
            if (!there.isSolid() && xLoc + dirMove[0] != xLocP
                    && yLoc + dirMove[1] != yLocP) {
                this.setCell(block.getSavedObject(), xLoc, yLoc, zLoc,
                        MazeConstants.LAYER_OBJECT);
                // Move the block
                block.setSavedObject(there);
                this.setCell(block, xLoc + dirMove[0], yLoc + dirMove[1], zLoc,
                        MazeConstants.LAYER_OBJECT);
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
            final int yLoc, final Monster monster) {
        final int[] dirMove = DirectionResolver
                .unresolveRelativeDirection(move);
        final int pLocX = this.getPlayerRow();
        final int pLocY = this.getPlayerColumn();
        final int zLoc = this.getPlayerFloor();
        try {
            final AbstractMazeObject there = this.getCell(xLoc + dirMove[0],
                    yLoc + dirMove[1], zLoc, MazeConstants.LAYER_OBJECT);
            final AbstractMazeObject ground = this.getCell(xLoc + dirMove[0],
                    yLoc + dirMove[1], zLoc, MazeConstants.LAYER_GROUND);
            if (!there.isSolid() && !there.getName().equals("Monster")) {
                if (this.radialScan(xLoc, yLoc, 0, pLocX, pLocY)) {
                    if (MazeRunnerII.getApplication()
                            .getMode() != Application.STATUS_BATTLE) {
                        MazeRunnerII.getApplication().getBattle().doBattle();
                        this.postBattle(monster, xLoc, yLoc, false);
                    }
                } else {
                    // Move the monster
                    this.setCell(monster.getSavedObject(), xLoc, yLoc, zLoc,
                            MazeConstants.LAYER_OBJECT);
                    monster.setSavedObject(there);
                    this.setCell(monster, xLoc + dirMove[0], yLoc + dirMove[1],
                            zLoc, MazeConstants.LAYER_OBJECT);
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

    public void postBattle(final Monster m, final int xLoc, final int yLoc,
            final boolean player) {
        final AbstractMazeObject saved = m.getSavedObject();
        final int zLoc = this.getPlayerFloor();
        if (player) {
            MazeRunnerII.getApplication().getGameManager()
                    .setSavedMazeObject(saved);
        } else {
            this.setCell(saved, xLoc, yLoc, zLoc, MazeConstants.LAYER_OBJECT);
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
        AbstractMazeObject currObj = this.getCell(randomRow, randomColumn, zLoc,
                MazeConstants.LAYER_OBJECT);
        if (!currObj.isSolid()) {
            final Monster m = new Monster();
            m.setSavedObject(currObj);
            this.setCell(m, randomRow, randomColumn, zLoc,
                    MazeConstants.LAYER_OBJECT);
        } else {
            while (currObj.isSolid()) {
                randomRow = row.generate();
                randomColumn = column.generate();
                currObj = this.getCell(randomRow, randomColumn, zLoc,
                        MazeConstants.LAYER_OBJECT);
            }
            final Monster m = new Monster();
            m.setSavedObject(currObj);
            this.setCell(m, randomRow, randomColumn, zLoc,
                    MazeConstants.LAYER_OBJECT);
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
                        final Monster m = (Monster) this.getCell(u, v, z, l);
                        this.setCell(m.getSavedObject(), u, v, z, l);
                        // Reward player for monster death
                        MazeRunnerII.getApplication().getBattle()
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

    public int getFinishMoveSpeed() {
        return this.finishMoveSpeed;
    }

    public void setFinishMoveSpeed(final int value) {
        this.finishMoveSpeed = value;
    }

    private int getFirstMovingFinishX() {
        return this.firstMovingFinishX;
    }

    public void setFirstMovingFinishX(final int value) {
        this.firstMovingFinishX = value;
    }

    private int getFirstMovingFinishY() {
        return this.firstMovingFinishY;
    }

    public void setFirstMovingFinishY(final int value) {
        this.firstMovingFinishY = value;
    }

    private int getFirstMovingFinishZ() {
        return this.firstMovingFinishZ;
    }

    public void setFirstMovingFinishZ(final int value) {
        this.firstMovingFinishZ = value;
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

    public boolean getAutoFinishEnabled() {
        return this.autoFinishEnabled;
    }

    public void setAutoFinishEnabled(final boolean afte) {
        this.autoFinishEnabled = afte;
    }

    public int getAutoFinishThreshold() {
        return this.autoFinishThreshold;
    }

    public void setAutoFinishThreshold(final int aft) {
        this.autoFinishThreshold = aft;
    }

    public boolean getAlternateAutoFinishEnabled() {
        return this.alternateAutoFinishEnabled;
    }

    public void setAlternateAutoFinishEnabled(final boolean aafte) {
        this.alternateAutoFinishEnabled = aafte;
    }

    public int getAlternateAutoFinishThreshold() {
        return this.alternateAutoFinishThreshold;
    }

    public void setAlternateAutoFinishThreshold(final int aaft) {
        this.alternateAutoFinishThreshold = aaft;
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

    public boolean hasNote(final int x, final int y, final int z) {
        return this.noteData.getNote(y, x, z) != null;
    }

    public void createNote(final int x, final int y, final int z) {
        this.noteData.setNote(new MazeNote(), y, x, z);
    }

    public MazeNote getNote(final int x, final int y, final int z) {
        return this.noteData.getNote(y, x, z);
    }

    public AbstractMazeObject getCell(final int row, final int col,
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

    public void findAllObjectPairsAndSwap(final AbstractMazeObject o1,
            final AbstractMazeObject o2) {
        int y, x, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final AbstractMazeObject mo = this.getCell(y, x, z,
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
        final LowLevelAMODataStore tempStorage = new LowLevelAMODataStore(y, x,
                z, MazeConstants.LAYER_COUNT);
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
        this.savedTowerState = new LowLevelAMODataStore(y, x, z,
                MazeConstants.LAYER_COUNT);
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
                | MazeConstants.VISION_MODE_EXPLORE) == this.visionMode) {
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
                                | MazeConstants.VISION_MODE_LOS) == this.visionMode) {
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
            return this.isSquareVisibleRadius(x1, y1, x2, y2);
        } else {
            boolean result = false;
            if ((this.visionMode
                    | MazeConstants.VISION_MODE_EXPLORE) == this.visionMode) {
                result = result || this.isSquareVisibleExplore(x2, y2);
                if (result && (this.visionMode
                        | MazeConstants.VISION_MODE_LOS) == this.visionMode) {
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
            if (this.radialScanVision(x2, y2, zLoc, MazeConstants.LAYER_OBJECT,
                    AbstractLightModifier.getEffectRadius(), dg.getName())) {
                result = false;
            } else {
                result = true;
            }
        } else {
            if (this.radialScanVision(x2, y2, zLoc, MazeConstants.LAYER_OBJECT,
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
                final AbstractMazeObject obj = this.getCell(fx1, fy1, zLoc,
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

    public void setCell(final AbstractMazeObject mo, final int row,
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

    public void updateThresholds() {
        int y, x, z;
        this.autoFinishThreshold = 0;
        this.alternateAutoFinishThreshold = 0;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final AbstractMazeObject obj = this.getCell(y, x, z,
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

    public void fill(final AbstractMazeObject bottom,
            final AbstractMazeObject top) {
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

    public void fillFloor(final AbstractMazeObject bottom,
            final AbstractMazeObject top, final int z) {
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
        final MazeObjectList objects = MazeRunnerII.getApplication()
                .getObjects();
        final AbstractMazeObject pass1FillBottom = PreferencesManager
                .getEditorDefaultFill();
        final AbstractMazeObject pass1FillTop = new Empty();
        RandomRange r = null;
        int x, y, e;
        // Pass 1
        this.fillFloor(pass1FillBottom, pass1FillTop, z);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
            final AbstractMazeObject[] objectsWithoutPrerequisites = objects
                    .getAllWithoutPrerequisiteAndNotRequired(e);
            if (objectsWithoutPrerequisites != null) {
                r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
                for (x = 0; x < columns; x++) {
                    for (y = 0; y < rows; y++) {
                        final AbstractMazeObject placeObj = objectsWithoutPrerequisites[r
                                .generate()];
                        final boolean okay = placeObj.shouldGenerateObject(maze,
                                x, y, z, w, e);
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
        for (int layer = 0; layer < MazeConstants.LAYER_COUNT; layer++) {
            final AbstractMazeObject[] requiredObjects = objects
                    .getAllRequired(layer);
            if (requiredObjects != null) {
                final RandomRange row = new RandomRange(0, this.getRows() - 1);
                final RandomRange column = new RandomRange(0,
                        this.getColumns() - 1);
                int randomColumn, randomRow;
                for (x = 0; x < requiredObjects.length; x++) {
                    randomRow = row.generate();
                    randomColumn = column.generate();
                    final AbstractMazeObject currObj = requiredObjects[x];
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
                                    objects.getNewInstanceByName(
                                            currObj.getName()),
                                    randomColumn, randomRow, z, layer);
                            currObj.editorGenerateHook(y, x, z);
                        } else {
                            while (!currObj.shouldGenerateObject(maze,
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

    public void fillFloorRandomlyCustom(final Maze maze, final int z,
            final int w) {
        // Pre-Pass
        final MazeObjectList objects = MazeRunnerII.getApplication()
                .getObjects();
        final AbstractMazeObject pass1FillBottom = PreferencesManager
                .getEditorDefaultFill();
        final AbstractMazeObject pass1FillTop = new Empty();
        final AbstractMazeObject[] withoutRuleSets = objects
                .getAllObjectsWithoutRuleSets();
        final AbstractMazeObject[] withRuleSets = objects
                .getAllObjectsWithRuleSets();
        RandomRange r = null;
        int x, y, e;
        // Pass 1
        this.fillFloor(pass1FillBottom, pass1FillTop, z);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        if (withoutRuleSets != null) {
            for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                final AbstractMazeObject[] objectsWithoutPrerequisites = MazeObjectList
                        .getAllWithoutPrerequisiteAndNotRequiredSubset(
                                withoutRuleSets, e);
                if (objectsWithoutPrerequisites != null
                        && objectsWithoutPrerequisites.length > 0) {
                    r = new RandomRange(0,
                            objectsWithoutPrerequisites.length - 1);
                    for (x = 0; x < columns; x++) {
                        for (y = 0; y < rows; y++) {
                            final AbstractMazeObject placeObj = objectsWithoutPrerequisites[r
                                    .generate()];
                            final boolean okay = placeObj
                                    .shouldGenerateObject(maze, y, x, z, w, e);
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
            for (int layer = 0; layer < MazeConstants.LAYER_COUNT; layer++) {
                final AbstractMazeObject[] requiredObjects = MazeObjectList
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
                        final AbstractMazeObject currObj = requiredObjects[x];
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
                                        objects.getNewInstanceByName(
                                                currObj.getName()),
                                        randomColumn, randomRow, z, layer);
                                currObj.editorGenerateHook(y, x, z);
                            } else {
                                while (!currObj.shouldGenerateObject(maze,
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
            for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                final AbstractMazeObject[] objectsWithoutPrerequisites = MazeObjectList
                        .getAllWithoutPrerequisiteAndNotRequiredSubset(
                                withRuleSets, e);
                if (objectsWithoutPrerequisites != null) {
                    r = new RandomRange(0,
                            objectsWithoutPrerequisites.length - 1);
                    for (x = 0; x < columns; x++) {
                        for (y = 0; y < rows; y++) {
                            final AbstractMazeObject placeObj = objectsWithoutPrerequisites[r
                                    .generate()];
                            final boolean okay = placeObj.getRuleSet()
                                    .shouldGenerateObject(maze, y, x, z, w, e);
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
            for (int layer = 0; layer < MazeConstants.LAYER_COUNT; layer++) {
                final AbstractMazeObject[] requiredObjects = MazeObjectList
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
                        final AbstractMazeObject currObj = requiredObjects[x];
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
                                        objects.getNewInstanceByName(
                                                currObj.getName()),
                                        randomColumn, randomRow, z, layer);
                                currObj.editorGenerateHook(y, x, z);
                            } else {
                                while (!currObj.getRuleSet()
                                        .shouldGenerateObject(maze, randomRow,
                                                randomColumn, z, w, layer)) {
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

    public void fillLayer(final AbstractMazeObject fillWith, final int e) {
        int y, x, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    this.setCell(fillWith, y, x, z, e);
                }
            }
        }
    }

    public void fillFloorAndLayer(final AbstractMazeObject fillWith,
            final int z, final int e) {
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
        final MazeObjectList objects = MazeRunnerII.getApplication()
                .getObjects();
        final AbstractMazeObject pass1Fill = PreferencesManager
                .getEditorDefaultFill(layer);
        RandomRange r = null;
        int x, y;
        // Pass 1
        this.fillFloorAndLayer(pass1Fill, z, layer);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        final AbstractMazeObject[] objectsWithoutPrerequisites = objects
                .getAllWithoutPrerequisiteAndNotRequired(layer);
        if (objectsWithoutPrerequisites != null) {
            r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
            for (x = 0; x < columns; x++) {
                for (y = 0; y < rows; y++) {
                    final AbstractMazeObject placeObj = objectsWithoutPrerequisites[r
                            .generate()];
                    final boolean okay = placeObj.shouldGenerateObject(maze, x,
                            y, z, w, layer);
                    if (okay) {
                        this.setCell(objects.getNewInstanceByName(
                                placeObj.getName()), y, x, z, layer);
                        placeObj.editorGenerateHook(y, x, z);
                    }
                }
            }
        }
        // Pass 3
        final AbstractMazeObject[] requiredObjects = objects
                .getAllRequired(layer);
        if (requiredObjects != null) {
            final RandomRange row = new RandomRange(0, this.getRows() - 1);
            final RandomRange column = new RandomRange(0,
                    this.getColumns() - 1);
            int randomColumn, randomRow;
            for (x = 0; x < requiredObjects.length; x++) {
                randomRow = row.generate();
                randomColumn = column.generate();
                final AbstractMazeObject currObj = requiredObjects[x];
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
                        while (!currObj.shouldGenerateObject(maze, randomColumn,
                                randomRow, z, w, layer)) {
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
        final MazeObjectList objects = MazeRunnerII.getApplication()
                .getObjects();
        final AbstractMazeObject pass1Fill = PreferencesManager
                .getEditorDefaultFill(layer);
        final AbstractMazeObject[] withoutRuleSets = objects
                .getAllObjectsWithoutRuleSets();
        final AbstractMazeObject[] withRuleSets = objects
                .getAllObjectsWithRuleSets();
        RandomRange r = null;
        int x, y;
        // Pass 1
        this.fillFloorAndLayer(pass1Fill, z, layer);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        if (withoutRuleSets != null) {
            final AbstractMazeObject[] objectsWithoutPrerequisites = MazeObjectList
                    .getAllWithoutPrerequisiteAndNotRequiredSubset(
                            withoutRuleSets, layer);
            if (objectsWithoutPrerequisites != null
                    && objectsWithoutPrerequisites.length > 0) {
                r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
                for (x = 0; x < columns; x++) {
                    for (y = 0; y < rows; y++) {
                        final AbstractMazeObject placeObj = objectsWithoutPrerequisites[r
                                .generate()];
                        final boolean okay = placeObj.shouldGenerateObject(maze,
                                y, x, z, w, layer);
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
            final AbstractMazeObject[] requiredObjects = MazeObjectList
                    .getAllRequiredSubset(withoutRuleSets, layer);
            if (requiredObjects != null) {
                final RandomRange row = new RandomRange(0, this.getRows() - 1);
                final RandomRange column = new RandomRange(0,
                        this.getColumns() - 1);
                int randomColumn, randomRow;
                for (x = 0; x < requiredObjects.length; x++) {
                    randomRow = row.generate();
                    randomColumn = column.generate();
                    final AbstractMazeObject currObj = requiredObjects[x];
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
                            this.setCell(
                                    objects.getNewInstanceByName(
                                            currObj.getName()),
                                    randomColumn, randomRow, z, layer);
                            currObj.editorGenerateHook(y, x, z);
                        } else {
                            while (!currObj.shouldGenerateObject(maze,
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
            final AbstractMazeObject[] objectsWithoutPrerequisites = MazeObjectList
                    .getAllWithoutPrerequisiteAndNotRequiredSubset(withRuleSets,
                            layer);
            if (objectsWithoutPrerequisites != null) {
                r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
                for (x = 0; x < columns; x++) {
                    for (y = 0; y < rows; y++) {
                        final AbstractMazeObject placeObj = objectsWithoutPrerequisites[r
                                .generate()];
                        final boolean okay = placeObj.getRuleSet()
                                .shouldGenerateObject(maze, y, x, z, w, layer);
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
            final AbstractMazeObject[] requiredObjects = MazeObjectList
                    .getAllRequiredSubset(withRuleSets, layer);
            if (requiredObjects != null) {
                final RandomRange row = new RandomRange(0, this.getRows() - 1);
                final RandomRange column = new RandomRange(0,
                        this.getColumns() - 1);
                int randomColumn, randomRow;
                for (x = 0; x < requiredObjects.length; x++) {
                    randomRow = row.generate();
                    randomColumn = column.generate();
                    final AbstractMazeObject currObj = requiredObjects[x];
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
                                    objects.getNewInstanceByName(
                                            currObj.getName()),
                                    randomColumn, randomRow, z, layer);
                            currObj.editorGenerateHook(y, x, z);
                        } else {
                            while (!currObj.getRuleSet().shouldGenerateObject(
                                    maze, randomRow, randomColumn, z, w,
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

    public void fillRandomlyInBattle(final Maze map, final int w,
            final AbstractMazeObject pass1FillBottom,
            final AbstractMazeObject pass1FillTop) {
        for (int z = 0; z < this.getFloors(); z++) {
            this.fillFloorRandomlyInBattle(map, z, w, pass1FillBottom,
                    pass1FillTop);
        }
    }

    private void fillFloorRandomlyInBattle(final Maze map, final int z,
            final int w, final AbstractMazeObject pass1FillBottom,
            final AbstractMazeObject pass1FillTop) {
        // Pre-Pass
        final MazeObjectList objects = new MazeObjectList();
        RandomRange r = null;
        int x, y, e, u, v;
        // Pass 1
        this.fillFloor(pass1FillBottom, pass1FillTop, z);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
            final AbstractMazeObject[] objectsWithoutPrerequisites = objects
                    .getAllNotRequiredInBattle(e);
            if (objectsWithoutPrerequisites != null) {
                r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
                for (x = 0; x < columns; x++) {
                    for (y = 0; y < rows; y++) {
                        if (e == MazeConstants.LAYER_GROUND) {
                            for (x = 0; x < columns; x += this.regionSize) {
                                for (y = 0; y < rows; y += this.regionSize) {
                                    final AbstractMazeObject placeObj = objectsWithoutPrerequisites[r
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
                                    final AbstractMazeObject placeObj = objectsWithoutPrerequisites[r
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
        for (int layer = 0; layer < MazeConstants.LAYER_COUNT; layer++) {
            final AbstractMazeObject[] requiredObjects = objects
                    .getAllRequiredInBattle(layer);
            if (requiredObjects != null) {
                final RandomRange row = new RandomRange(0, this.getRows() - 1);
                final RandomRange column = new RandomRange(0,
                        this.getColumns() - 1);
                int randomColumn, randomRow;
                for (x = 0; x < requiredObjects.length; x++) {
                    randomRow = row.generate();
                    randomColumn = column.generate();
                    final AbstractMazeObject currObj = requiredObjects[x];
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
        final AbstractMazeObject bottom = PreferencesManager
                .getEditorDefaultFill();
        final AbstractMazeObject top = new Empty();
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
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                        this.setCell(this.savedTowerState.getCell(x, y, z, e),
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

    public void findAllMatchingObjectsAndDecay(final AbstractMazeObject o) {
        int y, x, z;
        final AbstractMazeObject decayTo = new Empty();
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final AbstractMazeObject mo = this.getCell(y, x, z,
                            MazeConstants.LAYER_OBJECT);
                    if (mo != null) {
                        if (mo.getName().equals(o.getName())) {
                            this.setCell(decayTo, y, x, z,
                                    MazeConstants.LAYER_OBJECT);
                        }
                    }
                }
            }
        }
    }

    public void masterTrapTrigger() {
        int y, x, z;
        final AbstractMazeObject decayTo = new Empty();
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final AbstractMazeObject mo = this.getCell(y, x, z,
                            MazeConstants.LAYER_OBJECT);
                    if (mo != null) {
                        if (mo.isOfType(TypeConstants.TYPE_WALL_TRAP) || mo
                                .isOfType(TypeConstants.TYPE_TRAPPED_WALL)) {
                            this.setCell(decayTo, y, x, z,
                                    MazeConstants.LAYER_OBJECT);
                        }
                    }
                }
            }
        }
    }

    public void tickTimers(final int floor) {
        int x, y;
        // Tick all MazeObject timers
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                final AbstractMazeObject mo = this.getCell(y, x, floor,
                        MazeConstants.LAYER_OBJECT);
                if (mo != null) {
                    mo.tickTimer(y, x);
                }
            }
        }
        // Tick tower timer
        if (this.timerActive) {
            this.timerValue--;
            if (this.timerValue == 0) {
                // Time's up
                this.timerActive = false;
                CommonDialogs.showDialog("Time's Up!");
                MazeRunnerII.getApplication().getGameManager().solvedLevel();
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
        final AbstractMazeObject[][][] tempStorage = new AbstractMazeObject[2
                * r + 1][2 * r + 1][MazeConstants.LAYER_COUNT];
        try {
            for (u = x - r; u <= x + r; u++) {
                for (v = y - r; v <= y + r; v++) {
                    for (l = 0; l < MazeConstants.LAYER_COUNT; l++) {
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
                    for (l = 0; l < MazeConstants.LAYER_COUNT; l++) {
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
        final AbstractMazeObject[][][] tempStorage = new AbstractMazeObject[2
                * r + 1][2 * r + 1][MazeConstants.LAYER_COUNT];
        try {
            for (u = x - r; u <= x + r; u++) {
                for (v = y - r; v <= y + r; v++) {
                    for (l = 0; l < MazeConstants.LAYER_COUNT; l++) {
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
                    for (l = 0; l < MazeConstants.LAYER_COUNT; l++) {
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
                            .getCell(u, v, z, MazeConstants.LAYER_OBJECT)
                            .isOfType(TypeConstants.TYPE_REACTS_TO_ICE);
                    if (reactsToIce) {
                        final AbstractMazeObject there = this.getCell(u, v, z,
                                MazeConstants.LAYER_OBJECT);
                        if (there.getClass() == BarrierGenerator.class) {
                            // Freeze the generator
                            this.setCell(new IcedBarrierGenerator(), u, v, z,
                                    MazeConstants.LAYER_OBJECT);
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
                            MazeConstants.LAYER_GROUND);
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
                            .getCell(u, v, z, MazeConstants.LAYER_OBJECT)
                            .isOfType(TypeConstants.TYPE_REACTS_TO_FIRE);
                    if (reactsToFire) {
                        final AbstractMazeObject there = this.getCell(u, v, z,
                                MazeConstants.LAYER_OBJECT);
                        if (there.getClass() == BarrierGenerator.class) {
                            // Enrage the generator
                            this.setCell(new EnragedBarrierGenerator(), u, v, z,
                                    MazeConstants.LAYER_OBJECT);
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
                            MazeConstants.LAYER_GROUND);
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
                            .getCell(u, v, z, MazeConstants.LAYER_OBJECT)
                            .isOfType(TypeConstants.TYPE_REACTS_TO_POISON);
                    if (reactsToPoison) {
                        final AbstractMazeObject there = this.getCell(u, v, z,
                                MazeConstants.LAYER_OBJECT);
                        if (there.getClass() == BarrierGenerator.class) {
                            // Weaken the generator
                            this.setCell(new PoisonedBarrierGenerator(), u, v,
                                    z, MazeConstants.LAYER_OBJECT);
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
                            MazeConstants.LAYER_GROUND);
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
                            .getCell(u, v, z, MazeConstants.LAYER_OBJECT)
                            .isOfType(TypeConstants.TYPE_REACTS_TO_SHOCK);
                    if (reactsToShock) {
                        final AbstractMazeObject there = this.getCell(u, v, z,
                                MazeConstants.LAYER_OBJECT);
                        if (there.getClass() == BarrierGenerator.class) {
                            // Shock the generator
                            this.setCell(new ShockedBarrierGenerator(), u, v, z,
                                    MazeConstants.LAYER_OBJECT);
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
                            MazeConstants.LAYER_GROUND);
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
        final AbstractMazeObject[][][] preShuffle = new AbstractMazeObject[2 * r
                + 1][2 * r + 1][MazeConstants.LAYER_COUNT];
        // Load the preShuffle array
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                for (l = 0; l < MazeConstants.LAYER_COUNT; l++) {
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
        final AbstractMazeObject[][][] postShuffle = LayeredTower
                .shuffleObjects(preShuffle, r, px - (x - r), py - (y - r),
                        needsFix);
        // Load the maze with the postShuffle array
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                for (l = 0; l < MazeConstants.LAYER_COUNT; l++) {
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
                            .getCell(u, v, z, MazeConstants.LAYER_OBJECT)
                            .isOfType(TypeConstants.TYPE_EMPTY_SPACE);
                    if (isEmpty) {
                        final RandomRange rr = new RandomRange(1, 5);
                        final int chance = rr.generate();
                        if (chance == 1) {
                            // Grow a crevasse
                            this.setCell(new Crevasse(), u, v, z,
                                    MazeConstants.LAYER_OBJECT);
                        }
                    }
                    final boolean isBreakable = this
                            .getCell(u, v, z, MazeConstants.LAYER_OBJECT)
                            .isOfType(TypeConstants.TYPE_BREAKABLE_WALL);
                    if (isBreakable) {
                        // Destroy the wall
                        this.setCell(new Empty(), u, v, z,
                                MazeConstants.LAYER_OBJECT);
                    }
                    final boolean isWall = this
                            .getCell(u, v, z, MazeConstants.LAYER_OBJECT)
                            .isOfType(TypeConstants.TYPE_PLAIN_WALL);
                    if (isWall) {
                        // Crack the wall
                        this.setCell(new CrackedWall(), u, v, z,
                                MazeConstants.LAYER_OBJECT);
                    }
                    final boolean isCharacter = this
                            .getCell(u, v, z, MazeConstants.LAYER_OBJECT)
                            .isOfType(TypeConstants.TYPE_CHARACTER);
                    if (isCharacter) {
                        final Application app = MazeRunnerII.getApplication();
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

    public void warpObject(final AbstractMazeObject mo, final int x,
            final int y, final int z, final int l) {
        final RandomRange row = new RandomRange(0, this.getRows() - 1);
        final RandomRange column = new RandomRange(0, this.getColumns() - 1);
        int randomColumn, randomRow;
        randomRow = row.generate();
        randomColumn = column.generate();
        final AbstractMazeObject currObj = this.getCell(randomRow, randomColumn,
                z, MazeConstants.LAYER_OBJECT);
        if (!currObj.isSolid()) {
            this.setCell(new Empty(), x, y, z, l);
            this.setCell(mo, randomRow, randomColumn, z,
                    MazeConstants.LAYER_OBJECT);
        } else {
            while (currObj.isSolid()) {
                randomRow = row.generate();
                randomColumn = column.generate();
                this.getCell(randomRow, randomColumn, z,
                        MazeConstants.LAYER_OBJECT);
            }
            this.setCell(new Empty(), x, y, z, l);
            this.setCell(mo, randomRow, randomColumn, z,
                    MazeConstants.LAYER_OBJECT);
        }
    }

    public final String getTimeString() {
        if (this.isTimerActive()) {
            return "Time Limit: " + this.timerValue + " steps";
        } else {
            return "Time Limit: None";
        }
    }

    public final boolean isTimerActive() {
        return this.timerActive;
    }

    public final void activateTimer(final int ticks) {
        this.timerActive = true;
        this.timerValue = ticks;
        this.initialTimerValue = ticks;
    }

    public final void deactivateTimer() {
        this.timerActive = false;
        this.timerValue = 0;
        this.initialTimerValue = 0;
    }

    public final int getTimerValue() {
        if (this.timerActive) {
            return this.timerValue;
        } else {
            return -1;
        }
    }

    public final void resetTimer() {
        this.timerValue = this.initialTimerValue;
        if (this.timerValue != 0) {
            this.timerActive = true;
        }
    }

    public final void extendTimerByInitialValue() {
        if (this.timerActive) {
            this.timerValue += this.initialTimerValue;
        }
    }

    public final void extendTimerByInitialValueTripled() {
        if (this.timerActive) {
            this.timerValue += this.initialTimerValue * 3;
        }
    }

    public final void extendTimerByInitialValueDoubled() {
        if (this.timerActive) {
            this.timerValue += this.initialTimerValue * 2;
        }
    }

    private static AbstractMazeObject[][][] shuffleObjects(
            final AbstractMazeObject[][][] preShuffle, final int r,
            final int opx, final int opy, final boolean needsFix) {
        final AbstractMazeObject[][][] postShuffle = new AbstractMazeObject[2
                * r + 1][2 * r + 1][MazeConstants.LAYER_COUNT];
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
                for (int z = 0; z < MazeConstants.LAYER_COUNT; z++) {
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

    public void resetVisionRadius() {
        this.visionRadius = this.initialVisionRadius;
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
        this.initialVisionRadius = fVR;
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

    public void deactivateAllMovingFinishes() {
        int y, x, z, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                        final AbstractMazeObject obj = this.getCell(y, x, z, e);
                        if (obj instanceof MovingFinish) {
                            final MovingFinish mf = (MovingFinish) obj;
                            mf.deactivate();
                        }
                    }
                }
            }
        }
    }

    public void activateFirstMovingFinish() {
        final AbstractMazeObject obj = this.getCell(
                this.getFirstMovingFinishX(), this.getFirstMovingFinishY(),
                this.getFirstMovingFinishZ(), MazeConstants.LAYER_OBJECT);
        if (obj instanceof MovingFinish) {
            final MovingFinish mf = (MovingFinish) obj;
            mf.activate();
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
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                        this.getCell(y, x, z, e).writeMazeObject(writer);
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
        writer.writeBoolean(this.autoFinishEnabled);
        writer.writeInt(this.autoFinishThreshold);
        writer.writeBoolean(this.useOffset);
        writer.writeInt(this.nextLevel);
        writer.writeInt(this.nextLevelOffset);
        writer.writeInt(this.visionMode);
        writer.writeInt(this.visionModeExploreRadius);
        writer.writeBoolean(this.alternateAutoFinishEnabled);
        writer.writeInt(this.alternateAutoFinishThreshold);
        writer.writeBoolean(this.useAlternateOffset);
        writer.writeInt(this.alternateNextLevel);
        writer.writeInt(this.alternateNextLevelOffset);
        writer.writeInt(this.visionMode);
        writer.writeInt(this.visionModeExploreRadius);
        writer.writeInt(this.visionRadius);
        writer.writeInt(this.initialVisionRadius);
        writer.writeInt(this.poisonPower);
        writer.writeInt(this.oldPoisonPower);
        writer.writeInt(this.finishMoveSpeed);
        writer.writeInt(this.firstMovingFinishX);
        writer.writeInt(this.firstMovingFinishY);
        writer.writeInt(this.firstMovingFinishZ);
        writer.writeInt(this.regionSize);
        writer.writeInt(this.timerValue);
        writer.writeInt(this.initialTimerValue);
        writer.writeBoolean(this.timerActive);
        writer.writeClosingGroup(LayeredTower.TOWER_SETTINGS_GROUP);
        writer.writeClosingGroup(LayeredTower.TOWER_GROUP);
    }

    public static LayeredTower readLegacyLayeredTowerV1(
            final XLegacyDataReader reader) throws IOException {
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
                        AbstractMazeObject obj = MazeRunnerII.getApplication()
                                .getObjects().readLegacyMazeObject(reader,
                                        LegacyFormatConstants.LEGACY_MAZE_FORMAT_1);
                        if (obj == null) {
                            return null;
                        }
                        final boolean hasNote = reader.readBoolean();
                        if (hasNote) {
                            final MazeNote mn = MazeNote.readLegacyNote(reader);
                            lt.noteData.setNote(mn, y, x, z);
                        }
                        // Special handling for Player
                        if (obj.equals(new Player())) {
                            // Set start and player locations
                            lt.setStartColumn(x);
                            lt.setStartRow(y);
                            lt.setStartFloor(z);
                            lt.setPlayerColumn(x);
                            lt.setPlayerRow(y);
                            lt.setPlayerFloor(z);
                            // Convert to empty
                            obj = new Empty();
                        }
                        lt.setCell(obj, y, x, z, e);
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
        lt.autoFinishEnabled = reader.readBoolean();
        lt.autoFinishThreshold = reader.readInt();
        lt.useOffset = reader.readBoolean();
        lt.nextLevel = reader.readInt();
        lt.nextLevelOffset = reader.readInt();
        lt.visionMode = reader.readInt();
        lt.visionModeExploreRadius = reader.readInt();
        lt.alternateAutoFinishEnabled = reader.readBoolean();
        lt.alternateAutoFinishThreshold = reader.readInt();
        lt.useAlternateOffset = reader.readBoolean();
        lt.alternateNextLevel = reader.readInt();
        lt.alternateNextLevelOffset = reader.readInt();
        return lt;
    }

    public static LayeredTower readLegacyLayeredTowerV2(
            final XLegacyDataReader reader) throws IOException {
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
                        lt.setCell(MazeRunnerII.getApplication().getObjects()
                                .readLegacyMazeObject(reader,
                                        LegacyFormatConstants.LEGACY_MAZE_FORMAT_2),
                                y, x, z, e);
                        if (lt.getCell(y, x, z, e) == null) {
                            return null;
                        }
                        final boolean hasNote = reader.readBoolean();
                        if (hasNote) {
                            final MazeNote mn = MazeNote.readLegacyNote(reader);
                            lt.noteData.setNote(mn, y, x, z);
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
        lt.autoFinishEnabled = reader.readBoolean();
        lt.autoFinishThreshold = reader.readInt();
        lt.useOffset = reader.readBoolean();
        lt.nextLevel = reader.readInt();
        lt.nextLevelOffset = reader.readInt();
        lt.visionMode = reader.readInt();
        lt.visionModeExploreRadius = reader.readInt();
        lt.alternateAutoFinishEnabled = reader.readBoolean();
        lt.alternateAutoFinishThreshold = reader.readInt();
        lt.useAlternateOffset = reader.readBoolean();
        lt.alternateNextLevel = reader.readInt();
        lt.alternateNextLevelOffset = reader.readInt();
        return lt;
    }

    public static LayeredTower readLayeredTowerV1(final XDataReader reader)
            throws IOException {
        int y, x, z, e, mazeSizeX, mazeSizeY, mazeSizeZ;
        reader.readOpeningGroup(LayeredTower.TOWER_GROUP);
        reader.readOpeningGroup(LayeredTower.TOWER_SIZE_GROUP);
        mazeSizeX = reader.readInt();
        mazeSizeY = reader.readInt();
        mazeSizeZ = reader.readInt();
        reader.readClosingGroup(LayeredTower.TOWER_SIZE_GROUP);
        reader.readOpeningGroup(LayeredTower.TOWER_OBJECTS_GROUP);
        final LayeredTower lt = new LayeredTower(mazeSizeX, mazeSizeY,
                mazeSizeZ);
        for (x = 0; x < lt.getColumns(); x++) {
            for (y = 0; y < lt.getRows(); y++) {
                for (z = 0; z < lt.getFloors(); z++) {
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                        lt.setCell(
                                MazeRunnerII.getApplication().getObjects()
                                        .readMazeObject(reader,
                                                FormatConstants.MAZE_FORMAT_1),
                                y, x, z, e);
                        if (lt.getCell(y, x, z, e) == null) {
                            return null;
                        }
                    }
                    lt.visionData.setCell(reader.readBoolean(), y, x, z);
                    final boolean hasNote = reader.readBoolean();
                    if (hasNote) {
                        final MazeNote mn = MazeNote.readNote(reader);
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
        lt.autoFinishEnabled = reader.readBoolean();
        lt.autoFinishThreshold = reader.readInt();
        lt.useOffset = reader.readBoolean();
        lt.nextLevel = reader.readInt();
        lt.nextLevelOffset = reader.readInt();
        lt.visionMode = reader.readInt();
        lt.visionModeExploreRadius = reader.readInt();
        lt.alternateAutoFinishEnabled = reader.readBoolean();
        lt.alternateAutoFinishThreshold = reader.readInt();
        lt.useAlternateOffset = reader.readBoolean();
        lt.alternateNextLevel = reader.readInt();
        lt.alternateNextLevelOffset = reader.readInt();
        lt.visionMode = reader.readInt();
        lt.visionModeExploreRadius = reader.readInt();
        lt.visionRadius = reader.readInt();
        lt.initialVisionRadius = reader.readInt();
        lt.poisonPower = reader.readInt();
        lt.oldPoisonPower = reader.readInt();
        lt.finishMoveSpeed = reader.readInt();
        lt.firstMovingFinishX = reader.readInt();
        lt.firstMovingFinishY = reader.readInt();
        lt.firstMovingFinishZ = reader.readInt();
        lt.regionSize = reader.readInt();
        lt.timerValue = reader.readInt();
        lt.initialTimerValue = reader.readInt();
        lt.timerActive = reader.readBoolean();
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
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                        this.savedTowerState.getCell(y, x, z, e)
                                .writeMazeObject(writer);
                    }
                }
            }
        }
    }

    public void readLegacySavedTowerState(final XLegacyDataReader reader,
            final int formatVersion) throws IOException {
        int x, y, z, e, sizeX, sizeY, sizeZ;
        sizeX = reader.readInt();
        sizeY = reader.readInt();
        sizeZ = reader.readInt();
        this.savedTowerState = new LowLevelAMODataStore(sizeY, sizeX, sizeZ,
                MazeConstants.LAYER_COUNT);
        for (x = 0; x < sizeY; x++) {
            for (y = 0; y < sizeX; y++) {
                for (z = 0; z < sizeZ; z++) {
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                        this.savedTowerState.setCell(MazeRunnerII
                                .getApplication().getObjects()
                                .readLegacyMazeObject(reader, formatVersion), y,
                                x, z, e);
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
        this.savedTowerState = new LowLevelAMODataStore(sizeY, sizeX, sizeZ,
                MazeConstants.LAYER_COUNT);
        for (x = 0; x < sizeY; x++) {
            for (y = 0; y < sizeX; y++) {
                for (z = 0; z < sizeZ; z++) {
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                        this.savedTowerState.setCell(
                                MazeRunnerII.getApplication().getObjects()
                                        .readMazeObject(reader, formatVersion),
                                y, x, z, e);
                    }
                }
            }
        }
    }
}
