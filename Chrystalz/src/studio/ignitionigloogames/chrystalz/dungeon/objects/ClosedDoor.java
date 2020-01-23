/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon.objects;

import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractPassThroughObject;
import studio.ignitionigloogames.chrystalz.game.GameLogic;
import studio.ignitionigloogames.chrystalz.manager.asset.ObjectImageConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundManager;

public class ClosedDoor extends AbstractPassThroughObject {
    // Constructors
    public ClosedDoor() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.CLOSED_DOOR;
    }

    // Scriptability
    @Override
    public String getName() {
        return "Closed Door";
    }

    @Override
    public String getPluralName() {
        return "Closed Doors";
    }

    @Override
    public String getDescription() {
        return "Closed Doors open when stepped on.";
    }

    @Override
    public void interactAction() {
        SoundManager.playSound(SoundConstants.DOOR_OPENS);
        final GameLogic glm = Chrystalz.getApplication().getGame();
        GameLogic.morph(new OpenDoor());
        glm.redrawDungeon();
    }
}
