package net.worldwizard.mazerunner;

public class MazeMKey extends MazeGenericInfiniteKey {
    // Serialization
    private static final long serialVersionUID = 513L;

    // Constructors
    public MazeMKey() {
        super("MKey", "MKey");
    }

    // Scriptability
    @Override
    public String toString() {
        return "MK";
    }

    @Override
    public String getName() {
        return "M Key";
    }
}