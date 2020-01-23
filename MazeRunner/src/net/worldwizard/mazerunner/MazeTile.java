package net.worldwizard.mazerunner;

public class MazeTile extends MazeGenericGround {
    // Serialization
    private static final long serialVersionUID = 3004L;

    // Constructors
    public MazeTile() {
        super("Tile", "Tile", true, true, true, true);
    }

    @Override
    public String toString() {
        return "TILE";
    }

    @Override
    public String getName() {
        return "Tile";
    }
}