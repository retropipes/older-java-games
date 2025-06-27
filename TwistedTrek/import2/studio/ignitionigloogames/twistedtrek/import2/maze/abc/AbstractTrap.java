/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.abc;

import studio.ignitionigloogames.randomrange.RandomRange;
import studio.ignitionigloogames.twistedtrek.import2.maze.Maze;
import studio.ignitionigloogames.twistedtrek.import2.maze.MazeConstants;
import studio.ignitionigloogames.twistedtrek.import2.maze.utilities.TypeConstants;

public abstract class AbstractTrap extends AbstractMazeObject {
    // Fields
    private final int base;

    // Constructors
    protected AbstractTrap(final int baseName) {
        super(false, false);
        this.base = baseName;
    }

    // Scriptability
    @Override
    public abstract void postMoveAction(final boolean ie, final int dirX, final int dirY);

    @Override
    public int getBaseID() {
        return this.base;
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TRAP);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    public boolean shouldGenerateObject(final Maze maze, final int row, final int col, final int floor, final int level,
            final int layer) {
        // Generate all traps at 25% rate
        final RandomRange reject = new RandomRange(1, 100);
        return reject.generate() < 25;
    }
}