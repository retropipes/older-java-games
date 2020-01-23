package net.worldwizard.mazerunner;

public class MazeSunkenBlock extends MazeGenericGround {
    // Serialization
    private static final long serialVersionUID = 33L;

    // Constructors
    public MazeSunkenBlock() {
        super("SunkenBlock", "SunkenBlock", true, true, true, true);
    }

    @Override
    public String toString() {
        return "SB";
    }

    @Override
    public String getName() {
        return "Sunken Block";
    }
}