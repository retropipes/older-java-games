package net.worldwizard.mazerunner;

public class MazeNLock extends MazeGenericInfiniteLock {
    // Serialization
    private static final long serialVersionUID = 614L;

    // Constructors
    public MazeNLock() {
        super("NLock", "NLock", new MazeNKey());
    }

    public MazeNLock(final MazeNKey mk) {
        super("NLock", "NLock", mk);
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        if (this.isConditionallySolid(inv)) {
            Messager.showMessage("You need an N key");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need an N key");
        }
    }

    @Override
    public String toString() {
        return "NL";
    }

    @Override
    public String getName() {
        return "N Lock";
    }
}