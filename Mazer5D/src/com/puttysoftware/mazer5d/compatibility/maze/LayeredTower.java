/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.maze;

import java.io.IOException;
import java.util.Arrays;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.compatibility.abc.DirectionResolver;
import com.puttysoftware.mazer5d.compatibility.abc.GenericLightModifier;
import com.puttysoftware.mazer5d.compatibility.abc.GenericMovingObject;
import com.puttysoftware.mazer5d.compatibility.abc.GenericTeleport;
import com.puttysoftware.mazer5d.compatibility.abc.MazeObjectModel;
import com.puttysoftware.mazer5d.compatibility.abc.RandomGenerationRule;
import com.puttysoftware.mazer5d.compatibility.abc.TypeConstants;
import com.puttysoftware.mazer5d.compatibility.objects.GameObjects;
import com.puttysoftware.mazer5d.files.io.XDataReader;
import com.puttysoftware.mazer5d.files.io.XDataWriter;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.mazemodel.VisionModes;
import com.puttysoftware.mazer5d.objectmodel.Layers;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;
import com.puttysoftware.mazer5d.prefs.Prefs;
import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.storage.FlagStorage;

class LayeredTower implements Cloneable {
    // Properties
    private LowLevelDataStore data;
    private SavedTowerState savedTowerState;
    private final FlagStorage visionData;
    private final int[] playerData;
    private final int[] findResult;
    private int visionRadius;
    private int initialVisionRadius;
    private boolean horizontalWraparoundEnabled;
    private boolean verticalWraparoundEnabled;
    private boolean thirdDimensionWraparoundEnabled;
    private String levelTitle;
    private String levelStartMessage;
    private String levelEndMessage;
    private int poisonPower;
    private int oldPoisonPower;
    private int timerValue;
    private int initialTimerValue;
    private boolean timerActive;
    private boolean autoFinishThresholdEnabled;
    private int autoFinishThreshold;
    private int alternateAutoFinishThreshold;
    private int nextLevel;
    private int nextLevelOffset;
    private boolean useOffset;
    private int alternateNextLevel;
    private int alternateNextLevelOffset;
    private boolean useAlternateOffset;
    private int finishMoveSpeed;
    private int firstMovingFinishX;
    private int firstMovingFinishY;
    private int firstMovingFinishZ;
    private int visionMode;
    private int visionModeExploreRadius;
    private static final int MAX_POISON_POWER = 10;
    private static final int MAX_VISION_RADIUS = 6;
    private static final int MIN_VISION_RADIUS = 1;
    private static final int MAX_FLOORS = 64;
    private static final int MIN_FLOORS = 1;
    private static final int MAX_COLUMNS = 64;
    private static final int MIN_COLUMNS = 2;
    private static final int MAX_ROWS = 64;
    private static final int MIN_ROWS = 2;

    // Constructors
    public LayeredTower(final int rows, final int cols, final int floors) {
        this.data = new LowLevelDataStore(cols, rows, floors, Layers.COUNT);
        this.savedTowerState = new SavedTowerState(rows, cols, floors);
        this.visionData = new FlagStorage(cols, rows, floors);
        this.playerData = new int[3];
        Arrays.fill(this.playerData, -1);
        this.findResult = new int[3];
        Arrays.fill(this.findResult, -1);
        this.setVisionRadiusToMaximum();
        this.horizontalWraparoundEnabled = false;
        this.verticalWraparoundEnabled = false;
        this.thirdDimensionWraparoundEnabled = false;
        this.levelTitle = "Untitled Level";
        this.levelStartMessage = "Let's Solve The Level!";
        this.levelEndMessage = "Level Solved!";
        this.poisonPower = 0;
        this.oldPoisonPower = 0;
        this.timerValue = 0;
        this.initialTimerValue = 0;
        this.timerActive = false;
        this.autoFinishThresholdEnabled = false;
        this.autoFinishThreshold = 0;
        this.alternateAutoFinishThreshold = 0;
        this.nextLevel = 0;
        this.nextLevelOffset = 1;
        this.useOffset = true;
        this.finishMoveSpeed = 10;
        this.visionMode = VisionModes.RADIUS;
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
        final LayeredTower copy = new LayeredTower(this.getRows(), this
                .getColumns(), this.getFloors());
        copy.data = (LowLevelDataStore) this.data.clone();
        copy.savedTowerState = this.savedTowerState.clone();
        System.arraycopy(this.playerData, 0, copy.playerData, 0,
                this.playerData.length);
        System.arraycopy(this.findResult, 0, copy.findResult, 0,
                this.findResult.length);
        copy.visionRadius = this.visionRadius;
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

    public void addVisionMode(final int newMode) {
        if ((this.visionMode | newMode) != this.visionMode) {
            this.visionMode += newMode;
            this.resetVisibleSquares();
        }
    }

    public void removeVisionMode(final int newMode) {
        if ((this.visionMode | newMode) == this.visionMode) {
            this.visionMode -= newMode;
            this.resetVisibleSquares();
        }
    }

    public int getFinishMoveSpeed() {
        return this.finishMoveSpeed;
    }

    public void setFinishMoveSpeed(final int value) {
        this.finishMoveSpeed = value;
    }

    public int getFirstMovingFinishX() {
        return this.firstMovingFinishX;
    }

    public void setFirstMovingFinishX(final int value) {
        this.firstMovingFinishX = value;
    }

    public int getFirstMovingFinishY() {
        return this.firstMovingFinishY;
    }

    public void setFirstMovingFinishY(final int value) {
        this.firstMovingFinishY = value;
    }

    public int getFirstMovingFinishZ() {
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

    public boolean getAutoFinishThresholdEnabled() {
        return this.autoFinishThresholdEnabled;
    }

    public void setAutoFinishThresholdEnabled(final boolean afte) {
        this.autoFinishThresholdEnabled = afte;
    }

    public int getAutoFinishThreshold() {
        return this.autoFinishThreshold;
    }

    public void setAutoFinishThreshold(final int aft) {
        this.autoFinishThreshold = aft;
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

    public MazeObjectModel getCell(final int row, final int col,
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
                    final MazeObjectModel mo = this.getCell(y, x, z,
                            Layers.OBJECT);
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
                    final MazeObjectModel mo = this.getCell(y, x, z,
                            Layers.OBJECT);
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

    public void findAllObjectPairsAndSwap(final MazeObjectModel o1,
            final MazeObjectModel o2) {
        int y, x, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final MazeObjectModel mo = this.getCell(y, x, z,
                            Layers.OBJECT);
                    if (mo != null) {
                        if (mo.getName().equals(o1.getName())) {
                            this.setCell(o2, y, x, z, Layers.OBJECT);
                        } else if (mo.getName().equals(o2.getName())) {
                            this.setCell(o1, y, x, z, Layers.OBJECT);
                        }
                    }
                }
            }
        }
    }

    public void findAllMatchingObjectsAndDecay(final MazeObjectModel o) {
        int y, x, z;
        final MazeObjectModel decayTo = GameObjects.getEmptySpace();
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final MazeObjectModel mo = this.getCell(y, x, z,
                            Layers.OBJECT);
                    if (mo != null) {
                        if (mo.getName().equals(o.getName())) {
                            this.setCell(decayTo, y, x, z, Layers.OBJECT);
                        }
                    }
                }
            }
        }
    }

    public void masterTrapTrigger() {
        int y, x, z;
        final MazeObjectModel decayTo = GameObjects.getEmptySpace();
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final MazeObjectModel mo = this.getCell(y, x, z,
                            Layers.OBJECT);
                    if (mo != null) {
                        if (mo.isOfType(TypeConstants.TYPE_WALL_TRAP) || mo
                                .isOfType(TypeConstants.TYPE_TRAPPED_WALL)) {
                            this.setCell(decayTo, y, x, z, Layers.OBJECT);
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
                final MazeObjectModel mo = this.getCell(y, x, floor,
                        Layers.OBJECT);
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
                Mazer5D.getBagOStuff().getGameManager().solvedLevel();
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
        final MazeObjectModel[][][] tempStorage = new MazeObjectModel[2 * r
                + 1][2 * r + 1][Layers.COUNT];
        try {
            for (u = x - r; u <= x + r; u++) {
                for (v = y - r; v <= y + r; v++) {
                    for (l = 0; l < Layers.COUNT; l++) {
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
                    for (l = 0; l < Layers.COUNT; l++) {
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
        final MazeObjectModel[][][] tempStorage = new MazeObjectModel[2 * r
                + 1][2 * r + 1][Layers.COUNT];
        try {
            for (u = x - r; u <= x + r; u++) {
                for (v = y - r; v <= y + r; v++) {
                    for (l = 0; l < Layers.COUNT; l++) {
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
                    for (l = 0; l < Layers.COUNT; l++) {
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

    public void resize(final int x, final int y, final int z) {
        // Allocate temporary storage array
        final LowLevelDataStore tempStorage = new LowLevelDataStore(y, x, z,
                Layers.COUNT);
        // Copy existing maze into temporary array
        int u, v, w, e;
        for (u = 0; u < y; u++) {
            for (v = 0; v < x; v++) {
                for (w = 0; w < z; w++) {
                    for (e = 0; e < Layers.COUNT; e++) {
                        try {
                            tempStorage.setCell(this.getCell(u, v, w, e), u, v,
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

    public void radialScanFreezeObjects(final int x, final int y, final int z,
            final int r) {
        int u, v;
        u = v = 0;
        // Perform the scan, and do the action
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                try {
                    final boolean reactsToIce = this.getCell(u, v, z,
                            Layers.OBJECT).isOfType(
                                    TypeConstants.TYPE_REACTS_TO_ICE);
                    if (reactsToIce) {
                        final MazeObjectModel there = this.getCell(u, v, z,
                                Layers.OBJECT);
                        if (there.getUniqueID().equals(
                                MazeObjects.BARRIER_GENERATOR)) {
                            // Freeze the generator
                            this.setCell(GameObjects.createObject(
                                    MazeObjects.ICED_BARRIER_GENERATOR), u, v,
                                    z, Layers.OBJECT);
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
                    this.setCell(GameObjects.createObject(MazeObjects.ICE), u,
                            v, z, Layers.GROUND);
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
                    final boolean reactsToFire = this.getCell(u, v, z,
                            Layers.OBJECT).isOfType(
                                    TypeConstants.TYPE_REACTS_TO_FIRE);
                    if (reactsToFire) {
                        final MazeObjectModel there = this.getCell(u, v, z,
                                Layers.OBJECT);
                        if (there.getUniqueID().equals(
                                MazeObjects.BARRIER_GENERATOR)) {
                            // Enrage the generator
                            this.setCell(GameObjects.createObject(
                                    MazeObjects.ENRAGED_BARRIER_GENERATOR), u,
                                    v, z, Layers.OBJECT);
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
                    this.setCell(GameObjects.createObject(MazeObjects.DIRT), u,
                            v, z, Layers.GROUND);
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
                    final boolean reactsToPoison = this.getCell(u, v, z,
                            Layers.OBJECT).isOfType(
                                    TypeConstants.TYPE_REACTS_TO_POISON);
                    if (reactsToPoison) {
                        final MazeObjectModel there = this.getCell(u, v, z,
                                Layers.OBJECT);
                        if (there.getUniqueID().equals(
                                MazeObjects.BARRIER_GENERATOR)) {
                            // Weaken the generator
                            this.setCell(GameObjects.createObject(
                                    MazeObjects.POISONED_BARRIER_GENERATOR), u,
                                    v, z, Layers.OBJECT);
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
                    this.setCell(GameObjects.createObject(
                            MazeObjects.ICED_BARRIER_GENERATOR), u, v, z,
                            Layers.GROUND);
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
                    final boolean reactsToShock = this.getCell(u, v, z,
                            Layers.OBJECT).isOfType(
                                    TypeConstants.TYPE_REACTS_TO_SHOCK);
                    if (reactsToShock) {
                        final MazeObjectModel there = this.getCell(u, v, z,
                                Layers.OBJECT);
                        if (there.getUniqueID().equals(
                                MazeObjects.BARRIER_GENERATOR)) {
                            // Shock the generator
                            this.setCell(GameObjects.createObject(
                                    MazeObjects.SHOCKED_BARRIER_GENERATOR), u,
                                    v, z, Layers.OBJECT);
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
                    this.setCell(GameObjects.createObject(
                            MazeObjects.FORCE_FIELD), u, v, z, Layers.GROUND);
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
        final MazeObjectModel[][][] preShuffle = new MazeObjectModel[2 * r
                + 1][2 * r + 1][Layers.COUNT];
        // Load the preShuffle array
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                for (l = 0; l < Layers.COUNT; l++) {
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
        final MazeObjectModel[][][] postShuffle = LayeredTower.shuffleObjects(
                preShuffle, r);
        // Load the maze with the postShuffle array
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                for (l = 0; l < Layers.COUNT; l++) {
                    uFix = u - (x - r);
                    vFix = v - (y - r);
                    if (postShuffle[uFix][vFix][l] != null) {
                        this.setCell(postShuffle[uFix][vFix][l], u, v, z, l);
                    }
                }
            }
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
                    final boolean isEmpty = this.getCell(u, v, z, Layers.OBJECT)
                            .isOfType(TypeConstants.TYPE_EMPTY_SPACE);
                    if (isEmpty) {
                        final RandomRange rr = new RandomRange(1, 5);
                        final int chance = rr.generate();
                        if (chance == 1) {
                            // Grow a crevasse
                            this.setCell(GameObjects.createObject(
                                    MazeObjects.CREVASSE), u, v, z,
                                    Layers.OBJECT);
                        }
                    }
                    final boolean isBreakable = this.getCell(u, v, z,
                            Layers.OBJECT).isOfType(
                                    TypeConstants.TYPE_BREAKABLE_WALL);
                    if (isBreakable) {
                        // Destroy the wall
                        this.setCell(GameObjects.getEmptySpace(), u, v, z,
                                Layers.OBJECT);
                    }
                    final boolean isWall = this.getCell(u, v, z, Layers.OBJECT)
                            .isOfType(TypeConstants.TYPE_PLAIN_WALL);
                    if (isWall) {
                        // Crack the wall
                        this.setCell(GameObjects.createObject(
                                MazeObjects.CRACKED_WALL), u, v, z,
                                Layers.OBJECT);
                    }
                    final boolean isCharacter = this.getCell(u, v, z,
                            Layers.OBJECT).isOfType(
                                    TypeConstants.TYPE_CHARACTER);
                    if (isCharacter) {
                        final BagOStuff app = Mazer5D.getBagOStuff();
                        final MazeModel m = app.getMazeManager().getMaze();
                        app.getGameManager().keepNextMessage();
                        app.showMessage(
                                "You find yourself caught in the quake, and fall over, hurting yourself a bit.");
                        m.doDamagePercentage(2);
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Do nothing
                }
            }
        }
    }

    public void resetVisibleSquares() {
        for (int x = 0; x < this.getColumns(); x++) {
            for (int y = 0; y < this.getRows(); y++) {
                for (int z = 0; z < this.getFloors(); z++) {
                    this.visionData.setCell(false, x, y, z);
                }
            }
        }
    }

    public void updateVisibleSquares(final int xp, final int yp, final int zp) {
        if ((this.visionMode | VisionModes.EXPLORE) == this.visionMode) {
            for (int x = xp - this.visionModeExploreRadius; x <= xp
                    + this.visionModeExploreRadius; x++) {
                for (int y = yp - this.visionModeExploreRadius; y <= yp
                        + this.visionModeExploreRadius; y++) {
                    try {
                        this.visionData.setCell(true, x, y, zp);
                    } catch (final ArrayIndexOutOfBoundsException aioobe) {
                        // Ignore
                    }
                }
            }
        }
    }

    public boolean isSquareVisible(final int x1, final int y1, final int x2,
            final int y2) {
        if (this.visionMode == VisionModes.NONE) {
            return LayeredTower.isSquareVisibleNone();
        } else {
            boolean result = true;
            if ((this.visionMode | VisionModes.RADIUS) == this.visionMode) {
                result = result && this.isSquareVisibleRadius(x1, y1, x2, y2);
            }
            if ((this.visionMode | VisionModes.EXPLORE) == this.visionMode) {
                result = result && this.isSquareVisibleExplore(x2, y2);
            }
            return result;
        }
    }

    private static boolean isSquareVisibleNone() {
        return true;
    }

    private boolean isSquareVisibleRadius(final int x1, final int y1,
            final int x2, final int y2) {
        final GenericLightModifier lg = (GenericLightModifier) GameObjects
                .createObject(MazeObjects.LIGHT_GEM);
        final GenericLightModifier dg = (GenericLightModifier) GameObjects
                .createObject(MazeObjects.DARK_GEM);
        boolean result = true;
        final int zLoc = Mazer5D.getBagOStuff().getGameManager()
                .getPlayerManager().getPlayerLocationZ();
        final int xDist = this.pointDistance(x1, x2, 1);
        final int yDist = this.pointDistance(y1, y2, 2);
        if (xDist <= this.visionRadius && yDist <= this.visionRadius) {
            if (this.radialScan(x2, y2, zLoc, Layers.OBJECT, dg
                    .getEffectRadius(), dg.getName())) {
                result = false;
            } else {
                result = true;
            }
        } else {
            if (this.radialScan(x2, y2, zLoc, Layers.OBJECT, lg
                    .getEffectRadius(), lg.getName())) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
    }

    private boolean isSquareVisibleExplore(final int x2, final int y2) {
        final int zLoc = Mazer5D.getBagOStuff().getGameManager()
                .getPlayerManager().getPlayerLocationZ();
        try {
            return this.visionData.getCell(x2, y2, zLoc);
        } catch (final ArrayIndexOutOfBoundsException aioobe) {
            return true;
        }
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

    public void setCell(final MazeObjectModel mo, final int row, final int col,
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

    public void setStartRow(final int newStartRow) {
        this.playerData[1] = newStartRow;
    }

    public void setStartColumn(final int newStartColumn) {
        this.playerData[0] = newStartColumn;
    }

    public void setStartFloor(final int newStartFloor) {
        this.playerData[2] = newStartFloor;
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
                    for (e = 0; e < Layers.COUNT; e++) {
                        final MazeObjectModel obj = this.getCell(y, x, z, e);
                        if (obj.getUniqueID().equals(
                                MazeObjects.MOVING_FINISH)) {
                            final GenericTeleport gt = (GenericTeleport) obj;
                            gt.deactivate();
                        }
                    }
                }
            }
        }
    }

    public void activateFirstMovingFinish() {
        final MazeObjectModel obj = this.getCell(this.getFirstMovingFinishX(),
                this.getFirstMovingFinishY(), this.getFirstMovingFinishZ(),
                Layers.OBJECT);
        if (obj.getUniqueID().equals(MazeObjects.MOVING_FINISH)) {
            final GenericTeleport gt = (GenericTeleport) obj;
            gt.activate();
        }
    }

    public void fill(final MazeObjectModel bottom, final MazeObjectModel top) {
        int y, x, z, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < Layers.COUNT; e++) {
                        if (e == Layers.GROUND) {
                            this.setCell(bottom, y, x, z, e);
                        } else {
                            this.setCell(top, y, x, z, e);
                        }
                    }
                }
            }
        }
    }

    public void fillFloor(final MazeObjectModel bottom,
            final MazeObjectModel top, final int z) {
        int x, y, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (e = 0; e < Layers.COUNT; e++) {
                    if (e == Layers.GROUND) {
                        this.setCell(bottom, y, x, z, e);
                    } else {
                        this.setCell(top, y, x, z, e);
                    }
                }
            }
        }
    }

    public void fillRandomly(final MazeModel maze, final int w) {
        for (int z = 0; z < this.getFloors(); z++) {
            this.fillFloorRandomly(maze, z, w);
        }
    }

    public void fillRandomlyCustom(final MazeModel maze, final int w) {
        for (int z = 0; z < this.getFloors(); z++) {
            this.fillFloorRandomlyCustom(maze, z, w);
        }
    }

    public void fillFloorRandomly(final MazeModel maze, final int z,
            final int w) {
        // Pre-Pass
        final MazeObjectModel pass1FillBottom = GameObjects.createObject(Prefs
                .getEditorDefaultFill());
        final MazeObjectModel pass1FillTop = GameObjects.getEmptySpace();
        RandomRange r = null;
        int x, y, e;
        // Pass 1
        this.fillFloor(pass1FillBottom, pass1FillTop, z);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        for (e = 0; e < Layers.COUNT; e++) {
            final MazeObjectModel[] objectsWithoutPrerequisites = GameObjects
                    .getAllWithoutPrerequisiteAndNotRequired(e);
            if (objectsWithoutPrerequisites != null) {
                r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
                for (x = 0; x < columns; x++) {
                    for (y = 0; y < rows; y++) {
                        final MazeObjectModel placeObj = objectsWithoutPrerequisites[r
                                .generate()];
                        final boolean okay = placeObj.shouldGenerateObject(maze,
                                x, y, z, w, e);
                        if (okay) {
                            this.setCell(GameObjects.createObject(placeObj
                                    .getUniqueID()), y, x, z, e);
                            placeObj.editorGenerateHook(y, x, z);
                        }
                    }
                }
            }
        }
        // Pass 3
        for (int layer = 0; layer < Layers.COUNT; layer++) {
            final MazeObjectModel[] requiredObjects = GameObjects
                    .getAllRequired(layer);
            if (requiredObjects != null) {
                final RandomRange row = new RandomRange(0, this.getRows() - 1);
                final RandomRange column = new RandomRange(0, this.getColumns()
                        - 1);
                int randomColumn, randomRow;
                for (x = 0; x < requiredObjects.length; x++) {
                    randomRow = row.generate();
                    randomColumn = column.generate();
                    final MazeObjectModel currObj = requiredObjects[x];
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
                            this.setCell(GameObjects.createObject(currObj
                                    .getUniqueID()), randomColumn, randomRow, z,
                                    layer);
                            currObj.editorGenerateHook(y, x, z);
                        } else {
                            while (!currObj.shouldGenerateObject(maze,
                                    randomColumn, randomRow, z, w, layer)) {
                                randomRow = row.generate();
                                randomColumn = column.generate();
                            }
                            this.setCell(GameObjects.createObject(currObj
                                    .getUniqueID()), randomColumn, randomRow, z,
                                    layer);
                            currObj.editorGenerateHook(y, x, z);
                        }
                    }
                }
            }
        }
    }

    public void fillFloorRandomlyCustom(final MazeModel maze, final int z,
            final int w) {
        // Pre-Pass
        final MazeObjectModel pass1FillBottom = GameObjects.createObject(Prefs
                .getEditorDefaultFill());
        final MazeObjectModel pass1FillTop = GameObjects.getEmptySpace();
        final MazeObjectModel[] withoutRuleSets = GameObjects
                .getAllObjectsWithoutRuleSets();
        final MazeObjectModel[] withRuleSets = GameObjects
                .getAllObjectsWithRuleSets();
        RandomRange r = null;
        int x, y, e;
        // Pass 1
        this.fillFloor(pass1FillBottom, pass1FillTop, z);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        if (withoutRuleSets != null) {
            for (e = 0; e < Layers.COUNT; e++) {
                final MazeObjectModel[] objectsWithoutPrerequisites = GameObjects
                        .getAllWithoutPrerequisiteAndNotRequiredSubset(
                                withoutRuleSets, e);
                if (objectsWithoutPrerequisites != null
                        && objectsWithoutPrerequisites.length > 0) {
                    r = new RandomRange(0, objectsWithoutPrerequisites.length
                            - 1);
                    for (x = 0; x < columns; x++) {
                        for (y = 0; y < rows; y++) {
                            final MazeObjectModel placeObj = objectsWithoutPrerequisites[r
                                    .generate()];
                            final boolean okay = placeObj.shouldGenerateObject(
                                    maze, y, x, z, w, e);
                            if (okay) {
                                this.setCell(GameObjects.createObject(placeObj
                                        .getUniqueID()), y, x, z, e);
                                placeObj.editorGenerateHook(y, x, z);
                            }
                        }
                    }
                }
            }
            // Pass 3
            for (int layer = 0; layer < Layers.COUNT; layer++) {
                final MazeObjectModel[] requiredObjects = GameObjects
                        .getAllRequiredSubset(withoutRuleSets, layer);
                if (requiredObjects != null) {
                    final RandomRange row = new RandomRange(0, this.getRows()
                            - 1);
                    final RandomRange column = new RandomRange(0, this
                            .getColumns() - 1);
                    int randomColumn, randomRow;
                    for (x = 0; x < requiredObjects.length; x++) {
                        randomRow = row.generate();
                        randomColumn = column.generate();
                        final MazeObjectModel currObj = requiredObjects[x];
                        final int min = currObj.getMinimumRequiredQuantity(
                                maze);
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
                                this.setCell(GameObjects.createObject(currObj
                                        .getUniqueID()), randomColumn,
                                        randomRow, z, layer);
                                currObj.editorGenerateHook(y, x, z);
                            } else {
                                while (!currObj.shouldGenerateObject(maze,
                                        randomColumn, randomRow, z, w, layer)) {
                                    randomRow = row.generate();
                                    randomColumn = column.generate();
                                }
                                this.setCell(GameObjects.createObject(currObj
                                        .getUniqueID()), randomColumn,
                                        randomRow, z, layer);
                                currObj.editorGenerateHook(y, x, z);
                            }
                        }
                    }
                }
            }
        }
        if (withRuleSets != null) {
            // Pass N + 2
            for (e = 0; e < Layers.COUNT; e++) {
                final MazeObjectModel[] objectsWithoutPrerequisites = GameObjects
                        .getAllWithoutPrerequisiteAndNotRequiredSubset(
                                withRuleSets, e);
                if (objectsWithoutPrerequisites != null) {
                    r = new RandomRange(0, objectsWithoutPrerequisites.length
                            - 1);
                    for (x = 0; x < columns; x++) {
                        for (y = 0; y < rows; y++) {
                            final MazeObjectModel placeObj = objectsWithoutPrerequisites[r
                                    .generate()];
                            final boolean okay = placeObj.getRuleSet()
                                    .shouldGenerateObject(maze, y, x, z, w, e);
                            if (okay) {
                                this.setCell(GameObjects.createObject(placeObj
                                        .getUniqueID()), y, x, z, e);
                                placeObj.editorGenerateHook(y, x, z);
                            }
                        }
                    }
                }
            }
            // Pass N + 3
            for (int layer = 0; layer < Layers.COUNT; layer++) {
                final MazeObjectModel[] requiredObjects = GameObjects
                        .getAllRequiredSubset(withRuleSets, layer);
                if (requiredObjects != null) {
                    final RandomRange row = new RandomRange(0, this.getRows()
                            - 1);
                    final RandomRange column = new RandomRange(0, this
                            .getColumns() - 1);
                    int randomColumn, randomRow;
                    for (x = 0; x < requiredObjects.length; x++) {
                        randomRow = row.generate();
                        randomColumn = column.generate();
                        final MazeObjectModel currObj = requiredObjects[x];
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
                                this.setCell(GameObjects.createObject(currObj
                                        .getUniqueID()), randomColumn,
                                        randomRow, z, layer);
                                currObj.editorGenerateHook(y, x, z);
                            } else {
                                while (!currObj.getRuleSet()
                                        .shouldGenerateObject(maze, randomRow,
                                                randomColumn, z, w, layer)) {
                                    randomRow = row.generate();
                                    randomColumn = column.generate();
                                }
                                this.setCell(GameObjects.createObject(currObj
                                        .getUniqueID()), randomColumn,
                                        randomRow, z, layer);
                                currObj.editorGenerateHook(y, x, z);
                            }
                        }
                    }
                }
            }
        }
    }

    private void fillNulls() {
        final MazeObjectModel bottom = GameObjects.createObject(Prefs
                .getEditorDefaultFill());
        final MazeObjectModel top = GameObjects.getEmptySpace();
        int y, x, z, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < Layers.COUNT; e++) {
                        if (this.getCell(y, x, z, e) == null) {
                            if (e == Layers.GROUND) {
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
                    for (e = 0; e < Layers.COUNT; e++) {
                        this.savedTowerState.setDataCell(this.getCell(y, x, z,
                                e), x, y, z, e);
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
                    for (e = 0; e < Layers.COUNT; e++) {
                        this.setCell(this.savedTowerState.getDataCell(x, y, z,
                                e), y, x, z, e);
                    }
                }
            }
        }
    }

    public void updateMovingBlockPosition(final int move, final int xLoc,
            final int yLoc, final GenericMovingObject block) {
        final int[] dirMove = DirectionResolver.unresolveRelativeDirection(
                move);
        final int zLoc = Mazer5D.getBagOStuff().getGameManager()
                .getPlayerManager().getPlayerLocationZ();
        try {
            final MazeObjectModel there = this.getCell(xLoc + dirMove[0], yLoc
                    + dirMove[1], zLoc, Layers.OBJECT);
            final MazeObjectModel ground = this.getCell(xLoc + dirMove[0], yLoc
                    + dirMove[1], zLoc, Layers.GROUND);
            if (!there.isSolid() && !there.getName().equals("Player")) {
                this.setCell(block.getSavedObject(), xLoc, yLoc, zLoc,
                        Layers.OBJECT);
                // Move the block
                block.setSavedObject(there);
                this.setCell(block, xLoc + dirMove[0], yLoc + dirMove[1], zLoc,
                        Layers.OBJECT);
                // Does the ground have friction?
                if (!ground.hasFriction()) {
                    // No - move the block again
                    this.updateMovingBlockPosition(move, xLoc + dirMove[0], yLoc
                            + dirMove[1], block);
                }
            }
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
    }

    public void warpObject(final MazeObjectModel mo, final int x, final int y,
            final int z, final int l) {
        final RandomRange row = new RandomRange(0, this.getRows() - 1);
        final RandomRange column = new RandomRange(0, this.getColumns() - 1);
        int randomColumn, randomRow;
        randomRow = row.generate();
        randomColumn = column.generate();
        final MazeObjectModel currObj = this.getCell(randomRow, randomColumn, z,
                Layers.OBJECT);
        if (!currObj.isSolid()) {
            this.setCell(GameObjects.getEmptySpace(), x, y, z, l);
            this.setCell(mo, randomRow, randomColumn, z, Layers.OBJECT);
        } else {
            while (currObj.isSolid()) {
                randomRow = row.generate();
                randomColumn = column.generate();
                this.getCell(randomRow, randomColumn, z, Layers.OBJECT);
            }
            this.setCell(GameObjects.getEmptySpace(), x, y, z, l);
            this.setCell(mo, randomRow, randomColumn, z, Layers.OBJECT);
        }
    }

    public void hotGround(final int x, final int y, final int z) {
        this.setCell(GameObjects.createObject(MazeObjects.HOT_ROCK), x, y, z,
                Layers.GROUND);
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

    public final void extendTimerByInitialValueHalved() {
        if (this.timerActive) {
            this.timerValue += this.initialTimerValue / 2;
        }
    }

    public final void extendTimerByInitialValueDoubled() {
        if (this.timerActive) {
            this.timerValue += this.initialTimerValue * 2;
        }
    }

    private static MazeObjectModel[][][] shuffleObjects(
            final MazeObjectModel[][][] preShuffle, final int r) {
        final MazeObjectModel[][][] postShuffle = new MazeObjectModel[2 * r
                + 1][2 * r + 1][Layers.COUNT];
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
        randomLocations = LayeredTower.shuffleArray(randomLocations);
        // Populate postShuffle array
        counter = 0;
        for (int x = 0; x < preShuffle.length; x++) {
            for (int y = 0; y < preShuffle[x].length; y++) {
                for (int z = 0; z < Layers.COUNT; z++) {
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

    private static int[][] shuffleArray(final int[][] src) {
        int temp = 0;
        final int minSwaps = (int) Math.sqrt(src.length);
        final int maxSwaps = src.length - 1;
        int oldLoc, newLoc;
        final RandomRange rSwap = new RandomRange(minSwaps, maxSwaps);
        final RandomRange locSwap = new RandomRange(0, src.length - 1);
        final int swaps = rSwap.generate();
        for (int s = 0; s < swaps; s++) {
            do {
                oldLoc = locSwap.generate();
                newLoc = locSwap.generate();
            } while (src[oldLoc][0] == -1 || src[newLoc][0] == -1);
            for (int w = 0; w < src[0].length; w++) {
                // Swap
                temp = src[newLoc][w];
                src[newLoc][w] = src[oldLoc][w];
                src[oldLoc][w] = temp;
            }
        }
        return src;
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

    public void writeXMLLayeredTower(final XDataWriter writer)
            throws IOException {
        int y, x, z, e;
        writer.writeInt(this.getColumns());
        writer.writeInt(this.getRows());
        writer.writeInt(this.getFloors());
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < Layers.COUNT; e++) {
                        this.getCell(y, x, z, e).writeMazeObjectXML(writer);
                    }
                }
            }
        }
        for (y = 0; y < 3; y++) {
            writer.writeInt(this.playerData[y]);
        }
        writer.writeBoolean(this.horizontalWraparoundEnabled);
        writer.writeBoolean(this.verticalWraparoundEnabled);
        writer.writeBoolean(this.thirdDimensionWraparoundEnabled);
        writer.writeString(this.levelTitle);
        writer.writeString(this.levelStartMessage);
        writer.writeString(this.levelEndMessage);
        writer.writeInt(this.poisonPower);
        writer.writeInt(this.timerValue);
        writer.writeBoolean(this.timerActive);
        writer.writeBoolean(this.autoFinishThresholdEnabled);
        writer.writeInt(this.autoFinishThreshold);
        writer.writeBoolean(this.useOffset);
        writer.writeInt(this.nextLevel);
        writer.writeInt(this.nextLevelOffset);
        writer.writeInt(this.visionRadius);
        writer.writeInt(this.finishMoveSpeed);
        writer.writeInt(this.firstMovingFinishX);
        writer.writeInt(this.firstMovingFinishY);
        writer.writeInt(this.firstMovingFinishZ);
        writer.writeInt(this.visionMode);
        writer.writeInt(this.visionModeExploreRadius);
        writer.writeInt(this.alternateAutoFinishThreshold);
        writer.writeBoolean(this.useAlternateOffset);
        writer.writeInt(this.alternateNextLevel);
        writer.writeInt(this.alternateNextLevelOffset);
    }

    public static LayeredTower readXMLLayeredTowerV1(final XDataReader reader,
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
                    for (e = 0; e < Layers.COUNT; e++) {
                        lt.setCell(GameObjects.readObject(reader, ver), y, x, z,
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
        lt.horizontalWraparoundEnabled = reader.readBoolean();
        lt.verticalWraparoundEnabled = reader.readBoolean();
        lt.thirdDimensionWraparoundEnabled = reader.readBoolean();
        lt.levelTitle = reader.readString();
        lt.levelStartMessage = reader.readString();
        lt.levelEndMessage = reader.readString();
        lt.poisonPower = reader.readInt();
        lt.oldPoisonPower = lt.poisonPower;
        lt.timerValue = reader.readInt();
        lt.initialTimerValue = lt.timerValue;
        lt.timerActive = reader.readBoolean();
        lt.initialVisionRadius = LayeredTower.MAX_VISION_RADIUS;
        return lt;
    }

    public static LayeredTower readXMLLayeredTowerV2(final XDataReader reader,
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
                    for (e = 0; e < Layers.COUNT; e++) {
                        lt.setCell(GameObjects.readObject(reader, ver), y, x, z,
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
        lt.horizontalWraparoundEnabled = reader.readBoolean();
        lt.verticalWraparoundEnabled = reader.readBoolean();
        lt.thirdDimensionWraparoundEnabled = reader.readBoolean();
        lt.levelTitle = reader.readString();
        lt.levelStartMessage = reader.readString();
        lt.levelEndMessage = reader.readString();
        lt.poisonPower = reader.readInt();
        lt.oldPoisonPower = lt.poisonPower;
        lt.timerValue = reader.readInt();
        lt.initialTimerValue = lt.timerValue;
        lt.timerActive = reader.readBoolean();
        lt.autoFinishThresholdEnabled = reader.readBoolean();
        lt.autoFinishThreshold = reader.readInt();
        lt.useOffset = reader.readBoolean();
        lt.nextLevel = reader.readInt();
        lt.nextLevelOffset = reader.readInt();
        lt.initialVisionRadius = LayeredTower.MAX_VISION_RADIUS;
        return lt;
    }

    public static LayeredTower readXMLLayeredTowerV3(final XDataReader reader,
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
                    for (e = 0; e < Layers.COUNT; e++) {
                        lt.setCell(GameObjects.readObject(reader, ver), y, x, z,
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
        lt.horizontalWraparoundEnabled = reader.readBoolean();
        lt.verticalWraparoundEnabled = reader.readBoolean();
        lt.thirdDimensionWraparoundEnabled = reader.readBoolean();
        lt.levelTitle = reader.readString();
        lt.levelStartMessage = reader.readString();
        lt.levelEndMessage = reader.readString();
        lt.poisonPower = reader.readInt();
        lt.oldPoisonPower = lt.poisonPower;
        lt.timerValue = reader.readInt();
        lt.initialTimerValue = lt.timerValue;
        lt.timerActive = reader.readBoolean();
        lt.autoFinishThresholdEnabled = reader.readBoolean();
        lt.autoFinishThreshold = reader.readInt();
        lt.useOffset = reader.readBoolean();
        lt.nextLevel = reader.readInt();
        lt.nextLevelOffset = reader.readInt();
        lt.initialVisionRadius = LayeredTower.MAX_VISION_RADIUS;
        return lt;
    }

    public static LayeredTower readXMLLayeredTowerV4(final XDataReader reader,
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
                    for (e = 0; e < Layers.COUNT; e++) {
                        lt.setCell(GameObjects.readObject(reader, ver), y, x, z,
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
        lt.horizontalWraparoundEnabled = reader.readBoolean();
        lt.verticalWraparoundEnabled = reader.readBoolean();
        lt.thirdDimensionWraparoundEnabled = reader.readBoolean();
        lt.levelTitle = reader.readString();
        lt.levelStartMessage = reader.readString();
        lt.levelEndMessage = reader.readString();
        lt.poisonPower = reader.readInt();
        lt.oldPoisonPower = lt.poisonPower;
        lt.timerValue = reader.readInt();
        lt.initialTimerValue = lt.timerValue;
        lt.timerActive = reader.readBoolean();
        lt.autoFinishThresholdEnabled = reader.readBoolean();
        lt.autoFinishThreshold = reader.readInt();
        lt.useOffset = reader.readBoolean();
        lt.nextLevel = reader.readInt();
        lt.nextLevelOffset = reader.readInt();
        lt.visionRadius = reader.readInt();
        lt.initialVisionRadius = lt.visionRadius;
        lt.finishMoveSpeed = reader.readInt();
        lt.firstMovingFinishX = reader.readInt();
        lt.firstMovingFinishY = reader.readInt();
        lt.firstMovingFinishZ = reader.readInt();
        return lt;
    }

    public static LayeredTower readXMLLayeredTowerV5(final XDataReader reader,
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
                    for (e = 0; e < Layers.COUNT; e++) {
                        lt.setCell(GameObjects.readObject(reader, ver), y, x, z,
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
        lt.horizontalWraparoundEnabled = reader.readBoolean();
        lt.verticalWraparoundEnabled = reader.readBoolean();
        lt.thirdDimensionWraparoundEnabled = reader.readBoolean();
        lt.levelTitle = reader.readString();
        lt.levelStartMessage = reader.readString();
        lt.levelEndMessage = reader.readString();
        lt.poisonPower = reader.readInt();
        lt.oldPoisonPower = lt.poisonPower;
        lt.timerValue = reader.readInt();
        lt.initialTimerValue = lt.timerValue;
        lt.timerActive = reader.readBoolean();
        lt.autoFinishThresholdEnabled = reader.readBoolean();
        lt.autoFinishThreshold = reader.readInt();
        lt.useOffset = reader.readBoolean();
        lt.nextLevel = reader.readInt();
        lt.nextLevelOffset = reader.readInt();
        lt.visionRadius = reader.readInt();
        lt.initialVisionRadius = lt.visionRadius;
        lt.finishMoveSpeed = reader.readInt();
        lt.firstMovingFinishX = reader.readInt();
        lt.firstMovingFinishY = reader.readInt();
        lt.firstMovingFinishZ = reader.readInt();
        lt.visionMode = reader.readInt();
        lt.visionModeExploreRadius = reader.readInt();
        lt.alternateAutoFinishThreshold = reader.readInt();
        lt.useAlternateOffset = reader.readBoolean();
        lt.alternateNextLevel = reader.readInt();
        lt.alternateNextLevelOffset = reader.readInt();
        return lt;
    }

    public void writeSavedTowerStateXML(final XDataWriter writer)
            throws IOException {
        this.savedTowerState.writeSavedTowerStateXML(writer);
    }

    public void readSavedTowerStateXML(final XDataReader reader,
            final int formatVersion) throws IOException {
        this.savedTowerState = SavedTowerState.readSavedTowerStateXML(reader,
                formatVersion);
    }
}
