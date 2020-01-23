package net.worldwizard.mazerunner;

public abstract class MazeGenericWall extends MazeObject {
    // Serialization
    private static final long serialVersionUID = 51L;

    // Constructors
    protected MazeGenericWall(final String gameAppearance,
            final String editorAppearance) {
        super(true, gameAppearance, editorAppearance);
    }

    protected MazeGenericWall(final boolean isSolidXN, final boolean isSolidXS,
            final boolean isSolidXE, final boolean isSolidXW,
            final boolean isSolidIN, final boolean isSolidIS,
            final boolean isSolidIE, final boolean isSolidIW,
            final String gameAppearance, final String editorAppearance) {
        super(isSolidXN, isSolidXS, isSolidXE, isSolidXW, isSolidIN, isSolidIS,
                isSolidIE, isSolidIW, gameAppearance, editorAppearance);
    }

    protected MazeGenericWall(final String gameAppearance,
            final String editorAppearance, final boolean isDestroyable,
            final boolean doesChainReact) {
        super(true, true, true, true, true, true, true, true, gameAppearance,
                editorAppearance, false, false, false, false, false, false,
                true, false, 0, isDestroyable, doesChainReact);
    }

    @Override
    public void preMoveAction(final Inventory inv) {
        // Display can't go that way message, if it's enabled
        final MazeRunner app = MazeRunner.getApplication();
        if (app.getMessageEnabled(MazeRunner.MESSAGE_CANNOT_GO_THAT_WAY)) {
            Messager.showMessage("Can't go that way");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        // Display can't go that way message, if it's enabled
        final MazeRunner app = MazeRunner.getApplication();
        if (app.getMessageEnabled(MazeRunner.MESSAGE_CANNOT_GO_THAT_WAY)) {
            Messager.showMessage("Can't go that way");
        }
    }

    @Override
    public abstract String toString();

    @Override
    public abstract String getName();
}