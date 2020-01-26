package net.worldwizard.mazerunner;

public class MazeFakeFinish extends MazeObject {
    // Fields related to serialization
    private static final long serialVersionUID = 10L;

    // Constructors
    public MazeFakeFinish() {
        super(false, "Finish", "FakeFinish");
    }

    @Override
    public void postMoveAction(final Inventory inv) {
        // Display fake exit message, if it's enabled
        final MazeRunner app = MazeRunner.getApplication();
        if (app.getMessageEnabled(MazeRunner.MESSAGE_FAKE_EXIT)) {
            Messager.showMessage("Fake exit!");
        }
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        // Display fake exit message, if it's enabled
        final MazeRunner app = MazeRunner.getApplication();
        if (app.getMessageEnabled(MazeRunner.MESSAGE_FAKE_EXIT)) {
            Messager.showMessage("Fake exit!");
        }
    }

    @Override
    public String toString() {
        return "FF";
    }

    @Override
    public String getName() {
        return "Fake Finish";
    }
}