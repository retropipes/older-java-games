package net.worldwizard.mazerunner;

public abstract class MazeGenericLock extends MazeObject {
    // Field declarations
    MazeGenericKey key;
    // Serialization
    private static final long serialVersionUID = 7999L;

    // Constructors
    protected MazeGenericLock(final String gameAppearance,
            final String editorAppearance, final MazeGenericKey mgk) {
        super(true, gameAppearance, editorAppearance);
        this.key = mgk;
    }

    // Accessor methods
    public MazeGenericKey getKey() {
        return this.key;
    }

    // Scriptability
    @Override
    public abstract void preMoveAction(final Inventory inv);

    @Override
    public abstract void preMoveAction(final boolean ie, final int dirX,
            final int dirY, final Inventory inv);

    @Override
    public void postMoveAction(final Inventory inv) {
        inv.removeItem(this.key);
        final MazeRunner app = MazeRunner.getApplication();
        app.decay();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        inv.removeItem(this.key);
        final MazeRunner app = MazeRunner.getApplication();
        app.decay();
    }

    @Override
    public boolean isConditionallySolid(final Inventory inv) {
        return !inv.isItemThere(this.key);
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final Inventory inv) {
        return !inv.isItemThere(this.key);
    }

    @Override
    public abstract String toString();

    @Override
    public abstract String getName();

    @Override
    public boolean equals(final Object obj) {
        final MazeGenericLock mgl = (MazeGenericLock) obj;
        if (super.equals(mgl) && this.key.equals(mgl.key)) {
            return true;
        } else {
            return false;
        }
    }
}