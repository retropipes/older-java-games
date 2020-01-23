package net.worldwizard.mazerunner;

public abstract class MazeGenericTeleporter extends MazeObject {
    // Fields
    private int destRow;
    private int destCol;
    private int destFloor;
    private int destLevel;
    // Serialization
    private static final long serialVersionUID = 7555L;

    // Constructors
    protected MazeGenericTeleporter(final String gameAppearance,
            final String editorAppearance, final int destinationRow,
            final int destinationColumn, final int destinationFloor,
            final int destinationLevel) {
        super(false, gameAppearance, editorAppearance);
        this.destRow = destinationRow;
        this.destCol = destinationColumn;
        this.destFloor = destinationFloor;
        this.destLevel = destinationLevel;
    }

    protected MazeGenericTeleporter(final String gameAppearance,
            final String editorAppearance, final boolean doesAcceptPushInto) {
        super(false, false, false, false, false, false, false, false,
                gameAppearance, editorAppearance, false, doesAcceptPushInto,
                false, false, false, false, true, false, 0);
    }

    // Accessor methods
    public int getDestinationRow() {
        return this.destRow;
    }

    public int getDestinationColumn() {
        return this.destCol;
    }

    public int getDestinationFloor() {
        return this.destFloor;
    }

    public int getDestinationLevel() {
        return this.destLevel;
    }

    // Transformer methods
    public void setDestinationRow(final int destinationRow) {
        this.destRow = destinationRow;
    }

    public void setDestinationColumn(final int destinationColumn) {
        this.destCol = destinationColumn;
    }

    public void setDestinationFloor(final int destinationFloor) {
        this.destFloor = destinationFloor;
    }

    public void setDestinationLevel(final int destinationLevel) {
        this.destLevel = destinationLevel;
    }

    // Scriptability
    @Override
    public void postMoveAction(final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        app.updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor(),
                this.getDestinationLevel());
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        app.updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor(),
                this.getDestinationLevel());
    }

    @Override
    public abstract String toString();

    @Override
    public abstract String getName();

    @Override
    public abstract MazeObject editorHook();

    @Override
    public boolean equals(final Object obj) {
        final MazeGenericTeleporter mgt = (MazeGenericTeleporter) obj;
        if (super.equals(mgt) && this.destRow == mgt.destRow
                && this.destCol == mgt.destCol
                && this.destFloor == mgt.destFloor
                && this.destLevel == mgt.destLevel) {
            return true;
        } else {
            return false;
        }
    }
}