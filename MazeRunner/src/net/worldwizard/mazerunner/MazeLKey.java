package net.worldwizard.mazerunner;

public class MazeLKey extends MazeGenericInfiniteKey {
    // Serialization
    private static final long serialVersionUID = 512L;

    // Constructors
    public MazeLKey() {
        super("LKey", "LKey");
    }

    // Scriptability
    @Override
    public String toString() {
        return "LK";
    }

    @Override
    public String getName() {
        return "L Key";
    }
}