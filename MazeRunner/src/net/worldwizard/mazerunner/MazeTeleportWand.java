package net.worldwizard.mazerunner;

public class MazeTeleportWand extends MazeGenericWand {
    // Serialization
    private static final long serialVersionUID = 8202L;

    public MazeTeleportWand() {
        super("TeleportWand", "TeleportWand");
    }

    @Override
    public String getName() {
        return "Teleport Wand";
    }

    @Override
    public String toString() {
        return "TW";
    }

    @Override
    public void useHelper(final int x, final int y, final int z, final int w) {
        this.useAction(null, x, y, z, w);
    }

    @Override
    public void useAction(final MazeObject mo, final int x, final int y,
            final int z, final int w) {
        final MazeRunner app = MazeRunner.getApplication();
        app.updatePositionAbsolute(x, y, z, w);
    }
}
