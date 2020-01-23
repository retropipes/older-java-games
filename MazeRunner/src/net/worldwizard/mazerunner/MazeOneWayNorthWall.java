package net.worldwizard.mazerunner;

public class MazeOneWayNorthWall extends MazeGenericWall {
    // Serialization
    private static final long serialVersionUID = 162L;

    public MazeOneWayNorthWall() {
        super(true, false, true, true, true, false, true, true,
                "OneWayNorthWall", "OneWayNorthWall");
    }

    @Override
    public String toString() {
        return "OWNW";
    }

    @Override
    public String getName() {
        return "One-Way North Wall";
    }
}
