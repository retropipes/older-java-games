package net.worldwizard.mazerunner;

public class MazeGround extends MazeGenericGround {
    // Serialization
    private static final long serialVersionUID = 2L;

    // Constructors
    public MazeGround() {
        super("Ground", "Ground");
    }

    @Override
    public String toString() {
        return "G";
    }

    @Override
    public String getName() {
        return "Ground";
    }
}