package net.worldwizard.mazerunner;

public abstract class MazeGenericMovableObject extends MazeObject {
    // Fields
    private MazeObject savedObject;
    // Serialization
    private static final long serialVersionUID = 7588L;

    // Constructors
    protected MazeGenericMovableObject(final String gameAppearance,
            final String editorAppearance, final boolean pushable,
            final boolean pullable, final MazeObject startingSavedObject) {
        super(true, true, true, true, true, true, true, true, gameAppearance,
                editorAppearance, pushable, false, false, pullable, false,
                false, true, false, 0);
        this.savedObject = startingSavedObject;
    }

    public MazeObject getSavedObject() {
        return this.savedObject;
    }

    public void setSavedObject(final MazeObject obj) {
        this.savedObject = obj;
    }

    @Override
    public void updateGraphics() {
        this.savedObject.updateGraphics();
        super.updateGraphics();
    }

    @Override
    public void pushAction(final Inventory inv, final MazeObject mo,
            final int x, final int y, final int pushX, final int pushY) {
        final MazeObject obj = this.getSavedObject();
        final MazeRunner app = MazeRunner.getApplication();
        app.updatePushedPosition(x, y, pushX, pushY, obj, this);
        this.setSavedObject(mo);
    }

    @Override
    public abstract String toString();

    @Override
    public abstract String getName();
}