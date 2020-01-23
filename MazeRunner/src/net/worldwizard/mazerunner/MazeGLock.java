package net.worldwizard.mazerunner;

public class MazeGLock extends MazeGenericInfiniteLock {
    // Serialization
    private static final long serialVersionUID = 607L;

    // Constructors
    public MazeGLock() {
        super("GLock", "GLock", new MazeGKey());
    }

    public MazeGLock(final MazeGKey mk) {
        super("GLock", "GLock", mk);
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        if (this.isConditionallySolid(inv)) {
            Messager.showMessage("You need a G key");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need a G key");
        }
    }

    @Override
    public String toString() {
        return "GL";
    }

    @Override
    public String getName() {
        return "G Lock";
    }
}