package net.worldwizard.mazerunner;

public class MazeTabletSlot extends MazeGenericInfiniteLock {
    // Serialization
    private static final long serialVersionUID = 202L;

    // Constructors
    public MazeTabletSlot() {
        super("TabletSlot", "TabletSlot", new MazeTablet());
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        if (this.isConditionallySolid(inv)) {
            Messager.showMessage("You need a tablet");
        }
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need a tablet");
        }
    }

    @Override
    public String toString() {
        return "IL";
    }

    @Override
    public String getName() {
        return "Tablet Slot";
    }
}