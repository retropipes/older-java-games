package net.worldwizard.mazerunner;

public class MazePushablePullableBlock extends MazeGenericMovableObject {
    // Serialization
    private static final long serialVersionUID = 3006L;

    // Constructors
    public MazePushablePullableBlock() {
        super("PushablePullableBlock", "PushablePullableBlock", true, true,
                new MazeTile());
    }

    @Override
    public void pullAction(final Inventory inv, final MazeObject mo,
            final int x, final int y, final int pushX, final int pushY) {
        final MazeObject obj = this.getSavedObject();
        final MazeRunner app = MazeRunner.getApplication();
        app.updatePulledPosition(x, y, pushX, pushY, obj, this);
        this.setSavedObject(mo);
    }

    @Override
    public String toString() {
        return "PUB\n" + this.getSavedObject().toString();
    }

    @Override
    public String getName() {
        return "Pushable/Pullable Block";
    }
}
