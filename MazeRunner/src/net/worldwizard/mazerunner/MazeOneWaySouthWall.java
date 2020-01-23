package net.worldwizard.mazerunner;

public class MazeOneWaySouthWall extends MazeGenericWall {
    // Serialization
    private static final long serialVersionUID = 163L;

    public MazeOneWaySouthWall() {
        super(false, true, true, true, false, true, true, true,
                "OneWaySouthWall", "OneWaySouthWall");
    }

    @Override
    public String toString() {
        return "OWSW";
    }

    @Override
    public String getName() {
        return "One-Way South Wall";
    }
}
