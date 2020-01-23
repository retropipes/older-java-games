package net.worldwizard.mazerunner;

public class MazeRLock extends MazeGenericInfiniteLock {
    // Serialization
    private static final long serialVersionUID = 618L;

    // Constructors
    public MazeRLock() {
        super("RLock", "RLock", new MazeRKey());
    }

    public MazeRLock(final MazeRKey mk) {
        super("RLock", "RLock", mk);
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        if (this.isConditionallySolid(inv)) {
            Messager.showMessage("You need an R key");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need an R key");
        }
    }

    @Override
    public String toString() {
        return "RL";
    }

    @Override
    public String getName() {
        return "R Lock";
    }
}