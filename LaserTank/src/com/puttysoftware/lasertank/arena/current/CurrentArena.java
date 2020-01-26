/*  LaserTank: An Arena-Solving Game
 Copyright (C) 2008-2013 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.lasertank.arena.current;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.puttysoftware.lasertank.LaserTank;
import com.puttysoftware.lasertank.arena.AbstractArena;
import com.puttysoftware.lasertank.arena.AbstractArenaData;
import com.puttysoftware.lasertank.arena.AbstractPrefixIO;
import com.puttysoftware.lasertank.arena.AbstractSuffixIO;
import com.puttysoftware.lasertank.arena.HistoryStatus;
import com.puttysoftware.lasertank.arena.abstractobjects.AbstractArenaObject;
import com.puttysoftware.lasertank.arena.abstractobjects.AbstractButton;
import com.puttysoftware.lasertank.arena.abstractobjects.AbstractButtonDoor;
import com.puttysoftware.lasertank.arena.abstractobjects.AbstractCharacter;
import com.puttysoftware.lasertank.arena.abstractobjects.AbstractTunnel;
import com.puttysoftware.lasertank.prefs.PreferencesManager;
import com.puttysoftware.lasertank.stringmanagers.StringConstants;
import com.puttysoftware.lasertank.stringmanagers.StringLoader;
import com.puttysoftware.lasertank.utilities.DifficultyConstants;
import com.puttysoftware.lasertank.utilities.Direction;
import com.puttysoftware.lasertank.utilities.Extension;
import com.puttysoftware.lasertank.utilities.FormatConstants;
import com.puttysoftware.randomrange.RandomLongRange;
import com.puttysoftware.xio.DirectoryUtilities;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class CurrentArena extends AbstractArena {
    // Properties
    private CurrentArenaData arenaData;
    private CurrentArenaData clipboard;
    private LevelInfo infoClipboard;
    private int levelCount;
    private int activeLevel;
    private int activeEra;
    private String basePath;
    private AbstractPrefixIO prefixHandler;
    private AbstractSuffixIO suffixHandler;
    private String musicFilename;
    private boolean moveShootAllowed;
    private final ArrayList<LevelInfo> levelInfoData;
    private ArrayList<String> levelInfoList;

    // Constructors
    public CurrentArena() throws IOException {
        super();
        this.arenaData = null;
        this.clipboard = null;
        this.levelCount = 0;
        this.activeLevel = 0;
        this.activeEra = 0;
        this.prefixHandler = null;
        this.suffixHandler = null;
        this.musicFilename = "null";
        this.moveShootAllowed = false;
        this.levelInfoData = new ArrayList<>();
        this.levelInfoList = new ArrayList<>();
        final long random = new RandomLongRange(0, Long.MAX_VALUE).generate();
        final String randomID = Long.toHexString(random);
        this.basePath = System
                .getProperty(StringLoader.loadString(
                        StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_TEMP_DIR))
                + File.separator
                + StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_PROGRAM_NAME)
                + File.separator + randomID
                + StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_ARENA_FORMAT_FOLDER);
        final File base = new File(this.basePath);
        final boolean res = base.mkdirs();
        if (!res) {
            throw new IOException(
                    StringLoader.loadString(StringConstants.ERROR_STRINGS_FILE,
                            StringConstants.ERROR_STRING_TEMP_DIR));
        }
    }

    // Methods
    @Override
    public String getArenaTempMusicFolder() {
        return this.basePath + File.separator
                + StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_MUSIC_FOLDER)
                + File.separator;
    }

    @Override
    public boolean isMoveShootAllowed() {
        return this.isMoveShootAllowedGlobally()
                && this.isMoveShootAllowedThisLevel();
    }

    @Override
    public boolean isMoveShootAllowedGlobally() {
        return this.moveShootAllowed;
    }

    @Override
    public boolean isMoveShootAllowedThisLevel() {
        return this.levelInfoData.get(this.activeLevel).isMoveShootAllowed();
    }

    @Override
    public void setMoveShootAllowedGlobally(final boolean value) {
        this.moveShootAllowed = value;
    }

    @Override
    public void setMoveShootAllowedThisLevel(final boolean value) {
        this.levelInfoData.get(this.activeLevel).setMoveShootAllowed(value);
    }

    @Override
    public String getMusicFilename() {
        return this.musicFilename;
    }

    @Override
    public void setMusicFilename(final String newMusicFilename) {
        this.musicFilename = newMusicFilename;
    }

    @Override
    public String getName() {
        return this.levelInfoData.get(this.activeLevel).getName();
    }

    @Override
    public void setName(final String newName) {
        this.levelInfoData.get(this.activeLevel).setName(newName);
        this.levelInfoList.set(this.activeLevel,
                this.generateCurrentLevelInfo());
    }

    @Override
    public String getHint() {
        return this.levelInfoData.get(this.activeLevel).getHint();
    }

    @Override
    public void setHint(final String newHint) {
        this.levelInfoData.get(this.activeLevel).setHint(newHint);
    }

    @Override
    public String getAuthor() {
        return this.levelInfoData.get(this.activeLevel).getAuthor();
    }

    @Override
    public void setAuthor(final String newAuthor) {
        this.levelInfoData.get(this.activeLevel).setAuthor(newAuthor);
        this.levelInfoList.set(this.activeLevel,
                this.generateCurrentLevelInfo());
    }

    @Override
    public int getDifficulty() {
        return this.levelInfoData.get(this.activeLevel).getDifficulty();
    }

    @Override
    public void setDifficulty(final int newDifficulty) {
        this.levelInfoData.get(this.activeLevel).setDifficulty(newDifficulty);
        this.levelInfoList.set(this.activeLevel,
                this.generateCurrentLevelInfo());
    }

    @Override
    public String getBasePath() {
        return this.basePath;
    }

    @Override
    public void setPrefixHandler(final AbstractPrefixIO xph) {
        this.prefixHandler = xph;
    }

    @Override
    public void setSuffixHandler(final AbstractSuffixIO xsh) {
        this.suffixHandler = xsh;
    }

    @Override
    public int getActiveLevelNumber() {
        return this.activeLevel;
    }

    @Override
    public int getActiveEraNumber() {
        return this.activeEra;
    }

    @Override
    public String[] getLevelInfoList() {
        return this.levelInfoList
                .toArray(new String[this.levelInfoList.size()]);
    }

    @Override
    public void generateLevelInfoList() {
        final int saveLevel = this.getActiveLevelNumber();
        final ArrayList<String> tempStorage = new ArrayList<>();
        for (int x = 0; x < this.levelCount; x++) {
            this.switchLevel(x);
            tempStorage.add(this.generateCurrentLevelInfo());
        }
        this.switchLevel(saveLevel);
        this.levelInfoList = tempStorage;
    }

    private String generateCurrentLevelInfo() {
        final StringBuilder sb = new StringBuilder();
        sb.append(StringLoader.loadString(StringConstants.DIALOG_STRINGS_FILE,
                StringConstants.DIALOG_STRING_ARENA_LEVEL));
        sb.append(StringConstants.COMMON_STRING_SPACE);
        sb.append(this.getActiveLevelNumber() + 1);
        sb.append(StringConstants.COMMON_STRING_COLON
                + StringConstants.COMMON_STRING_SPACE);
        sb.append(this.getName().trim());
        sb.append(StringConstants.COMMON_STRING_SPACE);
        sb.append(StringLoader.loadString(StringConstants.DIALOG_STRINGS_FILE,
                StringConstants.DIALOG_STRING_ARENA_LEVEL_BY));
        sb.append(StringConstants.COMMON_STRING_SPACE);
        sb.append(this.getAuthor().trim());
        sb.append(StringConstants.COMMON_STRING_SPACE);
        sb.append(StringConstants.COMMON_STRING_OPEN_PARENTHESES);
        sb.append(CurrentArena
                .convertDifficultyNumberToName(this.getDifficulty()));
        sb.append(StringConstants.COMMON_STRING_CLOSE_PARENTHESES);
        return sb.toString();
    }

    private static String convertDifficultyNumberToName(final int number) {
        return DifficultyConstants.getDifficultyNames()[number - 1];
    }

    @Override
    public void switchLevel(final int level) {
        this.switchInternal(level, this.activeEra);
    }

    @Override
    public void switchLevelOffset(final int level) {
        this.switchInternal(this.activeLevel + level, this.activeEra);
    }

    @Override
    public void switchEra(final int era) {
        this.switchInternal(this.activeLevel, era);
    }

    @Override
    public void switchEraOffset(final int era) {
        this.switchInternal(this.activeLevel, this.activeEra + era);
    }

    @Override
    protected void switchInternal(final int level, final int era) {
        if (this.activeLevel != level || this.activeEra != era
                || this.arenaData == null) {
            if (this.arenaData != null) {
                try (XDataWriter writer = this.getLevelWriter()) {
                    // Save old level
                    this.writeArenaLevel(writer);
                    writer.close();
                } catch (final IOException io) {
                    // Ignore
                }
            }
            this.activeLevel = level;
            this.activeEra = era;
            try (XDataReader reader = this.getLevelReaderG6()) {
                // Load new level
                this.readArenaLevel(reader);
                reader.close();
            } catch (final IOException io) {
                // Ignore
            }
        }
    }

    @Override
    public boolean doesLevelExist(final int level) {
        return level < this.levelCount && level >= 0;
    }

    @Override
    public boolean doesLevelExistOffset(final int level) {
        return this.activeLevel + level < this.levelCount
                && this.activeLevel + level >= 0;
    }

    @Override
    public void cutLevel() {
        if (this.levelCount > 1) {
            this.clipboard = this.arenaData;
            this.infoClipboard = this.levelInfoData.get(this.activeLevel);
            this.removeActiveLevel();
        }
    }

    @Override
    public void copyLevel() {
        this.clipboard = this.arenaData.clone();
        this.infoClipboard = this.levelInfoData.get(this.activeLevel).clone();
    }

    @Override
    public void pasteLevel() {
        if (this.clipboard != null) {
            this.arenaData = this.clipboard.clone();
            this.levelInfoData.set(this.activeLevel,
                    this.infoClipboard.clone());
            this.levelInfoList.set(this.activeLevel,
                    this.generateCurrentLevelInfo());
            LaserTank.getApplication().getArenaManager().setDirty(true);
        }
    }

    @Override
    public boolean isPasteBlocked() {
        return this.clipboard == null;
    }

    @Override
    public boolean isCutBlocked() {
        return this.levelCount <= 1;
    }

    @Override
    public boolean insertLevelFromClipboard() {
        if (this.levelCount < AbstractArena.MAX_LEVELS) {
            this.arenaData = this.clipboard.clone();
            this.levelCount++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addLevel() {
        if (this.levelCount < AbstractArena.MAX_LEVELS) {
            if (this.arenaData != null) {
                try (XDataWriter writer = this.getLevelWriter()) {
                    // Save old level
                    this.writeArenaLevel(writer);
                    writer.close();
                } catch (final IOException io) {
                    // Ignore
                }
            }
            // Add all eras for the new level
            final int saveEra = this.activeEra;
            this.arenaData = new CurrentArenaData();
            for (int e = 0; e < AbstractArena.ERA_COUNT; e++) {
                this.switchEra(e);
                this.arenaData = new CurrentArenaData();
            }
            this.switchEra(saveEra);
            // Clean up
            this.levelCount++;
            this.activeLevel = this.levelCount - 1;
            this.levelInfoData.add(new LevelInfo());
            this.levelInfoList.add(this.generateCurrentLevelInfo());
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean removeActiveLevel() {
        if (this.levelCount > 1) {
            if (this.activeLevel >= 0 && this.activeLevel <= this.levelCount) {
                this.arenaData = null;
                // Delete all files corresponding to current level
                for (int e = 0; e < AbstractArena.ERA_COUNT; e++) {
                    final boolean res = this.getLevelFile(this.activeLevel, e)
                            .delete();
                    if (!res) {
                        return false;
                    }
                }
                // Shift all higher-numbered levels down
                for (int x = this.activeLevel; x < this.levelCount - 1; x++) {
                    for (int e = 0; e < AbstractArena.ERA_COUNT; e++) {
                        final File sourceLocation = this.getLevelFile(x + 1, e);
                        final File targetLocation = this.getLevelFile(x, e);
                        try {
                            DirectoryUtilities.moveFile(sourceLocation,
                                    targetLocation);
                        } catch (final IOException io) {
                            // Ignore
                        }
                    }
                }
                this.levelCount--;
                this.levelInfoData.remove(this.activeLevel);
                this.levelInfoList.remove(this.activeLevel);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean isCellDirty(final int row, final int col, final int floor) {
        return this.arenaData.isCellDirty(this, row, col, floor);
    }

    @Override
    public AbstractArenaObject getCell(final int row, final int col,
            final int floor, final int layer) {
        return this.arenaData.getCell(this, row, col, floor, layer);
    }

    @Override
    public AbstractArenaObject getVirtualCell(final int row, final int col,
            final int floor, final int layer) {
        return this.arenaData.getVirtualCell(this, row, col, floor, layer);
    }

    @Override
    public int getStartRow(final int pi) {
        return this.levelInfoData.get(this.activeLevel).getStartRow(pi);
    }

    @Override
    public int getStartColumn(final int pi) {
        return this.levelInfoData.get(this.activeLevel).getStartColumn(pi);
    }

    @Override
    public int getStartFloor(final int pi) {
        return this.levelInfoData.get(this.activeLevel).getStartFloor(pi);
    }

    public static int getStartLevel() {
        return 0;
    }

    @Override
    public int getRows() {
        return this.arenaData.getRows();
    }

    @Override
    public int getColumns() {
        return this.arenaData.getColumns();
    }

    @Override
    public int getFloors() {
        return this.arenaData.getFloors();
    }

    @Override
    public int getLevels() {
        return this.levelCount;
    }

    @Override
    public boolean doesPlayerExist(final int pi) {
        return this.levelInfoData.get(this.activeLevel).doesPlayerExist(pi);
    }

    @Override
    public int[] findPlayer(final int number) {
        return this.arenaData.findPlayer(this, number);
    }

    @Override
    public void tickTimers(final int floor, final int actionType) {
        this.arenaData.tickTimers(this, floor, actionType);
    }

    @Override
    public void checkForEnemies(final int floor, final int ex, final int ey,
            final AbstractCharacter e) {
        this.arenaData.checkForEnemies(this, floor, ex, ey, e);
    }

    @Override
    public int checkForMagnetic(final int floor, final int centerX,
            final int centerY, final Direction dir) {
        return this.arenaData.checkForMagnetic(this, floor, centerX, centerY,
                dir);
    }

    @Override
    public int[] circularScan(final int x, final int y, final int z,
            final int maxR, final String targetName, final boolean moved) {
        return this.arenaData.circularScan(this, x, y, z, maxR, targetName,
                moved);
    }

    @Override
    public int[] circularScanTunnel(final int x, final int y, final int z,
            final int maxR, final int tx, final int ty,
            final AbstractTunnel target, final boolean moved) {
        return this.arenaData.circularScanTunnel(this, x, y, z, maxR, tx, ty,
                target, moved);
    }

    @Override
    public void circularScanRange(final int x, final int y, final int z,
            final int maxR, final int rangeType, final int forceUnits) {
        this.arenaData.circularScanRange(this, x, y, z, maxR, rangeType,
                forceUnits);
    }

    @Override
    public int[] findObject(final int z, final String targetName) {
        return this.arenaData.findObject(this, z, targetName);
    }

    @Override
    public boolean circularScanTank(final int x, final int y, final int z,
            final int maxR) {
        return this.arenaData.circularScanTank(this, x, y, z, maxR);
    }

    @Override
    public void fullScanKillTanks() {
        this.arenaData.fullScanKillTanks(this);
    }

    @Override
    public void fullScanFreezeGround() {
        this.arenaData.fullScanFreezeGround(this);
    }

    @Override
    public void fullScanAllButtonOpen(final int z,
            final AbstractButton source) {
        this.arenaData.fullScanAllButtonOpen(this, z, source);
    }

    @Override
    public void fullScanAllButtonClose(final int z,
            final AbstractButton source) {
        this.arenaData.fullScanAllButtonClose(this, z, source);
    }

    @Override
    public void fullScanButtonBind(final int dx, final int dy, final int z,
            final AbstractButtonDoor source) {
        this.arenaData.fullScanButtonBind(this, dx, dy, z, source);
    }

    @Override
    public void fullScanButtonCleanup(final int px, final int py, final int z,
            final AbstractButton button) {
        this.arenaData.fullScanButtonCleanup(this, px, py, z, button);
    }

    @Override
    public void fullScanFindButtonLostDoor(final int z,
            final AbstractButtonDoor door) {
        this.arenaData.fullScanFindButtonLostDoor(this, z, door);
    }

    @Override
    public void setCell(final AbstractArenaObject mo, final int row,
            final int col, final int floor, final int layer) {
        this.arenaData.setCell(this, mo, row, col, floor, layer);
    }

    @Override
    public void setVirtualCell(final AbstractArenaObject mo, final int row,
            final int col, final int floor, final int layer) {
        this.arenaData.setVirtualCell(this, mo, row, col, floor, layer);
    }

    @Override
    public void markAsDirty(final int row, final int col, final int floor) {
        this.arenaData.markAsDirty(this, row, col, floor);
    }

    @Override
    public void clearDirtyFlags(final int floor) {
        this.arenaData.clearDirtyFlags(floor);
    }

    @Override
    public void setDirtyFlags(final int floor) {
        this.arenaData.setDirtyFlags(floor);
    }

    @Override
    public void clearVirtualGrid() {
        this.arenaData.clearVirtualGrid(this);
    }

    @Override
    public void setStartRow(final int pi, final int newStartRow) {
        this.levelInfoData.get(this.activeLevel).setStartRow(pi, newStartRow);
    }

    @Override
    public void setStartColumn(final int pi, final int newStartColumn) {
        this.levelInfoData.get(this.activeLevel).setStartColumn(pi,
                newStartColumn);
    }

    @Override
    public void setStartFloor(final int pi, final int newStartFloor) {
        this.levelInfoData.get(this.activeLevel).setStartFloor(pi,
                newStartFloor);
    }

    @Override
    public void fillDefault() {
        final AbstractArenaObject fill = PreferencesManager
                .getEditorDefaultFill();
        this.arenaData.fill(this, fill);
    }

    @Override
    public void save() {
        this.arenaData.save(this);
    }

    @Override
    public void restore() {
        this.arenaData.restore(this);
    }

    @Override
    public void resize(final int z, final AbstractArenaObject nullFill) {
        this.arenaData.resize(this, z, nullFill);
    }

    @Override
    public void setData(final AbstractArenaData newData, final int count) {
        if (newData instanceof CurrentArenaData) {
            this.arenaData = (CurrentArenaData) newData;
            this.levelCount = count;
        }
    }

    @Override
    public void enableHorizontalWraparound() {
        this.levelInfoData.get(this.activeLevel).enableHorizontalWraparound();
    }

    @Override
    public void disableHorizontalWraparound() {
        this.levelInfoData.get(this.activeLevel).disableHorizontalWraparound();
    }

    @Override
    public void enableVerticalWraparound() {
        this.levelInfoData.get(this.activeLevel).enableVerticalWraparound();
    }

    @Override
    public void disableVerticalWraparound() {
        this.levelInfoData.get(this.activeLevel).disableVerticalWraparound();
    }

    @Override
    public void enableThirdDimensionWraparound() {
        this.levelInfoData.get(this.activeLevel)
                .enableThirdDimensionWraparound();
    }

    @Override
    public void disableThirdDimensionWraparound() {
        this.levelInfoData.get(this.activeLevel)
                .disableThirdDimensionWraparound();
    }

    @Override
    public boolean isHorizontalWraparoundEnabled() {
        return this.levelInfoData.get(this.activeLevel)
                .isHorizontalWraparoundEnabled();
    }

    @Override
    public boolean isVerticalWraparoundEnabled() {
        return this.levelInfoData.get(this.activeLevel)
                .isVerticalWraparoundEnabled();
    }

    @Override
    public boolean isThirdDimensionWraparoundEnabled() {
        return this.levelInfoData.get(this.activeLevel)
                .isThirdDimensionWraparoundEnabled();
    }

    @Override
    public CurrentArena readArena() throws IOException {
        final CurrentArena m = new CurrentArena();
        // Attach handlers
        m.setPrefixHandler(this.prefixHandler);
        m.setSuffixHandler(this.suffixHandler);
        // Make base paths the same
        m.basePath = this.basePath;
        int version = -1;
        // Create metafile reader
        try (XDataReader metaReader = new XDataReader(m.basePath
                + File.separator
                + StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_ARENA_FORMAT_METAFILE)
                + Extension.getArenaLevelExtensionWithPeriod(),
                StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_ARENA_FORMAT_ARENA))) {
            // Read metafile
            version = m.readArenaMetafileVersion(metaReader);
            if (FormatConstants.isFormatVersionValidGeneration6(version)) {
                m.readArenaMetafileG6(metaReader, version);
            } else if (FormatConstants.isFormatVersionValidGeneration4(version)
                    || FormatConstants
                            .isFormatVersionValidGeneration5(version)) {
                m.readArenaMetafileG4(metaReader, version);
            } else {
                m.readArenaMetafileG3(metaReader, version);
            }
        } catch (final IOException ioe) {
            throw ioe;
        }
        if (!FormatConstants.isLevelListStored(version)) {
            // Create data reader
            try (XDataReader dataReader = m.getLevelReaderG5()) {
                // Read data
                m.readArenaLevel(dataReader, version);
            } catch (final IOException ioe) {
                throw ioe;
            }
            // Update level info
            m.generateLevelInfoList();
        } else {
            // Create data reader
            try (XDataReader dataReader = m.getLevelReaderG6()) {
                // Read data
                m.readArenaLevel(dataReader, version);
            } catch (final IOException ioe) {
                throw ioe;
            }
        }
        return m;
    }

    private XDataReader getLevelReaderG5() throws IOException {
        return new XDataReader(this.basePath + File.separator
                + StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_ARENA_FORMAT_LEVEL)
                + this.activeLevel
                + Extension.getArenaLevelExtensionWithPeriod(),
                StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_ARENA_FORMAT_LEVEL));
    }

    private XDataReader getLevelReaderG6() throws IOException {
        return new XDataReader(this.basePath + File.separator
                + StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_ARENA_FORMAT_LEVEL)
                + this.activeLevel
                + StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_ARENA_FORMAT_ERA)
                + this.activeEra + Extension.getArenaLevelExtensionWithPeriod(),
                StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_ARENA_FORMAT_LEVEL));
    }

    private int readArenaMetafileVersion(final XDataReader reader)
            throws IOException {
        int ver = FormatConstants.ARENA_FORMAT_LATEST;
        if (this.prefixHandler != null) {
            ver = this.prefixHandler.readPrefix(reader);
        }
        this.moveShootAllowed = FormatConstants.isMoveShootAllowed(ver);
        return ver;
    }

    private void readArenaMetafileG3(final XDataReader reader, final int ver)
            throws IOException {
        this.levelCount = reader.readInt();
        this.musicFilename = "null";
        if (this.suffixHandler != null) {
            this.suffixHandler.readSuffix(reader, ver);
        }
    }

    private void readArenaMetafileG4(final XDataReader reader, final int ver)
            throws IOException {
        this.levelCount = reader.readInt();
        this.musicFilename = reader.readString();
        if (this.suffixHandler != null) {
            this.suffixHandler.readSuffix(reader, ver);
        }
    }

    private void readArenaMetafileG6(final XDataReader reader, final int ver)
            throws IOException {
        this.levelCount = reader.readInt();
        this.musicFilename = reader.readString();
        this.moveShootAllowed = reader.readBoolean();
        for (int l = 0; l < this.levelCount; l++) {
            this.levelInfoData.add(LevelInfo.readLevelInfo(reader));
            this.levelInfoList.add(reader.readString());
        }
        if (this.suffixHandler != null) {
            this.suffixHandler.readSuffix(reader, ver);
        }
    }

    private void readArenaLevel(final XDataReader reader) throws IOException {
        this.readArenaLevel(reader, FormatConstants.ARENA_FORMAT_LATEST);
    }

    private void readArenaLevel(final XDataReader reader,
            final int formatVersion) throws IOException {
        this.arenaData = (CurrentArenaData) new CurrentArenaData()
                .readData(this, reader, formatVersion);
        this.arenaData.readSavedState(reader, formatVersion);
    }

    private File getLevelFile(final int level, final int era) {
        return new File(this.basePath + File.separator
                + StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_ARENA_FORMAT_LEVEL)
                + level
                + StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_ARENA_FORMAT_ERA)
                + era + Extension.getArenaLevelExtensionWithPeriod());
    }

    @Override
    public void writeArena() throws IOException {
        // Create metafile writer
        try (XDataWriter metaWriter = new XDataWriter(this.basePath
                + File.separator
                + StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_ARENA_FORMAT_METAFILE)
                + Extension.getArenaLevelExtensionWithPeriod(),
                StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_ARENA_FORMAT_ARENA))) {
            // Write metafile
            this.writeArenaMetafile(metaWriter);
        } catch (final IOException ioe) {
            throw ioe;
        }
        // Create data writer
        try (XDataWriter dataWriter = this.getLevelWriter()) {
            // Write data
            this.writeArenaLevel(dataWriter);
        } catch (final IOException ioe) {
            throw ioe;
        }
    }

    private XDataWriter getLevelWriter() throws IOException {
        return new XDataWriter(this.basePath + File.separator
                + StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_ARENA_FORMAT_LEVEL)
                + this.activeLevel
                + StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_ARENA_FORMAT_ERA)
                + this.activeEra + Extension.getArenaLevelExtensionWithPeriod(),
                StringLoader.loadString(StringConstants.NOTL_STRINGS_FILE,
                        StringConstants.NOTL_STRING_ARENA_FORMAT_LEVEL));
    }

    private void writeArenaMetafile(final XDataWriter writer)
            throws IOException {
        if (this.prefixHandler != null) {
            this.prefixHandler.writePrefix(writer);
        }
        writer.writeInt(this.levelCount);
        writer.writeString(this.musicFilename);
        writer.writeBoolean(this.moveShootAllowed);
        for (int l = 0; l < this.levelCount; l++) {
            this.levelInfoData.get(l).writeLevelInfo(writer);
            writer.writeString(this.levelInfoList.get(l));
        }
        if (this.suffixHandler != null) {
            this.suffixHandler.writeSuffix(writer);
        }
    }

    private void writeArenaLevel(final XDataWriter writer) throws IOException {
        // Write the level
        this.arenaData.writeData(this, writer);
        this.arenaData.writeSavedState(writer);
    }

    @Override
    public void undo() {
        this.arenaData.undo(this);
    }

    @Override
    public void redo() {
        this.arenaData.redo(this);
    }

    @Override
    public boolean tryUndo() {
        return this.arenaData.tryUndo();
    }

    @Override
    public boolean tryRedo() {
        return this.arenaData.tryRedo();
    }

    @Override
    public void updateUndoHistory(final HistoryStatus whatIs) {
        this.arenaData.updateUndoHistory(whatIs);
    }

    @Override
    public void updateRedoHistory(final HistoryStatus whatIs) {
        this.arenaData.updateRedoHistory(whatIs);
    }

    @Override
    public HistoryStatus getWhatWas() {
        return this.arenaData.getWhatWas();
    }

    @Override
    public void resetHistoryEngine() {
        this.arenaData.resetHistoryEngine();
    }
}