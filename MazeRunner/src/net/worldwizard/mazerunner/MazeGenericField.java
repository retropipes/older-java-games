package net.worldwizard.mazerunner;

public abstract class MazeGenericField extends MazeGenericInfiniteLock {
    // Serialization
    private static final long serialVersionUID = 8001L;

    // Constructors
    protected MazeGenericField(final String gameAppearance,
            final String editorAppearance, final MazeGenericPass mgp) {
        super(gameAppearance, editorAppearance, mgp);
    }

    protected MazeGenericField(final String gameAppearance,
            final String editorAppearance, final MazeGenericPass mgp,
            final boolean doesAcceptPushInto) {
        super(gameAppearance, editorAppearance, mgp, doesAcceptPushInto);
    }

    // Scriptability
    @Override
    public abstract void preMoveAction(final Inventory inv);

    @Override
    public abstract void preMoveAction(final boolean ie, final int dirX,
            final int dirY, final Inventory inv);

    @Override
    public void postMoveAction(final Inventory inv) {
        // Do nothing
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        // Do nothing
    }

    @Override
    public boolean isConditionallySolid(final Inventory inv) {
        return !inv.isItemThere(this.getKey());
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final Inventory inv) {
        return !inv.isItemThere(this.getKey());
    }

    @Override
    public abstract String toString();

    @Override
    public abstract String getName();
}
