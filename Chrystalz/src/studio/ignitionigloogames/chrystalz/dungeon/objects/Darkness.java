/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon.objects;

import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractPassThroughObject;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.TypeConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.ObjectImageConstants;

public class Darkness extends AbstractPassThroughObject {
    // Constructors
    public Darkness() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.DARKNESS;
    }

    @Override
    public String getName() {
        return "Darkness";
    }

    @Override
    public String getPluralName() {
        return "Squares of Darkness";
    }

    @Override
    public String getDescription() {
        return "Squares of Darkness are what fills areas that cannot be seen.";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PASS_THROUGH);
        this.type.set(TypeConstants.TYPE_EMPTY_SPACE);
    }
}