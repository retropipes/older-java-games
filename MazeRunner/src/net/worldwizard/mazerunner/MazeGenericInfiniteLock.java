package net.worldwizard.mazerunner;

public abstract class MazeGenericInfiniteLock extends MazeObject {
    // Field declarations
    private final MazeGenericInfiniteKey key;
    // Serialization
    private static final long serialVersionUID = 7988L;

    protected MazeGenericInfiniteLock(final String gameAppearance,
            final String editorAppearance, final MazeGenericInfiniteKey mgk) {
        super(true, gameAppearance, editorAppearance);
        this.key = mgk;
    }

    protected MazeGenericInfiniteLock(final String gameAppearance,
            final String editorAppearance, final MazeGenericInfiniteKey mgk,
            final boolean doesAcceptPushInto) {
        super(true, true, true, true, true, true, true, true, gameAppearance,
                editorAppearance, false, doesAcceptPushInto, false, false,
                false, false, true, false, 0);
        this.key = mgk;
    }

    // Accessor methods
    public MazeGenericInfiniteKey getKey() {
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
        final MazeRunner app = MazeRunner.getApplication();
        app.decay();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final Inventory inv) {
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((this.key == null) ? 0 : this.key.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof MazeGenericInfiniteLock)) {
            return false;
        }
        MazeGenericInfiniteLock other = (MazeGenericInfiniteLock) obj;
        if (this.key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!this.key.equals(other.key)) {
            return false;
        }
        return true;
    }
}