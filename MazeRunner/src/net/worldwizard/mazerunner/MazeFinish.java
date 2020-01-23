package net.worldwizard.mazerunner;

public class MazeFinish extends MazeGenericTeleporter {
    // Serialization
    private static final long serialVersionUID = 6L;

    // Constructors
    public MazeFinish() {
        super("Finish", "Finish", 0, 0, 0, 0);
    }

    public MazeFinish(final String gameAppearance,
            final String editorAppearance, final int destLevel) {
        super(gameAppearance, editorAppearance, 0, 0, 0, destLevel);
    }

    // Scriptability
    @Override
    public void postMoveAction(final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        if (app.isLevelAbove()) {
            Messager.showDialog("Level Solved!");
            app.solvedLevel();
        } else {
            Messager.showDialog("Maze Solved!");
            app.solvedMaze();
        }
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        if (app.isLevelAbove()) {
            Messager.showDialog("Level Solved!");
            app.solvedLevel();
        } else {
            Messager.showDialog("Maze Solved!");
            app.solvedMaze();
        }
    }

    @Override
    public String toString() {
        return "F";
    }

    @Override
    public String getName() {
        return "Finish";
    }

    @Override
    public MazeObject editorHook() {
        return this;
    }
}