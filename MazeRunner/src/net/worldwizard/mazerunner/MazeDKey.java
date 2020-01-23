package net.worldwizard.mazerunner;

public class MazeDKey extends MazeGenericInfiniteKey {
    // Serialization
    private static final long serialVersionUID = 504L;

    // Constructors
    public MazeDKey() {
        super("DKey", "DKey");
    }

    // Scriptability
    @Override
    public String toString() {
        return "DK";
    }

    @Override
    public String getName() {
        return "D Key";
    }
}