package net.worldwizard.mazerunner;

public abstract class MazeGenericInfiniteKey extends MazeObject {
    // Fields related to serialization
    private static final long serialVersionUID = 7989L;

    // Constructors
    protected MazeGenericInfiniteKey(final String gameAppearance,
            final String editorAppearance) {
        super(false, gameAppearance, editorAppearance, false, 0, true);
    }

    // Scriptability
    @Override
    public void postMoveAction(final Inventory inv) {
        inv.addItem(this);
        final MazeRunner app = MazeRunner.getApplication();
        app.decay();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        inv.addItem(this);
        final MazeRunner app = MazeRunner.getApplication();
        app.decay();
    }

    @Override
    public abstract String toString();

    @Override
    public abstract String getName();
}