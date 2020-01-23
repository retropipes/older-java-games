package net.worldwizard.mazerunner;

public class MazeBomb extends MazeGenericKey {
    // Serialization
    private static final long serialVersionUID = 301L;

    // Constructors
    public MazeBomb() {
        super("Bomb", "Bomb");
    }

    @Override
    public String toString() {
        return "K1";
    }

    @Override
    public String getName() {
        return "Bomb";
    }
}