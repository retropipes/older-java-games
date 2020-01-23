package net.worldwizard.mazerunner;

public class MazeUKey extends MazeGenericInfiniteKey {
    // Serialization
    private static final long serialVersionUID = 521L;

    // Constructors
    public MazeUKey() {
        super("UKey", "UKey");
    }

    // Scriptability
    @Override
    public String toString() {
        return "UK";
    }

    @Override
    public String getName() {
        return "U Key";
    }
}