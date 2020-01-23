package net.worldwizard.mazerunner;

public abstract class MazeGenericCharacter extends MazeObject {
    // Serialization
    private static final long serialVersionUID = 7763L;

    // Constructors
    protected MazeGenericCharacter(final String gameAppearance,
            final String editorAppearance) {
        super(true, gameAppearance, editorAppearance);
    }

    @Override
    public abstract String getName();

    @Override
    public abstract String toString();
}
