package net.worldwizard.mazerunner;

public class MazeFinishMakingWand extends MazeGenericWand {
    // Serialization
    private static final long serialVersionUID = 8201L;

    public MazeFinishMakingWand() {
        super("FinishMakingWand", "FinishMakingWand");
    }

    @Override
    public String getName() {
        return "Finish-Making Wand";
    }

    @Override
    public String toString() {
        return "FMW";
    }

    @Override
    public void useHelper(final int x, final int y, final int z, final int w) {
        this.useAction(new MazeFinish(), x, y, z, w);
    }
}
