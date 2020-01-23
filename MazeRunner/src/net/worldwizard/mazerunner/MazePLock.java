package net.worldwizard.mazerunner;

public class MazePLock extends MazeGenericInfiniteLock {
    // Serialization
    private static final long serialVersionUID = 616L;

    // Constructors
    public MazePLock() {
        super("PLock", "PLock", new MazePKey());
    }

    public MazePLock(final MazePKey mk) {
        super("PLock", "PLock", mk);
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        if (this.isConditionallySolid(inv)) {
            Messager.showMessage("You need a P key");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need a P key");
        }
    }

    @Override
    public String toString() {
        return "PL";
    }

    @Override
    public String getName() {
        return "P Lock";
    }
}