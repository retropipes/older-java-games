package net.worldwizard.mazerunner;

public class MazeZKey extends MazeGenericInfiniteKey {
    // Serialization
    private static final long serialVersionUID = 526L;

    // Constructors
    public MazeZKey() {
        super("ZKey", "ZKey");
    }

    // Scriptability
    @Override
    public String toString() {
        return "ZK";
    }

    @Override
    public String getName() {
        return "Z Key";
    }
}