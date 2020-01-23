/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon.abc;

import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.dungeon.DungeonConstants;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.TypeConstants;
import studio.ignitionigloogames.common.random.RandomRange;

public abstract class AbstractTrap extends AbstractGameObject {
    // Fields
    private final int base;

    // Constructors
    protected AbstractTrap(final int baseName) {
        super(false, false);
        this.base = baseName;
    }

    // Scriptability
    @Override
    public abstract void postMoveAction(final boolean ie, final int dirX,
            final int dirY);

    @Override
    public int getBaseID() {
        return this.base;
    }

    @Override
    public int getLayer() {
        return DungeonConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TRAP);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractGameObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    public boolean shouldGenerateObject(final Dungeon maze, final int row,
            final int col, final int level, final int layer) {
        // Generate all traps at 25% rate
        final RandomRange reject = new RandomRange(1, 100);
        return reject.generate() < 25;
    }
}