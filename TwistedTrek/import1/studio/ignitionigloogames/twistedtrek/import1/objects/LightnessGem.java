/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericGem;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class LightnessGem extends GenericGem {
    // Constructors
    public LightnessGem() {
        super();
    }

    @Override
    public String getName() {
        return "Lightness Gem";
    }

    @Override
    public String getPluralName() {
        return "Lightness Gems";
    }

    @Override
    public byte getObjectID() {
        return (byte) 3;
    }

    @Override
    public void postMoveActionHook() {
        final int currLevel = Import1.getApplication().getGameManager().getPlayerManager().getPlayerLocationW();
        Import1.getApplication().getMazeManager().getMaze().incrementVisionRadius(currLevel);
        if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            MazeObject.playLightSound();
        }
    }

    @Override
    public String getMoveSuccessSoundName() {
        return "light";
    }

    @Override
    public String getDescription() {
        return "Lightness Gems increase the visible area by 1.";
    }
}
