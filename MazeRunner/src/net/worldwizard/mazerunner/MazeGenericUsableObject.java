package net.worldwizard.mazerunner;

public abstract class MazeGenericUsableObject extends MazeObject {
    // Serialization
    private static final long serialVersionUID = 8050L;

    // Constructors
    protected MazeGenericUsableObject(final String gameAppearance,
            final String editorAppearance, final int newUses) {
        super(false, gameAppearance, editorAppearance, true, newUses, true);
    }

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
    public abstract void useAction(MazeObject mo, int x, int y, int z, int w);

    @Override
    public abstract String getName();

    @Override
    public abstract String toString();

    @Override
    protected abstract void useHelper(int x, int y, int z, int w);
}
