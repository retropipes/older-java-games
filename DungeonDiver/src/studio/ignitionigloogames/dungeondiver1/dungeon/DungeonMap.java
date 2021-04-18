package studio.ignitionigloogames.dungeondiver1.dungeon;

import java.awt.Container;
import java.awt.Dimension;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.HoldingBag;
import studio.ignitionigloogames.dungeondiver1.dungeon.objects.DungeonObject;
import studio.ignitionigloogames.dungeondiver1.dungeon.objects.Monster;
import studio.ignitionigloogames.dungeondiver1.utilities.Map2D;
import studio.ignitionigloogames.dungeondiver1.utilities.MapObject;
import studio.ignitionigloogames.dungeondiver1.utilities.NDimensionalLocation;
import studio.ignitionigloogames.dungeondiver1.utilities.NDimensionalMap;
import studio.ignitionigloogames.dungeondiver1.utilities.RandomRange;

public class DungeonMap extends Map2D {
    // Serialization
    private static final long serialVersionUID = 230359523503425346L;

    // Constructors
    public DungeonMap(final int rows, final int cols,
            final Dimension viewingWindowDimensions,
            final MapObject errorObject, final MapObject hiddenObject) {
        super(rows, cols, viewingWindowDimensions, errorObject, hiddenObject);
    }

    // Methods
    @Override
    public Container drawGameWithVisibility(
            final NDimensionalLocation otherDimensions) {
        final HoldingBag menu = DungeonDiver.getHoldingBag();
        final DungeonGUI app = menu.getDungeonGUI();
        final DungeonObject saved = app.getSavedMapObject();
        saved.moveOntoHook();
        this.processAllPlayerMoveHooks();
        return super.drawGameWithVisibility(otherDimensions);
    }

    public Container drawGameWithVisibilityBypassHook(
            final NDimensionalLocation otherDimensions) {
        return super.drawGameWithVisibility(otherDimensions);
    }

    private void processAllPlayerMoveHooks() {
        final NDimensionalLocation dimensions = this.getDimensions();
        final int rows = dimensions.getLocation(NDimensionalMap.ROW_DIMENSION);
        final int columns = dimensions
                .getLocation(NDimensionalMap.COLUMN_DIMENSION);
        int x, y;
        for (x = 0; x < columns; x++) {
            for (y = 0; y < rows; y++) {
                final NDimensionalLocation loc = new NDimensionalLocation(
                        this.getMaxDimension());
                loc.setLocation(NDimensionalMap.ROW_DIMENSION, y);
                loc.setLocation(NDimensionalMap.COLUMN_DIMENSION, x);
                final MapObject o = this.getCell(loc);
                final DungeonObject d = (DungeonObject) o;
                d.playerMoveHook(x, y);
            }
        }
    }

    public void updateAllAppearances() {
        final NDimensionalLocation dimensions = this.getDimensions();
        final int rows = dimensions.getLocation(NDimensionalMap.ROW_DIMENSION);
        final int columns = dimensions
                .getLocation(NDimensionalMap.COLUMN_DIMENSION);
        int x, y;
        for (x = 0; x < columns; x++) {
            for (y = 0; y < rows; y++) {
                final NDimensionalLocation loc = new NDimensionalLocation(
                        this.getMaxDimension());
                loc.setLocation(NDimensionalMap.ROW_DIMENSION, y);
                loc.setLocation(NDimensionalMap.COLUMN_DIMENSION, x);
                final MapObject o = this.getCell(loc);
                final DungeonObject d = (DungeonObject) o;
                d.invalidateAppearance();
                d.updateAppearance();
            }
        }
    }

    public void generateOneMonster() {
        final RandomRange row = new RandomRange(0,
                this.getDimensions().getLocation(NDimensionalMap.ROW_DIMENSION)
                        - 1);
        final RandomRange column = new RandomRange(0, this.getDimensions()
                .getLocation(NDimensionalMap.COLUMN_DIMENSION) - 1);
        final NDimensionalLocation loc3 = new NDimensionalLocation(
                this.getMaxDimension());
        int randomRow, randomColumn;
        randomRow = row.generate();
        randomColumn = column.generate();
        loc3.setLocation(NDimensionalMap.ROW_DIMENSION, randomRow);
        loc3.setLocation(NDimensionalMap.COLUMN_DIMENSION, randomColumn);
        final MapObject currObj = new Monster();
        if (currObj.shouldGenerateObject(loc3, this)) {
            this.setCell(currObj, loc3);
        } else {
            while (!currObj.shouldGenerateObject(loc3, this)) {
                randomRow = row.generate();
                randomColumn = column.generate();
                loc3.setLocation(NDimensionalMap.ROW_DIMENSION, randomRow);
                loc3.setLocation(NDimensionalMap.COLUMN_DIMENSION,
                        randomColumn);
            }
            this.setCell(currObj, loc3);
        }
    }
}
