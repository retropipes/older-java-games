package net.worldwizard.map;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;

public class Map2D implements NDimensionalMap {
    // Fields
    private static final long serialVersionUID = 22052305523055257L;
    private final MapObject[][] mapData;
    private final MapObject[][] savedLevel;
    private final int[] playerData;
    private final NDimensionalLocation findResult;
    private final Container view;
    private final Dimension viewingWindowSize;
    private Point viewingWindowLocation;
    private final MapObject error;
    private final MapObject hidden;
    private final Dimension drawDistance;
    private static final int MAX_DIMENSIONS = 2;

    // Constructors
    public Map2D(final int rows, final int cols,
            final Dimension viewingWindowDimensions,
            final MapObject errorObject) {
        this.mapData = new MapObject[cols][rows];
        this.savedLevel = new MapObject[cols][rows];
        this.playerData = new int[Map2D.MAX_DIMENSIONS];
        this.findResult = new NDimensionalLocation(Map2D.MAX_DIMENSIONS);
        this.viewingWindowSize = viewingWindowDimensions;
        this.viewingWindowLocation = new Point(0, 0);
        this.view = new Container();
        this.view.setLayout(new GridLayout(this.viewingWindowSize.width,
                this.viewingWindowSize.height));
        this.error = errorObject;
        this.hidden = errorObject;
        this.drawDistance = new Dimension();
        this.drawDistance.height = this.viewingWindowSize.height / 2 + 1;
        this.drawDistance.width = this.viewingWindowSize.width / 2 + 1;
    }

    public Map2D(final int rows, final int cols,
            final Dimension viewingWindowDimensions,
            final MapObject errorObject, final MapObject hiddenObject) {
        this.mapData = new MapObject[cols][rows];
        this.savedLevel = new MapObject[cols][rows];
        this.playerData = new int[Map2D.MAX_DIMENSIONS];
        this.findResult = new NDimensionalLocation(Map2D.MAX_DIMENSIONS);
        this.viewingWindowSize = viewingWindowDimensions;
        this.viewingWindowLocation = new Point(0, 0);
        this.view = new Container();
        this.view.setLayout(new GridLayout(this.viewingWindowSize.width,
                this.viewingWindowSize.height));
        this.error = errorObject;
        this.hidden = hiddenObject;
        this.drawDistance = new Dimension();
        this.drawDistance.height = this.viewingWindowSize.height / 2 + 1;
        this.drawDistance.width = this.viewingWindowSize.width / 2 + 1;
    }

    // Methods
    @Override
    public MapObject getCell(final NDimensionalLocation loc) {
        final int row = loc.getLocation(NDimensionalMap.ROW_DIMENSION);
        final int col = loc.getLocation(NDimensionalMap.COLUMN_DIMENSION);
        return this.mapData[col][row];
    }

    @Override
    public MapObject getCellOffset(final NDimensionalLocation loc,
            final NDimensionalLocation offset) {
        final int row = loc.getLocation(NDimensionalMap.ROW_DIMENSION)
                + offset.getLocation(NDimensionalMap.ROW_DIMENSION);
        final int col = loc.getLocation(NDimensionalMap.COLUMN_DIMENSION)
                + offset.getLocation(NDimensionalMap.COLUMN_DIMENSION);
        return this.mapData[col][row];
    }

    @Override
    public NDimensionalLocation getStart(final int dimension) {
        final int row = this.playerData[NDimensionalMap.ROW_DIMENSION];
        final int column = this.playerData[NDimensionalMap.COLUMN_DIMENSION];
        final NDimensionalLocation loc = new NDimensionalLocation(
                this.getMaxDimension());
        loc.setLocation(NDimensionalMap.ROW_DIMENSION, row);
        loc.setLocation(NDimensionalMap.COLUMN_DIMENSION, column);
        return loc;
    }

    @Override
    public NDimensionalLocation getDimensions() {
        final int row = this.mapData[0].length;
        final int column = this.mapData.length;
        final NDimensionalLocation loc = new NDimensionalLocation(
                this.getMaxDimension());
        loc.setLocation(NDimensionalMap.ROW_DIMENSION, row);
        loc.setLocation(NDimensionalMap.COLUMN_DIMENSION, column);
        return loc;
    }

    @Override
    public final int getMaxDimension() {
        return Map2D.MAX_DIMENSIONS;
    }

    @Override
    public boolean findObject(final MapObject obj) {
        final NDimensionalLocation loc = this.getDimensions();
        final int columns = loc.getLocation(NDimensionalMap.COLUMN_DIMENSION);
        final int rows = loc.getLocation(NDimensionalMap.ROW_DIMENSION);
        int x, y;
        for (x = 0; x < columns; x++) {
            for (y = 0; y < rows; y++) {
                if (this.mapData[x][y].getName().equals(obj.getName())) {
                    // Found it
                    this.findResult
                            .setLocation(NDimensionalMap.COLUMN_DIMENSION, x);
                    this.findResult.setLocation(NDimensionalMap.ROW_DIMENSION,
                            y);
                    return true;
                }
            }
        }
        // Didn't find it
        return false;
    }

    @Override
    public boolean findObjectOnDimension(final int dimension,
            final MapObject obj) {
        return this.findObject(obj);
    }

    @Override
    public boolean findNthObject(final int N, final MapObject obj) {
        final NDimensionalLocation loc = this.getDimensions();
        final int columns = loc.getLocation(NDimensionalMap.COLUMN_DIMENSION);
        final int rows = loc.getLocation(NDimensionalMap.ROW_DIMENSION);
        int x, y, counter;
        counter = 0;
        for (x = 0; x < columns; x++) {
            for (y = 0; y < rows; y++) {
                if (this.mapData[x][y].getName().equals(obj.getName())) {
                    counter++;
                    if (counter == N) {
                        // Found it
                        this.findResult.setLocation(
                                NDimensionalMap.COLUMN_DIMENSION, x);
                        this.findResult
                                .setLocation(NDimensionalMap.ROW_DIMENSION, y);
                        return true;
                    }
                }
            }
        }
        // Didn't find it
        return false;
    }

    @Override
    public boolean findNthObjectOnDimension(final int dimension, final int N,
            final MapObject obj) {
        return this.findNthObject(N, obj);
    }

    @Override
    public NDimensionalLocation getFindResult() {
        return this.findResult;
    }

    @Override
    public void setCell(final MapObject mo, final NDimensionalLocation loc) {
        final int row = loc.getLocation(NDimensionalMap.ROW_DIMENSION);
        final int col = loc.getLocation(NDimensionalMap.COLUMN_DIMENSION);
        this.mapData[col][row] = mo;
    }

    @Override
    public void setStart(final int dimension,
            final NDimensionalLocation newStart) {
        final int row = newStart.getLocation(NDimensionalMap.ROW_DIMENSION);
        final int column = newStart
                .getLocation(NDimensionalMap.COLUMN_DIMENSION);
        this.playerData[NDimensionalMap.ROW_DIMENSION] = row;
        this.playerData[NDimensionalMap.COLUMN_DIMENSION] = column;
    }

    @Override
    public void save() {
        final NDimensionalLocation loc = this.getDimensions();
        final int columns = loc.getLocation(NDimensionalMap.COLUMN_DIMENSION);
        final int rows = loc.getLocation(NDimensionalMap.ROW_DIMENSION);
        int x, y;
        for (x = 0; x < columns; x++) {
            for (y = 0; y < rows; y++) {
                this.savedLevel[x][y] = this.mapData[x][y];
            }
        }
    }

    @Override
    public void saveDimension(final int dimension) {
        this.save();
    }

    @Override
    public void restore() {
        final NDimensionalLocation loc = this.getDimensions();
        final int columns = loc.getLocation(NDimensionalMap.COLUMN_DIMENSION);
        final int rows = loc.getLocation(NDimensionalMap.ROW_DIMENSION);
        int x, y;
        for (x = 0; x < columns; x++) {
            for (y = 0; y < rows; y++) {
                this.mapData[x][y] = this.savedLevel[x][y];
            }
        }
    }

    @Override
    public void restoreDimension(final int dimension) {
        this.restore();
    }

    @Override
    public void fillMap(final MapObject fillObj) {
        final NDimensionalLocation loc = this.getDimensions();
        final int columns = loc.getLocation(NDimensionalMap.COLUMN_DIMENSION);
        final int rows = loc.getLocation(NDimensionalMap.ROW_DIMENSION);
        int x, y;
        for (x = 0; x < columns; x++) {
            for (y = 0; y < rows; y++) {
                this.mapData[y][x] = fillObj;
            }
        }
    }

    @Override
    public void fillMapDimension(final MapObject fillObj, final int dimension) {
        this.fillMap(fillObj);
    }

    @Override
    public void fillMapRandomly(final MapObjectList objects,
            final MapObject pass1Fill) {
        // Pass 1
        this.fillMap(pass1Fill);
        // Pass 2
        final NDimensionalLocation loc = this.getDimensions();
        final int columns = loc.getLocation(NDimensionalMap.COLUMN_DIMENSION);
        final int rows = loc.getLocation(NDimensionalMap.ROW_DIMENSION);
        final MapObject[] objectsWithoutPrerequisites = objects
                .getAllWithoutPrerequisiteAndNotRequired();
        RandomRange r = new RandomRange(0,
                objectsWithoutPrerequisites.length - 1);
        int x, y;
        for (x = 0; x < columns; x++) {
            for (y = 0; y < rows; y++) {
                final MapObject placeObj = objectsWithoutPrerequisites[r
                        .generate()];
                final NDimensionalLocation loc2 = new NDimensionalLocation(
                        this.getMaxDimension());
                loc2.setLocation(NDimensionalMap.COLUMN_DIMENSION, x);
                loc2.setLocation(NDimensionalMap.ROW_DIMENSION, y);
                final boolean okay = placeObj.shouldGenerateObject(loc2, this);
                if (okay) {
                    if (placeObj.shouldCache()) {
                        this.mapData[x][y] = placeObj;
                    } else {
                        this.mapData[x][y] = objects
                                .getNewInstanceByName(placeObj.getName());
                    }
                }
            }
        }
        // Pass 3...N
        int N = 1;
        MapObject[] objectsWithPrerequisites = objects
                .getAllWithNthPrerequisite(N);
        while (objectsWithPrerequisites != null) {
            r = new RandomRange(0, objectsWithPrerequisites.length - 1);
            for (x = 0; x < columns; x++) {
                for (y = 0; y < rows; y++) {
                    final MapObject placeObj = objectsWithPrerequisites[r
                            .generate()];
                    final NDimensionalLocation loc2 = new NDimensionalLocation(
                            this.getMaxDimension());
                    loc2.setLocation(NDimensionalMap.COLUMN_DIMENSION, x);
                    loc2.setLocation(NDimensionalMap.ROW_DIMENSION, y);
                    final boolean okay = placeObj.shouldGenerateObject(loc2,
                            this);
                    if (okay) {
                        if (placeObj.shouldCache()) {
                            this.mapData[x][y] = placeObj;
                        } else {
                            this.mapData[x][y] = objects
                                    .getNewInstanceByName(placeObj.getName());
                        }
                    }
                }
            }
            N++;
            objectsWithPrerequisites = objects.getAllWithNthPrerequisite(N);
        }
        // Pass N + 1
        final MapObject[] requiredObjects = objects.getAllRequired();
        final RandomRange row = new RandomRange(0,
                this.getDimensions().getLocation(NDimensionalMap.ROW_DIMENSION)
                        - 1);
        final RandomRange column = new RandomRange(0, this.getDimensions()
                .getLocation(NDimensionalMap.COLUMN_DIMENSION) - 1);
        final NDimensionalLocation loc3 = new NDimensionalLocation(
                this.getMaxDimension());
        int randomRow, randomColumn;
        for (x = 0; x < requiredObjects.length; x++) {
            randomRow = row.generate();
            randomColumn = column.generate();
            loc3.setLocation(NDimensionalMap.ROW_DIMENSION, randomRow);
            loc3.setLocation(NDimensionalMap.COLUMN_DIMENSION, randomColumn);
            final MapObject currObj = requiredObjects[x];
            final int min = currObj.getMinimumRequiredQuantity(this);
            final int max = currObj.getMaximumRequiredQuantity(this);
            final RandomRange howMany = new RandomRange(min, max);
            final int generateHowMany = howMany.generate();
            for (y = 0; y < generateHowMany; y++) {
                randomRow = row.generate();
                randomColumn = column.generate();
                if (currObj.shouldGenerateObject(loc3, this)) {
                    if (currObj.shouldCache()) {
                        this.mapData[randomColumn][randomRow] = currObj;
                    } else {
                        this.mapData[randomColumn][randomRow] = objects
                                .getNewInstanceByName(currObj.getName());
                    }
                } else {
                    while (!currObj.shouldGenerateObject(loc3, this)) {
                        randomRow = row.generate();
                        randomColumn = column.generate();
                        loc3.setLocation(NDimensionalMap.ROW_DIMENSION,
                                randomRow);
                        loc3.setLocation(NDimensionalMap.COLUMN_DIMENSION,
                                randomColumn);
                    }
                    if (currObj.shouldCache()) {
                        this.mapData[randomColumn][randomRow] = currObj;
                    } else {
                        this.mapData[randomColumn][randomRow] = objects
                                .getNewInstanceByName(currObj.getName());
                    }
                }
            }
        }
    }

    @Override
    public void fillMapDimensionRandomly(final MapObjectList objects,
            final MapObject pass1Fill, final int dimension) {
        this.fillMapRandomly(objects, pass1Fill);
    }

    @Override
    public Container drawGame(final NDimensionalLocation otherDimensions) {
        this.view.removeAll();
        int x, y;
        final int maxViewingWindowRow = this.viewingWindowLocation.y
                + this.viewingWindowSize.height;
        final int maxViewingWindowColumn = this.viewingWindowLocation.x
                + this.viewingWindowSize.width;
        for (x = this.viewingWindowLocation.x; x < maxViewingWindowColumn; x++) {
            for (y = this.viewingWindowLocation.y; y < maxViewingWindowRow; y++) {
                try {
                    this.view.add(
                            new JLabel(this.mapData[y][x].getGameAppearance()));
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    this.view.add(new JLabel(this.error.getGameAppearance()));
                } catch (final NullPointerException np) {
                    this.view.add(new JLabel(this.error.getGameAppearance()));
                }
            }
        }
        return this.view;
    }

    @Override
    public Container drawGameWithVisibility(
            final NDimensionalLocation otherDimensions) {
        this.view.removeAll();
        int x, y;
        final int maxViewingWindowRow = this.viewingWindowLocation.y
                + this.viewingWindowSize.height;
        final int maxViewingWindowColumn = this.viewingWindowLocation.x
                + this.viewingWindowSize.width;
        for (x = this.viewingWindowLocation.x; x < maxViewingWindowColumn; x++) {
            for (y = this.viewingWindowLocation.y; y < maxViewingWindowRow; y++) {
                try {
                    if (this.isVisibleHorizontal(x)
                            && this.isVisibleVertical(y)) {
                        this.view.add(new JLabel(
                                this.mapData[y][x].getGameAppearance()));
                    } else {
                        this.view.add(
                                new JLabel(this.hidden.getGameAppearance()));
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    this.view.add(new JLabel(this.error.getGameAppearance()));
                } catch (final NullPointerException np) {
                    this.view.add(new JLabel(this.error.getGameAppearance()));
                }
            }
        }
        return this.view;
    }

    @Override
    public Container drawEditor(final NDimensionalLocation otherDimensions) {
        this.view.removeAll();
        int x, y;
        final int maxViewingWindowRow = this.viewingWindowLocation.y
                + this.viewingWindowSize.height;
        final int maxViewingWindowColumn = this.viewingWindowLocation.x
                + this.viewingWindowSize.width;
        for (x = this.viewingWindowLocation.x; x < maxViewingWindowColumn; x++) {
            for (y = this.viewingWindowLocation.y; y < maxViewingWindowRow; y++) {
                try {
                    this.view.add(new JLabel(
                            this.mapData[y][x].getEditorAppearance()));
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    this.view.add(new JLabel(this.error.getEditorAppearance()));
                } catch (final NullPointerException np) {
                    this.view.add(new JLabel(this.error.getEditorAppearance()));
                }
            }
        }
        return this.view;
    }

    @Override
    public Container drawEditorWithVisibility(
            final NDimensionalLocation otherDimensions) {
        this.view.removeAll();
        int x, y;
        final int maxViewingWindowRow = this.viewingWindowLocation.y
                + this.viewingWindowSize.height;
        final int maxViewingWindowColumn = this.viewingWindowLocation.x
                + this.viewingWindowSize.width;
        for (x = this.viewingWindowLocation.x; x < maxViewingWindowColumn; x++) {
            for (y = this.viewingWindowLocation.y; y < maxViewingWindowRow; y++) {
                try {
                    if (this.isVisibleHorizontal(x)
                            && this.isVisibleVertical(y)) {
                        this.view.add(new JLabel(
                                this.mapData[y][x].getEditorAppearance()));
                    } else {
                        this.view.add(
                                new JLabel(this.hidden.getEditorAppearance()));
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    this.view.add(new JLabel(this.error.getEditorAppearance()));
                } catch (final NullPointerException np) {
                    this.view.add(new JLabel(this.error.getEditorAppearance()));
                }
            }
        }
        return this.view;
    }

    @Override
    public Container drawOther(final int appearanceID,
            final NDimensionalLocation otherDimensions) {
        this.view.removeAll();
        int x, y;
        final int maxViewingWindowRow = this.viewingWindowLocation.y
                + this.viewingWindowSize.height;
        final int maxViewingWindowColumn = this.viewingWindowLocation.x
                + this.viewingWindowSize.width;
        for (x = this.viewingWindowLocation.x; x < maxViewingWindowColumn; x++) {
            for (y = this.viewingWindowLocation.y; y < maxViewingWindowRow; y++) {
                try {
                    this.view.add(new JLabel(this.mapData[y][x]
                            .getOtherAppearance(appearanceID)));
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    this.view.add(new JLabel(
                            this.error.getOtherAppearance(appearanceID)));
                } catch (final NullPointerException np) {
                    this.view.add(new JLabel(
                            this.error.getOtherAppearance(appearanceID)));
                }
            }
        }
        return this.view;
    }

    @Override
    public Container drawOtherWithVisibility(final int appearanceID,
            final NDimensionalLocation otherDimensions) {
        this.view.removeAll();
        int x, y;
        final int maxViewingWindowRow = this.viewingWindowLocation.y
                + this.viewingWindowSize.height;
        final int maxViewingWindowColumn = this.viewingWindowLocation.x
                + this.viewingWindowSize.width;
        for (x = this.viewingWindowLocation.x; x < maxViewingWindowColumn; x++) {
            for (y = this.viewingWindowLocation.y; y < maxViewingWindowRow; y++) {
                try {
                    if (this.isVisibleHorizontal(x)
                            && this.isVisibleVertical(y)) {
                        this.view.add(new JLabel(this.mapData[y][x]
                                .getOtherAppearance(appearanceID)));
                    } else {
                        this.view.add(new JLabel(
                                this.hidden.getOtherAppearance(appearanceID)));
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    this.view.add(new JLabel(
                            this.error.getOtherAppearance(appearanceID)));
                } catch (final NullPointerException np) {
                    this.view.add(new JLabel(
                            this.error.getOtherAppearance(appearanceID)));
                }
            }
        }
        return this.view;
    }

    @Override
    public Point alterViewingWindow(final Point newViewingWindow) {
        final Point save = this.viewingWindowLocation;
        this.viewingWindowLocation = newViewingWindow;
        return save;
    }

    @Override
    public Point alterViewingWindowOffset(final Point newViewingWindowOffset) {
        final Point save = this.viewingWindowLocation;
        this.viewingWindowLocation.x += newViewingWindowOffset.x;
        this.viewingWindowLocation.y += newViewingWindowOffset.y;
        return save;
    }

    @Override
    public int getDrawDistance() {
        return Math.max(this.drawDistance.width, this.drawDistance.height);
    }

    @Override
    public void setDrawDistance(final int newDrawDistance) {
        this.drawDistance.height = newDrawDistance;
        this.drawDistance.width = newDrawDistance;
    }

    @Override
    public void decrementDrawDistance() {
        this.drawDistance.width--;
        this.drawDistance.height--;
    }

    @Override
    public void decrementHorizontalDrawDistance() {
        this.drawDistance.width--;
    }

    @Override
    public void decrementVerticalDrawDistance() {
        this.drawDistance.height--;
    }

    @Override
    public int getHorizontalDrawDistance() {
        return this.drawDistance.width;
    }

    @Override
    public int getVerticalDrawDistance() {
        return this.drawDistance.height;
    }

    @Override
    public void incrementDrawDistance() {
        this.drawDistance.width++;
        this.drawDistance.height++;
    }

    @Override
    public void incrementHorizontalDrawDistance() {
        this.drawDistance.width++;
    }

    @Override
    public void incrementVerticalDrawDistance() {
        this.drawDistance.height++;
    }

    @Override
    public void setHorizontalDrawDistance(final int newDrawDistance) {
        this.drawDistance.width = newDrawDistance;
    }

    @Override
    public void setVerticalDrawDistance(final int newDrawDistance) {
        this.drawDistance.height = newDrawDistance;
    }

    private boolean isVisibleHorizontal(final int x) {
        if (Math.abs(x - this.viewingWindowLocation.x
                - this.viewingWindowSize.width / 2) < this.drawDistance.width) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isVisibleVertical(final int y) {
        if (Math.abs(
                y - this.viewingWindowLocation.y - this.viewingWindowSize.height
                        / 2) < this.drawDistance.height) {
            return true;
        } else {
            return false;
        }
    }
}