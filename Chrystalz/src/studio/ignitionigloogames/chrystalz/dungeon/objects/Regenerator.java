/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon.objects;

import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractShop;
import studio.ignitionigloogames.chrystalz.manager.asset.ObjectImageConstants;
import studio.ignitionigloogames.chrystalz.shops.ShopType;

public class Regenerator extends AbstractShop {
    // Constructors
    public Regenerator() {
        super(ShopType.REGENERATOR);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.REGENERATOR;
    }

    @Override
    public String getName() {
        return "Regenerator";
    }

    @Override
    public String getPluralName() {
        return "Regenerators";
    }

    @Override
    public String getDescription() {
        return "Regenerators restore magic, for a fee.";
    }
}
