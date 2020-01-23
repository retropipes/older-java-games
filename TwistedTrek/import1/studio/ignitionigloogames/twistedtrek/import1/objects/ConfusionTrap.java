/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.effects.EffectConstants;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrap;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class ConfusionTrap extends GenericTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public ConfusionTrap() {
	super();
    }

    @Override
    public String getName() {
	return "Confusion Trap";
    }

    @Override
    public String getPluralName() {
	return "Confusion Traps";
    }

    @Override
    public byte getObjectID() {
	return (byte) 4;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	Messager.showMessage("You are confused!");
	Import1.getApplication().getGameManager().activateEffect(EffectConstants.EFFECT_CONFUSED,
		ConfusionTrap.EFFECT_DURATION);
	if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    MazeObject.playConfusedSound();
	}
    }

    @Override
    public String getMoveSuccessSoundName() {
	return "confused";
    }

    @Override
    public String getDescription() {
	return "Confusion Traps randomly alter your controls for 10 steps when stepped on.";
    }
}