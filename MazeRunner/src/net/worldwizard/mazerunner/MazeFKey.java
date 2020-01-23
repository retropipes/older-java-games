package net.worldwizard.mazerunner;

public class MazeFKey extends MazeGenericInfiniteKey {
    // Serialization
    private static final long serialVersionUID = 506L;

    // Constructors
    public MazeFKey() {
        super("FKey", "FKey");
    }

    // Scriptability
    @Override
    public String toString() {
        return "FK";
    }

    @Override
    public String getName() {
        return "F Key";
    }
}