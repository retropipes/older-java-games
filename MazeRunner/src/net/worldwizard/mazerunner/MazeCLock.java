package net.worldwizard.mazerunner;

public class MazeCLock extends MazeGenericInfiniteLock {
    // Serialization
    private static final long serialVersionUID = 603L;

    // Constructors
    public MazeCLock() {
        super("CLock", "CLock", new MazeCKey());
    }

    public MazeCLock(final MazeCKey mk) {
        super("CLock", "CLock", mk);
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        if (this.isConditionallySolid(inv)) {
            Messager.showMessage("You need a C key");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need a C key");
        }
    }

    @Override
    public String toString() {
        return "CL";
    }

    @Override
    public String getName() {
        return "C Lock";
    }
}