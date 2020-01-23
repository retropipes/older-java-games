package net.worldwizard.mazerunner;

public class MazePushableBlock extends MazeGenericMovableObject {
    // Serialization
    private static final long serialVersionUID = 3004L;

    // Constructors
    public MazePushableBlock() {
        super("PushableBlock", "PushableBlock", true, false, new MazeTile());
    }

    @Override
    public String toString() {
        return "PB\n" + this.getSavedObject().toString();
    }

    @Override
    public String getName() {
        return "Pushable Block";
    }
}