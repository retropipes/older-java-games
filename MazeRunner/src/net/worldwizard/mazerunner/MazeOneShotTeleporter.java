package net.worldwizard.mazerunner;

public class MazeOneShotTeleporter extends MazeGenericTeleporter {
    // Serialization
    private static final long serialVersionUID = 203L;

    // Constructors
    public MazeOneShotTeleporter() {
        super("OneShotTeleporter", "OneShotTeleporter", 0, 0, 0, 0);
    }

    public MazeOneShotTeleporter(final int destinationRow,
            final int destinationColumn, final int destinationFloor,
            final int destinationLevel) {
        super("OneShotTeleporter", "OneShotTeleporter", destinationRow,
                destinationColumn, destinationFloor, destinationLevel);
    }

    public MazeOneShotTeleporter(final String gameAppearance,
            final String editorAppearance, final int destinationRow,
            final int destinationColumn, final int destinationFloor,
            final int destinationLevel) {
        super(gameAppearance, editorAppearance, destinationRow,
                destinationColumn, destinationFloor, destinationLevel);
    }

    // Scriptability
    @Override
    public void postMoveAction(final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        app.decay();
        app.updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor(),
                this.getDestinationLevel());
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        app.decay();
        app.updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor(),
                this.getDestinationLevel());
    }

    @Override
    public String toString() {
        return "OT\n" + Integer.toString(this.getDestinationColumn()) + "\n"
                + Integer.toString(this.getDestinationRow()) + "\n"
                + Integer.toString(this.getDestinationFloor()) + "\n"
                + Integer.toString(this.getDestinationLevel());
    }

    @Override
    public String getName() {
        return "One-Shot Teleporter";
    }

    @Override
    public MazeObject editorHook() {
        final MazeObject mo = MazeMaker
                .editTeleporterDestination(MazeMaker.TELEPORTER_TYPE_ONESHOT);
        return mo;
    }
}