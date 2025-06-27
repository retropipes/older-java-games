/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractShop;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.shops.ShopTypes;

public class SpellShop extends AbstractShop {
    // Constructors
    public SpellShop() {
        super(ShopTypes.SHOP_TYPE_SPELLS);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SPELL_SHOP;
    }

    @Override
    public String getName() {
        return "Spell Shop";
    }

    @Override
    public String getPluralName() {
        return "Spell Shops";
    }

    @Override
    public String getDescription() {
        return "Spell Shops teach spells, for a fee.";
    }
}
