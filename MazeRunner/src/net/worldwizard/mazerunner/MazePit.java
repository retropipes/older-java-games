package net.worldwizard.mazerunner;

public class MazePit extends MazeStairsDown {
    // Serialization
    private static final long serialVersionUID = 3003L;

    // Constructors
    public MazePit() {
        super("Pit", "Pit", true);
    }

    @Override
    public String toString() {
        return "PIT";
    }

    @Override
    public String getName() {
        return "Pit";
    }

    @Override
    public void preMoveAction(final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        if (!app.isFloorBelow()
                && app.getMessageEnabled(MazeRunner.MESSAGE_CANNOT_GO_THAT_WAY)) {
            Messager.showMessage("Can't go that way");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        if (!app.isFloorBelow()
                && app.getMessageEnabled(MazeRunner.MESSAGE_CANNOT_GO_THAT_WAY)) {
            Messager.showMessage("Can't go that way");
        }
    }

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
    public void pushIntoAction(final Inventory inv, final MazeObject pushed,
            final int x, final int y, final int z, final int w) {
        if (pushed.isPushable()) {
            final MazeRunner app = MazeRunner.getApplication();
            final MazePushableBlock pushedInto = (MazePushableBlock) pushed;
            app.updatePushedIntoPositionAbsolute(x, y, z - 1, w, x, y, z, w,
                    pushedInto, this);
        }
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        if (!app.isFloorBelow()) {
            if (ie) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public MazeObject editorHook() {
        return this;
    }
}
