package net.worldwizard.mazerunner;

public class MazePlayer extends MazeGenericCharacter {
    // Serialization
    private static final long serialVersionUID = 7L;

    // Constructors
    public MazePlayer() {
        super("Player", "Player");
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public String getName() {
        return "Player";
    }

    @Override
    public MazeObject editorHook() {
        MazeMaker.setPlayerLocation();
        return this;
    }
}