/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.world;

import java.io.IOException;
import java.util.Arrays;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;
import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.DirectionResolver;
import net.worldwizard.worldz.generic.RandomGenerationRule;
import net.worldwizard.worldz.generic.TypeConstants;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.generic.WorldObjectList;
import net.worldwizard.worldz.objects.BarrierGenerator;
import net.worldwizard.worldz.objects.Dirt;
import net.worldwizard.worldz.objects.Empty;
import net.worldwizard.worldz.objects.EnragedBarrierGenerator;
import net.worldwizard.worldz.objects.ForceField;
import net.worldwizard.worldz.objects.Ice;
import net.worldwizard.worldz.objects.IcedBarrierGenerator;
import net.worldwizard.worldz.objects.MovingBlock;
import net.worldwizard.worldz.objects.PoisonedBarrierGenerator;
import net.worldwizard.worldz.objects.ShockedBarrierGenerator;
import net.worldwizard.worldz.objects.Slime;

class LayeredTower implements Cloneable {
    // Properties
    private WorldObject[][][][] towerData;
    private SavedTowerState savedTowerState;
    private final int[] playerData;
    private final int[] findResult;
    private int visionRadius;
    private boolean horizontalWraparoundEnabled;
    private boolean verticalWraparoundEnabled;
    private boolean thirdDimensionWraparoundEnabled;
    private int poisonPower;
    private static final int MAX_POISON_POWER = 10;
    private static final int MAX_VISION_RADIUS = 6;
    private static final int MIN_VISION_RADIUS = 1;
    private static final int MAX_FLOORS = 16;
    private static final int MIN_FLOORS = 1;
    private static final int MAX_COLUMNS = 128;
    private static final int MIN_COLUMNS = 2;
    private static final int MAX_ROWS = 128;
    private static final int MIN_ROWS = 2;

    // Constructors
    LayeredTower(final int rows, final int cols, final int floor,
            final LayeredTower source) {
        this.towerData = new WorldObject[cols][rows][1][WorldConstants.LAYER_COUNT];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                for (int l = 0; l < WorldConstants.LAYER_COUNT; l++) {
                    this.towerData[x][y][0][l] = source.towerData[x][y][floor][l];
                }
            }
        }
        this.savedTowerState = new SavedTowerState(rows, cols, 1);
        this.playerData = new int[3];
        Arrays.fill(this.playerData, -1);
        this.findResult = new int[3];
        Arrays.fill(this.findResult, -1);
        this.setVisionRadiusToMaximum();
        this.horizontalWraparoundEnabled = false;
        this.verticalWraparoundEnabled = false;
        this.thirdDimensionWraparoundEnabled = false;
        this.poisonPower = 0;
    }

    public LayeredTower(final int rows, final int cols, final int floors) {
        this.towerData = new WorldObject[cols][rows][floors][WorldConstants.LAYER_COUNT];
        this.savedTowerState = new SavedTowerState(rows, cols, floors);
        this.playerData = new int[3];
        Arrays.fill(this.playerData, -1);
        this.findResult = new int[3];
        Arrays.fill(this.findResult, -1);
        this.setVisionRadiusToMaximum();
        this.horizontalWraparoundEnabled = false;
        this.verticalWraparoundEnabled = false;
        this.thirdDimensionWraparoundEnabled = false;
        this.poisonPower = 0;
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
        int x, y, z, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
                        copy.towerData[x][y][z][e] = this.towerData[x][y][z][e]
                                .clone();
                    }
                }
            }
        }
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

    public int getPoisonPower() {
        return this.poisonPower;
    }

    public void setPoisonPower(int fPP) {
        int pp = fPP;
        if (pp < 0) {
            pp = 0;
        }
        this.poisonPower = pp;
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

    public WorldObject getCell(int fR, int fC, int fF, final int extra) {
        int row = fR;
        int col = fC;
        int floor = fF;
        if (this.verticalWraparoundEnabled) {
            col = this.normalizeColumn(col);
        }
        if (this.horizontalWraparoundEnabled) {
            row = this.normalizeRow(row);
        }
        if (this.thirdDimensionWraparoundEnabled) {
            floor = this.normalizeFloor(floor);
        }
        return this.towerData[col][row][floor][extra];
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
        return this.towerData[0].length;
    }

    public int getColumns() {
        return this.towerData.length;
    }

    public int getFloors() {
        return this.towerData[0][0].length;
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
        int x, y, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final WorldObject mo = this.towerData[x][y][z][WorldConstants.LAYER_OBJECT];
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
        int x, y, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final WorldObject mo = this.towerData[x][y][z][WorldConstants.LAYER_OBJECT];
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

    public void findAllObjectPairsAndSwap(final WorldObject o1,
            final WorldObject o2) {
        int x, y, z;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final WorldObject mo = this.towerData[x][y][z][WorldConstants.LAYER_OBJECT];
                    if (mo != null) {
                        if (mo.getName().equals(o1.getName())) {
                            this.towerData[x][y][z][WorldConstants.LAYER_OBJECT] = o2;
                        } else if (mo.getName().equals(o2.getName())) {
                            this.towerData[x][y][z][WorldConstants.LAYER_OBJECT] = o1;
                        }
                    }
                }
            }
        }
    }

    public void findAllMatchingObjectsAndDecay(final WorldObject o) {
        int x, y, z;
        final WorldObject decayTo = new Empty();
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final WorldObject mo = this.towerData[x][y][z][WorldConstants.LAYER_OBJECT];
                    if (mo != null) {
                        if (mo.getName().equals(o.getName())) {
                            this.towerData[x][y][z][WorldConstants.LAYER_OBJECT] = decayTo;
                        }
                    }
                }
            }
        }
    }

    public void masterTrapTrigger() {
        int x, y, z;
        final WorldObject decayTo = new Empty();
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    final WorldObject mo = this.towerData[x][y][z][WorldConstants.LAYER_OBJECT];
                    if (mo != null) {
                        if (mo.isOfType(TypeConstants.TYPE_WALL_TRAP)
                                || mo.isOfType(TypeConstants.TYPE_TRAPPED_WALL)) {
                            this.towerData[x][y][z][WorldConstants.LAYER_OBJECT] = decayTo;
                        }
                    }
                }
            }
        }
    }

    public void tickTimers(final int floor) {
        int x, y;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                final WorldObject mo = this.towerData[x][y][floor][WorldConstants.LAYER_OBJECT];
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
        final WorldObject[][][] tempStorage = new WorldObject[2 * r + 1][2 * r + 1][WorldConstants.LAYER_COUNT];
        try {
            for (u = x - r; u <= x + r; u++) {
                for (v = y - r; v <= y + r; v++) {
                    for (l = 0; l < WorldConstants.LAYER_COUNT; l++) {
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
                    for (l = 0; l < WorldConstants.LAYER_COUNT; l++) {
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
        final WorldObject[][][] tempStorage = new WorldObject[2 * r + 1][2 * r + 1][WorldConstants.LAYER_COUNT];
        try {
            for (u = x - r; u <= x + r; u++) {
                for (v = y - r; v <= y + r; v++) {
                    for (l = 0; l < WorldConstants.LAYER_COUNT; l++) {
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
                    for (l = 0; l < WorldConstants.LAYER_COUNT; l++) {
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
        final WorldObject[][][][] tempStorage = new WorldObject[y][x][z][WorldConstants.LAYER_COUNT];
        // Copy existing world into temporary array
        int u, v, w, e;
        for (u = 0; u < y; u++) {
            for (v = 0; v < x; v++) {
                for (w = 0; w < z; w++) {
                    for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
                        try {
                            tempStorage[u][v][w][e] = this.towerData[u][v][w][e];
                        } catch (final ArrayIndexOutOfBoundsException aioob) {
                            // Do nothing
                        }
                    }
                }
            }
        }
        // Set the current data to the temporary array
        this.towerData = tempStorage;
        // Fill any blanks
        this.fillNulls();
        // Recreate saved tower state
        this.savedTowerState = new SavedTowerState(x, y, z);
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
                            WorldConstants.LAYER_OBJECT).isOfType(
                            TypeConstants.TYPE_REACTS_TO_ICE);
                    if (reactsToIce) {
                        final WorldObject there = this.getCell(u, v, z,
                                WorldConstants.LAYER_OBJECT);
                        if (there.getClass() == BarrierGenerator.class) {
                            // Freeze the generator
                            this.setCell(new IcedBarrierGenerator(), u, v, z,
                                    WorldConstants.LAYER_OBJECT);
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
                            WorldConstants.LAYER_GROUND);
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
                            WorldConstants.LAYER_OBJECT).isOfType(
                            TypeConstants.TYPE_REACTS_TO_FIRE);
                    if (reactsToFire) {
                        final WorldObject there = this.getCell(u, v, z,
                                WorldConstants.LAYER_OBJECT);
                        if (there.getClass() == BarrierGenerator.class) {
                            // Enrage the generator
                            this.setCell(new EnragedBarrierGenerator(), u, v,
                                    z, WorldConstants.LAYER_OBJECT);
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
                            WorldConstants.LAYER_GROUND);
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
                            WorldConstants.LAYER_OBJECT).isOfType(
                            TypeConstants.TYPE_REACTS_TO_POISON);
                    if (reactsToPoison) {
                        final WorldObject there = this.getCell(u, v, z,
                                WorldConstants.LAYER_OBJECT);
                        if (there.getClass() == BarrierGenerator.class) {
                            // Weaken the generator
                            this.setCell(new PoisonedBarrierGenerator(), u, v,
                                    z, WorldConstants.LAYER_OBJECT);
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
                            WorldConstants.LAYER_GROUND);
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
                            WorldConstants.LAYER_OBJECT).isOfType(
                            TypeConstants.TYPE_REACTS_TO_SHOCK);
                    if (reactsToShock) {
                        final WorldObject there = this.getCell(u, v, z,
                                WorldConstants.LAYER_OBJECT);
                        if (there.getClass() == BarrierGenerator.class) {
                            // Shock the generator
                            this.setCell(new ShockedBarrierGenerator(), u, v,
                                    z, WorldConstants.LAYER_OBJECT);
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
                            WorldConstants.LAYER_GROUND);
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
        final WorldObject[][][] preShuffle = new WorldObject[2 * r + 1][2 * r + 1][WorldConstants.LAYER_COUNT];
        // Load the preShuffle array
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                for (l = 0; l < WorldConstants.LAYER_COUNT; l++) {
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
        final WorldObject[][][] postShuffle = LayeredTower
                .shuffleObjects(preShuffle, r);
        // Load the world with the postShuffle array
        for (u = x - r; u <= x + r; u++) {
            for (v = y - r; v <= y + r; v++) {
                for (l = 0; l < WorldConstants.LAYER_COUNT; l++) {
                    uFix = u - (x - r);
                    vFix = v - (y - r);
                    if (postShuffle[uFix][vFix][l] != null) {
                        this.setCell(postShuffle[uFix][vFix][l], u, v, z, l);
                    }
                }
            }
        }
    }

    public boolean isSquareVisible(final int x1, final int y1, final int x2,
            final int y2) {
        boolean result = true;
        final int xDist = this.pointDistance(x1, x2, 1);
        final int yDist = this.pointDistance(y1, y2, 2);
        if (xDist <= this.visionRadius && yDist <= this.visionRadius) {
            result = true;
        } else {
            result = false;
        }
        return result;
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

    public void setCell(final WorldObject mo, int fR, int fC, int fF,
            final int extra) {
        int row = fR;
        int col = fC;
        int floor = fF;
        if (this.verticalWraparoundEnabled) {
            col = this.normalizeColumn(col);
        }
        if (this.horizontalWraparoundEnabled) {
            row = this.normalizeRow(row);
        }
        if (this.thirdDimensionWraparoundEnabled) {
            floor = this.normalizeFloor(floor);
        }
        this.towerData[col][row][floor][extra] = mo;
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

    public void fill(final WorldObject bottom, final WorldObject top) {
        int x, y, z, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
                        if (e == WorldConstants.LAYER_GROUND) {
                            this.towerData[x][y][z][e] = bottom.clone();
                        } else {
                            this.towerData[x][y][z][e] = top.clone();
                        }
                    }
                }
            }
        }
    }

    public void fillFloor(final WorldObject bottom, final WorldObject top,
            final int z) {
        int x, y, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
                    if (e == WorldConstants.LAYER_GROUND) {
                        this.towerData[x][y][z][e] = bottom.clone();
                    } else {
                        this.towerData[x][y][z][e] = top.clone();
                    }
                }
            }
        }
    }

    public void fillRandomly(final World world, final int w) {
        for (int z = 0; z < this.getFloors(); z++) {
            this.fillFloorRandomly(world, z, w);
        }
    }

    public void fillRandomlyCustom(final World world, final int w) {
        for (int z = 0; z < this.getFloors(); z++) {
            this.fillFloorRandomlyCustom(world, z, w);
        }
    }

    public void fillFloorRandomly(final World world, final int z, final int w) {
        // Pre-Pass
        final WorldObjectList objects = Worldz.getApplication().getObjects();
        final WorldObject pass1FillBottom = Worldz.getApplication()
                .getPrefsManager().getEditorDefaultFill();
        final WorldObject pass1FillTop = new Empty();
        RandomRange r = null;
        int x, y, e;
        // Pass 1
        this.fillFloor(pass1FillBottom, pass1FillTop, z);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
            final WorldObject[] objectsWithoutPrerequisites = objects
                    .getAllWithoutPrerequisiteAndNotRequired(e);
            if (objectsWithoutPrerequisites != null) {
                r = new RandomRange(0, objectsWithoutPrerequisites.length - 1);
                for (x = 0; x < columns; x++) {
                    for (y = 0; y < rows; y++) {
                        final WorldObject placeObj = objectsWithoutPrerequisites[r
                                .generate()];
                        final boolean okay = placeObj.shouldGenerateObject(
                                world, x, y, z, w, e);
                        if (okay) {
                            this.towerData[x][y][z][e] = objects
                                    .getNewInstanceByName(placeObj.getName());
                            placeObj.editorGenerateHook(x, y, z);
                        }
                    }
                }
            }
        }
        // Pass 3...N
        int N = 1;
        for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
            WorldObject[] objectsWithPrerequisites = objects
                    .getAllWithNthPrerequisite(e, N);
            while (objectsWithPrerequisites != null) {
                r = new RandomRange(0, objectsWithPrerequisites.length - 1);
                for (x = 0; x < columns; x++) {
                    for (y = 0; y < rows; y++) {
                        final WorldObject placeObj = objectsWithPrerequisites[r
                                .generate()];
                        final boolean okay = placeObj.shouldGenerateObject(
                                world, x, y, z, w, e);
                        if (okay) {
                            this.towerData[x][y][z][e] = objects
                                    .getNewInstanceByName(placeObj.getName());
                            placeObj.editorGenerateHook(x, y, z);
                        }
                    }
                }
                N++;
                objectsWithPrerequisites = objects.getAllWithNthPrerequisite(e,
                        N);
            }
        }
        // Pass N + 1
        for (int layer = 0; layer < WorldConstants.LAYER_COUNT; layer++) {
            final WorldObject[] requiredObjects = objects.getAllRequired(layer);
            if (requiredObjects != null) {
                final RandomRange row = new RandomRange(0, this.getRows() - 1);
                final RandomRange column = new RandomRange(0,
                        this.getColumns() - 1);
                int randomRow, randomColumn;
                for (x = 0; x < requiredObjects.length; x++) {
                    randomRow = row.generate();
                    randomColumn = column.generate();
                    final WorldObject currObj = requiredObjects[x];
                    final int min = currObj.getMinimumRequiredQuantity(world);
                    int max = currObj.getMaximumRequiredQuantity(world);
                    if (max == RandomGenerationRule.NO_LIMIT) {
                        // Maximum undefined, so define it relative to this
                        // world
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
                        if (currObj.shouldGenerateObject(world, randomRow,
                                randomColumn, z, w, layer)) {
                            this.towerData[x][y][z][e] = objects
                                    .getNewInstanceByName(currObj.getName());
                            currObj.editorGenerateHook(x, y, z);
                        } else {
                            while (!currObj.shouldGenerateObject(world,
                                    randomRow, randomColumn, z, w, layer)) {
                                randomRow = row.generate();
                                randomColumn = column.generate();
                            }
                            this.towerData[x][y][z][e] = objects
                                    .getNewInstanceByName(currObj.getName());
                            currObj.editorGenerateHook(x, y, z);
                        }
                    }
                }
            }
        }
    }

    public void fillFloorRandomlyCustom(final World world, final int z,
            final int w) {
        // Pre-Pass
        final WorldObjectList objects = Worldz.getApplication().getObjects();
        final WorldObject pass1FillBottom = Worldz.getApplication()
                .getPrefsManager().getEditorDefaultFill();
        final WorldObject pass1FillTop = new Empty();
        final WorldObject[] withoutRuleSets = objects
                .getAllObjectsWithoutRuleSets();
        final WorldObject[] withRuleSets = objects.getAllObjectsWithRuleSets();
        RandomRange r = null;
        int x, y, e;
        // Pass 1
        this.fillFloor(pass1FillBottom, pass1FillTop, z);
        // Pass 2
        final int columns = this.getColumns();
        final int rows = this.getRows();
        if (withoutRuleSets != null) {
            for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
                final WorldObject[] objectsWithoutPrerequisites = WorldObjectList
                        .getAllWithoutPrerequisiteAndNotRequiredSubset(
                                withoutRuleSets, e);
                if (objectsWithoutPrerequisites != null
                        && objectsWithoutPrerequisites.length > 0) {
                    r = new RandomRange(0,
                            objectsWithoutPrerequisites.length - 1);
                    for (x = 0; x < columns; x++) {
                        for (y = 0; y < rows; y++) {
                            final WorldObject placeObj = objectsWithoutPrerequisites[r
                                    .generate()];
                            final boolean okay = placeObj.shouldGenerateObject(
                                    world, x, y, z, w, e);
                            if (okay) {
                                this.towerData[x][y][z][e] = objects
                                        .getNewInstanceByName(placeObj
                                                .getName());
                                placeObj.editorGenerateHook(x, y, z);
                            }
                        }
                    }
                }
            }
            // Pass 3...N
            int N = 1;
            for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
                WorldObject[] objectsWithPrerequisites = WorldObjectList
                        .getAllWithNthPrerequisiteSubset(withoutRuleSets, e, N);
                while (objectsWithPrerequisites != null) {
                    r = new RandomRange(0, objectsWithPrerequisites.length - 1);
                    for (x = 0; x < columns; x++) {
                        for (y = 0; y < rows; y++) {
                            final WorldObject placeObj = objectsWithPrerequisites[r
                                    .generate()];
                            final boolean okay = placeObj.shouldGenerateObject(
                                    world, x, y, z, w, e);
                            if (okay) {
                                this.towerData[x][y][z][e] = objects
                                        .getNewInstanceByName(placeObj
                                                .getName());
                                placeObj.editorGenerateHook(x, y, z);
                            }
                        }
                    }
                    N++;
                    objectsWithPrerequisites = WorldObjectList
                            .getAllWithNthPrerequisiteSubset(withoutRuleSets,
                                    e, N);
                }
            }
            // Pass N + 1
            for (int layer = 0; layer < WorldConstants.LAYER_COUNT; layer++) {
                final WorldObject[] requiredObjects = WorldObjectList
                        .getAllRequiredSubset(withoutRuleSets, layer);
                if (requiredObjects != null) {
                    final RandomRange row = new RandomRange(0,
                            this.getRows() - 1);
                    final RandomRange column = new RandomRange(0,
                            this.getColumns() - 1);
                    int randomRow, randomColumn;
                    for (x = 0; x < requiredObjects.length; x++) {
                        randomRow = row.generate();
                        randomColumn = column.generate();
                        final WorldObject currObj = requiredObjects[x];
                        final int min = currObj
                                .getMinimumRequiredQuantity(world);
                        int max = currObj.getMaximumRequiredQuantity(world);
                        if (max == RandomGenerationRule.NO_LIMIT) {
                            // Maximum undefined, so define it relative to this
                            // world
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
                            if (currObj.shouldGenerateObject(world, randomRow,
                                    randomColumn, z, w, layer)) {
                                this.towerData[x][y][z][e] = objects
                                        .getNewInstanceByName(currObj.getName());
                                currObj.editorGenerateHook(x, y, z);
                            } else {
                                while (!currObj.shouldGenerateObject(world,
                                        randomRow, randomColumn, z, w, layer)) {
                                    randomRow = row.generate();
                                    randomColumn = column.generate();
                                }
                                this.towerData[x][y][z][e] = objects
                                        .getNewInstanceByName(currObj.getName());
                                currObj.editorGenerateHook(x, y, z);
                            }
                        }
                    }
                }
            }
        }
        if (withRuleSets != null) {
            // Pass N + 2
            for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
                final WorldObject[] objectsWithoutPrerequisites = WorldObjectList
                        .getAllWithoutPrerequisiteAndNotRequiredSubset(
                                withRuleSets, e);
                if (objectsWithoutPrerequisites != null) {
                    r = new RandomRange(0,
                            objectsWithoutPrerequisites.length - 1);
                    for (x = 0; x < columns; x++) {
                        for (y = 0; y < rows; y++) {
                            final WorldObject placeObj = objectsWithoutPrerequisites[r
                                    .generate()];
                            final boolean okay = placeObj.getRuleSet()
                                    .shouldGenerateObject(world, x, y, z, w, e);
                            if (okay) {
                                this.towerData[x][y][z][e] = objects
                                        .getNewInstanceByName(placeObj
                                                .getName());
                                placeObj.editorGenerateHook(x, y, z);
                            }
                        }
                    }
                }
            }
            // Pass N + 3...2N + 3
            int N = 1;
            for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
                WorldObject[] objectsWithPrerequisites = WorldObjectList
                        .getAllWithNthPrerequisiteSubset(withRuleSets, e, N);
                while (objectsWithPrerequisites != null) {
                    r = new RandomRange(0, objectsWithPrerequisites.length - 1);
                    for (x = 0; x < columns; x++) {
                        for (y = 0; y < rows; y++) {
                            final WorldObject placeObj = objectsWithPrerequisites[r
                                    .generate()];
                            final boolean okay = placeObj.getRuleSet()
                                    .shouldGenerateObject(world, x, y, z, w, e);
                            if (okay) {
                                this.towerData[x][y][z][e] = objects
                                        .getNewInstanceByName(placeObj
                                                .getName());
                                placeObj.editorGenerateHook(x, y, z);
                            }
                        }
                    }
                    N++;
                    objectsWithPrerequisites = WorldObjectList
                            .getAllWithNthPrerequisiteSubset(withRuleSets, e, N);
                }
            }
            // Pass 2N + 4
            for (int layer = 0; layer < WorldConstants.LAYER_COUNT; layer++) {
                final WorldObject[] requiredObjects = WorldObjectList
                        .getAllRequiredSubset(withRuleSets, layer);
                if (requiredObjects != null) {
                    final RandomRange row = new RandomRange(0,
                            this.getRows() - 1);
                    final RandomRange column = new RandomRange(0,
                            this.getColumns() - 1);
                    int randomRow, randomColumn;
                    for (x = 0; x < requiredObjects.length; x++) {
                        randomRow = row.generate();
                        randomColumn = column.generate();
                        final WorldObject currObj = requiredObjects[x];
                        final int min = currObj.getRuleSet()
                                .getMinimumRequiredQuantity(world);
                        int max = currObj.getRuleSet()
                                .getMaximumRequiredQuantity(world);
                        if (max == RandomGenerationRule.NO_LIMIT) {
                            // Maximum undefined, so define it relative to this
                            // world
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
                            if (currObj.getRuleSet()
                                    .shouldGenerateObject(world, randomRow,
                                            randomColumn, z, w, layer)) {
                                this.towerData[x][y][z][e] = objects
                                        .getNewInstanceByName(currObj.getName());
                                currObj.editorGenerateHook(x, y, z);
                            } else {
                                while (!currObj.getRuleSet()
                                        .shouldGenerateObject(world, randomRow,
                                                randomColumn, z, w, layer)) {
                                    randomRow = row.generate();
                                    randomColumn = column.generate();
                                }
                                this.towerData[x][y][z][e] = objects
                                        .getNewInstanceByName(currObj.getName());
                                currObj.editorGenerateHook(x, y, z);
                            }
                        }
                    }
                }
            }
        }
    }

    private void fillNulls() {
        final WorldObject bottom = Worldz.getApplication().getPrefsManager()
                .getEditorDefaultFill();
        final WorldObject top = new Empty();
        int x, y, z, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
                        if (this.towerData[x][y][z][e] == null) {
                            if (e == WorldConstants.LAYER_GROUND) {
                                this.towerData[x][y][z][e] = bottom;
                            } else {
                                this.towerData[x][y][z][e] = top;
                            }
                        }
                    }
                }
            }
        }
    }

    public void save() {
        int x, y, z, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
                        this.savedTowerState.setDataCell(
                                this.towerData[x][y][z][e], x, y, z, e);
                    }
                }
            }
        }
    }

    public void restore() {
        int x, y, z, e;
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
                        this.towerData[x][y][z][e] = this.savedTowerState
                                .getDataCell(x, y, z, e);
                    }
                }
            }
        }
    }

    public void updateMovingBlockPosition(final int move, final int xLoc,
            final int yLoc, final MovingBlock block) {
        final int[] dirMove = DirectionResolver
                .unresolveRelativeDirection(move);
        final int zLoc = Worldz.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationZ();
        try {
            final WorldObject there = this.getCell(xLoc + dirMove[0], yLoc
                    + dirMove[1], zLoc, WorldConstants.LAYER_OBJECT);
            final WorldObject ground = this.getCell(xLoc + dirMove[0], yLoc
                    + dirMove[1], zLoc, WorldConstants.LAYER_GROUND);
            if (!there.isSolid() && !there.getName().equals("Player")) {
                this.setCell(block.getSavedObject(), xLoc, yLoc, zLoc,
                        WorldConstants.LAYER_OBJECT);
                // Move the block
                block.setSavedObject(there);
                this.setCell(block, xLoc + dirMove[0], yLoc + dirMove[1], zLoc,
                        WorldConstants.LAYER_OBJECT);
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

    public void warpObject(final WorldObject mo, final int x, final int y,
            final int z, final int l) {
        final RandomRange row = new RandomRange(0, this.getRows() - 1);
        final RandomRange column = new RandomRange(0, this.getColumns() - 1);
        int randomRow, randomColumn;
        randomRow = row.generate();
        randomColumn = column.generate();
        WorldObject currObj = this.getCell(randomRow, randomColumn, z,
                WorldConstants.LAYER_OBJECT);
        if (!currObj.isSolid()) {
            this.setCell(new Empty(), x, y, z, l);
            this.setCell(mo, randomRow, randomColumn, z,
                    WorldConstants.LAYER_OBJECT);
        } else {
            while (currObj.isSolid()) {
                randomRow = row.generate();
                randomColumn = column.generate();
                currObj = this.getCell(randomRow, randomColumn, z,
                        WorldConstants.LAYER_OBJECT);
            }
            this.setCell(new Empty(), x, y, z, l);
            this.setCell(mo, randomRow, randomColumn, z,
                    WorldConstants.LAYER_OBJECT);
        }
    }

    private static WorldObject[][][] shuffleObjects(
            final WorldObject[][][] preShuffle, final int r) {
        final WorldObject[][][] postShuffle = new WorldObject[2 * r + 1][2 * r + 1][WorldConstants.LAYER_COUNT];
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
                for (int z = 0; z < WorldConstants.LAYER_COUNT; z++) {
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

    private int normalizeRow(int fR) {
        int row = fR;
        if (row < 0) {
            row += this.getRows();
            while (row < 0) {
                row += this.getRows();
            }
        } else if (row > this.getRows() - 1) {
            row -= this.getRows();
            while (row > this.getRows() - 1) {
                row -= this.getRows();
            }
        }
        return row;
    }

    private int normalizeColumn(int fC) {
        int column = fC;
        if (column < 0) {
            column += this.getColumns();
            while (column < 0) {
                column += this.getColumns();
            }
        } else if (column > this.getColumns() - 1) {
            column -= this.getColumns();
            while (column > this.getColumns() - 1) {
                column -= this.getColumns();
            }
        }
        return column;
    }

    private int normalizeFloor(int fF) {
        int floor = fF;
        if (floor < 0) {
            floor += this.getFloors();
            while (floor < 0) {
                floor += this.getFloors();
            }
        } else if (floor > this.getFloors() - 1) {
            floor -= this.getFloors();
            while (floor > this.getFloors() - 1) {
                floor -= this.getFloors();
            }
        }
        return floor;
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

    public void writeLayeredTower(final DataWriter writer) throws IOException {
        int x, y, z, e;
        writer.writeInt(this.getColumns());
        writer.writeInt(this.getRows());
        writer.writeInt(this.getFloors());
        for (x = 0; x < this.getColumns(); x++) {
            for (y = 0; y < this.getRows(); y++) {
                for (z = 0; z < this.getFloors(); z++) {
                    for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
                        this.towerData[x][y][z][e].writeWorldObject(writer);
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
        writer.writeInt(this.poisonPower);
    }

    public static LayeredTower readLayeredTower(final DataReader reader)
            throws IOException {
        int x, y, z, e, worldSizeX, worldSizeY, worldSizeZ;
        worldSizeX = reader.readInt();
        worldSizeY = reader.readInt();
        worldSizeZ = reader.readInt();
        final LayeredTower lt = new LayeredTower(worldSizeX, worldSizeY,
                worldSizeZ);
        for (x = 0; x < lt.getColumns(); x++) {
            for (y = 0; y < lt.getRows(); y++) {
                for (z = 0; z < lt.getFloors(); z++) {
                    for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
                        lt.towerData[x][y][z][e] = Worldz
                                .getApplication()
                                .getObjects()
                                .readWorldObject(reader,
                                        FormatConstants.WORLD_FORMAT_1);
                        if (lt.towerData[x][y][z][e] == null) {
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
        lt.poisonPower = reader.readInt();
        return lt;
    }

    public void writeSavedTowerState(final DataWriter writer)
            throws IOException {
        this.savedTowerState.writeSavedTowerState(writer);
    }

    public void readSavedTowerState(final DataReader reader,
            final int formatVersion) throws IOException {
        this.savedTowerState = SavedTowerState.readSavedTowerState(reader,
                formatVersion);
    }
}
