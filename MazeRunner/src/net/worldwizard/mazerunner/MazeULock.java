package net.worldwizard.mazerunner;

public class MazeULock extends MazeGenericInfiniteLock {
    // Serialization
    private static final long serialVersionUID = 621L;

    // Constructors
    public MazeULock() {
        super("ULock", "ULock", new MazeUKey());
    }

    public MazeULock(final MazeUKey mk) {
        super("ULock", "ULock", mk);
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        if (this.isConditionallySolid(inv)) {
            Messager.showMessage("You need a U key");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need a U key");
        }
    }

    @Override
    public String toString() {
        return "UL";
    }

    @Override
    public String getName() {
        return "U Lock";
    }
}