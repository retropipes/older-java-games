package net.worldwizard.mazerunner;

public class MazeWall extends MazeGenericWall {
    // Serialization
    private static final long serialVersionUID = 3L;

    // Constructors
    public MazeWall() {
        super("Wall", "Wall");
    }

    @Override
    public String toString() {
        return "W";
    }

    @Override
    public String getName() {
        return "Wall";
    }
}