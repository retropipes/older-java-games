/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.effects.EffectConstants;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrap;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class DizzinessTrap extends GenericTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public DizzinessTrap() {
	super();
    }

    @Override
    public String getName() {
	return "Dizziness Trap";
    }

    @Override
    public String getPluralName() {
	return "Dizziness Traps";
    }

    @Override
    public byte getObjectID() {
	return (byte) 5;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	Messager.showMessage("You feel dizzy!");
	Import1.getApplication().getGameManager().activateEffect(EffectConstants.EFFECT_DIZZY,
		DizzinessTrap.EFFECT_DURATION);
	if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    MazeObject.playDizzySound();
	}
    }

    @Override
    public String getMoveSuccessSoundName() {
	return "dizzy";
    }

    @Override
    public String getDescription() {
	return "Dizziness Traps randomly alter your controls each step for 10 steps when stepped on.";
    }
}