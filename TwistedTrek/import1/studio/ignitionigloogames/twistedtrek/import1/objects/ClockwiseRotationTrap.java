/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.effects.EffectConstants;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrap;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class ClockwiseRotationTrap extends GenericTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public ClockwiseRotationTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Clockwise Rotation Trap";
    }

    @Override
    public String getPluralName() {
        return "Clockwise Rotation Traps";
    }

    @Override
    public byte getObjectID() {
        return (byte) 1;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            MazeObject.playRotatedSound();
        }
        Messager.showMessage("Your controls are rotated!");
        Import1.getApplication().getGameManager().activateEffect(EffectConstants.EFFECT_ROTATED_CLOCKWISE,
                ClockwiseRotationTrap.EFFECT_DURATION);
    }

    @Override
    public String getMoveSuccessSoundName() {
        return "change";
    }

    @Override
    public String getDescription() {
        return "Clockwise Rotation Traps rotate your controls clockwise for 10 steps when stepped on.";
    }
}