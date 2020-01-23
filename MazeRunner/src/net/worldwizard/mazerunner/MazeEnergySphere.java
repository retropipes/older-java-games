package net.worldwizard.mazerunner;

public class MazeEnergySphere extends MazeGenericPass {
    // Serialization
    private static final long serialVersionUID = 8003L;

    // Constructors
    public MazeEnergySphere() {
        super("EnergySphere", "EnergySphere");
    }

    @Override
    public String getName() {
        return "Energy Sphere";
    }

    @Override
    public String toString() {
        return "ES";
    }
}
