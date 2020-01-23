/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.effects.EffectConstants;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrap;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class UTurnTrap extends GenericTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public UTurnTrap() {
	super();
    }

    @Override
    public String getName() {
	return "U Turn Trap";
    }

    @Override
    public String getPluralName() {
	return "U Turn Traps";
    }

    @Override
    public byte getObjectID() {
	return (byte) 3;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	Messager.showMessage("Your controls are turned around!");
	Import1.getApplication().getGameManager().activateEffect(EffectConstants.EFFECT_U_TURNED,
		UTurnTrap.EFFECT_DURATION);
	if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    MazeObject.playRotatedSound();
	}
    }

    @Override
    public String getMoveSuccessSoundName() {
	return "change";
    }

    @Override
    public String getDescription() {
	return "U Turn Traps invert your controls for 10 steps when stepped on.";
    }
}