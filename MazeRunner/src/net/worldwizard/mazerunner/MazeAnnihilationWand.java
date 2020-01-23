package net.worldwizard.mazerunner;

public class MazeAnnihilationWand extends MazeGenericWand {
    // Serialization
    private static final long serialVersionUID = 8203L;

    public MazeAnnihilationWand() {
        super("AnnihilationWand", "AnnihilationWand");
    }

    @Override
    public String getName() {
        return "Annihilation Wand";
    }

    @Override
    public String toString() {
        return "AW";
    }

    @Override
    public void useHelper(final int x, final int y, final int z, final int w) {
        this.useAction(new MazeGround(), x, y, z, w);
    }
}
