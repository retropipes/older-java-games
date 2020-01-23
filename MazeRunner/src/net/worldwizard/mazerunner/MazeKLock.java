package net.worldwizard.mazerunner;

public class MazeKLock extends MazeGenericInfiniteLock {
    // Serialization
    private static final long serialVersionUID = 611L;

    // Constructors
    public MazeKLock() {
        super("KLock", "KLock", new MazeKKey());
    }

    public MazeKLock(final MazeKKey mk) {
        super("KLock", "KLock", mk);
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        if (this.isConditionallySolid(inv)) {
            Messager.showMessage("You need a K key");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need a K key");
        }
    }

    @Override
    public String toString() {
        return "KL";
    }

    @Override
    public String getName() {
        return "K Lock";
    }
}