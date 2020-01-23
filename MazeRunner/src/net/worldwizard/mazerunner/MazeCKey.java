package net.worldwizard.mazerunner;

public class MazeCKey extends MazeGenericInfiniteKey {
    // Serialization
    private static final long serialVersionUID = 503L;

    // Constructors
    public MazeCKey() {
        super("CKey", "CKey");
    }

    // Scriptability
    @Override
    public String toString() {
        return "CK";
    }

    @Override
    public String getName() {
        return "C Key";
    }
}