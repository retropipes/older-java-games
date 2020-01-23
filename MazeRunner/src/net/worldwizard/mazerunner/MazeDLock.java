package net.worldwizard.mazerunner;

public class MazeDLock extends MazeGenericInfiniteLock {
    // Serialization
    private static final long serialVersionUID = 604L;

    // Constructors
    public MazeDLock() {
        super("DLock", "DLock", new MazeDKey());
    }

    public MazeDLock(final MazeDKey mk) {
        super("DLock", "DLock", mk);
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        if (this.isConditionallySolid(inv)) {
            Messager.showMessage("You need a D key");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need a D key");
        }
    }

    @Override
    public String toString() {
        return "DL";
    }

    @Override
    public String getName() {
        return "D Lock";
    }
}