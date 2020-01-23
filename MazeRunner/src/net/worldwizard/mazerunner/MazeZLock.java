package net.worldwizard.mazerunner;

public class MazeZLock extends MazeGenericInfiniteLock {
    // Serialization
    private static final long serialVersionUID = 626L;

    // Constructors
    public MazeZLock() {
        super("ZLock", "ZLock", new MazeZKey());
    }

    public MazeZLock(final MazeZKey mk) {
        super("ZLock", "ZLock", mk);
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        if (this.isConditionallySolid(inv)) {
            Messager.showMessage("You need a Z key");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need a Z key");
        }
    }

    @Override
    public String toString() {
        return "ZL";
    }

    @Override
    public String getName() {
        return "Z Lock";
    }
}