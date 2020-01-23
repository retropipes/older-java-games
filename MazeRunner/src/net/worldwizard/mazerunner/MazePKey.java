package net.worldwizard.mazerunner;

public class MazePKey extends MazeGenericInfiniteKey {
    // Serialization
    private static final long serialVersionUID = 516L;

    // Constructors
    public MazePKey() {
        super("PKey", "PKey");
    }

    // Scriptability
    @Override
    public String toString() {
        return "PK";
    }

    @Override
    public String getName() {
        return "P Key";
    }
}