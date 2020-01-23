/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.items.ShopTypes;
import com.puttysoftware.fantastlex.maze.abc.AbstractShop;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

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
