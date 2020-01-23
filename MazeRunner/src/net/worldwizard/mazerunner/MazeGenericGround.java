package net.worldwizard.mazerunner;

public abstract class MazeGenericGround extends MazeObject {
    // Serialization
    private static final long serialVersionUID = 7643L;

    // Constructors
    protected MazeGenericGround(final String gameAppearance,
            final String editorAppearance) {
        super(false, gameAppearance, editorAppearance);
    }

    protected MazeGenericGround(final String gameAppearance,
            final String editorAppearance, final boolean doesAcceptPushInto,
            final boolean doesAcceptPushOut, final boolean doesAcceptPullInto,
            final boolean doesAcceptPullOut) {
        super(false, false, false, false, false, false, false, false,
                gameAppearance, editorAppearance, false, doesAcceptPushInto,
                doesAcceptPushOut, false, doesAcceptPullInto,
                doesAcceptPullOut, true, false, 0, false, false);
    }

    protected MazeGenericGround(final String gameAppearance,
            final String editorAppearance, final boolean doesAcceptPushInto,
            final boolean doesAcceptPushOut, final boolean doesAcceptPullInto,
            final boolean doesAcceptPullOut, final boolean hasFriction) {
        super(false, false, false, false, false, false, false, false,
                gameAppearance, editorAppearance, false, doesAcceptPushInto,
                doesAcceptPushOut, false, doesAcceptPullInto,
                doesAcceptPullOut, hasFriction, false, 0, false, false);
    }

    @Override
    public abstract String toString();

    @Override
    public abstract String getName();
}