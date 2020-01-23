package net.worldwizard.mazerunner;

public class MazeFakeWall extends MazeGenericGround {
    // Serialization
    private static final long serialVersionUID = 4L;

    // Constructors
    public MazeFakeWall() {
        super("Wall", "FakeWall");
    }

    @Override
    public String toString() {
        return "FW";
    }

    @Override
    public String getName() {
        return "Fake Wall";
    }
}