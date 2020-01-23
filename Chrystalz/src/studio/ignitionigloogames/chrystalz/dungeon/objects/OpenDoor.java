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

public class OpenDoor extends AbstractPassThroughObject {
    // Constructors
    public OpenDoor() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OPEN_DOOR;
    }

    // Scriptability
    @Override
    public String getName() {
        return "Open Door";
    }

    @Override
    public String getPluralName() {
        return "Open Doors";
    }

    @Override
    public String getDescription() {
        return "Open Doors can be closed by interacting with them.";
    }

    @Override
    public void interactAction() {
        SoundManager.playSound(SoundConstants.DOOR_CLOSES);
        final GameLogic glm = Chrystalz.getApplication().getGame();
        GameLogic.morph(new ClosedDoor());
        glm.redrawDungeon();
    }
}
