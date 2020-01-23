package net.worldwizard.mazerunner;

public class MazeTwoWayTeleporter extends MazeGenericTeleporter {
    // Serialization
    private static final long serialVersionUID = 8021L;

    public MazeTwoWayTeleporter() {
        super("TwoWayTeleporter", "TwoWayTeleporter", 0, 0, 0, 0);
    }

    public MazeTwoWayTeleporter(final int destRow, final int destCol,
            final int destFloor, final int destLevel) {
        super("TwoWayTeleporter", "TwoWayTeleporter", destRow, destCol,
                destFloor, destLevel);
    }

    @Override
    public void postMoveAction(final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        app.updatePositionAbsoluteNoEvents(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor(),
                this.getDestinationLevel());
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        app.updatePositionAbsoluteNoEvents(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor(),
                this.getDestinationLevel());
    }

    @Override
    public MazeObject editorHook() {
        final MazeObject mo = MazeMaker
                .editTeleporterDestination(MazeMaker.TELEPORTER_TYPE_TWOWAY);
        return mo;
    }

    @Override
    public String getName() {
        return "Two-Way Teleporter";
    }

    @Override
    public String toString() {
        return "T2\n" + Integer.toString(this.getDestinationColumn()) + "\n"
                + Integer.toString(this.getDestinationRow()) + "\n"
                + Integer.toString(this.getDestinationFloor()) + "\n"
                + Integer.toString(this.getDestinationLevel());
    }
}
