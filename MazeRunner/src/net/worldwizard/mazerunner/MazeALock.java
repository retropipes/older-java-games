package net.worldwizard.mazerunner;

public class MazeALock extends MazeGenericInfiniteLock {
    // Serialization
    private static final long serialVersionUID = 601L;

    // Constructors
    public MazeALock() {
        super("ALock", "ALock", new MazeAKey());
    }

    public MazeALock(final MazeAKey mk) {
        super("ALock", "ALock", mk);
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        if (this.isConditionallySolid(inv)) {
            Messager.showMessage("You need an A key");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need an A key");
        }
    }

    @Override
    public String toString() {
        return "AL";
    }

    @Override
    public String getName() {
        return "A Lock";
    }
}