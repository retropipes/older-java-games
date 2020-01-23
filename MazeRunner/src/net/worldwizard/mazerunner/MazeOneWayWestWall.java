package net.worldwizard.mazerunner;

public class MazeOneWayWestWall extends MazeGenericWall {
    // Serialization
    private static final long serialVersionUID = 161L;

    public MazeOneWayWestWall() {
        super(true, true, false, true, true, true, false, true,
                "OneWayWestWall", "OneWayWestWall");
    }

    @Override
    public String toString() {
        return "OWWW";
    }

    @Override
    public String getName() {
        return "One-Way West Wall";
    }
}
