package net.worldwizard.mazerunner;

public class MazeFinishTo extends MazeFinish {
    // Serialization
    private static final long serialVersionUID = 30L;

    // Constructors
    public MazeFinishTo() {
        super("FinishTo", "FinishTo", 0);
    }

    public MazeFinishTo(final int newDestinationLevel) {
        super("FinishTo", "FinishTo", newDestinationLevel);
    }

    // Scriptability
    @Override
    public void postMoveAction(final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        if (app.doesLevelExist(this.getDestinationLevel())) {
            Messager.showDialog("Level Solved!");
            app.solvedLevelWarp(this.getDestinationLevel());
        } else {
            Messager.showDialog("Maze Solved!");
            app.solvedMaze();
        }
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        if (app.doesLevelExist(this.getDestinationLevel())) {
            Messager.showDialog("Level Solved!");
            app.solvedLevelWarp(this.getDestinationLevel());
        } else {
            Messager.showDialog("Maze Solved!");
            app.solvedMaze();
        }
    }

    @Override
    public String toString() {
        return "FT\n" + Integer.toString(this.getDestinationLevel());
    }

    @Override
    public String getName() {
        return "Finish To...";
    }

    @Override
    public MazeObject editorHook() {
        final MazeObject mo = MazeMaker.editFinishToDestination();
        return mo;
    }
}