package net.worldwizard.mazerunner;

public class MazeRandomOneShotTeleporter extends MazeRandomTeleporter {
    // Serialization
    private static final long serialVersionUID = 105L;

    // Constructors
    public MazeRandomOneShotTeleporter() {
        super();
    }

    public MazeRandomOneShotTeleporter(final int newRandomRangeY,
            final int newRandomRangeX) {
        super("RandomOneShotTeleporter", "RandomOneShotTeleporter",
                newRandomRangeY, newRandomRangeX);
    }

    // Scriptability
    @Override
    public void postMoveAction(final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        app.decay();
        app.updatePosition(this.getDestinationRow(),
                this.getDestinationColumn());
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        app.decay();
        app.updatePosition(this.getDestinationRow(),
                this.getDestinationColumn());
    }

    @Override
    public String toString() {
        return "ROT\n" + Integer.toString(this.getRandomRowRange()) + "\n"
                + Integer.toString(this.getRandomColumnRange());
    }

    @Override
    public String getName() {
        return "Random One-Shot Teleporter";
    }

    @Override
    public MazeObject editorHook() {
        final MazeObject mo = MazeMaker
                .editTeleporterDestination(MazeMaker.TELEPORTER_TYPE_RANDOM_ONESHOT);
        return mo;
    }
}