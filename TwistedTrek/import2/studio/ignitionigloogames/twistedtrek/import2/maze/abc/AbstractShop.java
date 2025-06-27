/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.abc;

import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.maze.MazeConstants;
import studio.ignitionigloogames.twistedtrek.import2.maze.utilities.TypeConstants;
import studio.ignitionigloogames.twistedtrek.import2.shops.Shop;

public abstract class AbstractShop extends AbstractMazeObject {
    // Fields
    private final int shopType;

    // Constructors
    public AbstractShop(final int newShopType) {
        super(false, false);
        this.shopType = newShopType;
    }

    // Methods
    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_SHOP);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
        final Shop shop = Import2.getApplication().getGenericShop(this.shopType);
        if (shop != null) {
            shop.showShop();
        }
    }

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
