package net.worldwizard.mazerunner;

public abstract class MazeGenericPass extends MazeGenericInfiniteKey {
    // Serialization
    private static final long serialVersionUID = 8002L;

    // Constructors
    protected MazeGenericPass(final String gameAppearance,
            final String editorAppearance) {
        super(gameAppearance, editorAppearance);
    }

    @Override
    public abstract String toString();

    @Override
    public abstract String getName();
}
