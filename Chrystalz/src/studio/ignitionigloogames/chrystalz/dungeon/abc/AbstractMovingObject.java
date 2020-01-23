/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon.abc;

import studio.ignitionigloogames.chrystalz.dungeon.DungeonConstants;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.TypeConstants;

public abstract class AbstractMovingObject extends AbstractGameObject {
    // Constructors
    public AbstractMovingObject(final boolean solid) {
        super(solid, false);
    }

    // Methods
    @Override
    public boolean isMoving() {
        return true;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        this.postMoveActionHook();
    }

    public void postMoveActionHook() {
        // Do nothing
    }

    @Override
    public int getLayer() {
        return DungeonConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_DUNGEON);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractGameObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
