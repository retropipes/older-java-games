/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon;

import java.io.File;
import java.io.IOException;

import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.VersionException;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractGameObject;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractMovingObject;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Empty;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Tile;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.DirectionResolver;
import studio.ignitionigloogames.chrystalz.manager.dungeon.FormatConstants;
import studio.ignitionigloogames.chrystalz.manager.dungeon.PrefixIO;
import studio.ignitionigloogames.chrystalz.manager.dungeon.SuffixIO;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;
import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;
import studio.ignitionigloogames.common.random.RandomLongRange;

public class Dungeon {
    // Properties
    private LayeredTower mazeData;
    private int startW;
    private int locW;
    private int saveW;
    private int levelCount;
    private int activeLevel;
    private String basePath;
    private PrefixIO prefixHandler;
    private SuffixIO suffixHandler;
    private final int[] savedStart;
    private static final int MIN_LEVELS = 1;
    private static final int MAX_LEVELS = 13;

    // Constructors
    public Dungeon() {
        this.mazeData = null;
        this.levelCount = 0;
        this.startW = 0;
        this.locW = 0;
        this.saveW = 0;
        this.activeLevel = 0;
        this.savedStart = new int[4];
        final long random = new RandomLongRange(0, Long.MAX_VALUE).generate();
        final String randomID = Long.toHexString(random);
        this.basePath = System.getProperty("java.io.tmpdir") + File.separator
                + "Chrystalz" + File.separator + randomID + ".maze";
        final File base = new File(this.basePath);
        final boolean success = base.mkdirs();
        if (!success) {
            CommonDialogs.showErrorDialog(
                    "Dungeon temporary folder creation failed!", "Chrystalz");
        }
    }

    // Static methods
    public static String getDungeonTempFolder() {
        return System.getProperty("java.io.tmpdir") + File.separator
                + "Chrystalz";
    }

    public static int getMinLevels() {
        return Dungeon.MIN_LEVELS;
    }

    public static int getMaxLevels() {
        return Dungeon.MAX_LEVELS;
    }

    public static int getMaxColumns() {
        return LayeredTower.getMaxColumns();
    }

    public static int getMaxRows() {
        return LayeredTower.getMaxRows();
    }

    // Methods
    public static Dungeon getTemporaryBattleCopy() {
        final Dungeon temp = new Dungeon();
        temp.addLevel(Chrystalz.getBattleDungeonSize(),
                Chrystalz.getBattleDungeonSize());
        temp.fill(new Tile(), new Empty());
        return temp;
    }

    public int computeFinalBossMoveDirection(final int locX, final int locY) {
        int px = this.getPlayerLocationX();
        int py = this.getPlayerLocationY();
        int relX = px - locX;
        int relY = py - locY;
        int moveX = 0;
        int moveY = 0;
        if (relX != 0) {
            moveX = relX / Math.abs(relX);
        }
        if (relY != 0) {
            moveY = relY / Math.abs(relY);
        }
        boolean canMove = !this.getCell(locX + moveX, locY + moveY,
                DungeonConstants.LAYER_OBJECT).isSolid();
        if (canMove) {
            return DirectionResolver.resolveRelativeDirection(moveX, moveY);
        }
        int moveX1L = DirectionResolver.rotate45LeftX(moveX, moveY);
        int moveY1L = DirectionResolver.rotate45LeftY(moveX, moveY);
        boolean canMove1L = !this.getCell(locX + moveX1L, locY + moveY1L,
                DungeonConstants.LAYER_OBJECT).isSolid();
        if (canMove1L) {
            return DirectionResolver.resolveRelativeDirection(moveX1L, moveY1L);
        }
        int moveX1R = DirectionResolver.rotate45RightX(moveX, moveY);
        int moveY1R = DirectionResolver.rotate45RightY(moveX, moveY);
        boolean canMove1R = !this.getCell(locX + moveX1R, locY + moveY1R,
                DungeonConstants.LAYER_OBJECT).isSolid();
        if (canMove1R) {
            return DirectionResolver.resolveRelativeDirection(moveX1R, moveY1R);
        }
        int moveX2L = DirectionResolver.rotate45LeftX(moveX1L, moveY1L);
        int moveY2L = DirectionResolver.rotate45LeftY(moveX1L, moveY1L);
        boolean canMove2L = !this.getCell(locX + moveX2L, locY + moveY2L,
                DungeonConstants.LAYER_OBJECT).isSolid();
        if (canMove2L) {
            return DirectionResolver.resolveRelativeDirection(moveX2L, moveY2L);
        }
        int moveX2R = DirectionResolver.rotate45RightX(moveX1R, moveY1R);
        int moveY2R = DirectionResolver.rotate45RightY(moveX1R, moveY1R);
        boolean canMove2R = !this.getCell(locX + moveX2R, locY + moveY2R,
                DungeonConstants.LAYER_OBJECT).isSolid();
        if (canMove2R) {
            return DirectionResolver.resolveRelativeDirection(moveX2R, moveY2R);
        }
        int moveX3L = DirectionResolver.rotate45LeftX(moveX2L, moveY2L);
        int moveY3L = DirectionResolver.rotate45LeftY(moveX2L, moveY2L);
        boolean canMove3L = !this.getCell(locX + moveX3L, locY + moveY3L,
                DungeonConstants.LAYER_OBJECT).isSolid();
        if (canMove3L) {
            return DirectionResolver.resolveRelativeDirection(moveX3L, moveY3L);
        }
        int moveX3R = DirectionResolver.rotate45RightX(moveX2R, moveY2R);
        int moveY3R = DirectionResolver.rotate45RightY(moveX2R, moveY2R);
        boolean canMove3R = !this.getCell(locX + moveX3R, locY + moveY3R,
                DungeonConstants.LAYER_OBJECT).isSolid();
        if (canMove3R) {
            return DirectionResolver.resolveRelativeDirection(moveX3R, moveY3R);
        }
        int moveX4 = DirectionResolver.rotate45LeftX(moveX3L, moveY3L);
        int moveY4 = DirectionResolver.rotate45LeftY(moveX3L, moveY3L);
        return DirectionResolver.resolveRelativeDirection(moveX4, moveY4);
    }

    public void updateMonsterPosition(final int move, final int xLoc,
            final int yLoc, final AbstractMovingObject monster) {
        this.mazeData.updateMonsterPosition(move, xLoc, yLoc, monster);
    }

    public void postBattle(final AbstractMovingObject m, final int xLoc,
            final int yLoc, final boolean player) {
        this.mazeData.postBattle(m, xLoc, yLoc, player);
    }

    public String getBasePath() {
        return this.basePath;
    }

    public void setPrefixHandler(final PrefixIO xph) {
        this.prefixHandler = xph;
    }

    public void setSuffixHandler(final SuffixIO xsh) {
        this.suffixHandler = xsh;
    }

    public void tickTimers() {
        this.mazeData.tickTimers();
    }

    public void resetVisibleSquares() {
        this.mazeData.resetVisibleSquares();
    }

    public void updateVisibleSquares(final int xp, final int yp) {
        this.mazeData.updateVisibleSquares(xp, yp);
    }

    public void switchLevel(final int level) {
        this.switchLevelInternal(level);
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
        return this.activeLevel + level < this.levelCount
                && this.activeLevel + level >= 0;
    }

    public int getActiveLevel() {
        return this.activeLevel;
    }

    public boolean addLevel(final int rows, final int cols) {
        this.mazeData = new LayeredTower(rows, cols);
        this.levelCount++;
        this.activeLevel = this.levelCount - 1;
        return true;
    }

    public AbstractGameObject getCell(final int row, final int col,
            final int extra) {
        return this.mazeData.getCell(row, col, extra);
    }

    public int getPlayerLocationX() {
        return this.mazeData.getPlayerRow();
    }

    public int getPlayerLocationY() {
        return this.mazeData.getPlayerColumn();
    }

    public int getStartLevel() {
        return this.startW;
    }

    public int getRows() {
        return this.mazeData.getRows();
    }

    public int getColumns() {
        return this.mazeData.getColumns();
    }

    public boolean doesPlayerExist() {
        return this.mazeData.doesPlayerExist();
    }

    public boolean isSquareVisible(final int x1, final int y1, final int x2,
            final int y2) {
        return this.mazeData.isSquareVisible(x1, y1, x2, y2);
    }

    public void setCell(final AbstractGameObject mo, final int row,
            final int col, final int extra) {
        this.mazeData.setCell(mo, row, col, extra);
    }

    public void setStartRow(final int newStartRow) {
        this.mazeData.setStartRow(newStartRow);
    }

    public void setStartColumn(final int newStartColumn) {
        this.mazeData.setStartColumn(newStartColumn);
    }

    public void savePlayerLocation() {
        this.saveW = this.locW;
        this.mazeData.savePlayerLocation();
    }

    public void restorePlayerLocation() {
        this.locW = this.saveW;
        this.mazeData.restorePlayerLocation();
    }

    public void setPlayerToStart() {
        this.mazeData.setPlayerToStart();
    }

    public void setPlayerLocationX(final int newPlayerRow) {
        this.mazeData.setPlayerRow(newPlayerRow);
    }

    public void setPlayerLocationY(final int newPlayerColumn) {
        this.mazeData.setPlayerColumn(newPlayerColumn);
    }

    public void offsetPlayerLocationX(final int newPlayerRow) {
        this.mazeData.offsetPlayerRow(newPlayerRow);
    }

    public void offsetPlayerLocationY(final int newPlayerColumn) {
        this.mazeData.offsetPlayerColumn(newPlayerColumn);
    }

    public void offsetPlayerLocationW(final int newPlayerLevel) {
        this.locW += newPlayerLevel;
    }

    public void fillLevelRandomly() {
        this.mazeData.fillRandomly(this, this.activeLevel);
    }

    public void save() {
        this.mazeData.save();
    }

    public void restore() {
        this.mazeData.restore();
    }

    private void fill(final AbstractGameObject bottom,
            final AbstractGameObject top) {
        this.mazeData.fill(bottom, top);
    }

    public Dungeon readDungeon() throws IOException {
        final Dungeon m = new Dungeon();
        // Attach handlers
        m.setPrefixHandler(this.prefixHandler);
        m.setSuffixHandler(this.suffixHandler);
        // Make base paths the same
        m.basePath = this.basePath;
        int version = 0;
        // Create metafile reader
        try (FileIOReader metaReader = new FileIOReader(
                m.basePath + File.separator + "metafile.xml", "maze")) {
            // Read metafile
            version = m.readDungeonMetafile(metaReader);
        } catch (final IOException ioe) {
            throw ioe;
        }
        // Create data reader
        try (FileIOReader dataReader = m.getLevelReader()) {
            // Read data
            m.readDungeonLevel(dataReader, version);
            return m;
        } catch (final IOException ioe) {
            throw ioe;
        }
    }

    private FileIOReader getLevelReader() throws IOException {
        return new FileIOReader(this.basePath + File.separator + "level"
                + this.activeLevel + ".xml", "level");
    }

    private int readDungeonMetafile(final FileIOReader reader)
            throws IOException {
        int ver = FormatConstants.MAZE_FORMAT_LATEST;
        if (this.prefixHandler != null) {
            ver = this.prefixHandler.readPrefix(reader);
        }
        this.levelCount = reader.readInt();
        this.startW = reader.readInt();
        this.locW = reader.readInt();
        this.saveW = reader.readInt();
        this.activeLevel = reader.readInt();
        for (int y = 0; y < 4; y++) {
            this.savedStart[y] = reader.readInt();
        }
        if (this.suffixHandler != null) {
            this.suffixHandler.readSuffix(reader, ver);
        }
        return ver;
    }

    private void readDungeonLevel(final FileIOReader reader,
            final int formatVersion) throws IOException {
        if (formatVersion == FormatConstants.MAZE_FORMAT_LATEST) {
            this.mazeData = LayeredTower.readLayeredTowerV1(reader);
            this.mazeData.readSavedTowerState(reader, formatVersion);
        } else {
            throw new VersionException(
                    "Unknown maze format version: " + formatVersion + "!");
        }
    }

    public void writeDungeon() throws IOException {
        try {
            // Create metafile writer
            try (FileIOWriter metaWriter = new FileIOWriter(
                    this.basePath + File.separator + "metafile.xml", "maze")) {
                // Write metafile
                this.writeDungeonMetafile(metaWriter);
            }
            // Create data writer
            try (FileIOWriter dataWriter = this.getLevelWriter()) {
                // Write data
                this.writeDungeonLevel(dataWriter);
            }
        } catch (final IOException ioe) {
            throw ioe;
        }
    }

    private FileIOWriter getLevelWriter() throws IOException {
        return new FileIOWriter(this.basePath + File.separator + "level"
                + this.activeLevel + ".xml", "level");
    }

    private void writeDungeonMetafile(final FileIOWriter writer)
            throws IOException {
        if (this.prefixHandler != null) {
            this.prefixHandler.writePrefix(writer);
        }
        writer.writeInt(this.levelCount);
        writer.writeInt(this.startW);
        writer.writeInt(this.locW);
        writer.writeInt(this.saveW);
        writer.writeInt(this.activeLevel);
        for (int y = 0; y < 4; y++) {
            writer.writeInt(this.savedStart[y]);
        }
        if (this.suffixHandler != null) {
            this.suffixHandler.writeSuffix(writer);
        }
    }

    private void writeDungeonLevel(final FileIOWriter writer)
            throws IOException {
        // Write the level
        this.mazeData.writeLayeredTower(writer);
        this.mazeData.writeSavedTowerState(writer);
    }
}
