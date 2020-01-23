/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon.abc;

import studio.ignitionigloogames.chrystalz.dungeon.DungeonConstants;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.TypeConstants;

public abstract class AbstractTrigger extends AbstractGameObject {
    // Constructors
    protected AbstractTrigger() {
        super(false, true, false);
    }

    @Override
    public int getLayer() {
        return DungeonConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TRIGGER);
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