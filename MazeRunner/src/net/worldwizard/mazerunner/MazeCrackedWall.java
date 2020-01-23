package net.worldwizard.mazerunner;

public class MazeCrackedWall extends MazeGenericLock {
    // Fields related to serialization
    private static final long serialVersionUID = 401L;

    // Constructors
    public MazeCrackedWall() {
        super("CrackedWall", "CrackedWall", new MazeBomb());
    }

    public MazeCrackedWall(final MazeBomb newKey) {
        super("CrackedWall", "CrackedWall", newKey);
    }

    @Override
    public void preMoveAction(final Inventory inv) {
        if (this.isConditionallySolid(inv)) {
            Messager.showMessage("You need a bomb");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need a bomb");
        }
    }

    @Override
    public String toString() {
        return "L1";
    }

    @Override
    public String getName() {
        return "Cracked Wall";
    }
}