package net.worldwizard.mazerunner;

public class MazeKKey extends MazeGenericInfiniteKey {
    // Serialization
    private static final long serialVersionUID = 511L;

    // Constructors
    public MazeKKey() {
        super("KKey", "KKey");
    }

    // Scriptability
    @Override
    public String toString() {
        return "KK";
    }

    @Override
    public String getName() {
        return "K Key";
    }
}