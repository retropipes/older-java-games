package net.worldwizard.mazerunner;

public class MazeTablet extends MazeGenericInfiniteKey {
    // Fields related to serialization
    private static final long serialVersionUID = 102L;

    // Constructors
    public MazeTablet() {
        super("Tablet", "Tablet");
    }

    @Override
    public String toString() {
        return "IK";
    }

    @Override
    public String getName() {
        return "Tablet";
    }
}