package net.worldwizard.mazerunner;

public class MazeMLock extends MazeGenericInfiniteLock {
    // Serialization
    private static final long serialVersionUID = 613L;

    // Constructors
    public MazeMLock() {
        super("MLock", "MLock", new MazeMKey());
    }

    public MazeMLock(final MazeMKey mk) {
        super("MLock", "MLock", mk);
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        if (this.isConditionallySolid(inv)) {
            Messager.showMessage("You need an M key");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need an M key");
        }
    }

    @Override
    public String toString() {
        return "ML";
    }

    @Override
    public String getName() {
        return "M Lock";
    }
}