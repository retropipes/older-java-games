/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon;

import java.io.File;
import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.objects.Empty;
import com.puttysoftware.dungeondiver4.dungeon.objects.MonsterObject;
import com.puttysoftware.dungeondiver4.dungeon.objects.MovingBlock;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectList;
import com.puttysoftware.dungeondiver4.prefs.PreferencesManager;
import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.xio.DirectoryUtilities;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class Dungeon implements DungeonConstants {
    // Properties
    private LayeredTower dungeonData;
    private LayeredTower clipboard;
    private int startW;
    private int locW;
    private int saveW;
    private int levelCount;
    private int activeLevel;
    private String dungeonTitle;
    private String dungeonStartMessage;
    private String dungeonEndMessage;
    private String basePath;
    private PrefixIO prefixHandler;
    private SuffixIO suffixHandler;
    private int[] savedStart;
    private static final int MIN_LEVELS = 1;
    private static final int MAX_LEVELS = Integer.MAX_VALUE;
    private static final String DUNGEON_PREFIX_GROUP = "prefix";
    private static final String DUNGEON_SETTINGS_GROUP = "settings";
    private static final String DUNGEON_SUFFIX_GROUP = "suffix";

    // Constructors
    public Dungeon() {
        this.dungeonData = null;
        this.clipboard = null;
        this.levelCount = 0;
        this.startW = 0;
        this.locW = 0;
        this.saveW = 0;
        this.activeLevel = 0;
        this.dungeonTitle = "Untitled Dungeon";
        this.dungeonStartMessage = "Let's Solve The Dungeon!";
        this.dungeonEndMessage = "Dungeon Solved!";
        this.savedStart = new int[4];
        long random = new RandomRange(0, Long.MAX_VALUE).generateLong();
        String randomID = Long.toHexString(random);
        this.basePath = System.getProperty("java.io.tmpdir") + File.separator
                + "DungeonDiver4" + File.separator + randomID + ".dungeon";
        File base = new File(this.basePath);
        boolean success = base.mkdirs();
        if (!success) {
            CommonDialogs.showErrorDialog(
                    "Dungeon temporary folder creation failed!",
                    "DungeonDiver4");
        }
    }

    // Static methods
    public static String getDungeonTempFolder() {
        return System.getProperty("java.io.tmpdir") + File.separator
                + "DungeonDiver4";
    }

    public static int getMinLevels() {
        return Dungeon.MIN_LEVELS;
    }

    public static int getMaxLevels() {
        return Dungeon.MAX_LEVELS;
    }

    public static int getMinFloors() {
        return LayeredTower.getMinFloors();
    }

    public static int getMaxFloors() {
        return LayeredTower.getMaxFloors();
    }

    public static int getMinColumns() {
        return LayeredTower.getMinColumns();
    }

    public static int getMaxColumns() {
        return LayeredTower.getMaxColumns();
    }

    public static int getMinRows() {
        return LayeredTower.getMinRows();
    }

    public static int getMaxRows() {
        return LayeredTower.getMaxRows();
    }

    // Methods
    public static Dungeon getTemporaryBattleCopy() {
        Dungeon temp = new Dungeon();
        temp.addLevel(DungeonDiver4.getBattleDungeonSize(),
                DungeonDiver4.getBattleDungeonSize(), 1);
        DungeonObjectList list = new DungeonObjectList();
        AbstractDungeonObject[] glo = list.getAllGroundLayerObjects();
        RandomRange gen = new RandomRange(0, glo.length - 1);
        AbstractDungeonObject rand = glo[gen.generate()];
        if (PreferencesManager.getRandomBattleEnvironment()) {
            temp.fillLevelRandomlyInBattle(rand, new Empty());
        } else {
            temp.fillLevel(rand, new Empty());
        }
        return temp;
    }

    public void updateMonsterPosition(final int move, final int xLoc,
            final int yLoc, final MonsterObject monster) {
        this.dungeonData.updateMonsterPosition(move, xLoc, yLoc, monster);
    }

    public void postBattle(MonsterObject m, final int xLoc, final int yLoc,
            boolean player) {
        this.dungeonData.postBattle(m, xLoc, yLoc, player);
    }

    public void radialScanTimerAction(final int x, final int y, final int z,
            final int l, final int r, final String targetName,
            final int timerMod) {
        this.dungeonData.radialScanTimerAction(x, y, z, l, r, targetName,
                timerMod);
    }

    public void radialScanKillMonsters(final int x, final int y, final int z,
            final int l, final int r) {
        this.dungeonData.radialScanKillMonsters(x, y, z, l, r);
    }

    public String getBasePath() {
        return this.basePath;
    }

    public void setPrefixHandler(PrefixIO xph) {
        this.prefixHandler = xph;
    }

    public void setSuffixHandler(SuffixIO xsh) {
        this.suffixHandler = xsh;
    }

    public String getDungeonTitle() {
        return this.dungeonTitle;
    }

    public void setDungeonTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null!");
        }
        this.dungeonTitle = title;
    }

    public String getDungeonStartMessage() {
        return this.dungeonStartMessage;
    }

    public void setDungeonStartMessage(String msg) {
        if (msg == null) {
            throw new IllegalArgumentException("Message cannot be null!");
        }
        this.dungeonStartMessage = msg;
    }

    public String getDungeonEndMessage() {
        return this.dungeonEndMessage;
    }

    public void setDungeonEndMessage(String msg) {
        if (msg == null) {
            throw new IllegalArgumentException("Message cannot be null!");
        }
        this.dungeonEndMessage = msg;
    }

    public String getLevelTitle() {
        return this.dungeonData.getLevelTitle();
    }

    public void setLevelTitle(String title) {
        this.dungeonData.setLevelTitle(title);
    }

    public String getLevelStartMessage() {
        return this.dungeonData.getLevelStartMessage();
    }

    public void setLevelStartMessage(String msg) {
        this.dungeonData.setLevelStartMessage(msg);
    }

    public String getLevelEndMessage() {
        return this.dungeonData.getLevelEndMessage();
    }

    public void setLevelEndMessage(String msg) {
        this.dungeonData.setLevelEndMessage(msg);
    }

    public int getPoisonPower() {
        return this.dungeonData.getPoisonPower();
    }

    public void setPoisonPower(int pp) {
        this.dungeonData.setPoisonPower(pp);
    }

    public void doPoisonousAmulet() {
        this.dungeonData.doPoisonousAmulet();
    }

    public void doCounterpoisonAmulet() {
        this.dungeonData.doCounterpoisonAmulet();
    }

    public void undoPoisonAmulets() {
        this.dungeonData.undoPoisonAmulets();
    }

    public String getPoisonString() {
        return this.dungeonData.getPoisonString();
    }

    public static int getMaxPoisonPower() {
        return LayeredTower.getMaxPoisonPower();
    }

    public int getVisionRadius() {
        return this.dungeonData.getVisionRadius();
    }

    public void setVisionRadius(int vr) {
        this.dungeonData.setVisionRadius(vr);
    }

    public void masterTrapTrigger() {
        this.dungeonData.masterTrapTrigger();
    }

    public void tickTimers(final int floor) {
        this.dungeonData.tickTimers(floor);
    }

    public boolean rotateRadiusClockwise(final int x, final int y, final int z,
            final int r) {
        return this.dungeonData.rotateRadiusClockwise(x, y, z, r);
    }

    public boolean rotateRadiusCounterclockwise(final int x, final int y,
            final int z, final int r) {
        return this.dungeonData.rotateRadiusCounterclockwise(x, y, z, r);
    }

    public boolean radialScan(final int cx, final int cy, final int r,
            final int tx, final int ty) {
        return this.dungeonData.radialScan(cx, cy, r, tx, ty);
    }

    public void radialScanFreezeObjects(final int x, final int y, final int z,
            final int r) {
        this.dungeonData.radialScanFreezeObjects(x, y, z, r);
    }

    public void radialScanFreezeGround(final int x, final int y, final int z,
            final int r) {
        this.dungeonData.radialScanFreezeGround(x, y, z, r);
    }

    public void radialScanEnrageObjects(final int x, final int y, final int z,
            final int r) {
        this.dungeonData.radialScanEnrageObjects(x, y, z, r);
    }

    public void radialScanBurnGround(final int x, final int y, final int z,
            final int r) {
        this.dungeonData.radialScanBurnGround(x, y, z, r);
    }

    public void radialScanPoisonObjects(final int x, final int y, final int z,
            final int r) {
        this.dungeonData.radialScanPoisonObjects(x, y, z, r);
    }

    public void radialScanPoisonGround(final int x, final int y, final int z,
            final int r) {
        this.dungeonData.radialScanPoisonGround(x, y, z, r);
    }

    public void radialScanShockObjects(final int x, final int y, final int z,
            final int r) {
        this.dungeonData.radialScanShockObjects(x, y, z, r);
    }

    public void radialScanShockGround(final int x, final int y, final int z,
            final int r) {
        this.dungeonData.radialScanShockGround(x, y, z, r);
    }

    public void radialScanWarpObjects(final int x, final int y, final int z,
            final int l, final int r) {
        this.dungeonData.radialScanWarpObjects(x, y, z, l, r);
    }

    public void radialScanShuffleObjects(final int x, final int y, final int z,
            final int r) {
        this.dungeonData.radialScanShuffleObjects(x, y, z, r);
    }

    public void radialScanQuakeBomb(final int x, final int y, final int z,
            final int r) {
        this.dungeonData.radialScanQuakeBomb(x, y, z, r);
    }

    public void setVisionRadiusToMaximum() {
        this.dungeonData.setVisionRadiusToMaximum();
    }

    public void setVisionRadiusToMinimum() {
        this.dungeonData.setVisionRadiusToMinimum();
    }

    public void incrementVisionRadius() {
        this.dungeonData.incrementVisionRadius();
    }

    public void decrementVisionRadius() {
        this.dungeonData.decrementVisionRadius();
    }

    public void updateMovingBlockPosition(final int move, final int xLoc,
            final int yLoc, final MovingBlock block) {
        this.dungeonData.updateMovingBlockPosition(move, xLoc, yLoc, block);
    }

    public void warpObject(AbstractDungeonObject mo, int x, int y, int z, int l) {
        this.dungeonData.warpObject(mo, x, y, z, l);
    }

    public int getExploreRadius() {
        return this.dungeonData.getExploreRadius();
    }

    public void setExploreRadius(int newER) {
        this.dungeonData.setExploreRadius(newER);
    }

    public int getVisionMode() {
        return this.dungeonData.getVisionMode();
    }

    public void setVisionMode(int newVM) {
        this.dungeonData.setVisionMode(newVM);
    }

    public void resetVisibleSquares() {
        this.dungeonData.resetVisibleSquares();
    }

    public void updateVisibleSquares(int xp, int yp, int zp) {
        this.dungeonData.updateVisibleSquares(xp, yp, zp);
    }

    public int getActiveLevelNumber() {
        return this.activeLevel;
    }

    public void switchLevel(int level) {
        this.switchLevelInternal(level);
    }

    public void switchLevelOffset(int level) {
        this.switchLevelInternal(this.activeLevel + level);
    }

    private void switchLevelInternal(int level) {
        if (this.activeLevel != level) {
            if (this.dungeonData != null) {
                try (XDataWriter writer = this.getLevelWriter()) {
                    // Save old level
                    this.writeDungeonLevel(writer);
                } catch (IOException io) {
                    // Ignore
                }
            }
            this.activeLevel = level;
            try (XDataReader reader = this.getLevelReader()) {
                // Load new level
                this.readDungeonLevel(reader);
            } catch (IOException io) {
                // Ignore
            }
        }
    }

    public void cutLevel() {
        if (this.levelCount > 1) {
            this.clipboard = this.dungeonData;
            this.removeLevel();
        }
    }

    public void copyLevel() {
        this.clipboard = this.dungeonData.clone();
    }

    public void pasteLevel() {
        if (this.clipboard != null) {
            this.dungeonData = this.clipboard.clone();
            DungeonDiver4.getApplication().getDungeonManager().setDirty(true);
        }
    }

    public boolean isPasteBlocked() {
        return this.clipboard == null;
    }

    public boolean isCutBlocked() {
        return this.levelCount <= 1;
    }

    public boolean insertLevelFromClipboard() {
        if (this.levelCount < Dungeon.MAX_LEVELS) {
            this.dungeonData = this.clipboard.clone();
            this.levelCount++;
            return true;
        } else {
            return false;
        }
    }

    public boolean addLevel(final int rows, final int cols, final int floors) {
        if (this.levelCount < Dungeon.MAX_LEVELS) {
            if (this.dungeonData != null) {
                try (XDataWriter writer = this.getLevelWriter()) {
                    // Save old level
                    this.writeDungeonLevel(writer);
                } catch (IOException io) {
                    // Ignore
                }
            }
            this.dungeonData = new LayeredTower(rows, cols, floors);
            this.levelCount++;
            this.activeLevel = this.levelCount - 1;
            return true;
        } else {
            return false;
        }
    }

    public boolean removeLevel() {
        if (this.levelCount > 1) {
            if (this.activeLevel >= 1 && this.activeLevel <= this.levelCount) {
                this.dungeonData = null;
                // Delete file corresponding to current level
                boolean delSuccess = this.getLevelFile(this.activeLevel)
                        .delete();
                if (!delSuccess) {
                    return false;
                }
                // Shift all higher-numbered levels down
                for (int x = this.activeLevel; x < this.levelCount - 1; x++) {
                    File sourceLocation = this.getLevelFile(x + 1);
                    File targetLocation = this.getLevelFile(x);
                    try {
                        DirectoryUtilities.moveFile(sourceLocation,
                                targetLocation);
                    } catch (IOException io) {
                        // Ignore
                    }
                }
                // Load level 1
                this.switchLevel(0);
                this.levelCount--;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean hasNote(int x, int y, int z) {
        return this.dungeonData.hasNote(x, y, z);
    }

    public void createNote(int x, int y, int z) {
        this.dungeonData.createNote(x, y, z);
    }

    public DungeonNote getNote(int x, int y, int z) {
        return this.dungeonData.getNote(x, y, z);
    }

    public AbstractDungeonObject getCell(final int row, final int col,
            final int floor, final int extra) {
        return this.dungeonData.getCell(row, col, floor, extra);
    }

    public AbstractDungeonObject getBattleCell(final int row, final int col) {
        return this.dungeonData.getCell(row, col, 0,
                DungeonConstants.LAYER_OBJECT);
    }

    public AbstractDungeonObject getBattleGround(final int row, final int col) {
        return this.dungeonData.getCell(row, col, 0,
                DungeonConstants.LAYER_GROUND);
    }

    public int getStartRow() {
        return this.dungeonData.getStartRow();
    }

    public int getStartColumn() {
        return this.dungeonData.getStartColumn();
    }

    public int getPlayerLocationX() {
        return this.dungeonData.getPlayerRow();
    }

    public int getPlayerLocationY() {
        return this.dungeonData.getPlayerColumn();
    }

    public int getPlayerLocationZ() {
        return this.dungeonData.getPlayerFloor();
    }

    public int getPlayerLocationW() {
        return this.locW;
    }

    public int getStartLevel() {
        return this.startW;
    }

    public int getRows() {
        return this.dungeonData.getRows();
    }

    public int getColumns() {
        return this.dungeonData.getColumns();
    }

    public int getFloors() {
        return this.dungeonData.getFloors();
    }

    public int getLevels() {
        return this.levelCount;
    }

    public boolean doesPlayerExist() {
        return this.dungeonData.doesPlayerExist();
    }

    public void findAllMatchingObjectsAndDecay(final AbstractDungeonObject o) {
        this.dungeonData.findAllMatchingObjectsAndDecay(o);
    }

    public void findAllObjectPairsAndSwap(final AbstractDungeonObject o1,
            final AbstractDungeonObject o2) {
        this.dungeonData.findAllObjectPairsAndSwap(o1, o2);
    }

    public void resize(final int x, final int y, final int z) {
        this.dungeonData.resize(x, y, z);
    }

    public boolean isSquareVisible(int x1, int y1, int x2, int y2) {
        return this.dungeonData.isSquareVisible(x1, y1, x2, y2);
    }

    public void setBattleCell(final AbstractDungeonObject mo, final int row,
            final int col) {
        this.dungeonData
                .setCell(mo, row, col, 0, DungeonConstants.LAYER_OBJECT);
    }

    public void setCell(final AbstractDungeonObject mo, final int row,
            final int col, final int floor, final int extra) {
        this.dungeonData.setCell(mo, row, col, floor, extra);
    }

    public void setStartRow(final int newStartRow) {
        this.dungeonData.setStartRow(newStartRow);
    }

    public void setStartColumn(final int newStartColumn) {
        this.dungeonData.setStartColumn(newStartColumn);
    }

    public void setStartFloor(final int newStartFloor) {
        this.dungeonData.setStartFloor(newStartFloor);
    }

    public void setStartLevel(final int newStartLevel) {
        this.startW = newStartLevel;
    }

    public void savePlayerLocation() {
        this.saveW = this.locW;
        this.dungeonData.savePlayerLocation();
    }

    public void restorePlayerLocation() {
        this.locW = this.saveW;
        this.dungeonData.restorePlayerLocation();
    }

    public void setPlayerToStart() {
        this.dungeonData.setPlayerToStart();
    }

    public void setPlayerLocationX(final int newPlayerRow) {
        this.dungeonData.setPlayerRow(newPlayerRow);
    }

    public void setPlayerLocationY(final int newPlayerColumn) {
        this.dungeonData.setPlayerColumn(newPlayerColumn);
    }

    public void setPlayerLocationZ(final int newPlayerFloor) {
        this.dungeonData.setPlayerFloor(newPlayerFloor);
    }

    public void setPlayerLocationW(final int newPlayerLevel) {
        this.locW = newPlayerLevel;
    }

    public void offsetPlayerLocationX(final int newPlayerRow) {
        this.dungeonData.offsetPlayerRow(newPlayerRow);
    }

    public void offsetPlayerLocationY(final int newPlayerColumn) {
        this.dungeonData.offsetPlayerColumn(newPlayerColumn);
    }

    public void fill() {
        AbstractDungeonObject bottom = PreferencesManager
                .getEditorDefaultFill();
        AbstractDungeonObject top = new Empty();
        this.dungeonData.fill(bottom, top);
    }

    public void fillFloor(int floor) {
        AbstractDungeonObject bottom = PreferencesManager
                .getEditorDefaultFill();
        AbstractDungeonObject top = new Empty();
        this.dungeonData.fillFloor(bottom, top, floor);
    }

    public void fillLevel(AbstractDungeonObject bottom,
            AbstractDungeonObject top) {
        this.dungeonData.fill(bottom, top);
    }

    public void fillLevelRandomly() {
        this.dungeonData.fillRandomly(this, this.activeLevel);
    }

    public void fillFloorRandomly(int z) {
        this.dungeonData.fillFloorRandomly(this, z, this.activeLevel);
    }

    public void fillLevelRandomlyCustom() {
        this.dungeonData.fillRandomlyCustom(this, this.activeLevel);
    }

    public void fillFloorRandomlyCustom(int z) {
        this.dungeonData.fillFloorRandomlyCustom(this, z, this.activeLevel);
    }

    public void fillLayer(int layer) {
        AbstractDungeonObject fillWith = PreferencesManager
                .getEditorDefaultFill(layer);
        this.dungeonData.fillLayer(fillWith, layer);
    }

    public void fillFloorAndLayer(int floor, int layer) {
        AbstractDungeonObject fillWith = PreferencesManager
                .getEditorDefaultFill(layer);
        this.dungeonData.fillFloorAndLayer(fillWith, floor, layer);
    }

    public void fillLevelAndLayerRandomly(int layer) {
        this.dungeonData.fillLayerRandomly(this, this.activeLevel, layer);
    }

    public void fillFloorAndLayerRandomly(int z, int layer) {
        this.dungeonData.fillFloorAndLayerRandomly(this, z, this.activeLevel,
                layer);
    }

    public void fillLevelAndLayerRandomlyCustom(int layer) {
        this.dungeonData.fillLayerRandomlyCustom(this, this.activeLevel, layer);
    }

    public void fillFloorAndLayerRandomlyCustom(int z, int layer) {
        this.dungeonData.fillFloorAndLayerRandomlyCustom(this, z,
                this.activeLevel, layer);
    }

    public void generateStart() {
        this.dungeonData.generateStart();
    }

    private void fillLevelRandomlyInBattle(
            final AbstractDungeonObject pass1FillBottom,
            final AbstractDungeonObject pass1FillTop) {
        this.dungeonData.fillRandomlyInBattle(this, this.activeLevel,
                pass1FillBottom, pass1FillTop);
    }

    public void save() {
        this.dungeonData.save();
    }

    public void restore() {
        this.dungeonData.restore();
    }

    public void saveStart() {
        this.savedStart[0] = this.startW;
        this.savedStart[1] = this.dungeonData.getStartRow();
        this.savedStart[2] = this.dungeonData.getStartColumn();
        this.savedStart[3] = this.dungeonData.getStartFloor();
    }

    public void restoreStart() {
        this.startW = this.savedStart[0];
        this.dungeonData.setStartRow(this.savedStart[1]);
        this.dungeonData.setStartColumn(this.savedStart[2]);
        this.dungeonData.setStartFloor(this.savedStart[3]);
    }

    public void hotGround(int x, int y, int z) {
        this.dungeonData.hotGround(x, y, z);
    }

    public void enableHorizontalWraparound() {
        this.dungeonData.enableHorizontalWraparound();
    }

    public void disableHorizontalWraparound() {
        this.dungeonData.disableHorizontalWraparound();
    }

    public void enableVerticalWraparound() {
        this.dungeonData.enableVerticalWraparound();
    }

    public void disableVerticalWraparound() {
        this.dungeonData.disableVerticalWraparound();
    }

    public void enable3rdDimensionWraparound() {
        this.dungeonData.enable3rdDimensionWraparound();
    }

    public void disable3rdDimensionWraparound() {
        this.dungeonData.disable3rdDimensionWraparound();
    }

    public boolean isHorizontalWraparoundEnabled() {
        return this.dungeonData.isHorizontalWraparoundEnabled();
    }

    public boolean isVerticalWraparoundEnabled() {
        return this.dungeonData.isVerticalWraparoundEnabled();
    }

    public boolean is3rdDimensionWraparoundEnabled() {
        return this.dungeonData.is3rdDimensionWraparoundEnabled();
    }

    public Dungeon readDungeon() throws IOException {
        Dungeon m = new Dungeon();
        // Attach handlers
        m.setPrefixHandler(this.prefixHandler);
        m.setSuffixHandler(this.suffixHandler);
        // Make base paths the same
        m.basePath = this.basePath;
        int version = 0;
        // Create metafile reader
        try (XDataReader metaReader = new XDataReader(m.basePath
                + File.separator + "metafile.xml", "dungeon")) {
            // Read metafile
            version = m.readDungeonMetafile(metaReader);
        } catch (IOException ioe) {
            throw ioe;
        }
        // Create data reader
        try (XDataReader dataReader = m.getLevelReader()) {
            // Read data
            m.readDungeonLevel(dataReader, version);
            return m;
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    private XDataReader getLevelReader() throws IOException {
        return new XDataReader(this.basePath + File.separator + "level"
                + this.activeLevel + ".xml", "level");
    }

    private int readDungeonMetafile(XDataReader reader) throws IOException {
        int ver = FormatConstants.DUNGEON_FORMAT_1;
        reader.readOpeningGroup(DUNGEON_PREFIX_GROUP);
        if (this.prefixHandler != null) {
            ver = this.prefixHandler.readPrefix(reader);
        }
        reader.readClosingGroup(DUNGEON_PREFIX_GROUP);
        reader.readOpeningGroup(DUNGEON_SETTINGS_GROUP);
        this.levelCount = reader.readInt();
        this.startW = reader.readInt();
        this.dungeonTitle = reader.readString();
        this.dungeonStartMessage = reader.readString();
        this.dungeonEndMessage = reader.readString();
        this.locW = reader.readInt();
        this.saveW = reader.readInt();
        this.activeLevel = reader.readInt();
        for (int y = 0; y < 4; y++) {
            this.savedStart[y] = reader.readInt();
        }
        reader.readClosingGroup(DUNGEON_SETTINGS_GROUP);
        reader.readOpeningGroup(DUNGEON_SUFFIX_GROUP);
        if (this.suffixHandler != null) {
            this.suffixHandler.readSuffix(reader, ver);
        }
        reader.readClosingGroup(DUNGEON_SUFFIX_GROUP);
        return ver;
    }

    private void readDungeonLevel(XDataReader reader) throws IOException {
        this.readDungeonLevel(reader, FormatConstants.DUNGEON_FORMAT_LATEST);
    }

    private void readDungeonLevel(XDataReader reader, int formatVersion)
            throws IOException {
        if (formatVersion == FormatConstants.DUNGEON_FORMAT_1) {
            this.dungeonData = LayeredTower.readLayeredTowerV1(reader);
            this.dungeonData.readSavedTowerState(reader, formatVersion);
        } else {
            throw new IOException("Unknown dungeon format version!");
        }
    }

    private File getLevelFile(int level) {
        return new File(this.basePath + File.separator + level + ".level");
    }

    public void writeDungeon() throws IOException {
        try {
            // Create metafile writer
            try (XDataWriter metaWriter = new XDataWriter(this.basePath
                    + File.separator + "metafile.xml", "dungeon")) {
                // Write metafile
                this.writeDungeonMetafile(metaWriter);
            }
            // Create data writer
            try (XDataWriter dataWriter = this.getLevelWriter()) {
                // Write data
                this.writeDungeonLevel(dataWriter);
            }
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    private XDataWriter getLevelWriter() throws IOException {
        return new XDataWriter(this.basePath + File.separator + "level"
                + this.activeLevel + ".xml", "level");
    }

    private void writeDungeonMetafile(XDataWriter writer) throws IOException {
        writer.writeOpeningGroup(DUNGEON_PREFIX_GROUP);
        if (this.prefixHandler != null) {
            this.prefixHandler.writePrefix(writer);
        }
        writer.writeClosingGroup(DUNGEON_PREFIX_GROUP);
        writer.writeOpeningGroup(DUNGEON_SETTINGS_GROUP);
        writer.writeInt(this.levelCount);
        writer.writeInt(this.startW);
        writer.writeString(this.dungeonTitle);
        writer.writeString(this.dungeonStartMessage);
        writer.writeString(this.dungeonEndMessage);
        writer.writeInt(this.locW);
        writer.writeInt(this.saveW);
        writer.writeInt(this.activeLevel);
        for (int y = 0; y < 4; y++) {
            writer.writeInt(this.savedStart[y]);
        }
        writer.writeClosingGroup(DUNGEON_SETTINGS_GROUP);
        writer.writeOpeningGroup(DUNGEON_SUFFIX_GROUP);
        if (this.suffixHandler != null) {
            this.suffixHandler.writeSuffix(writer);
        }
        writer.writeClosingGroup(DUNGEON_SUFFIX_GROUP);
    }

    private void writeDungeonLevel(XDataWriter writer) throws IOException {
        // Write the level
        this.dungeonData.writeLayeredTower(writer);
        this.dungeonData.writeSavedTowerState(writer);
    }
}
