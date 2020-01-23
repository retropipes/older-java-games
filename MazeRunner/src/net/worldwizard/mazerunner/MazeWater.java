package net.worldwizard.mazerunner;

public class MazeWater extends MazeGenericField {
    // Serialization
    private static final long serialVersionUID = 8004L;

    // Constructors
    public MazeWater() {
        super("Water", "Water", new MazeBoots(), true);
    }

    public MazeWater(final MazeBoots b) {
        super("Water", "Water", b, true);
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        Messager.showMessage("You'll drown");
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        Messager.showMessage("You'll drown");
    }

    @Override
    public void pushIntoAction(final Inventory inv, final MazeObject pushed,
            final int x, final int y, final int z, final int w) {
        final MazeRunner app = MazeRunner.getApplication();
        if (pushed.isPushable()) {
            app.morph(new MazeSunkenBlock(), x, y, z, w);
        }
    }

    @Override
    public String getName() {
        return "Water";
    }

    @Override
    public String toString() {
        return "WATER";
    }
}
