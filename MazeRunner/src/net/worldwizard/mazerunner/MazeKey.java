package net.worldwizard.mazerunner;

public class MazeKey extends MazeGenericKey {
    // Serialization
    private static final long serialVersionUID = 101L;

    // Constructors
    public MazeKey() {
        super("Key", "Key");
    }

    // Scriptability
    @Override
    public String toString() {
        return "K";
    }

    @Override
    public String getName() {
        return "Key";
    }
}