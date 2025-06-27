/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericGem;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class BrightnessGem extends GenericGem {
    // Constructors
    public BrightnessGem() {
        super();
    }

    @Override
    public String getName() {
        return "Brightness Gem";
    }

    @Override
    public String getPluralName() {
        return "Brightness Gems";
    }

    @Override
    public byte getObjectID() {
        return (byte) 4;
    }

    @Override
    public void postMoveActionHook() {
        final int currLevel = Import1.getApplication().getGameManager().getPlayerManager().getPlayerLocationW();
        Import1.getApplication().getMazeManager().getMaze().setVisionRadiusToMaximum(currLevel);
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
        return "Brightness Gems increase the visible area to its maximum.";
    }
}
