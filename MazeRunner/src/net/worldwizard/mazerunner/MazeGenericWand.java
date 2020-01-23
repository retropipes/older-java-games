package net.worldwizard.mazerunner;

public abstract class MazeGenericWand extends MazeGenericUsableObject {
    // Serialization
    private static final long serialVersionUID = 8200L;

    // Constructors
    protected MazeGenericWand(final String gameAppearance,
            final String editorAppearance) {
        super(gameAppearance, editorAppearance, 1);
    }

    @Override
    public abstract String getName();

    @Override
    public abstract String toString();

    @Override
    public void useAction(final MazeObject mo, final int x, final int y,
            final int z, final int w) {
        final MazeRunner app = MazeRunner.getApplication();
        app.morph(mo, x, y, z, w);
    }

    @Override
    protected abstract void useHelper(int x, int y, int z, int w);
}
