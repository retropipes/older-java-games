package net.worldwizard.mazerunner;

public class MazeStairsDown extends MazeGenericTeleporter {
    // Serialization
    private static final long serialVersionUID = 3002L;

    // Constructors
    public MazeStairsDown() {
        super("StairsDown", "StairsDown", 0, 0, 0, 0);
    }

    public MazeStairsDown(final String gameAppearance,
            final String editorAppearance) {
        super(gameAppearance, editorAppearance, 0, 0, 0, 0);
    }

    // For derived classes only
    protected MazeStairsDown(final String gameAppearance,
            final String editorAppearance, final boolean doesAcceptPushInto) {
        super(gameAppearance, editorAppearance, doesAcceptPushInto);
    }

    @Override
    public String toString() {
        return "DOWN";
    }

    @Override
    public String getName() {
        return "Stairs Down";
    }

    @Override
    public int getDestinationRow() {
        final MazeRunner app = MazeRunner.getApplication();
        return app.getPlayerLocation(false, false);
    }

    @Override
    public int getDestinationColumn() {
        final MazeRunner app = MazeRunner.getApplication();
        return app.getPlayerLocation(false, true);
    }

    @Override
    public int getDestinationFloor() {
        final MazeRunner app = MazeRunner.getApplication();
        return app.getPlayerLocation(true, false) - 1;
    }

    @Override
    public int getDestinationLevel() {
        final MazeRunner app = MazeRunner.getApplication();
        return app.getPlayerLocation(true, true);
    }

    @Override
    public void postMoveAction(final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        app.updatePositionAbsoluteNoEvents(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor(),
                this.getDestinationLevel());
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        app.updatePositionAbsoluteNoEvents(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor(),
                this.getDestinationLevel());
    }

    @Override
    public MazeObject editorHook() {
        MazeMaker.pairStairs(MazeMaker.STAIRS_DOWN);
        return this;
    }
}
