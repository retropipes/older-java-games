package net.worldwizard.mazerunner;

public class MazeWallMakingWand extends MazeGenericWand {
    // Serialization
    private static final long serialVersionUID = 8202L;

    public MazeWallMakingWand() {
        super("WallMakingWand", "WallMakingWand");
    }

    @Override
    public String getName() {
        return "Wall-Making Wand";
    }

    @Override
    public String toString() {
        return "WMW";
    }

    @Override
    public void useHelper(final int x, final int y, final int z, final int w) {
        this.useAction(new MazeWall(), x, y, z, w);
    }
}
