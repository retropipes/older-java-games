package net.worldwizard.mazerunner;

public class MazeBoots extends MazeGenericPass {
    // Serialization
    private static final long serialVersionUID = 8003L;

    // Constructors
    public MazeBoots() {
        super("Boots", "Boots");
    }

    @Override
    public String getName() {
        return "Water-Walking Boots";
    }

    @Override
    public String toString() {
        return "BOOTS";
    }
}
