package net.worldwizard.mazerunner;

public class MazeFLock extends MazeGenericInfiniteLock {
    // Serialization
    private static final long serialVersionUID = 606L;

    // Constructors
    public MazeFLock() {
        super("FLock", "FLock", new MazeFKey());
    }

    public MazeFLock(final MazeFKey mk) {
        super("FLock", "FLock", mk);
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        if (this.isConditionallySolid(inv)) {
            Messager.showMessage("You need a F key");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need a F key");
        }
    }

    @Override
    public String toString() {
        return "FL";
    }

    @Override
    public String getName() {
        return "F Lock";
    }
}