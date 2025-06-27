/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericGem;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class DarknessGem extends GenericGem {
    // Constructors
    public DarknessGem() {
        super();
    }

    @Override
    public String getName() {
        return "Darkness Gem";
    }

    @Override
    public String getPluralName() {
        return "Darkness Gems";
    }

    @Override
    public byte getObjectID() {
        return (byte) 2;
    }

    @Override
    public void postMoveActionHook() {
        final int currLevel = Import1.getApplication().getGameManager().getPlayerManager().getPlayerLocationW();
        Import1.getApplication().getMazeManager().getMaze().setVisionRadiusToMinimum(currLevel);
        if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            MazeObject.playDarknessSound();
        }
    }

    @Override
    public String getMoveSuccessSoundName() {
        return "darkness";
    }

    @Override
    public String getDescription() {
        return "Darkness Gems decrease the visible area to its minimum.";
    }
}
