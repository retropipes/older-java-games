package net.worldwizard.mazerunner;

public class MazeLock extends MazeGenericLock {
    // Serialization
    private static final long serialVersionUID = 201L;

    // Constructors
    public MazeLock() {
        super("Lock", "Lock", new MazeKey());
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        if (this.isConditionallySolid(inv)) {
            Messager.showMessage("You need a key");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need a key");
        }
    }

    @Override
    public String toString() {
        return "L";
    }

    @Override
    public String getName() {
        return "Lock";
    }
}