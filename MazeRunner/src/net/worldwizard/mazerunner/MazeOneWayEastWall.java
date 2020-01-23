package net.worldwizard.mazerunner;

public class MazeOneWayEastWall extends MazeGenericWall {
    // Serialization
    private static final long serialVersionUID = 160L;

    public MazeOneWayEastWall() {
        super(true, true, true, false, true, true, true, false,
                "OneWayEastWall", "OneWayEastWall");
    }

    @Override
    public String toString() {
        return "OWEW";
    }

    @Override
    public String getName() {
        return "One-Way East Wall";
    }
}
