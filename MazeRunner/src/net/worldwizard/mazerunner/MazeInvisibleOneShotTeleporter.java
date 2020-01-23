package net.worldwizard.mazerunner;

public class MazeInvisibleOneShotTeleporter extends
        MazeGenericInvisibleTeleporter {
    // Serialization
    private static final long serialVersionUID = 203L;

    // Constructors
    public MazeInvisibleOneShotTeleporter() {
        super("Ground", "InvisibleOneShotTeleporter", 0, 0, 0, 0);
    }

    public MazeInvisibleOneShotTeleporter(final int destinationRow,
            final int destinationColumn, final int destinationFloor,
            final int destinationLevel) {
        super("Ground", "InvisibleOneShotTeleporter", destinationRow,
                destinationColumn, destinationFloor, destinationLevel);
    }

    public MazeInvisibleOneShotTeleporter(final String gameAppearance,
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
        // Display invisible teleporter message, if it's enabled
        if (app.getMessageEnabled(MazeRunner.MESSAGE_INVISIBLE_TELEPORTER)) {
            Messager.showMessage("Invisible Teleporter!");
        }
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        app.decay();
        app.updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor(),
                this.getDestinationLevel());
        // Display invisible teleporter message, if it's enabled
        if (app.getMessageEnabled(MazeRunner.MESSAGE_INVISIBLE_TELEPORTER)) {
            Messager.showMessage("Invisible Teleporter!");
        }
    }

    @Override
    public String toString() {
        return "IOT\n" + Integer.toString(this.getDestinationColumn()) + "\n"
                + Integer.toString(this.getDestinationRow()) + "\n"
                + Integer.toString(this.getDestinationFloor()) + "\n"
                + Integer.toString(this.getDestinationLevel());
    }

    @Override
    public String getName() {
        return "Invisible One-Shot Teleporter";
    }

    @Override
    public MazeObject editorHook() {
        final MazeObject mo = MazeMaker
                .editTeleporterDestination(MazeMaker.TELEPORTER_TYPE_INVISIBLE_ONESHOT);
        return mo;
    }
}