package net.worldwizard.mazerunner;

public class MazeLLock extends MazeGenericInfiniteLock {
    // Serialization
    private static final long serialVersionUID = 612L;

    // Constructors
    public MazeLLock() {
        super("LLock", "LLock", new MazeLKey());
    }

    public MazeLLock(final MazeLKey mk) {
        super("LLock", "LLock", mk);
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        if (this.isConditionallySolid(inv)) {
            Messager.showMessage("You need an L key");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need an L key");
        }
    }

    @Override
    public String toString() {
        return "LL";
    }

    @Override
    public String getName() {
        return "L Lock";
    }
}